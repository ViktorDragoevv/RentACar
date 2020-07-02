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
@Table(name="operator")
public class Operator {
	
	private int idOperator;
	private String userName;
	private String password;
	private String nameOfOperator;
	private boolean statusLogin;
	private Company company;
	
	public Operator() {
		this.userName="";
	}

	public Operator(String userName, String password, String nameOfOperator, boolean statusLogin, Company company) {
		super();
		this.userName = userName;
		this.password = password;
		this.nameOfOperator = nameOfOperator;
		this.statusLogin = statusLogin;
		this.company = company;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idOperator")
	public int getIdOperator() {
		return idOperator;
	}

	public void setIdOperator(int idOperator) {
		this.idOperator = idOperator;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNameOfOperator() {
		return nameOfOperator;
	}

	public void setNameOfOperator(String nameOfOperator) {
		this.nameOfOperator = nameOfOperator;
	}

	public boolean isStatusLogin() {
		return statusLogin;
	}

	public void setStatusLogin(boolean statusLogin) {
		this.statusLogin = statusLogin;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return nameOfOperator +" / " + company;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (idOperator != other.idOperator)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}




	
	
		
		
		
		

	
}
