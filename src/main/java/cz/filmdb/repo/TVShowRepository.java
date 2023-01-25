package cz.filmdb.repo;

import cz.filmdb.model.Movie;
import cz.filmdb.model.TVShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TVShowRepository extends JpaRepository<TVShow, Long> {

    @Query("SELECT DISTINCT tv FROM TVShow tv JOIN tv.genres genre JOIN genre.filmworks filmwork WHERE genre.id IN (:genreIds)")
    Page<TVShow> findAllByGenres(@Param("genreIds") List<Long> id, Pageable pageable);

    @Query("SELECT show FROM TVShow show WHERE SOUNDEX(show.name) = SOUNDEX(:query)")
    Page<TVShow> findAllByNameContainingIgnoreCase(@Param("query") String query, Pageable pageable);
}
