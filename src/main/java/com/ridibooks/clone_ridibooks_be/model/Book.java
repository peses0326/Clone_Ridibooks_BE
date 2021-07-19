package com.ridibooks.clone_ridibooks_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = true)
    private Long category; // 카테고리

    @Column(nullable = true)
    private String categoryDetail; // 세부 카테고리 명

    @Column(nullable = true)
    private String bookname; // 책 제목

    @Column(nullable=true)
    private String imgUrl; // 이미지

    @Column(nullable = true)
    private String bookDetailElements; // 책 정보

    @Column(columnDefinition = "LONGTEXT")
    private String bookIntro; // 책 소개

    @Column(columnDefinition = "LONGTEXT")
    private String writerIntro; // 저자 소개

    @Column(nullable=true)
    private String bgColor; // backgrount color

    @Column(columnDefinition = "LONGTEXT")
    private String bookIndex; // 목차

    @Column(nullable = true)
    private String publicationDate; // 출간일

    @Column(nullable = false)
    @ColumnDefault("0")
    private double stars; // 평정

    public Book(Long category, String categoryDetail, String bookname, String imgUrl, String bookDetailElements, String bookIntro, String writerIntro, String bgColor, String bookIndex, String publicationDate) {
        this.category = category;
        this.categoryDetail = categoryDetail;
        this.bookname = bookname;
        this.imgUrl = imgUrl;
        this.bookDetailElements = bookDetailElements;
        this.bookIntro = bookIntro;
        this.writerIntro = writerIntro;
        this.bgColor = bgColor;
        this.bookIndex = bookIndex;
        this.publicationDate = publicationDate;
    }
}
