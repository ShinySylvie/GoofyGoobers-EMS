import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main {

    public static void main(String[] args)
    {
        //the popup of our application
        JFrame app = new JFrame();
        //main container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel buttonS1 = new JPanel(); //container buttons for manipulation
        JPanel search = new JPanel(); //container for search abr
        JPanel Display = new JPanel(); //container where the list of employees should appear
        

        JButton AddEbuttonS1oyee = new JButton("Add Employee");
        JButton DeleteEbuttonS1oyee = new JButton("Delete Employee");
        JButton UpdateEbuttonS1oyee = new JButton("Update Employee");

        JRadioButton name = new JRadioButton("via name");
        JRadioButton ssn = new JRadioButton("via SSN");
        JRadioButton empid = new JRadioButton("via ID");
        ButtonGroup group = new ButtonGroup();
        group.add(name);
        group.add(ssn);
        group.add(empid);

        JTextField temp = new JTextField("List of employees should show up here."); //just sitting here for visual reasons
        
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


        //buttonS1 panel contents
        buttonS1.add(AddEbuttonS1oyee);
        buttonS1.add(DeleteEbuttonS1oyee);
        buttonS1.add(UpdateEbuttonS1oyee);

        //search panel contents
        search.add(bar);
        search.add(name);
        search.add(ssn);
        search.add(empid);

        //display panel contents
        Display.add(temp);
        Display.setLayout(new BorderLayout());  // Center-aligns the text field in the display panel
        Display.add(temp, BorderLayout.CENTER);

        //add all panels to the main panel 
        mainPanel.add(buttonS1);
        mainPanel.add(search);
        mainPanel.add(Display);

        //add mainPanel to application
        app.add(mainPanel);
        app.setSize(1500,1000);
        app.setVisible(true);
    }

}