package com.ridibooks.clone_ridibooks_be.repository;

import com.ridibooks.clone_ridibooks_be.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByIdDesc();
    Optional<List<Comment>> findAllByBookIdOrderByCreatedAtDesc(Long bookId);
    Optional<Comment> findByUsernameAndBookId(String username, Long bookId);
}