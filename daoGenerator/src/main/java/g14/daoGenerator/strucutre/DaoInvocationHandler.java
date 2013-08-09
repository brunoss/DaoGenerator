package g14.daoGenerator.strucutre;

import g14.daoGenerator.strucutre.cmdTypes.DaoVisitable;
import g14.daoGenerator.strucutre.funcs.factories.DaoAbstractFactory;
import g14.daoGenerator.utils.Func2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import orm.JdbcExecutor;

public class DaoInvocationHandler<Domain, Dao> implements InvocationHandler {

    private final Map<Method, Func2<Class<?>[], Object[], Object>> mapMethod = 
            new HashMap<Method, Func2<Class<?>[], Object[], Object>>();

    private static final Object[] noArgs = new Object[0];
    private final DaoAbstractFactory<Domain, Dao> factory;
    private Method cuurMethod;

    public DaoInvocationHandler(Class<Dao> dao, JdbcExecutor executor, DaoAbstractFactory<Domain, Dao> factory) {
        this.factory = factory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (args == null) {
            args = noArgs;
        }
        Func2<Class<?>[], Object[], Object> func = mapMethod.get(method);
        if (func == null) {
            SqlCmd cmd = method.getAnnotation(SqlCmd.class);
            this.cuurMethod = method;
            func = getFuncForCmdType(cmd);
            // Just to remind that the lifeTime of currMehod is just important to
            // determine func for the 1st time
            this.cuurMethod = null;
            mapMethod.put(method, func);
        }
        return func.call(method.getParameterTypes(), args);
    }

    @SuppressWarnings("unchecked")
    private Func2<Class<?>[], Object[], Object> getFuncForCmdType(SqlCmd cmd) {
        try {
            DaoVisitable<Domain, Dao> visitable = (DaoVisitable<Domain, Dao>) cmd.type().newInstance();
            
            // modified version of visitor pattern
            // this is a mix of the two options available, the other would be
            // that Create, Read, ... would be DaoAbstractFunc by them selves
            
            //DaoFuncs instances could be reused
            return visitable.accept(this).getDataOperation(cmd.cmd());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public DaoAbstractFactory<Domain, Dao> getFactory() {
        return factory;
    }

    public Method getCurrentMethod() {
        return cuurMethod;
    }
}
