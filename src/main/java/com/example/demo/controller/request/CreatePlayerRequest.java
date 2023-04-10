package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;
import javax.validation.constraints.*;

@Data
public class CreatePlayerRequest {
    @Size(max = 12)
    @NotBlank
    String	name; // Имя персонажа (до 12 знаков включительно)
    @Size(max = 30)
    @NotBlank
    String	title; // Титул персонажа (до 30 знаков включительно)
    @NotNull
    Race race; //	Расса персонажа
    @NotNull
    Profession profession; //	Профессия персонажа
    @Min(946684800000L)
    @Max(32503680000000L)
    @NotNull
    Long birthday; //	Д0ата регистрации Диапазон значений года 2000..3000 включительно
    Boolean banned = false;
    Integer experience = 0;
}
