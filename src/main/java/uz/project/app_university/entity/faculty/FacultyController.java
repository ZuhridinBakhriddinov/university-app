package uz.project.app_university.entity.faculty;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("${app.domain}/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping("/add/{universityId}")
    public ResponseEntity<?> addNewFaculty(@PathVariable Integer universityId,
                                           @Valid @RequestBody FacultyDto faculty
    ) {
        return facultyService.addNewFaculty(universityId, faculty);
    }

    @GetMapping("/getAll/{universityId}")
    public ResponseEntity<?> getAllFacultiesByUniversityId(@PathVariable Integer universityId) {
        return facultyService.getAllFacultiesByUniversityId(universityId);
    }

    @PutMapping("/edit/{facultyId}")
    public ResponseEntity<?> editFacultyById(@PathVariable Integer facultyId,
                                             @Valid @RequestBody FacultyDto faculty
    ) {
        return facultyService.editFacultyById(facultyId,faculty);
    }

    @GetMapping("/groups/students/{facultyId}")
    public ResponseEntity<?> getGroupsWithNumberOfStudent(@PathVariable Integer facultyId){
        return facultyService.getGroupsWithNumberOfStudent(facultyId);
    }

    @DeleteMapping("/delete/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Integer facultyId){
        return facultyService.deleteFaculty(facultyId);
    }
}
