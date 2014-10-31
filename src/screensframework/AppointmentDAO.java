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
import swing.Appointment;
import swing.ScheduleSwing2;

public class AppointmentDAO implements Initializable, ControlledScreen {

    ScreensController myController;
    
    // declare variables
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet results = null;
    String url = "jdbc:oracle:thin:z3417581/w8uhawAs@sage.business.unsw.edu.au:1521:orcl01";

    PreparedStatement insertAppointment = null;

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
    public void insertAppointment(Appointment apt) {
        
        openConnection();
        System.out.println("TEST");
        try {
            insertAppointment = conn.prepareStatement("INSERT INTO APPOINTMENTS ( START_TIME, END_TIME) values (?, ?)");
            insertAppointment.setDate(1, new java.sql.Date(apt.getStartTime().getTime()));
            insertAppointment.setDate(2, new java.sql.Date(apt.getStartTime().getTime()));

            insertAppointment.executeUpdate();

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
}

