package com.example.demo.dao.database;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public class SqlBuilder {
   private final String select;
   private final String from;
   private final List<String> where = new ArrayList<>();

    public SqlBuilder(String select, String from) {
        this.select = select;
        this.from = from;
    }
    public SqlBuilder where(String clause){
        where.add(clause);
        return this;
    }

    public String build(){
        String build = "select " + select + " from " + from;
        if (!where.isEmpty()){
            build += " where ";
            for (int x = 0; x < where.size() - 1; x++) {
                build += where.get(x) + " and ";
            }
            build += where.get(where.size() - 1);
        }
        return build += ";";
    }
}
