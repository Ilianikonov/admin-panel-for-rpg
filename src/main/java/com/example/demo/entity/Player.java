package com.example.demo.entity;

import lombok.Data;


import java.util.Date;
@Data
public class Player {
    Long id; // ID игрока
    String	name; // Имя персонажа (до 12 знаков включительно)
    String	title; // Титул персонажа (до 30 знаков включительно)
    Race race; //	Расса персонажа
    Profession profession; //	Профессия персонажа
    Integer experience = 0; //	Опыт персонажа. Диапазон значений 0..10,000,000
    Integer level = 0; //	Уровень персонажа
    Integer untilNextLevel = 100; //	Остаток опыта до следующего уровня
    Date birthday; //	Дата регистрации Диапазон значений года 2000..3000 включительно
    Boolean banned = false; //	Забанен / не забанен
}
