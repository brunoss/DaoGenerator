package entities;
import g14.daoGenerator.strucutre.DaoHolder;
import holder.ValueHolder;

import java.util.Date;

import dao.EmployeeDao;
import dao.ShipperDao;


/**
 * @author  mcarvalho
 */
public class Order {
	
	private  int id;   
	private Date orderDate;
	private Date shippedDate;
	private double freight;
	private String shipName;
	
	@DaoHolder(column="EmployeeID", dao=EmployeeDao.class)
	ValueHolder<Employee> employee;
	
	@DaoHolder(column="ShipVia", dao=ShipperDao.class)
	ValueHolder<Shipper> shipper;
	
	private Order(Date orderDate, Date shippedDate, double freight, String shipName) {
		this.orderDate = orderDate;
		this.shippedDate = shippedDate;
		this.freight = freight;
		this.shipName = shipName;
	}
	
	private Order(int id, Date orderDate, Date shippedDate, double freight, String shipName) {
		this.id = id;
		this.orderDate = orderDate;
		this.shippedDate = shippedDate;
		this.freight = freight;
		this.shipName = shipName;
	}
	public int getId() {
		return id;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	@Ignore
	public Employee getEmployee(){
	    return employee.value();
	}
	@Ignore
	public Shipper getShipper(){
	    return shipper.value();
	}
}
