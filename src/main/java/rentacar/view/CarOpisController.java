package rentacar.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import DataValidation.DataValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Opis;

public class CarOpisController implements Initializable {
	@FXML private TextField regNumber;
	@FXML private Label statusLabel;
	@FXML private TableView<Opis> opisTV;

	
	@FXML private void opisBtn() {// show opis for selected car
		String regNumb = regNumber.getText().toString();
		
		boolean regNumberValid = DataValidation.regNumber(regNumber, statusLabel, "Невалиден рег. номер");
		if(regNumberValid) {
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();	    
	    Query query = session.createQuery("from Opis o where o.car.regNumber='"+regNumb+"'"); 
		ObservableList<Opis> listOpis = FXCollections.observableArrayList(query.list());
		session.getTransaction().commit();
		opisTV.setItems(listOpis);
		regNumber.clear();
		}
	}
	@FXML	TableColumn<Opis, LocalDate> dateCol;
	@FXML	TableColumn<Opis, String> problemCol;
	@FXML	TableColumn<Opis, Double> kmCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dateCol.setCellValueFactory(new PropertyValueFactory<Opis, LocalDate>("date"));
		problemCol.setCellValueFactory(new PropertyValueFactory<Opis, String>("problems"));
		kmCol.setCellValueFactory(new PropertyValueFactory<Opis, Double>("currKM"));
	}

}
