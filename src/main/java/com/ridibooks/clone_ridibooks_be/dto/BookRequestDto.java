package com.ridibooks.clone_ridibooks_be.dto;

import lombok.Getter;

@Getter
public class BookRequestDto {
    private Long category;
    private String categoryDetail;
    private String bookname;
    private String imgUrl;
    private String bookDetailElements;
    private String bookIntro;
    private String writerIntro;
    private String bgColor;
    private String bookIndex;
    private String publicationDate;
    private String stars;
}
