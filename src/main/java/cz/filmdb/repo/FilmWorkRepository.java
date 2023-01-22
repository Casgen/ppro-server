package cz.filmdb.repo;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FilmWorkRepository extends JpaRepository<Filmwork, Long> {

    Set<Filmwork> findAllByGenresIn(Set<Genre> genres);

    @Query("SELECT DISTINCT f FROM Filmwork f JOIN f.genres genre WHERE genre.id IN (:genreIds)")
    List<Filmwork> findAllByGenreIds(@Param("genreIds") List<Long> genreIds);

}
