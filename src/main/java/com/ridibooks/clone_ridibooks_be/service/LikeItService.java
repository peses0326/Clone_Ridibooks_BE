package com.ridibooks.clone_ridibooks_be.service;

import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.model.LikeIt;
import com.ridibooks.clone_ridibooks_be.model.User;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.repository.LikeItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeItService {
    private final LikeItRepository likeItRepository;
    private final CommentRepository commentRepository;

    public String addLike(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("commentId가 없습니다."));
        // 좋아요 중복 체크
        Optional<LikeIt> likeItCheck = likeItChecker(user, comment);
        if (!likeItCheck.isPresent()) {
            likeItRepository.save(new LikeIt(comment, user));
            return "Like It";
        }else {
            likeItRepository.deleteById(likeItCheck.get().getId());
            return "false";
        }
    }

    private Optional<LikeIt> likeItChecker(User user, Comment comment) {
        return likeItRepository.findByUserAndComment(user, comment);
    }
}
