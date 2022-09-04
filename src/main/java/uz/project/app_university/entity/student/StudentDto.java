package uz.project.app_university.entity.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentDto {
    @NotNull(message = "Student name did not fill")
    @NotBlank(message = "You must to fill this")
    @NotEmpty(message = "Student name cannot be empty")
    @Size(min = 3, max = 100, message = "name size must be between 2 and 10 characters ")
    private String name;

    @NotNull(message = "username did not fill")
    @NotBlank(message = "You must to fill this")
    @NotEmpty(message = "Student name cannot be empty")
    @Size(min = 6, max = 100, message = "minimum length of username is 6 ")
    private String username;

    @NotNull(message = "Password did not fill")
    @NotBlank(message = "You must to fill this")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "minimum length of username is 6 ")
    private String password;

}
