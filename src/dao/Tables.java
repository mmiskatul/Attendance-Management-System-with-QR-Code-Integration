package dao;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class Tables {
    public static void main(String[] args) {
        try {
            Connection con = ConnectionProvider.getcon(); 
            if (con != null) {
                System.out.println("Connected to database successfully!"); 
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally{
            
        }
    }
}
