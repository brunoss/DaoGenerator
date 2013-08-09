package entities;

import g14.daoGenerator.strucutre.DaoHolder;
import holder.ValueHolder;
import dao.CategoryDao;
import dao.SupplierDao;

public class Product {

	int id;
	String productName;    
	double unitPrice;
    int unitsInStock;
    
    @DaoHolder(column = "supplierID", dao = SupplierDao.class)
    ValueHolder<Supplier> supplier;
    
    @DaoHolder(column = "categoryID", dao = CategoryDao.class)
    ValueHolder<Category> category;
    
    public Product(int id, String name, double price, int stock) {
        super();
        this.id = id;
        this.productName = name;
        this.unitPrice = price;
        this.unitsInStock = stock;
    }

    public Product(String name, double price, int stock) {
        this.productName = name;
        this.unitPrice = price;
        this.unitsInStock = stock;

    }

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public int getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getUnitsInStock() {
		return unitsInStock;
	}
	@Ignore
	public Supplier getSupllier(){
	    return supplier.value();
	}
	@Ignore
	public Category getCategory(){
	    return category.value();
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + productName + ", price=" + unitPrice
				+ ", stock=" + unitsInStock + "]";
	}	
}
