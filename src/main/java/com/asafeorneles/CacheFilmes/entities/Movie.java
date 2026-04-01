package com.asafeorneles.CacheFilmes.entities;

import com.asafeorneles.CacheFilmes.enums.MovieAgeRating;
import com.asafeorneles.CacheFilmes.enums.MovieGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "movie_id")
    private UUID movieId;

    private String name;

    private Integer duration; // Minutes or Hours

    @Column(name = "movie_genre")
    @Enumerated(EnumType.STRING)
    private MovieGenre movieGenre;

    @Column(name = "age_rating")
    @Enumerated(EnumType.STRING)
    private MovieAgeRating movieAgeRating;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    List<Session> sessions;
}
