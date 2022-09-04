package uz.project.app_university.entity.teacher;

import org.springframework.beans.factory.annotation.Value;
import uz.project.app_university.entity.group.GroupProjection;

import java.util.List;

public interface TeacherSubjectProjection {
    Integer getSubjectId();

    String getSubjectName();

//    Integer getTeacherId();

    @Value("#{@groupRepository.getGroupsWithNumberOfStudentsBySubject(target.subjectId,target.teacherId)}")
    List<GroupProjection> getSubjectOfGroups();


}
