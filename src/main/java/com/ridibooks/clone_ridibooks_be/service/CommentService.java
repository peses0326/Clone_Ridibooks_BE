package com.ridibooks.clone_ridibooks_be.service;

import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.model.LikeIt;
import com.ridibooks.clone_ridibooks_be.model.User;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.repository.LikeItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeItRepository likeItRepository;

    @Transactional  // 댓글 수정 기능
    public Comment update(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        comment.update(requestDto);
        return comment;
    }

//    public List<Comment> getCommentList(Long bookId){
//        List<Comment> commentList = commentRepository.findAllByBookIdOrderByCreatedAtDesc(bookId).orElseThrow(() -> new IllegalArgumentException("해당 bookId가 없습니다."));
//        return likeItBoolean(commentLikesCounter(commentList));
//    }


//    // 좋아요 개수
//    public List<Comment> commentLikesCounter(List<Comment> commentList) {
//        for (Comment value : commentList) {
//            Long commentId = value.getId();
//            Long likesCount = likeItRepository.countByCommentId(commentId);
//            value.addCommentLikesCount(likesCount);
//        }
//        return commentList;
//    }

    // 좋아요 누름/안누름 체크
    public List<Comment> likeItChecker(List<Comment> commentList, User user) {
        for (Comment value : commentList) {
            Long commentId = value.getId();
            Long likesCount = likeItRepository.countByCommentId(commentId);
            value.addlikesCount(likesCount);
            if (user != null) {
                Optional<LikeIt> LikeItCheck = likeItRepository.findByUserAndComment(user, value);
                if (LikeItCheck.isPresent()) {
                    value.changeLikeItChecker(true);
                } else {
                    value.changeLikeItChecker(false);
                }
            } else {
                value.changeLikeItChecker(false);
            }
        }
        return commentList;
    }
}