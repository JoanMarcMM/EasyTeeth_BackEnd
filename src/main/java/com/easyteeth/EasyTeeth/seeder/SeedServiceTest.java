package com.easyteeth.EasyTeeth.seeder;

import com.easyteeth.EasyTeeth.model.*;
import com.easyteeth.EasyTeeth.controller.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SeedServiceTest {
	
	private final UserRepository userRepository;
    private final ToothRepository toothRepository;
    private final SideRepository sideRepository;
    private final BoxRepository boxRepository;
    private final StorageRepository storageRepository;
    private final PathologyRepository pathologyRepository;
    private final TreatmentRepository treatmentRepository;
    private final SupplierRepository supplierRepository;
    private final UtensilRepository utensilRepository;
    private final TreatmentUtensilRepository treatmentUtensilRepository;
    private final SpecialityRepository specialityRepository;
    private final AvailabilityRepository availabilityRepository;
	private final PatientRepository patientRepository;
	private final OdontologistRepository odontologistRepository;
	private final AppointmentRepository appointmentRepository;

    public SeedServiceTest(
    		UserRepository userRepository,
            ToothRepository toothRepository,
            SideRepository sideRepository,
            BoxRepository boxRepository,
            StorageRepository storageRepository,
            PathologyRepository pathologyRepository,
            TreatmentRepository treatmentRepository,
            SupplierRepository supplierRepository,
            UtensilRepository utensilRepository,
            TreatmentUtensilRepository treatmentUtensilRepository,
            SpecialityRepository specialityRepository,
            AvailabilityRepository availabilityRepository,
            PatientRepository patientRepository,
            OdontologistRepository odontologistRepository,
            AppointmentRepository appointmentRepository
    ) {
    	this.userRepository = userRepository;
        this.toothRepository = toothRepository;
        this.sideRepository = sideRepository;
        this.boxRepository = boxRepository;
        this.storageRepository = storageRepository;
        this.pathologyRepository = pathologyRepository;
        this.treatmentRepository = treatmentRepository;
        this.supplierRepository = supplierRepository;
        this.utensilRepository = utensilRepository;
        this.treatmentUtensilRepository = treatmentUtensilRepository;
        this.specialityRepository = specialityRepository;
        this.availabilityRepository = availabilityRepository;
        this.patientRepository = patientRepository;
        this.odontologistRepository = odontologistRepository;
        this.appointmentRepository = appointmentRepository;
    }
    
    
    private static final List<Patient> PRESET_PATIENTS = List.of(
            new Patient("Marc", "Garcia", "Lopez", "12345678901", "12345678A"),
            new Patient("Laia", "Martinez", "Soler", "12345678902", "12345678B"),
            new Patient("Pol", "Fernandez", "Ruiz", "12345678903", "12345678C"),
            new Patient("Anna", "Torres", "Vila", "12345678904", "12345678D"),
            new Patient("Judit", "Navarro", "Pons", "12345678905", "12345678E")
    );

    @Transactional
    public void seedPatientsIfMissing() {
        for (Patient p : PRESET_PATIENTS) {
            if (!patientRepository.existsByDni(p.getDni())) {
                patientRepository.save(new Patient(
                        p.getName(),
                        p.getLastname1(),
                        p.getLastname2(),
                        p.getSsn(),
                        p.getDni()
                ));
            }
        }
    }
    
    @Transactional
    public void seedOdontologistsIfMissing() {

        Map<Long, Speciality> specialityById = new HashMap<>();
        for (Speciality s : specialityRepository.findAll()) {
            specialityById.put(s.getId(), s);
        }

        Map<Long, Availability> availabilityById = new HashMap<>();
        for (Availability a : availabilityRepository.findAll()) {
            availabilityById.put(a.getId(), a);
        }

        class OdontologistPreset {
            String name;
            String lastname1;
            String lastname2;
            String dni;
            String licenseNumber;
            List<Long> specialityIds;
            List<Long> availabilityIds;

            OdontologistPreset(String name, String lastname1, String lastname2, String dni, String licenseNumber,
                               List<Long> specialityIds, List<Long> availabilityIds) {
                this.name = name;
                this.lastname1 = lastname1;
                this.lastname2 = lastname2;
                this.dni = dni;
                this.licenseNumber = licenseNumber;
                this.specialityIds = specialityIds;
                this.availabilityIds = availabilityIds;
            }
        }

        List<OdontologistPreset> preset = List.of(
                new OdontologistPreset("Jordi", "Casas", "Ribas", "11111111A", "COL001", List.of(1L, 2L), List.of(1L, 2L, 3L, 4L)),
                new OdontologistPreset("Marta", "Pujol", "Serra", "22222222B", "COL002", List.of(1L, 3L), List.of(5L, 6L, 7L, 8L)),
                new OdontologistPreset("Anna", "Vidal", "Costa", "33333333C", "COL003", List.of(4L, 5L), List.of(1L, 2L, 9L, 10L)),
                new OdontologistPreset("Pere", "Roca", "Mila", "44444444D", "COL004", List.of(6L), List.of(3L, 4L, 11L, 12L)),
                new OdontologistPreset("Clara", "Font", "Duran", "55555555E", "COL005", List.of(7L, 1L), List.of(5L, 6L, 7L, 8L))
        );

        for (OdontologistPreset op : preset) {
            if (!odontologistRepository.existsByDni(op.dni)) {
                Odontologist odontologist = new Odontologist();
                odontologist.setName(op.name);
                odontologist.setLastname1(op.lastname1);
                odontologist.setLastname2(op.lastname2);
                odontologist.setDni(op.dni);
                odontologist.setLicenseNumber(op.licenseNumber);

                Set<Speciality> specialities = new HashSet<>();
                for (Long specialityId : op.specialityIds) {
                    Speciality speciality = specialityById.get(specialityId);
                    if (speciality != null) {
                        specialities.add(speciality);
                    }
                }
                odontologist.setSpecialities(specialities);

                Set<Availability> availabilities = new HashSet<>();
                for (Long availabilityId : op.availabilityIds) {
                    Availability availability = availabilityById.get(availabilityId);
                    if (availability != null) {
                        availabilities.add(availability);
                    }
                }
                odontologist.setAvailabilities(availabilities);

                odontologistRepository.save(odontologist);
            }
        }
    }
    
    @Transactional
    public void seedAppointmentsIfMissing() {

        Map<Long, Patient> patientById = new HashMap<>();
        for (Patient p : patientRepository.findAll()) {
            patientById.put(p.getId(), p);
        }

        Map<Long, Box> boxById = new HashMap<>();
        for (Box b : boxRepository.findAll()) {
            boxById.put(b.getId(), b);
        }

        Map<Long, Odontologist> odontologistById = new HashMap<>();
        for (Odontologist o : odontologistRepository.findAll()) {
            odontologistById.put(o.getId(), o);
        }

        Map<Long, Treatment> treatmentById = new HashMap<>();
        for (Treatment t : treatmentRepository.findAll()) {
            treatmentById.put(t.getId(), t);
        }

        class AppointmentPreset {
            String motive;
            java.time.LocalDateTime date;
            Long patientId;
            Long boxId;
            Long odontologistId;
            Long treatmentId;

            AppointmentPreset(String motive, java.time.LocalDateTime date, Long patientId, Long boxId, Long odontologistId, Long treatmentId) {
                this.motive = motive;
                this.date = date;
                this.patientId = patientId;
                this.boxId = boxId;
                this.odontologistId = odontologistId;
                this.treatmentId = treatmentId;
            }
        }

        List<AppointmentPreset> preset = List.of(
                new AppointmentPreset("Primera visita", java.time.LocalDateTime.of(2026, 3, 10, 9, 0), 1L, 1L, 1L, 1L),
                new AppointmentPreset("Revisió general", java.time.LocalDateTime.of(2026, 3, 10, 10, 0), 2L, 2L, 2L, 2L),
                new AppointmentPreset("Empastament", java.time.LocalDateTime.of(2026, 3, 10, 11, 0), 3L, 3L, 1L, 8L),
                new AppointmentPreset("Endodòncia", java.time.LocalDateTime.of(2026, 3, 11, 9, 30), 4L, 1L, 1L, 11L),
                new AppointmentPreset("Neteja dental", java.time.LocalDateTime.of(2026, 3, 11, 12, 0), 5L, 4L, 3L, 5L)
        );

        for (AppointmentPreset ap : preset) {
            Patient patient = patientById.get(ap.patientId);
            Box box = boxById.get(ap.boxId);
            Odontologist odontologist = odontologistById.get(ap.odontologistId);
            Treatment treatment = treatmentById.get(ap.treatmentId);

            if (patient != null && box != null && odontologist != null && treatment != null) {

                boolean exists = appointmentRepository.findAll().stream().anyMatch(a ->
                        a.getPatient().getId().equals(ap.patientId) &&
                        a.getBox().getId().equals(ap.boxId) &&
                        a.getOdontologist().getId().equals(ap.odontologistId) &&
                        a.getTreatment().getId().equals(ap.treatmentId)
                );

                if (!exists) {
                    Appointment appointment = new Appointment();
                    appointment.setMotive(ap.motive);
                    appointment.setDate(ap.date);
                    appointment.setPatient(patient);
                    appointment.setBox(box);
                    appointment.setOdontologist(odontologist);
                    appointment.setTreatment(treatment);

                    appointmentRepository.save(appointment);
                }
            }
        }
    }

   
}