// Calendar page controller
// INFS3605 AASA Technology

package screensframework;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import swing.ScheduleSwing2;

public class CalendarController implements Initializable , ControlledScreen {

    ScreensController myController;
    
    // declare variables
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet results = null;
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
    }

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
