package com.youcefmei.sparadrap.service;

import com.youcefmei.sparadrap.dao.DoctorDAO;
import com.youcefmei.sparadrap.dao.DoctorGeneralDAO;
import com.youcefmei.sparadrap.dao.DoctorSpecialityDAO;
import com.youcefmei.sparadrap.dao.DoctorSpecializedDAO;
import com.youcefmei.sparadrap.model.Doctor;
import com.youcefmei.sparadrap.model.DoctorGeneral;
import com.youcefmei.sparadrap.model.DoctorSpeciality;
import com.youcefmei.sparadrap.model.DoctorSpecialized;
import javafx.collections.ObservableList;
import lombok.Getter;

public class DoctorService {


    private DoctorGeneralDAO doctorGeneralDAO = new DoctorGeneralDAO();
    private DoctorSpecializedDAO doctorSpecializedDAO = new DoctorSpecializedDAO();

    private DoctorDAO doctorDAO = new DoctorDAO();
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
    }


    public ObservableList<Doctor> findAllDoctors(){
        return doctorDAO.findAllObservable();
    }


    public ObservableList<DoctorGeneral> findAllDoctorGenerals(){
        return doctorGeneralDAO.findAllObservable();
    }


    public ObservableList<DoctorSpecialized> findAllDoctorSpecializeds(){
        return doctorSpecializedDAO.findAllObservable();
    }

}
