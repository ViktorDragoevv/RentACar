package rentacar.view.spravki;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Client;

public class ClientRatingController implements Initializable {
	
	@FXML private TableView<Client>	clientTableView;
	@FXML private TableColumn<Client,Double> clientRating;
	@FXML private TableColumn<Client,String> clientNames;
	@FXML private TableColumn<Client, String> clientPin;
	@FXML private TableColumn<Client, String> clientDriveLic;
	@FXML private TableColumn<Client, String> clientAddress;
	
	@FXML private void setRating() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    
	    Query query = session.createQuery("from Client"); 
		ObservableList<Client> listClient = FXCollections.observableArrayList(query.list());
		session.getTransaction().commit();
		
		clientRating.setCellValueFactory(
                new PropertyValueFactory<Client, Double>("clientRating"));
		clientNames.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientName"));
        clientPin.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientPIN"));
        clientAddress.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientAddress"));       
        clientDriveLic.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientDriveLicenceNumber")); 
        
        clientTableView.setItems(listClient);
		
		clientRating.setSortType(TableColumn.SortType.ASCENDING);
        clientTableView.getSortOrder().add(clientRating);
	    
	}
}
