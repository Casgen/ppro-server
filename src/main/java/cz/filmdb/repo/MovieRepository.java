package cz.filmdb.repo;

import cz.filmdb.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findMoviesByName(String name, Pageable pageable);

    Page<Movie> findAllByAudienceScoreGreaterThan(float score, Pageable pageable);

    Page<Movie> findAllByAudienceScoreGreaterThanEqual(float score, Pageable pageable);

    Page<Movie> findAllByAudienceScoreLessThanEqual(float score, Pageable pageable);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.genres genre JOIN genre.filmworks filmwork WHERE genre.id IN (:genreIds)")
    List<Movie> findMoviesByGenres(@Param("genreIds") List<Long> id);

}
