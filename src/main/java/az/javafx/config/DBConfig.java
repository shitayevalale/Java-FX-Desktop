package az.javafx.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    public static Connection getconnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/studentmanagmentapp?serverTimezone=UTC";
            String user = "root";
            String password = "mysql";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            if (con != null) {
                System.out.println("ok");
            } else {
                System.out.println("not ok");
            }

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
