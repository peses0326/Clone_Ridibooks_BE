package com.ridibooks.clone_ridibooks_be.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommentRequestDto {

    private Long bookId;
    private String comments;
    private Long stars;


}
