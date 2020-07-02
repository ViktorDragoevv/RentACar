package rentacar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="company")
public class Company {
	
	private int idCompany;
	private String companyName;
	private String companyAddress;
	
	public Company() {
		
	}
	
	public Company(String companyName, String companyAddress) {
		super();
		this.companyName = companyName;
		this.companyAddress = companyAddress;
	}
	

	public Company(int idCompany, String companyName, String companyAddress) {
		super();
		this.idCompany = idCompany;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCompany")
	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	@Override
	public String toString() {
		return ""+companyName + ", " + companyAddress ;
	}
	
	
}
