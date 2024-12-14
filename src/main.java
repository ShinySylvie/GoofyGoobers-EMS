import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class main {
    private static JTextArea emplist; // Declare as a field

    public static void main(String[] args) {
        // Create the main application window
        JFrame app = new JFrame("Employee Management System");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel buttonS1 = new JPanel(); // Container for main buttons
        JPanel search = new JPanel(); // Container for search bar
        JPanel display = new JPanel(); // Container where the list of employees should appear

        // Create buttons
        JButton addEmployeeButton = new JButton("Add Employee");
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        JButton updateEmployeeButton = new JButton("Update Employee");
        JButton updateSalariesButton = new JButton("Update Salaries");
        JButton generateReportsButton = new JButton("Generate Report");
        JButton clear = new JButton("Clear Results");

        JRadioButton name = new JRadioButton("via Name");
        JRadioButton ssn = new JRadioButton("via SSN");
        JRadioButton empid = new JRadioButton("via ID");
        ButtonGroup group = new ButtonGroup();
        group.add(name);
        group.add(ssn);
        group.add(empid);

        // JTextArea to display employee list
        emplist = new JTextArea("List of employees should show up here.");
        emplist.setEditable(false);
        List<String> employees = EmployeeSearch.getAllEmployees(); // Fetch all employees
        if (employees.isEmpty()) {
            emplist.setText("No employees found in the database.");
        } else {
            for (String employee : employees) {
                emplist.append(employee + "\n");
            }
        }
        
        JTextField bar = new JTextField("Type here to search EMS");
        bar.setColumns(50);
        bar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (bar.getText().equals("Type here to search EMS")) {
                    bar.setText("");  // Clears the text when the user clicks on it
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (bar.getText().isEmpty()) {
                    bar.setText("Type here to search EMS");  // Reset placeholder text if empty and not in searchbar
                }
            }
        });
        bar.addActionListener(e -> {
               String searchType = "";
    if (name.isSelected()) {
        searchType = "name";
    } else if (ssn.isSelected()) {
        searchType = "ssn";
    } else if (empid.isSelected()) {
        searchType = "id";
    } else {
        emplist.setText("Please select a search type.");
        return;
    }

    // Get the search value from the text field
    String searchValue = bar.getText();

    // Call the DAO method to search employees
    List<String> results = EmployeeSearch.searchEmployees(searchType, searchValue);

    // Display results in the emplist text area
    emplist.setText(""); // Clear the previous results
    if (results.isEmpty()) {
        emplist.setText("No employees found matching the criteria.");
    } else {
        for (String employee : results) {
            emplist.append(employee + "\n");
        }
    }
        });

        JScrollPane scrollPane = new JScrollPane(emplist);
        display.setLayout(new BorderLayout());
        display.add(scrollPane, BorderLayout.CENTER);

        // Action: Add Employee
        addEmployeeButton.addActionListener(e -> {
            JDialog addDialog = new JDialog(app, "Add Employee", true);
            addDialog.setLayout(new FlowLayout());
            addDialog.setSize(400, 300);

            JTextField fname = new JTextField(20);
            JTextField lname = new JTextField(20);
            JTextField email = new JTextField(20);
            JTextField hireDate = new JTextField(10);
            JTextField salary = new JTextField(10);
            JTextField ssnnum = new JTextField(15);

            addDialog.add(new JLabel("First Name:"));
            addDialog.add(fname);
            addDialog.add(new JLabel("Last Name:"));
            addDialog.add(lname);
            addDialog.add(new JLabel("Email:"));
            addDialog.add(email);
            addDialog.add(new JLabel("Hire Date (YYYY-MM-DD):"));
            addDialog.add(hireDate);
            addDialog.add(new JLabel("Salary:"));
            addDialog.add(salary);
            addDialog.add(new JLabel("SSN:"));
            addDialog.add(ssn);

            JButton submit = new JButton("Submit");
            addDialog.add(submit);

            submit.addActionListener(submitEvent -> {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO employees (fname, lname, email, hiredate, salary, ssn) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, fname.getText());
                    stmt.setString(2, lname.getText());
                    stmt.setString(3, email.getText());
                    stmt.setString(4, hireDate.getText());
                    stmt.setDouble(5, Double.parseDouble(salary.getText()));
                    stmt.setString(6, ssnnum.getText());

                    int rowsInserted = stmt.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(addDialog, "Employee added successfully!");
                        fetchAndDisplayEmployees(emplist); // Refresh employee list
                        addDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(addDialog, "Error adding employee.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(addDialog, "Error adding employee: " + ex.getMessage());
                }
            });

            addDialog.setVisible(true);
        });

        // Action: Delete Employee
        deleteEmployeeButton.addActionListener(e -> {
            JDialog deleteDialog = new JDialog(app, "Delete Employee", true);
            deleteDialog.setLayout(new FlowLayout());
            deleteDialog.setSize(300, 150);

            JTextField ssnField = new JTextField(15);
            deleteDialog.add(new JLabel("Enter SSN:"));
            deleteDialog.add(ssnField);

            JButton deleteButton = new JButton("Delete");
            deleteDialog.add(deleteButton);

            deleteButton.addActionListener(deleteEvent -> {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "DELETE FROM employees WHERE ssn = ?";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, ssnField.getText());

                    int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(deleteDialog, "Employee deleted successfully!");
                        fetchAndDisplayEmployees(emplist); // Refresh employee list
                        deleteDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(deleteDialog, "No employee found with the given SSN.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(deleteDialog, "Error deleting employee: " + ex.getMessage());
                }
            });

            deleteDialog.setVisible(true);
        });

        // Action: Update Salaries
        updateSalariesButton.addActionListener(e -> salaryPopup(app, emplist));

                // Action: Update Salaries
        updateEmployeeButton .addActionListener(e -> updateEmpPopup(app, emplist));

        // Action: Generate Reports
        generateReportsButton.addActionListener(e -> reportsPopup(app));

        // Search panel contents
        search.add(bar);
        search.add(name);
        search.add(ssn);
        search.add(empid);
        search.add(clear);

        clear.addActionListener(clearlist -> {
            fetchAndDisplayEmployees(emplist);
        });

        // Button panel contents
        buttonS1.add(addEmployeeButton);
        buttonS1.add(deleteEmployeeButton);
        buttonS1.add(updateEmployeeButton);
        buttonS1.add(updateSalariesButton);
        buttonS1.add(generateReportsButton);

        // Add all panels to the main panel
        mainPanel.add(buttonS1);
        mainPanel.add(search);
        mainPanel.add(display);

        // Add mainPanel to application
        app.add(mainPanel);
        app.setSize(1000, 1000);
        app.setVisible(true);

        // Fetch and display employee list on load
        fetchAndDisplayEmployees(emplist);
    }

    public static void fetchAndDisplayEmployees(JTextArea emplist) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT empid, fname, lname, email FROM employees";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder result = new StringBuilder("Employee List:\n");
            while (rs.next()) {
                int empid = rs.getInt("empid");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");

                result.append(String.format("ID: %d, Name: %s %s, Email: %s%n", empid, fname, lname, email));
            }

            emplist.setText(result.toString());
        } catch (SQLException ex) {
            emplist.setText("Error fetching employees: " + ex.getMessage());
        }
    }

    public static void salaryPopup(JFrame parent, JTextArea field) {
        JDialog salaryInfo = new JDialog(parent, "Update Salary", true);
        salaryInfo.setLayout(new FlowLayout());
        salaryInfo.setSize(400, 200);

        JLabel upperBoundLabel = new JLabel("Insert Upper Bound:");
        JTextField upperBound = new JTextField(20);

        JLabel lowerBoundLabel = new JLabel("Insert Lower Bound:");
        JTextField lowerBound = new JTextField(20);

        JLabel percentageLabel = new JLabel("Insert Percentage:");
        JTextField percentage = new JTextField(20);

        salaryInfo.add(upperBoundLabel);
        salaryInfo.add(upperBound);
        salaryInfo.add(lowerBoundLabel);
        salaryInfo.add(lowerBound);
        salaryInfo.add(percentageLabel);
        salaryInfo.add(percentage);

        JButton updateButton = new JButton("Update Salary");
        salaryInfo.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            String result = "";
            public void actionPerformed(ActionEvent e){
                System.out.println("You've pressed the update button!"); //debug message
                try {
                    // Parse user inputs
                    double lower = Double.parseDouble(lowerBound.getText());
                    double upper = Double.parseDouble(upperBound.getText());
                    double percent = Double.parseDouble(percentage.getText());
            
                    // Call the method to update salaries
                    result = Updater.updateSalary(lower, upper, percent);
                    //error if input isn't a number
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(salaryInfo, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                field.setText(result);
                

            }
          
        });

        salaryInfo.setVisible(true);
    }

    public static void updateEmpPopup(JFrame parent, JTextArea field){

        JDialog empInfo = new JDialog(parent, "Update Salary", true);
        empInfo.setLayout(new FlowLayout());
        empInfo.setSize(400, 400);

        JLabel empidLabel = new JLabel("Input employee ID.");
        JTextField empid = new JTextField(20);

        JLabel fnameLabel = new JLabel("Update First Name:");
        JTextField fname = new JTextField(20);

        JLabel lnameLabel = new JLabel("Update Last Name:");
        JTextField lname = new JTextField(20);

        JLabel emailLabel = new JLabel("Update Email:");
        JTextField email = new JTextField(20);

        JLabel hireDateLabel = new JLabel("Update Hire Date:");
        JTextField hireDate = new JTextField(10);

        JLabel ssnLabel = new JLabel("Update SSN:");
        JTextField ssn = new JTextField(15);

        JLabel instructions = new JLabel("Leave field blank if not updating");

        empInfo.add(empidLabel);
        empInfo.add(empid);
        empInfo.add(instructions);
        empInfo.add(fname);
        empInfo.add(fnameLabel);
        empInfo.add(lname);
        empInfo.add(lnameLabel);
        empInfo.add(email);
        empInfo.add(emailLabel);
        empInfo.add(hireDate);
        empInfo.add(hireDateLabel);
        empInfo.add(ssn);
        empInfo.add(ssnLabel);

        JButton updateButton = new JButton("Update Employee");
        empInfo.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            String result = "";
            public void actionPerformed(ActionEvent e){
                System.out.println("You've pressed the update button!"); //debug message
                try {
                    // Parse user inputs
                    int EmpID = Integer.parseInt(empid.getText().trim());
                    String empFname = fname.getText();
                    String empLname = lname.getText();
                    String empEmail = email.getText();
                    String empHiredate = hireDate.getText();
                    String empSsn = ssn.getText();
            
                    // Call the method to update salaries
                    result = Updater.updateEmployee(EmpID, empFname, empLname, empEmail, empHiredate, empSsn);
                    //error if input isn't a number
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(empInfo, "Please enter valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                field.setText(result);
                

            }
          
        });


        empInfo.setVisible(true);

    }

    public static void reportsPopup(JFrame parent) {
        JDialog generate = new JDialog(parent, "Generate Report", true);
        generate.setLayout(new FlowLayout());
        generate.setSize(400, 200);

        JButton byTitle = new JButton("By Title");
        JButton byDivision = new JButton("By Division");
        JButton byPayHistory = new JButton("By Pay History");

        // Add listeners for each button (you can replace the action with your specific
        // report logic)
        byTitle.addActionListener(e -> generateReportByTitle(generate));
        byDivision.addActionListener(e -> generateReportByDivision(generate));
        byPayHistory.addActionListener(e -> generateReportByPayHistory(generate));

        generate.add(byTitle);
        generate.add(byDivision);
        generate.add(byPayHistory);

        generate.setVisible(true);
    }

    public static void generateReportByTitle(JDialog generate) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to fetch job titles
            String sql = "SELECT job_title_id, job_title FROM employeedata.job_titles ORDER BY job_title_id ASC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
    
            // Building the report
            StringBuilder report = new StringBuilder("Report by Title:\n\n");
            while (rs.next()) {
                int jobTitleId = rs.getInt("job_title_id");
                String jobTitle = rs.getString("job_title");
                report.append(String.format("ID: %d, Title: %s%n", jobTitleId, jobTitle));
            }
    
            // Display the report
            JOptionPane.showMessageDialog(generate, report.toString(), "Report by Title",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            // Show error message if SQL exception occurs
            JOptionPane.showMessageDialog(generate, "Error generating report: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void generateReportByDivision(JDialog generate) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Your SQL query for fetching employee division details
            String sql = "SELECT ID, Name, city, addressLine1, addressLine2, state, country, postalCode FROM employeeData.division";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Check if the query returns any results
            StringBuilder report = new StringBuilder("Report by Division:\n");
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
                JOptionPane.showMessageDialog(generate, report.toString(), "Report by Division",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(generate, "No data available for the report.", "No Data",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            // Log and show error message if there is an exception
            System.out.println("SQL Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(generate, "Error generating report: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param generate
     */
    public static void generateReportByPayHistory(JDialog generate) {
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
            StringBuilder report = new StringBuilder("Report by Pay History:\n\n");
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
    
            // Display the report
            JOptionPane.showMessageDialog(generate, report.toString(), "Report by Pay History",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            // Handle SQL exceptions
            JOptionPane.showMessageDialog(generate, "Error generating report: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
}}

