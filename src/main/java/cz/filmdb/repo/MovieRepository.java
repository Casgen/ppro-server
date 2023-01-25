package cz.filmdb.repo;

import cz.filmdb.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("SELECT m FROM Movie m WHERE SOUNDEX(m.name) = SOUNDEX(:query)")
    Page<Movie> findAllByNameContainingIgnoreCase(@Param("query") String query, Pageable pageable);

    Page<Movie> findAllByAudienceScoreGreaterThan(float score, Pageable pageable);

    Page<Movie> findAllByAudienceScoreGreaterThanEqual(float score, Pageable pageable);

    Page<Movie> findAllByAudienceScoreLessThanEqual(float score, Pageable pageable);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.genres genre JOIN genre.filmworks filmwork WHERE genre.id IN (:genreIds)")
    Page<Movie> findAllByGenreIds(@Param("genreIds") List<Long> id, Pageable pageable);

}
