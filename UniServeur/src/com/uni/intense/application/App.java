package com.uni.intense.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    protected static final String URL = "jdbc:mysql://localhost:3306/gestionuniversite?useSSL=false&serverTimezone=UTC";
    protected static final String USER = "root";
    protected static final String PASS = "3059"; // mets ton vrai mot de passe ici

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}