package cz.filmdb.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @SequenceGenerator(
            sequenceName = "movie_sequence",
            name = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "movie_sequence"
    )
    public Long id;
    @OneToOne
    public FilmWork filmWork;
    public int yearOfRelease;
    @ManyToMany
    public ArrayList<Person> directors;

    public Movie(FilmWork filmWork, int yearOfRelease, ArrayList<Person> directors) {
        this.filmWork = filmWork;
        this.yearOfRelease = yearOfRelease;
        this.directors = directors;
    }

    public Movie(Long id, FilmWork filmWork, int yearOfRelease, ArrayList<Person> directors) {
        this.id = id;
        this.filmWork = filmWork;
        this.yearOfRelease = yearOfRelease;
        this.directors = directors;
    }

    public Movie() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilmWork getFilmWork() {
        return filmWork;
    }

    public void setFilmWork(FilmWork filmWork) {
        this.filmWork = filmWork;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public ArrayList<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Person> directors) {
        this.directors = directors;
    }

}
