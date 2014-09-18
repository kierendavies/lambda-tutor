package za.ac.uct.cs.ddd.lambda.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MarkController {
    JFileChooser fileChooser;
    private MarkView view;


    public MarkController(MarkView v) {
        view = v;
        v.addBrowseListener(new BrowseListener());

    }

    class BrowseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                view.setFileName(fileChooser.getName(fileChooser.getSelectedFile()));
                // System.out.println(fileChooser.getName(fileChooser.getSelectedFile()));

            }

        }


    }

    class MarkListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(view.getFileName()));
                List<String> steps = new ArrayList<String>();
                String line = null;
                while ((line = input.readLine()) != null) {
                    steps.add(line);

                }
                //Pass arrayList to marker backend


            } catch (java.io.IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}