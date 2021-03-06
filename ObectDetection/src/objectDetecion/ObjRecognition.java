package objectDetecion;

import org.opencv.core.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class ObjRecognition extends Application
{
	/**
	 * The main class for a JavaFX application. It creates and handles the main
	 * window with its resources (style, graphics, etc.).
	 * 
	 * This application looks for any tennis ball in the camera video stream and
	 * try to select them according to their HSV values. Found tennis balls are
	 * framed with a blue line.
	 * Before running this program create an empty application.css file 
	 */
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			// load the FXML resource
			
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("ObjRecognition.fxml"));
			// set a whitesmoke background
			root.setStyle("-fx-background-color: whitesmoke;");
			// create and style a scene
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// create the stage with the given title and the previously created
			// scene
			primaryStage.setTitle("Object Recognition");
			primaryStage.setScene(scene);
			// show the GUI
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		// load the native OpenCV library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}
}
