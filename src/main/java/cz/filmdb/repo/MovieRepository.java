package cz.filmdb.repo;

import cz.filmdb.model.Genre;
import cz.filmdb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findMoviesByName(String name);
    List<Movie> getAllByOrderByAudienceScore();
    List<Movie> findMoviesByAudienceScoreGreaterThanEqual(float score);
    List<Movie> findMoviesByAudienceScoreLessThanEqual(float score);

    @Query("SELECT m, genre.name FROM Movie m JOIN m.genres genre JOIN genre.filmworks filmwork WHERE genre.id IN (:genreIds)")
    List<Movie> findMoviesByGenres(@Param("genreIds") List<Long> id);

}
