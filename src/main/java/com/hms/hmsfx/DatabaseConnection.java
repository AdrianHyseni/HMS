package com.hms.hmsfx;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName     = "hms";
        String databaseUser     = "root";
        String databasePassword = "root";

        String url = "jdbc:mysql://localhost/"+ databaseName;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);

        } catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }

}
