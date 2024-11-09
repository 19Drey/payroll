package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import system.Employee;
import system.HRMSystem;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class EditWindow {

    private JFrame frame;
    private JTextField tfFNameEdit;
    private JTextField tfLNameEdit;
    private String f, l, id = null, index;
    private HRMSystem system = new HRMSystem();
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblRatePerDay;
    private JTextField tfRatePerDayEdit;
    private JLabel lblNoOfWork;
    private JTextField tfWorkDaysEdit;
    private JTextField tfAbsences;
    private JLabel lblNoOfAbsences;
    private DefaultTableModel tableModel;

    public EditWindow(String f, String l) {
        this.f = f;
        this.l = l;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditWindow window = new EditWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public EditWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 309, 277);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnSaveEdit = new JButton("Save");
        btnSaveEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    LinkedList<Employee> emps;
                    try {
                        // Get the updated list of employees from the system
                        emps = system.getEmployeeList();
                        int position = Integer.parseInt(index);
                        double ratePerDay = Double.parseDouble(tfRatePerDayEdit.getText());
                        int workDays = Integer.parseInt(tfWorkDaysEdit.getText());
                        int absences = Integer.parseInt(tfAbsences.getText());
                        double grossPay = system.generateGrossPay(ratePerDay, workDays - absences);
                        
                        // Update the employee details
                        Employee emp = emps.get(position);
                        emp.setfName(tfFNameEdit.getText());
                        emp.setlName(tfLNameEdit.getText());
                        emp.setWorkdays(workDays);
                        emp.setRatePerDay(ratePerDay);
                        emp.setGrossPay(grossPay);
                        emp.setNetPay(system.generateNetPay(grossPay));
                        emp.setAbsences(absences);
                        
                        // Use the update method from HRMSystem to update the table
                        system.updateEmployee(emp);
                        system.update(emps, tableModel);
                        frame.dispose();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnSaveEdit.setBounds(37, 199, 86, 23);
        frame.getContentPane().add(btnSaveEdit);

        JButton btnCancelEdit = new JButton("Cancel");
        btnCancelEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnCancelEdit.setBounds(160, 199, 86, 23);
        frame.getContentPane().add(btnCancelEdit);

        tfFNameEdit = new JTextField();
        tfFNameEdit.setBounds(10, 48, 113, 20);
        frame.getContentPane().add(tfFNameEdit);
        tfFNameEdit.setColumns(10);

        tfLNameEdit = new JTextField();
        tfLNameEdit.setBounds(160, 48, 113, 20);
        frame.getContentPane().add(tfLNameEdit);
        tfLNameEdit.setColumns(10);

        JLabel lblEdit = new JLabel("EDIT");
        lblEdit.setHorizontalAlignment(SwingConstants.CENTER);
        lblEdit.setBounds(111, 11, 46, 14);
        frame.getContentPane().add(lblEdit);

        lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(10, 29, 113, 14);
        frame.getContentPane().add(lblFirstName);

        lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(160, 29, 113, 14);
        frame.getContentPane().add(lblLastName);

        lblRatePerDay = new JLabel("Rate Per Day");
        lblRatePerDay.setBounds(10, 79, 113, 14);
        frame.getContentPane().add(lblRatePerDay);

        tfRatePerDayEdit = new JTextField();
        tfRatePerDayEdit.setText((String) null);
        tfRatePerDayEdit.setColumns(10);
        tfRatePerDayEdit.setBounds(10, 98, 113, 20);
        frame.getContentPane().add(tfRatePerDayEdit);

        lblNoOfWork = new JLabel("No. of Work Days");
        lblNoOfWork.setBounds(160, 79, 113, 14);
        frame.getContentPane().add(lblNoOfWork);

        tfWorkDaysEdit = new JTextField();
        tfWorkDaysEdit.setText((String) null);
        tfWorkDaysEdit.setColumns(10);
        tfWorkDaysEdit.setBounds(160, 98, 113, 20);
        frame.getContentPane().add(tfWorkDaysEdit);

        tfAbsences = new JTextField();
        tfAbsences.setText((String) null);
        tfAbsences.setColumns(10);
        tfAbsences.setBounds(10, 154, 113, 20);
        frame.getContentPane().add(tfAbsences);

        lblNoOfAbsences = new JLabel("No. of Absences");
        lblNoOfAbsences.setBounds(10, 129, 113, 14);
        frame.getContentPane().add(lblNoOfAbsences);

        fillFields();
    }

    public void setData(String fName, String lName) {
        f = fName;
        l = lName;
    }

    public JFrame getEditFrame(String index, String id, String f, String l, String workDays, String ratePerDay, String absences, DefaultTableModel tableModel) {
        tfFNameEdit.setText(f);
        tfLNameEdit.setText(l);
        tfRatePerDayEdit.setText(ratePerDay);
        tfWorkDaysEdit.setText(workDays);
        tfAbsences.setText(absences);
        this.id = id;
        this.index = index;
        this.tableModel = tableModel;
        return frame;
    }

    private void fillFields() {
        tfFNameEdit.setText(f);
        tfLNameEdit.setText(l);
    }
}
