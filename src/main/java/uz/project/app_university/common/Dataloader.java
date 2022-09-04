package uz.project.app_university.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.project.app_university.entity.teacher.Teacher;
import uz.project.app_university.entity.teacher.TeacherRepository;
import uz.project.app_university.entity.faculty.Faculty;
import uz.project.app_university.entity.faculty.FacultyRepository;
import uz.project.app_university.entity.group.Group;
import uz.project.app_university.entity.group.GroupRepository;
import uz.project.app_university.entity.mark.Mark;
import uz.project.app_university.entity.mark.MarkRepository;
import uz.project.app_university.entity.student.Student;
import uz.project.app_university.entity.student.StudentRepository;
import uz.project.app_university.entity.subject.Subject;
import uz.project.app_university.entity.subject.SubjectRepository;
import uz.project.app_university.entity.university.University;
import uz.project.app_university.entity.university.UniversityRepository;
import uz.project.app_university.enums.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class Dataloader implements CommandLineRunner {

    private final UniversityRepository universityRepository;
    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    private final TeacherRepository teacherRepository;

    private final MarkRepository markRepository;
    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            /**
             * University adding
             */
            List<University> universities = List.of(
                    new University("Tashkent University", "Tashkent city", 1891),
                    new University("Samarkand University", "Samarkand city", 1882),
                    new University("Bukhara University", "Bukhara States", 1852)
            );
            List<University> universityList = universityRepository.saveAll(universities);

            /**
             * Faculty adding (3 faculties belongs to first university)
             */
            List<Faculty> faculties = List.of(
                    new Faculty("Mathematics", universityList.get(0)),
                    new Faculty("Physical Sciences", universityList.get(0)),
                    new Faculty("Computer Science Department", universityList.get(0))
            );
            List<Faculty> facultyList = facultyRepository.saveAll(faculties);

            /**
             * Group adding (6 groups, each faculty has 2 groups)
             */
            List<Group> groups = List.of(
                    new Group("BH1", facultyList.get(0), 2018),
                    new Group("BH2", facultyList.get(0), 2018),
                    new Group("BH3", facultyList.get(1), 2019),
                    new Group("BH4", facultyList.get(1), 2019),
                    new Group("BH5", facultyList.get(2), 2020),
                    new Group("BH6", facultyList.get(2), 2020)
            );
            List<Group> groupList = groupRepository.saveAll(groups);

            /**
             * Student adding (18 student, each faculty has 3 students)
             */
            List<Student> students = List.of(
                    new Student("Tom11", "user11", "password", Role.ROLE_STUDENT, groupList.get(0)),
                    new Student("Tom12", "user12", "password", Role.ROLE_STUDENT, groupList.get(0)),
                    new Student("Tom13", "user13", "password", Role.ROLE_STUDENT, groupList.get(0)),

                    new Student("Tom21", "user21", "password", Role.ROLE_STUDENT, groupList.get(1)),
                    new Student("Tom22", "user22", "password", Role.ROLE_STUDENT, groupList.get(1)),
                    new Student("Tom23", "user23", "password", Role.ROLE_STUDENT, groupList.get(1)),

                    new Student("Tom31", "user31", "password", Role.ROLE_STUDENT, groupList.get(2)),
                    new Student("Tom32", "user32", "password", Role.ROLE_STUDENT, groupList.get(2)),
                    new Student("Tom33", "user33", "password", Role.ROLE_STUDENT, groupList.get(2)),

                    new Student("Tom41", "user41", "password", Role.ROLE_STUDENT, groupList.get(3)),
                    new Student("Tom42", "user42", "password", Role.ROLE_STUDENT, groupList.get(3)),
                    new Student("Tom43", "user43", "password", Role.ROLE_STUDENT, groupList.get(3)),

                    new Student("Tom51", "user51", "password", Role.ROLE_STUDENT, groupList.get(4)),
                    new Student("Tom52", "user52", "password", Role.ROLE_STUDENT, groupList.get(4)),
                    new Student("Tom53", "user53", "password", Role.ROLE_STUDENT, groupList.get(4)),

                    new Student("Tom61", "user61", "password", Role.ROLE_STUDENT, groupList.get(5)),
                    new Student("Tom62", "user62", "password", Role.ROLE_STUDENT, groupList.get(5)),
                    new Student("Tom63", "user63", "password", Role.ROLE_STUDENT, groupList.get(5))
            );

            List<Student> studentList = studentRepository.saveAll(students);

            /**
             *  All groups for Math subject
             */
            Set<Group> groupsM = new HashSet<>(groupList);

            /**
             * 4 groups (from 1 to 4) for Physics subject
             */
            Set<Group> groupsP = groupList.stream()
                    .limit(4)
                    .collect(Collectors.toSet());

            /**
             * Only last 2 groups for Java
             */
            Set<Group> groupsJ = groupList.stream()
                    .skip(4)
                    .collect(Collectors.toSet());

            List<Subject> subjects = List.of(
                    new Subject("Math", facultyList.get(0), groupsM),
                    new Subject("Physics", facultyList.get(1), groupsP),
                    new Subject("Java Programming Languages", facultyList.get(2), groupsJ),
                    new Subject("Foreign (English) Languages", facultyList.get(2), groupsM)
            );
            List<Subject> subjectList = subjectRepository.saveAll(subjects);

            /**
             * Grading students
             */
            List<Mark> marksM = studentList.
                    stream()
                    .map((student) -> markRepository.save(new Mark((byte) randomGrade(), subjects.get(0), student)))
                    .toList();
            markRepository.saveAll(marksM);

            List<Mark> marksP = studentList.stream()
                    .filter((student -> student.getGroup().getFaculty().getId() != 3))
                    .map((student) -> markRepository.save(new Mark((byte) randomGrade(), subjects.get(1), student)))
                    .toList();

            markRepository.saveAll(marksP);

            List<Mark> marksJ = studentList.stream()
                    .filter((student -> student.getGroup().getFaculty().getId() == 3))
                    .map((student) -> markRepository.save(new Mark((byte) randomGrade(), subjects.get(2), student)))
                    .toList();

            markRepository.saveAll(marksJ);

            List<Mark> marksE = studentList.
                    stream()
                    .map((student) -> markRepository.save(new Mark((byte) randomGrade(), subjects.get(3), student)))
                    .toList();
            markRepository.saveAll(marksE);

            /**
             * Adding teacher
             */

            Set<Subject> subjectsM = subjectList.stream()
                    .filter((subject -> subject.getId() == 1))
                    .collect(Collectors.toSet());

            Set<Subject> subjectsP = subjectList.stream()
                    .filter((subject -> subject.getId() == 2))
                    .collect(Collectors.toSet());

            Set<Subject> subjectsJ = subjectList.stream()
                    .filter((subject -> subject.getId() == 3))
                    .collect(Collectors.toSet());

            Set<Subject> subjectsE = subjectList.stream()
                    .filter((subject -> subject.getId() == 4))
                    .collect(Collectors.toSet());

            Set<Group> groupsJ1 = groupList.stream()
                    .skip(4)
                    .limit(1)
                    .collect(Collectors.toSet());

            Set<Group> groupsJ2 = groupList.stream()
                    .skip(5)
                    .limit(1)
                    .collect(Collectors.toSet());


            List<Teacher> teachers = List.of(
                    new Teacher("Devlin Brand", "@devlin", "123456", Role.ROLE_TEACHER, facultyList.get(0), subjectsM, groupsM),
                    new Teacher("Albert Einstein", "@albert", "123456", Role.ROLE_TEACHER, facultyList.get(1), subjectsP, groupsP),
                    new Teacher("Mosh", "@coddingWithMosh", "123456", Role.ROLE_TEACHER, facultyList.get(2), subjectsJ, groupsJ1),
                    new Teacher("Amigos", "@amigosCode", "123456", Role.ROLE_TEACHER, facultyList.get(2), subjectsJ, groupsJ2),
                    new Teacher("Angela", "@englishWithAngela", "123456", Role.ROLE_TEACHER, facultyList.get(2), subjectsE, groupsM)
            );
            List<Teacher> teacherList = teacherRepository.saveAll(teachers);

        }
    }
    private int randomGrade() {
        return (byte) (Math.random() * ((100 - 55) + 1)) + 55;
    }
}
