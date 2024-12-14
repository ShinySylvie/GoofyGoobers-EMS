import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDeleteEmp {

    public static String DeleteEmployee(String ssn){
        String msg;
        try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "DELETE FROM employees WHERE ssn = ?";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, ssn);

                    int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        msg = "Employee deleted successfully!";

                    } else {
                        msg = "No employee found with the given SSN.";
                    }
                } catch (SQLException ex) {
                    msg = "Error deleting employee: " + ex.getMessage();
                }
        return msg;
    }

    public static String AddEmployee(double salary, String fname, String lname, String email, String HireDate, String ssn ){
        String msg;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO employees (fname, lname, email, hiredate, salary, ssn) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, HireDate);
            stmt.setDouble(5, salary);
            stmt.setString(6, ssn);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                msg = "Employee added successfully!";
            } else {
                msg = "Error adding employee.";
            }
        } catch (SQLException ex) {
            msg = "Error adding employee: " + ex.getMessage();
        }

        return msg;
    }

}
