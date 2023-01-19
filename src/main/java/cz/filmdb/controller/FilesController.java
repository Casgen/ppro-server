package cz.filmdb.controller;

import cz.filmdb.service.StorageService;
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

@RestController
@RequestMapping("api/v1/files")
@CrossOrigin("http://localhost:5173")
public class FilesController {

    private final StorageService storageService;

    @Autowired
    public FilesController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadUserImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "path", required = false) String pathParam) {
        String message;
        try {

            if (pathParam == null) {
                storageService.store(file);
            } else {

                String[] dirs = pathParam.split("/");
                Path path = Paths.get("files",dirs);

                storageService.store(file, path);
            }

            message = String.format("'%s' file was uploaded successfully.",Objects.requireNonNull(file.getOriginalFilename()));
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            message = String.format("'%s' couldn't be uploaded!: %s",Objects.requireNonNull(file.getOriginalFilename()), e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    private ResponseEntity<Resource> sendOkResponse(Resource file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename, @RequestParam(value = "path", required = false) String pathParam) {

        Resource file;
        try {

            if (pathParam == null) {
                file = storageService.loadAsResource(filename);
                return sendOkResponse(file);
            }

            String[] dirs = pathParam.split("/");
            Path path = Paths.get("files",dirs);

            file = storageService.loadAsResource(filename, path);

            return sendOkResponse(file);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

}
