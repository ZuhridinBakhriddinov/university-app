package uz.project.app_university.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@AllArgsConstructor
@Getter
public class SubjectDto {
    @NotNull(message = "name of new group did not fill")
    @NotBlank(message = "you must to fill this")
    @NotEmpty(message = "name of group cannot be empty")
    @Size(min = 3, max = 50, message = "name size must be between 2 and 50 characters ")
    private String name;

    @NotNull(message = "Start year for this group did not fill")
    private Integer facultyId;
}
