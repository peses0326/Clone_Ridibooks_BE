package com.ridibooks.clone_ridibooks_be.model;

import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Comment extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    private Long stars;

    @Column(nullable = false)
    private String username;

    //유저 id
    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "user_id")
    //private User user;

    public Comment(Long bookId, String comments, Long stars, String username) {
        this.bookId = bookId;
        this.comments = comments;
        this.stars = stars;
        this.username = username;
    }

    public Comment(CommentRequestDto requestDto) {
        this.bookId = requestDto.getBookId();
        this.comments = requestDto.getComments();
        this.stars = requestDto.getStars();
        this.username = requestDto.getUsername();
    }

    public void update(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }
}