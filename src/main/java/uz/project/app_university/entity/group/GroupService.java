package uz.project.app_university.entity.group;

import lombok.AllArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.faculty.FacultyRepository;
import uz.project.app_university.entity.student.Student;
import uz.project.app_university.entity.student.StudentRepository;
import uz.project.app_university.exception.AlreadyExistCustomException;
import uz.project.app_university.exception.CannotDeleteException;
import uz.project.app_university.exception.NotFoundSomethingException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@PackagePrivate
public class GroupService {
    final GroupRepository groupRepository;
    final FacultyRepository facultyRepository;
    final StudentRepository studentRepository;

    public ResponseEntity<?> addNewGroup(GroupDto groupDto) {
        boolean exists = groupRepository.existsByName(groupDto.getName());
        if (exists) throw new AlreadyExistCustomException("Group with this name already exist.");
        boolean exists1 = facultyRepository.existsById(groupDto.getFacultyId());
        if (!exists1) throw new NotFoundSomethingException("Faculty not found");
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        Faculty faculty = optionalFaculty.orElseThrow(() -> new NotFoundSomethingException("Faculty not found"));
        Group group = groupRepository.save(new Group(groupDto.getName(), faculty, groupDto.getYear()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true,
                        group.getName() + " successfully saved"));
    }


    public ResponseEntity<?> getAllGroupsByFacultyId(Integer facultyId, int page, int size, String sort, Boolean direction) {
        Sort.Direction direction1 = direction ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction1, sort));
        Page<Group> groupPage = groupRepository.findAll(pageable);
        List<Group> groupList = groupRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true, groupPage));
    }

    public ResponseEntity<?> editGroupByGroupId(Integer groupId, GroupDto groupDto) {
        boolean exists = groupRepository.existsById(groupId);
        if (!exists) throw new NotFoundSomethingException("Group not found");
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.orElseThrow(() -> new NotFoundSomethingException("we cannot find the group"));
        whenGroupNameChanging(group.getName(), groupDto.getName());
        group.setName(groupDto.getName());
        group.setYear(groupDto.getYear());
        Group savedGroup = groupRepository.save(group);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true, savedGroup.getName() + " successfully update"));

    }

    private void whenGroupNameChanging(String currentName, String newName) {
        if (currentName.equals(newName))
            return;
        boolean isExistsByName = groupRepository.existsByName(newName);
        if (isExistsByName)
            throw new AlreadyExistCustomException("Group with this name already exist");
    }

    public ResponseEntity<?> getGradesTable(Integer groupId) {
        List<ListWithMarkProjection> gradesTable = groupRepository.getGradesTable(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true, gradesTable));

    }

    public ResponseEntity<?> deleteGroup(Integer groupId) {
        boolean exists = groupRepository.existsById(groupId);
        if (!exists) throw new NotFoundSomethingException("Group not found");
        try {
            groupRepository.deleteById(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("delete", true));
        } catch (DataIntegrityViolationException e) {
            throw new CannotDeleteException("Sorry but you can not delete this group! May be it has some students");
        }

    }

    public ResponseEntity<?> addStudentToTheGroup(Integer groupId, Integer studentId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.orElseThrow(() -> new NotFoundSomethingException("This group not found"));
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseThrow(() -> new NotFoundSomethingException("This Student not found"));
        student.setGroup(group);
        Student save = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true, save.getName() + " successfully joined new group"));
    }
}
