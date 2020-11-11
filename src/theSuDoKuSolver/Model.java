package theSuDoKuSolver;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * <p>
 * Title: Model Class - A component of the JavaFX SuDoKu System
 * </p>
 *
 * <p>
 * Description: A controller object class that implements the application business logic
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
public class Model {

	/**********
	 * This nested entity class defines the SuDoKu Puzzle Board
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	public class SuDoKuBoard {
		/**
		 * <p>
		 * Title: Model Class - A component of the JavaFX SuDoKu System
		 * </p>
		 *
		 * <p>
		 * Description: An entity class that implements the SuDoKu Board
		 * </p>
		 *
		 * <p>
		 * Copyright: Copyright (c) 2019
		 * </p>
		 *
		 * @author Lynn Robert Carter, Puneet Garg
		 * @version 2.00	This is the JavaFX rewrite of a previous Java Swing version from 2008
		 * @version 3.00	This is the JavaFX version with all the methods implemented to solve the SuDoKu
		 */

		// This is the class attribute that represents the SuDoKu Puzzle
		int [][] square;

		/**********
		 * This constructor established an empty SuDoKu puzzle
		 */
		public SuDoKuBoard() {
			square = new int[9][9];
		}

		/***********
		 * This is a copy constructor
		 * 
		 * @param b		This parameter specifies the board to be used to initialize this new board
		 */
		public SuDoKuBoard(SuDoKuBoard b) {
			// Replace this with the code to create a copy of the puzzle from another
			square = b.square;
		}

		/**********
		 * This setter sets a specific square in the puzzle to a specific value. This method does not
		 * check to see if this is a valid thing to do
		 * 
		 * @param r		This int is the row to be used
		 * @param c		This int is the column to be used
		 * @param p		This int is the value to be placed into the square
		 */
		protected void setSquare(int r, int c, int p) {
			// Replace this with the code to set the value of a square
			square[r][c]=p;
		}

		/**********
		 * This getter returns the value of a specified square in the puzzle based on the parameters
		 * 
		 * @param r		This int is the row to be used for the get
		 * @param c		This int is the column to be used for the get
		 * 
		 * @return		The returned value is the int at that row in that column
		 */
		protected int getSquare(int r, int c) {
			
			return square[r][c]; // Replace this with the code to get the value of a square
		}

		/**********
		 * This method checks to see that the file consists of precisely 9 lines of input, where each
		 * line consists of precisely 9 characters, and each of those characters are digits in the
		 * range from 0 through 9.  If any of these conditions are not met, the routine returns a
		 * message that explains what the problem is.  If there are all met, an empty string is
		 * returned
		 * 
		 * @param input		A Scanner class object that refers to the specified "SuDoKu" puzzle file
		 * 
		 * @return			A String class object is returned signaling an error with a message or
		 * 					no error with an empty String
		 */
		protected String isTheFileFormattedProperly(Scanner input){
			// The following are the four possible errors the file cold have that this code must check
			// before assuming the input is formatted properly.  If an error is found, return the
			// appropriate string.  If none of the errors are found, return an empty string
			// "*** SuDoKu input error: There are less than 9 input lines!";
			// "*** SuDoKu input error: A line must have exactly 9 characters!";
			// "*** SuDoKu input error: Each character must be in the range 0 through 9!";
			// "*** SuDoKu input error: There are more than 9 input lines!";
			  int lines = 0;
		         
		         while(input.hasNextLine())
		         {
		        	 String s = input.nextLine();
		        
		             lines++;
		            	
		            	// System.out.println(s);
		            	 int count = 0;
		            	 for (int i = 0;  i < s.length(); i++) {
		            	     if (Character.isDigit(s.charAt(i))) {
		            	         count++;
		            	     }
		            	     else {
		            	    	 return "*** SuDoKu input error: Each character must be in the range 0 through 9!";
		                        	
		            	     }
		            	 }
		           
		            		 
		            	if(count != 9) {
		            		return "*** SuDoKu input error: A line must have exactly 9 characters!";
		            	}
		        }
		      //  System.out.println(lines);
		        
		        
		         if(lines<9) {
		 			return "*** SuDoKu input error: There are less than 9 input lines!";
		 		}
		 		
		 		else if(lines>9) {
		 			return "*** SuDoKu input error: There are more than 9 input lines!";
		 		}
			// If all of those conditions have been met, signal that it is okay to read in this file
			return "";
		}	

		/**********
		 * Given a Scanner object as a parameter that has a properly formatted input, establish a new
		 * SuDoKu board with a new puzzle in it and then check to see that each that the SuDoKu rules
		 * have been followed (1. each digit appears only once inside each row; 2. each digit appears
		 * only once inside of each column; 3. each digit appears only once inside of each .
		 * @param fileReader
		 * @return
		 */
		protected boolean isTheInputPuzzleValid(Scanner fileReader) {
			// Establish the puzzle on the board
			establishTheSuDoKuPuzzle(fileReader);

			// Check the puzzle to see if it follows the SuDoKu rules for a valid puzzle
			return isTheBoardValid();
		}

		/**********
		 * Given a Scanner object as a parameter that has a properly formatted input, establish a new
		 * SuDoKu board, process the nine lines of input, and process the nine characters on each line.
		 * 
		 * @param input		This is a Scanner class object to a file that has already been vetted
		 * 					to insure the format is correct before calling this routine.
		 */
		private void establishTheSuDoKuPuzzle(Scanner input){
			// Establish a new empty puzzle
			square = new int[9][9];
			
			//the code to re-establish an empty puzzle
			for (int col = 0; input.hasNextLine(); col++) {
				String[] inputLine = input.nextLine().trim().split("");
				for (int row = 0; row < square.length; row++) {
					square[col][row] = Integer.parseInt(inputLine[row]);
				}

			}

			
		}

		/**********
		 * Check the entire board to see if all 81 positions are valid moves or a move there has not 
		 * yet been made 
		 * 
		 * @return	true if the board is valid as far as we can tell; false if a rule has been violated
		 */
		private boolean isTheBoardValid(){
			// the code to check the squares to see if there is a move there 
			// and if so, that the move is valid given the context of the rest of the puzzle.  If any
			// of the SuDoKu rules as violated, return false.  If after check all of the positions,
			// no rules have been violated, return true and shown below.
			for (int i = 0; i < square[0].length; i++) {
				for (int j = 0; j < square.length; j++) {
					if (square[i][j] == 0) {
						boolean move = isThisMoveValid(i,j);
						if(move == true) {
							System.out.println("No rules violated");
							return true;
						}
						else {
							System.out.println("rules violated");
							return false;
						}
					} else {
						System.out.println("No Move");
					}
				}
			}
			return true;
		}

		/**********
		 * This private routine checks a move to see if a move is valid.  This is done by checking
		 * the row, the column, and the owning sub-puzzle.  If the move is valid, the method
		 * returns a true else a false.
		 * 
		 * @param r		The row that was changed
		 * @param c		The column that was changed
		 * @return		true is returned if the move was valid, else a false is returned
		 */
		protected boolean isThisMoveValid(int r, int c){

			// getting the number on the index
			int num = square[r][c];
				HashSet<Integer> set = new HashSet<Integer>(); // creating a set to check the numbers
				
			// row checker
				for(int i = 0; i<9; i++) {
					
					if(i!=r) {
				if(square[i][c] == 0) {
					continue;
				}
				
					set.add(square[i][c]);}
				}
				
			// column checker
				for(int j = 0; j<9; j++) {
					if(j!=c) {
					if(square[r][j] == 0) {
						continue;
					}
					
						set.add(square[r][j]);}
					}
				
			//checking sub matrix
		        int row_start = (r/3)*3;
		        int col_start = (c/3)*3;
		        for(int i=row_start;i<row_start+3;i++)
		        {
		            for(int j=col_start;j<col_start+3;j++)
		            {
		            	if(i!=r && j!=c) {
		            	if(square[i][j] == 0) {
		    				continue;
		    			}
		            	
		            	set.add(square[i][j]);}
		            }
		        }
		        
		       
				if(set.contains(num)){
					System.out.println("***Error in set******"+set);
					
					return false;
				}
			// If none of the three conditions above was violated, then this must be a valid move!
			return true;
		}

		/**********
		 * This routine is only called after a valid move, but it is possible that several valid moves
		 * will result in a situation where there is no valid.  Similarly, an invalid move can be
		 * by valid moves, so there is no guarantee that a valid move that completes the puzzle is 
		 * really a solution, so we must also check for all squares filled in *and* that all squares
		 * are correct.
		 * 
		 * @return	returns true if the puzzle has actually be completed and solved correctly
		 */
		protected boolean isDone(){
			// checks every square and returns false if even one 
			// of them contains a zero
			for(int i=0;i<square.length;i++) {
				for(int j=0;j<square.length;j++) {
					if(square[i][j] == 0) {
						return false;
					}
				}
			}
			// If we get here, all 81 moves have been made, so we must see that there are not
			System.out.println("is done-->checktheboard");
			return checkTheBoard();	// duplicates with a row, a column, or a 3x3 sub square.
		}


		/**********
		 * This routine checks to see that any selected square is valid.  This can be used to check for 
		 * verifying the input puzzle as well as for checking for done after verifying that all squares
		 * have specified a value between "1" and "9".
		 * 
		 * @return	returns true if every non-zero entry is unique in its row, column, and sub-square
		 */
		protected boolean checkTheBoard(){
			// Check all of the positions on the board.  For each position that has a move, check
			// to see if that move is valid with respect to the other moves in that row, the other
			// moves in the column, and the others moves in the 3x3 block.  If even one is not
			// valid, return false

	      for(int r = 0; r<9; r++) {
		    	for(int c = 0; c<9; c++) {
		           boolean b =  isThisMoveValid(r,c);
		           if(b == false) {
			       return false;
	        	}
			}
	 }
	         return true;
	 	}

		/**********
		 * This toString method formats the SuDoKu puzzle into a string that is appropriate to be
		 * display on the console for debugging purposes
		 */
		public String toString() {
			String result="";
			
			for (int y = 0; y < square.length; y++) {
				for (int x = 0; x < square.length; x++) {
					result += " " + square[y][x] + " ";
					if (x == 2 || x == 5) {
						result += "|";
					}
				}
				result += "\n";
				if (y == 2 || y == 5) {
					result += " - - - - + - - - - + - - - -";
					result += "\n";
				}
			}
			
			return result;
		}
		
		/**********
		 * Given a board and a specified row and column number (0-8) compute the set of
		 * possible moves for that square.  If the square is not 0 (i.e. a move has already
		 * been made there) return an empty set.  If the square is 0, the routine scans
		 * the row, the column, and the sub-square, forms the union of all made moves and
		 * then subtracts the set of all of the moves that have been made from the set of
		 * all possible moves, resulting in the set of all potential valid moves.
		 * 
		 * @param r		the row number (0 - 8) to be examined
		 * @param c 	the column number (0 - 8) to be examined
		 * @return		the set of potential moves for this square given the current board state
		 */
		private Set <Integer> potentialMoves(int r, int c){
		
			// If the specified position is a blank, set up empty sets for all three of the aspects
			
			Set<Integer> row = new HashSet<>();
			Set<Integer> col = new HashSet<>();
			Set<Integer> tit = new HashSet<>();
			
			
			// Loop through the rows using this column to add in any already made moves found there
		
			for(int j = 0; j<9; j++) {
				if(j!=c) {
				if(square[r][j] == 0) {
					continue;
				}
				
					col.add(square[r][j]);}
				}
		
			
			// Loop through the  columns using this row to add in any already made moves found there
			
			for(int i = 0; i<9; i++) {
				if(i!=r) {
			if(square[i][c] == 0) {
				continue;
			}
				row.add(square[i][c]);}
			}
			
			// Loop through the 3 x 3 sub-square adding in each already made move found to the set

			 //checking sub matrix
	        int row_start = (r/3)*3;
	        int col_start = (c/3)*3;
	        for(int i=row_start;i<row_start+3;i++)
	        {
	            for(int j=col_start;j<col_start+3;j++)
	            {
	            	if(i!=r && j!=c) {
	            	if(square[i][j] == 0) {
	    				continue;
	    			}
	            	tit.add(square[i][j]);}
	            }
	        }
	        
			// Compute the union of the three sets... This represents all of the moves that have been made
				
	        row.addAll(col);
	        row.addAll(tit);
	        System.out.println(row);
	        
			return row;
		}

		/**********
		 * This internal routine is used to make a move and then set up for recursive calls to
		 * make the next move.  This method employs recursion with backtracking.  The tempPuzzle
		 * is the mechanism that supports the backtracking. With each new move a copy from the
		 * last is made.  Worse case, there could be a stack of 81 backtrack copies.  After a 
		 * recursive call, a check is made to see if the returned result is done.  If it is, the
		 * method returns that done puzzle to the caller.  If it isn't done, then we try the 
		 * next potential move via the inner loop. If the inner loop reaches the end, then we back
		 * up one level and try all of those possible moves in the outer loop.
		 * 
		 * Eventually one of two things will happen... we find a solution and that gets returned
		 * or we return all the way back to the beginning and exhaust all of those possible moves
		 * which will result in a final return with the original puzzle, signaling that this 
		 * method was unable to find a solution.
		 * 
		 * @param r		The row number where the initial change is to be made
		 * @param c		The column number where the initial change is to be made
		 * @param m		The value for the initial change; zero means search for a square that 
		 * 					needs a move to be made
		 * 
		 * @return		The returned value is a puzzle... either the original puzzle or the solved
		 * 				puzzle based on whether or not this method is successful at finding a 
		 * 				solution.
		 */
		private SuDoKuBoard solveThePuzzle(int r, int c, int m){
			
			if (r == 9) {
				r = 0;
				if (++c == 9) {
					return this;
				}
			}
			if (square[r][c] != 0) {
				return solveThePuzzle(r+1,c,0);
			}
			// For all possible values
			for(int val = 1; val <= 9; val++) {
				Set<Integer> pos = new HashSet<>();
				pos = potentialMoves(r, c);
				if(!pos.contains(val)) {
					square[r][c] = val;
					if (solveThePuzzle(r+1, c, 0) == this) {
						return this;
					}
				}
			}
			// Reset the cell to EMPTY to do recursive backtrack and try again
			square[r][c] = 0;
			return null;
		}
	}

	/**
	 * These are the class attributes for the Model class. (All the material above was the code for the
	 * nested class that defines the SuDoKu board and the actions on that board.)
	 */

	protected SuDoKuBoard originalPuzzle;	// A copy of the puzzle before computer-based moves are made
	protected SuDoKuBoard resultPuzzle;		// The result after the computer-based moves are made
	protected SuDoKuBoard theBoard;			// This is the working board for this class

	// A set with all nine valid moves present.  This is used to speed processing of potential sets.
	private static final Set<Integer> allPossibleMoves = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

	// This is the reference to the View class that displays this Model class
	private View theView;

	/**********
	 * This is the default constructor for the Model class.
	 */
	public Model() {
		theBoard = new SuDoKuBoard();
	}

	/**********
	 * This setter establishes the reference to the View class
	 * 
	 * @param tv	This is the reference to the View class
	 */
	public void setView(View tv) {
		theView = tv;
	}

	/**********
	 * This method checks to see if the provided game obeys all three of the rules. Just because
	 * all of the non-zero squares obey all three rules does not mean that it is possible to win 
	 * the game.
	 * 
	 * @param a Scanner object of the game
	 * 
	 * @return true if the input is syntactically valid, but it may not be possible to win the game
	 */
	public boolean isTheInputFileValid(Scanner input){
		// Loop through nine rows of nine characters each
		for (int r = 0; r < 9; r++){
			// Validate that there are nine lines
			if (!input.hasNextLine()) {
				System.out.println("*** SuDoKu input error: There are less than 9 input lines!");
				return false;
			}

			// Validate that each line is exactly 9 characters long
			String line = input.nextLine();			// Read a whole line at a time
			if (line.length() != 9) {				// Validate that the line is the right length
				System.out.println("*** SuDoKu input error: A line must have exactly 9 characters!");
				return false;
			}

			// Validate that each of those character are a digit from zero through 9 (0 -> blank)
			for (int c = 0; c < 9; c++){			
				int thePlay = line.charAt(c) - '0';	
				if ((thePlay<0) || (thePlay>9)) {
					System.out.println("*** SuDoKu input error: Each character must be in the range 0 through 9!");
					return false;
				}
			}
		}

		// Validate that there is nothing after the ninth line
		if (input.hasNextLine()) {					// Validate that there are no more than nine lines
			System.out.println("*** SuDoKu input error: There are more than 9 input lines!");
			return false;
		}	

		// If all of those conditions have been met, signal that it is okay to read in this file
		return true;
	}


	/**********
	 * This routine uses the passed in parameter to define the puzzle from an input file.  The
	 * input file must consist of nine rows of nine digits in the range 0 through 9.  A 0 value
	 * means that that square has not yet been determined.  All others indicate that the specified
	 * square must be the specified value.  Any non-zero square has it's combo box set, so the
	 * player has no option to change the value from it's initial value.
	 * 
	 * @param input		the input scanner used to define the starting state of the board
	 */
	void establishTheSuDoKuPuzzle(Scanner input){
		// Loop through nine rows of nine characters each
		for (int r = 0; r < 9; r++){
			String line = input.nextLine();			// Read a whole line at a time
			for (int c = 0; c < 9; c++){			// Then reach into the line for each character
				int thePlay = line.charAt(c) - '0';	// In the case of a 0, this is not a move
				// If the option is a zero, indicate that the user must specify this square
				if  (thePlay == 0)	{				// For a 0, use the default ten value select
					theBoard.setSquare(r,c,0);		// list but do not actually select any value...
					theView.resetSelector(r,c);
				} else {

					// This code will only be performed for selections of 1 through 9.
					int theItem = thePlay;		// Set the value into a wrapper object
					theBoard.setSquare(r,c,thePlay);	// Assign the value to puzzle square
					theView.setSingleSelectItem(r,c,theItem);
				}									// regular comboBoxes will be black)
			}
		}
	}

	/**********
	 * This routine is the top level routine for automatically solving the SuDoKu puzzle.  The
	 * routine first verifies that the provided puzzle is not in error (already violates one of
	 * the SuDoKu rules) and if it is okay, it starts the recursive solutions process with a 
	 * special move that signals a scan for a square that requires a move must be used.
	 * 
	 * @return	If the puzzle was valid, the routine returns the processing time in milliseconds
	 * 			If the puzzle was invalid upon receipt, the routine returns zero
	 */
	long solveTheSuDoKuPuzzle(){
		System.out.println("solvetheSuDoKuPuzzle-->checktheboard");
		if (theBoard.checkTheBoard()){							// See if the puzzle is correct
			originalPuzzle = new SuDoKuBoard(theBoard);			// If so, save the initial state
			long start = System.currentTimeMillis();			// Establish the start time
			resultPuzzle= new SuDoKuBoard();
			resultPuzzle = theBoard.solveThePuzzle(0,0,0);		// Request the recursive solution
			long stop = System.currentTimeMillis();				// Establish the stop time
			System.out.println(resultPuzzle);
			return stop - start;								// Return the computation time
		}
		else {
			theView.setMsgInvalid("The original puzzle is not valid");
			return 0;											// No computation, so return zero
		}
	}

	/*******
	 * This method calls the checkTheBoard method from the sudoku board class and returns the boolean type 
	 * to the calling statement.
	 * 
	 * @return if the board is valid, then it will return true, otherwise it will return false.
	 */
	boolean check(){
		if(theBoard.checkTheBoard()) {
			return true;
		}
		return false;
	}
	
	/*******
	 * This method calls the isDone method from the sudoku board class and returns the boolean type 
	 * to the calling statement.
	 * 
	 * @return if the solution is done and there is no zero value left, then it will return true, otherwise it will return false.
	 */
	boolean done(){
		if(theBoard.isDone()) {
			return true;
		}
		return false;
	}
	/**********
	 * This interface routine is used when the controller needs to update the current board
	 * due to input from the user changing one of the combo-boxes.
	 * 
	 * @param r		This is the row number where the change occurred 
	 * @param c		This is the column number where the change occurred
	 * @param value	This is the new value for the specified square
	 */
	void setPuzzleValue(int r, int c, int value){theBoard.setSquare(r,c,value);}

}