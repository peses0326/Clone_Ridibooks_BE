package com.ridibooks.clone_ridibooks_be.model;

import com.ridibooks.clone_ridibooks_be.dto.StarsRequestDto;
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
public class Stars {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name="Stars_Id")
    private Long id;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="Book_ID",nullable = false)
//    private Book book;

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private Float avgStar; // 별점 평균

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private Long totalCount; // 전체 별점 개수

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private int star1Count; // 별점 1점 개수

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private int star2Count; // 별점 2점 개수

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private int star3Count; // 별점 3점 개수

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private int star4Count; // 별점 4점 개수

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private int star5Count; // 별점 5점 개수

//    public Stars(Book book) {
//        this.book = book;
//    }

    public void updateStars(StarsRequestDto requestDto){
        this.avgStar = avgStar;
        this.totalCount = totalCount;
        this.star1Count = star1Count;
        this.star2Count = star2Count;
        this.star3Count = star3Count;
        this.star4Count = star4Count;
        this.star5Count = star5Count;
    }
}