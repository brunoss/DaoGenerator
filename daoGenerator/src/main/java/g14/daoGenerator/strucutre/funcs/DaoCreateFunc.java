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
public class DaoCreateFunc<T> extends DaoAbstractFunc<T> {


    Class<T> type;

    DomainEntity entity;

    public DaoCreateFunc(DomainEntity entity, JdbcExecutor executor) {
        super(executor);
        this.type = (Class<T>) entity.value();
        this.entity = entity;
    }

    @Override
    protected JdbcConverter getConverter(int nArgs) {
        return new JdbcConverter() {
            @Override
            public Object convert(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }
        };
    }

    @Override
    protected T executeDataOperation(JdbcExecutor executor, JdbcCmd cmd, Object[] args)
            throws SQLException {
        Object key = executor.executeInsert(cmd, args);
        Constructor<T> ctor = ReflectionUtils.matchCtor(type, args.length);
        T t = ReflectionUtils.instaciate(ctor, entity, args, key);
        return t;
    }
}
