package com.example.demo.filter;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PlayerFilter {
    String name;
    String title;
    Race race;
    Profession profession;
    Long after;
    Long before;
    Boolean banned = false;
    Integer minExperience = 0;
    Integer maxExperience = 10000000;
    Integer minLevel = 0;
    Integer maxLevel;
}
