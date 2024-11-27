import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeManagementSystem {
   private static final Scanner scanner;

   public EmployeeManagementSystem() {
   }

   public static void main(String[] args) {
      String dbUrl = "jdbc:mysql://localhost:3306/employeeData";
      String dbUser = "user";
      String dbPassword = "password";

      try {
         Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

         try {
            System.out.println("Connected to the database.");

            label42:
            while(true) {
               System.out.println("\n--- Employee Management System ---");
               System.out.println("1. Add New Employee");
               System.out.println("2. Delete Employee");
               System.out.println("3. Update Employee");
               System.out.println("4. List All Employees");
               System.out.println("5. Search for Employee");
               System.out.println("6. Update Salaries");
               System.out.println("7. Generate Report");
               System.out.println("8. Exit");
               System.out.print("Choose an option: ");
               int choice = scanner.nextInt();
               scanner.nextLine();
               switch (choice) {
                  case 1:
                     addNewEmployee.AddNewEmployee(connection);
                     break;
                  case 2:
                     deleteEmployee.DeleteEmployee(connection);
                     break;
                  case 3:
                     updateEmployeeInformation.UpdateEmployeeInformation(connection);
                     break;
                  case 4:
                     listAllEmployees.ListAllEmployees(connection);
                     break;
                  case 5:
                     searchForEmployee.SearchForEmployee(connection);
                     break;
                  case 6:
                     updateSalary.UpdateSalary(connection);
                     break;
                  case 7:
                     generateReports.GenerateReports(connection);
                     break;
                  case 8:
                     System.out.println("Exiting system. Goodbye!");
                     break label42;
                  default:
                     System.out.println("Invalid choice. Please try again.");
               }
            }
         } catch (Throwable var8) {
            if (connection != null) {
               try {
                  connection.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (connection != null) {
            connection.close();
         }

      } catch (SQLException var9) {
         System.out.println("ERROR: Unable to connect to database. " + var9.getMessage());
      }
   }

   static {
      scanner = new Scanner(System.in);
   }
}

