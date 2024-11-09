package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import system.Employee;
import system.HRMSystem;

public class MainWindow {

	private JFrame frame;
	private JTextField tfFName;
	private HRMSystem system = new HRMSystem();
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel = new DefaultTableModel();

	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmLogout;
	private JButton btnAddEmployee;
	private JButton btnDeleteEmployee;
	private JTextField tfLName;
	private JPanel mainPanel;
	private JPanel plAdd;
	private JButton btnUpdate;
	private String id, index = null;
	private boolean selected = false;
	private int rowSelected;
	private JTextField tfNoDays;
	private JTextField tfRateDay;
	private JTextField tfSearchId;
	private JButton btnSearch;
	private JLabel lblAbscences;
	private JTextField tfAbsences;
	// LoginWindow loginWindow = new LoginWindow();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public MainWindow() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		// system.addInitial();
		Object[] columns = { "Id", "First Name", "Last Name","Rate Per Day", "No. of Days", "Absences", "Gross Pay", "Net Pay"};
		tableModel.setColumnIdentifiers(columns);
		refreshTable();

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 827, 536);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "Logout from the application?",
						"Logout confirmation box", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					frame.dispose();
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 811, 476);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		plAdd = new JPanel();
		plAdd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		plAdd.setBounds(10, 54, 254, 216);
		mainPanel.add(plAdd);
		plAdd.setLayout(null);

		JLabel lblName = new JLabel("First name:");
		lblName.setBounds(17, 14, 63, 14);
		plAdd.add(lblName);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);

		tfFName = new JTextField();
		tfFName.setBounds(121, 14, 101, 20);
		plAdd.add(tfFName);
		tfFName.setColumns(10);

		btnAddEmployee = new JButton("Add");
		btnAddEmployee.setBounds(17, 176, 205, 23);
		plAdd.add(btnAddEmployee);
		btnAddEmployee.setForeground(Color.BLACK);
		btnAddEmployee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean fieldsEmpty = true;
				try {
					fieldsEmpty = system.addEmployee(tfFName.getText(), tfLName.getText(), Integer.parseInt(tfNoDays.getText()), Double.parseDouble(tfRateDay.getText()), Integer.parseInt(tfAbsences.getText()));
					btnAddEmployee.setEnabled(false);
					refreshTable();
					disableFields();
					clearFields();
					JOptionPane.showMessageDialog(frame, "Employee added successfully", "Operation successful",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException e1) {
					disableFields();
					clearFields();
					btnAddEmployee.setEnabled(false);
					JOptionPane.showMessageDialog(frame, e1.getMessage(), "Input invalid",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					disableFields();
					clearFields();
					btnAddEmployee.setEnabled(false);
					JOptionPane.showMessageDialog(frame, e1.getMessage(), "Input invalid",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAddEmployee.setEnabled(false);

		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setBounds(17, 46, 63, 14);
		plAdd.add(lblLastName);
		lblLastName.setHorizontalAlignment(SwingConstants.LEFT);

		tfLName = new JTextField();
		tfLName.setBounds(121, 46, 101, 20);
		plAdd.add(tfLName);
		tfLName.setEnabled(false);
		tfLName.setColumns(10);
		
		tfNoDays = new JTextField();
		tfNoDays.setEnabled(false);
		tfNoDays.setColumns(10);
		tfNoDays.setBounds(121, 77, 101, 20);
		plAdd.add(tfNoDays);
		
		JLabel lblNoOfWork = new JLabel("No. of work days:");
		lblNoOfWork.setHorizontalAlignment(SwingConstants.LEFT);
		lblNoOfWork.setBounds(17, 77, 101, 14);
		plAdd.add(lblNoOfWork);
		
		JLabel lblRatePerDay = new JLabel("Rate per day:");
		lblRatePerDay.setHorizontalAlignment(SwingConstants.LEFT);
		lblRatePerDay.setBounds(17, 108, 87, 14);
		plAdd.add(lblRatePerDay);
		
		tfRateDay = new JTextField();
		tfRateDay.setEnabled(false);
		tfRateDay.setColumns(10);
		tfRateDay.setBounds(121, 108, 101, 20);
		plAdd.add(tfRateDay);
		
		lblAbscences = new JLabel("No. of Absences:");
		lblAbscences.setHorizontalAlignment(SwingConstants.LEFT);
		lblAbscences.setBounds(17, 142, 87, 14);
		plAdd.add(lblAbscences);
		
		tfAbsences = new JTextField();
		tfAbsences.setEnabled(false);
		tfAbsences.setColumns(10);
		tfAbsences.setBounds(121, 142, 101, 20);
		plAdd.add(tfAbsences);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(274, 38, 527, 427);
		mainPanel.add(scrollPane);

		table = new JTable(tableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rowSelected = table.getSelectedRow();
				index = Integer.toString(rowSelected);
				selected = true;
				id = tableModel.getValueAt(rowSelected, 0).toString();
				tfFName.setText(tableModel.getValueAt(rowSelected, 1).toString());
				tfLName.setText(tableModel.getValueAt(rowSelected, 2).toString());
				tfRateDay.setText(tableModel.getValueAt(rowSelected, 3).toString());
				tfNoDays.setText(tableModel.getValueAt(rowSelected, 4).toString());
				tfAbsences.setText(tableModel.getValueAt(rowSelected, 5).toString());
			}
		});

		scrollPane.setViewportView(table);

		JButton btnAdd = new JButton("Add Employee");
		btnAdd.setBounds(10, 20, 116, 23);
		mainPanel.add(btnAdd);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected) {
					EditWindow editWindow = new EditWindow();
					editWindow.getEditFrame(index, id, tfFName.getText(), tfLName.getText(), tfNoDays.getText(), tfRateDay.getText(), tfAbsences.getText(), tableModel).setVisible(true);
				}
			}
		});
		btnUpdate.setBounds(443, 11, 89, 23);
		mainPanel.add(btnUpdate);

		JButton btnRefreshTable = new JButton("Refresh");
		btnRefreshTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refreshTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				clearFields();
				selected = false;
			}
		});
		btnRefreshTable.setBounds(274, 11, 79, 23);
		mainPanel.add(btnRefreshTable);

		btnDeleteEmployee = new JButton("Delete");
		btnDeleteEmployee.setBounds(363, 11, 70, 23);
		btnDeleteEmployee.setEnabled(true);
		mainPanel.add(btnDeleteEmployee);
		btnDeleteEmployee.setForeground(Color.BLACK);
		
		tfSearchId = new JTextField();
		tfSearchId.setBounds(637, 12, 70, 20);
		mainPanel.add(tfSearchId);
		tfSearchId.setColumns(10);
		tfSearchId.setToolTipText("Employee ID");
		
		btnSearch = new JButton("Search ID");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchResult(tfSearchId.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(712, 11, 89, 23);
		mainPanel.add(btnSearch);
		
		JButton btnDisplayPayslip = new JButton("Display Payslip");
		btnDisplayPayslip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected) {
					JOptionPane.showMessageDialog(null, "-----PAY SLIP-----\n" + "Employee: " + tfFName.getText() + " " + tfLName.getText() + "\nRate per day: " + tfRateDay.getText() + "\nNumber of work days: " + tfNoDays.getText() + "\nNo. of Absences: " + tfAbsences.getText() + "\nGross pay: " + tableModel.getValueAt(rowSelected, 6).toString() + "\nNet pay: " + tableModel.getValueAt(rowSelected, 7).toString() + "\n");
				}
				
				selected = false;
				clearFields();
			}
		});
		btnDisplayPayslip.setBounds(66, 358, 132, 23);
		mainPanel.add(btnDisplayPayslip);
		btnDeleteEmployee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (index != null) {
					int result = JOptionPane.showConfirmDialog(frame, "Delete employee " + id + "?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						try {
							system.deleteEmployee(Integer.parseInt(index));
							refreshTable();
							clearFields();
							JOptionPane.showMessageDialog(frame, "Employee deleted successfully", "Deletion confirmation box", JOptionPane.INFORMATION_MESSAGE);
						} catch (ArrayIndexOutOfBoundsException e1) {
							JOptionPane.showMessageDialog(frame, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableFields();
				btnAddEmployee.setEnabled(true);
			}
		});

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		mnOptions.add(mntmLogout);
		disableFields();

	}

	public JFrame getMainWindow() {
		return frame;
	}

	private void enableFields() {
		btnAddEmployee.setForeground(Color.BLACK);
		btnDeleteEmployee.setForeground(Color.BLACK);
		btnAddEmployee.setBackground(Color.BLUE);
		btnDeleteEmployee.setBackground(Color.RED);

		tfFName.setEnabled(true);
		tfLName.setEnabled(true);
		tfNoDays.setEnabled(true);
		tfRateDay.setEnabled(true);
		tfAbsences.setEnabled(true);
	}

	private void disableFields() {
		btnAddEmployee.setBackground(Color.LIGHT_GRAY);
		tfFName.setEnabled(false);
		tfLName.setEnabled(false);
		tfNoDays.setEnabled(false);
		tfRateDay.setEnabled(false);
		tfAbsences.setEnabled(false);
	}

	private void clearFields() {
		tfFName.setText("");
		tfLName.setText("");
		tfNoDays.setText("");
		tfRateDay.setText("");
		tfAbsences.setText("");
	}

	private void refreshTable() throws SQLException {
		LinkedList<Employee> employees = system.getEmployeeList();
		tableModel.setRowCount(0);
		for (Employee e : employees) {
			Object[] obj = { e.getId(), e.getfName(), e.getlName(), Double.toString(e.getRatePerDay()), Integer.toString(e.getWorkdays()), Integer.toString(e.getAbsences()), Double.toString(e.getGrossPay()), Double.toString(e.getNetPay()) };
			tableModel.addRow(obj);
		}
	}
	
	private void searchResult(String id) throws SQLException {
		LinkedList<Employee> employees = system.getEmployeeList();
		tableModel.setRowCount(0);
		for (Employee e : employees) {
			if (id.equals(e.getId())) {
				Object[] obj = { e.getId(), e.getfName(), e.getlName(), Double.toString(e.getRatePerDay()), Integer.toString(e.getWorkdays()), Integer.toString(e.getAbsences()), Double.toString(e.getGrossPay()), Double.toString(e.getNetPay()) };
				tableModel.addRow(obj);
			}
		}
	}
}
