package com.ridibooks.clone_ridibooks_be.service;

import com.ridibooks.clone_ridibooks_be.model.Book;
import com.ridibooks.clone_ridibooks_be.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Page<Book> getAllBooks(Long category, Pageable pageable) {
        Long maxCategory = category+100;
        return bookRepository.findByCategoryIsGreaterThanAndCategoryIsLessThan(category,maxCategory ,pageable);
    }

    public Page<Book> getBooks(Long category, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        if (category % 100 == 0) {
            return getAllBooks(category, pageable);
        }else {
            return bookRepository.findAllByCategory(category, pageable);
        }
    }
}