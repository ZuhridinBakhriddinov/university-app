package uz.project.app_university.entity.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByName(String name);

    boolean existsByUsername(String username);

    Optional<Student> findByName(String name);
    @Query(nativeQuery = true, value = "select s.id   as studentId,\n" +
            "       s.name as studentName,\n" +
            "       g.id   as groupId,\n" +
            "       g.name as groupName\n" +
            "from students s\n" +
            "         join groups g on g.id = s.group_id where s.id=:studentId")
    StudentSubjectsGroupsProjection getSubjectsByStudentId(Integer studentId);
}
