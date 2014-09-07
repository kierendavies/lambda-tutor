package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ContentWindow extends SizeablePanel {
    GridBagConstraints gbc;
    JButton menuButton;
    GUI parent;

    public ContentWindow( GUI par) {
        parent = par;
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();



        menuButton = new JButton("Main menu");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        //gbc.gridheight=2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        menuButton.addActionListener(new menuListener());
        menuButton.setFont(new Font("Serif", Font.PLAIN, 14));
        add(menuButton, gbc);


    }


    class menuListener  implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.setPane(parent.menu);
        }
    }



}
