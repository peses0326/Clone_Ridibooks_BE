package com.ridibooks.clone_ridibooks_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Double avgStars; // 평균 평점

    @Column(nullable = false)
    private Long countStars; // 평점 총 개수

    public void updateCountStars(Long countStars) {
        this.countStars = countStars;
    }

    public void updateAvgStars(Double avgStars) {
        this.avgStars = avgStars;
    }
}
