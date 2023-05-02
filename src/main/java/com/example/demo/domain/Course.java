package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "COURSES")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    @Size(min = -90, max =90, message="Latitude must be between -90 and 90 inclusive. ")
    private float latitude;

    @NotNull
    @Column
   @Size(min = -180, max = 180, message="Longitude must be between -180 and 180 inclusive. ")
    private float longitude;


    @Column
    @NotBlank
    @Size(min=68, max=74, message = "Course par rating should be between 68 and 74")
    private int par;

    @Column
    @NotNull
    private int yardage;

    @Column
    @Size(min=1, max=5, message="Rating must be between 1 and 5 inclusive." )
    private float rating;

}
