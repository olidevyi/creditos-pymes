// src/main/java/com/setiembre2025nocountry/creditospymes/backend/service/implementacion/FirmaDigitalServisImpl.java
package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaInitRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaCompleteReq;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaInitReq;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Documento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.FirmaDigital;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.FirmaDigitalMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.DocumentoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.FirmaDigitalRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
import com.setiembre2025nocountry.creditospymes.backend.storage.StorageService;
import com.setiembre2025nocountry.creditospymes.backend.storage.StoredObject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FirmaDigitalServisImpl implements FirmaDigitalServis {

    private final FirmaDigitalRepository repo;
    private final DocumentoRepository docRepo;
    private final SolicitudCreditoRepository solRepo;
    private final UsuarioRepository userRepo;
    private final StorageService storage;
    private final FirmaDigitalMapper mapper;

    @Value("${app.sign.digest-alg:SHA-256}")
    private String defaultDigestAlg;

    @Value("${app.sign.policy-oid:2.16.32.1.1}") // ejemplo
    private String defaultPolicyOid;

    public FirmaDigitalServisImpl(FirmaDigitalRepository repo,
                                  DocumentoRepository docRepo,
                                  SolicitudCreditoRepository solRepo,
                                  UsuarioRepository userRepo,
                                  StorageService storage,
                                  FirmaDigitalMapper mapper) {
        this.repo = repo;
        this.docRepo = docRepo;
        this.solRepo = solRepo;
        this.userRepo = userRepo;
        this.storage = storage;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public FirmaInitRes init(FirmaInitReq req, String ip, String userAgent) {
        Documento doc = docRepo.findById(req.documentoId())
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado: " + req.documentoId()));
        Usuario firmante = userRepo.findById(req.firmanteId())
                .orElseThrow(() -> new EntityNotFoundException("Firmante no encontrado: " + req.firmanteId()));
        SolicitudCredito sol = doc.getSolicitudCredito() != null
                ? doc.getSolicitudCredito()
                : solRepo.findById(req.solicitudId()).orElseThrow(() ->
                new EntityNotFoundException("Solicitud no encontrada: " + req.solicitudId()));

        String format = guessFormat(doc.getTipoContenido()); // PAdES si es PDF, CAdES si no
        String toSignHash = doc.getSha256();

        FirmaDigital f = new FirmaDigital();
        f.setDocumento(doc);
        f.setDocSha256(doc.getSha256());
        f.setSolicitudCredito(sol);
        f.setFirmante(firmante);
        f.setEstado(EstadoFirmaDigital.EN_PROCESO);
        f.setSignatureFormat(format);
        f.setDigestAlg(defaultDigestAlg);
        f.setPolicyOid(defaultPolicyOid);
        f.setIpFirmante(ip);
        f.setUserAgent(userAgent);
        repo.save(f);

        return new FirmaInitRes(
                f.getId(),
                doc.getSha256(),
                defaultDigestAlg,
                toSignHash,
                defaultPolicyOid,
                format
        );
    }

    @Override
    @Transactional
    public FirmaDigitalDtoRes complete(FirmaCompleteReq req) throws IOException {
        FirmaDigital f = repo.findById(req.firmaId())
                .orElseThrow(() -> new EntityNotFoundException("Firma no encontrada: " + req.firmaId()));

        if (f.getEstado() != EstadoFirmaDigital.EN_PROCESO && f.getEstado() != EstadoFirmaDigital.PENDIENTE) {
            throw new IllegalStateException("Estado inválido para completar: " + f.getEstado());
        }

        String ct = contentTypeFor(req.signatureFormat());
        byte[] bytes = req.signatureContainer();
        if (bytes == null || bytes.length == 0) throw new IllegalArgumentException("Contenedor de firma vacío");

        StoredObject so;
        try (var in = new ByteArrayInputStream(bytes)) {
            so = storage.store(in, bytes.length, ct, "firma-" + f.getId());
        }

        f.setSignatureStorageKey(so.key());
        f.setSignAlg(req.signAlg());
        f.setCertSubject(req.certSubject());
        f.setCertIssuer(req.certIssuer());
        f.setCertSerial(req.certSerial());
        f.setCertFingerprint(req.certFingerprint());
        f.setEstado(EstadoFirmaDigital.FIRMADA);

        repo.save(f);
        return mapper.toDto(f);
    }

    @Override
    public boolean verify(Long firmaId) throws IOException {
        FirmaDigital f = repo.findById(firmaId)
                .orElseThrow(() -> new EntityNotFoundException("Firma no encontrada: " + firmaId));
        if (f.getEstado() != EstadoFirmaDigital.FIRMADA) return false;
        // Chequeo mínimo: existe el artefacto
        storage.load(f.getSignatureStorageKey()); // lanza si falta
        // TODO: integrar validación criptográfica/OCSP/CRL/TSA.
        return true;
    }

    @Override
    @Transactional
    public void revoke(Long firmaId, Long actorId, String motivo) {
        FirmaDigital f = repo.findById(firmaId)
                .orElseThrow(() -> new EntityNotFoundException("Firma no encontrada: " + firmaId));
        f.setEstado(EstadoFirmaDigital.RECHAZADA);
        // TODO: persistir evento en bitácora con actorId/motivo.
        repo.save(f);
    }

    @Override
    public FirmaDigitalDtoRes getById(Long id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Firma no encontrada: " + id));
    }

    @Override
    public List<FirmaDigitalDtoRes> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public InputStreamWithMeta download(Long firmaId) throws IOException {
        FirmaDigital f = repo.findById(firmaId)
                .orElseThrow(() -> new EntityNotFoundException("Firma no encontrada: " + firmaId));
        if (f.getSignatureStorageKey() == null) throw new EntityNotFoundException("Firma sin artefacto");
        return storage.load(f.getSignatureStorageKey());
    }

    private static String guessFormat(String contentType) {
        return (contentType != null && contentType.equalsIgnoreCase(MediaType.APPLICATION_PDF_VALUE))
                ? "PAdES" : "CAdES";
    }

    private static String contentTypeFor(String format) {
        if ("PAdES".equalsIgnoreCase(format)) return MediaType.APPLICATION_PDF_VALUE;
        return "application/pkcs7-signature";
    }
}
