package com.youcefmei.sparadrap.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface IDAOObservable<T> extends IDAO<T> {

    default ObservableList<T> findAllObservable() {
        return  FXCollections.observableArrayList(findAll()) ;
    }

}
