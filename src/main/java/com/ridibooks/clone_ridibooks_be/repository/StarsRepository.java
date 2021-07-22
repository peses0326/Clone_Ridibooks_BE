package com.ridibooks.clone_ridibooks_be.repository;

import com.ridibooks.clone_ridibooks_be.model.Stars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarsRepository extends JpaRepository<Stars, Long> {
//    Optional<Stars> findByBookId(Long id);
}
