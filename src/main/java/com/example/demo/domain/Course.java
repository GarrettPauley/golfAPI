package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @DecimalMin(value="-90.0", message= "latitude must be greater than -90.0 and less than 90.0")
    @DecimalMax(value="90.0", message= "latitude must be greater than -90.0 and less than 90.0")
    private float latitude;

    @NotNull
    @Column
    @DecimalMin(value="-180.0", message= "latitude must be greater than -180.0 and less than 180.0")
    @DecimalMax(value="180.0", message= "latitude must be greater than -180.0 and less than 180.0")
    private float longitude;


    @Column
    @NotNull
    @Min(value=68)
    @Max(value=72)
    private int par;

    @Column
    @NotNull
    private int yardage;

    @Column
    @DecimalMin(value="0.0", message= "rating must be between 0 and 5")
    @DecimalMax(value="5.0", message= "rating must be between 0 and 5")
    private float rating;

}
