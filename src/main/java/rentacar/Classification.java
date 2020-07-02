package rentacar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="classification")
public class Classification {
	

	private int idClassification; 
	private String classificationType;
	private double pricePerDay;
	private double pricePerKM;
	
	public Classification() {
		
	}

	public Classification(String classificationType, double pricePerDay, double pricePerKM) {
		super();
		this.classificationType = classificationType;
		this.pricePerDay = pricePerDay;
		this.pricePerKM = pricePerKM;
	}
	



	public Classification(int idClassification, String classificationType, double pricePerDay, double pricePerKM) {
		super();
		this.idClassification = idClassification;
		this.classificationType = classificationType;
		this.pricePerDay = pricePerDay;
		this.pricePerKM = pricePerKM;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idClassification")
	public int getIdClassification() {
		return idClassification;
	}

	public void setIdClassification(int idClassification) {
		this.idClassification = idClassification;
	}

	public String getClassificationType() {
		return classificationType;
	}

	public void setClassificationType(String classificationType) {
		this.classificationType = classificationType;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public double getPricePerKM() {
		return pricePerKM;
	}

	public void setPricePerKM(double pricePerKM) {
		this.pricePerKM = pricePerKM;
	}

	@Override
	public String toString() {
		return "" + classificationType + ", " + pricePerDay+ "лв/ден, " + pricePerKM+ "/км ";
	}
	
	
}
