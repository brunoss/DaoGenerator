package orm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCmdTemplate<T> implements JdbcCmd<T>{

	final String sql;
	@SuppressWarnings("rawtypes")
	final JdbcBinder [] binders;
	final JdbcConverter<T> converter;
	
	public JdbcCmdTemplate(String sql, JdbcConverter<T> converter, JdbcBinder<?>...binder) {
		this.sql = sql;
		this.binders = binder;
		this.converter = converter;
	}

	@Override
	public final String getSql() {
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void bind(PreparedStatement stmt, Object[] args) throws SQLException {
		if(args.length != binders.length)
			throw new IllegalArgumentException("Illegal number of arguments!");
		int i = 0;
		for (Object o : args) {
			binders[i].bind(stmt, i+1, o);
			++i;
		}
	}

	@Override
	public final T convert(ResultSet rs) throws SQLException {
		return converter.convert(rs);
	}

}
