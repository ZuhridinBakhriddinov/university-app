package uz.project.app_university.entity.teacher;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByUsername(String username);

    @Query(nativeQuery = true, value = "select s.id as subjectId,\n" +
            "       s.name as subjectName,\n" +
            "       t.id as teacherId\n" +
            "from teachers t\n" +
            "join teacher_subjects ts on t.id = ts.teacher_id\n" +
            "join subjects s on s.id = ts.subject_id where t.id=:teacher_id")
    List<TeacherSubjectProjection> getTeachersSubjectWithGroups(Pageable pageable, Integer teacher_id);
}
