package g14.daoGenerator.strucutre.cmdTypes;

import g14.daoGenerator.strucutre.DaoInvocationHandler;
import g14.daoGenerator.strucutre.funcs.IDaoFunc;

@SuppressWarnings("rawtypes")
public class Read implements DaoVisitable {

    @Override
    public IDaoFunc accept(DaoInvocationHandler t) {
        if(Iterable.class.isAssignableFrom(t.getCurrentMethod().getReturnType())){
            return t.getFactory().makeReadManyFunc();
        }
        return t.getFactory().makeReadOneFunc();
    }
}
