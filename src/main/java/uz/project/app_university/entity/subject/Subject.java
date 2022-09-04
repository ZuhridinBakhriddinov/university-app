package uz.project.app_university.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.group.Group;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subjects")
@Getter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "subjects_groups",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @ManyToOne
    private Faculty faculty;

    public Subject(String name,Faculty faculty) {
        this.name = name;
        this.faculty = faculty;
    }
    public Subject(String name, Faculty faculty, Set<Group> groups) {
        this.name = name;
        this.faculty = faculty;
        this.groups = groups;
    }
}
