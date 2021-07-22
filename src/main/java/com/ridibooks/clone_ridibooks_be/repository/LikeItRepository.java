package com.ridibooks.clone_ridibooks_be.repository;

import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.model.LikeIt;
import com.ridibooks.clone_ridibooks_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeItRepository extends JpaRepository<LikeIt, Long> {
    Optional<LikeIt> findByUserAndComment(User user, Comment comment);
    Long countByCommentId(Long commentId);
}
