package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static ConnectDB connectDB;
    private static Connection con = null;


    public static ConnectDB getConnectDB() {
        return connectDB;
    }

    public static Connection getConnection() {
        try {
            String user = "sa";
            String password = "sapassword";
            String url = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=QuanLyDatBan1;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
            		+ "sendStringParametersAsUnicode=true;";
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public static void disconnect() throws SQLException {
        if(con != null) {
            System.out.println("ngắt thành công");
            con.close();
        };
    }

    // lấy kết nối
//    public static Connection getConnection() throws SQLException {
//        connect();
//        return con;
//    }
}
