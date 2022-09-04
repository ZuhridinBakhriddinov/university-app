package uz.project.app_university.entity.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "universities")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Integer openYear;

    public University(String name, String address, Integer openYear) {
        this.name = name;
        this.address = address;
        this.openYear = openYear;
    }
}
