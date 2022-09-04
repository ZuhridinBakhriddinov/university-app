package uz.project.app_university.entity.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("${app.domain}/subject")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewSubject(
            @Valid @RequestBody SubjectDto subjectDto
    ) {
        return subjectService.addNewSubject(subjectDto);
    }

    @GetMapping("/subjects/{facultyId}")
    public ResponseEntity<?> getSubjects(@PathVariable Integer facultyId) {
        return subjectService.getSubjects(facultyId);
    }

    @PutMapping("/edit/{subjectId}")
    public ResponseEntity<?> editSubject(
            @PathVariable Integer subjectId,
            @Valid @RequestBody SubjectDto subjectDto
    ) {
        return subjectService.editSubject(subjectId, subjectDto);
    }

    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer subjectId){
        return subjectService.deleteSubject(subjectId);
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getSubjectSByStudentId(
            @PathVariable Integer studentId
    ) {
        return subjectService.getSubjectsStudentId(studentId);
    }

    @GetMapping("/student/mark")
    public ResponseEntity<?> getSubjectWithMarks(
            @RequestParam(name = "studentId") Integer studentId,
            @RequestParam(name = "subjectId") Integer subjectId
    ) {
        return subjectService.getSubjectWithMarks(studentId, subjectId);
    }


}
