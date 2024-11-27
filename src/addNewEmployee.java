import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class addNewEmployee 
{
    private static final Scanner scanner = new Scanner(System.in);

    public static void AddNewEmployee(Connection connection)
    {
        System.out.print("\nEnter employee's First name: ");
        String fName = scanner.nextLine();

        System.out.print("Enter employee's last name: ");
        String lName = scanner.nextLine();

        System.out.print("Enter employee's email: ");
        String email = scanner.nextLine();

        System.out.print("Enter employee's SSN: ");
        String ssn = scanner.nextLine();

        System.out.print("Enter employee's hireDate (YYYY-MM-DD): ");
        String hireDate = scanner.nextLine();

        System.out.print("Enter employee's Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        String sqlInsert = "INSERT INTO employees (Fname, Lname, email, SSN, HireDate, Salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, ssn);
            preparedStatement.setDate(5, java.sql.Date.valueOf(hireDate));
            preparedStatement.setDouble(6, salary);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("\n\tEmployee added successfully.\n\n\n");
            } else {
                System.out.println("No record inserted.\n");
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
