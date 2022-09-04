package uz.project.app_university.entity.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);

    @Query(nativeQuery = true, value = "select g.id as groupId, g.name as groupName,g.year as groupYear,\n" +
            "(select count(*) from students s where s.group_id = g.id) as NumberOfStudents\n" +
            " from groups g where g.faculty_id=:faculty_id")
    List<GroupProjection> getGroupsWithNumberOfStudents(Integer faculty_id);

    @Query(nativeQuery = true, value = "select count(*) from groups g\n" +
            "join subjects_groups sg on g.id = sg.group_id\n" +
            "join subjects s on s.id = sg.subject_id where g.id=:group_id")
    byte numberOfSubjectByGroupId(Integer group_id);

    @Query(nativeQuery = true, value = "select s.name as studentName,\n" +
            "       ceil(avg(m.value)) as mark\n" +
            "from students s\n" +
            "         join groups g on g.id = s.group_id\n" +
            "         join marks m on s.id = m.student_id\n" +
            "where g.id=:group_id\n" +
            "group by s.id ORDER BY mark desc")
    List<ListWithMarkProjection> getGradesTable(Integer group_id);


    @Query(nativeQuery = true, value = "select g.id                                                      as groupId,\n" +
            "       g.name                                                    as groupName,\n" +
            "       g.year                                                    as groupYear,\n" +
            "       (select count(*) from students s where s.group_id = g.id) as numberOfStudents\n" +
            "from groups g\n" +
            "join subjects_groups sg on g.id = sg.group_id\n" +
            "join subjects s on s.id = sg.subject_id\n" +
            "join teacher_groups tg on g.id = tg.group_id\n" +
            "join teachers t on t.id = tg.teacher_id\n" +
            "where s.id=:subject_id and tg.teacher_id=:teacher_id")
    List<GroupProjection> getGroupsWithNumberOfStudentsBySubject(Integer subject_id, Integer teacher_id);


    @Query(nativeQuery = true, value = "select g.id as groupId," +
                    "g.name as groupName, \n" +
                    "s.id as subjectId, \n" +
                    "s.name as subjectName \n" +
                    "from groups g \n" +
                    "join subjects_groups sg on g.id = sg.group_id\n" +
                    "join subjects s on s.id = sg.subject_id\n" +
                    "where s.id=:subject_id and g.id=:group_id")
    StudentMarkTableProjection getMarkTableWithGroupIdAndSubjectId(Integer group_id, Integer subject_id);


    @Query(nativeQuery = true, value = "select s.name  as studentName,\n" +
            "       ceil(avg(m.value)) as mark\n"+
            "from students s\n" +
            "         join groups g on g.id = s.group_id\n" +
            "         join marks m on s.id = m.student_id\n" +
            "         join subjects s2 on s2.id = m.subject_id\n" +
            "where g.id = :group_id and s2.id = :subject_id GROUP BY s.name ORDER BY mark desc")
    List<ListWithMarkProjection> getSubjectGradesTable(Integer group_id,Integer subject_id);


}
