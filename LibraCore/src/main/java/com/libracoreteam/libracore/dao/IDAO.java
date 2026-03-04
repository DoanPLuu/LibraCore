
package com.libracoreteam.libracore.dao;

import java.util.List;


public interface IDAO<T> {
    public List<T> getAll();
    public boolean insert(T obj);
    public boolean update(T obj);
    public boolean softDelete(int id);
}
