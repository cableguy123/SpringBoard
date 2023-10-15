package com.deann.demo.Question;

import com.deann.demo.Answer.Answer;
import com.deann.demo.DataNotFoundException;
import com.deann.demo.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Write Recent Pages Date
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }
    public Page<Question> getList(int page,String content) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return this.questionRepository.findAllByKeyword(content,pageable);
    }
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()) {
            return question.get();
        }else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    public void modify(Question question,String subject,String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    public void vote(Question question,SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
    private Specification<Question> search(String context) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question,SiteUser> u1 = q.join("author",JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList",JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author",JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"),"%" + context + "%"),
                        cb.like(q.get("content"),"%" + context + "%"),
                        cb.like(u1.get("username"),"%" + context + "%"),
                        cb.like(a.get("content"),"%" + context + "%"),
                        cb.like(u2.get("username"),"%" + context + "%"));
            }
        };
    }
}
