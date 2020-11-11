package theSuDoKuSolver;

import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

/**
 * <p>
 * Title: Controller Class - A component of the JavaFX SuDoKu System
 * </p>
 *
 * <p>
 * Description: A controller object that controllers the JavaFX application execution - This
 * controller works with a Model and a View to implement the SuDoKu Puzzle Player/Solver
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 *
 * @author Lynn Robert Carter, Puneet Garg
 * @version 2.00	This is the JavaFX rewrite of a previous Java Swing version from 2008
 * @version 3.00	This is the JavaFX version with all the methods implemented to solve the SuDoKu
 * 
 */
public class Controller {
	
	// The following attributes are the reference to the other two main elements of the application
	private Model thePuzzle;		// The model manages the semantics of the applications
	private View theView;			// The view establishes and maintains the User Interface
	
	// This reference is the linkage between the controller and the view so this class can set up
	// the user interface
	@SuppressWarnings("unchecked")
	private ComboBox<String>[][] comboBoxSelector = new ComboBox[9][9];
	
	// This flag is used to keep programmed changes to a comboxBox from processing an event when it
	// is just a side effect of code altering a comboBox
	private boolean loadingThePuzzle = false;

	
	/**********
	 * This constructor establishes this controller and gives it a reference to the Model that this
	 * controller is supposed to use
	 * @param tp
	 */
	public Controller (Model tp) {
		thePuzzle = tp;
	}

	/**********
	 * This setter is used to connect this controller to the View that manages the user interface
	 * @param tv
	 */
	public void setView(View tv) {
		theView = tv;
		comboBoxSelector = theView.getComboBoxSelectors();	// Save a reference to the array of combo boxes
	}
	
	/**********
	 * This method reads in the puzzle input from the provided parameter and establishes the
	 * puzzle.  The way this code is designed, we know by the fact that we got here that the input
	 * is a valid puzzle in terms that it is the right size and all of the input values are within
	 * range, so we do not need to check those aspects as we set up the puzzle.
	 * @param input
	 */
	public void establishTheSuDoKuPuzzle(Scanner input) {
		loadingThePuzzle = true;					// There are no user interface events during
		thePuzzle.establishTheSuDoKuPuzzle(input);	// this process, so we turn-off that processing
		loadingThePuzzle = false;					// until the input process has finished
		return;
	}
	
	/**********
	 * This is the root of the process to solve the puzzle. The bulk of this code is setting up the
	 * user interface array of comboBoxes to reflect the solution that was found.  The real work
	 * to find the solution is actually performed in the Model Class.
	 * 
	 * @return	The method returns true if a solution have been found, else it return false
	 */
	public boolean solveTheSuDoKuPuzzle() {
		// Try to solve the puzzle and capture how long it took. This is where the real work takes
		// place.
		long elapsedTime = thePuzzle.solveTheSuDoKuPuzzle();
		System.out.println("Solution time: " + elapsedTime);
		
		// Check to see if the result indeed solves the puzzle and if so, align the View with it
		//System.out.println("controller-->checkTheBoard()");
		//System.out.println(thePuzzle.resultPuzzle.checkTheBoard());
		//boolean b2=thePuzzle.originalPuzzle.checkTheBoard();
		boolean b2 = thePuzzle.check();
		System.out.println("controller-->isDone");
		//boolean b1=thePuzzle.originalPuzzle.isDone();
		boolean b1 = thePuzzle.done();
		if (b1&&b2) {
			// If so, the array of combo boxes must be changed to reflect the solution found
			loadingThePuzzle = true;			// Ignore the events the comboBox changes generate
			for (int r = 0; r < 9; r++) {			// Examine all squares, look for 0s in the original
				for (int c = 0; c < 9; c++)	{	// board and use the corresponding value from the
					thePuzzle.theBoard.setSquare(r, c, thePuzzle.resultPuzzle.getSquare(r,c));
					
						int theItem = thePuzzle.resultPuzzle.getSquare(r,c);	// Set the value as the
								comboBoxSelector[r][c].setValue(Integer.toString(theItem));			// selected item
				}}	
			loadingThePuzzle = false;			// After all 81 have been processed, enable combo box changes
			
			theView.setMsgInvalid("Puzzle solved in " + elapsedTime + "ms!");
			return true;					// Set the completion message and return true
		}
		
		theView.setMsgInvalid("The puzzle cannot be solved!");
		return false;							// The puzzle could not be solved.  Say so and return false.
	}
	
	/**********
	 * This method is used when the user changes a comboBox select list item. This is not used
	 * when the user asked the application to solve the puzzle
	 * 
	 * @param ae	This parameter allows us to figure out which of the 81 comboBoxes was altered
	 */
	@SuppressWarnings("unchecked")
	public  void performSelection(ActionEvent ae) {
		// If we are loading the puzzle, ignore any comboBox events that might be fired
		if (loadingThePuzzle) return;
		
		// Use the event to get the reference of the comboBox that trigger the event
		ComboBox<String> s = null;
		
		// We know that the only events that could be generated come from the user changing one 
		// of the 81 comboxBoxes, but we do this check in case something is wrong
		if (ae.getSource() instanceof ComboBox<?>)
			s = (ComboBox<String>)ae.getSource();
		
		// Search through all of the comboBoxes until the match is found.  When the matching comboBox is 
		//found, "r" and "c" will specify which one was the one that was changed
		int r = 0;
		int c = 0;
		while (r < 9 && s != comboBoxSelector[r][c]){
			while (c < 9 &&  s != comboBoxSelector[r][c]) c++;
			if (c==9){
				c=0;
				r++;
			}
		}
		
		// The comboBox uses a set of String objects for each possible option.  This command 
		//  the item that was selected
		String str = (String)s.getValue();
		if (str.equals(" ")) str = "0";
		int value = str.charAt(0) - '0';		
		
		// Use the "r" and "c" indexes to access the changed comboBox so we can save the value
		loadingThePuzzle = true;
		thePuzzle.setPuzzleValue(r, c, value);
		loadingThePuzzle = false;
		
		// If the player selected first element (an index of zero which is also a String with a
		// value of a single blank character, we treat that as a request to reset that square to empty
		if (value == 0){
			// A blank move was made
			if (thePuzzle.theBoard.checkTheBoard())		// Check to see if the puzzle is in a valid state
				theView.setMsgInvalid("");				// If so, do not display a message
			else										// If not, we display a red error message
				theView.setMsgInvalid("Invalid solution!");
		}
		else
			// A non-blank move was made!
			if (thePuzzle.theBoard.isThisMoveValid(r,c))// Check to see if the move is valid
				if (thePuzzle.theBoard.checkTheBoard())
					if (thePuzzle.theBoard.isDone())	// If the move is valid, check for done (a winner!)
						theView.setMsgInvalid("You have solved the puzzle!");
					else {										// If this is not a winner, be sure the invalid flag 
						theView.setMsgInvalid("");				// is reset so that the player knows this was valid
					}											// a valid move
				else
					theView.setMsgInvalid("Invalid move!");		// set to inform the player that the move was invalid
			else												// If the move was invalid, the error message must be
				theView.setMsgInvalid("Invalid move!");			// set to inform the player that the move was invalid
	};
}