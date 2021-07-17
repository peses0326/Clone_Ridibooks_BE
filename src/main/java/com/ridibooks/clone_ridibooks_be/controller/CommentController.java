package com.ridibooks.clone_ridibooks_be.controller;

import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 댓글 전체 조회
    @GetMapping("/comment")
    public List<Comment> getAllComment() {
        return commentRepository.findAllByOrderByIdDesc();
    }

    // 책별 댓글 조회
    @GetMapping("/comment/{bookId}")
    public List<Comment> getComment(@PathVariable Long bookId) {
        return commentRepository.findAllByBookIdOrderByCreatedAtDesc(bookId).orElseThrow(() -> new IllegalArgumentException("해당 bookId가 없습니다."));
    }

    // 댓글 생성
    @PostMapping("/comment")
    public Comment createComment(@RequestBody CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        return commentRepository.save(comment);
    }

    @PutMapping("/comment/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.update(id, requestDto);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}