package rentacar.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Client;
import rentacar.Operator;
import rentacar.Opis;
import rentacar.Rent;

public class RentCarController implements Initializable {

	@FXML	private TextField clientPIN;
	@FXML	private TextField carSpecs;
	@FXML	private TableView<Car> cartableView;
	@FXML	private TableView<Client> clientTableView;
	@FXML	private DatePicker returnDate;
	@FXML	private ImageView carImg;
	final static Logger logger = Logger.getLogger(RentCarController.class);
	@FXML	private TableColumn<Client, String> clientNames;
	@FXML	private TableColumn<Client, String> clientPin;
	@FXML	private TableColumn<Client, String> clientDriveLic;
	@FXML	private TableColumn<Client, String> clientAddress;
	@FXML	private TableColumn<Client, Double> clientrating;
	@FXML	private AnchorPane apRent;
	
	@FXML	private void ChangeAPtoReturn() throws IOException
	{
		apRent.getChildren().clear();
		apRent.getChildren().add(FXMLLoader.load(getClass().getResource("ReturnCarView.fxml")));
	}
	

	public void showClientInfo() {//Function that adds Clients in table view

		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Client");
		ObservableList<Client> clientList = FXCollections.observableArrayList(query.list());

		session.getTransaction().commit();

		clientNames.setCellValueFactory(new PropertyValueFactory<Client, String>("clientName"));
		clientPin.setCellValueFactory(new PropertyValueFactory<Client, String>("clientPIN"));
		clientAddress.setCellValueFactory(new PropertyValueFactory<Client, String>("clientAddress"));
		clientDriveLic.setCellValueFactory(new PropertyValueFactory<Client, String>("clientDriveLicenceNumber"));
		clientrating.setCellValueFactory(new PropertyValueFactory<Client, Double>("clientRating"));
		
		clientTableView.setRowFactory(t -> new TableRow<Client>() {
		    @Override
		    public void updateItem(Client item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.getClientRating()<20) {
		            setStyle("-fx-background-color: salmon;");
		        } else {
		            setStyle("");
		        }
		    }
		});
		
		clientTableView.setItems(clientList);
		FilteredList<Client> filteredData = new FilteredList<>(clientList, p -> true);

		clientPIN.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (person.getClientName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (person.getClientPIN().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Client> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(clientTableView.comparatorProperty());
		clientTableView.setItems(sortedData);

	}
	
	@FXML
	TableColumn<Car, String> regNumberCol;
	@FXML
	TableColumn<Car, String> carModelCol;
	@FXML
	TableColumn<Car, Category> carCategoryCol;
	@FXML
	TableColumn<Car, Classification> carClassificatinCol;
	@FXML
	TableColumn<Car, Boolean> smoking;
	ObservableList<Car> list2;
	
	public void initCarTableView() {//Function that adds Car in table view
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query2 = session.createQuery("from Car c where c.carStatus='0'");
		list2 = FXCollections.observableArrayList(query2.list());
		session.getTransaction().commit();

		regNumberCol.setCellValueFactory(new PropertyValueFactory<Car, String>("regNumber"));

		carModelCol.setCellValueFactory(new PropertyValueFactory<Car, String>("carModel"));

		smoking.setCellValueFactory(new PropertyValueFactory<Car, Boolean>("smoking"));

		carCategoryCol.setCellValueFactory(new PropertyValueFactory<Car, Category>("category"));

		carClassificatinCol.setCellValueFactory(new PropertyValueFactory<Car, Classification>("classification"));

		cartableView.setItems(list2);

		// Begin of the filtration
		FilteredList<Car> filteredData = new FilteredList<>(list2, p -> true);

		carSpecs.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (person.getCarModel().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (person.getCategory().getCategoryType().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Car> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(cartableView.comparatorProperty());
		cartableView.setItems(sortedData);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCarTableView();
		showClientInfo();
		returnDate.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();
	            setDisable(empty || date.compareTo(today) < 0 );
	        }
	    });
	}

	public void imageViewUpdate() throws MalformedURLException {//Update image view
		try {
			Car car1 = cartableView.getSelectionModel().getSelectedItem();
			File file = new File(car1.getPhotoPath());
			String localUrl = file.toURI().toURL().toString();
			Image image = new Image(localUrl);
			carImg.setImage(image);
			carCheck.setText("");
		} catch (NullPointerException e) {
			System.out.print("NullPointerException caught");
		}
	}
	
	public void selectedClient() {
		clientCheck.setText("");
	}

	@FXML
	private Label clientCheck;
	@FXML
	private Label carCheck;
	@FXML
	private Label dateCheck;

	public void rentBtn() throws MalformedURLException {//Add to database

		if (clientTableView.getSelectionModel().getSelectedItem() == null)
			clientCheck.setText("Изберете клиент");
		else
			clientCheck.setText("");
		if (cartableView.getSelectionModel().getSelectedItem() == null)
			carCheck.setText("Изберете кола");
		else
			carCheck.setText("");

		if (returnDate.getValue() == null)
			dateCheck.setText("Изберете дата на връщане");
		else
			dateCheck.setText("");

		if (clientTableView.getSelectionModel().getSelectedItem() != null
				&& cartableView.getSelectionModel().getSelectedItem() != null && returnDate.getValue() != null) {
			LocalDate rtrnDate = returnDate.getValue();
			Operator operator = Singleton.getInstance().getLogedOperator();
			Car car1 = cartableView.getSelectionModel().getSelectedItem();
			Client choosenClient = clientTableView.getSelectionModel().getSelectedItem();

			Rent rent = new Rent(LocalDate.now().plusDays(1), rtrnDate.plusDays(1), 0, 0, operator, car1, choosenClient);
			Opis opis = new Opis(car1.getCurrKM(), LocalDate.now(), "Отдадена без проблем.", car1);

			Session session1 = rentacar.HibernateUtil.getSessionFactory().openSession();
			session1.beginTransaction();
			Car carCheck = (Car) session1.createQuery("from Car c where c.idCar='" + car1.getIdCar() + "'")
					.uniqueResult();
			session1.getTransaction().commit();

			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			if (!carCheck.isCarStatus()) {
				session.save(rent);
				session.save(opis);
				car1.setCarStatus(true);
				list2.remove(car1);
				session.update(car1);
				logger.info("Operator "+operator.getUserName()+" rented "+car1+" to a client "+choosenClient.getClientName()+choosenClient.getClientPIN());
			}
			session.getTransaction().commit();
			returnDate.getEditor().clear();
			cartableView.getSelectionModel().clearSelection();
			clientTableView.getSelectionModel().clearSelection();
			clientPIN.clear();
			carSpecs.clear();
			carImg.setImage(null);
		}
	}
	
	
	
}
