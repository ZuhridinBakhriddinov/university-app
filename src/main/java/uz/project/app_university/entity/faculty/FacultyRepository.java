package uz.project.app_university.entity.faculty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    List<Faculty> findAllByUniversityId(Integer university_id);

    boolean existsByName(String name);

    @Query(nativeQuery = true, value = "select fac.id as facultyId,\n" +
            "       fac.name as facultyName\n" +
            "from faculties fac where fac.id=:faculty_id")
    FacultyWithGroupsProjection getGroupsWithNumberOfStudent(Integer faculty_id);
}
