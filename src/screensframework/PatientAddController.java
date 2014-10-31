// Patient Add Page
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import swing.ScheduleSwing2;

public class PatientAddController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    // declare variables
    Connection conn = null;
    ResultSet results = null;
    PreparedStatement ps = null;    
    PreparedStatement insertPatient = null;        
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

    @FXML
    public TextField patFirstNameTextField;
    public TextField patLastNameTextField;
    public TextField patContactTextField;
    public TextField patAddressTextField;
    public TextField patSuburbTextField;
    public TextField patPostcodeTextField;
    public TextField patInsuranceTextField;
    public TextField patDOBTextField;
    public TextField patMedicareNumTextField;
    public TextField patMedicareIDTextField;

    // Add Patient 
    public void insertPatient() {
        
        openConnection();
        System.out.println("Patient Added");
        try {
            insertPatient = conn.prepareStatement("INSERT INTO PATIENTS (FIRST_NAME, LAST_NAME, ADDRESS, SUBURB, POSTCODE, DOB, MEDICARE_CARD_NO, MEDICARE_ID_NO, PHONE_NO, HEALTH_INSURANCE) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertPatient.setString(1, patFirstNameTextField.getText());
            insertPatient.setString(2, patLastNameTextField.getText());
            insertPatient.setString(3, patAddressTextField.getText());
            insertPatient.setString(4, patSuburbTextField.getText());
            Integer patPostcode = tryParseInt(patPostcodeTextField.getText());
            insertPatient.setInt(5, patPostcode);
            insertPatient.setString(6, patDOBTextField.getText());
            Integer patMedicareNum = tryParseInt(patMedicareNumTextField.getText());
            insertPatient.setInt(7, patMedicareNum);
            Integer patMedicareID = tryParseInt(patMedicareIDTextField.getText());
            insertPatient.setInt(8, patMedicareID);
            Integer patContact = tryParseInt(patContactTextField.getText());
            insertPatient.setInt(9, patContact);
            insertPatient.setString(10, patInsuranceTextField.getText());

            insertPatient.executeUpdate();

        } catch (SQLException ex) {
                ex.printStackTrace();
        }
        closeConnection();
    }

    // method to parse string into ints. returns 0 if empty
    int tryParseInt(String value) { 
     try {  
         return Integer.parseInt(value);  
      } catch(NumberFormatException nfi) {  
          // Log exception.
          return 0;
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
    }

    @FXML
    private void goToLogin(ActionEvent event){
       myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);                
       myController.setScreen(ScreensFramework.loginID);
    }
    
    @FXML
    private void goToPatient(ActionEvent event) {
        myController.unloadScreen(ScreensFramework.patientID);
        myController.loadScreen(ScreensFramework.patientID, ScreensFramework.patientFX);
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
    private void goToPatientView(ActionEvent event) {
        myController.setScreen(ScreensFramework.patientViewID);
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

