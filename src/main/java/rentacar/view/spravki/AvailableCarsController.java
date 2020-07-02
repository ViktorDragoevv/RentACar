package rentacar.view.spravki;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Rent;

public class AvailableCarsController {
		
	//nalichni avtomobili
	@FXML private DatePicker dateFrom;
	@FXML private DatePicker dateTo;
	@FXML private TableView<Car> carTableView;
	@FXML private TableColumn<Car,String> regNumberCol;
	@FXML private TableColumn<Car,String> carModelCol;
	@FXML private TableColumn<Car,Category> carCategoryCol;
	@FXML private TableColumn<Car,Classification> carClassificatinCol;
	@FXML private TableColumn<Car,Boolean> smoking;
	@FXML private Label statusPeriod;
	
	@FXML private void availableCars() {
	LocalDate startDate = dateFrom.getValue();
	LocalDate endDate = dateTo.getValue();
	
	if(startDate!=null && endDate!=null)
	{
		statusPeriod.setText("");
	Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    
    Query query = session.createQuery("from Car"); 
	ObservableList<Car> listCar = FXCollections.observableArrayList(query.list());

	Query query1 = session.createQuery("from Rent where (dateRent BETWEEN '" + startDate + "' AND '" + endDate+ "') OR (dateReturn BETWEEN '" + startDate + "' AND '" + endDate + "') OR (dateRent < '"+startDate+"' AND dateReturn > '"+endDate+"')"); 
	ObservableList<Rent> listRent = FXCollections.observableArrayList(query1.list());
	
	regNumberCol.setCellValueFactory(
            new PropertyValueFactory<Car, String>("regNumber"));

    carModelCol.setCellValueFactory(
            new PropertyValueFactory<Car, String>("carModel"));
    
    smoking.setCellValueFactory(
            new PropertyValueFactory<Car, Boolean>("carStatus"));

    carCategoryCol.setCellValueFactory(
            new PropertyValueFactory<Car, Category>("category"));
    
    carClassificatinCol.setCellValueFactory(
            new PropertyValueFactory<Car, Classification>("classification"));
	
	for (Rent rent : listRent) {
		listCar.remove(rent.getCar());
	}
    session.getTransaction().commit();
    carTableView.setItems(listCar);
    }else
		statusPeriod.setText("Въведете коректен период!");
	}
	
}


