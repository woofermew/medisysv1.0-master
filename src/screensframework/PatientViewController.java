// Patient View Page
// INFS3605 AASA Technology

package screensframework;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import swing.ScheduleSwing2;

public class PatientViewController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @FXML
    public TextField patIDTextField;
    
    // declare variables
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet results = null;
    // PreparedStatement deletePatient = null;    
    String url = "jdbc:oracle:thin:z3417581/w8uhawAs@sage.business.unsw.edu.au:1521:orcl01";

        // open connection to database method
        public void openConnection() {
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    
        // close connection to database method
        public void closeConnection() {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setScreenParent(ScreensController screenParent){

        myController = screenParent;
        
        // Get patient ID
        String patientID = myController.getSessionValue("CurrentViewPatient");

        
        // Use the patient ID
        patIDTextField.setText(patientID);
        
        // Clear it after use, or it will default to the last selected patient
        myController.deleteSessionValue("CurrentViewPatient");
    }
    
    /**
    @FXML
    public void deletePatient(String patientID) {
        openConnection();
        try {
            System.out.println();           
            deletePatient = conn.prepareStatement("DELETE FROM PATIENT WHERE PATIENT_ID = ?");
            deletePatient.setString(1, patientID);
            
            //deletePatient.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
    */
    
    @FXML
    private void goToLogin(ActionEvent event){
       myController.setScreen(ScreensFramework.loginID);
    }
    
    @FXML
    private void goToPatient(ActionEvent event) {
        myController.setScreen(ScreensFramework.patientID);        
    }
    
    @FXML
    private void goToBilling(ActionEvent event){
       myController.setScreen(ScreensFramework.billingID);
    }
    
    @FXML
    private void goToScreen4(ActionEvent event) {
        myController.setScreen(ScreensFramework.screen4ID);
    }
    
    @FXML
    private void goToCalendar(ActionEvent event) {
        myController.setScreen(ScreensFramework.calendarID);
    }
    
    @FXML
    private void goToNotes(ActionEvent event) {
        myController.setScreen(ScreensFramework.notesID);
    }
    
    @FXML
    private void openCalendarSwing(ActionEvent event) {
        ScheduleSwing2 screenCal = new ScheduleSwing2();
        screenCal.runCalendar();
    }

    @FXML
    private void goToLanding(ActionEvent event) {
        myController.setScreen(ScreensFramework.landingID);
    }
    
}

