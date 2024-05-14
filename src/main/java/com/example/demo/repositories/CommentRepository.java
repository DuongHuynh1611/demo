package com.example.demo.repositories;

import com.example.demo.models.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTutorialId(Long postId);

    @Transactional
    void deleteByTutorialId(long personId);
}
