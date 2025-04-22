package foms;

import dao.ConnectionProvider;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import utility.DBUtility;

public class ViewAttendance extends javax.swing.JFrame {

    public ViewAttendance() {
        initComponents();
        DBUtility.SetImage(this, "/utility/images/A.jpg", 1024, 600);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        datePicker = new com.toedter.calendar.JDateChooser();
        btnSearch = new javax.swing.JButton();
        btnShowAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1024, 600));
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnExit.setText("X");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("View Attendance Records");

        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] {
                "User ID", "Name", "Date", "Check In", "Check Out", "Work Duration"
            }
        ));
        jScrollPane1.setViewportView(attendanceTable);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Date:");

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnShowAll.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnShowAll.setText("Show All");
        btnShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch)
                                .addGap(18, 18, 18)
                                .addComponent(btnShowAll)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnExit))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSearch)
                        .addComponent(btnShowAll)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
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

    private javax.swing.JTable attendanceTable;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShowAll;
    private com.toedter.calendar.JDateChooser datePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
}