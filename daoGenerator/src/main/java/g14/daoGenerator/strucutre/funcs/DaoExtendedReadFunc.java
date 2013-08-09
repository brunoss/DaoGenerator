package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.strucutre.Builder;
import g14.daoGenerator.strucutre.DaoHolder;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.funcs.factories.DaoExtendedFuncFactory;
import g14.daoGenerator.utils.ReflectionUtils;
import holder.ValueHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import orm.JdbcCmd;
import orm.JdbcConverter;
import orm.JdbcExecutor;
import orm.JdbcResultSetParser;

@SuppressWarnings("rawtypes")
public abstract class DaoExtendedReadFunc<Domain, Result> extends DaoAbstractFunc<Result> {

    private final Class<Domain> domainType;
    private final Method getMethod;
    protected final Iterable<DaoFieldHolder> daoHolders;
        
    @SuppressWarnings("unchecked")
    public DaoExtendedReadFunc(Method getMethod, DomainEntity entity, JdbcExecutor executor) {
        super(executor);
        this.getMethod = getMethod;
        domainType = (Class<Domain>) entity.value();
        daoHolders = getDaoHolders(domainType);
    }
    
    private Iterable<DaoFieldHolder> getDaoHolders(Class<Domain> type) {
        Field[] fields = type.getDeclaredFields();
        Collection<DaoFieldHolder> daoHolders = new ArrayList<DaoFieldHolder>(fields.length);

        for (int i = 0; i < fields.length; ++i) {
            DaoHolder holder = fields[i].getAnnotation(DaoHolder.class);
            if (holder != null) {
                daoHolders.add(new DaoFieldHolder(holder, fields[i]));
            }
        }
        return daoHolders;
    }
    
    protected static <Dao> Method getMethod(Class<Dao> dao, String name, Class... params){
        try {
            return dao.getMethod(name, params);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    protected void setHolders(Domain elem, final Object[] fks){
        if(fks.length == 0)return;
        int i = 0;
        for(DaoFieldHolder holder : daoHolders){
            Field f = holder.field;
            @SuppressWarnings("unchecked")
            final Object dao = Builder.make(holder.holder.dao(), executor, new DaoExtendedFuncFactory());
            final Object key = fks[i++];
            try {
                final Method getById = dao.getClass().getMethod("getById", int.class);
                f.setAccessible(true);
                f.set(elem, new ValueHolder<Object>() {
                    public Object value() {
                        try {
                            Object relation = getById.invoke(dao, key);
                            return relation;
                        } catch (Exception e){
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    
    private String getCompleteSelectString(String selectBase) {
        StringBuilder builder = new StringBuilder(selectBase);
        for (DaoFieldHolder holder : daoHolders) {
            if(!Iterable.class.isAssignableFrom(holder.field.getType()))
                builder.append(", ").append(holder.holder.column());
        }
        return builder.toString();
    }
    
    /**
     */
    int oldArgs;
    @Override
    protected JdbcCmd getCmd(String query, Class[] args) {
        int idxFrom = query.toUpperCase().indexOf("FROM");
        String select = query.substring(0, idxFrom);
        oldArgs = select.split(",").length;
        
        select = getCompleteSelectString(select);

        String fromClause = query.substring(idxFrom-1);
        query = select + fromClause;
        return super.getCmd(query, getMethod.getParameterTypes());
    }

    @Override
    protected JdbcConverter<Domain> getConverter(final int nArgs) {
        final Constructor<Domain> ctor = ReflectionUtils.matchCtor(domainType, oldArgs);
        return new JdbcConverter<Domain>() {
            @Override
            public Domain convert(ResultSet rs) throws SQLException {
                Domain t = ReflectionUtils.instaciate(ctor, rs);
                Object[] fks = new Object[nArgs - oldArgs];
                for(int i = 0; i < fks.length; ++i){
                    fks[i] = JdbcResultSetParser.parseInt.parse(rs, oldArgs+i+1);
                }
                setHolders(t, fks);
                return t;
            }
        };
    }
    
    /**
     * @author  Bruno
     */
    private static class DaoFieldHolder{
        /**
         */
        private final DaoHolder holder;
        private final Field field;
        
        public DaoFieldHolder(DaoHolder holder, Field field) {
            super();
            this.holder = holder;
            this.field = field;
        }
    }
}
