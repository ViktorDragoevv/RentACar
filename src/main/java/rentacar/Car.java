package rentacar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="car")
public class Car {
	

	private int idCar;
	private String regNumber;
	private String carModel;
	private boolean smoking;
	private boolean carStatus;	
	private double currKM;	
	private String photoPath;
	private Classification classification;
	private Category category;

	public Car() {
		
	}

	public Car(String regNumber, String carModel, boolean smoking, boolean carStatus, double currKM, String photoPath,
			Classification classification, Category category) {
		super();
		this.regNumber = regNumber;
		this.carModel = carModel;
		this.smoking = smoking;
		this.carStatus = carStatus;
		this.currKM = currKM;
		this.photoPath = photoPath;
		this.classification = classification;
		this.category = category;
	}
	
	

	public Car(String regNumber, String carModel, boolean smoking, boolean carStatus, double currKM,
			String photoPath) {
		super();
		this.regNumber = regNumber;
		this.carModel = carModel;
		this.smoking = smoking;
		this.carStatus = carStatus;
		this.currKM = currKM;
		this.photoPath = photoPath;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCar")
	public int getIdCar() {
		return idCar;
	}

	public void setIdCar(int idCar) {
		this.idCar = idCar;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public boolean isSmoking() {
		return smoking;
	}

	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}

	public boolean isCarStatus() {
		return carStatus;
	}

	public void setCarStatus(boolean carStatus) {
		this.carStatus = carStatus;
	}

	public double getCurrKM() {
		return currKM;
	}

	public void setCurrKM(double currKM) {
		this.currKM = currKM;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	
	@ManyToOne(cascade = CascadeType.ALL) 
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@ManyToOne(cascade = CascadeType.ALL) 
	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	


	@Override
	public String toString() {
		return carModel +" " + category+" "+regNumber;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	
	
}
