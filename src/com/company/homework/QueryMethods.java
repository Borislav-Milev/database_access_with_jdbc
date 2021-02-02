package com.company.homework;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryMethods {

    private final Connection connection;
    private PreparedStatement statement;
    
    public QueryMethods(Connection connection) {
        this.connection = connection;
    }

    public int getEntityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format(Queries.GET_ID_BY_NAME, tableName);
        this.statement = this.connection.prepareStatement(query);
        this.statement.setString(1, entityName);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    public String getEntityNameById(int entityId, String tableName) throws SQLException {
        String query = String.format(Queries.GET_NAME_BY_ID, tableName);
        PreparedStatement statement = this.connection.prepareStatement(query);

        statement.setInt(1, entityId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    public void insertEntityInTableByName(String entityName, String tableName) throws SQLException {
        String query = String.format(Queries.INSERT_ENTITY_BY_TABLE_NAME, tableName);

        this.statement = this.connection.prepareStatement(query);
        this.statement.setString(1, entityName);
        this.statement.execute();
    }

    public void insertVillain(String villain) throws SQLException {
        String query = Queries.INSERT_VILLAIN;
        this.statement = this.connection.prepareStatement(query);
        this.statement.setString(1, villain);
        this.statement.execute();
    }

    public void insertMinion(String name, int age, int townId) throws SQLException {
        String query = Queries.INSERT_MINION;

        this.statement = this.connection.prepareStatement(query);
        this.statement.setString(1, name);
        this.statement.setInt(2, age);
        this.statement.setInt(3, townId);
        this.statement.execute();
    }

    public void insertIntoMinionsVillains(int minionId, int villainId) throws SQLException {
        this.statement = this.connection.prepareStatement(Queries.INSERT_INTO_MINIONS_VILLAINS);
        this.statement.setInt(1, minionId);
        this.statement.setInt(2, villainId);
        this.statement.execute();
    }

    public ResultSet selectAllFromEntityWithId(int id, String tableName) throws SQLException {
        this.statement = this.connection.prepareStatement(String.format(Queries.SELECT_ALL_BY_ID, tableName));
        this.statement.setInt(1, id);
        return this.statement.executeQuery();
    }
}
