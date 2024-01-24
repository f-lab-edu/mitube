package com.misim.repository;

import com.misim.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
}