package uz.project.app_university.entity.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UniversityDto {
    @NotNull(message = "name of new university did not fill")
    @NotBlank(message = "you must to fill this")
    @NotEmpty(message = "name of university cannot be empty")
    @Size(min = 3, max = 50, message = "name size must be between 2 and 50 characters ")
    private String name;


    @NotNull(message = "address did not fill")
    @NotEmpty(message = "address of university cannot be empty")
    @NotBlank(message = "you must to enter address")
    @Size(min = 5, max = 150, message = "Address size must be between 2 and 150 characters ")
    private String address;


    @NotNull(message = "Discovery year did not fill")
    @Min(value = 999, message = "Incorporated year wrong, length must be contains 4 numbers")
    @Max(value = 9999, message = "Incorporated year wrong, length must be contains 4 numbers")
    private Integer openYear;
}
