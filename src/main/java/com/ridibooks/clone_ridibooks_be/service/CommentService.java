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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final StarsRepository starsRepository;

    @Transactional  // 댓글 수정
    public Comment update(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
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
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(()->new IllegalArgumentException("bookId가 존재하지 않습니다."));
        updateBookStars(requestDto, book);

        Optional<Stars> stars = starsRepository.findByBookId(requestDto.getBookId());
        if (stars.isPresent()) {
            updateStars(book, requestDto.getStars());
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
    public void updateStars(Book book, int ratedStar){
        Optional<Stars> stars = starsRepository.findByBookId(book.getId());

        if (stars.isPresent()) {
            Stars beforeStars =stars.get();
            Float avgStar = beforeStars.getAvgStar();
            Long totalCount = beforeStars.getTotalCount();
            beforeStars.setTotalCount(totalCount + 1L);
            beforeStars.setAvgStar((avgStar * totalCount + ratedStar) / beforeStars.getTotalCount());

            if (ratedStar==1) {
                beforeStars.setStar1Count(beforeStars.getStar1Count()+1);
            }else if(ratedStar==2){
                beforeStars.setStar2Count(beforeStars.getStar2Count()+1);
            }else if(ratedStar==3){
                beforeStars.setStar3Count(beforeStars.getStar3Count()+1);
            }else if(ratedStar==4){
                beforeStars.setStar4Count(beforeStars.getStar4Count()+1);
            }else if(ratedStar==5){
                beforeStars.setStar5Count(beforeStars.getStar5Count()+1);
            }
        }}

    @Transactional
    public void creatStars(Book book, int ratedStar){
        Stars stars = new Stars(book);

        stars.setTotalCount(1L);
        stars.setAvgStar((float)ratedStar);

        if (ratedStar==1) {
            stars.setStar1Count(stars.getStar1Count()+1);
        }else if(ratedStar==2){
            stars.setStar2Count(stars.getStar2Count()+1);
        }else if(ratedStar==3){
            stars.setStar3Count(stars.getStar3Count()+1);
        }else if(ratedStar==4){
            stars.setStar4Count(stars.getStar4Count()+1);
        }else if(ratedStar==5){
            stars.setStar5Count(stars.getStar5Count()+1);
        }
        starsRepository.save(stars);
    }

}