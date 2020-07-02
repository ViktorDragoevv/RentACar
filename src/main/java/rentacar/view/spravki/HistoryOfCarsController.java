package rentacar.view.spravki;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mysql.cj.xdevapi.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Car;
import rentacar.Rent;

public class HistoryOfCarsController implements Initializable {
@FXML private DatePicker fromdate;
@FXML private DatePicker todate;
@FXML private ComboBox<Car> carComboBox;
@FXML private TableView<Rent> tabRent;
@FXML private TableColumn<Rent,Car> tabCar;
@FXML private TableColumn<Rent,Client> tabClient;
@FXML private TableColumn<Rent,LocalDate> tab1Rent;
@FXML private TableColumn<Rent,LocalDate> tab2Rent;
@FXML private Label statusPeriod;
@FXML private void History(){
	
	LocalDate startDate = fromdate.getValue();
	LocalDate endDate = todate.getValue();
	Car carOp =(Car) carComboBox.getValue();
	
	if(startDate!=null && endDate!=null)
	{
	Session session1 = rentacar.HibernateUtil.getSessionFactory().openSession();
    session1.beginTransaction();
    
    
    Query query = session1.createQuery("from Rent r where Car_idCar='"+ carOp.getIdCar() +"' AND ( (dateRent BETWEEN '" + startDate + "' AND '" + endDate+ "') OR (dateReturn BETWEEN '" + startDate + "' AND '" + endDate + "') OR (dateRent < '"+startDate+"' AND dateReturn > '"+endDate+"'))"); 
	ObservableList<Rent> listRent = FXCollections.observableArrayList(query.list());
	
	  session1.getTransaction().commit();
	  tabRent.setItems(listRent);
   
	
	   }else
			statusPeriod.setText("Въведете коректен период!");
		
		
	
}

@Override
public void initialize(URL location, ResourceBundle resources) {
	
   
	// TODO Auto-generated method stub
	
	
    tabCar.setCellValueFactory( new PropertyValueFactory<Rent, Car>("Car"));
	tabClient.setCellValueFactory( new PropertyValueFactory<Rent, Client>("Client"));
	tab1Rent.setCellValueFactory( new PropertyValueFactory<Rent, LocalDate>("dateRent"));
	tab2Rent.setCellValueFactory( new PropertyValueFactory<Rent, LocalDate>("dateReturn"));
    
    Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Query query = session.createQuery("from Car"); 
	ObservableList<Car> listofcars = FXCollections.observableArrayList(query.list());
    session.getTransaction().commit();
    
    
    carComboBox.setItems(listofcars);
    carComboBox.getSelectionModel().select(0);


	
	

	
}

}
