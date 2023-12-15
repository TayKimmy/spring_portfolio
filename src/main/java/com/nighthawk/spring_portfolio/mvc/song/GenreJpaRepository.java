package com.nighthawk.spring_portfolio.mvc.song;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {
    
}
