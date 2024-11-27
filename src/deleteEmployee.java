import java.sql.*;
import java.util.Scanner;
public class deleteEmployee 
{
    private static final Scanner scanner = new Scanner(System.in);
    // Start DeleteEmployee method

    public static void DeleteEmployee(Connection connection)
    {
        
        // SQL delete statement
        // Prompt user to enter the ID of the employee to delete
        System.out.print("\nEnter employee s'ID to delete : ");
        int empid = scanner.nextInt();
        String sqlDelete = "DELETE FROM employees WHERE empid = ?";

        try (
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
            
            ) 
        {

            // Set the search value as a parameter for each criterion
            preparedStatement.setInt(1, empid);

            // Execute the delete statement
            int rowsDeleted = preparedStatement.executeUpdate();


            // Check if the deleted was successful

            if(rowsDeleted>0)
            {
                System.out.println("\n The employee with employee ID: "  +empid + " was successfully deleted.\n\n");
            
            }
            else
            {
                System.out.println("No Employee found with ID: " + empid +"\n");

            }
        }
        catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally 
        {
        }
        
    }

    // End Of DeleteEmployee


}
