package com.nighthawk.spring_portfolio.mvc.song;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {
    
}
