package cz.filmdb.controller;

import cz.filmdb.model.User;
import cz.filmdb.service.FilmWorkService;
import cz.filmdb.service.StorageService;
import cz.filmdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
@CrossOrigin("http://localhost:5173")
public class FilesController {

    private final StorageService storageService;
    private final FilmWorkService filmWorkService;
    private final UserService userService;

    @Autowired
    public FilesController(StorageService storageService, FilmWorkService filmWorkService, UserService userService) {
        this.storageService = storageService;
        this.filmWorkService = filmWorkService;
        this.userService = userService;
    }

    @PostMapping("/imgs/usr/upload/{userId}")
    public ResponseEntity<String> uploadUserFile(@RequestParam(name = "file") MultipartFile file,
                                                 @PathVariable(name = "userId") Long userId) {
        userService.addOrUpdateImgFile(userId, file);

        String message = String.format("'%s' file was uploaded successfully.", Objects.requireNonNull(file.getOriginalFilename()));
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/imgs/filmwork/upload/{filmworkId}")
    public ResponseEntity<String> uploadFilmworkFile(@RequestParam(name = "file", required = true) MultipartFile file,
                                                     @PathVariable(name = "filmworkId") Long filmworkId) {
        filmWorkService.addOrUpdateImgFile(filmworkId, file);

        String message = String.format("'%s' file was uploaded successfully.", Objects.requireNonNull(file.getOriginalFilename()));
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(name = "file", required = true) MultipartFile file) throws Exception {
        return saveFile(file);
    }

    @GetMapping("/imgs/usr/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getUsrImageFile(@PathVariable String filename) {
        return loadFile(filename, "imgs", "usr");
    }

    @GetMapping("/imgs/filmwork/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFilmworkImgFile(@PathVariable String filename) {
        return loadFile(filename, "imgs", "filmwork");
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        return loadFile(filename);
    }


    private ResponseEntity<Resource> loadFile(String filename, String... paths) {
        Path path = Paths.get("files", paths);
        Resource file = storageService.loadAsResource(filename, path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

    }

    private ResponseEntity<String> saveFile(MultipartFile file, String... paths) throws Exception {

        String message;

        try {
            Path path = Paths.get("files", paths);
            storageService.store(file, path);

            message = String.format("'%s' file was uploaded successfully.", Objects.requireNonNull(file.getOriginalFilename()));
            return ResponseEntity.ok().body(message);

        } catch (Exception e) {
            message = String.format("'%s' couldn't be uploaded!: %s", Objects.requireNonNull(file.getOriginalFilename()), e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    private ResponseEntity<String> deleteFile(String filename, String... paths) {
        return deleteFile(filename, Paths.get("files", paths));
    }

    /**
     * Deletes a file under the given path. THE PATH WILL ALSO AUTOMATICALLY INCLUDE THE 'FILES' DIR!
     * @param filename - Name of the file to be deleted
     * @param path - Path where the file is located under the root 'files'.
     * @return
     */
    private ResponseEntity<String> deleteFile(String filename, Path path) {
        String message;

        try {
            storageService.delete(filename, path);

            message = String.format("'%s' file was deleted successfully.", Objects.requireNonNull(filename));
            return ResponseEntity.ok().body(message);

        } catch (Exception e) {
            message = String.format("'%s' couldn't be deleted!: %s", Objects.requireNonNull(filename), e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }

}
