package uz.project.app_university.entity.teacher;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.faculty.FacultyRepository;
import uz.project.app_university.entity.group.GroupRepository;
import uz.project.app_university.entity.group.StudentMarkTableProjection;
import uz.project.app_university.entity.student.Student;
import uz.project.app_university.entity.student.StudentDto;
import uz.project.app_university.entity.subject.SubjectRepository;
import uz.project.app_university.enums.Role;
import uz.project.app_university.exception.AlreadyExistCustomException;
import uz.project.app_university.exception.CannotDeleteException;
import uz.project.app_university.exception.NotFoundSomethingException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final FacultyRepository facultyRepository;

    public ResponseEntity<?> getAllTeachers(Pageable pageable) {
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                teachers.getContent()));
    }

    public ResponseEntity<?> getTeachersSubjects(Pageable pageable, Integer teacherId) {
        boolean exists = teacherRepository.existsById(teacherId);
        if (!exists) throw new NotFoundSomethingException("Teacher not found");
        List<TeacherSubjectProjection> teacherSubjectProjection = teacherRepository.getTeachersSubjectWithGroups(pageable, teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                teacherSubjectProjection));
    }


    public ResponseEntity<?> getStudentListWithMark(Integer groupId, Integer subjectId) {
        boolean exists = groupRepository.existsById(groupId);
        if (!exists) throw new NotFoundSomethingException("This group not found");
        boolean exist = subjectRepository.existsById(subjectId);
        if (!exist) throw new NotFoundSomethingException("This subject not found");
        StudentMarkTableProjection markTableProjection = groupRepository.getMarkTableWithGroupIdAndSubjectId(groupId, subjectId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,
                markTableProjection));
    }

    public ResponseEntity<?> addTeacher(TeacherDto teacherDto) {
        boolean exists = teacherRepository.existsByUsername(teacherDto.getUsername());
        if (exists) throw new AlreadyExistCustomException("Teacher with this username already exist");
        Optional<Faculty> optionalFaculty = facultyRepository.findById(teacherDto.getFacultyId());
        Faculty faculty = optionalFaculty.orElseThrow(() -> new NotFoundSomethingException("Faculty not found"));
        Teacher save = teacherRepository.save(new Teacher(teacherDto.getName(), teacherDto.getUsername(), teacherDto.getPassword(), Role.ROLE_TEACHER,faculty));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, save.getName() + " saved successfully"));
    }

    public ResponseEntity<?> editTeacher(Integer studentId, TeacherDto studentDto) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(studentId);
        Teacher student = optionalTeacher.orElseThrow(() -> new NotFoundSomethingException("Teacher not found"));
        whenTeacherWantToEdit(student.getUsername(), studentDto.getUsername());
        student.setName(studentDto.getName());
        student.setUsername(studentDto.getUsername());
        student.setPassword(studentDto.getPassword());
        Teacher save = teacherRepository.save(student);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true,save.getName()+" edited"));
    }

    private void whenTeacherWantToEdit(String currentName, String newName) {
        if (currentName.equals(newName))
            return;
        boolean isExistsByName = teacherRepository.existsByUsername(newName);
        if (isExistsByName)
            throw new AlreadyExistCustomException("This username already exist, please find other one");
    }

    public ResponseEntity<?> deleteTeacher(Integer studentId) {
        boolean isExist = teacherRepository.existsById(studentId);
        if (!isExist)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Teacher not found ", false));
        try {
            teacherRepository.deleteById(studentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("deleted", true));
        } catch (DataIntegrityViolationException e) {
            throw new CannotDeleteException("Sorry but you can not delete this student!");
        }
    }
}