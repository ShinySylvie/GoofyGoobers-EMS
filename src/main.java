import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main {

    public static void main(String[] args)
    {
        //the popup of our application
        JFrame app = new JFrame("Employee Management System");
        //main container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel buttonS1 = new JPanel(); //container for main buttons
        JPanel search = new JPanel(); //container for search abr
        JPanel Display = new JPanel(); //container where the list of employees should appear
        

        JButton AddEbuttonS1oyee = new JButton("Add Employee");
        JButton DeleteEbuttonS1oyee = new JButton("Delete Employee");
        JButton UpdateEbuttonS1oyee = new JButton("Update Employee");
        JButton UpdateSalaries = new JButton("Update Salaries");
                UpdateSalaries.addActionListener(new ActionListener() {
          
            // Override the actionPerformed() method
            public void actionPerformed(ActionEvent e){
                
                salarypopup(app);

            }
          
        });
        JButton GenerateReports = new JButton("Generate Report");
        GenerateReports.addActionListener(new ActionListener() {
          
            // Override the actionPerformed() method
            public void actionPerformed(ActionEvent e){
                
                reportspopup(app);

            }
          
        });


        JRadioButton name = new JRadioButton("via name");
        JRadioButton ssn = new JRadioButton("via SSN");
        JRadioButton empid = new JRadioButton("via ID");
        ButtonGroup group = new ButtonGroup();
        group.add(name);
        group.add(ssn);
        group.add(empid);

        JTextField emplist = new JTextField("List of employees should show up here."); //just sitting here for visual reasons
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
            String input = bar.getText(); // Get the text
            emplist.setText("Query submitted: "+input);//just testing that pressing enter works
        });


        //buttonS1 panel contents
        buttonS1.add(AddEbuttonS1oyee);
        buttonS1.add(DeleteEbuttonS1oyee);
        buttonS1.add(UpdateEbuttonS1oyee);
        buttonS1.add(UpdateSalaries);
        buttonS1.add(GenerateReports);

        //search panel contents
        search.add(bar);
        search.add(name);
        search.add(ssn);
        search.add(empid);

        //display panel contents
        Display.setLayout(new BorderLayout());  // Center-aligns the text field in the display panel
        Display.add(emplist, BorderLayout.CENTER);

        //add all panels to the main panel 
        mainPanel.add(buttonS1);
        mainPanel.add(search);
        mainPanel.add(Display);

        //add mainPanel to application
        app.add(mainPanel);
        app.setSize(1500,1000);
        app.setVisible(true);



    }


    public static void salarypopup(JFrame parent) {
        // Create a JDialog (a popup window)
        JDialog salaryinfo = new JDialog(parent, "Update Salary", true);
        salaryinfo.setTitle("Update Salary");
        salaryinfo.setSize(400, 200);
        salaryinfo.setLocationRelativeTo(null);
        salaryinfo.setLayout(new FlowLayout());

        // Create labels and text fields
        JLabel upperboundLabel = new JLabel("Insert Upper Bound:");
        JTextField upperbound = new JTextField(20);

        JLabel lowerboundLabel = new JLabel("Insert Lower Bound:");
        JTextField lowerbound = new JTextField(20);

        JLabel percentageLabel = new JLabel("Insert Percentage:");
        JTextField percentage = new JTextField(20);

        // Add labels and text fields to the dialog
        salaryinfo.add(upperboundLabel);
        salaryinfo.add(upperbound);

        salaryinfo.add(lowerboundLabel);
        salaryinfo.add(lowerbound);

        salaryinfo.add(percentageLabel);
        salaryinfo.add(percentage);

        JButton updateButton = new JButton("Update Salary");
        salaryinfo.add(updateButton);


        updateButton.addActionListener(new ActionListener() {
            String result = "";
            public void actionPerformed(ActionEvent e){
                System.out.println("You've pressed the update button!"); //debug message
                try {
                    // Parse user inputs
                    double lower = Double.parseDouble(lowerbound.getText());
                    double upper = Double.parseDouble(upperbound.getText());
                    double percent = Double.parseDouble(percentage.getText());
            
                    // Call the method to update salaries
                    result = SalaryUpdater.updateSalary(lower, upper, percent);
                    //error if input isn't a number
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(salaryinfo, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //text area for result to show up in
                JTextArea updated = new JTextArea();
                updated.setEditable(false);
                salaryinfo.add(updated);

                updated.setText(result);
                

            }
          
        });
        // make salaryinfo visible
        salaryinfo.setVisible(true);
        
    }

    public static void reportspopup(JFrame parent){

        JDialog generate = new JDialog(parent,"Generate Report", true);
        generate.setTitle("Generate Report");
        generate.setSize(400, 200);
        generate.setLocationRelativeTo(null);
        JPanel options = new JPanel();

        JButton ByTitle = new JButton("By Title");
        JButton ByDivision = new JButton("By Division");
        JButton ByPayHistory = new JButton("By Pay Statement History");

        options.add(ByTitle);
        options.add(ByDivision);
        options.add(ByPayHistory);

        generate.add(options);
        generate.setVisible(true);

        

    }


}