package uz.project.app_university.entity.university;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Integer> {

    boolean existsByName(String name);
}
