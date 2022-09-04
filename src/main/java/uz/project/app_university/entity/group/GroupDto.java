package uz.project.app_university.entity.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
public class GroupDto {
    @NotNull(message = "name of new group did not fill")
    @NotBlank(message = "you must to fill this")
    @NotEmpty(message = "name of group cannot be empty")
    @Size(min = 3, max = 50, message = "name size must be between 2 and 50 characters ")
    private String name;


    @NotNull(message = "Start year for this group did not fill")
    private Integer facultyId;


    @NotNull(message = "Start year for this group did not fill")
    @Min(value = 999, message = "Start year wrong, length must be contains 4 numbers")
    @Max(value = 9999, message = "Start year wrong, length must be contains 4 numbers")
    private Integer year;
}
