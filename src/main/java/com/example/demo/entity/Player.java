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
    Integer level = ((int) Math.sqrt(200 * experience + 2500) - 50)/100; //	Уровень персонажа
    Integer untilNextLevel = 50 * (level + 1) * (level + 2) - experience; //	Остаток опыта до следующего уровня
    Date birthday; //	Дата регистрации Диапазон значений года 2000..3000 включительно
    Boolean banned = false; //	Забанен / не забанен
}
