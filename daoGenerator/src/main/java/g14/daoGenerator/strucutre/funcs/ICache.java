package g14.daoGenerator.strucutre.funcs;

public interface ICache<K> {
    void invalidateCache(Object key);

    void cache(Object key, K value);

    K getCachedValue(Object k);
}
