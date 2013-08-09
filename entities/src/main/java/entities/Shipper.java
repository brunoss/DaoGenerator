package entities;

import g14.daoGenerator.strucutre.DaoHolder;
import dao.ProductDao;

public class Shipper {

	private int id;
	private String companyName;
	private String phone;
	
	private Shipper(int id, String companyName, String phone) {
		this.id = id;
		this.companyName = companyName;
		this.phone = phone;
	}
	
	private Shipper(String companyName, String phone) {
		this.companyName = companyName;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
