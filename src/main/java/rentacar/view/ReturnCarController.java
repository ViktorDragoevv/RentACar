package rentacar.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import DataValidation.DataValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Car;
import rentacar.Client;
import rentacar.Operator;
import rentacar.Opis;
import rentacar.Rent;

public class ReturnCarController implements Initializable{
	@FXML TextField clientPin;
	@FXML ListView<Rent> rentListView;
	@FXML TextField currKM;
	@FXML CheckBox problemsCheckBox;
	@FXML TextField problemsTextField;
	@FXML Label problemsLabel;
	final static Logger logger = Logger.getLogger(ReturnCarController.class);
	
		@FXML public void enableProblemsField() {
	
		if(problemsCheckBox.isSelected())
		{
			problemsTextField.setVisible(true);
			problemsLabel.setVisible(true);
			problemsTextField.requestFocus();
		}
		else
		{
			problemsTextField.setVisible(false);
			problemsLabel.setVisible(false);
		}
		
	}
		
		@FXML private TableView<Rent> rentTableView;
		@FXML private TableColumn<Rent,LocalDate> rentDayColumn;
		@FXML private TableColumn<Rent,LocalDate> returnDayColumn;
		@FXML private TableColumn<Rent, Car> rentCarColumn;
		@FXML private TableColumn<Rent, Integer> rentIdRentColumn;
		@FXML private TableColumn<Rent, Client> rentClientColumn;
		
		ObservableList<Rent> list1;
		public void updateRentListView() {
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Rent s where s.completedStatus='0'");
			list1 = FXCollections.observableArrayList(query.list());
			 session.getTransaction().commit();
			
			rentDayColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, LocalDate>("dateRent"));
			returnDayColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, LocalDate>("dateReturn"));
			rentCarColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Car>("car"));
			rentIdRentColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Integer>("idRent"));
			rentClientColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Client>("client"));
			
			rentTableView.setItems(list1);
			
			FilteredList<Rent> filteredData = new FilteredList<>(list1, p -> true);

			clientPin.textProperty().addListener((observable, oldValue, newValue) -> {
	            filteredData.setPredicate(rent -> {
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                }
	                String lowerCaseFilter = newValue.toLowerCase();
	                
	                if (rent.getClient().getClientPIN().toLowerCase().contains(lowerCaseFilter)) {
	                    return true;
	                }
	                	return false;
	                              
	            });
	        });
	    
	        SortedList<Rent> sortedData = new SortedList<>(filteredData);
	        sortedData.comparatorProperty().bind(rentTableView.comparatorProperty());
	        rentTableView.setItems(sortedData); 
	        
	        LocalDate today = LocalDate.now();
	        
	        rentTableView.setRowFactory(t -> new TableRow<Rent>() {
			    @Override
			    public void updateItem(Rent item, boolean empty) {
			        super.updateItem(item, empty) ;
			        if (item == null) {
			            setStyle("");
			        } else if (item.getDateReturn().isBefore(today)) {
			            setStyle("-fx-background-color: salmon;");
			        } else if (item.getDateReturn().isEqual(today)){
			            setStyle("-fx-background-color: #ffad99;");
			        }
			    }
			});
		}

		@FXML private Button notRed;
		ObservableList<Rent> notificationList;
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			updateRentListView();
			
			Runnable task = new Runnable() {
				@Override
				public void run() {
					LocalDate today = LocalDate.now();
	        		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	        		session.beginTransaction();
	        		Query query = session.createQuery("from Rent r where r.completedStatus='0' AND r.dateReturn='"+today+"'");
	        		notificationList = FXCollections.observableArrayList(query.list());
	        		session.getTransaction().commit();
	        		if(!notificationList.isEmpty()) {
	        			notRed.setVisible(true);
	        			notRed.setStyle("-fx-background-color: salmon;");
	        		}				
	        		else
						notRed.setVisible(false);
				}
			};

		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(task, 3, 30, TimeUnit.SECONDS);
		
		}
		@FXML private void notRedBtn() {
			 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Известие");
			 alert.setHeaderText(null);
			 alert.setContentText("Изтичащи наемания днес:\n"+notificationList);
			 alert.showAndWait();
		}
		
		@FXML private Label rentChoosenLabel;
		@FXML private Label correctKMLabel;
		@FXML private Label retrunStatus;
		
		public static double calcRaiting(Client c, double number) {
			return c.getClientRating()-number;
		}
		
		public static  double calcPrice(Car rentedCar,Rent rent,double traveledKm, boolean problems) {//Function that calculate price
			LocalDate today = LocalDate.now();
			Period p = Period.between(rent.getDateRent(),today);
			
			Double totalPrice = p.getDays()*rentedCar.getClassification().getPricePerDay()+rentedCar.getClassification().getPricePerKM()*traveledKm;
			if(rent.getDateReturn().isBefore(today)) {
				totalPrice+=totalPrice*0.08;  //8% vrushtane sled sroka
				rent.setDateReturn(today.plusDays(1));
				rent.getClient().setClientRating(calcRaiting(rent.getClient(), 5));
			}
			if(problems) {
				totalPrice+=50;
				rent.getClient().setClientRating(calcRaiting(rent.getClient(), 3));
			}
			return totalPrice;
		}
		
		public static double calcTraveledKm(double a,double b) {
			return a-b;
		}

		@FXML private void returnCarBtn() {
			
			boolean numericKM = DataValidation.textNumeric(currKM, correctKMLabel, "Въведете коректни данни 0-9");
			if(rentTableView.getSelectionModel().getSelectedItem()==null)
			rentChoosenLabel.setText("Изберете наемане");
			else
				rentChoosenLabel.setText("");
			
			if(numericKM && rentTableView.getSelectionModel().getSelectedItem()!=null)
			{
			Double newKm = Double.parseDouble(currKM.getText().toString());
			Rent rent= rentTableView.getSelectionModel().getSelectedItem();
			Car rentedCar = rent.getCar();
			LocalDate today = LocalDate.now();
			
			//Double traveledKm = newKm - rentedCar.getCurrKM();
			double traveledKm = calcTraveledKm(newKm, rentedCar.getCurrKM());
			double totalPrice=calcPrice(rentedCar, rent,traveledKm,problemsCheckBox.isSelected());
			
			rent.setTraveledKM(traveledKm);
			rent.setTotalPrice(totalPrice);
			rent.setCompletedStatus(true);
			
			rentedCar.setCarStatus(false);
			rentedCar.setCurrKM(newKm);
			
			String returnProblems = "Върната без проблем.";
			if(problemsCheckBox.isSelected())
				returnProblems = "Върната - "+problemsTextField.getText().toString();
			
			Opis returnOpis = new Opis(newKm, today, returnProblems, rentedCar);
			
			 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		     session.beginTransaction();	
		     session.update(rent);
		     session.update(rentedCar);
		     session.save(returnOpis);
	     
		     Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Наемане " + rent.getIdRent());
			 alert.setHeaderText(null);
			 alert.setContentText("Пероид: " + rent.getDateRent()+ "/"+ today+ " \n" + "Клиент: " + rent.getClient().getClientName()+ "\n" + "Изминати километри: " + traveledKm+ "\n" + "Кола: " + rentedCar+ "\n" + "Дължима сума: " + totalPrice +" лв ");
			 alert.showAndWait();
			 
			 list1.remove(rent);
		     session.getTransaction().commit();
			Operator operator = Singleton.getInstance().getLogedOperator();
		    logger.info("Client "+rent.getClient().getClientName()+" "+rent.getClient().getClientPIN()+" returned car "+rentedCar+" to operator "+operator.getUserName());
			currKM.clear();
			clientPin.clear();
			problemsTextField.clear();
			
			}
			
		}

}
