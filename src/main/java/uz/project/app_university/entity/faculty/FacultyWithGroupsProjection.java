package uz.project.app_university.entity.faculty;

import org.springframework.beans.factory.annotation.Value;
import uz.project.app_university.entity.group.GroupProjection;

import java.util.List;

public interface FacultyWithGroupsProjection {
    Integer getFacultyId();
    String getFacultyName();
    @Value("#{@groupRepository.getGroupsWithNumberOfStudents(target.facultyId)}")
    List<GroupProjection> getGroupsStudents();
}
