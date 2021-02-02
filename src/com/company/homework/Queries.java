package com.company.homework;

public class Queries {

    private Queries() {
    }

    public static final String GET_NAME_BY_ID = "select name from %s where id = ?";
    public static final String GET_ID_BY_NAME = "select id from %s where name = ?";
    public static final String SELECT_ALL_BY_ID = "select * from %s where id = ?";
    public static final String SELECT_NAME_AGE_BY_TABLE = "select name, age from %s";


    //EX2
    public static final String EX_2_SELECT_VILLAINS =
            "select v.name, count(mv.minion_id) count\n" +
            "from villains v\n" +
            "join minions_villains mv on v.id = mv.villain_id\n" +
            "group by v.id\n" +
            "having count > 15\n" +
            "order by count desc";

    //EX3
    public static final String EX_3_SELECT_MINIONS =
            "select m.name, m.age from minions m\n" +
            "join minions_villains mv on m.id = mv.minion_id\n" +
            "where mv.villain_id = ?";

    //EX4
    public static final String INSERT_ENTITY_BY_TABLE_NAME = "insert into %s(name) value(?)";
    public static final String INSERT_MINION = "insert into minions(name, age, town_id) values (?, ?, ?)";
    public static final String INSERT_VILLAIN = "insert into villains(name, evilness_factor) values(?, 'evil')";
    public static final String
            INSERT_INTO_MINIONS_VILLAINS = "insert into minions_villains(minion_id, villain_id) values (?, ?)";

    //EX5
    public static final String EX_5_UPDATE_TOWNS = "update towns set name = upper(name) where country = ?";
    public static final String EX_5_SELECT_TOWNS = "select name from towns where country like ?";

    //EX6
    public static final String EX_6_DELETE_VILLAIN_FROM_MINIONS_VILLAINS =
            "delete from minions_villains where villain_id = ?";
    public static final String EX_6_DELETE_VILLAIN_BY_ID = "delete from villains where id = ?";

    //EX7
    public static final String EX_7_COUNT_MINIONS = "select count(id) from minions";

    //EX8
    public static final String EX_8_UPDATE_MINIONS =
            "update minions " +
            "set name = lower(name), " +
            "age = age + 1 " +
            "where id in (%s)";

    //EX9
    public static final String EX_9_CALL_PROCEDURE = "call usp_get_older(?)";
    public static final String EX_9_DROP_PROCEDURE = "drop procedure if exists usp_get_older";
    public static final String EX_9_CREATE_PROCEDURE =
            "create procedure usp_get_older(minion_id int) " +
                "begin " +
                    "update minions set age = age + 1 where id = minion_id; " +
                "end;";

}
