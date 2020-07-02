package rentacar.view.spravki;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;

public class StatisticsController implements Initializable{
	@FXML private DatePicker fromDate;
	@FXML private DatePicker toDate;
	@FXML private ChoiceBox<String> filterChoiseBox;
	
		@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		filterChoiseBox.getItems().add("Клас");
		filterChoiseBox.getItems().add("Категория");
		filterChoiseBox.getSelectionModel().selectFirst();
		yAxis.setLabel("Брой"); 
	}
	
		@FXML private BarChart<String,Number>  barChart;
		@FXML private CategoryAxis xAxis;
		@FXML private NumberAxis yAxis;
		@FXML private Label statusPeriod;
		
	public void initBarChart() {
		LocalDate startDate = fromDate.getValue();
		LocalDate endDate = toDate.getValue();

		
		if(startDate!=null && endDate!=null)
		{
		statusPeriod.setText("");
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
		Query query1 = session.createQuery("Select s.car from Rent s where (dateRent BETWEEN '" + startDate + "' AND '" + endDate+ "') OR (dateReturn BETWEEN '" + startDate + "' AND '" + endDate + "') OR (dateRent < '"+startDate+"' AND dateReturn > '"+endDate+"')"); 
	    ObservableList<Car> listCar = FXCollections.observableArrayList(query1.list());

		session.getTransaction().commit();
		
		barChart.getData().clear();
		//filterChoiseBox.getSelectionModel().getSelectedItem()=="Клас"
		int p=0;
			if(filterChoiseBox.getSelectionModel().getSelectedIndex()==0)
			{

				p=0;
				XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
				dataSeries1.setName("Клас");
				Set<Category> categorieSet = new HashSet<Category>(); 
				for (Car car : listCar) {
					categorieSet.add(car.getCategory());
				}
				ArrayList<Integer> numCategory = new ArrayList<Integer>();
				int k=1, index=0;
				for (Category category : categorieSet) {
					numCategory.add(index, 0);
					for (Car car : listCar) {							
						if(car.getCategory().equals(category))
							{
								numCategory.set(index, k);
								k++;
							}				
						}					
				    k=1;
				    index++;
				}
				
				for (Category category : categorieSet) {
					dataSeries1.getData().add(new XYChart.Data<String, Number>(category.getCategoryType(), numCategory.get(p)));
					p++;
				}

				barChart.getData().add(dataSeries1);
			}
			else if (filterChoiseBox.getSelectionModel().getSelectedIndex()==1)
			{
				p=0;
				XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
				dataSeries2.setName("Категория");
				Set<Classification> classificationSet = new HashSet<Classification>(); 
				for (Car car : listCar) {
					classificationSet.add(car.getClassification());
				}
				ArrayList<Integer> numCategory = new ArrayList<Integer>();
				int k=1, index=0;
				for (Classification classification : classificationSet) {
					numCategory.add(index, 0);
					for (Car car : listCar) {							
						if(car.getClassification().equals(classification))
							{
								numCategory.set(index, k);
								k++;
							}				
						}					
				    k=1;
				    index++;
				}
				for (Classification classification : classificationSet) {
					dataSeries2.getData().add(new XYChart.Data<String, Number>(classification.getClassificationType(), numCategory.get(p)));
					p++;
				}

				barChart.getData().add(dataSeries2);
			}
			
	    }else
			statusPeriod.setText("Въведете коректен период!");
	}
	
	
}
