package dao;

import java.util.Date;

import entities.Category;
import entities.Employee;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.SqlCmd;
import g14.daoGenerator.strucutre.cmdTypes.*;

@DomainEntity(value = Category.class, key = "id")
public interface EmployeeDao {
	  @SqlCmd(cmd = "SELECT EmployeeID, Title, FirstName, LastName, BirthDate from Employees")
	  Iterable<Employee> getAll();
	  
	  @SqlCmd(cmd = "SELECT EmployeeID, Title, FirstName, LastName, BirthDate from Employees where EmployeeID = ?")
	  Employee getById(int id);
	  
	  @SqlCmd(type = Update.class, cmd = "UPDATE Employees set Title = ?, FirstName = ?, LastName = ?, BirthDate = ? WHERE EmployeeID = ?")
	  void update(String title, String firstName, String lastName, Date birthDate, int id);
	  
	  @SqlCmd(type = Delete.class, cmd = "DELETE FROM Employees WHERE EmployeeID = ?")
	  void delete(int id);
	  
	  @SqlCmd(type = Create.class, cmd = "INSERT INTO Categories (Title, FirstName, LastName, BirthDate) VALUES (?, ?, ?, ?)")
	  Employee insert(String title, String firstName, String lastName, Date birthDate);
}
