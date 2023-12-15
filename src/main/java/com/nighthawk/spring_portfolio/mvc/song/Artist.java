package com.nighthawk.spring_portfolio.mvc.song;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Artist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String dob;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.EAGER)
    private Set<Album> albums = new HashSet<>();

    public Artist() {}

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob(String dob) {
        return dob;
    }
}
