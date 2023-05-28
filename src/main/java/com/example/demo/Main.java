package com.example.demo;

import com.example.demo.dao.database.SqlBuilder;

public class Main {
    public static void main(String[] args) {
        String sql = new SqlBuilder("player ", "player join inner join race on player.race_id = race.id inner join profession on player.profession_id = profession.id")
                .where("player.level < 8")
                .where("race.name = 'HUMAN'")
                .build();
        System.out.println(sql);
    }
}

