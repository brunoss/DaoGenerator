package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.strucutre.DomainEntity;

import java.sql.SQLException;

import orm.JdbcCmd;
import orm.JdbcExecutor;

public class DaoExtendedReadManyFunc<Dao, Domain> extends
        DaoExtendedReadFunc<Domain, Iterable<Domain>> {

    public DaoExtendedReadManyFunc(Class<Dao> daoType, DomainEntity entity, JdbcExecutor executor) {
        super(getMethod(daoType, "getAll"), entity, executor);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Iterable<Domain> executeDataOperation(JdbcExecutor executor, JdbcCmd cmd,
            Object[] args) throws SQLException {
        return executor.executeQuery(cmd, args);
    }
}
