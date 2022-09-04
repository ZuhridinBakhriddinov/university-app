package uz.project.app_university.entity.university;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static uz.project.app_university.common.Constants.DEFAULT_PAGE_SIZE;


@Controller
@AllArgsConstructor
@RequestMapping("${app.domain}/university")
public class UniversityController {
    private final UniversityService universityService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllUniversities(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = "name") String sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        return universityService.getAllUniversities(pageable);
    }

    @GetMapping("/single/{universityId}")
    public ResponseEntity<?> getUniversityById(@PathVariable Integer universityId) {
        return universityService.getUniversityById(universityId);
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveNewUniversity(@Valid @RequestBody UniversityDto university) {
        return universityService.saveNewUniversity(university);
    }

    @PutMapping("/edit/{universityId}")
    public ResponseEntity<?> editeUniversity(@PathVariable Integer universityId,
                                             @Valid @RequestBody UniversityDto universityDto
    ) {
        return universityService.editeUniversity(universityId, universityDto);
    }

    @DeleteMapping("/delete/{universityId}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Integer universityId) {
        return universityService.deleteUniversity(universityId);
    }
}
