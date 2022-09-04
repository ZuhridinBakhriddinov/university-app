package uz.project.app_university.entity.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.faculty.FacultyRepository;
import uz.project.app_university.entity.student.StudentRepository;
import uz.project.app_university.exception.AlreadyExistCustomException;
import uz.project.app_university.exception.CannotDeleteException;
import uz.project.app_university.exception.NotFoundSomethingException;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;

    public ResponseEntity<?> addNewSubject(SubjectDto subjectDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(subjectDto.getFacultyId());
        Faculty faculty = optionalFaculty.orElseThrow(() -> new NotFoundSomethingException("Faculty not found"));
        Subject save = subjectRepository.save(new Subject(subjectDto.getName(), faculty));
        Subject savedSubject = subjectRepository.save(save);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                savedSubject.getName() + " successfully saved"));
    }

    public ResponseEntity<?> getSubjects(Integer facultyId) {
        List<Subject> subjects = subjectRepository.findByFacultyId(facultyId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, subjects));
    }

    public ResponseEntity<?> editSubject(Integer subjectId, SubjectDto subjectDto) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        Subject subject = optionalSubject.orElseThrow(() -> new NotFoundSomethingException("Subject not found"));
        whenSubjectNameChanging(subject.getName(), subjectDto.getName());
        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                savedSubject.getName() + " successfully update"));
    }


    private void whenSubjectNameChanging(String currentName, String newName) {
        if (currentName.equals(newName))
            return;
        boolean isExistsByName = subjectRepository.existsByName(newName);
        if (isExistsByName)
            throw new AlreadyExistCustomException("Group with this name already exist");
    }

    public ResponseEntity<?> getSubjectsStudentId(Integer studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) throw new NotFoundSomethingException("Student not found");
        List<StudentSubjectProjection> subjectsStudentId = subjectRepository.getSubjectsStudentId(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, subjectsStudentId));
    }

    public ResponseEntity<?> getSubjectWithMarks(Integer studentId, Integer subjectId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) throw new NotFoundSomethingException("Student not found");
        boolean exist = subjectRepository.existsById(subjectId);
        if (!exist) throw new NotFoundSomethingException("Subject not found");
        StudentSubjectProjection subjectWithMarks = subjectRepository.getSubjectWithMarks(studentId, subjectId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, subjectWithMarks));

    }

    public ResponseEntity<?> deleteSubject(Integer subjectId) {
        boolean exists = subjectRepository.existsById(subjectId);
        if (!exists) throw new NotFoundSomethingException("Subject not found");
        try {
            subjectRepository.deleteById(subjectId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("delete", true));
        } catch (Exception e) {
            throw new CannotDeleteException("Sorry but you can not delete this subject!");
        }
    }
}
