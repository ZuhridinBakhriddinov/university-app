package uz.project.app_university.entity.mark;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.entity.teacher.TeacherRepository;
import uz.project.app_university.entity.student.Student;
import uz.project.app_university.entity.student.StudentRepository;
import uz.project.app_university.entity.subject.Subject;
import uz.project.app_university.entity.subject.SubjectRepository;
import uz.project.app_university.exception.NotFoundSomethingException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkService {
    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public ResponseEntity<?> evaluateTheStudent(MarkDto markDto) {
        boolean exists = teacherRepository.existsById(markDto.getTeacherId());
        if (!exists) throw new NotFoundSomethingException("Teacher no found");
        Optional<Student> optionalStudent = studentRepository.findById(markDto.getStudentId());
        Student student = optionalStudent.orElseThrow(() -> new NotFoundSomethingException("Student not found"));
        Optional<Subject> optionalSubject = subjectRepository.findById(markDto.getSubjectId());
        Subject subject = optionalSubject.orElseThrow(() -> new NotFoundSomethingException("Subject not found"));
//        boolean yes = teacherRepository.isTeacherCanGradeThisGroupStudents
//                (markDto.getTeacherId(), student.getGroup().getId());
//        if(!yes)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("bad request", false,
//                "You cannot grade this student"));
        Mark savedMark = markRepository.save(new Mark(markDto.getMark(), subject, student));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                student.getName()+" successfully graded"));

    }
}
