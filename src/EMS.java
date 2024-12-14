import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class EMS {
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
            addDialog.add(ssnnum);

            JButton submit = new JButton("Submit");
            addDialog.add(submit);

            submit.addActionListener(submitEvent -> {
                try {
                    String first = fname.getText();
                    String last = lname.getText();
                    String empEmail =  email.getText();
                    String date =  hireDate.getText();
                    Double money = Double.parseDouble(salary.getText());
                    String empSSN = ssnnum.getText();

                    String msg = AddDeleteEmp.AddEmployee(money, first, last, empEmail, date, empSSN);

                    JOptionPane.showMessageDialog(addDialog, msg);
                    EmployeeSearch.updateEmployeeListDisplay(emplist);
                    addDialog.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addDialog, "Error Adding Employee: " + ex.getMessage());
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
                try  {

                    String SSN = ssnField.getText();
                    String msg = AddDeleteEmp.DeleteEmployee(SSN);

                    JOptionPane.showMessageDialog(deleteDialog, msg);
                    EmployeeSearch.updateEmployeeListDisplay(emplist);
                    deleteDialog.dispose();
                } catch (Exception ex) {
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
        generateReportsButton.addActionListener(e -> reportsPopup(app, emplist));

        // Search panel contents
        search.add(bar);
        search.add(name);
        search.add(ssn);
        search.add(empid);
        search.add(clear);

        clear.addActionListener(clearlist -> {
            EmployeeSearch.updateEmployeeListDisplay(emplist);

        
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
        EmployeeSearch.updateEmployeeListDisplay(emplist);

        
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

        JDialog empInfo = new JDialog(parent, "Update Employee", true);
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(empInfo, "Something has gone wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                field.setText(result);
                

            }
          
        });


        empInfo.setVisible(true);

    }

    public static void reportsPopup(JFrame parent, JTextArea field) {
        JDialog generate = new JDialog(parent, "Generate Report", true);
        generate.setLayout(new FlowLayout());
        generate.setSize(400, 200);

        JButton byTitle = new JButton("By Title");
        JButton byDivision = new JButton("By Division");
        JButton byPayHistory = new JButton("By Pay History");


        byTitle.addActionListener(new ActionListener() {
            String result = "";
            public void actionPerformed(ActionEvent e){

                try {
                    // Call the method to generate report
                    result = Generate.generateReportByTitle();
                    //error 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(generate, "Something has gone wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                field.setText(result);
            }
          
        });

        byDivision.addActionListener(new ActionListener() {
            String result = "";
            public void actionPerformed(ActionEvent e){

                try {

                    result = Generate.generateReportByDivision();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(generate, "Something has gone wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                field.setText(result);
            }
          
        });

        byPayHistory.addActionListener(new ActionListener() {
            String result = "";
            public void actionPerformed(ActionEvent e){

                try {
                    result = Generate.generateReportByPayHistory();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(generate, "Something has gone wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                field.setText(result);
            }
        });

        generate.add(byTitle);
        generate.add(byDivision);
        generate.add(byPayHistory);

        generate.setVisible(true);
    }
}

