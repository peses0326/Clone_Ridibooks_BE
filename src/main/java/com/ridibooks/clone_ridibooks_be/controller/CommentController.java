package com.ridibooks.clone_ridibooks_be.controller;

import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.security.UserDetailsImpl;
import com.ridibooks.clone_ridibooks_be.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2. Commet_댓글api"}) // Swagger # 2. Comment 댓글
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 댓글 전체 조회
    @ApiOperation(value = "전체 댓글 조회", notes = "모든 댓글을 조회합니다.")
    @GetMapping("/comment")
    public List<Comment> getAllComment(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return commentService.likeItChecker(commentRepository.findAllByOrderByIdDesc(), userDetails.getUser());
        } else {
            return commentService.likeItChecker(commentRepository.findAllByOrderByIdDesc(), null);
        }
    }

    // 책별 댓글 조회
    @ApiOperation(value = "책별 댓글 조회", notes = "책별 댓글을 조회합니다.")
    @GetMapping("/comment/{bookId}")
    public List<Comment> getComment(@PathVariable Long bookId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Comment> commentList = commentRepository.findAllByBookIdOrderByCreatedAtDesc(bookId).orElseThrow(()->new IllegalArgumentException("bookId가 존재하지 않습니다."));
        if (userDetails != null) {
            return commentService.likeItChecker(commentList, userDetails.getUser());
        } else {
            return commentService.likeItChecker(commentList, null);
        }
    }

    // 로그인 유저 댓글 가져오기
    @ApiOperation(value = "책별 댓글 조회", notes = "책별 댓글을 조회합니다.")
    @GetMapping("/usercomment/{bookId}")
    public Comment getUserComment(@PathVariable Long bookId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return commentRepository.findByUsernameAndBookId(userDetails.getUsername(), bookId).orElseThrow(() -> new IllegalArgumentException("유저 댓글이 없습니다."));
        } else {
            throw new IllegalArgumentException("로그인 하지 않았습니다.");
        }
    }

    // 댓글 생성
    @ApiOperation(value = "댓글 생성", notes = "해당 댓글을 생성합니다.")
    @PostMapping("/comment")
    public void createComment(@RequestBody CommentRequestDto requestDto) {
        commentService.createComment(requestDto);
    }

    // 댓글 수정
    @ApiOperation(value = "댓글 수정", notes = "해당 댓글을 수정합니다.")
    @PutMapping("/comment/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.update(id, requestDto);
    }

    // 댓글 삭제
    @ApiOperation(value = "댓글 삭제", notes = "해당 댓글을 삭제합니다.")
    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}