package dao;

import entities.Category;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.SqlCmd;
import g14.daoGenerator.strucutre.cmdTypes.Create;
import g14.daoGenerator.strucutre.cmdTypes.Delete;
import g14.daoGenerator.strucutre.cmdTypes.Update;

@DomainEntity(value = Category.class, key = "id")
public interface CategoryDao {
	  @SqlCmd(cmd = "SELECT CategoryID, CategoryName, Description FROM Categories")
	  Iterable<Category> getAll();
	  
	  @SqlCmd(cmd = "SELECT CategoryID, CategoryName, Description FROM Categories WHERE CategoryID = ?")
	  Category getById(int id);
	  
	  @SqlCmd(type = Update.class, cmd = "UPDATE Categories set CategoryName = ?, Description = ? WHERE CategoryID = ?")
	  void update(String name, String description, int id);
	  
	  @SqlCmd(type = Delete.class, cmd = "DELETE FROM Categories WHERE CategoryID = ?")
	  void delete(int id);
	  
	  @SqlCmd(type = Create.class, cmd = "INSERT INTO Categories (CategoryName, Description) VALUES (?, ?)")
	  Category insert(String name, String description);
}
