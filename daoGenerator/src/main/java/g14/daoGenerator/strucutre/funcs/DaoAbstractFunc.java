package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.utils.Func2;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import orm.JdbcBinder;
import orm.JdbcCmd;
import orm.JdbcCmdTemplate;
import orm.JdbcConverter;
import orm.JdbcExecutor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class DaoAbstractFunc<Result> implements IDaoFunc<Result> {

    protected final JdbcExecutor executor;
    private static final Map<Class<?>, JdbcBinder> bindMap = new HashMap<Class<?>, JdbcBinder>();
    
    static{
        bindMap.put(String.class, JdbcBinder.BindString);
        bindMap.put(int.class, JdbcBinder.BindInt);
        bindMap.put(float.class, JdbcBinder.BindFloat);
        bindMap.put(double.class, JdbcBinder.BindDouble);
        bindMap.put(Date.class, JdbcBinder.BindDate);
    }
    
    public DaoAbstractFunc(JdbcExecutor executor) {
        this.executor = executor;
    }

    @Override
    public final Func2<Class<?>[], Object[], Result> getDataOperation(final String query) {
        return new Func2<Class<?>[], Object[], Result>() {
            @Override
            public Result call(Class<?>[] types, Object[] t1) {
                try {
                    JdbcCmd cmd = getCmd(query, types);
                    Result k = executeDataOperation(executor, cmd, t1);
                    return k;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    protected abstract Result executeDataOperation(JdbcExecutor executor, JdbcCmd cmd, Object[] args)
            throws SQLException;

    protected JdbcCmd getCmd(String query, Class[] args) {
        JdbcBinder[] binders;
        if (args != null && args.length > 0) {
            binders = new JdbcBinder[args.length];
        } else {
            binders = new JdbcBinder[0];
        }
        for (int i = 0; i < args.length; ++i) {
            Class argType = args[i];
            if (args[i] == null)
                throw new UnsupportedOperationException("In this version we don't support null parameters");
            JdbcBinder binder = bindMap.get(argType);
            if(binder == null){
                throw new UnsupportedOperationException("Parameter type " + argType.getCanonicalName() + " not supported");
            }
            binders[i] = binder;
        }
        JdbcConverter converter = getConverter(query.split(",").length);
        return new JdbcCmdTemplate(query, converter, binders);
    }

    protected JdbcConverter getConverter(int nArgs) {
        return null;
    }

}
