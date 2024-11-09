package system;

import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import database.Database;

public class HRMSystem {

    private int employeeId = 000;
    private LinkedList<Employee> employees = new LinkedList<>();
    private DefaultTableModel tableModel;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Critical critical = new Critical();
    private Database database = new Database();

    private String generateId() {
        return String.valueOf(employeeId++);
    }

    public double generateGrossPay(double ratePerDay, int workDays) {
        return ratePerDay * workDays;
    }

    public double generateNetPay(double grossPay) {
        double deductionRate = 0;
        if (grossPay <= 10000) {
            deductionRate = 0.03;
        } else if (grossPay > 10000 && grossPay < 20000) {
            deductionRate = 0.07;
        } else {
            deductionRate = 0.12;
        }
        return grossPay - (grossPay * 3 * deductionRate);
    }

    public boolean addEmployee(String fName, String lName, int workDays, double ratePerDay, int absences) throws SQLException {
        if (fName.isEmpty() || lName.isEmpty()) return false;

        double grossPay = generateGrossPay(ratePerDay, workDays - absences);
        Employee employee = new Employee(generateId(), fName, lName, workDays, ratePerDay, grossPay, generateNetPay(grossPay), absences);
        employees.add(employee);

        connection = database.getConnection();
        preparedStatement = connection.prepareStatement("INSERT INTO employees (first_name, last_name, work_days, rate_per_day, absences, gross_pay, net_pay) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, fName);
        preparedStatement.setString(2, lName);
        preparedStatement.setInt(3, workDays);
        preparedStatement.setDouble(4, ratePerDay);
        preparedStatement.setInt(5, absences);
        preparedStatement.setDouble(6, grossPay);
        preparedStatement.setDouble(7, generateNetPay(grossPay));
        preparedStatement.execute();

        sortEmployeesById();
        return true;
    }
    
    public LinkedList<Employee> getEmployeeList() throws SQLException {
        employees.clear();
        connection = database.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM employees");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            employees.add(new Employee(
                Integer.toString(resultSet.getInt("id")),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("work_days"),
                resultSet.getDouble("rate_per_day"),
                resultSet.getDouble("gross_pay"),
                resultSet.getDouble("net_pay"),
                resultSet.getInt("absences")
            ));
        }

        return employees;
    }

    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, work_days = ?, rate_per_day = ?, absences = ?, gross_pay = ?, net_pay = ? WHERE id = ?";
        connection = database.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, employee.getfName());
        preparedStatement.setString(2, employee.getlName());
        preparedStatement.setInt(3, employee.getWorkdays());
        preparedStatement.setDouble(4, employee.getRatePerDay());
        preparedStatement.setInt(5, employee.getAbsences());
        preparedStatement.setDouble(6, employee.getGrossPay());
        preparedStatement.setDouble(7, employee.getNetPay());
        preparedStatement.setInt(8, Integer.parseInt(employee.getId()));
        preparedStatement.executeUpdate();
    }

    public void deleteEmployee(int index) throws SQLException {
        String employeeId = employees.get(index).getId();
        String sql = "DELETE FROM employees WHERE id = ?";
        connection = database.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(employeeId));
        preparedStatement.executeUpdate();

        employees.remove(index);
    }

    public void update(LinkedList<Employee> employees, DefaultTableModel tableModel) {
        this.employees = employees;
        sortEmployeesById();
        this.tableModel = tableModel;
        this.tableModel.setRowCount(0);
        for (Employee e : employees) {
            Object[] obj = { e.getId(), e.getfName(), e.getlName(), e.getRatePerDay(), e.getWorkdays(), e.getAbsences(), e.getGrossPay(), e.getNetPay() };
            tableModel.addRow(obj);
        }
    }

    private void sortEmployeesById() {
    	critical.ensure();
        Collections.sort(employees, Comparator.comparingInt(e -> Integer.parseInt(e.getId())));
    }
}
