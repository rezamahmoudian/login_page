package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class databace {

    public void connect_to_databace() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
    }




}
