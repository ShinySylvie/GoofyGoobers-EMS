import java.sql.*;

public class Generate {

        public static String generateReportByTitle() {
        StringBuilder report = new StringBuilder("Report by Title:\n\n");
        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to fetch job titles
            String sql = "SELECT job_title_id, job_title FROM employeedata.job_titles ORDER BY job_title_id ASC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
    
            // Building the report
            while (rs.next()) {
                int jobTitleId = rs.getInt("job_title_id");
                String jobTitle = rs.getString("job_title");
                report.append(String.format("ID: %d, Title: %s%n", jobTitleId, jobTitle));
            }
    
            // Display the report

        } catch (SQLException ex) {
            // Show error message if SQL exception occurs
            report.append("Error generating report: " + ex.getMessage()+ "Error");
        }

        return report.toString();
    }
    
    public static String generateReportByDivision() {
        StringBuilder report = new StringBuilder("Report by Division:\n");
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Your SQL query for fetching employee division details
            String sql = "SELECT ID, Name, city, addressLine1, addressLine2, state, country, postalCode FROM employeeData.division";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Check if the query returns any results
            boolean hasResults = false;

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String city = rs.getString("city");
                String addressLine1 = rs.getString("addressLine1");
                String addressLine2 = rs.getString("addressLine2");
                String state = rs.getString("state");
                String country = rs.getString("country");
                String postalCode = rs.getString("postalCode");

                report.append(String.format("ID: %d, Name: %s, City: %s, Address: %s, %s, %s, %s, PostalCode: %s%n",
                        id, name, city, addressLine1, addressLine2, state, country, postalCode));
                hasResults = true;
            }

            if (hasResults) {
                report.toString();
            } else {
                report.append("No data available for the report.");
            }
        } catch (SQLException ex) {
            // Log and show error message if there is an exception
            report.append("Error generating report: " + ex.getMessage()+ "Error");
        }

        return report.toString();
    }

    /**
     * @param generate
     */
    public static String generateReportByPayHistory() {
        StringBuilder report = new StringBuilder("Report by Pay History:\n\n");
        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to fetch payroll details
            String sql = """
                    SELECT payID, pay_date, earnings, fed_tax, fed_med, fed_SS, 
                           state_tax, retire_401k, health_care, empid
                    FROM employeedata.payroll
                    ORDER BY pay_date DESC
                    """;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
    
            // Build the report
            while (rs.next()) {
                int payID = rs.getInt("payID");
                Date payDate = rs.getDate("pay_date");
                double earnings = rs.getDouble("earnings");
                double fedTax = rs.getDouble("fed_tax");
                double fedMed = rs.getDouble("fed_med");
                double fedSS = rs.getDouble("fed_SS");
                double stateTax = rs.getDouble("state_tax");
                double retire401k = rs.getDouble("retire_401k");
                double healthCare = rs.getDouble("health_care");
                int empId = rs.getInt("empid");
    
                report.append(String.format("""
                        Pay ID: %d
                        Employee ID: %d
                        Pay Date: %s
                        Earnings: %.2f
                        Federal Tax: %.2f
                        Federal Medicare: %.2f
                        Federal Social Security: %.2f
                        State Tax: %.2f
                        Retirement 401k: %.2f
                        Health Care: %.2f
                        ---------------------------------------
                        """, payID, empId, payDate, earnings, fedTax, fedMed, fedSS, stateTax, retire401k, healthCare));
            }
    
        } catch (SQLException ex) {
            // Handle SQL exceptions
            report.append("Error generating report: " + ex.getMessage()+ "Error");
        }

        return report.toString();

}
}
