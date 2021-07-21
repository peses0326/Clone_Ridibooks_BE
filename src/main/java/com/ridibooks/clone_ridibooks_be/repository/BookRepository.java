package com.ridibooks.clone_ridibooks_be.repository;

import com.ridibooks.clone_ridibooks_be.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long > {
    Page<Book> findByCategoryIsGreaterThanAndCategoryIsLessThan(Long minCategory, Long maxCategory, Pageable pageable);
    Page<Book> findAllByCategory(Long category,Pageable pageable);
}
