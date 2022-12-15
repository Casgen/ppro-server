package cz.filmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cz.filmdb.repo")
public class FilmDBServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmDBServerApplication.class, args);
    }

}
