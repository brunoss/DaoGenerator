package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;

import orm.JdbcCmd;
import orm.JdbcConverter;
import orm.JdbcExecutor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DaoReadManyFunc<T> extends DaoAbstractFunc<Iterable<T>> {

    Class<T> type;
    
    public DaoReadManyFunc(DomainEntity entity, JdbcExecutor executor) {
        super(executor);
        this.type = (Class<T>) entity.value();
    }

    @Override
    protected Iterable<T> executeDataOperation(JdbcExecutor executor, JdbcCmd cmd, Object[] args)
            throws SQLException {
        return executor.executeQuery(cmd, args);
    }

    @Override
    protected JdbcConverter getConverter(final int nArgs) {
        final Constructor<T> ctor = ReflectionUtils.matchCtor(type, nArgs);
        return new JdbcConverter<T>() {
            @Override
            public T convert(ResultSet rs) throws SQLException {
                // As exercise says I assume there is a ctor for the read operation
                return ReflectionUtils.instaciate(ctor, rs);
            }
        };
    }
}
