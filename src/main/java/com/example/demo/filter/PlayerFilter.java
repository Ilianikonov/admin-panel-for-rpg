package com.example.demo.filter;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Value;

@Value
public class PlayerFilter {
    String name;
    String title;
    Race race;
    Profession profession;
    Long after;
    Long before;
    Boolean banned;
    Integer minExperience;
    Integer maxExperience;
    Integer minLevel;
    Integer maxLevel;
    PlayerOrder playerOrder;
    Integer pageNumber;
    Integer pageSize;
}
