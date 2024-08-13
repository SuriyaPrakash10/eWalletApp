package com.example.UserDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private String email;

    @Min(18)
    private int age;
}