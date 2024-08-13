package com.example.UserDto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String name;

    private String phone;

    private String email;

    private int age;

    private Date createdOn;

    private Date updatedOn;
}
