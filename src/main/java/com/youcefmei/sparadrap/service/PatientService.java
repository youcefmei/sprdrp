package com.youcefmei.sparadrap.service;

import com.youcefmei.sparadrap.dao.PatientDAO;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.model.Patient;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.Objects;

public class PatientService {

    @Getter
    private ObservableList<Patient> patients;

    PatientDAO patientDAO = new PatientDAO();


    public PatientService() {
        patients = patientDAO.findAllObservable();

    }

//
//    private void checkPatientDuplicate(String secuId ) throws DuplicateException {
//        for (Patient patientTemp : patients) {
//            if (patientTemp.getSecuId().equals(secuId)) {
//                throw new DuplicateException("Il y a déja un patient ayant ce numero de secu");
//            }
//        }
//    }

    /**
     * Add patient.
     *
     * @param patient the patient
     * @throws DuplicateException the duplicate exception
     */
    public void addPatient(Patient patient) throws DuplicateException {
//        checkPatientDuplicate(patient.getSecuId());
        patientDAO.create(patient);
        patients.add(patient);
    }


    /**
     * Remove patient.
     *
     * @param patient the patient
     */
    public void removePatient(Patient patient) {
        patientDAO.delete(patient.getPatientId());
        patients.remove(patient);
    }

    public void updatePatient(Patient patient) {
        patientDAO.update(patient);
        for (int i = 0; i < patients.size(); i++) {
            if (Objects.equals( patients.get(i).getPatientId(), patient.getPatientId() ) ) {
                patients.set(i,patient);
            }

        }

    }



}
