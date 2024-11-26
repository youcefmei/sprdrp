package com.youcefmei.sparadrap.service;

import com.youcefmei.sparadrap.dao.HealthMutualDAO;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.model.HealthMutual;
import javafx.collections.ObservableList;
import lombok.Getter;

public class HealthMutualService {


    private HealthMutualDAO healthMutualDAO = new HealthMutualDAO();
    @Getter
    private ObservableList<HealthMutual>  healthMutuals ;

    public HealthMutualService() {
        healthMutuals = healthMutualDAO.findAllObservable();

    }



    private void checkHealthMutualDuplicate(HealthMutual healthMutual ) throws DuplicateException {
        if (healthMutual == null && healthMutuals.contains(null)) {
            throw new DuplicateException("Il y a déja une mutuelle vide");
        } else if (healthMutual != null) {
            for ( HealthMutual healthMutualTemp : healthMutuals) {
                if (healthMutualTemp.getName().equals(healthMutual.getName())) {
                    throw new DuplicateException("Il y a déja une mutuelle nommée ainsi");
                }
            }
        }
    }


    /**
     * Add health mutual.
     *
     * @param healthMutual the health mutual
     * @throws DuplicateException the duplicate exception
     */
    public void addHealthMutual(HealthMutual healthMutual) throws DuplicateException {
        checkHealthMutualDuplicate(healthMutual);
        healthMutuals.add(healthMutual);
    }

}
