package cz.filmdb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    void store(MultipartFile file, Path path);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    Resource loadAsResource(String filename, Path path);

    void deleteAll();

    void delete(String filename, Path path);

    void delete(Path pathToFile);

    void move(Path source, Path dst);

}
