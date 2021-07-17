package com.ridibooks.clone_ridibooks_be.model;

import com.ridibooks.clone_ridibooks_be.dto.CommentEditRequestDto;
import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Setter
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

    //유저 id
    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "user_id")
    //private User user;




    public Comment(Long bookId, String comments, Long stars) {
        //this.user = user;
        this.bookId = bookId;
        this.comments = comments;
        this.stars = stars;
    }

    public Comment(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }

    public void update(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }

    public void update(CommentEditRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }

}