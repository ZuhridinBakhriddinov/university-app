package uz.project.app_university.entity.university;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.app_university.common.ApiResponse;
import uz.project.app_university.exception.AlreadyExistCustomException;
import uz.project.app_university.exception.CannotDeleteException;

import javax.validation.Valid;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    public ResponseEntity<ApiResponse> getAllUniversities(Pageable pageable) {
        try {
            Page<University> universities = universityRepository.findAll(pageable);
            return ResponseEntity.ok(new ApiResponse("success", true, universities.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("products not found", false));
        }
    }

    public ResponseEntity<?> saveNewUniversity(@Valid UniversityDto university) {
        try {
            University save = universityRepository.save(new University(university.getName(), university.getAddress(), university.getOpenYear()));
            return ResponseEntity.ok(new ApiResponse("success", true, save.getName() + " saved"));

        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistCustomException("University with this name already exist");
        }
    }

    public ResponseEntity<?> editeUniversity(Integer universityId, UniversityDto universityDto) {
        boolean isExistsById = universityRepository.existsById(universityId);
        if (isExistsById) {
            Optional<University> universityOptional = universityRepository.findById(universityId);
            if (universityOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ApiResponse("No University found with this id", false,
                                "university you have selected is not found"));
            University university = universityOptional.get();
            whenUniversityNameChanging(university.getName(), universityDto.getName());
            university.setName(universityDto.getName());
            university.setAddress(universityDto.getAddress());
            university.setOpenYear(universityDto.getOpenYear());
            universityRepository.save(university);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("success", true, "successfully update"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No University found with this id",
                false, "university you have selected is not found"));
    }

    private void whenUniversityNameChanging(String currentName, String newName) {
        if (currentName.equals(newName))
            return;
        boolean isExistsByName = universityRepository.existsByName(newName);
        if (isExistsByName)
            throw new AlreadyExistCustomException("University with this name already exist");
    }

    public ResponseEntity<?> getUniversityById(Integer universityId) {
        boolean exists = universityRepository.existsById(universityId);
        if (exists) {
            Optional<University> universityOptional = universityRepository.findById(universityId);
            if (universityOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ApiResponse("No University found with this id", false,
                                "university you have selected is not found"));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("success", true, universityOptional.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No University found with this id",
                false, "university you have selected is not found"));

    }

    public ResponseEntity<?> deleteUniversity(Integer universityId) {
        boolean isExist = universityRepository.existsById(universityId);
        if (!isExist)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No University found with this id",
                    false, "university you have selected is not found"));

        try {
            universityRepository.deleteById(universityId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("deleted", true));
        } catch (DataIntegrityViolationException e) {
            throw new CannotDeleteException("Sorry but you can not delete this university! Therefore it has some faculties");
        }
    }
}
