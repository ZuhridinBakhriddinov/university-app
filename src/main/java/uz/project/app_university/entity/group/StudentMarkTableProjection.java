package uz.project.app_university.entity.group;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface StudentMarkTableProjection {
    Integer getGroupId();
    String getGroupName();
    Integer getSubjectId();
    String getSubjectName();
    @Value("#{@groupRepository.getSubjectGradesTable(target.groupId,target.subjectId)}")
    List<ListWithMarkProjection> getMarks();
}
