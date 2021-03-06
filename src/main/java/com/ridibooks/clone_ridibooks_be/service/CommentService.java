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

    // 댓글 생성
    @Transactional
    public void createComment(CommentRequestDto requestDto) {
        // 댓글 저장
        if (!starCheckInRange(requestDto.getStars())) {
            throw new IllegalArgumentException("stars가 범위를 벗어났습니다.");
        }
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);

        // 책 별점 업데이트
        Book book = getBook(requestDto.getBookId());
        updateBookStars(requestDto, book);

        // 별점 테이블 업데이트
        Stars stars = book.getStars();
        if (stars != null) {
            updateStars(stars, requestDto.getStars());
        } else {
            creatStars(book, requestDto.getStars());
        }
    }

    @Transactional  // 댓글 수정
    public Comment update(Long commentId, CommentRequestDto requestDto) {
        // 별점 테이블 수정
        Comment comment = getComment(commentId);
        Book book = getBook(comment.getBookId());
        Stars stars = book.getStars();
        Double avgStars = stars.getAvgStar();
        Long totalCount = stars.getTotalCount();
        int beforeStars = comment.getStars();
        stars.setAvgStar(Math.round((avgStars * totalCount - beforeStars + requestDto.getStars()) / totalCount * 10) / 10.0);
        if (requestDto.getStars() != comment.getStars()) {
            countDownStar(stars, comment.getStars());
            countUpStar(stars, requestDto.getStars());
        }
        book.updateAvgStars(stars.getAvgStar());
        // 댓글 수정 업데이트
        comment.update(requestDto);
        return comment;
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId){
        // 별점 테이블 수정
        Comment comment = getComment(commentId);
        Book book = getBook(comment.getBookId());
        Stars stars = book.getStars();
        Double avgStars = stars.getAvgStar();
        Long totalCount = stars.getTotalCount();
        int beforeStars = comment.getStars();
        stars.setAvgStar(Math.round((avgStars * totalCount - beforeStars) / (totalCount-1L) * 10) / 10.0);
        stars.setTotalCount(totalCount-1L);
        countDownStar(book.getStars(), comment.getStars());
        // 책 별점 수정
        book.updateAvgStars(stars.getAvgStar());
        book.updateCountStars(stars.getTotalCount());
        //댓글 삭제
        commentRepository.deleteById(commentId);
    }

    public Comment getComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("commentId가 존재하지 않습니다."));
    }

    public Book getBook(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("bookId가 존재하지 않습니다."));
    }

    public boolean starCheckInRange(int stars) {
        return stars >= 0 && stars <= 5;
    }

    public void updateBookStars(CommentRequestDto requestDto, Book book) {
        Double avg = book.getAvgStars();
        Long count = book.getCountStars();
        book.updateCountStars(count + 1L);
        book.updateAvgStars(Math.round((avg * count + requestDto.getStars()) / book.getCountStars() * 10) / 10.0);
    }

    @Transactional
    public void updateStars(Stars stars, int ratedStar) {
        Double avgStar = stars.getAvgStar();
        Long totalCount = stars.getTotalCount();
        stars.setTotalCount(totalCount + 1L);
        stars.setAvgStar(Math.round((avgStar * totalCount + ratedStar) / stars.getTotalCount() * 10) / 10.0);
        countUpStar(stars, ratedStar);
    }

    //    double result1 = Math.round(num * 100) / 100.0
    @Transactional
    public void creatStars(Book book, int ratedStar) {
        Stars stars = new Stars();
        stars.setTotalCount(1L);
        stars.setAvgStar(Math.round(ratedStar * 10) / 10.0);
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

    private void countDownStar(Stars stars, int ratedStar) {
        if (ratedStar == 1) {
            stars.setStar1Count(stars.getStar1Count() - 1);
        } else if (ratedStar == 2) {
            stars.setStar2Count(stars.getStar2Count() - 1);
        } else if (ratedStar == 3) {
            stars.setStar3Count(stars.getStar3Count() - 1);
        } else if (ratedStar == 4) {
            stars.setStar4Count(stars.getStar4Count() - 1);
        } else if (ratedStar == 5) {
            stars.setStar5Count(stars.getStar5Count() - 1);
        }
    }
}