package g14.daoGenerator.strucutre.funcs;

import java.sql.SQLException;

import orm.JdbcCmd;
import orm.JdbcExecutor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DaoUpdateFunc<K> extends DaoAbstractFunc<Void> {

    public DaoUpdateFunc(JdbcExecutor executor) {
        super(executor);
    }

    @Override
    protected Void executeDataOperation(JdbcExecutor executor, JdbcCmd cmd, Object[] args)
            throws SQLException {
        executor.executeUpdate(cmd, args);
        return null;
    }
}
