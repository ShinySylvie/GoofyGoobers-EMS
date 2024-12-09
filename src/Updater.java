import java.sql.*;

public class Updater {

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
            output.append("Employees Updated:");

            //loop through result set
            while (selected.next()){

                output.append("\nID: " + selected.getString("empid"));
                output.append("\nName: " + selected.getString( "Fname" ) ).append( " " + selected.getString( "Lname" ) );
                output.append("\nOld Salary: " +selected.getDouble("salary")); //salary grabbed before update
                output.append("\nNew Salary: " +(selected.getDouble("Salary") + (selected.getDouble("Salary") * (percentage / 100)))); //salary after update
            }
            Updatestmt.executeUpdate(Updatedquery);
            System.out.println(output); //debug result


             }catch (SQLException e) {
                e.printStackTrace();
                output.append("Error updating salaries: ").append(e.getMessage());
                System.out.println(output); //debug message
            }

            return output.toString();


    }

    public static String updateEmployee(int empid, String fname, String lname, String email, String HireDate, String ssn ){

        StringBuilder output = new StringBuilder( "" );

        try (Connection conn = DatabaseConnection.getConnection();
        Statement getempID = conn.createStatement();
        Statement Updatestmt = conn.createStatement();) {

            String IDquery = "SELECT Fname, Lname, email, HireDate, ssn FROM employees WHERE empid = " +empid ;
            String update = "";
            ResultSet set = getempID.executeQuery(IDquery);

            output.append("Employee Choosen: \n");
            while (set.next()) {
                output.append("ID: "+empid+ "\n");
                output.append("First Name: "+set.getString("Fname")+ "\n");
                output.append("Last Name: "+set.getString("Lname")+ "\n");
                output.append("Email: "+set.getString("email")+ "\n");
                output.append("Hire Date: "+set.getString("HireDate")+ "\n");
                output.append("SSN: "+set.getString("ssn")+ "\n");
            }

            output.append("\nEmployee " +empid+ " updated! New Changes:\n");

                if (fname != null &&!fname.isEmpty()) {
                    update = "UPDATE employees SET fname = '" + fname + "' WHERE empid = " + empid + ";";
                    Updatestmt.executeUpdate(update);
                    output.append("New First Name: "+fname+ "\n");
                }
                if (lname != null &&!lname.isEmpty()) {
                    update = "UPDATE employees SET lname = '" + lname + "' WHERE empid = " + empid + ";";
                    Updatestmt.executeUpdate(update);
                    output.append("New Last Name: "+lname+ "\n");
                }
                if (email != null &&!email.isEmpty()) {
                    update = "UPDATE employees SET email = '" + email + "' WHERE empid = " + empid + ";";
                    Updatestmt.executeUpdate(update);
                    output.append("New Email: "+email+ "\n");
                }
                if (HireDate != null &&!HireDate.isEmpty()) {
                    update = "UPDATE employees SET HireDate = '" + HireDate + "' WHERE empid = " + empid + ";";
                    Updatestmt.executeUpdate(update);
                    output.append("New Hire Date: "+HireDate+ "\n");
                }
                if (ssn != null &&!ssn.isEmpty()) {
                    update = "UPDATE employees SET ssn = '" + ssn + "' WHERE empid = " + empid + ";";
                    Updatestmt.executeUpdate(update);
                    output.append("New SSN: "+ssn+ "\n");
                }


        }catch (SQLException e) {
                e.printStackTrace();
                output.append("Error updating employee: ").append(e.getMessage());
                System.out.println(output); //debug message
            }

        return output.toString();
    }


}
