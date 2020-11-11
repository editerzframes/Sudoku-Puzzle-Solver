package theSuDoKuSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * <p>
 * Title: View Class - A component of the JavaFX SuDoKu System
 * </p>
 *
 * <p>
 * Description: A controller object class that implements the user interface
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 2.00	This is the JavaFX rewrite of a previous Java Swing version from 2008
 */
public class View {
	
	// These are the attributes of this class
	private WindowManager theWindowManager;		// A reference to the WindowManager parent class
	private Controller theController;			// A reference to the application Controller class
	
	private int windowWidth;					// The width of the window
	private int windowHeight;					// The height of the window
	private Pane theRoot;						// The root of the GUI elements that form the UI
	
	// This is the array of combo boxes that are the heart of the user interface.  The Controller
	// needs access to this array in order to determine which combo box has changed, but the
	// actual combo boxes are part of the view module.
	@SuppressWarnings("unchecked")
	private ComboBox<String>[][] comboBoxSelector = new ComboBox[9][9];


	// A set with all nine valid moves present.  This is used to speed processing of potential sets.
	private static final List<String> ALL_VALID_COMBOBOX_VALUES = 
			new ArrayList<String>(Arrays.asList(" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

	// The constant that establishes a border between the end of the window and the GUI elements
	private final int BORDER = 10;
	
	/**********
	 * This is the fully parameterized constructor for this class
	 * 
	 * @param twm	The reference to the WindowManager parent
	 * @param ww	The width of the window
	 * @param wh	The height of the window
	 * @param tc	The reference to the Controller Object
	 * @param tr	The reference to the Window Pane object
	 */
	public View (WindowManager twm, int ww, int wh, Controller tc, Pane tr) {
		theWindowManager = twm;
		windowWidth = ww;
		windowHeight = wh;
		theController = tc;
		theRoot = tr;
	}
	
	/**********
	 * This setup method is used to add the decorations and the GUI elements of the puzzle to the
	 * window that the WindowManager set up for us
	 */
	public void setupWindowDecoration () {
		
		// Decorate the window with a pair of rectangles
		Rectangle outerRectangle = new Rectangle();  
		Rectangle innerRectangle = new Rectangle();  

		//Setting the properties of the outer rectangle 
		outerRectangle.setX(0); 
		outerRectangle.setY(50); 
		outerRectangle.setWidth(windowWidth); 
		outerRectangle.setHeight(windowHeight-195);
		outerRectangle.setFill(Color.LIGHTGRAY);
		outerRectangle.setStroke(Color.BLACK);
		
		//Setting the properties of the inner rectangle 
		innerRectangle.setX(5); 
		innerRectangle.setY(55); 
		innerRectangle.setWidth(windowWidth-10); 
		innerRectangle.setHeight(windowHeight-205);
		innerRectangle.setStroke(Color.BLACK);
		innerRectangle.setFill(Color.WHITE);
		
		// Compute the width and the height of the 3 by 3 grid
		int verticalSpace = (windowHeight-205)/3;
		int horizontalSpace = (windowWidth-10)/3;
		
		// Draw the lines that divide the space into nine regions
		Line upperLine = new Line(10, verticalSpace+50, windowWidth-10, verticalSpace+50);
		Line lowerLine = new Line(10, 2*verticalSpace+50, windowWidth-10, 2*verticalSpace+50);
		Line leftLine = new Line(3 + horizontalSpace, 60, 3 + horizontalSpace, windowHeight-155);
		Line rightLine = new Line(2 + 2*horizontalSpace, 60, 2 + 2*horizontalSpace, windowHeight-155);

		// Add the above items to the window the WindowManager established for us
		theRoot.getChildren().addAll(outerRectangle, innerRectangle,
				upperLine, lowerLine, leftLine, rightLine);
		
		// Now add in all 81 of the comboBoxes to the window and display them in the traditional
		// SuDoKu layout
		int squareWidth = (windowWidth - 20) / 9;
		int squareHeight = (windowHeight - 205 - 20) / 9;

		for (int r = 0; r < 9; r++)				// Establish all 81 of the combo boxes to initially have
			for (int c = 0; c < 9; c++){		// the possibility to hold any of the ten possible states (0-9)
				// Each comboBox has a unique instance, position, and state
				ComboBox<String> s = new ComboBox<String>
					(FXCollections.observableArrayList(ALL_VALID_COMBOBOX_VALUES));
				comboBoxSelector[r][c] = s;		// Record the reference so find the comboBox when one is used

				// Position the comboBox in the window
				s.setLayoutX(BORDER + (squareWidth + 1) * c);
				s.setLayoutY(BORDER + 55 + (squareHeight + 3) * r);
				
				// There is just one action listener for them all, but it can figure out which invoked
				// it by using this reference matrix
		        s.setOnAction((event) -> {theController.performSelection(event);});
				theRoot.getChildren().add(s);	// Add each of the comboBoxes to the window
			}
	}
	
	/**********
	 * This method allows others to set and clear the message field value that is displayed by
	 * the window manager
	 * @param s		the string to be displayed in the message field
	 */
	void setMsgInvalid(String s){theWindowManager.msgInvalid.setText(s);}

	
	/**********
	 * This routine is used by the model to reset a comboBox back to the defaults state.
	 * 
	 * @param r		the row number  where the square is to be altered (0-8)
	 * @param c		the column number where the square is to be altered (0-8)
	 */
	void resetSelector(int r, int c){
		comboBoxSelector[r][c].setItems(FXCollections.observableArrayList(ALL_VALID_COMBOBOX_VALUES));
		comboBoxSelector[r][c].getSelectionModel().clearSelection();
		}
	
	/*********
	 * This routine is also used by the model to change a combo box.  In this case the list of options
	 * will be reduced to just one value so it is not possible for the puzzle player to change values
	 * for those squares established when they were set during the initialization of the puzzle from a file.
	 * 
	 * @param r		the row number  where the square is to be altered (0-8)
	 * @param c		the column number where the square is to be altered (0-8)
	 * @param p		the value for the specified square
	 */
	void setSingleSelectItem(int r, int c, int p){
		String s = "" + p;
		if (s.equals("0")) s = " ";
		comboBoxSelector[r][c].setItems(FXCollections.observableArrayList(s));	// Only one option
		comboBoxSelector[r][c].setStyle("-fx-background-color: mistyrose;");	// and highlight it
		comboBoxSelector[r][c].setValue(s);										// and select it
	}
	
	/**********
	 * This getter returns a reference to the matrix of comboBoxes so the rest of the systems can
	 * access them and update them as needed
	 * 
	 * @return	a reference to the double dimensioned array of comboBoxes
	 */
	public ComboBox<String>[][] getComboBoxSelectors() {
		return comboBoxSelector;
	}
}