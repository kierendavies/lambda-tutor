package za.ac.uct.cs.ddd.lambda.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowController {
    private ContentWindow window;

    public  WindowController(ContentWindow contentWindow){
        window = contentWindow;

        //... Add listeners to the window
        window.addMenuButtonListener(new  mainMenuListener());
    }

     class mainMenuListener implements ActionListener{


         @Override
         public void actionPerformed(ActionEvent e) {
             Window frame = window.parent;
            frame.setContentPane(frame.menu);
         }
     }
}
