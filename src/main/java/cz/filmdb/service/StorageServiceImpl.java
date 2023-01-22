package cz.filmdb.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootDir = Paths.get("files");
    private final Path imgsDir = rootDir.resolve("imgs");
    private final Path usrImgsDir = imgsDir.resolve("user");
    private final Path filmworkImgsDir = imgsDir.resolve("filmwork");

    @Override
    public void init() {
        try {

            if (!Files.exists(rootDir))
                Files.createDirectory(rootDir);

            if (!Files.exists(imgsDir))
                Files.createDirectory(imgsDir);

            if (!Files.exists(usrImgsDir))
                Files.createDirectory(usrImgsDir);

            if (!Files.exists(filmworkImgsDir))
                Files.createDirectory(filmworkImgsDir);

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize the folders!", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootDir.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists!");
            }
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void store(MultipartFile file, Path path) {

        try {
            Files.copy(file.getInputStream(), path.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists!");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {

            return Files.walk(rootDir, 3).filter(path -> !path.equals(this.rootDir)).map(this.rootDir::relativize);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path load(String filename) {
        try {

            return rootDir.resolve(filename);

        } catch (InvalidPathException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        return loadAsResource(filename, null);
    }

    @Override
    public Resource loadAsResource(String filename, Path path) {
        try {

            Path file = path != null ? path.resolve(filename) : this.rootDir.resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() && !resource.isReadable())
                throw new RuntimeException("Could not read the file");

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootDir.toFile());
    }
}
