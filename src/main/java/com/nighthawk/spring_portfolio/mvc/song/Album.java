package com.nighthawk.spring_portfolio.mvc.song;

import jakarta.persistence.*;
import java.util.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String date;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Artist> artists;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> songs;

    public Album() {
        this.artists = new HashSet<>();
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
        artist.getAlbums().add(this);
    }

    public void removeArtist(Artist artist) {
        artists.remove(artist);
        artist.getAlbums().remove(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public void setSongs(Map<String, Map<String, Object>> songs) {
        this.songs = songs;
    }

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "album_genre",
    joinColumns = @JoinColumn(name = "album_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id")
)
private Set<Genre> genres = new HashSet<>();

public Set<Genre> getGenres() {
    return genres;
}

public void setGenres(Set<Genre> genres) {
    this.genres = genres;
}

public String getDate() {
    return date;
}

}
