package com.nighthawk.spring_portfolio.mvc.song;

import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Data {
    
    @Bean
    CommandLineRunner initDatabase(AlbumJpaRepository albumRepo, ArtistJpaRepository artistRepo, GenreJpaRepository genreRepo) {
        return args -> {
            Artist artist1 = new Artist();
            artist1.setName("Feddie Mercury");
            artist1.setDob("1946-09-05");
            Artist artist2 = new Artist();
            artist2.setName("Kanye West");
            artist2.setDob("1977-06-08");
            Artist artist3 = new Artist();
            artist3.setName("Elvis Presley");
            artist3.setDob("1935-01-08");
            Artist artist4 = new Artist();
            artist4.setName("Taylor Swift");
            artist4.setDob("1989-12-13");
            Artist artist5 = new Artist();
            artist5.setName("Juice Wrld");
            artist5.setDob("1998-12-02");
            // Save artists
            artistRepo.save(artist1);
            artistRepo.save(artist2);
            artistRepo.save(artist3);
            artistRepo.save(artist4);
            artistRepo.save(artist5);

            Genre genreRap = new Genre();
            genreRap.setName("Rap");
            genreRap.setTitle("Goodbye & Good Riddance");
            Genre genreRock = new Genre();
            genreRock.setName("Rock");
            genreRock.setTitle("News of the World");
            Genre genrePop = new Genre();
            genrePop.setName("Pop");
            genrePop.setTitle("Lover");
            Genre genreCountry = new Genre();
            genreCountry.setName("Country");
            genreCountry.setTitle("Elvis Now");
            // Save genres
            genreRepo.save(genreRap);
            genreRepo.save(genreRock);
            genreRepo.save(genrePop);
            genreRepo.save(genreCountry);

            Map<String, Map<String, Object>> goodbyeAndGoodRiddanceSongs = new HashMap<>();
            goodbyeAndGoodRiddanceSongs.put("Song 1", Map.of("title", "Lucid Dreams", "duration", 231));
            goodbyeAndGoodRiddanceSongs.put("Song 2", Map.of("title", "Lean Wit Me", "duration", 242));
            Album goodbyeAneGoodRiddance = new Album();
            goodbyeAneGoodRiddance.setTitle("Goodbye & Good Riddance");
            goodbyeAneGoodRiddance.setDate("2018-05-23");
            goodbyeAneGoodRiddance.setSongs(goodbyeAndGoodRiddanceSongs);
            goodbyeAneGoodRiddance.getGenres().add(genreRap);

            Map<String, Map<String, Object>> newsOfTheWorldSongs = new HashMap<>();
            newsOfTheWorldSongs.put("Song 1", Map.of("title", "We Will Rock You", "duration", 122));
            newsOfTheWorldSongs.put("Song 2", Map.of("title", "We Are The Champions", "duration", 181));
            Album newsOfTheWorld = new Album();
            newsOfTheWorld.setTitle("News of the World");
            newsOfTheWorld.setDate("1977-10-28");
            newsOfTheWorld.setSongs(newsOfTheWorldSongs);
            newsOfTheWorld.getGenres().add(genreRock);
            Map<String, Map<String, Object>> loverSongs = new HashMap<>();
            loverSongs.put("Song 1", Map.of("title", "Cruel Summer", "duration", 179));
            loverSongs.put("Song 2", Map.of("title", "You Need To Calm Down", "duration", 172));
            Album lover = new Album();
            lover.setTitle("Lover");
            lover.setDate("2019-08-23");
            lover.setSongs(loverSongs);
            lover.getGenres().add(genrePop);
            lover.getGenres().add(genreCountry);
            albumRepo.save(goodbyeAneGoodRiddance);
            albumRepo.save(newsOfTheWorld);
            albumRepo.save(lover);

        };
    }
}
