package za.ac.uct.cs.ddd.lambda.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutController {
    private TutView view ;

 public TutController (TutView v){
     view = v;
 }

    class ProblemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}

