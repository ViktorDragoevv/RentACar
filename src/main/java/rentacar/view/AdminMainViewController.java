package rentacar.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import DataValidation.DataValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rentacar.Company;
import rentacar.Operator;

public class AdminMainViewController implements Initializable {
	
	@FXML private AnchorPane newClientAP= new AnchorPane();
	@FXML private AnchorPane newCarap= new AnchorPane();
	@FXML private AnchorPane addCategroyClassAP= new AnchorPane();
	@FXML private AnchorPane addClientCompanyAP;
	@FXML private AnchorPane availableCarsAP= new AnchorPane();
	@FXML private AnchorPane historyOfCarsAP= new AnchorPane();
	@FXML private AnchorPane operatorsWorkAP= new AnchorPane();
	@FXML private AnchorPane clinetRatingAP= new AnchorPane();
	@FXML private AnchorPane statisticskAP= new AnchorPane();
	@FXML private BorderPane mainBP;
	final static Logger logger = Logger.getLogger(AdminMainViewController.class);
	
	@FXML	public void NewCarMenuItem() throws IOException {	
		newCarap.getChildren().clear();
		newCarap.getChildren().add(FXMLLoader.load(getClass().getResource("RegisterCarView.fxml")));
		mainBP.setCenter(newCarap);
	}
	@FXML	public void NewClientMenuItem() throws IOException {	
		newClientAP.getChildren().clear();
		newClientAP.getChildren().add(FXMLLoader.load(getClass().getResource("RegisterClientView.fxml")));
		mainBP.setCenter(newClientAP);
	}
	@FXML	public void addCategoryClassMenuItem() throws IOException {	
		addCategroyClassAP.getChildren().clear();
		addCategroyClassAP.getChildren().add(FXMLLoader.load(getClass().getResource("createCategoryClassification.fxml")));
		mainBP.setCenter(addCategroyClassAP);
	}
	
	@FXML	public void addClientCompanyMenuItem() throws IOException {	
		mainBP.setCenter(addClientCompanyAP);
	}
	
	
	@FXML	public void availableCarsMenuItem() throws IOException {		
		availableCarsAP.getChildren().clear();
		availableCarsAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/AvailableCarsView.fxml")));
		mainBP.setCenter(availableCarsAP);
	}
	@FXML	public void historyOfCarsMenuItem() throws IOException {		
		historyOfCarsAP.getChildren().clear();
		historyOfCarsAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/HistoryOfCarsView.fxml")));
		mainBP.setCenter(historyOfCarsAP);
	}
	@FXML	public void operatorsWorkMenuItem() throws IOException {		
		operatorsWorkAP.getChildren().clear();
		operatorsWorkAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/OperatorsWorkView.fxml")));
		mainBP.setCenter(operatorsWorkAP);
	}
	
	@FXML	public void clientRatingMenuItem() throws IOException {		
		clinetRatingAP.getChildren().clear();
		clinetRatingAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/ClientRatingView.fxml")));
		mainBP.setCenter(clinetRatingAP);
	}
	@FXML	public void statisticsMenuItem() throws IOException {		
		statisticskAP.getChildren().clear();
		statisticskAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/StatisticsView.fxml")));
		mainBP.setCenter(statisticskAP);
	}
	
	@FXML	public void logout() throws IOException {		
		 Stage stage = (Stage) mainBP.getScene().getWindow();
		 stage.close();
	
		Stage primaryStage = new Stage();		
			try {			
				Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.getIcons().add(new Image("/photos/icon11.png"));
				primaryStage.setTitle("Коли под наем");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		logger.info("Admin logged out");
	}
	
	@FXML
	private TextField operatorUsername;
	@FXML
	private PasswordField operatorPassword;
	@FXML
	private TextField operatorNames;
	@FXML
	private ComboBox<Company> companyComboBox;
	@FXML
	private Button addOperatorBtn;
	private ObservableList<Company> list;
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Company");
		list = FXCollections.observableArrayList(query.list());
		companyComboBox.getItems().setAll(list);
	}

	@FXML private Label usernameLabel;
	@FXML private Label passwordLabel;
	@FXML private Label namesLabel;
	@FXML private Label companyLabel;
	@FXML private Label addOperatorStatusLabel;
	
	@FXML
	private void addOperator() { //Function that adds Operators
		 boolean usernameCheck = DataValidation.textFieldIsNull(operatorUsername, usernameLabel, "Веведете потребителско име");
		 boolean passwordCheck = DataValidation.textFieldIsNull(operatorPassword, passwordLabel, "Веведете парола");
		 boolean namesCheck = DataValidation.textAlphabet(operatorNames, namesLabel, "Въведете коректни имена");
		 if(companyComboBox.getSelectionModel().isEmpty())
				companyLabel.setText("Изберете фирма");
		 else
			 companyLabel.setText("");
		 
		 if(!usernameCheck && !passwordCheck && namesCheck && !companyComboBox.getSelectionModel().isEmpty())
		 {
			String opUsername = operatorUsername.getText().toString();
			String opPassword = operatorPassword.getText().toString();
			String opNames = operatorNames.getText().toString();
			Company cmp = (Company) companyComboBox.getValue();
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Operator OperatorCheck = (Operator) session
					.createQuery("from Operator o where o.userName='" + opUsername + "'").uniqueResult();
			if(OperatorCheck==null)
			{
						Operator oper = new Operator(opUsername, opPassword, opNames, false, cmp);
						session.save(oper);
						addOperatorStatusLabel.setText("Успешно добавен оператор");
						operatorUsername.clear();
						operatorPassword.clear();
						operatorNames.clear();
						companyComboBox.getSelectionModel().clearSelection();
			}
			else
				addOperatorStatusLabel.setText("Вече съществува такъв оператор!");
				session.getTransaction().commit();
		 }
		 
	}
	// Add Company Tab
	@FXML
	private TextField companyName;
	@FXML
	private TextField companyAddress;
	@FXML
	private Label companyNameLabel;
	@FXML
	private Label companyAddressLabel;
	@FXML
	private Label addCompanyStatus;

	@FXML
	private void addCompany() {

		boolean nameCheck = DataValidation.textFieldIsNull(companyName, companyNameLabel, "Въведете име на фирма!");
		boolean addressCheck = DataValidation.textFieldIsNull(companyAddress, companyAddressLabel,
				"Въведете адрес на фирма!");

		if (!nameCheck && !addressCheck) {
			String companyName1 = companyName.getText().toString();
			String companyAddress1 = companyAddress.getText().toString();

			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Company companyCheck = (Company) session
					.createQuery("from Company c where c.companyName='" + companyName1 + "'").uniqueResult();
			if (companyCheck == null) {
				Company cmpn = new Company(companyName1, companyAddress1);
				session.save(cmpn);
				addCompanyStatus.setText("Успешно добавена фирма");
				companyName.clear();
				companyAddress.clear();
				list.add(cmpn);
				companyComboBox.getItems().setAll(list);

			} else
				addCompanyStatus.setText("Вече съществува такава фирма!");

			session.getTransaction().commit();

		}
	}
	

}
