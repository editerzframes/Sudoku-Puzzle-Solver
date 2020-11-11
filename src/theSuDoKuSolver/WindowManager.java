package theSuDoKuSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import theSuDoKuSolver.Controller;
import theSuDoKuSolver.Model;
import theSuDoKuSolver.View;

/**
 * <p>
 * Title: WindowManger Class - A component of the JavaFX SuDoKu System
 * </p>
 *
 * <p>
 * Description: A controller object class that implements the GUI window using JavaFX
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 2.00	This is the JavaFX rewrite of a previous Java Swing version from 2008
 */
public class WindowManager{
	/**
	 * These are the class attributes
	 */
	private static int windowWidth;		// Specify the width and
	private static int windowHeight;	// height of the window

	// The attributes define the messages and buttons that establish the 
	// generic portion of the user interface
	private Label msgWindowTitle = new Label("SuDoKu Puzzle Player/Solver");
	private Label msgAuthor = new Label("by Puneet Garg, Sparsh Goel & Deepesh     ");
	
	private Label msgFileName = new Label("Please insert a file name here:");
	private TextField fldFileName = new TextField();
	private Label msgFileFound = new Label("");
	private Button btnReadData = new Button("Load This Puzzle!");
	private Button btnSolvePuzzle = new Button("Solve This Puzzle!");
	public Label msgInvalid = new Label("");

	// This establishes the user's screen size
	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

	// This is the puzzle instance variable
	private Model thePuzzle;
	private View theView;
	private Controller theController;


	// These private variable are used to support the input of the puzzle
	static String fileName;

	
	private Scanner inputScanner;

	/**********
	 * This constructor creates the WindowManager
	 */
	public WindowManager(Stage theStage, Pane theRoot, int ww, int wh) {
		windowWidth = ww;
		windowHeight = wh;
		// Set the window title and terminate on close of the window
		theStage.setTitle("SuDoKu Puzzle System");

		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {System.exit(0);}
		});

		// Establish an instance of the puzzle and specify the portion of the screen
		// that is available for the puzzle to use (the size of the puzzle)
		thePuzzle = new Model();
		theController = new Controller(thePuzzle);
		theView = new View(this, windowWidth, windowHeight, theController, theRoot);
		theController.setView(theView);
		thePuzzle.setView(theView);

		// Request the view to set up the window decoration
		theView.setupWindowDecoration();

		// Set up program and author titles
		setupLabelUI(msgWindowTitle, "Arial", 18, windowWidth, Pos.BASELINE_CENTER, 0, 10);
		setupLabelUI(msgAuthor, "Arial", 12, windowWidth, Pos.BASELINE_RIGHT, 0, 30);

		// Set up the label to tell the user that this field is to be used to specify a file name
		setupLabelUI(msgFileName, "Arial", 14, 200, Pos.BASELINE_LEFT, 30, 
				windowHeight - 155 + 80);

		// Set up the user text field that is to be used to specify the file name
		setupTextUI(fldFileName, "Arial", 14, 220, Pos.BASELINE_LEFT, 30, 
				windowHeight - 155 + 100, true);
		fldFileName.textProperty().addListener((observable, oldValue, newValue) -> {
			checkFileName();
		});

		// Set up the message field that tells the user if the file name matches an actual file
		setupLabelUI(msgFileFound, "Arial", 14, 200, Pos.BASELINE_LEFT, 230, 
				windowHeight - 155 + 80);

		// Set up the message field that tells the user about errors with the puzzle
		setupLabelUI(msgInvalid, "Arial", 14, 200, Pos.BASELINE_LEFT, 230, 
				windowHeight - 155 + 20);
		msgInvalid.setTextFill(Color.RED);

		// Set up the button that tells the system to read in a file to set up the game
		setupButtonUI(btnReadData, "Arial", 14, 200, Pos.BASELINE_LEFT, 375, 
				windowHeight - 155 + 105);
		btnReadData.setDisable(true);
		btnReadData.setOnAction((event) -> { readThePuzzle(); });

		// Set up the button that tells the system to solve the puzzle
		setupButtonUI(btnSolvePuzzle, "Arial", 14, 200, Pos.BASELINE_LEFT, 375, 
				windowHeight - 155 + 45);
		btnSolvePuzzle.setDisable(true);
		btnSolvePuzzle.setOnAction((event) -> { solveThePuzzle(); });

		// Add these element to the layout so they will be displayed
		theRoot.getChildren().addAll(msgWindowTitle, msgAuthor, msgFileName, fldFileName, 
				msgInvalid, msgFileFound, btnReadData, btnSolvePuzzle);
	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the label
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 * 
	 * @param t		The TextField object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the text field
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param e		true if the field should be editable, else false
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	/**********
	 * This routine checks to see if the puzzle  file is there and if so, sets up a scanner
	 * to it and enables a button to process it and run the simulation.  If a file is not
	 * found, a warning message is displayed and the button is disabled.
	 */
	void checkFileName(){
		fileName = fldFileName.getText().trim();	// Whenever the text area for the file name id changed
		if (fileName.length()<=0){					// this routine is called to see if it is a valid filename
			msgFileFound.setTextFill(Color.BLACK);
			msgFileFound.setText("");				// the button to play the puzzle is not enabled
			inputScanner = null;
			btnReadData.setDisable(true);
		} else 										// If there is something in the file name text area
			try {									// this routine tries to open it and establish a scanner
				inputScanner = new Scanner(new File(fileName));	// If no exception is thrown, there must
				if (thePuzzle.isTheInputFileValid(inputScanner)) {
					inputScanner = new Scanner(new File(fileName));
					msgFileFound.setTextFill(Color.GREEN.darker());
					msgFileFound.setText("File found!");			
					btnReadData.setDisable(false);
				} else {
					msgFileFound.setTextFill(Color.RED.darker());
					msgFileFound.setText("File found, but it is not a valid puzzle!");			
					btnReadData.setDisable(true);
				}
			} catch (FileNotFoundException e) {					// If an exception is thrown, the file name
				msgFileFound.setTextFill(Color.RED.darker());	// is not valid. Show a red error message and
				msgFileFound.setText("File not found!");		// disable the button that runs the puzzle.
				btnReadData.setDisable(true);
				inputScanner = null;
				
			}
	}

	/**********
	 * This is the routine to read the configuration file.  In this case, we assume that the intersection is
	 * really better equipped to do that input processing, because it knows about the simulation and it is
	 * where the queues will be for processing the cars... that is the information in the configuration file.
	 */
	private void readThePuzzle(){
		theController.establishTheSuDoKuPuzzle(inputScanner);	// Start up the game
		msgFileFound.setText("");								// Eliminate the file found message
		btnReadData.setText("The game is in progress!");		// Set the button's message to say it is running
		btnReadData.setDisable(true);							// and disable the button... no more input
		btnSolvePuzzle.setDisable(false);						// Enable the SolveThePuzzle button
	}

	/**********
	 * This routine is invoked when the player presses the SolveThePuzzle button.  The routine calls the
	 * controller and requests it to solve the puzzle.  When the controller return, the value it returns
	 * tells us whether or not the puzzle was solved and that information is used to set SolveThePuzzle
	 * button to one of two states.  Regardless, the routine finishes by changing the LoadThisPuzzle button
	 * to tell the player that the game is over.
	 */
	private void solveThePuzzle(){
		btnSolvePuzzle.setDisable(true);						// Disable the button so it can not be used again
		if (theController.solveTheSuDoKuPuzzle())				// Ask the controller to solve the puzzle and use
			btnSolvePuzzle.setText("The puzzle is solved!");	// the result to determine which of two messages
		else													// to use for the SolveThePuzzle button after the
			btnSolvePuzzle.setText("No solution is possible!");	// the computer has tried to solve it
		btnReadData.setText("The game is over!");				// Set the load button's message to say game over
	}

}