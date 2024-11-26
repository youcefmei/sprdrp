package com.youcefmei.sparadrap.service;

import com.youcefmei.sparadrap.dao.DoctorDAO;
import com.youcefmei.sparadrap.dao.DoctorGeneralDAO;
import com.youcefmei.sparadrap.dao.DoctorSpecialityDAO;
import com.youcefmei.sparadrap.dao.DoctorSpecializedDAO;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.model.Doctor;
import com.youcefmei.sparadrap.model.DoctorGeneral;
import com.youcefmei.sparadrap.model.DoctorSpeciality;
import com.youcefmei.sparadrap.model.DoctorSpecialized;
import jakarta.validation.constraints.NotNull;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class DoctorService {

    @Getter
    private DoctorGeneralDAO doctorGeneralDAO = new DoctorGeneralDAO();
    @Getter
    private DoctorSpecializedDAO doctorSpecializedDAO = new DoctorSpecializedDAO();
    @Getter
    private DoctorDAO doctorDAO = new DoctorDAO();
    @Getter
    private DoctorSpecialityDAO doctorSpecialityDAO = new DoctorSpecialityDAO();

    @Getter
    private ObservableList<DoctorGeneral> doctorGenerals ;
    @Getter
    private ObservableList<DoctorSpecialized> doctorSpecializeds ;
    @Getter
    private ObservableList<Doctor> doctors ;
    @Getter
    private ObservableList<DoctorSpeciality> doctorSpecialities;

    public DoctorService() {
        doctorGenerals = doctorGeneralDAO.findAllObservable();
        doctorSpecializeds = doctorSpecializedDAO.findAllObservable();
        doctorSpecialities = doctorSpecialityDAO.findAllObservable();
//        doctors = doctorDAO.findAllObservable();
        doctors = FXCollections.observableArrayList();
        for (DoctorSpecialized doctorSpecialized : doctorSpecializeds) {
            doctors.add( (Doctor) doctorSpecialized );
        }
        for (DoctorGeneral doctorGeneral : doctorGenerals) {
            doctors.add( (Doctor) doctorGeneral );
        }
    }



    private void checkDoctorDuplicate(Doctor doctor) throws DuplicateException {
        if (doctor == null && doctors.contains(null)) {
            throw new DuplicateException("Le docteur ne peut etre nul");
        } else if (doctor != null) {
            for (Doctor doctorTemp : doctorGenerals) {
                if ((doctorTemp !=null) && (doctorTemp.getRegistrationNb().equals(doctor.getRegistrationNb())) ) {
                    throw new DuplicateException("Il y a déja un docteur ayant ce numero d'agrement");
                }
            }
            for (Doctor doctorTemp : doctorGenerals) {
                if ((doctorTemp !=null) && (doctorTemp.getRegistrationNb().equals(doctor.getRegistrationNb()))) {
                    throw new DuplicateException("Il y a déja un docteur ayant ce numero d'agrement");
                }
            }
        }
    }


    /**
     * Add doctor general.
     *
     * @param doctor the doctor
     * @throws DuplicateException the duplicate exception
     */
    public void addDoctorGeneral(@NotNull DoctorGeneral doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor);
        doctorGeneralDAO.create(doctor);
        doctorGenerals.add(doctor);
        doctors.add( doctor);
    }

    /**
     * Add doctor specialized.
     *
     * @param doctor the doctor
     * @throws DuplicateException the duplicate exception
     */
    public void addDoctorSpecialized(@NotNull DoctorSpecialized doctor) throws DuplicateException {
        doctorSpecializedDAO.create(doctor);
        checkDoctorDuplicate(doctor);
        doctorSpecializeds.add(doctor);
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctorDAO.delete(doctor.getDoctorId());
        if (doctor instanceof DoctorSpecialized){
//            doctorSpecializedDAO.delete(((DoctorSpecialized) doctor).getDoctorSpecializedId());
            doctorSpecializeds.remove(doctor);
        }else if (doctor instanceof DoctorGeneral){
//            doctorGeneralDAO.delete(doctor.getDoctorId());
            doctorGenerals.remove(doctor);
        }
        doctors.remove( doctor);
    }

    public void updateDoctor(Doctor doctor) throws DuplicateException {

        if (doctor instanceof DoctorSpecialized doctorSpecialized){
            for (int i = 0; i < doctorSpecializeds.size(); i++) {
                if (doctorSpecializeds.get(i).getDoctorId() ==  doctor.getDoctorId()) {
                    doctorSpecializedDAO.update(doctorSpecialized);
                    doctorSpecializeds.set(i, doctorSpecialized);
                }
            }
//            doctorSpecializeds.remove(doctorSpecialized);
        }else if (doctor instanceof DoctorGeneral doctorGeneral){
            for (int i = 0; i < doctorGenerals.size(); i++) {
               if (doctorGenerals.get(i).getDoctorId() ==  doctor.getDoctorId()) {
                   doctorGeneralDAO.update(doctorGeneral);
                   doctorGenerals.set(i, doctorGeneral);
               }
            }
//            doctorGenerals.remove(doctor);
        }
        for (int i = 0; i < doctors.size(); i++) {
           if (doctors.get(i).getDoctorId() == doctor.getDoctorId()) {
               doctors.set(i, doctor);
           }
        }

    }

}