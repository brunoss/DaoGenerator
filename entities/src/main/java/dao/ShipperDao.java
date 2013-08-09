package dao;

import entities.Shipper;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.SqlCmd;
import g14.daoGenerator.strucutre.cmdTypes.*;

@DomainEntity(value = Shipper.class, key = "id")
public interface ShipperDao {
	  @SqlCmd(cmd = "SELECT ShipperID, CompanyName, Phone FROM Shippers")
	  Iterable<Shipper> getAll();
	  
	  @SqlCmd(cmd = "SELECT ShipperID, CompanyName, Phone FROM Shippers WHERE ShipperID = ?")
	  Shipper getById(int id);
	  
	  @SqlCmd(type = Update.class, cmd = "UPDATE Shippers set CompanyName = ?, Phone = ? WHERE ShipperID = ?")
	  void update(String name, String phone, int id);
	 
	  @SqlCmd(type = Delete.class, cmd = "DELETE FROM Shippers WHERE ShipperID = ?")
	  void delete(int id);
	  
	  @SqlCmd(type = Create.class, cmd = "INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)")
	  Shipper insert(String name, String phone);
}
