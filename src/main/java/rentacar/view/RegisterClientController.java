package rentacar.view;

import org.hibernate.Session;

import DataValidation.DataValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rentacar.Client;

public class RegisterClientController {
	
	public static boolean checkCl(Client c) {
		if(c==null)
			return true;
		else return false;

	}
	
	Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	//Add Client
		@FXML	private TextField clPin;
		@FXML 	private TextField clNames;
		@FXML	private TextField clAddress;
		@FXML 	private TextField clDrLicence;
		@FXML 	private Label clStatusAdd;
		
		//ValidationLabels
		@FXML private Label pinLabel;
		@FXML private Label namesLabel;
		@FXML private Label adressLabel;
		@FXML private Label drLicNumbLabel;
		
		@FXML private void addOperatorBtn() {
			String cPin = clPin.getText().toString();
			String cNames = clNames.getText().toString();
			String cAddress = clAddress.getText().toString();
			String cDrLicence = clDrLicence.getText().toString();
			
			 boolean numericPin = DataValidation.textNumeric(clPin, pinLabel, "ЕГН може да съдаржа само 0 - 9");
			 boolean alphabetName = DataValidation.textAlphabet(clNames, namesLabel, "Може да съдържа само  а - я");
			 boolean addressNotNull = DataValidation.textFieldIsNull(clAddress, adressLabel, "Моля, въведете адрес");
			 boolean numericDrLicNUmb = DataValidation.textNumeric(clDrLicence, drLicNumbLabel, "Номер на Ш.К само 0 - 9");
			 
			 if(numericPin && alphabetName && !addressNotNull && numericDrLicNUmb)
			 {
			        session.beginTransaction();
					Client cliCheck = (Client) session.createQuery("from Client s where s.clientPIN='" + cPin + "'").uniqueResult();
			        
				 if(checkCl(cliCheck))
				 {
					 Client client1 = new Client(cNames, cPin, cAddress, 50, cDrLicence);
				        session.save(client1);
				        clStatusAdd.setText("Успешно регистриран клиент!");
				        				        
				 }else {
					 clStatusAdd.setText("Съществуващ клиент!");
				 }
				 
				session.getTransaction().commit();
				 
					clPin.clear(); clNames.clear(); clAddress.clear();clDrLicence.clear();
								 }
			 else {
				 clStatusAdd.setText("Грешни полета!");
			 }
			
		}
}
