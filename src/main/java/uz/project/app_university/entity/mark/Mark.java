package uz.project.app_university.entity.mark;

import lombok.NoArgsConstructor;
import uz.project.app_university.entity.student.Student;
import uz.project.app_university.entity.subject.Subject;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Byte value;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Student student;

    public Mark(Byte value, Subject subject, Student student) {
        this.value = value;
        this.subject = subject;
        this.student = student;
    }
}
