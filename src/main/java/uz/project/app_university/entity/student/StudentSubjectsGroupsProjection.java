package uz.project.app_university.entity.student;

import org.springframework.beans.factory.annotation.Value;
import uz.project.app_university.entity.subject.SubjectProjection;

import java.util.List;

public interface StudentSubjectsGroupsProjection {
    Integer getStudentId();
    String getStudentName();
    String getGroupId();
    String getGroupName();
    @Value("#{@subjectRepository.findByGroupId(target.groupId)}")
    List<SubjectProjection> getSubjects();
}
