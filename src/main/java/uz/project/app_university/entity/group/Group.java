package uz.project.app_university.entity.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.subject.Subject;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Faculty faculty;
    @Column(nullable = false)
    private Integer year;

    @ManyToMany(mappedBy = "groups")
    Set<Subject> subjects;

    public Group(String name, Faculty faculty, Integer year) {
        this.name = name;
        this.faculty = faculty;
        this.year = year;
    }
}
