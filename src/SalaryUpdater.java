import java.sql.*;

public class SalaryUpdater {

    public static String updateSalary(double lowerbound, double upperbound, double percentage) {
        System.out.println("Hey, you've managed to access the Salary Updater!"); //debug message
        StringBuilder output = new StringBuilder( "" );
        try (Connection conn = DatabaseConnection.getConnection();
             Statement Selectstmt = conn.createStatement();
             Statement Updatestmt = conn.createStatement();) {
            //sql queries
            String Selectedquery = "SELECT empid, Fname, Lname, Salary FROM employees WHERE salary BETWEEN " +lowerbound+ " AND " +upperbound;
            String Updatedquery = "UPDATE employees SET salary = salary + (salary * " +percentage+ " / 100) WHERE salary BETWEEN " +lowerbound+ " AND " +upperbound;
            
            ResultSet selected = Selectstmt.executeQuery(Selectedquery); //grab employees that fit sql statement
            Updatestmt.executeUpdate(Updatedquery);
            output.append("Employees Updated:");

            //loop through result set
            while (selected.next()){

                output.append("\nID: " + selected.getString("empid"));
                output.append("\nName: " + selected.getString( "Fname" ) ).append( " " + selected.getString( "Lname" ) );
                output.append("\nOld Salary: " +selected.getString("salary")); //salary grabbed before update
                output.append("\nNew Salary: " +(selected.getDouble("Salary") + (selected.getDouble("Salary") * (percentage / 100)))); //salary after update
            }
            System.out.println(output); //debug result


             }catch (SQLException e) {
                e.printStackTrace();
                output.append("Error updating salaries: ").append(e.getMessage());
                System.out.println(output); //debug message
            }

            return output.toString();


    }


}
