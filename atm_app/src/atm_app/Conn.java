package atm_app;

import java.sql.*;

public class Conn {

    private static Connection connection;
    static Statement statement;

    public static Connection getConnection() {
        Connection Conn=null;
        return connection;
    }

    public static Statement getStatement() throws SQLException {
        statement = connection.createStatement();
        return statement;
    }
 public static ResultSet getResultset(String query) throws SQLException {
        statement = getStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sign_up", "root", "MePushkar@sql#193?PW");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

}