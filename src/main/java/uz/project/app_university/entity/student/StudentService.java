package uz.project.app_university.entity.student;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.cryptography.Symmetric;
import uz.project.app_university.entity.group.GroupRepository;
import uz.project.app_university.enums.Role;
import uz.project.app_university.exception.AlreadyExistCustomException;
import uz.project.app_university.exception.CannotDeleteException;
import uz.project.app_university.exception.NotFoundSomethingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public final GroupRepository groupRepository;

    public ResponseEntity<?> addStudents(StudentDto studentDto) {
        boolean exists = studentRepository.existsByUsername(studentDto.getUsername());
        if (exists) throw new AlreadyExistCustomException("Student with this username already exist");
        Student save = studentRepository.save(new Student(studentDto.getName(), studentDto.getUsername(), studentDto.getPassword(), Role.ROLE_STUDENT));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, save.getName() + " saved successfully, id:"+save.getId()));
    }

    public ResponseEntity<?> getSubjectsByStudentId(Integer studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("not found", false));
        try {
            StudentSubjectsGroupsProjection subjectsByStudentId = studentRepository.getSubjectsByStudentId(studentId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                    subjectsByStudentId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("error", false));
        }
    }

    public ResponseEntity<?> getInformation(String name) {
        Optional<Student> optionalStudent = studentRepository.findByName(name);
        Student student = optionalStudent.orElseThrow(() -> new NotFoundSomethingException("Student with this name not found"));
        byte numberOfSubjectByGroupId = groupRepository.numberOfSubjectByGroupId(student.getGroup().getId());
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", student.getId());
        map.put("studentName", name);
        map.put("username", student.getUsername());
        map.put("password", Symmetric.encrypt(student.getPassword()));
        map.put("facultyId", student.getGroup().getFaculty().getId());
        map.put("facultyName", student.getGroup().getFaculty().getName());
        map.put("groupId", student.getGroup().getId());
        map.put("groupName", student.getGroup().getName());
        map.put("year", student.getGroup().getYear());
        map.put("numberOfSubject", numberOfSubjectByGroupId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, map));
    }

    public ResponseEntity<?> decryptPasswordForChecking(Integer studentId, String password) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseThrow(() -> new NotFoundSomethingException("User not found"));
        String decryptPa = Symmetric.decrypt(password);
        if (Objects.equals(decryptPa, student.getPassword()))
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, student.getName() + "'s password is " + decryptPa));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("error", false));
    }

    public ResponseEntity<?> editStudent(Integer studentId, StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseThrow(() -> new NotFoundSomethingException("Student not found"));
        whenStudentWantToEdit(student.getUsername(), studentDto.getUsername());
        student.setName(studentDto.getName());
        student.setUsername(studentDto.getUsername());
        student.setPassword(studentDto.getPassword());
        Student save = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,save.getName()+" edited"));
    }

    private void whenStudentWantToEdit(String currentName, String newName) {
        if (currentName.equals(newName))
            return;
        boolean isExistsByName = studentRepository.existsByUsername(newName);
        if (isExistsByName)
            throw new AlreadyExistCustomException("This username already exist, please find other one");
    }

    public ResponseEntity<?> deleteStudent(Integer studentId) {
        boolean isExist = studentRepository.existsById(studentId);
        if (!isExist)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Student not found ", false));
        try {
            studentRepository.deleteById(studentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("deleted", true));
        } catch (DataIntegrityViolationException e) {
            throw new CannotDeleteException("Sorry but you can not delete this student!");
        }
    }
}
