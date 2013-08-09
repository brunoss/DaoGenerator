package g14.daoGenerator.strucutre.cmdTypes;

import g14.daoGenerator.strucutre.DaoInvocationHandler;
import g14.daoGenerator.strucutre.funcs.IDaoFunc;

@SuppressWarnings("rawtypes")
public class Create implements DaoVisitable {

    @Override
    public IDaoFunc accept(DaoInvocationHandler t) {
        return t.getFactory().makeCreateFunc();
    }

}
