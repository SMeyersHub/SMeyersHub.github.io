import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * CS 121 Project 1: Traffic Animation
 *
 * Animates a car moving from the left side of the frame
 * to the right side of the frame.
 *
 * @author BSU CS 121 Instructors
 * @author Steven Meyers
 */
@SuppressWarnings("serial")
public class TrafficAnimation extends JPanel
{
	// This is where you declare constants and variables that need to keep their
	// values between calls	to paintComponent(). Any other variables should be
	// declared locally, in the method where they are used.

	/**
	 * A constant to regulate the frequency of Timer events.
	 * Note: 100ms is 10 frames per second - you should not need
	 * a faster refresh rate than this
	 */
	private final int DELAY = 100; //milliseconds

	/**
	 * The anchor coordinate for drawing / animating. All of your vehicle's
	 * coordinates should be relative to this offset value.
	 */
	private int xOffset = 0;

	/**
	 * The number of pixels added to xOffset each time paintComponent() is called.
	 */
	private int stepSize = 10;

	private final Color BACKGROUND_COLOR = Color.black;

	/* This method draws on the panel's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		// Get the current width and height of the window.
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height

		// Fill the graphics page with the background color.
		g.setColor(BACKGROUND_COLOR);
		//Immediately change that background color
		Color skyBlue = new Color(162, 195, 249);
		g.setColor(skyBlue);
		g.fillRect(0, 0, width, height);
		
		//Draw the background mountain.
		int[] xMountain= {width/10,width/4,width/2};
		int[] yMountain= {height, height/10, height};
		Color mountainBrown = new Color(130, 93, 49);
		g.setColor(mountainBrown);
		g.fillPolygon(xMountain, yMountain, 3);
		//Draw the ground
		Color grassGreen = new Color(57, 94, 53);
		g.setColor(grassGreen);
		g.fillRect(0, height/2, width, height/2);
		g.setColor(Color.GRAY);
		g.fillRect(0, height/2, width, height/6);
		
		//Draw the observer.
		Color eyeColor = new Color(247, 243, 234);
		Color skinColor = new Color(242, 223, 181);
		g.setColor(skinColor);
		g.fillOval((4*width)/6, (3*height)/4, width/7, height/5);
		//eyes
		g.setColor(eyeColor);
		g.fillOval((4*width)/6, (8*height)/10, width/18, height/20);
		g.fillOval((9*width)/12, (8*height)/10, width/18, height/20);
		//pupils
		g.setColor(Color.BLACK);
		g.fillOval((8*width)/12, (8*height)/10, width/36, height/40);
		g.fillOval((9*width)/12, (8*height)/10, width/36, height/40);
		//Mouth
		g.fillArc((17*width)/24, (17*height)/20, width/24, height/15, 180, 360);
		
		//Draw text of what the avatar is saying
		String speech = new String("Woah! Its that new cool looking car!");
		g.setFont(new Font("TimesRoman", height/40, width/40));
		g.drawString(speech,(2*width)/4, (3*height)/4);
		// Calculate the new xOffset position of the moving object.
		xOffset  = (xOffset + stepSize) % width;

		// Use width, height, and xOffset to draw your scalable objects
		// at their new positions on the screen
		
		// This draws a green square. Replace it with your own object.
		int carBodyWidth= width/6;
		int carBodyHeight = height/10;
		int yPlacing = height/2;
		
		//Top part of the car
		int carHeadWidth = width/8;
		int carHeadHeight = height/8;
		int yPlacingHead = yPlacing-(carHeadHeight/2);
		
		//Back(left) Wheel
		int wheelWidth = width/20;
		int wheelHeight = height/20;
		int yPlacingWheel = yPlacing+(wheelHeight*2);
		
		//Placing of the Right Wheel
		int xPlacingWheel = xOffset + (wheelWidth*2);
		
		//Drawing the car
		g.setColor(Color.DARK_GRAY);
		g.fillRect(xOffset, yPlacing, carBodyWidth, carBodyHeight);
		g.fillRect(xOffset, yPlacingHead, carHeadWidth, carHeadHeight);
		g.setColor(Color.BLACK);
		g.fillOval(xOffset, yPlacingWheel, wheelWidth, wheelHeight);
		g.fillOval(xPlacingWheel, yPlacingWheel, wheelWidth, wheelHeight);
		
		
		//Draw a pretty looking golden frame around this piece of art
		g.setColor(Color.YELLOW);
		g.drawRect(0, 0, (width*999)/1000, (height*999)/1000);
		
		// Put your code above this line. This makes the drawing smoother.
		Toolkit.getDefaultToolkit().sync();
	}


	//==============================================================
	// You don't need to modify anything beyond this point.
	//==============================================================


	/**
	 * Starting point for this program. Your code will not go in the
	 * main method for this program. It will go in the paintComponent
	 * method above.
	 *
	 * DO NOT MODIFY this method!
	 *
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		// DO NOT MODIFY THIS CODE.
		JFrame frame = new JFrame ("Traffic Animation");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficAnimation());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Constructor for the display panel initializes necessary variables.
	 * Only called once, when the program first begins. This method also
	 * sets up a Timer that will call paint() with frequency specified by
	 * the DELAY constant.
	 */
	public TrafficAnimation()
	{
		// Do not initialize larger than 800x600. I won't be able to
		// grade your project if you do.
		int initWidth = 600;
		int initHeight = 400;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);

		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/**
	 * Create an animation thread that runs periodically.
	 * DO NOT MODIFY this method!
	 */
	private void startAnimation()
	{
		ActionListener timerListener = new TimerListener();
		Timer timer = new Timer(DELAY, timerListener);
		timer.start();
	}

	/**
	 * Repaints the graphics panel every time the timer fires.
	 * DO NOT MODIFY this class!
	 */
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
}
