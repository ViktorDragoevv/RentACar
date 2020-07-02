package rentacar.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import DataValidation.DataValidation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rentacar.App;
import rentacar.Operator;

public class LoginControler implements Initializable {

	@FXML	private TextField textUsername;
	@FXML	private PasswordField textPassword;
	@FXML 	private Label loginStatus;
	
	@FXML private Label loginVerUser;
	@FXML private Label loginVerPass;
	final static Logger logger = Logger.getLogger(LoginControler.class);
	
	@FXML	private void login() throws IOException {

		String username = textUsername.getText().toString();
		String password = textPassword.getText().toString();
		Operator logedOperator;
		Stage stage = (Stage) loginStatus.getScene().getWindow();
		
		if (username.equals("admin") && password.equals("admin")) {
			// Opening OperatorMainView
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/AdminMainView.fxml"));
			BorderPane AdminMainView = loader.load();

			Stage adminMainStage = new Stage();
			adminMainStage.setTitle("Администраторски панел");
			
			adminMainStage.initModality(Modality.WINDOW_MODAL);
			adminMainStage.getIcons().add(new Image("/photos/icon11.png"));
			Scene scene = new Scene(AdminMainView);
			adminMainStage.setScene(scene);
			adminMainStage.show();
			stage.close();
			logger.info("Admin logged in");
	    	
		} else {
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			logedOperator = (Operator) session
					.createQuery(
							"from Operator s where s.userName='" + username + "' AND s.password='" + password + "' AND s.statusLogin='0'")
					.uniqueResult();			
			if (logedOperator != null) {
				logger.info("Operator "+logedOperator.getUserName()+"-"+logedOperator.getNameOfOperator()+" logged in.");
		    	
				logedOperator.setStatusLogin(true);
				session.update(logedOperator);
				session.getTransaction().commit();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(App.class.getResource("view/OperatorMainView.fxml"));
				BorderPane OperatorMainView = loader.load();

				Stage operatorMainStage = new Stage();
				operatorMainStage.setTitle("Оператор - " + logedOperator.getNameOfOperator()+" ["+ logedOperator.getCompany().getCompanyName()+"]");
				operatorMainStage.initModality(Modality.WINDOW_MODAL);
				operatorMainStage.getIcons().add(new Image("/photos/icon11.png"));
				operatorMainStage.setResizable(false);
				
				Scene scene = new Scene(OperatorMainView);
				operatorMainStage.setScene(scene);
				operatorMainStage.show();
				
				operatorMainStage.setOnCloseRequest((WindowEvent event1) -> {
				     Operator loged = Singleton.getInstance().getLogedOperator();
					 Session session1 = rentacar.HibernateUtil.getSessionFactory().openSession();
					 session1.beginTransaction();
					 loged.setStatusLogin(false);
					 session1.update(loged);
					 session1.getTransaction().commit();
					 logger.info("Operator "+loged.getUserName()+"-"+loged.getNameOfOperator()+" logged out");
			    });
				
				
				Singleton.getInstance().setLogedOperator(logedOperator);
				stage.close();	
							 
			} else
			{
				loginStatus.setText("Грешно потребителско име или парола");
				session.getTransaction().commit();
			}
			
		}

		 boolean LoginValidationUserr = DataValidation.LoginValidationUser(textUsername, loginVerUser, "  ");
		 boolean LoginValidationPasss = DataValidation.textFieldIsNull(textPassword, loginVerPass, "  ");
		
		if (!LoginValidationUserr)
			loginVerUser.setText("Напишете коректно потребителско име");
		else
			loginVerUser.setText("");
		
		if (LoginValidationPasss)
			loginVerPass.setText("Моля напишете парола");
		else
		  loginVerPass.setText("");
		

		
	}

	public void handleEnter(KeyEvent keyEvent) throws IOException {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	@FXML
	private ImageView carLoginImg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	      
		Image image = new Image("/photos/carLogin.png");
		carLoginImg.setImage(image);
	}
	

}
