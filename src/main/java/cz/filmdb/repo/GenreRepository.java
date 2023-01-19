package cz.filmdb.repo;

import cz.filmdb.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g FROM Genre g WHERE SOUNDEX(g.name) = SOUNDEX(:query)")
    public List<Genre> findAllByNameContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT g FROM Genre g WHERE SOUNDEX(g.name) = SOUNDEX(:query)")
    public Page<Genre> findAllByNameContainingIgnoreCase(@Param("query") String query, Pageable pageable);
}
