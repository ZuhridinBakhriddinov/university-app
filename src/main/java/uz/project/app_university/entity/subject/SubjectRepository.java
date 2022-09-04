package uz.project.app_university.entity.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    boolean existsByName(String name);

    List<Subject> findByFacultyId(Integer faculty_id);

    @Query(nativeQuery = true, value = "select s.id as subjectId,s.name as subjectName from subjects s\n" +
            "join subjects_groups sg on s.id = sg.subject_id\n" +
            "join groups g on g.id = sg.group_id where g.id=:group_id")
    List<SubjectProjection> findByGroupId(Integer group_id);


    @Query(nativeQuery = true, value = "select s.id   as id,\n" +
            "       s.name as name\n" +
            "from subjects s\n" +
            "         join subjects_groups sg on s.id = sg.subject_id\n" +
            "         join groups g on g.id = sg.group_id\n" +
            "         join students st on g.id = st.group_id\n" +
            "where st.id = :student_id")
    List<StudentSubjectProjection> getSubjectsStudentId(Integer student_id);

    @Query(nativeQuery = true, value = "select s.id   as id,\n" +
            "       s.name as name,\n" +
            "       ceil(avg(m.value)) as mark\n" +
            "from subjects s\n" +
            "         join marks m on s.id = m.subject_id\n" +
            "where m.student_id=:student_id and m.subject_id=:subject_id GROUP BY s.id, s.name;")
    StudentSubjectProjection getSubjectWithMarks(Integer student_id, Integer subject_id);
}
