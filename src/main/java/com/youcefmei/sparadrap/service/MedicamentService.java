package com.youcefmei.sparadrap.service;

import com.youcefmei.sparadrap.dao.MedicamentDAO;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.model.Medicament;
import javafx.collections.ObservableList;
import lombok.Getter;

public class MedicamentService {

    private MedicamentDAO medicamentDAO = new MedicamentDAO();
    @Getter
    private ObservableList<Medicament>  medicaments;

    public MedicamentService() {
        medicaments = medicamentDAO.findAllObservable();
    }


    private void checkMedicamentDuplicate(String name ) throws DuplicateException {
        for (Medicament medicamentTemp : medicaments) {
            if (medicamentTemp.getTitle().equals(name)) {
                throw new DuplicateException("Il y a déja un medicament nommé comme ceci");
            }
        }
    }

    /**
     * Add medicament.
     *
     * @param medicament the medicament
     * @throws DuplicateException the duplicate exception
     */
    public void addMedicament(Medicament medicament) throws DuplicateException {
        checkMedicamentDuplicate(medicament.getTitle());
        medicaments.add(medicament);
    }

}
