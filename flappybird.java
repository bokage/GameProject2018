/*
* APCS Kumar Final
* Flappy Bird remake 
* 
* needs Render.java class
*  
*/

/*TO-DO LIST
*
*-Fix bug where score counter increases by 2 occasionally
*-Press button to start game > game is frozen in beginning
*-Press button to restart game
*-Make bird jumps 'smoother'
*-Make Score counter stay on screen with a High Score counter
*
*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Random;

public class flappybird implements ActionListener, KeyListener{

	public static final int WIDTH = 800; // edit to change window dimensions
	public static final int HEIGHT = 900;
	public static final int PIPESPEED = 13; // speed of pipes
	public static final int PIPEGAP = 325; // gap between top and bottom halves of pipe

	public static flappybird fb;

	public Render r;

	public int score = 0;

	public int ticks;
	public int movement;
	public boolean die;
	public boolean start = true;

	public Rectangle bird;
	public ArrayList<Rectangle> pipe;


	public flappybird(){ // constructor
		JFrame frame = new JFrame("Flappy Bird"); // names window
		Timer timer = new Timer(20, this);	// refreshes render (i think)

		r = new Render(); // initialize Render object. renders game graphics

		frame.add(r); // adds Render to JFrame instance
		frame.addKeyListener(this); // adds KeyListener to JFrame instance
		frame.setVisible(true);	// window is visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // shuts down program when window closes
		frame.setSize(WIDTH, HEIGHT); // sets dimensions
		frame.setResizable(false); // cannot change size

		bird = new Rectangle(WIDTH/2 - 20, HEIGHT/2 - 20, 20, 20); // creates bird 
		pipe = new ArrayList<Rectangle>();

		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);

		timer.start();
	}

	public void keyPressed(KeyEvent e){ // "jumps"
		bird.y -= 150;
	}

	public void keyReleased(KeyEvent e){ // KeyListener interface method

	}

	public void keyTyped(KeyEvent e){ // KeyListener interface method

	}

	public void actionPerformed(ActionEvent a){ // necessary method from ActionListener interface
		int pipeSpeed = PIPESPEED;
		ticks++;

		if(start && !die){ // only if game is not over
			for(int i = 0; i < pipe.size(); i++){ // moves pipes left
				Rectangle pipes = pipe.get(i);
				pipes.x -= pipeSpeed; // moves left by subtracting pipeSpeed from x-coord of pipe
			}

			if(ticks%2 == 0 && movement < 10){ // increases bird vertical velocity
				movement += 1;
			}

			for(int i = 0; i < pipe.size(); i++){
				Rectangle pipes = pipe.get(i);

				if(pipes.x + pipes.width < 0){ // if pipes reach the left side of the window, remove pipes
					pipe.remove(pipes);

					if(pipes.y == 0){
						addPipe(false);
					}
				}
			}

			bird.y += movement; // "moves" bird by changing vertical velocity

			for(int i = 0; i < pipe.size(); i++){
				Rectangle pipes = pipe.get(i);

				if(bird.x > pipes.x + pipes.width/2 - 10 && bird.x < pipes.x + pipes.width/2 + 10){ // increase score if pass through middle of pipe
					score++;
				}

				if(pipes.intersects(bird)){ // if bird hits pipe, you die!
					die = true;
				}
			}

			if(bird.y < 0 || bird.y > HEIGHT -120){ // if bird exceeds height limit OR hits ground, you die!
				die = true;
			}
		}

		r.repaint(); // paints the changes
	}

	public void addPipe(boolean b){
		int space = PIPEGAP; // the gap between top and bottom half of pipes
		int width = 100; // pipe width
		int height = 50 + (int)(500*Math.random()); // pipe height is random

		if(b){ // if this is first pipe
			pipe.add(new Rectangle(WIDTH + width + pipe.size()*300, HEIGHT - height - 120, width, height)); // bottom half of pipe
			pipe.add(new Rectangle(WIDTH + width + (pipe.size()-1)*300, 0, width, HEIGHT - height - space)); // top half of pipe
		}
		else{ // following pipes
			pipe.add(new Rectangle(pipe.get(pipe.size()-1).x + 800, HEIGHT - height - 120, width, height));
			pipe.add(new Rectangle(pipe.get(pipe.size()-1).x, 0, width, HEIGHT - height - space));
		}


	}

	public void paintPipe(Graphics g, Rectangle pipe){ // paints pipes dark green

		g.setColor(Color.GREEN.darker().darker());
		g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);

	}

	public void repaint(Graphics g){ // paints background: sky-blue, ground-orange, grass-green, bird-red
		
		g.setColor(Color.CYAN.darker()); // sky
		g.fillRect(0, 0, WIDTH, HEIGHT);

		/*
		g.setColor(Color.ORANGE); // ground
		g.fillRect(0, HEIGHT-120, WIDTH, 120);
		*/

		g.setColor(Color.GREEN); // grass
		g.fillRect(0, HEIGHT-120, WIDTH, 120);

		g.setColor(Color.RED); // bird
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		for(int i = 0; i < pipe.size(); i++){ // paints pipes by calling paintPipe method
			Rectangle pipes = pipe.get(i);
			paintPipe(g, pipes);
		}

		g.setColor(Color.RED.darker());
		g.setFont(new Font("Monotype Corsiva", Font.PLAIN, 120)); // start and death font type and size

		if(!die){ // comment out for now 5/11/18
			//g.drawString("PRESS TO START GAME", 100, 300);
		}
		else{
			g.drawString("YOU DIED!", WIDTH/3 - 50, HEIGHT/2 - 20);
		}

		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 60));	
		g.drawString("Score: " + Integer.toString(score/2), 10, 60); // score/2 because the counter increases by 2 for some reason...
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		g.drawString("CLOSED ALPHA VERSION", 1335, 25);
	}
	
	public static void main(String[] args){ 
		fb = new flappybird();
	}


}