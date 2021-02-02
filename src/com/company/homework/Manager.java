package com.company.homework;

import java.sql.*;
import java.util.ArrayList;

public class Manager {

    private final Connection connection;
    private final QueryMethods queryMethods;

    private PreparedStatement statement;
    private ResultSet resultSet;

    public Manager(Connection connection) {
        this.connection = connection;
        this.queryMethods = new QueryMethods(connection);
    }

    public void getVillainsNamesEx2() throws SQLException {

        this.statement = this.connection.prepareStatement(Queries.EX_2_SELECT_VILLAINS);
        this.resultSet = this.statement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d%n",
                    resultSet.getString("name"),
                    resultSet.getInt("count"));
        }
    }

    public void getMinionNamesEx3(int villainId) throws SQLException {

        this.statement = this.connection.prepareStatement(Queries.EX_3_SELECT_MINIONS);
        this.statement.setInt(1, villainId);
        this.resultSet = this.statement.executeQuery();


        String villainName = this.queryMethods.getEntityNameById(villainId, "villains");

        if(villainName == null){
            System.out.printf("No villain with id %d%n", villainId);
            return;
        }
        int counter = 0;
        System.out.printf("Villain name: %s%n", villainName);
        while (this.resultSet.next()){
            System.out.printf("%d. %s %d%n", ++counter,
                    this.resultSet.getString("name"),
                    this.resultSet.getInt("age"));
        }
    }

    public void addMinionEx4(String minionName, int age, String townName, String villainName) throws SQLException {
        String townsTable = "towns";
        String villainsTable = "villains";
        String minionsTable = "minions";
        int townId = this.queryMethods.getEntityIdByName(townName, townsTable);
        int villainId = this.queryMethods.getEntityIdByName(villainName, villainsTable);
        int minionId = this.queryMethods.getEntityIdByName(minionName, minionsTable);
        if(townId < 0){
            this.queryMethods.insertEntityInTableByName(townName, townsTable);
            System.out.printf("Town %s was added to the database.%n", townName);
            townId = this.queryMethods.getEntityIdByName(townName, townsTable);
        }
        if(villainId < 0){
            this.queryMethods.insertVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
            villainId = this.queryMethods.getEntityIdByName(villainName, villainsTable);
        }

        if(minionId < 0){
            this.queryMethods.insertMinion(minionName, age, townId);
            minionId = this.queryMethods.getEntityIdByName(minionName, minionsTable);
        }
        this.queryMethods.insertIntoMinionsVillains(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
    }

    public void changeTownNameCasingEx5(String countryName) throws SQLException {
        ArrayList<String> towns = new ArrayList<>();

        this.statement = this.connection.prepareStatement(Queries.EX_5_UPDATE_TOWNS);
        this.statement.setString(1, countryName);
        int townsAffected = this.statement.executeUpdate();
        if(townsAffected == 0){
            System.out.println("No town names were affected");
            return;
        }
        System.out.printf("%d town names were affected.%n", townsAffected);

        this.statement = this.connection.prepareStatement(Queries.EX_5_SELECT_TOWNS);
        this.statement.setString(1, countryName);
        ResultSet set = this.statement.executeQuery();
        while (set.next()){
            towns.add(set.getString("name"));
        }
        System.out.println("[" + String.join(", ",towns) + "]");
    }

    public void removeVillainEx6(int villainId) throws SQLException {
        String tableName = "villains";
        String villainName = this.queryMethods.getEntityNameById(villainId, tableName);
        if(villainName == null){
            System.out.printf("There is no villain with id %d%n", villainId);
            return;
        }
        this.statement = this.connection.prepareStatement(
                Queries.EX_6_DELETE_VILLAIN_FROM_MINIONS_VILLAINS);
        this.statement.setInt(1, villainId);
        int affectedMinions = this.statement.executeUpdate();
        this.statement = this.connection.prepareStatement(
                Queries.EX_6_DELETE_VILLAIN_BY_ID);
        this.statement.setInt(1, villainId);

        System.out.printf("%s was deleted %d minions released%n", villainName, affectedMinions);
    }

    public void printAllMinionNamesEx07() throws SQLException {
        ArrayList<String> arr = new ArrayList<>();

        this.statement = this.connection.prepareStatement
                (Queries.EX_7_COUNT_MINIONS);
        this.resultSet = this.statement.executeQuery();
        int countOfMinions = 0;
        while (this.resultSet.next()) countOfMinions = resultSet.getInt(1);

        for (int i = 1; i <= countOfMinions / 2; i++) {
            arr.add(this.queryMethods.getEntityNameById(i, "minions"));
            arr.add(this.queryMethods.getEntityNameById(countOfMinions - i + 1, "minions"));
        }
        System.out.println(String.join("\n", arr));
    }



    public void increaseMinionsAgeEx8(String[] ids) throws SQLException {
        String tableName = "minions";

        this.statement = this.connection.prepareStatement(String.format(Queries.EX_8_UPDATE_MINIONS,
                String.join(", ", ids)));
        this.statement.execute();
        this.statement = this.connection.prepareStatement(String.format(Queries.SELECT_NAME_AGE_BY_TABLE, tableName));

        this.resultSet = this.statement.executeQuery();
        while (resultSet.next()){
            System.out.printf("%s %s%n", resultSet.getString(1), resultSet.getInt(2));
        }
    }

    public void increaseAgeWithProcedureEx9(int minionId) throws SQLException {
        String tableName = "minions";

        this.statement = this.connection.prepareStatement(Queries.EX_9_DROP_PROCEDURE);
        this.statement.execute();
        this.statement = this.connection.prepareStatement(Queries.EX_9_CREATE_PROCEDURE);
        this.statement.execute();


        String minionName = this.queryMethods.getEntityNameById(minionId, tableName);
        if(minionName == null){
            System.out.printf("There is no minion with id %d.%n", minionId);
            return;
        }
        CallableStatement callableStatement = connection.prepareCall(Queries.EX_9_CALL_PROCEDURE);
        callableStatement.setInt(1, minionId);
        callableStatement.execute();


        this.resultSet = this.queryMethods.selectAllFromEntityWithId(minionId, tableName);
        while (resultSet.next()){
            System.out.printf("Minion %s is now %d years old.%n",
                    resultSet.getString("name"), resultSet.getInt("age"));
        }
    }

}
