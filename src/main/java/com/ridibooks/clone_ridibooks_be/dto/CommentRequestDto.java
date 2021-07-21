package com.ridibooks.clone_ridibooks_be.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String username;
    private Long bookId;
    private String comments;
    private Double stars;
}
