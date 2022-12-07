package com.walnutclinics.billingService.service;

import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.model.patient.Patient;
import com.walnutclinics.billingService.repository.ClinicRepository;
import com.walnutclinics.billingService.repository.DoctorRepository;
import com.walnutclinics.billingService.repository.PatientRepository;
import com.walnutclinics.billingService.repository.TreatmentRepository;
import com.walnutclinics.billingService.request.PatientSaveRequest;
import com.walnutclinics.billingService.response.ClinicBasicInfoResponse;
import com.walnutclinics.billingService.response.DoctorBasicInfoResponse;
import com.walnutclinics.billingService.response.PatientBasicInfoResponse;
import com.walnutclinics.billingService.response.TreatmentBasicInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public List<Patient> getPatientList() {
        return patientRepository.findAll();
    }

    public List<PatientBasicInfoResponse> searchByNames(String name) {
        return patientRepository.getMatchingPatients(name);
    }

    public Patient save(PatientSaveRequest patientRequest) {
        Patient patient = new Patient();
        patient.setFirstName(patientRequest.getFirstName());
        patient.setMiddleName(patientRequest.getMiddleName());
        patient.setLastName(patientRequest.getLastName());
        patient.setDateOfBirth(patientRequest.getDateOfBirth());
        patient.setEmail(patientRequest.getEmailId());
        patient.setGender(patientRequest.getGender());
        return patientRepository.save(patient);
    }

    public Patient update(Patient patient) {
        return patientRepository.save(patient);
    }

    public void delete(int id) {
        patientRepository.deleteById(id);
    }

    public List<Clinic> getMappedClinics(int patientId) {
        return patientRepository.findById(patientId).get().getClinics();
    }

    public List<DoctorBasicInfoResponse> getMappedDoctors(int patientId) {
//        List<Doctor> DoctorList = patientRepository.findById(patientId).get().getDoctors();
        List<Doctor> DoctorList = doctorRepository.findAll();
        List<DoctorBasicInfoResponse> doctorBasicInfoResponses = new ArrayList<>();
        for (Doctor doctor : DoctorList) {
            doctorBasicInfoResponses.add(new DoctorBasicInfoResponse(doctor.getDoctorId(), "Dr." + doctor.getFirstName().trim() + " " + doctor.getMiddleName().trim() + " " + doctor.getLastName().trim()));
        }
        return doctorBasicInfoResponses;
    }

    public List<ClinicBasicInfoResponse> getMappedClinicsBasedOnPatientAndDoctors(int patientId, long doctorId) {
//        List<Doctor> DoctorList = patientRepository.findById(patientId).get().getDoctors();
        List<ClinicBasicInfoResponse> clinicBasicInfoResponses = new ArrayList<>();
//        for (Doctor doctor : DoctorList) {
//            if (doctor.getDoctorId().equals(doctorId)) {

        for (Clinic clinic : clinicRepository.findAll()) {
            clinicBasicInfoResponses.add(new ClinicBasicInfoResponse(clinic.getClinicId(), clinic.getName() + " - " + clinic.getLocation()));
        }
//            }
//        }
        return clinicBasicInfoResponses;
    }

    public List<TreatmentBasicInfoResponse> getMappedTreatmentsBasedOnPatientAndDoctorAndClinic(int patientId, long doctorId, long clinicId) {
//        List<Doctor> DoctorList = patientRepository.findById(patientId).get().getDoctors();
        List<TreatmentBasicInfoResponse> treatmentBasicInfoResponses = new ArrayList<>();
//        for (Doctor doctor : DoctorList) {
//            if (doctor.getDoctorId().equals(doctorId)) {
//                for (Clinic clinic : doctor.getClinics()) {
//                    if (clinic.getClinicId().equals(clinicId)) {
        for (Treatment treatment : treatmentRepository.findAll())
            treatmentBasicInfoResponses.add(new TreatmentBasicInfoResponse(treatment.getTreatmentId(), treatment.getName()));
//                    }
//                }
//            }
//        }
        return treatmentBasicInfoResponses;
    }

    public Patient getById(int id)
    {
        return patientRepository.findById(id).get();
    }

}
