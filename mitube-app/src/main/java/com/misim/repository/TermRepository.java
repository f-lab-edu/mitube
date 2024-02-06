package com.misim.repository;

import com.misim.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT t1 FROM Term t1 Where (t1.id) IN (SELECT MAX(t2.id) AS max_id FROM Term t2 GROUP BY t2.title)")
    List<Term> findTermGroupByTitle();

    @Query("SELECT t1 FROM Term t1 WHERE t1.title = :title AND t1.id = (SELECT MAX(t2.id) FROM Term t2 WHERE t2.title = :title)")
    Term findTermByTitle(@Param("title") String title);

}