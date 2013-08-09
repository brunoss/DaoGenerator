package dao;

import entities.Supplier;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.SqlCmd;
import g14.daoGenerator.strucutre.cmdTypes.Create;
import g14.daoGenerator.strucutre.cmdTypes.Delete;
import g14.daoGenerator.strucutre.cmdTypes.Update;

@DomainEntity(value = Supplier.class, key = "supplierID")
public interface SupplierDao {
	  @SqlCmd(cmd = "SELECT SupplierID, CompanyName, ContactName, ContactTitle, Address, City FROM Suppliers")
	  Iterable<Supplier> getAll();
	  
	  @SqlCmd(cmd = "SELECT SupplierID, CompanyName, ContactName, ContactTitle, Address, City FROM Suppliers WHERE SupplierID = ?")
	  Supplier getById(int id);
	  
	  @SqlCmd(type = Update.class, cmd = "UPDATE Suppliers set CompanyName = ?, ContactName = ?, ContactTitle = ?, Address = ?, City = ? WHERE SupplierID = ?")
	  void update(String companyName, String contactName, String contactTitle, String Adress, String City, int id);
	  
	  @SqlCmd(type = Delete.class, cmd = "DELETE FROM Suppliers WHERE SupplierID = ?")
	  void delete(int id);
	  
	  @SqlCmd(type = Create.class, cmd = "INSERT INTO Suppliers (CompanyName, ContactName, ContactTitle, Address, City) VALUES (?, ?, ?, ?, ?)")
	  Supplier insert(String companyName, String contactName, String contactTitle, String Adress, String City);
}
