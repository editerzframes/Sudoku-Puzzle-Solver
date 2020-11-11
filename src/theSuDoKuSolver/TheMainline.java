package theSuDoKuSolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * <p>
 * Title: TheMainline Class - A component of the JavaFX SuDoKu System
 * </p>
 *
 * <p>
 * Description: A controller object that sets up the stage and pane for this JavaFX application
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 2.00	This is the JavaFX rewrite of a previous Java Swing version from 2008
 */
public class TheMainline extends Application {

	// These are the attributes of this class

	private Pane theRoot;									// The list of GUI elements for the UI

	private final int WINDOW_WIDTH = 610;					// The width and the height of the
	private final int WINDOW_HEIGHT = 710;					// window established for this app

	/**********
	 * This method is called once the User Interface system has been initialized and is ready.
	 * 
	 * The methods places a title into the window, sets up the root of the GUI element list,
	 * initializes the WindowManager, set the scene on the stage, and shows the stage to the 
	 * user.  At this point, the application is completely driven by the user interacting with
	 * various Graphical User Interface elements.
	 */
	public void start(Stage theStage) throws Exception {
		theStage.setTitle("SuDoKu Player/Solver");					// Label the stage (a window)

		theRoot = new Pane();										// Create a pane within the window

		new WindowManager(theStage, theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Define the GUI elements							

		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene with
		// the required width and height

		theStage.setScene(theScene);								// Set the scene on the stage

		theStage.show();											// Show the stage to the user
	}

	/**********
	 * This mainline launches the JavaFX application.
	 * 
	 * @param args	The program arguments, args, are ignored
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
