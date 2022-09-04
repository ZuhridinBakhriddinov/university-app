package uz.project.app_university.entity.teacher;

import lombok.NoArgsConstructor;
import uz.project.app_university.common.User;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.group.Group;
import uz.project.app_university.entity.subject.Subject;
import uz.project.app_university.enums.Role;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Entity(name = "teachers")
public class Teacher extends User {
    @ManyToMany
    @JoinTable(
            name = "teacher_groups",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    Set<Group> groups;

    @ManyToMany
    @JoinTable(
            name = "teacher_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    Set<Subject> subjects;

    @ManyToOne
    private Faculty faculty;

    public Teacher(String name, String username, String password, Role role, Faculty faculty) {
        super(name, username, password, role);
        this.faculty = faculty;
    }

    public Teacher(String name, String username, String password, Role role, Faculty faculty, Set<Subject> subjects, Set<Group> groups) {
        super(name, username, password, role);
        this.faculty = faculty;
        this.groups = groups;
        this.subjects = subjects;
    }
}
