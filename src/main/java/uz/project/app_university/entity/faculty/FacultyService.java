package uz.project.app_university.entity.faculty;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.entity.group.GroupRepository;
import uz.project.app_university.entity.university.University;
import uz.project.app_university.entity.university.UniversityRepository;
import uz.project.app_university.exception.AlreadyExistCustomException;
import uz.project.app_university.exception.CannotDeleteException;
import uz.project.app_university.exception.NotFoundSomethingException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;

    private final GroupRepository groupRepository;

    public ResponseEntity<?> getAllFacultiesByUniversityId(Integer universityId) {
        List<Faculty> facultiesOfUniversity = facultyRepository.findAllByUniversityId(universityId);
        if (facultiesOfUniversity.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse("not found", false, "Faculties of this University not found"));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true, facultiesOfUniversity));
    }

    public ResponseEntity<?> addNewFaculty(Integer universityId, FacultyDto faculty) {
        boolean isExists = universityRepository.existsById(universityId);
        if (!isExists) throw new NotFoundSomethingException("University not found");
        boolean isExistsName = facultyRepository.existsByName(faculty.getName());
        if (isExistsName) throw new AlreadyExistCustomException("Faculty with this name already exist.");
        Optional<University> universityOptional = universityRepository.findById(universityId);
        University university = universityOptional.orElseThrow(() -> new NotFoundSomethingException("University not found"));
        Faculty facultySaved = facultyRepository.save(new Faculty(faculty.getName(), university));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true,
                        facultySaved.getName() + " successfully saved"));
    }

    public ResponseEntity<?> editFacultyById(Integer facultyId, FacultyDto faculty) {
        boolean isExists = facultyRepository.existsById(facultyId);
        if (!isExists) throw new NotFoundSomethingException("Faculty not found");
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);
        Faculty currentFaculty = optionalFaculty.orElseThrow(() -> new NotFoundSomethingException("Faculty not found"));
        whenFacultyNameChanging(currentFaculty.getName(), faculty.getName());
        currentFaculty.setName(faculty.getName());
        Faculty saveFaculty = facultyRepository.save(currentFaculty);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", true,
                        saveFaculty.getName() + " successfully edited"));
    }

    private void whenFacultyNameChanging(String currentName, String newName) {
        if (currentName.equals(newName))
            return;
        boolean isExistsByName = facultyRepository.existsByName(newName);
        if (isExistsByName)
            throw new AlreadyExistCustomException("Faculty with this name already exist");
    }

    public ResponseEntity<?> getGroupsWithNumberOfStudent(Integer facultyId) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);
        Faculty faculty = optionalFaculty.orElseThrow(() -> new NotFoundSomethingException("Faculty not found"));
        FacultyWithGroupsProjection facultyWithGroupsProjection = facultyRepository.getGroupsWithNumberOfStudent(faculty.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", true, facultyWithGroupsProjection));
    }

    public ResponseEntity<?> deleteFaculty(Integer facultyId) {
        boolean isExists = facultyRepository.existsById(facultyId);
        if (!isExists) throw new NotFoundSomethingException("Faculty not found");
        try {
            facultyRepository.deleteById(facultyId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("delete", true));
        } catch (DataIntegrityViolationException e) {
            throw new CannotDeleteException("Sorry but you can not delete this faculty! Therefore it has some groups or subjects");
        }
    }
}
