// Main argc
// Screen framework
// AASA Technology

package screensframework;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreensFramework extends Application {
    
    public static String loginID = "login";
    public static String loginFile = "Login.fxml";
    public static String patientID = "patient";
    public static String patientFX = "Patient.fxml";
    public static String billingID = "billing";
    public static String billingFX = "Billing.fxml";
    public static String screen4ID = "screen4";
    public static String screen4File = "Screen4.fxml";
    public static String calendarID = "calendar";
    public static String calendarFX = "Calendar.fxml";
    public static String patientAddID = "patientAdd";
    public static String patientAddFX = "PatientAdd.fxml";
    public static String patientViewID = "patientView";
    public static String patientViewFX = "PatientView.fxml";
    public static String notesID = "notes";
    public static String notesFX = "Notes.fxml";
    public static String landingID = "landing";
    public static String landingFX = "Landing.fxml";
    public static String clinicalID = "clinical";
    public static String clinicalFX = "ClinicalMeasures.fxml";
    
    @Override
    public void start(Stage primaryStage) {
        
        // Loads each page
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
        mainContainer.loadScreen(ScreensFramework.patientID, ScreensFramework.patientFX);
        mainContainer.loadScreen(ScreensFramework.billingID, ScreensFramework.billingFX);
        mainContainer.loadScreen(ScreensFramework.screen4ID, ScreensFramework.screen4File);
        mainContainer.loadScreen(ScreensFramework.calendarID, ScreensFramework.calendarFX);
        mainContainer.loadScreen(ScreensFramework.patientAddID, ScreensFramework.patientAddFX);
        mainContainer.loadScreen(ScreensFramework.patientViewID, ScreensFramework.patientViewFX);
        mainContainer.loadScreen(ScreensFramework.notesID, ScreensFramework.notesFX);
        mainContainer.loadScreen(ScreensFramework.landingID, landingFX);
        mainContainer.loadScreen(ScreensFramework.clinicalID, clinicalFX);
        
        mainContainer.setScreen(ScreensFramework.loginID);
        
        /*
        // Locks the scene from resizing
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        **/
        Scene scene = new Scene(mainContainer);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
