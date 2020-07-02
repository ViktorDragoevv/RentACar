package rentacar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idClient")
	private int idClient;
	
	@Column(name="clientName")
	private String clientName;
	
	@Column(name="clientPIN")
	private String clientPIN;
	
	@Column(name="clientAddress")
	private String clientAddress;
	
	@Column(name="clientRating")
	private double clientRating;
	
	@Column(name="clientDriverLicenceNumber")
	private String clientDriveLicenceNumber;
	
	public Client() {
		
	}
	
	
	
	public Client(String clientName, String clientPIN, String clientAddress, double clientRating,
			String clientDriveLicenceNumber) {
		super();
		this.clientName = clientName;
		this.clientPIN = clientPIN;
		this.clientAddress = clientAddress;
		this.clientRating = clientRating;
		this.clientDriveLicenceNumber = clientDriveLicenceNumber;
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientPIN() {
		return clientPIN;
	}

	public void setClientPIN(String clientPIN) {
		this.clientPIN = clientPIN;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientDriveLicenceNumber() {
		return clientDriveLicenceNumber;
	}

	public void setClientDriveLicenceNumber(String clientDriveLicenceNumber) {
		this.clientDriveLicenceNumber = clientDriveLicenceNumber;
	}

	public void setClientRating(double clientRating) {
		this.clientRating = clientRating;
	}


	public int getIdClient() {
		return idClient;
	}



	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}



	public double getClientRating() {
		return clientRating;
	}



	@Override
	public String toString() {
		return clientName+" ЕГН=" + clientPIN
				+ "\n № Ш.К="+ clientDriveLicenceNumber
				+ " Рейтинг=" + clientRating 
				 + "\nАдрес=" + clientAddress;
	}
	
	
	
}
