package com.ridibooks.clone_ridibooks_be.controller;

import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2-1. Commet_댓글api"}) // Swagger # 2. Comment 댓글
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 댓글 전체 조회
    @ApiOperation(value="전체 댓글 조회", notes="모든 댓글을 조회합니다.")
    @GetMapping("/comment")
    public List<Comment> getAllComment() {
        return commentRepository.findAllByOrderByIdDesc();
    }

    // 책별 댓글 조회
    @ApiOperation(value="책별 댓글 조회", notes="책별 댓글을 조회합니다.")
    @GetMapping("/comment/{bookId}")
    public List<Comment> getComment(@PathVariable Long bookId) {
        return commentRepository.findAllByBookIdOrderByCreatedAtDesc(bookId).orElseThrow(() -> new IllegalArgumentException("해당 bookId가 없습니다."));
    }

    // 댓글 생성
    @ApiOperation(value="댓글 생성", notes="해당 댓글을 생성합니다.")
    @PostMapping("/comment")
    public Comment createComment(@RequestBody CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        return commentRepository.save(comment);
    }

    // 댓글 수정
    @ApiOperation(value="댓글 수정", notes="해당 댓글을 수정합니다.")
    @PutMapping("/comment/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.update(id, requestDto);
    }

    // 댓글 삭제
    @ApiOperation(value="댓글 삭제", notes="해당 댓글을 삭제합니다.")
    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}