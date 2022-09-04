package uz.project.app_university.entity.student;

import lombok.*;
import uz.project.app_university.common.User;
import uz.project.app_university.entity.group.Group;
import uz.project.app_university.enums.Role;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "students")
public class Student extends User {
    @ManyToOne
    private Group group;

    public Student(String name, String username, String password, Role role, Group group) {
        super(name, username, password, role);
        this.group = group;
    }


    public Student(String name, String username, String password, Role role) {
        super(name, username, password, role);
    }
}
