package rentacar;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rent")
public class Rent {
	private int idRent;
	private LocalDate dateRent;
	private LocalDate dateReturn;
	private double traveledKM;
	private double totalPrice;
	private Operator operator;
	private Car car;
	private Client client;
	private boolean completedStatus;
	
	
	public Rent() {
		
	}
	
	public Rent(LocalDate dateRent, LocalDate dateReturn, Client client) {
		super();
		this.dateRent = dateRent;
		this.dateReturn = dateReturn;
		this.client = client;
	}
	public Rent(LocalDate dateRent, LocalDate dateReturn, double traveledKM, double totalPrice, Operator operator,
			Car car, Client client) {
		super();
		this.completedStatus=false;
		this.dateRent = dateRent;
		this.dateReturn = dateReturn;
		this.traveledKM = traveledKM;
		this.totalPrice = totalPrice;
		this.operator = operator;
		this.car = car;
		this.client = client;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idRent")
	public int getIdRent() {
		return idRent;
	}

	public void setIdRent(int idRent) {
		this.idRent = idRent;
	}

	public LocalDate getDateRent() {
		return dateRent;
	}

	public void setDateRent(LocalDate dateRent) {
		this.dateRent = dateRent;
	}

	public LocalDate getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(LocalDate dateReturn) {
		this.dateReturn = dateReturn;
	}

	public double getTraveledKM() {
		return traveledKM;
	}

	public void setTraveledKM(double traveledKM) {
		this.traveledKM = traveledKM;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	@ManyToOne(cascade = CascadeType.ALL) 
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public boolean isCompletedStatus() {
		return completedStatus;
	}

	public void setCompletedStatus(boolean completedStatus) {
		this.completedStatus = completedStatus;
	}

	@Override
	public String toString() {
		return "№: "+ idRent+" "+ client.getClientName() +" "+client.getClientPIN()+"\n";
	}
	

}
