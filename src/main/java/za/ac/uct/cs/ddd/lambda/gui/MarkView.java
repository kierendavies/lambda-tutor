/*
 *
 *
 */

package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarkView extends ContentWindow {
    JTextArea feedbackArea;
    JRadioButton normalButton;
    JRadioButton applicativeButton;
    ButtonGroup group;
    JButton markButton, browseButton;
    JTextField locationField;

    String fileName;


    public MarkView(GUI parent) {
        super(parent);
        //super(parentFrame);

        //Create the JTextField
        locationField = new JTextField("<<File or folder name here. >>");

        //GridBag constraints for JTextField
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 30;
        gbc.insets = new Insets(10, 10, 0, 10);


        //Add JTextField to context
        add(locationField, gbc);


        //Create the JTextArea
        feedbackArea = new JTextArea(5, 20);

        //GridBag constraints for JTextArea
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.weighty = 5;
        gbc.insets = new Insets(10, 10, 10, 10);


        //Add JTextArea to context pane
        add(feedbackArea, gbc);


        // Create radio button panel
        JPanel radioPane = new JPanel(new GridLayout(0, 1));


        // Create radio buttons
        normalButton = new JRadioButton("Normal Form");
        applicativeButton = new JRadioButton("Applicative Form");
        normalButton.setFont(new Font("Serif", Font.PLAIN, 14));
        applicativeButton.setFont(new Font("Serif", Font.PLAIN, 14));


        //Create and set up button group
        group = new ButtonGroup();
        group.add(normalButton);
        group.add(applicativeButton);

        //Add button to panel
        radioPane.add(normalButton);
        radioPane.add(applicativeButton);


        //GridBag constraints for radio pane
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 5;

        //Add radio pane to context pane
        add(radioPane, gbc);


        //Create "mark" JButton
        markButton = new JButton("Mark");

        //GridBag constraints for JButton
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        add(markButton, gbc);



        //Create "mark" JButton
        browseButton = new JButton("Browse");
       // browseButton.addActionListener(this);


        //GridBag constraints for JButton
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        add(browseButton, gbc);




    }

    void setFileName(String file){
        locationField.setText(fileName);

    }

     String getFileName  (){
        return locationField.getText();
    }

    void addBrowseListener(ActionListener browseListener){
        browseButton.addActionListener(browseListener);

    }


    //@Override
  /*  public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            System.out.println("Yes");
            int returnVal = fileChooser.showOpenDialog(Marker.this);


        }

    }*/
}


