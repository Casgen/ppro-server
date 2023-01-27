package cz.filmdb.service;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.FilmworkImage;
import cz.filmdb.model.User;
import cz.filmdb.repo.FilmWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class FilmWorkService {

    private final FilmWorkRepository filmworkRepository;
    private final StorageService storageService;

    @Autowired
    public FilmWorkService(FilmWorkRepository filmworkRepository, StorageService storageService) {
        this.filmworkRepository = filmworkRepository;
        this.storageService = storageService;
    }

    public void addOrUpdateImgFile(Long filmworkId, MultipartFile file) {

        try {

            Optional<Filmwork> optFilmwork = filmworkRepository.findById(filmworkId);

            if (optFilmwork.isEmpty()) throw new NullPointerException("filmwork was not found!");

            Filmwork filmwork = optFilmwork.get();
            Path path = Paths.get("files","imgs", "filmwork");

            storageService.store(file, path);

            Path src = path.resolve(file.getOriginalFilename());
            Path dst = path.resolve(String.format("%s-%d", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), filmwork.getId()));

            // This renames the given file to a unique identifier
            storageService.move(src, dst);

            FilmworkImage filmworkImage = new FilmworkImage(false,dst.toString());
            filmwork.addImg(filmworkImage);

            filmworkRepository.save(filmwork);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
