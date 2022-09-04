package uz.project.app_university.entity.teacher;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.project.app_university.entity.student.StudentDto;

import static uz.project.app_university.common.Constants.DEFAULT_PAGE_SIZE;

@AllArgsConstructor
@Controller
@RequestMapping("${app.domain}/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTeachers(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return teacherService.getAllTeachers(pageable);
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> getTeachersSubjects(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "teacherId", defaultValue = "0") int teacherId
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return teacherService.getTeachersSubjects(pageable, teacherId);
    }

    @GetMapping("/mark/table/student")
    public ResponseEntity<?> getStudentListWithMark(
            @RequestParam(name = "groupId", defaultValue = "0") Integer groupId,
            @RequestParam(name = "subjectId", defaultValue = "0") Integer subjectId
    ) {
        return teacherService.getStudentListWithMark(groupId, subjectId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.addTeacher(teacherDto);
    }

    @PutMapping("/edit/{teacherId}")
    public ResponseEntity<?> editStudent(@PathVariable Integer teacherId, @RequestBody TeacherDto teacherDto) {
        return teacherService.editTeacher(teacherId,teacherDto);
    }

    @DeleteMapping("/delete/{teacherId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }

}
