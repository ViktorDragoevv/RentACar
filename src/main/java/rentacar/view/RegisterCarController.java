package rentacar.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import DataValidation.DataValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;

public class RegisterCarController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Category");
		ObservableList<Category> list = FXCollections.observableArrayList(query.list());
		categoryChoiceBox.setItems(list);

		Query query2 = session.createQuery("from Classification");
		ObservableList<Classification> list2 = FXCollections.observableArrayList(query2.list());
		classificationChoiceBox.setItems(list2);

		session.getTransaction().commit();

	}

	// ADD CAR
	String fileAsString = "";
	@FXML
	private TextField regNumber;
	@FXML
	private TextField carModel;
	@FXML
	private ChoiceBox<Category> categoryChoiceBox;
	@FXML
	private ChoiceBox<Classification> classificationChoiceBox;
	@FXML
	private TextField currentKM;
	@FXML
	private Button choosePictureBtn;
	@FXML
	private CheckBox smokingCheckBox;
	@FXML
	private ImageView img1;
	@FXML
	private Label statusAdded;

	@FXML
	private Label regNumberLabel;
	@FXML
	private Label carModelLabel;
	@FXML
	private Label categoryChoiceBoxLabel;
	@FXML
	private Label ClassificationChoiceBoxLabel;
	@FXML
	private Label currKMLabel;
	@FXML
	private Label pictureLabel;

	@FXML
	private void choosePicture() throws MalformedURLException {// Function that select and add picture
		img1.setImage(null);
		Stage stage = new Stage();
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(imageFilter);
		File file = chooser.showOpenDialog(stage);
		if (file != null) {
			fileAsString = file.toString();
			String localUrl = file.toURI().toURL().toString();
			Image image = new Image(localUrl);
			img1.setImage(image);
			pictureLabel.setText("");
		} else {
			System.out.println("Not choosed image");
		}

	}

	public void addNewCar() {
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		String regNumb = regNumber.getText().toString();
		String carMdl = carModel.getText().toString();
		Category carCategory = (Category) categoryChoiceBox.getValue();
		Classification carClassification = (Classification) classificationChoiceBox.getValue();
		String km = currentKM.getText().toString();

		boolean smokingStatus = smokingCheckBox.isSelected();

		boolean regNumberValid = DataValidation.regNumber(regNumber, regNumberLabel, "Невалиден рег номер");
		boolean carModelNotNull = DataValidation.textFieldIsNull(carModel, carModelLabel, "Моля, въведете модел");
		boolean numericCurrKm = DataValidation.textNumeric(currentKM, currKMLabel, "КМ може да съдаржа само 0 - 9");
		if (fileAsString.equals(""))
			pictureLabel.setText("Изберете снимка");
		boolean categoryCheck = categoryChoiceBox.getSelectionModel().isEmpty();
		boolean classCheck = classificationChoiceBox.getSelectionModel().isEmpty();
		if (categoryCheck)
			categoryChoiceBoxLabel.setText("Моля изберете категория");
		else
			categoryChoiceBoxLabel.setText("");
		if (classCheck)
			ClassificationChoiceBoxLabel.setText("Моля изберете клас");
		else
			ClassificationChoiceBoxLabel.setText("");
		if (classCheck)
			ClassificationChoiceBoxLabel.setText("Моля изберете клас");
		else
			ClassificationChoiceBoxLabel.setText("");

		if (regNumberValid && !carModelNotNull && numericCurrKm && !fileAsString.equals("") && !categoryCheck
				&& !classCheck) {
			double currKm = Double.parseDouble(km);
			Car carCheck = (Car) session.createQuery("from Car s where s.regNumber='" + regNumb + "'").uniqueResult();

			if (carCheck == null) {
				Car car1 = new Car(regNumb, carMdl, smokingStatus, false, currKm, fileAsString, carClassification,
						carCategory);
				session.save(car1);
				statusAdded.setTextFill(Color.web("#00FF00"));
				statusAdded.setText("Успешно добавен автомобил!");
				regNumber.clear();
				carModel.clear();
				currentKM.clear();
				smokingCheckBox.setSelected(false);
				img1.setImage(null);
				fileAsString = "";

			} else {
				statusAdded.setTextFill(Color.web("#FF0000"));
				statusAdded.setText("Съществуващ автомобил!");
			}

		} else {
			statusAdded.setTextFill(Color.web("#FF0000"));
			statusAdded.setText("Грешни полета!");
		}
      session.getTransaction().commit();
	}	

}
