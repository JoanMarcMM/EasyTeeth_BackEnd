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
	private final OdontogramRepository odontogramRepository;
	private final BackgroundRepository backgroundRepository;

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
	        AppointmentRepository appointmentRepository,
	        OdontogramRepository odontogramRepository,
	        BackgroundRepository backgroundRepository
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
	    this.odontogramRepository = odontogramRepository;
	    this.backgroundRepository = backgroundRepository;
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
    
    @Transactional
    public void seedOdontogramsIfMissing() {

        Map<Long, Patient> patientById = new HashMap<>();
        for (Patient p : patientRepository.findAll()) {
            patientById.put(p.getId(), p);
        }

        Map<Long, Tooth> toothById = new HashMap<>();
        for (Tooth t : toothRepository.findAll()) {
            toothById.put(t.getId(), t);
        }

        Map<Long, Side> sideById = new HashMap<>();
        for (Side s : sideRepository.findAll()) {
            sideById.put(s.getId(), s);
        }

        Map<Long, Pathology> pathologyById = new HashMap<>();
        for (Pathology p : pathologyRepository.findAll()) {
            pathologyById.put(p.getId(), p);
        }

        class OdontogramPreset {
            Long patientId;
            Long toothId;
            Long sideId;
            Long pathologyId;
            boolean treated;
            String note;

            OdontogramPreset(Long patientId, Long toothId, Long sideId, Long pathologyId, boolean treated, String note) {
                this.patientId = patientId;
                this.toothId = toothId;
                this.sideId = sideId;
                this.pathologyId = pathologyId;
                this.treated = treated;
                this.note = note;
            }
        }

        List<OdontogramPreset> preset = List.of(
                // PACIENTE 1 - Marc
                new OdontogramPreset(1L, 16L, 1L, 1L, false, "Càries incipient a cara vestibular"),
                new OdontogramPreset(1L, 26L, 2L, 2L, true, "Empastament antic en bon estat"),
                new OdontogramPreset(1L, 36L, 5L, 3L, false, "Desgast oclusal lleu"),

                // PACIENTE 2 - Laia
                new OdontogramPreset(2L, 11L, 1L, 1L, false, "Petita càries vestibular"),
                new OdontogramPreset(2L, 21L, 1L, 4L, false, "Taca / lesió a revisar"),
                new OdontogramPreset(2L, 46L, 5L, 3L, true, "Reconstrucció oclusal prèvia"),

                // PACIENTE 3 - Pol
                new OdontogramPreset(3L, 14L, 3L, 1L, false, "Càries interproximal"),
                new OdontogramPreset(3L, 24L, 4L, 1L, false, "Càries interproximal"),
                new OdontogramPreset(3L, 31L, 1L, 5L, false, "Fractura petita en vora incisal"),

                // PACIENTE 4 - Anna
                new OdontogramPreset(4L, 37L, 5L, 2L, true, "Obtura antiga"),
                new OdontogramPreset(4L, 47L, 5L, 1L, false, "Càries oclusal"),
                new OdontogramPreset(4L, 12L, 1L, 4L, false, "Lesió blanca a controlar"),

                // PACIENTE 5 - Judit
                new OdontogramPreset(5L, 22L, 2L, 1L, false, "Càries palatina"),
                new OdontogramPreset(5L, 35L, 5L, 3L, false, "Desgast oclusal moderat"),
                new OdontogramPreset(5L, 45L, 5L, 2L, true, "Empastament en molar inferior")
        );

        for (OdontogramPreset op : preset) {

            Patient patient = patientById.get(op.patientId);
            Tooth tooth = toothById.get(op.toothId);
            Side side = sideById.get(op.sideId);
            Pathology pathology = pathologyById.get(op.pathologyId);

            if (patient != null && tooth != null && side != null && pathology != null) {

                boolean exists = odontogramRepository.existsByPatientIdAndToothIdAndSideIdAndPathologyId(
                        op.patientId,
                        op.toothId,
                        op.sideId,
                        op.pathologyId
                );

                if (!exists) {
                    Odontogram odontogram = new Odontogram();
                    odontogram.setPatient(patient);
                    odontogram.setTooth(tooth);
                    odontogram.setSide(side);
                    odontogram.setPathology(pathology);
                    odontogram.setTreated(op.treated);
                    odontogram.setNote(op.note);

                    odontogramRepository.save(odontogram);
                }
            }
        }
    }
    
    @Transactional
    public void seedBackgroundsIfMissing() {

        Map<Long, Patient> patientById = new HashMap<>();
        for (Patient p : patientRepository.findAll()) {
            patientById.put(p.getId(), p);
        }

        class BackgroundPreset {
            Long patientId;
            String familyHistory;
            String healthState;
            String lifeHabits;
            String allergies;
            String medication;
            boolean importantAllergie;
            boolean infectiousDisease;
            boolean hasSignedConsent;
            boolean hasSignedAnesthesia;

            BackgroundPreset(
                    Long patientId,
                    String familyHistory,
                    String healthState,
                    String lifeHabits,
                    String allergies,
                    String medication,
                    boolean importantAllergie,
                    boolean infectiousDisease,
                    boolean hasSignedConsent,
                    boolean hasSignedAnesthesia
            ) {
                this.patientId = patientId;
                this.familyHistory = familyHistory;
                this.healthState = healthState;
                this.lifeHabits = lifeHabits;
                this.allergies = allergies;
                this.medication = medication;
                this.importantAllergie = importantAllergie;
                this.infectiousDisease = infectiousDisease;
                this.hasSignedConsent = hasSignedConsent;
                this.hasSignedAnesthesia = hasSignedAnesthesia;
            }
        }

        List<BackgroundPreset> preset = List.of(
                new BackgroundPreset(
                        1L,
                        "Pare amb antecedents de càries recurrents i mare amb bruxisme.",
                        "Bon estat general de salut.",
                        "Raspallat 2 cops al dia, consum moderat de cafè.",
                        "Cap al·lèrgia coneguda.",
                        "No pren medicació habitual.",
                        false,
                        false,
                        true,
                        true
                ),
                new BackgroundPreset(
                        2L,
                        "Antecedents familiars de sensibilitat dental i gingivitis.",
                        "Salut general correcta.",
                        "Raspallat irregular, consum freqüent de dolços.",
                        "Al·lèrgia lleu a penicil·lina.",
                        "Antihistamínics en èpoques d'al·lèrgia.",
                        true,
                        false,
                        true,
                        true
                ),
                new BackgroundPreset(
                        3L,
                        "Sense antecedents familiars rellevants.",
                        "Pacient amb estrès i episodis de bruxisme nocturn.",
                        "Fuma ocasionalment i pren cafè diàriament.",
                        "Cap al·lèrgia coneguda.",
                        "Ibuprofèn puntualment.",
                        false,
                        false,
                        true,
                        false
                ),
                new BackgroundPreset(
                        4L,
                        "Mare amb problemes periodontals.",
                        "Bona salut general, però amb sensibilitat dental.",
                        "Higiene oral bona, dieta àcida freqüent.",
                        "Al·lèrgia a alguns antiinflamatoris.",
                        "Pren suplements vitamínics.",
                        true,
                        false,
                        true,
                        true
                ),
                new BackgroundPreset(
                        5L,
                        "Antecedents de càries i desgast dental a la família.",
                        "Salut general estable.",
                        "Raspallat 3 cops al dia, però amb apretament dental.",
                        "Sense al·lèrgies conegudes.",
                        "No pren medicació habitual.",
                        false,
                        false,
                        true,
                        false
                )
        );

        for (BackgroundPreset bp : preset) {
            Patient patient = patientById.get(bp.patientId);

            if (patient != null && !backgroundRepository.existsByPatientId(bp.patientId)) {
                Background background = new Background();
                background.setPatient(patient);
                background.setFamilyHistory(bp.familyHistory);
                background.setHealthState(bp.healthState);
                background.setLifeHabits(bp.lifeHabits);
                background.setAllergies(bp.allergies);
                background.setMedication(bp.medication);
                background.setImportantAllergie(bp.importantAllergie);
                background.setInfectiousDisease(bp.infectiousDisease);
                background.setHasSignedConsent(bp.hasSignedConsent);
                background.setHasSignedAnesthesia(bp.hasSignedAnesthesia);

                backgroundRepository.save(background);
            }
        }
    }

   
}