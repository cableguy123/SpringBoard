package com.deann.demo.Question;
import com.deann.demo.Answer.Answer;
import com.deann.demo.Question.Question;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

// Page Number
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

// Query
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface QuestionRepository extends JpaRepository<Question, Answer>{


    Optional<Question> findById(int i);
    List<Question> findBySubject(String subject);
    Question findBySubjectAndContent(String content,String subject);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);
    Page<Question> findAll(Specification<Question> spec,Pageable pageable);
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author = u1 "
            + "left outer join Answer a on a.question = q "
            + "left outer join SiteUser u2 on a.author = u2 "
            + "where "
            + "      q.subject like %:content% "
            + "      or q.content like %:content% "
            + "      or u1.username like %:content% "
            + "      or a.content like %:content% "
            + "      or u2.username like %:content% ")
    Page<Question> findAllByKeyword(@Param("content") String content,Pageable pageable);


}
