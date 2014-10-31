// Landing Page
// INFS 3605 AASA Technology

package screensframework;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty; 
import javafx.beans.value.ObservableValue; 
import javafx.scene.control.TableColumn.CellDataFeatures; 
import javafx.scene.layout.VBox;
import javafx.util.Callback; 
import swing.ScheduleSwing2;

public class PatientController implements Initializable , ControlledScreen  {

    ScreensController myController;

    @FXML
    public javafx.scene.control.Button closeButton;
    public TableView tableview; 
    public ObservableList<ObservableList> data;
    public TableColumn colFirstName;
    public TextField patSearchString;
    
    
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

    // Connect to database
    @FXML
    public void buildData(){

        data = FXCollections.observableArrayList();

        // Get the search string (if exists, otherwise null will be returned)
        String searchTerm = myController.getSessionValue("PatientSearchTerm");
          
        String SQL = "";
          
        if (searchTerm == null || searchTerm == "") {
            SQL = "SELECT * FROM PATIENTS";
        } else {
            SQL = "SELECT * FROM PATIENTS WHERE FIRST_NAME LIKE '%" + searchTerm + "%'";
        }
          
        // Reset search string to stop filtering if they navigate back here next time
        myController.deleteSessionValue("PatientSearchTerm");
        
        try{
            openConnection();
            
            //ResultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            // Table Column added dynamically
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                // Add dnyamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
               
                tableview.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] " + col.getText());
            }

            // Add data to observablelist
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    Object item = rs.getObject(i);
                    String strValue = (item == null ? "" : item.toString());
                    row.add(strValue);
                }
                System.out.println("Row [1] added "+row+" SIZE:" + row.size());
                data.add(row);
            }
            
            // Add to tableview
            tableview.setItems(data);
        } catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
    }
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
        
        // Testing search term setup
        //myController.setSessionValue("PatientSearchTerm", "lark");        
        buildData();
    }
 
    @FXML
    private void loadFilteredTable() {
        
        myController.setSessionValue("PatientSearchTerm", patSearchString.getText());
        
        // goToPatient - reloads data
        myController.unloadScreen(ScreensFramework.patientID);
        myController.loadScreen(ScreensFramework.patientID, ScreensFramework.patientFX);
        myController.setScreen(ScreensFramework.patientID);
    }
    
    @FXML
    private void goViewPatient() {

        String selectedPatient = tableview.getSelectionModel().getSelectedItem().toString();
        
        String[] patientArray = selectedPatient.split(",");
        String patientID = patientArray[0].replaceAll("\\D+","");

        myController.setSessionValue("CurrentViewPatient", patientID);
        
        myController.unloadScreen(ScreensFramework.patientViewID);
        myController.loadScreen(ScreensFramework.patientViewID, ScreensFramework.patientViewFX);
        myController.setScreen(ScreensFramework.patientViewID);
    }
    
    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToPatientAdd(ActionEvent event) {
        myController.setScreen(ScreensFramework.patientAddID);
    }
    
    @FXML
    private void goToLogin(ActionEvent event){
       myController.setScreen(ScreensFramework.loginID);
    }

    @FXML
    private void goToPatient(ActionEvent event){
        myController.setScreen(ScreensFramework.patientID);

        myController.unloadScreen(ScreensFramework.patientID);
        myController.loadScreen(ScreensFramework.patientID, ScreensFramework.patientFX);
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
    private void goToPatientView(ActionEvent event){
        myController.setScreen(ScreensFramework.patientViewID);
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

    @FXML
    private void goToClinicalMeasures(ActionEvent event) {
        myController.setScreen(ScreensFramework.clinicalID);
    }

}
