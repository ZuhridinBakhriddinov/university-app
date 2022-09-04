package uz.project.app_university.entity.faculty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.project.app_university.entity.university.University;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private University university;

    public Faculty(String name, University university) {
        this.name = name;
        this.university = university;
    }
}
