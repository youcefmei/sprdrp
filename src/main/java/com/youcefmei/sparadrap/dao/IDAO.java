package com.youcefmei.sparadrap.dao;

import java.sql.Connection;
import java.util.List;

public interface IDAO<T> {
    static Connection conn = DB.getInstance().getConnection();
    public T findById(int id);
    public T create(T obj);
    public boolean update(T obj);
    public boolean delete(int id);
    public List<T> findAll();

}
