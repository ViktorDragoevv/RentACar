package rentacar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	

	private int idCategory;
	private String CategoryType;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}
	

	public Category(String categoryType) {
		//super();
		CategoryType = categoryType;
	}

	public Category(int idCategory, String categoryType) {
		super();
		this.idCategory = idCategory;
		CategoryType = categoryType;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCategory")
	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getCategoryType() {
		return CategoryType;
	}

	public void setCategoryType(String categoryType) {
		CategoryType = categoryType;
	}


	@Override
	public String toString() {
		return CategoryType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CategoryType == null) ? 0 : CategoryType.hashCode());
		result = prime * result + idCategory;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (CategoryType == null) {
			if (other.CategoryType != null)
				return false;
		} else if (!CategoryType.equals(other.CategoryType))
			return false;
		if (idCategory != other.idCategory)
			return false;
		return true;
	}
	
	
	
}

