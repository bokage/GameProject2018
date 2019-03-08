//previous gameFrame class, not used in final product

import java.awt.*;
import javax.swing.*;

public class gameFrame extends JFrame implements ActionListener{
	
	private final int WIDTH = 1600;
	private final int HEIGHT = 900;

	public gameFrame(){

		super("Flappy Bird"); //sets title
		setLayout(new FlowLayout()); //makes it so that the layout of the window is automatically set

		Timer timer = new Timer(20, this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes program close when you click the X button
		this.setSize(WIDTH, HEIGHT); //sets size
		this.setVisible(true); //lets you see it
		this.setResizable(false); // cannot resize window

		timer.start();
	}

	public void actionPerformed(ActionEvent a){
		this.paint();
	} 

	public void paint(Graphics g){
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	public static void main(String[] args){
		JFrame frame = new gameFrame();

		
	}

}
