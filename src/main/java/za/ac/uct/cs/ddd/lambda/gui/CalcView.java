package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
class CalcView extends ContentWindow {


    public CalcView(Window parent) {
        super(parent);
        Calculator calculator  = new Calculator();

     GridBagConstraints gbc = new GridBagConstraints();
     gbc.gridx =0;
     gbc.gridy  =0;
     gbc.gridwidth =6;
     gbc.gridheight = 3;
     gbc.anchor = GridBagConstraints.FIRST_LINE_START;
     gbc.fill = GridBagConstraints.BOTH;
     gbc.weightx =2;
     gbc.weighty =6;
     gbc.insets = new Insets(10, 10, 20, 10);
      add(calculator ,gbc);
    }


}
