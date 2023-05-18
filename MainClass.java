package graphicProject;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import uk.ac.leedsbeckett.oop.LBUGraphics;


public class MainClass extends LBUGraphics{

	public static void main(String[] args) {
		new GraphicsSystem();
//		new Imagination();
	}
	
	@Override
	public void processCommand(String arg0) {
		
	}

}



class GraphicsSystem extends LBUGraphics implements ActionListener{
	
	int width = getWidth();
	int height = getHeight();
	
	private String command;
	private JFrame MainFrame;
	ArrayList<String> commands;
	
	
	private int showWarningMessage() {
		String[] buttonLabels = new String[] {"Yes", "No", "Cancel"};
		String defaultOption = buttonLabels[0];
		Icon icon = null;
 
		return JOptionPane.showOptionDialog(this,
				"There's still something unsaved.\n" +
						"Do you want to save before exiting?",
						"Warning",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);    
    }
 
	 private void handleClosing() {
		 if (commands.size() > 0) {
	            int answer = showWarningMessage();
	             
	            switch (answer) {
	                case JOptionPane.YES_OPTION:
	                    System.out.println("Save and Quit");
	                    processCommand("save");
	                    this.MainFrame.dispose();
	                    break;
	                     
	                case JOptionPane.NO_OPTION:
	                    System.out.println("Don't Save and Quit");
	                    this.MainFrame.dispose();
	                    break;
	                     
	                case JOptionPane.CANCEL_OPTION:
	                    System.out.println("Don't Quit");
	                    break;
	            }
	        } else {
	            this.MainFrame.dispose();
	        } 
	 }
	
	GraphicsSystem(){
		System.out.println(width+" "+height);
		this.MainFrame = new JFrame();   
		this.MainFrame.setLayout(new FlowLayout()); 
		this.MainFrame.add(this);                                   
		this.MainFrame.pack();
		this.MainFrame.setVisible(true);
		super.about(); // calls the about method of LBU graphics which does the simple animation.
		penDown();
		
		// The initializaion of arraylist.
		this.commands = new ArrayList<String>();
		
		// Adding window listener to the JFrame.
		this.MainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.MainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				handleClosing();
			}
		});
	}
	
	
	
	// Check parameter for those commands which takes exactly one parameter.
     int checkParam(String input){
        int parameter = 0;
        String[] splitParts = input.split(" ");
        
        if (splitParts.length == 1){
        	JOptionPane.showMessageDialog(this.MainFrame, "Missing Parameter!", "Error!", JOptionPane.ERROR_MESSAGE);
            System.out.println("Missing parameter.");
            return 0;
        }
        
        if(splitParts.length > 2){
        	JOptionPane.showMessageDialog(this.MainFrame, "Given method take only one parameter.", "Error!", JOptionPane.ERROR_MESSAGE);
            System.out.println("Given method take only one parameter.");
            return 0;
        }
        
        try {
			parameter = Integer.parseInt(splitParts[1]);
			if (parameter < 0){
				JOptionPane.showMessageDialog(this.MainFrame, "Negative value for parameter.", "Error!", JOptionPane.ERROR_MESSAGE);
			    System.out.println("Negative value for parameter.");
			    return 0;
			}
			System.out.println(parameter);
		 }
		 catch(NumberFormatException e){
			 JOptionPane.showMessageDialog(this.MainFrame, "No numeric data for parameter.", "Error!", JOptionPane.ERROR_MESSAGE);
			 System.out.println("No numeric data for parameter.");
		 }
		 return parameter;
    }
	
     
     // Check parameters for those command which takes exactly three parameter.
     public int[] checkThreeParameter(String input){
         int[] arr = new int[3];
         
         String[] split = input.split(" ");
         // here after all the parameter is separated and converted in to integer type check all the possible errors.
         
         if(split.length < 4){
        	 JOptionPane.showMessageDialog(this.MainFrame, "Missing Parameter!", "Error!", JOptionPane.ERROR_MESSAGE);
             System.out.println("Parameter missing.");
             return null;
         }
         
         if(split.length > 4){
        	 JOptionPane.showMessageDialog(this.MainFrame, "Given method take three parameters.", "Error!", JOptionPane.ERROR_MESSAGE);
             System.out.println("more number of parameter given.");
             return null;
         }
         
         try{
             int para1 = Integer.parseInt(split[1]);
             int para2 = Integer.parseInt(split[2]);
             int para3 = Integer.parseInt(split[3]);
             
             if(para1 < 0 || para2 < 0 || para3 < 0){
            	 JOptionPane.showMessageDialog(this.MainFrame, "Negative value for parameter.", "Error!", JOptionPane.ERROR_MESSAGE);
                 System.out.println("Given parameter is negative.");
                 return null;
             }
             else {
            	 arr[0] = para1;
                 arr[1] = para2;
                 arr[2] = para3;
             }
         }
         catch(NumberFormatException e){
        	 JOptionPane.showMessageDialog(this.MainFrame, "No numeric data for parameter.", "Error!", JOptionPane.ERROR_MESSAGE);
             System.out.println("No numeric data for parameter.");
         } 
         return arr;
     }
	

	@Override
	public void processCommand(String commandWithParameter) {
		commands.add(commandWithParameter);
		
		
	// check if textbox is empty or not.
		if (commandWithParameter.length() <= 0) {
			JOptionPane.showMessageDialog(this.MainFrame,"Please enter command!");
			return;
		}
		
		String[] splitParts = commandWithParameter.split(" ");
		
		command = commandWithParameter.toLowerCase();
//		System.out.println(command);
		System.out.println(splitParts.length);
		
		
		if(command.equals("about")) {
			about();
		}
		
		else if(command.equals("penup")) {
			penUp();
			displayMessage("pen is up");
		}
		
		else if(command.equals("pendown")) {
			penDown();
			displayMessage("pen is down");
		}
		
		else if(command.startsWith("turnleft") && "turnleft".length() == splitParts[0].length()){	
			turnLeft(checkParam(command));
		}
		
		else if(command.startsWith("turnright") && "turnright".length() == splitParts[0].length()){	
			turnRight(checkParam(command));
		}
		
		else if(command.startsWith("forward") && "forward".length() == splitParts[0].length()){	
			int distance = checkParam(command);
			
			int currentXPos = getxPos();
			int currentYPos = getyPos();
			
			int newXpos = currentXPos + distance;
			int newYpos = currentYPos + distance;
			
			if(newXpos < 0 || newXpos > width || newYpos < 0 || newYpos > height) {
				JOptionPane.showMessageDialog(this.MainFrame, "The given paramter will move the turtle out of panel.", "Error!", JOptionPane.ERROR_MESSAGE);
				System.out.println("The given paramter will move the turtle out of panel.");
			}
			else {
				forward(distance);
			}
		}
		
		else if(command.startsWith("backward") && "backward".length() == splitParts[0].length()) {
			int distance = checkParam(command);
			
			int currentXPos = getxPos();
			int currentYPos = getyPos();
			
			int newXpos = currentXPos + distance;
			int newYpos = currentYPos + distance;
			
			if(currentXPos + distance > width || currentXPos - distance < 0 || currentYPos + distance > height || currentYPos - distance < 0) {
				JOptionPane.showMessageDialog(this.MainFrame, "The given paramter will move the turtle out of panel.", "Error!", JOptionPane.ERROR_MESSAGE);
				System.out.println("The given paramter will move the turtle out of panel.");
			}
			else {
				forward(-distance);
			}
		}
		
		else if(command.equals("black")) {
			setPenColour(Color.black);
			displayMessage("pen color changed to black");
		}
		
		else if(command.equals("green")) {
			setPenColour(Color.green);
			displayMessage("pen color changed to green");
		}
		
		else if(command.equals("red")) {
			setPenColour(Color.red);
			displayMessage("pen color changed to red");
		}
		
		else if(command.equals("white")) {
			setPenColour(Color.white);
			displayMessage("pen color changed to white");
		}
		
		else if(command.equals("reset")) {
//			penDown();
			setPenColour(Color.red);
			setStroke(1);
			reset();
			penDown();
		}
		
		else if(command.equals("clear")) {
			clear();
		}
		else if(command.startsWith("square") && "square".length() == splitParts[0].length()) {
			drawSquare(checkParam(command));
		}
		else if(command.startsWith("penwidth") && "penwidth".length() == splitParts[0].length()) {
			setSize(checkParam(command));
			displayMessage("Stroke size changed.");
		}
		else if (command.startsWith("pencolor") && "pencolor".length() == splitParts[0].length()){
            penColor();
        }
		else if (command.startsWith("triangle") && "triangle".length() == splitParts[0].length()) {
			if(splitParts.length == 2) {
				drawTriangle(checkParam(command));
			}
			else if(splitParts.length == 4) {
				scaleneTriangle();
			}
			else {
				JOptionPane.showMessageDialog(this.MainFrame, "Triangle command takes either 1 or 3 number of parameters.", "Error!", JOptionPane.ERROR_MESSAGE);
				System.out.println("Invalid");
			}
		}
		else if (command.equals("save")) {
			saveCommand();
			saveImage();
		}
		else if (command.equals("load")) {
			loadImage();
			try {
				Thread.sleep(4000);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			loadCommand();
		}
		else if (command.equals("name")) {
			name();
			reset();
		}
		else if (command.equals("shape")) {
			triangle();
			reset();
		}
		else {
			JOptionPane.showMessageDialog(this.MainFrame, "Invalid Command!", "Error!", JOptionPane.ERROR_MESSAGE);
			System.out.println("Invalid Command.");
		}
		
	}
	
	
	public void about() {
		super.about();
		displayMessage("Biliyas Maharjan.");
	}
	
	
	public void drawSquare(int distance) {
		
		int xPosition = getxPos();
		int yPosition = getyPos();
		
		drawLine(Color.red, xPosition, yPosition, xPosition + distance, yPosition);//topside
		drawLine(Color.red, xPosition + distance, yPosition, xPosition + distance, yPosition + distance);// right side
		drawLine(Color.red, xPosition + distance, yPosition + distance, xPosition, yPosition + distance); // bottom side
		drawLine(Color.red, xPosition, yPosition + distance, xPosition, yPosition); // left side
		turnRight(0);
	}
	
	
	public void setSize(int size){
	    setStroke(size); 
	    System.out.println(size);
	}
	
	
	public void penColor() {
		int[] array = checkThreeParameter(command);
		
		if(array != null) {
			int red = array[0];
			int green = array[1];
			int blue = array[2];
			
			if(red > 255 || green > 255 || blue > 255) {
            	JOptionPane.showMessageDialog(this.MainFrame, "RGB format takes value only up to 255.", "Error!", JOptionPane.ERROR_MESSAGE);
                System.out.println("RGB format takes value only up to 255.");
                return; 
            }
			else {
				Color c = new Color(red, green, blue);
				setPenColour(c);
			}
		}
	}
	
	
	public void drawTriangle(int length) {
		penDown();
		turnRight(90);
		forward(length);
		turnRight(120);
		forward(length);
		turnRight(120);
		forward(length);
	}
	
	
	public void saveCommand() {
		try {
			File f = new File("command.txt");
			FileWriter fw = new FileWriter(f, true);
			for(String command: commands) {
				if(command.equals("load")){
					continue;
				}
				else if(command.equals("save")) {
					continue;
				}
				else {
					fw.write(command+"\n");
				}
			}
            fw.close();
            commands.clear();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void saveImage(){
    	File outputfile = new File("image.png");
    	try{
    		ImageIO.write(getBufferedImage(), "png", outputfile);
    		
    	}
    	catch (IOException e){
    		e.printStackTrace();
    	}
	}
	 
	
	public void loadCommand() {
		try {
			File file = new File("C:\\Users\\97798\\eclipse-workspace\\graphicProject\\command.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String command = scan.nextLine();
                System.out.println(">>>"+command);
                processCommand(command);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	
	public void loadImage(){
		BufferedImage image;
		try{
			image = ImageIO.read(new File ("image.png"));
			setBufferedImage(image);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		repaint();
    }
	
	
	// Create a pattern.
	public void triangle() {
		for(int i = 0; i <= 11; i++) {
			penDown();
			drawTriangle(90);
			
		}
	}
	
	
	public void scaleneTriangle() {
		int[] arr = new int[3];
		
		// calling the checkThreeParameter method to get the parameters.
		arr = checkThreeParameter(command);
		
		System.out.println(arr[0]);
		System.out.println(arr[1]);
		System.out.println(arr[2]);
		
		double a, b, c;
	    double cosA, cosB, cosC;
	    
	    if(arr != null) {
	    	a = arr[0];
			b = arr[1];
			c = arr[2];
			
			if(a + b > c && b + c > a && c + a > b) {
				// calculation of angle in radiant.
				cosA=  (c*c + b*b - a*a) / (2*b*c);
				System.out.println(cosA);
				// and this is the conversion of radiant to degree.
			    double angleA = Math.toDegrees(Math.acos(cosA));
			    System.out.println("Angles>>>");
			    System.out.println(angleA);
			    
			    cosB =  (a*a + c*c - b*b) / (2*a*c);
			    double angleB = Math.toDegrees(Math.acos(cosB));
			    System.out.println(angleB);
			    
			    cosC =  (a*a + b*b - c*c) / (2*a*b);
			    double angleC = Math.toDegrees(Math.acos(cosC));
			    System.out.println(angleC);
			    
			    int len1 = (int) a;
			    int len2 = (int) b;
			    int len3 = (int) c;	 
			    
			    angleA = 180 - angleA;
			    angleB = 180 - angleB;
			    angleC = 180 - angleC;
			    
			    int angA = (int) angleA;
			    int angB = (int) angleB;
			    int angC = (int) angleC;
			    
			    penDown();
			    forward(len3);
			    turnRight(angB);
			    forward(len1);
				turnRight(angC);
				forward(len2);
				// turnRight(angC);
			}
			else {
				JOptionPane.showMessageDialog(this.MainFrame, "Triangle inequality theorem not fulfilled.", "Error!", JOptionPane.ERROR_MESSAGE);
				System.out.println("Triangle inequality theorem not fulfilled.");
			}
	    }	
	}
	
	
	public void name() {
    	clear();
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	
    	//For B
    	setxPos(30);
    	setyPos(90);
    	forward(200);
    	
    	setxPos(80);
    	setyPos(140);
    	circle(50);
    	setxPos(80);
    	setyPos(250);
    	circle(50);
    	//For I
    	setxPos(180);
    	setyPos(100);
    	forward(179);
    	
    	setxPos(150);
    	setyPos(90);
    	turnLeft();
    	forward(69);
    	
    	setxPos(150);
    	setyPos(290);
    	forward(69);
    	
    	//for L
    	reset();
    	setxPos(260);
    	setyPos(90);
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	forward(200);
    	setxPos(260);
    	setyPos(290);
    	turnLeft();
    	forward(69);
    	
    	//for I again
    	reset();
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	setxPos(380);
    	setyPos(100);
    	forward(182);
    	
    	setxPos(350);
    	setyPos(90);
    	turnLeft();
    	forward(69);
    	
    	setxPos(355);
    	setyPos(290);
    	forward(69);
    	
    	//for Y
    	reset();
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	setxPos(450);
    	setyPos(90);
    	turnRight(330);
    	forward(90);
    	
    	setxPos(555);
    	setyPos(90);
    	turnLeft(300);
    	forward(90);
    	
    	reset();
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	setxPos(503);
    	setyPos(173);
    	forward(120);
    	
    	//for A
    	reset();
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	setxPos(550);
    	pointTurtle(15);
    	
    	setxPos(550);
    	setyPos(295);
    	forward(210);
    	
    	turnLeft(207);
    	forward(207);
    	
    	
    	turnLeft();
    	setxPos(580);
    	setyPos(200);
    	forward(60);
    	
    	
    	//for S
    	reset();
    	setStroke(10);
    	setPenColour(Color.orange);
    	penDown();
    	setxPos(750);
    	setyPos(90);
    	turnRight(53);
    	forward(100);
    	
    	turnLeft(111);
    	forward(100);
    	
    	turnRight(120);
    	forward(100);
    	
    }
}



class Imagination extends JFrame implements KeyListener, ActionListener{
	
	private JLabel label;
	private JLabel label2; 
	private Timer timer;
	
	private ImageIcon icon;
	private ImageIcon icon2;
	
	int xlabel, ylabel;
	int labelWidth = 100;
	int labelHeight = 100;
	
	Random random;
	int xlabel2, ylabel2;
	
	Imagination(){
		random = new Random();
		xlabel2 = random.nextInt(600);
		ylabel2 = random.nextInt(600);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		this.addKeyListener(this);
		
		label = new JLabel();
		label.setBounds(0, 580, labelWidth, labelHeight);
		icon = new ImageIcon("spaceship-1.png");
		label.setIcon(icon);
		
		label2 = new JLabel();
		label2.setBounds(xlabel2, ylabel2, 100, 100);
		icon2 = new ImageIcon("saturn.png");
		label2.setIcon(icon2);
		
		this.getContentPane().setBackground(Color.black);
		this.add(label);
		this.add(label2);
		this.setVisible(true);
		
		timer = new Timer(10, this);
        timer.start();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		switch(e.getKeyChar()) {
		case 'a':
			moveLabel(label.getX()-10, label.getY());
			break;
		case 'd':
			moveLabel(label.getX()+10, label.getY());
			break;
		case 'w':
			moveLabel(label.getX(), label.getY()-10);
			break;
		case 's':
			moveLabel(label.getX(), label.getY()+10);
			break;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		case 37: // left key
			moveLabel(label.getX()-10, label.getY());
			break;
		case 38: // up key
			moveLabel(label.getX(), label.getY()-10);
			break;
		case 39: // right key
			moveLabel(label.getX()+10, label.getY());
			break;
		case 40: // down key
			moveLabel(label.getX(), label.getY()+10);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Released key code: "+e.getKeyCode());
	}
	
	private void moveLabel(int x, int y) {
		if(x >= 0 && y >= 0  && x + labelWidth <= getWidth() && y+labelHeight <= getHeight()) {
			label.setLocation(x, y);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Rectangle bounds1 = label.getBounds();
		Rectangle bounds2 = label2.getBounds();
		
		if(bounds1.intersects(bounds2)) {
			System.out.println("Game finish.");
			collisionDetected();
		}
		
	}
	
	 private void collisionDetected() {
	        timer.stop();
	        JOptionPane.showMessageDialog(this, "Rocket reached its destination.\n Mission Succesfull.");
	        dispose();
	    }
}


