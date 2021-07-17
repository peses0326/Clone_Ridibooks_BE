package com.ridibooks.clone_ridibooks_be.controller;

import com.ridibooks.clone_ridibooks_be.dto.CommentEditRequestDto;
import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentRepository commentRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }


    @GetMapping("/api/comments")
    public List<Comment> getComments() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        System.out.println(commentRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt")));
        return commentRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));

    }

    @PostMapping("/api/comments")
    public Comment createComment(@RequestBody CommentRequestDto requestDto) {
        log.info("request dto {}", requestDto);
        System.out.println(requestDto);
        Comment comment = new Comment(requestDto);
        comment.setBookId(requestDto.getBookId());
        comment.setComments(requestDto.getComments());
        comment.setStars(requestDto.getStars());
        return commentRepository.save(comment);
    }

    @PutMapping("/api/comments/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentEditRequestDto requestDto) {
        commentService.update(id, requestDto);
        return id;
    }

    @DeleteMapping("/api/comments/{id}")
    public Long deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return id;
    }

    public CommentService getCommentService() {
        return commentService;
    }
}