package cz.filmdb.repo;

import cz.filmdb.model.FilmWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmWorkRepository extends JpaRepository<FilmWork, Long> {
}
