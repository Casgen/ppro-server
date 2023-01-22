package cz.filmdb.controller;

import cz.filmdb.model.Occupation;
import cz.filmdb.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/occupations")
@CrossOrigin("http://localhost:5173")
public class OccupationController {

    private final OccupationService occupationService;

    @Autowired
    public OccupationController(OccupationService occupationService) {
        this.occupationService = occupationService;
    }

    @GetMapping("/by-filmwork/{id}")
    public Page<Occupation> getOccupationsByFilmWork(@PathVariable(name = "id") String id, Pageable pageable) {
        return occupationService.loadOccupationsByFilmWork(Long.parseLong(id), pageable);
    }

    @PostMapping
    public List<Occupation> createOccupations(@RequestBody List<Occupation> occupations) {
        return occupationService.saveOccupations(occupations);
    }

    @PutMapping
    public List<Occupation> putOccupations(@RequestBody List<Occupation> occupations) {
        List<Occupation> updatedOccupation = occupationService.saveOccupations(occupations);

        if (updatedOccupation == null)
            ResponseEntity.status(503).body("Error occurred! Updating the occupations failed!");

        if (updatedOccupation.size() != occupations.size())
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Warning! Not all occupations were successfully updated!");

        ResponseEntity.ok().body("All occupations were updated successfully.");

        return updatedOccupation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOccupation(@PathVariable(name = "id") String occupationId) {
        occupationService.removeOccupation(Long.parseLong(occupationId));
        return ResponseEntity.ok().body("Occupation was removed successfully.");
    }

    @GetMapping("/by-person/{id}")
    public Page<Occupation> getOccupationsByPerson(@PathVariable(name = "id") String id, Pageable pageable) {
        return occupationService.loadOccupationsByPerson(Long.parseLong(id), pageable);
    }
}
