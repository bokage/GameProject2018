/*
* APCS Kumar Final
* Flappy Bird remake
*
* needs Render.java class
*
*/

/*TODO:
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
import java.util.ArrayList;
import java.util.Random;

public class flappybird implements ActionListener, KeyListener{

	public static final int WIDTH = 1000, HEIGHT = 900, PIPESPEED = 5, PIPEGAP = 305; // edit to change window dimensions
	// game window width, height, speed of pipes, gap between top and bottom of the pipes
	
	public static flappybird fb;

	public Render r;
	public int score = 0;

	//ticks, movements, etc
	public int ticks = 0;
	public float velocity = 0;
	public boolean die;
	public boolean start = true;
	public static final int frameTime = 10;




	public Rectangle bird;
	public ArrayList<Rectangle> pipe;


	public flappybird(){ // constructor
		JFrame frame = new JFrame("Flappy Bird"); // names window
		Timer timer = new Timer(frameTime, this);	// refreshes render (i think)

		r = new Render(); // initialize Render object. renders game graphics

		frame.add(r); // adds Render to JFrame instance
		frame.addKeyListener(this); // adds KeyListener to JFrame instance
		frame.setVisible(true);	// window is visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // shuts down program when window closes
		frame.setSize(WIDTH, HEIGHT); // sets dimensions
		frame.setResizable(true); // cannot change size

		bird = new Rectangle(WIDTH/2 - 20, HEIGHT/2 - 20, 20, 20); // creates bird
		pipe = new ArrayList<Rectangle>();

		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);

		timer.start();
	}

	public void keyPressed(KeyEvent e){ // "jumps"
		if (e.getKeyCode() == KeyEvent.VK_A) {
	
		}
           
		velocity = -7;
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

			// increases bird vertical velocity
			velocity += 0.287;
			

			for(int i = 0; i < pipe.size(); i++){
				Rectangle pipes = pipe.get(i);

				if(pipes.x + pipes.width < 0){ // if pipes reach the left side of the window, remove pipes
					pipe.remove(pipes);

					if(pipes.y == 0){
						addPipe(false);
					}
				}
			}

			bird.y += velocity; // "moves" bird by changing vertical velocity
			System.out.println(velocity);

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

		//g.setColor(Color.GREEN.darker().darker());
		//g.fillRect(pipe.x-20, pipe.y-20, pipe.width+40, 40); //numbers are sizes+displacement of pipe lip

	}

	public void repaint(Graphics g){ // paints background: sky-blue, ground-orange, grass-green, bird-red

		g.setColor(new Color(81, 192, 201)); // sky
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// GRASS
		g.setColor(Color.GREEN); 
		g.fillRect(0, HEIGHT-120, WIDTH, 120);
		g.setColor(new Color(223, 216, 148));//paints ground
		g.fillRect(0, HEIGHT - 80, WIDTH, 80);

		// BIRD
		g.setColor(new Color(227, 190, 66)); 
		g.fillOval(bird.x, bird.y, 45, 45); //numbers = bird size
		g.setColor(Color.WHITE); //paints eyes
		g.fillOval(bird.x+26, bird.y+4, 18, 18);
		g.setColor(new Color(255, 255, 224));//paints wings
		g.fillOval(bird.x-5, bird.y + 10, 30, 20);
		g.setColor(new Color(255,69,0)); //paints beak
		g.fillRect(bird.x+30, bird.y+25, 20, 12); 
		g.setColor(Color.BLACK); //paints pupil
		g.fillOval(bird.x+36, bird.y+10, 6, 8);
		g.fillRect(bird.x+30, bird.y+30, 20, 2);


		// paints pipes by calling paintPipe method
		for(int i = 0; i < pipe.size(); i++){ 
			Rectangle pipes = pipe.get(i);
			paintPipe(g, pipes);
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Monotype Corsiva", Font.PLAIN, 120)); // start and death font type and size

		if(!die){ // comment out for now 5/11/18
			//g.drawString("PRESS TO START GAME", 100, 300);
		}
		else{
			g.setColor(Color.WHITE); //death screen border
			g.fillRect(18* WIDTH/100 -20, HEIGHT/5 +40, 640, 280);

			g.setColor(new Color(223, 216, 148)); //death screen background
			g.fillRect(18* WIDTH/100 -10, HEIGHT/5 +50, 620, 260);
			
			g.setColor(Color.WHITE); //death text
			g.drawString("YOU DIED!", 18* WIDTH/100, 2* HEIGHT/5);
			g.setFont(new Font("Monaco", Font.PLAIN, 60));
			g.drawString("Score: " + Integer.toString(score/6), 32* WIDTH/100, 3*HEIGHT/6);

		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Monaco", Font.PLAIN, 30));
		g.drawString("Score: " + Integer.toString(score/6), 10, 30); // score/2 because the counter increases by 2 for some reason...

		g.setColor(Color.WHITE);
		g.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		g.drawString("CLOSED ALPHA VERSION", 75 * WIDTH / 100, HEIGHT/20); // closed alpha tag lol
	}

	public static void main(String[] args){
		fb = new flappybird();
	}


}
