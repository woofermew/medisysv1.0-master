// Login Page Screen

package screensframework;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import swing.Appointment;
import swing.ScheduleSwing2;

public class LoginController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @FXML
    private Label label;
    public TextField username;
    public PasswordField password;
    public String usernameText;
    public String passwordText;
    public javafx.scene.control.Button closeButton;
    
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

    // Login method
    public void LoginUser() throws SQLException {
        System.out.println("Login User Invoked");
        usernameText = username.getText();
        passwordText = password.getText();
        
        System.out.println(usernameText+" "+passwordText);
        
        openConnection();
        
        String queryString = "SELECT username, password FROM USERS WHERE username='" + usernameText +"'" ;

        ps = conn.prepareStatement(queryString);

        results = ps.executeQuery();

        // check if user exist
        if (results.next()) {
            System.out.println("user exist");
            String pass = results.getString("password");

            if (pass.equals(passwordText)) {
                
                System.out.println("Password correct");
                label.setText("Login Successful!");
                myController.setScreen(ScreensFramework.landingID);

                // clears the textfields
                username.setText("");
                password.setText("");
               
               
            }
            
            if (!pass.equals(passwordText)) {
                label.setText("Incorrect Username or Password");
            }
            
        } else {
            System.out.println("user does not exist");
                label.setText("Incorrect Username or Password");
        } 
        results.close();
        conn.close();
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
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void goToPatient(ActionEvent event){
       myController.setScreen(ScreensFramework.patientID);
    }
    
    @FXML
    private void goToBilling(ActionEvent event){
       myController.setScreen(ScreensFramework.billingID);
    }
    
    @FXML
    private void openCalendarSwing(ActionEvent event) {
        AppointmentDAO aptDAO = new AppointmentDAO();
        aptDAO.insertAppointment(new Appointment(new Date(), new Date()));
        ScheduleSwing2 screenCal = new ScheduleSwing2();
        screenCal.runCalendar();
    }

    @FXML
    private void goToLanding(ActionEvent event) {
        myController.setScreen(ScreensFramework.landingID);
    }

}