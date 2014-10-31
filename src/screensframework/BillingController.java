// Billing Page controller
// INFS3605 AASA Technology

package screensframework;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import swing.ScheduleSwing2;

public class BillingController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @FXML
    public TextField totalInputOne;
    public TextField totalInputTwo;
    public TextField totalInputThree;
    public TextField totalInputFour;
    public TextField totalInputFive;
    public TextField totalInputSix;
    public TextField billCodeOne;
    public TextField billCodeTwo;
    public TextField billCodeThree;
    public TextField billCodeFour;
    public TextField billCodeFive;
    public TextField typeOne;
    public TextField typeTwo;
    public TextField typeThree;
    public TextField typeFour;
    public TextField typeFive;
    public Label grandTotalLabel;

    // declare variables
    String priceOne;
    String priceTwo;
    String priceThree;
    String priceFour;
    String priceFive;
    String priceSix;
    Connection conn = null;
    ResultSet results = null;
    PreparedStatement ps = null;    
    PreparedStatement insertInvoice = null;        
    String url = "jdbc:oracle:thin:z3417581/w8uhawAs@sage.business.unsw.edu.au:1521:orcl01";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TO DO
    }


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

    // Add Patient 
    public void insertInvoice() {
        
        openConnection();
        System.out.println("Invoice Added");
        try {
            insertInvoice = conn.prepareStatement("INSERT INTO BILLING (BILLABLE_ITEM_ONE, BILLABLE_ITEM_TWO, BILLABLE_ITEM_THREE, BILLABLE_ITEM_FOUR, BILLABLE_ITEM_FIVE, ITEM_CODE_ONE, ITEM_CODE_TWO, ITEM_CODE_THREE, ITEM_CODE_FOUR, ITEM_CODE_FIVE, TOTAL) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertInvoice.setString(1, typeOne.getText());
            insertInvoice.setString(2, typeTwo.getText());
            insertInvoice.setString(3, typeThree.getText());
            insertInvoice.setString(4, typeFour.getText());
            insertInvoice.setString(5, typeFive.getText());
            Integer billCodeInputOne = tryParseInt(billCodeOne.getText());
            insertInvoice.setInt(6, billCodeInputOne);
            Integer billCodeInputTwo = tryParseInt(billCodeTwo.getText());
            insertInvoice.setInt(7, billCodeInputTwo);
            Integer billCodeInputThree = tryParseInt(billCodeThree.getText());
            insertInvoice.setInt(8, billCodeInputThree);           
            Integer billCodeInputFour = tryParseInt(billCodeFour.getText());
            insertInvoice.setInt(9, billCodeInputFour);              
            Integer billCodeInputFive = tryParseInt(billCodeFive.getText());
            insertInvoice.setInt(10, billCodeInputFive);
            Integer total = 243;
            insertInvoice.setInt(11, total);

            insertInvoice.executeUpdate();

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
    //Function to calcualate grand total for invoice
    public void calculateTotal() {

            priceOne = totalInputOne.getText();
            priceTwo = totalInputTwo.getText();
            priceThree = totalInputThree.getText();
            priceFour = totalInputFour.getText();
            priceFive = totalInputFive.getText();
            priceSix = totalInputSix.getText();
            
            // convert strings into integers
            Integer resultOne = tryParseInt(priceOne);
            Integer resultTwo = tryParseInt(priceTwo);
            Integer resultThree = tryParseInt(priceThree);
            Integer resultFour = tryParseInt(priceFour);
            Integer resultFive = tryParseInt(priceFive);
            Integer resultSix = tryParseInt(priceSix);
            
            // Compute the total of invoice
            Integer grandTotal = resultOne + resultTwo + resultThree
                    + resultFour + resultFive - resultSix;
            
            System.out.println(grandTotal);
            
            grandTotalLabel.setText("$ " + grandTotal);
            
    }
  
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToLogin(ActionEvent event){
       myController.setScreen(ScreensFramework.loginID);
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
    private void goToScreen4(ActionEvent event){
       myController.setScreen(ScreensFramework.screen4ID);
    }

    @FXML
    private void goToCalendar(ActionEvent event){
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
