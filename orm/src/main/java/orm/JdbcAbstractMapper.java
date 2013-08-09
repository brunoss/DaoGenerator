package orm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class JdbcAbstractMapper<K, T> implements DataMapper<K, T>{
	
	private final JdbcExecutor exec;
	
	protected final static JdbcConverter<Integer> genKey = new JdbcConverter<Integer>() {		
		@Override
		public Integer convert(ResultSet rs) throws SQLException {
			return rs.getInt(1);
		}
	};
	
	public JdbcAbstractMapper(JdbcExecutor exec) {
		this.exec = exec;
	}

	@Override
	public final T getById(K key) {
		try {
			Iterator<T> iter = exec.executeQuery(cmdGetById(), key).iterator();
			return iter.hasNext()? iter.next(): null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public final Iterable<T> getAll() {
		try {
			return exec.executeQuery(cmdGetAll());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * One cache per DataMapper. The same clause turns into the same command.
	 */
	private final Map<String, JdbcCmd<T>> whereCache = new HashMap<String, JdbcCmd<T>>();
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Iterable<T> where(final String clause, final JdbcBinder binder, final Object arg) {
		JdbcCmd<T> cmdWhere = whereCache.get(clause);
		if(cmdWhere == null){
			final JdbcCmd<T> cmdAll = cmdGetAll();
			cmdWhere = new JdbcCmd<T>(){
				public String getSql() {
					return cmdAll.getSql() + " WHERE " + clause;
				}
				public void bind(PreparedStatement stmt, Object[] args) throws SQLException {
					binder.bind(stmt, 1, args[0]);
				}
				public T convert(ResultSet rs)
						throws SQLException {
					return cmdAll.convert(rs);
				}
			};
		}
		try {
			return exec.executeQuery(cmdWhere, arg);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void update(T value) {
		try {
			exec.executeUpdate(cmdUpdate(), value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void insert(T value) {
		try {
			K key = exec.executeInsert(cmdInsert(), value);
			updateKeyOnValue(value, key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void delete(T value) {
		try {
			exec.executeUpdate(cmdDelete(), value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract void updateKeyOnValue(T value, K key);

	protected abstract JdbcCmd<K> cmdInsert();

	protected abstract JdbcCmd<T> cmdGetAll();
	
	protected abstract JdbcCmd<T> cmdGetById();
	
	protected abstract JdbcCmd<T> cmdUpdate();
	
	protected abstract JdbcCmd<T> cmdDelete();
	
	public static class QueryIterable<T> implements Iterable<T>{
		JdbcCmd<T> cmd;
		JdbcExecutor executor;
		public QueryIterable(JdbcExecutor executor, JdbcCmd<T> cmd){
			this.cmd = cmd;
			this.executor = executor;
		}
		public QueryIterable<T> where(final String clause ){
			JdbcCmd<T> newCmd = new JdbcQueryCmd<T>(this.cmd){
				public String getSql() {
					return this.queryCmd.getSql() + " WHERE " + clause;
				}
			};
			this.cmd = newCmd;
			return this;
		}
		public Iterable<T> orderBy(final String orderClause){
			JdbcCmd<T> newCmd = new JdbcQueryCmd<T>(this.cmd) {
				public String getSql() {
					return this.queryCmd.getSql() + " ORDER BY " + orderClause; 
				}
			};
			this.cmd = newCmd;
			return this;
		}
		public Iterator<T> iterator() {
			try {
				return executor.executeQuery(cmd).iterator();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
