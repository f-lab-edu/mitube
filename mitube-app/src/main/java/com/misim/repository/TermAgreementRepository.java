package com.misim.repository;

import com.misim.entity.TermAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermAgreementRepository extends JpaRepository<TermAgreement, Long> {
}
