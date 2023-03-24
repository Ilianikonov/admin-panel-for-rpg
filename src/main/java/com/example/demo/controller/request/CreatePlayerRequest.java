package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;

import java.util.Date;

public class CreatePlayerRequest {
    String	name; // Имя персонажа (до 12 знаков включительно)
    String	title; // Титул персонажа (до 30 знаков включительно)
    Race race; //	Расса персонажа
    Profession profession; //	Профессия персонажа
    Date birthday; //	Д0ата регистрации Диапазон значений года 2000..3000 включительно

}
