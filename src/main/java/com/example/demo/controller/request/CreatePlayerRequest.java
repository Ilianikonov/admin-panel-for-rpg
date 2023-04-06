package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

@Data
public class CreatePlayerRequest {
    String	name; // Имя персонажа (до 12 знаков включительно)
    String	title; // Титул персонажа (до 30 знаков включительно)
    Race race; //	Расса персонажа
    Profession profession; //	Профессия персонажа
    Long birthday; //	Д0ата регистрации Диапазон значений года 2000..3000 включительно
    Boolean banned;
    Integer experience;
    public void setName(String name) {
        if (name.length() < 12) {
            this.name = name;
        } else {
           new  RuntimeException ("превышает количество символов в name");
        }
    }

    public void setTitle(String title) {
        if (title.length() < 30) {
        this.title = title;
        } else {
            new  RuntimeException ("превышает количество символов в title");
        }
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }
}
