/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

/**
 *
 * @author Lexus
 */
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class ScheduleSwing2 {

    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext, addAptConfirm;
    static JButton addApt = new JButton("+ Add");
    static JButton closeCalendar = new JButton("Close Calendar");
    static JTable tblCalendar;
    static JTable tblWeekView;
    static JComboBox cmbYear;
    static JComboBox cmbTime;
    static JFrame frmMain;
    static Schedule schedule;
    static JDatePickerImpl datePicker;
    static DefaultTableModel mtblCalendar; //Table model
    static DefaultTableModel mtblWeekView; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JScrollPane stblWeekView; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    static JTabbedPane tabbedPane = new JTabbedPane();
    static JPanel tabWeekView = new JPanel();
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMMM-yyyy");
    static SimpleDateFormat formatterWithTime = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
    static String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
    

    public static void loadWeekView(Calendar cal, String option) {
        if (!option.equals("preLoad")) {
            resetCalendarWeek();
        }
        Map<String, java.util.List<Appointment>> map = schedule.getWeeksAppointment(schedule, cal);
        for (int i = 0; i < headers.length; i++) {
            java.util.List<Appointment> list = map.get(headers[i]);
            if (list != null) {
                for (Appointment apt : list) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(apt.getStartTime());
                    mtblWeekView.setValueAt(apt.printForWeek(), c.get(Calendar.HOUR_OF_DAY) - 6, i + 1);
                }
            }
        }
        tblWeekView = new JTable(mtblWeekView);
        stblWeekView = new JScrollPane(tblWeekView);
        resetFields();
        tabWeekView.add(stblWeekView);
        tabbedPane.addTab("Weeks View", tabWeekView);

        frmMain.add(tabbedPane);
    }

    public static void resetFields() {
        tabbedPane = new JTabbedPane();
        tabWeekView = new JPanel();
    }

    public static void resetCalendarWeek() {
        mtblWeekView = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        mtblWeekView.addColumn("Time");
        for (int i = 0; i < 7; i++) {
            mtblWeekView.addColumn(headers[i]);
        }
        for (int i = 0; i < 18; i++) {
            String[] row = {i + 6 + ":00", "FREE", "FREE", "FREE", "FREE", "FREE", "FREE", "FREE"};
            mtblWeekView.addRow(row);
        }

        mtblWeekView.setColumnCount(8);
        mtblWeekView.setRowCount(20);

    }

//    public void runCalendar() {
//    }
//    public static void main(String args[]) {
    public void runCalendar() {
        //Look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }

        ScheduleTest scheduleTest = new ScheduleTest();
        schedule = new Schedule();
        scheduleTest.loadScheduleWithDummyData(schedule);

        resetFields();
        //Prepare frame
        frmMain = new JFrame("Schedule Appointments"); //Create frame
        frmMain.setSize(800, 800); //Set size to 400x400 pixels
        frmMain.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //Close when X is clicked

        //Create controls
        lblMonth = new JLabel("January");
        lblYear = new JLabel("Change year:");
        cmbYear = new JComboBox();
//        addApt = new JButton("Add");
        btnPrev = new JButton("&lt;&lt;");
        btnNext = new JButton("&gt;&gt;");
        closeCalendar = new JButton("Close Calendar");
        mtblCalendar = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        resetCalendarWeek();
        tblCalendar = new JTable(mtblCalendar);
        tblWeekView = new JTable(mtblWeekView);
        stblWeekView = new JScrollPane(tblWeekView);
        stblCalendar = new JScrollPane(tblCalendar);
        pnlCalendar = new JPanel(null);

        //Set border
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));

        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        addApt.addActionListener(new btnAddAppointment());
        cmbYear.addActionListener(new cmbYear_Action());
        closeCalendar.addActionListener(new closeCalendar());

        pnlCalendar.add(lblMonth);
        pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(addApt);
        pnlCalendar.add(closeCalendar);
        pnlCalendar.add(stblCalendar);
        tabbedPane.addTab("Calendar", pnlCalendar);

        tabWeekView.add(stblWeekView);
        tabbedPane.addTab("Weeks View", tabWeekView);
        

        frmMain.add(tabbedPane);

        //Set bounds
        pnlCalendar.setBounds(0, 0, 800, 300);
        lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 100, 25);
        lblYear.setBounds(10, 380, 80, 20);
        cmbYear.setBounds(230, 380, 80, 20);
        btnPrev.setBounds(10, 25, 50, 25);
        btnNext.setBounds(260, 25, 50, 25);
        addApt.setBounds(300, 450, 100, 50);
        closeCalendar.setBounds(400, 450, 200, 50);
        stblCalendar.setBounds(10, 50, 800, 330);        
        pnlCalendar.setBackground(Color.GREEN);

        //Make frame visible
        frmMain.setResizable(true);
        frmMain.setVisible(true);

        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;

        //Add headers
        for (int i = 0; i < 7; i++) {
            mtblCalendar.addColumn(headers[i]);
        }

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        loadWeekView(c, "preLoad");

        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(true);
        tblCalendar.getTableHeader().setReorderingAllowed(false);

        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setCellSelectionEnabled(true);

        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Set row/column count
        tblCalendar.setRowHeight(50);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);

        tblCalendar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();

                    Calendar cal = Calendar.getInstance();
                    try {
                        cal.setTime(formatter.parse(target.getValueAt(row, column) + "-" + lblMonth.getText() + "-" + realYear));
                    } catch (ParseException ex) {
                        Logger.getLogger(ScheduleSwing2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Appointment> list = (ArrayList) schedule.getDayAppointment(schedule, cal);
                    String txt = buildAppointmentList(list);
//                    JOptionPane.showMessageDialog(null, "My Goodness, this is so concise row : " + row + " column : " + column + " target : " + target.getValueAt(row, column) + " " + lblMonth.getText() + " " + realYear);
                    JOptionPane.showMessageDialog(null, "Date selected : " + formatter.format(cal.getTime()) + "\n" + txt);
                }
            }
        });

        //Populate table
        for (int i = realYear - 100; i <= realYear + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }

        //Refresh calendar
        refreshCalendar(realMonth, realYear); //Refresh calendar
    }

    public static String buildAppointmentList(ArrayList<Appointment> list) {
        String txt = "";
        for (Appointment apt : list) {
            txt += apt + "\n";
        }
        if (txt.isEmpty()) {
            txt = "No appointments scheduled for this day";
        }
        return txt;
    }

    public static void refreshCalendar(int month, int year) {
        //Variables
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month

        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear - 10) {
            btnPrev.setEnabled(false);
        } //Too early
        if (month == 11 && year >= realYear + 100) {
            btnNext.setEnabled(false);
        } //Too late
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box

        //Clear table
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                mtblCalendar.setValueAt(null, i, j);
            }
        }

        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        //Draw calendar
        for (int i = 1; i <= nod; i++) {
            int row = new Integer((i + som - 2) / 7);
            int column = (i + som - 2) % 7;
            mtblCalendar.setValueAt(i, row, column);
        }

        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }

    static class tblCalendarRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6) { //Week-end
                setBackground(new Color(255, 220, 220));
            } else { //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null) {
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) { //Today
                    setBackground(new Color(220, 220, 255));
                }
            }

            if (table.isCellSelected(row, column)) {
                setForeground(Color.red);
            }
//            else if (table.isRowSelected(row)) {
//                setForeground(Color.green);
//            } else if (table.isColumnSelected(column)) {
//                setForeground(Color.blue);
//            } else {
//                setForeground(Color.black);
//            }

            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }

    static class btnPrev_Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (currentMonth == 0) { //Back one year
                currentMonth = 11;
                currentYear -= 1;
            } else { //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }

    static class btnNext_Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (currentMonth == 11) { //Foward one year
                currentMonth = 0;
                currentYear += 1;
            } else { //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }

    static class btnAddAppointment implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            showDialog();
        }
    }


    static class btnAddAppointmentConfirm implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Calendar start = Calendar.getInstance();
            start.setTime((Date) datePicker.getModel().getValue());
            if (cmbTime.getSelectedItem().toString().length() < 5) {
                start.set(Calendar.HOUR_OF_DAY, Character.getNumericValue(cmbTime.getSelectedItem().toString().charAt(0)));
            } else {
                start.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cmbTime.getSelectedItem().toString().subSequence(0, 2).toString()));
            }
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);
            Calendar end = (Calendar) start.clone();
            end.add(Calendar.HOUR_OF_DAY, 1);
            try {
                schedule.addAppointment(new Appointment(start.getTime(), end.getTime()));
                JOptionPane.showMessageDialog(frmMain, "Appointment scheduled!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmMain, "Error : Appointment already exists", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            Date date = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            loadWeekView(c, "posLoad");
        }
    }

    static void showDialog() {
        JPanel panel = new JPanel();

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        panel.add(datePicker);

        cmbTime = new JComboBox();
        for (int i = 8; i < 21; i++) {
            cmbTime.addItem(i + ":00");
        }

        addAptConfirm = new JButton("Add Apt");
        addAptConfirm.addActionListener(new btnAddAppointmentConfirm());

        panel.add(cmbTime);
        panel.add(addAptConfirm);
        JPanel holder = new JPanel(
                new BorderLayout());
        holder.add(panel, BorderLayout.NORTH);

        // This is where the dialog is actually displayed..
        JOptionPane.showMessageDialog(frmMain,
                holder,
                "JOptionPane dialog",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static class cmbYear_Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (cmbYear.getSelectedItem() != null) {
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }

    static class closeCalendar implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frmMain.dispose();
        }
    }

}
