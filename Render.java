import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Render extends JPanel{
	
	private static final long serialVersionUID = 1L; // i have no idea what this is

	protected void paintComponent(Graphics g){ // override paintComponent
		super.paintComponent(g);

		flappybird.fb.repaint(g); 
	}

}