package orm;

import java.sql.SQLException;

import orm.JdbcAbstractMapper.QueryIterable;

public class JdbcQueryableExecutor implements JdbcExecutor{

	JdbcExecutor executor;
	public JdbcQueryableExecutor(JdbcExecutor executor) {
		this.executor = executor;
	}
	
	@Override
	public void close() throws Exception {
		executor.close();
	}

	@Override
	public <T> QueryIterable<T> executeQuery(final JdbcCmd<T> cmd, final Object... args)
			throws SQLException {		
		return new QueryIterable<T>(executor, cmd);
	}

	@Override
	public <T> void executeUpdate(JdbcCmd<T> cmd, Object... args)
			throws SQLException {
		executor.executeUpdate(cmd, args);
	}

	@Override
	public <K> K executeInsert(JdbcCmd<K> cmd, Object... args)
			throws SQLException {
		return executor.executeInsert(cmd, args);
	}
	
}
