package cz.filmdb.controller;

import cz.filmdb.model.Occupation;
import cz.filmdb.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/by-person/{id}")
    public Page<Occupation> getOccupationsByPerson(@PathVariable(name = "id") String id, Pageable pageable) {
        return occupationService.loadOccupationsByPerson(Long.parseLong(id), pageable);
    }
}
