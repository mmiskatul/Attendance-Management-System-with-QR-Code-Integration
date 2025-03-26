package dao;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.*;

public class Tables {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        try {
            con = ConnectionProvider.getcon();
            st = con.createStatement();

            if (!tableExists(con, "userdetails")) {
                st.executeUpdate("CREATE TABLE userdetails ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(255) NOT NULL, "
                        + "gender VARCHAR(50) NOT NULL, "
                        + "email VARCHAR(255) NOT NULL, "
                        + "contract VARCHAR(20) NOT NULL, "
                        + "address VARCHAR(500) NOT NULL, "
                        + "division VARCHAR(100), "
                        + "country VARCHAR(100), "
                        + "unique_registration_id VARCHAR(100) NOT NULL, "
                        + "imageName VARCHAR(100)"
                        + ")");
                System.out.println("Table 'userdetails' Create successfully");
                JOptionPane.showMessageDialog(null, "Table 'userdetails' Create successfully");
            }else{
                System.out.print("Table is ALready Exits in the Database");
            }

            if (!tableExists(con, "userattendance")) {
                if (!tableExists(con, "userattendance")) {
                    st.executeUpdate("CREATE TABLE userattendance ("
                            + "attendance_id INT AUTO_INCREMENT PRIMARY KEY, "
                            + "user_id INT NOT NULL, "
                            + "date DATE NOT NULL, "
                            + "checkIn DATETIME, "
                            + "checkOut DATETIME, "
                            + "workDuration VARCHAR(100), "
                            + "FOREIGN KEY (user_id) REFERENCES userdetails(id)"
                            + ")");
                    System.out.println("Table 'userattendance' created successfully!");
                    JOptionPane.showMessageDialog(null,"Table 'userattendance' Create successfully");
                }else{
                    System.out.println("Table is ALready Exits in the Database");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }
}
