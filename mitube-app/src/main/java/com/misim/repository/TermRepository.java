package com.misim.repository;

import com.misim.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query(value = "select title from Term")
    List<String> findAllTitle();
}