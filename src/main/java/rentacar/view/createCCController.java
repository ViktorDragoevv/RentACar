package rentacar.view;

import org.hibernate.Session;

import DataValidation.DataValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rentacar.Category;
import rentacar.Classification;

public class createCCController  {
	// Add Classification
		@FXML
		private TextField typeClassification;
		@FXML
		private TextField classificationPricePerDay;
		@FXML
		private TextField classificationPricePerKM;
		@FXML
		private Label labelClassT;
		@FXML
		private Label labelClassP;
		@FXML
		private Label labelClassK;
		@FXML
		private Label labelClassSucc;

		public void addClassification() {//Function that add classification
			
			boolean alphabetName = DataValidation.textAlphabet(typeClassification, labelClassT,
					"Моля, въведете коректен тип клас");
			boolean numericPriceDay = DataValidation.textDouble(classificationPricePerDay, labelClassP,
					"Моля, въведете коректно число");
			boolean numericPriceKM = DataValidation.textDouble(classificationPricePerKM, labelClassK,
					"Моля, въведете коректно число");
			if (alphabetName && numericPriceDay && numericPriceKM) {
				String classificationType = typeClassification.getText().toString();
				Double pricePerDay = Double.parseDouble(classificationPricePerDay.getText().toString());
				Double pricePerKM = Double.parseDouble(classificationPricePerKM.getText().toString());
				Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				Classification classificationCheck = (Classification) session
						.createQuery("from Classification c where c.classificationType='" + classificationType + "'").uniqueResult();
				if (classificationCheck == null) {
				Classification cl = new Classification(classificationType, pricePerDay, pricePerKM);
				session.save(cl);
				session.getTransaction().commit();
				labelClassSucc.setText("Успешно добавен клас");
				typeClassification.clear();
				classificationPricePerDay.clear();
				classificationPricePerKM.clear();
				}
				else
					labelClassSucc.setText("Вече съществува такъв клас!");
				session.getTransaction().commit();
			} 
			
		}

		// Add Category
		@FXML
		private TextField typeCategory;
		@FXML
		private Label categoryLabel;

		public void addCategory() {
			boolean categoryCheck = DataValidation.textFieldIsNull(typeCategory, categoryLabel, "Моля, въведете категория!");

			if (!categoryCheck) {
				String categoryTypeString = typeCategory.getText().toString();
				Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				Category categoryCheckDubl = (Category) session
						.createQuery("from Category c where CategoryType='" + categoryTypeString + "'").uniqueResult();
				if(categoryCheckDubl==null)
				{
				Category ctgr = new Category(categoryTypeString);
				session.save(ctgr);
				categoryLabel.setText("Успешно въведена категория!");
				typeCategory.clear();
				
				}			
				else
				categoryLabel.setText("Вече съществува такава категория!");
				session.getTransaction().commit();
				
			} 

		}
		

}
