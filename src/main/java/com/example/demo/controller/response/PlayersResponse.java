package com.example.demo.controller.response;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class PlayersResponse {
    Long id; // ID игрока
    String	name; // Имя персонажа (до 12 знаков включительно)
    String	title; // Титул персонажа (до 30 знаков включительно)
    Race race; //	Расса персонажа
    Profession profession; //	Профессия персонажа
    Integer experience; //	Опыт персонажа. Диапазон значений 0..10,000,000
    Integer level; //	Уровень персонажа
    Integer untilNextLevel; //	Остаток опыта до следующего уровня
    Long birthday; //	Дата регистрации Диапазон значений года 2000..3000 включительно
    Boolean banned; //	Забанен / не забанен
}
