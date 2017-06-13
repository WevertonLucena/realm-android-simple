package br.com.lucenasistemas.realmexemple.dao;

import java.util.List;

/**
 * Created by Weverton on 13/06/2017.
 */

public interface Dao<T> extends AutoCloseable {

    public <T> List<T> get();
    public <T> T get(Long id);
    public  void save(T obj);
    public void delete(T obj);
    public Long getNewId();
}
