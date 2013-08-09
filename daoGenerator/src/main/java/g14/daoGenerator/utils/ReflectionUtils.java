package g14.daoGenerator.utils;

import g14.daoGenerator.strucutre.DomainEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import orm.JdbcResultSetParser;

@SuppressWarnings("rawtypes")
public class ReflectionUtils {
    
    private static final Map<Class, JdbcResultSetParser> parserMap = new HashMap<Class, JdbcResultSetParser>();
    
    static{
        parserMap.put(int.class, JdbcResultSetParser.parseInt);
        parserMap.put(double.class, JdbcResultSetParser.parseDouble);
        parserMap.put(float.class, JdbcResultSetParser.parseFloat);
        parserMap.put(String.class, JdbcResultSetParser.parseString);
        parserMap.put(Date.class, JdbcResultSetParser.parseDate);
    }
    
    /**
     * Forces error if domain entity has more than one constructor with
     * args.length parameters
     */
    public static <T> T instaciate(Constructor<T> ctor, DomainEntity entity, Object[] args,
            Object key) {
        try {
            int ctorParams = ctor.getParameterTypes().length;
            if (ctorParams != args.length) {
                Object[] aux = new Object[ctorParams];
                aux[0] = 0;
                for (int i = 0; i < args.length; ++i) {
                    aux[i + 1] = args[i];
                }
                args = aux;
            }
            ctor.setAccessible(true);
            T o = ctor.newInstance(args);
            Field f = entity.value().getDeclaredField(entity.key());
            f.setAccessible(true);
            f.set(o, key);
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Forces error if domain entity has more than one constructor with nParams */
    public static <T> T instaciate(Constructor<T> ctor, ResultSet rs) {
        try {
            Class[] params = ctor.getParameterTypes();
            Object[] valuesToCtor = new Object[params.length];
            for (int i = 0; i < valuesToCtor.length; ++i) {
                JdbcResultSetParser parser = parserMap.get(params[i]);
                assert parser != null;
                valuesToCtor[i] = parser.parse(rs, i+1);
            }
            ctor.setAccessible(true);
            return ctor.newInstance(valuesToCtor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> matchCtor(Class<T> type, int nArgs) {
        Constructor<T> found = null;
        Constructor[] ctors = type.getDeclaredConstructors();
        if (ctors.length == 1)
            return ctors[0];

        for (Constructor ctor : ctors) {
            if (ctor.getParameterTypes().length == nArgs) {
                if (found != null)
                    throw new RuntimeException(
                            "Can not destinguish ctors with same number of arguments");
                found = ctor;
            }
        }
        return found;
    }
}
