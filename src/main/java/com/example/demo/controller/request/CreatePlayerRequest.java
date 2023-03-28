package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
public class CreatePlayerRequest {
    String	name; // Имя персонажа (до 12 знаков включительно)
    String	title; // Титул персонажа (до 30 знаков включительно)
    Race race; //	Расса персонажа
    Profession profession; //	Профессия персонажа
    Date birthday; //	Д0ата регистрации Диапазон значений года 2000..3000 включительно

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

    public void setBirthday(Date birthday) {
        Date dataMin = new Date(1,0,2000);
        Date dataMax = new Date(30,11,3000);
        if (dataMin.getTime() < birthday.getTime() && birthday.getTime() < dataMax.getTime()) {
        this.birthday = birthday;
        } else {
            new  RuntimeException ("превышает количество символов в title");
        }
    }
}
