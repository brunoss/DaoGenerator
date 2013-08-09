package dao;

import java.util.Date;

import entities.Order;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.SqlCmd;
import g14.daoGenerator.strucutre.cmdTypes.Create;
import g14.daoGenerator.strucutre.cmdTypes.Delete;
import g14.daoGenerator.strucutre.cmdTypes.Update;


@DomainEntity(value = Order.class, key = "id")
public interface OrderDao {
	  @SqlCmd(cmd = "SELECT OrderID, OrderDate, ShippedDate, Freight, ShipName FROM Orders")
	  Iterable<Order> getAll();
	  
	  @SqlCmd(cmd = "SELECT OrderID, OrderDate, ShippedDate, Freight, ShipName FROM Orders WHERE OrderID = ?")
	  Order getById(int id);
	  
	  @SqlCmd(type = Update.class, cmd = "UPDATE Orders set OrderDate = ?, ShippedDate = ?, Freight = ?, ShipName = ? WHERE OrderID = ?")
	  void update(Date orderDate, Date shippedDate, float Freight, String shipName, int id);
	  
	  @SqlCmd(type = Delete.class, cmd = "DELETE FROM Orders WHERE OrderID = ?")
	  void delete(int id);
	  
	  @SqlCmd(type = Create.class, cmd = "INSERT INTO Orders (OrderDate, ShippedDate, Freight, ShipName) VALUES (?, ?, ?, ?)")
	  Order insert(Date orderDate, Date shippedDate, float Freight, String shipName);
}
