package uz.project.app_university.entity.student;

import lombok.AllArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@PackagePrivate
@RequestMapping("${app.domain}/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<?> addStudents(@RequestBody StudentDto studentDto) {
        return studentService.addStudents(studentDto);
    }

    @GetMapping("/get/subjects/{studentId}")
    public ResponseEntity<?> getSubjectsByStudentId(@PathVariable Integer studentId) {
        return studentService.getSubjectsByStudentId(studentId);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getInformationBySearching(
            @RequestParam(name = "search", defaultValue = "") String name
    ) {
        return studentService.getInformation(name);
    }

    @GetMapping("/decrypt")
    public ResponseEntity<?> decryptPasswordForChecking(
            @RequestParam String password,
            @RequestParam Integer userId) {
        return studentService.decryptPasswordForChecking(userId, password);
    }

    @PutMapping("/edit/{studentId}")
    public ResponseEntity<?> editStudent(@PathVariable Integer studentId, @RequestBody StudentDto studentDto) {
        return studentService.editStudent(studentId,studentDto);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId) {
        return studentService.deleteStudent(studentId);
    }

}
