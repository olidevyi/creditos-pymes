package com.setiembre2025nocountry.creditospymes.backend.model.repository;

import com.setiembre2025nocountry.creditospymes.backend.model.entity.KycRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRequestRepository extends JpaRepository<KycRequest, Long> {
}
