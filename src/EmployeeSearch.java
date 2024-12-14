import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

public class EmployeeSearch {
    
    public static List<String> searchEmployees(String searchType, String searchValue) {
        List<String> employees = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query;
            PreparedStatement stmt;

            switch (searchType.toLowerCase()) {
                case "id":
                    query = "SELECT empid, Fname, Lname, SSN FROM employees WHERE empid = ?";
                    stmt = connection.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(searchValue));
                    break;
                case "name":
                    query = "SELECT empid, Fname, Lname, SSN FROM employees WHERE Fname LIKE ? OR Lname LIKE ?";
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, "%" + searchValue + "%");
                    stmt.setString(2, "%" + searchValue + "%");
                    break;
                case "ssn":
                    query = "SELECT empid, Fname, Lname, SSN FROM employees WHERE SSN = ?";
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, searchValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search type: " + searchType);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String employee = String.format("ID: %d, Name: %s %s, SSN: %s",
                        rs.getInt("empid"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getString("SSN"));
                employees.add(employee);
            }

        } catch (SQLException e) {
            System.out.println("Error while searching for employees: " + e.getMessage());
        }

        return employees;
    }

    // method to get all employees for display
    public static List<String> getAllEmployees() {
        List<String> employees = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT empid, Fname, Lname, SSN FROM employees";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String employee = String.format("ID: %d, Name: %s %s, SSN: %s",
                        rs.getInt("empid"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getString("SSN"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching employees: " + e.getMessage());
        }

        return employees;
    }

    // new
    public static void updateEmployeeListDisplay(JTextArea emplist) {
        List<String> employees = getAllEmployees();
        emplist.setText(""); 
        if (employees.isEmpty()) {
            emplist.setText("No employees found in the database.");
        } else {
            for (String employee : employees) {
                emplist.append(employee + "\n");
            }
        }
    }
}

