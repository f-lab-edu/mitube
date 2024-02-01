package com.misim.repository;

import com.misim.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT DISTINCT title FROM Term")
    List<String> findTitles();

    @Query("SELECT t1 FROM Term t1 WHERE t1.title = :title AND t1.modifiedDate = (SELECT MAX(t2.modifiedDate) FROM Term t2 WHERE t2.title = :title)")
    Term findTermByTitle(@Param("title") String title);

}