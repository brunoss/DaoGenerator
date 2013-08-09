package orm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JdbcQueryCmd<T> implements JdbcCmd<T>{
	protected JdbcCmd<T> queryCmd;
	@SuppressWarnings("rawtypes")
	JdbcBinder binder;
	public JdbcQueryCmd(JdbcCmd<T> cmd) {
		this.queryCmd = cmd;
	}
	
	
	@Override
	public void bind(PreparedStatement stmt, Object[] args) throws SQLException {
		return;
	}
	
	@Override
	public T convert(ResultSet rs) throws SQLException {
		return queryCmd.convert(rs);
	}
}
