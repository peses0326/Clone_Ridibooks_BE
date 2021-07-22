package com.ridibooks.clone_ridibooks_be.service;

import com.ridibooks.clone_ridibooks_be.dto.CommentRequestDto;
import com.ridibooks.clone_ridibooks_be.model.Book;
import com.ridibooks.clone_ridibooks_be.model.Comment;
import com.ridibooks.clone_ridibooks_be.model.Stars;
import com.ridibooks.clone_ridibooks_be.repository.BookRepository;
import com.ridibooks.clone_ridibooks_be.repository.CommentRepository;
import com.ridibooks.clone_ridibooks_be.repository.StarsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final StarsRepository starsRepository;

    @Transactional  // 댓글 수정
    public Comment update(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(()->new IllegalArgumentException("bookId가 존재하지 않습니다."));
        Stars stars = book.getStars();
        float avgStars = stars.getAvgStar();
        Long totalCount = stars.getTotalCount();
        int beforeStars = comment.getStars();
        stars.setAvgStar((avgStars*totalCount-beforeStars+requestDto.getStars())/totalCount);
        comment.update(requestDto);
        return comment;
    }

    // 댓글 생성
    @Transactional
    public void createComment(CommentRequestDto requestDto) {
        if (!starCheckInRange(requestDto.getStars())) {
            throw new IllegalArgumentException("stars가 범위를 벗어났습니다.");
        }
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
        // 책 별점 업데이트
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(() -> new IllegalArgumentException("bookId가 존재하지 않습니다."));
        updateBookStars(requestDto, book);

        Stars stars = book.getStars();
        if (stars != null) {
            updateStars(stars, requestDto.getStars());
        } else {
            creatStars(book, requestDto.getStars());
        }
    }

    public boolean starCheckInRange(int stars) {
        return stars >= 0 && stars <= 5;
    }

    public void updateBookStars(CommentRequestDto requestDto, Book book) {
        Double avg = book.getAvgStars();
        Long count = book.getCountStars();
        book.updateCountStars(count + 1L);
        book.updateAvgStars((avg * count + requestDto.getStars()) / book.getCountStars());
    }

    @Transactional
    public void updateStars(Stars stars, int ratedStar) {
        Float avgStar = stars.getAvgStar();
        Long totalCount = stars.getTotalCount();
        stars.setTotalCount(totalCount + 1L);
        stars.setAvgStar((avgStar * totalCount + ratedStar) / stars.getTotalCount());
        countUpStar(stars, ratedStar);
    }

    @Transactional
    public void creatStars(Book book, int ratedStar) {
        Stars stars = new Stars();
        stars.setTotalCount(1L);
        stars.setAvgStar((float) ratedStar);
        countUpStar(stars, ratedStar);
        starsRepository.save(stars);
        book.createStars(stars);
    }

    private void countUpStar(Stars stars, int ratedStar) {
        if (ratedStar == 1) {
            stars.setStar1Count(stars.getStar1Count() + 1);
        } else if (ratedStar == 2) {
            stars.setStar2Count(stars.getStar2Count() + 1);
        } else if (ratedStar == 3) {
            stars.setStar3Count(stars.getStar3Count() + 1);
        } else if (ratedStar == 4) {
            stars.setStar4Count(stars.getStar4Count() + 1);
        } else if (ratedStar == 5) {
            stars.setStar5Count(stars.getStar5Count() + 1);
        }
    }
}