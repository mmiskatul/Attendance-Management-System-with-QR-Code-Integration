package foms;

import dao.ConnectionProvider;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import utility.DBUtility;

public class ViewAttendance extends javax.swing.JFrame {

    public ViewAttendance() {
        initComponents();
        DBUtility.SetImage(this, "/utility/images/A.jpg", 1024, 600);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void formComponentShown(java.awt.event.ComponentEvent evt) {
        loadAttendanceData(null);
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        if (datePicker.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String selectedDate = sdf.format(datePicker.getDate());
            loadAttendanceData(selectedDate);
        } else {
            loadAttendanceData(null);
        }
    }

    private void btnShowAllActionPerformed(java.awt.event.ActionEvent evt) {
        loadAttendanceData(null);
    }

    private void loadAttendanceData(String dateFilter) {
        DefaultTableModel model = (DefaultTableModel) attendanceTable.getModel();
        model.setRowCount(0);

        try {
            Connection con = ConnectionProvider.getcon();
            String query = "SELECT u.id, u.name, a.date, a.checkIn, a.checkOut, a.workDuration " +
                           "FROM userattendance a " +
                           "JOIN userdetails u ON a.user_id = u.id";
            
            if (dateFilter != null && !dateFilter.isEmpty()) {
                query += " WHERE a.date = '" + dateFilter + "'";
            }
            
            query += " ORDER BY a.date DESC, u.name";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("checkIn"),
                    rs.getString("checkOut"),
                    rs.getString("workDuration")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTable attendanceTable;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShowAll;
    private com.toedter.calendar.JDateChooser datePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration
}
