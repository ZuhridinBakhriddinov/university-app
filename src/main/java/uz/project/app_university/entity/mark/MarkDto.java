package uz.project.app_university.entity.mark;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@AllArgsConstructor
@Getter
public class MarkDto {
    @NotNull(message = "grade not shown")
    private Byte mark;

    @NotNull(message = "student not shown")
    private Integer studentId;

    @NotNull(message = "subject not shown")
    private Integer subjectId;

    @NotNull(message = "subject not shown")
    private Integer teacherId;

}
