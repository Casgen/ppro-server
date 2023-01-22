package cz.filmdb.repo;

import cz.filmdb.model.Occupation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {

    @Query("SELECT o FROM Occupation o JOIN o.filmwork filmwork WHERE filmwork.id IN (:id)")
    Page<Occupation> findAllByFilmWork(@Param("id") Long id, Pageable pageable);

    @Query("SELECT o FROM Occupation o JOIN o.person person WHERE person.id IN (:id)")
    Page<Occupation> findAllByPerson(@Param("id") Long id, Pageable pageable);
}
