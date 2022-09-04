package uz.project.app_university.entity.faculty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
public class FacultyDto {
    @NotNull(message = "name of new university did not fill")
    @NotBlank(message = "you must to fill this")
    @NotEmpty(message = "name of university cannot be empty")
    @Size(min = 3, max = 100, message = "name size must be between 2 and 100 characters ")
    private String name;

    @NotNull(message = "Discovery year did not fill")
    @Min(value = 0, message = "Incorporated year wrong, length must be contains 4 numbers")
    @Max(value = Integer.MAX_VALUE, message = "Incorporated year wrong, length must be contains 4 numbers")
    private Integer universityId;
}
