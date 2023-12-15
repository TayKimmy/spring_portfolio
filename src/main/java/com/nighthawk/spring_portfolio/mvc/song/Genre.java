package com.nighthawk.spring_portfolio.mvc.song;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String title;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Album> albums = new HashSet<>();

    public Genre() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
