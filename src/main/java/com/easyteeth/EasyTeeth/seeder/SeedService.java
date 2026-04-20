package com.easyteeth.EasyTeeth.seeder;

import com.easyteeth.EasyTeeth.model.*;
import com.easyteeth.EasyTeeth.controller.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SeedService {

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
    private final StockStorageRepository stockStorageRepository;

    public SeedService(
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
            StockStorageRepository stockStorageRepository
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
        this.stockStorageRepository = stockStorageRepository;
    }

    private static final List<User> PRESET_USERS = List.of(
            new User("admin", "admin", true),
            new User("user1", "aula123", false),
            new User("user2", "aula123", false),
            new User("user3", "aula123", false)
    );

    @Transactional
    public void seedUsersIfMissing() {
        for (User u : PRESET_USERS) {
            if (!userRepository.existsByUsername(u.getUsername())) {
                userRepository.save(new User(u.getUsername(), u.getPassword(), u.isAdmin()));
            }
        }
    }

    private static final List<Tooth> PRESET_TOOTH = List.of(
            new Tooth("Incisiu central", 11),
            new Tooth("Incisiu lateral", 12),
            new Tooth("Caní", 13),
            new Tooth("Primer premolar", 14),
            new Tooth("Segon premolar", 15),
            new Tooth("Primer molar", 16),
            new Tooth("Segon molar", 17),
            new Tooth("Tercer molar", 18),

            new Tooth("Incisiu central", 21),
            new Tooth("Incisiu lateral", 22),
            new Tooth("Caní", 23),
            new Tooth("Primer premolar", 24),
            new Tooth("Segon premolar", 25),
            new Tooth("Primer molar", 26),
            new Tooth("Segon molar", 27),
            new Tooth("Tercer molar", 28),

            new Tooth("Incisiu central", 31),
            new Tooth("Incisiu lateral", 32),
            new Tooth("Caní", 33),
            new Tooth("Primer premolar", 34),
            new Tooth("Segon premolar", 35),
            new Tooth("Primer molar", 36),
            new Tooth("Segon molar", 37),
            new Tooth("Tercer molar", 38),

            new Tooth("Incisiu central", 41),
            new Tooth("Incisiu lateral", 42),
            new Tooth("Caní", 43),
            new Tooth("Primer premolar", 44),
            new Tooth("Segon premolar", 45),
            new Tooth("Primer molar", 46),
            new Tooth("Segon molar", 47),
            new Tooth("Tercer molar", 48),

            new Tooth("Incisiu central", 51),
            new Tooth("Incisiu lateral", 52),
            new Tooth("Caní", 53),
            new Tooth("Primer molar", 54),
            new Tooth("Segon molar", 55),

            new Tooth("Incisiu central", 61),
            new Tooth("Incisiu lateral", 62),
            new Tooth("Caní", 63),
            new Tooth("Primer molar", 64),
            new Tooth("Segon molar", 65),

            new Tooth("Incisiu central", 71),
            new Tooth("Incisiu lateral", 72),
            new Tooth("Caní", 73),
            new Tooth("Primer molar", 74),
            new Tooth("Segon molar", 75),

            new Tooth("Incisiu central", 81),
            new Tooth("Incisiu lateral", 82),
            new Tooth("Caní", 83),
            new Tooth("Primer molar", 84),
            new Tooth("Segon molar", 85)
    );

    @Transactional
    public void seedToothIfMissing() {
        for (Tooth t : PRESET_TOOTH) {
            if (!toothRepository.existsByNumber(t.getNumber())) {
                toothRepository.save(new Tooth(t.getName(), t.getNumber()));
            }
        }
    }

    private static final List<Side> PRESET_SIDE = List.of(
            new Side("Vestibular"),
            new Side("Lingual"),
            new Side("Mesial"),
            new Side("Distal"),
            new Side("Oclusal")
    );

    @Transactional
    public void seedSideIfMissing() {
        for (Side s : PRESET_SIDE) {
            if (!sideRepository.existsByName(s.getName())) {
                sideRepository.save(new Side(s.getName()));
            }
        }
    }

    private static final List<Box> PRESET_BOX = List.of(
            new Box(101),
            new Box(102)
    );

    @Transactional
    public void seedBoxIfMissing() {
        for (Box b : PRESET_BOX) {
            if (!boxRepository.existsByNumBox(b.getNumBox())) {
                boxRepository.save(new Box(b.getNumBox()));
            }
        }
    }

    private static final List<Storage> PRESET_STORAGE = List.of(
            new Storage(1)
    );

    @Transactional
    public void seedStorageIfMissing() {
        for (Storage s : PRESET_STORAGE) {
            if (!storageRepository.existsByNumStorage(s.getNumStorage())) {
                storageRepository.save(new Storage(s.getNumStorage()));
            }
        }
    }

    private static final List<Treatment> PRESET_TREATMENTS = List.of(
            new Treatment("Diagnòstic i exploració", 60),
            new Treatment("Revisió i control", 60),
            new Treatment("Radiografia periapical", 60),
            new Treatment("Anestèsia local", 60),
            new Treatment("Neteja dental (profilaxi)", 60),
            new Treatment("Poliment i fluorització", 60),
            new Treatment("Sellat de fissures", 60),
            new Treatment("Obturació simple (empastament)", 60),
            new Treatment("Obturació complexa", 60),
            new Treatment("Reconstrucció dental", 60),
            new Treatment("Endodòncia unirradicular", 60),
            new Treatment("Endodòncia multirradicular", 60),
            new Treatment("Desinfecció i irrigació endodòntica", 60),
            new Treatment("Extracció simple", 60),
            new Treatment("Extracció quirúrgica", 60),
            new Treatment("Curetaje periodontal (1 quadrant)", 60),
            new Treatment("Fèrula de descàrrega", 60),
            new Treatment("Blanquejament dental", 60)
    );

    @Transactional
    public void seedTreatmentsIfMissing() {
        for (Treatment t : PRESET_TREATMENTS) {
            if (!treatmentRepository.existsByName(t.getName())) {
                treatmentRepository.save(new Treatment(t.getName(), t.getDuration()));
            }
        }
    }

    private static final List<Pathology> PRESET_PATHOLOGY = List.of(
            new Pathology("Càries"),
            new Pathology("Càries Radiogràfica"),
            new Pathology("Sellat de fosses i fissures"),
            new Pathology("Absència Natural"),
            new Pathology("Extracció"),
            new Pathology("Endodòncia"),
            new Pathology("Blanquejament"),
            new Pathology("Erosió dental"),
            new Pathology("Sensibilitat dental"),
            new Pathology("Pigmentació")
    );

    @Transactional
    public void seedPathologyIfMissingWithTreatments() {

        Map<String, Treatment> treatmentByName = new HashMap<>();
        for (Treatment tr : treatmentRepository.findAll()) {
            treatmentByName.put(tr.getName(), tr);
        }

        Map<String, List<String>> relations = Map.ofEntries(
        	    Map.entry("Càries", List.of(
        	            "Diagnòstic i exploració",
        	            "Radiografia periapical",
        	            "Obturació simple (empastament)",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Càries Radiogràfica", List.of(
        	            "Diagnòstic i exploració",
        	            "Radiografia periapical",
        	            "Obturació simple (empastament)",
        	            "Obturació complexa"
        	    )),
        	    Map.entry("Sellat de fosses i fissures", List.of(
        	            "Diagnòstic i exploració",
        	            "Sellat de fissures",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Absència Natural", List.of(
        	            "Diagnòstic i exploració",
        	            "Radiografia periapical",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Extracció", List.of(
        	            "Diagnòstic i exploració",
        	            "Radiografia periapical",
        	            "Extracció simple",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Endodòncia", List.of(
        	            "Diagnòstic i exploració",
        	            "Radiografia periapical",
        	            "Endodòncia unirradicular",
        	            "Endodòncia multirradicular",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Blanquejament", List.of(
        	            "Blanquejament dental"
        	    )),
        	    Map.entry("Erosió dental", List.of(
        	            "Diagnòstic i exploració",
        	            "Obturació simple (empastament)",
        	            "Poliment i fluorització",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Sensibilitat dental", List.of(
        	            "Diagnòstic i exploració",
        	            "Poliment i fluorització",
        	            "Revisió i control"
        	    )),
        	    Map.entry("Pigmentació", List.of(
        	            "Diagnòstic i exploració",
        	            "Neteja dental (profilaxi)",
        	            "Poliment i fluorització",
        	            "Revisió i control"
        	    ))
        	);

        for (Pathology p : PRESET_PATHOLOGY) {

            Pathology pathology = pathologyRepository.findByName(p.getName())
                    .orElseGet(() -> pathologyRepository.save(new Pathology(p.getName())));

            List<String> treatmentNames = relations.get(pathology.getName());
            if (treatmentNames != null) {
                for (String trName : treatmentNames) {

                    Treatment tr = treatmentByName.get(trName);
                    if (tr == null) {
                        throw new IllegalStateException("No existeix el tractament: " + trName);
                    }

                    if (!pathology.getTreatments().contains(tr)) {
                        pathology.addTreatment(tr);
                    }
                }
            }

            pathologyRepository.save(pathology);
        }
    }

    private static final List<Supplier> PRESET_SUPPLIERS = List.of(
            new Supplier("Coltene Dental Materials", "vendes@coltenecatalunya.es", 931234567),
            new Supplier("GC Corporation", "servei@gccorp-dental.es", 972345678),
            new Supplier("Septodont Spain", "distribucio@septodont-spain.es", 934567890),
            new Supplier("Hu-Friedy Instruments", "clients@hufriedyspain.es", 972112233),
            new Supplier("Universal Dental Supplies", "compres@universaldentalspain.es", 933221100)
    );

    @Transactional
    public void seedSuppliersIfMissing() {
        for (Supplier s : PRESET_SUPPLIERS) {
            if (!supplierRepository.existsByName(s.getName())) {
                supplierRepository.save(new Supplier(s.getName(), s.getEmail(), s.getPhone()));
            }
        }
    }

    @Transactional
    public void seedUtensilsIfMissingWithTreatments() {

        Map<String, Supplier> supplierByName = new HashMap<>();
        for (Supplier s : supplierRepository.findAll()) {
            supplierByName.put(s.getName(), s);
        }

        record UtensilPreset(String name, String brand, String model, double price, String supplierName) {}

        List<UtensilPreset> preset = List.of(
                // Basic Instruments - Mirrors and Explorers
                new UtensilPreset("Mirall intraoral", "Hu-Friedy", "MIR-001", 2.50, "Hu-Friedy Instruments"),
                new UtensilPreset("Explorador dental", "Hu-Friedy", "EXP-001", 1.75, "Hu-Friedy Instruments"),
                new UtensilPreset("Serreta manual", "Coltene", "SCA-001", 3.25, "Coltene Dental Materials"),
                new UtensilPreset("Raspall endodòntic", "Universal", "ENP-001", 0.50, "Universal Dental Supplies"),
                
                // Disposable Items
                new UtensilPreset("Guants de nitrili (caixa 100)", "Universal", "GLV-100", 4.99, "Universal Dental Supplies"),
                new UtensilPreset("Mascareta quirúrgica (caixa 50)", "Universal", "MAS-050", 2.99, "Universal Dental Supplies"),
                new UtensilPreset("Bavella dental (paquet 50)", "Coltene", "BIB-050", 3.75, "Coltene Dental Materials"),
                new UtensilPreset("Gasa estèril (paquet 100)", "Septodont", "GAZ-100", 2.50, "Septodont Spain"),
                new UtensilPreset("Rotlle de cotó (caixa)", "Universal", "COT-BOX", 1.25, "Universal Dental Supplies"),
                
                // Syringes and Needles
                new UtensilPreset("Xeringa carpul reutilitzable", "Universal", "SYR-001", 8.50, "Universal Dental Supplies"),
                new UtensilPreset("Agulla 27G estèril (paquet 50)", "Septodont", "NED-27G", 3.99, "Septodont Spain"),
                new UtensilPreset("Agulla 30G estèril (paquet 50)", "Septodont", "NED-30G", 4.50, "Septodont Spain"),
                new UtensilPreset("Cartuix d'anestèsia 1.8ml (10 unitats)", "Septodont", "ANA-CAR", 6.75, "Septodont Spain"),
                
                // Polishing and Cleaning
                new UtensilPreset("Copa de poliment de goma", "Universal", "POL-COP", 0.75, "Universal Dental Supplies"),
                new UtensilPreset("Cepill de poliment", "Universal", "POL-BRU", 0.50, "Universal Dental Supplies"),
                new UtensilPreset("Pasta de poliment (tub 100g)", "GC", "POL-PAS", 2.25, "GC Corporation"),
                new UtensilPreset("Puntes de succió (paquet 50)", "Universal", "SUC-TIP", 2.99, "Universal Dental Supplies"),
                
                // Filling and Restoration Materials
                new UtensilPreset("Composite A2 (jeringa 4g)", "GC", "COM-A2", 3.50, "GC Corporation"),
                new UtensilPreset("Composite A3 (jeringa 4g)", "GC", "COM-A3", 3.50, "GC Corporation"),
                new UtensilPreset("Ciment de vidre ionomèric", "Coltene", "CEM-GI", 4.75, "Coltene Dental Materials"),
                new UtensilPreset("Fosset estèril (caixa 100)", "Universal", "FOE-100", 3.25, "Universal Dental Supplies"),
                
                // Etch and Bonding Agents
                new UtensilPreset("Grab estètic 37% (jeringa 1.2ml)", "Septodont", "ETC-37", 2.99, "Septodont Spain"),
                new UtensilPreset("Adhesiu dentinari (jeringa 1.5ml)", "GC", "BND-ADH", 5.50, "GC Corporation"),
                
                // Burs and Files
                new UtensilPreset("Fresa de diamant (1mm)", "Universal", "BUR-DIA1", 1.25, "Universal Dental Supplies"),
                new UtensilPreset("Fresa de diamant (1.5mm)", "Universal", "BUR-DIA15", 1.25, "Universal Dental Supplies"),
                new UtensilPreset("Fresa de carbur (1mm)", "Universal", "BUR-CAR1", 0.85, "Universal Dental Supplies"),
                new UtensilPreset("Limetres endodòntiques (joc 6)", "Universal", "FILE-ENP", 6.99, "Universal Dental Supplies"),
                
                // Endodontic Materials
                new UtensilPreset("Goma de dique (caixa 36)", "Coltene", "DAM-036", 2.75, "Coltene Dental Materials"),
                new UtensilPreset("Clamps de goma (paquet 5)", "Universal", "CLM-RUB", 3.50, "Universal Dental Supplies"),
                new UtensilPreset("Gutaperxa en barres (caixa 30)", "Septodont", "GTA-BAR", 3.99, "Septodont Spain"),
                new UtensilPreset("Cemento per endodòncia", "Coltene", "CEM-END", 5.25, "Coltene Dental Materials"),
                
                // Periodontal
                new UtensilPreset("Sonda periodontal", "Hu-Friedy", "PRD-001", 2.50, "Hu-Friedy Instruments"),
                new UtensilPreset("Cureta Gracey (assortiment 6 peces)", "Universal", "CUR-GRY", 12.99, "Universal Dental Supplies"),
                
                // General Supplies
                new UtensilPreset("Gafes de protecció", "Universal", "GLV-EYE", 1.50, "Universal Dental Supplies"),
                new UtensilPreset("Gorro de protecció", "Universal", "CAP-PRO", 0.75, "Universal Dental Supplies"),
                new UtensilPreset("Protector de camilla reutilitzable", "Universal", "PRT-CHR", 4.50, "Universal Dental Supplies")
        );

        for (UtensilPreset up : preset) {
            Supplier supplier = supplierByName.get(up.supplierName());
            if (supplier == null) {
                throw new IllegalStateException("No existeix el proveïdor: " + up.supplierName());
            }

            if (!utensilRepository.existsByNameAndModel(up.name(), up.model())) {
                utensilRepository.save(new Utensil(up.name(), up.brand(), up.model(), up.price(), supplier));
            }
        }

        Map<String, Treatment> treatmentByName = new HashMap<>();
        for (Treatment t : treatmentRepository.findAll()) {
            treatmentByName.put(t.getName(), t);
        }

        Map<String, Utensil> utensilByKey = new HashMap<>();
        for (Utensil u : utensilRepository.findAll()) {
            utensilByKey.put(u.getName() + "|" + u.getModel(), u);
        }

        class Linker {
            void link(String treatmentName, String utensilName, String utensilModel, int quantity) {
                Treatment t = treatmentByName.get(treatmentName);
                Utensil u = utensilByKey.get(utensilName + "|" + utensilModel);

                if (t == null) throw new IllegalStateException("No existeix el tractament: " + treatmentName);
                if (u == null) throw new IllegalStateException("No existeix l'utensili: " + utensilName + " (" + utensilModel + ")");

                if (!treatmentUtensilRepository.existsByTreatmentAndUtensil(t, u)) {
                    TreatmentUtensil tu = new TreatmentUtensil();
                    tu.setTreatment(t);
                    tu.setUtensil(u);
                    tu.setQuantity(quantity);
                    treatmentUtensilRepository.save(tu);
                }
            }
        }

        Linker linker = new Linker();

        // Diagnòstic i exploració
        linker.link("Diagnòstic i exploració", "Mirall intraoral", "MIR-001", 1);
        linker.link("Diagnòstic i exploració", "Explorador dental", "EXP-001", 1);
        linker.link("Diagnòstic i exploració", "Guants de nitrili (caixa 100)", "GLV-100", 1);
        linker.link("Diagnòstic i exploració", "Mascareta quirúrgica (caixa 50)", "MAS-050", 1);
        linker.link("Diagnòstic i exploració", "Gafes de protecció", "GLV-EYE", 1);

        // Revisió i control
        linker.link("Revisió i control", "Mirall intraoral", "MIR-001", 1);
        linker.link("Revisió i control", "Explorador dental", "EXP-001", 1);
        linker.link("Revisió i control", "Guants de nitrili (caixa 100)", "GLV-100", 1);
        linker.link("Revisió i control", "Mascareta quirúrgica (caixa 50)", "MAS-050", 1);

        // Radiografia periapical
        linker.link("Radiografia periapical", "Guants de nitrili (caixa 100)", "GLV-100", 1);
        linker.link("Radiografia periapical", "Mascareta quirúrgica (caixa 50)", "MAS-050", 1);

        // Anestèsia local
        linker.link("Anestèsia local", "Xeringa carpul reutilitzable", "SYR-001", 1);
        linker.link("Anestèsia local", "Agulla 27G estèril (paquet 50)", "NED-27G", 1);
        linker.link("Anestèsia local", "Cartuix d'anestèsia 1.8ml (10 unitats)", "ANA-CAR", 1);
        linker.link("Anestèsia local", "Guants de nitrili (caixa 100)", "GLV-100", 1);

        // Neteja dental (profilaxi)
        linker.link("Neteja dental (profilaxi)", "Serreta manual", "SCA-001", 2);
        linker.link("Neteja dental (profilaxi)", "Mirall intraoral", "MIR-001", 1);
        linker.link("Neteja dental (profilaxi)", "Rotlle de cotó (caixa)", "COT-BOX", 1);
        linker.link("Neteja dental (profilaxi)", "Puntes de succió (paquet 50)", "SUC-TIP", 2);
        linker.link("Neteja dental (profilaxi)", "Guants de nitrili (caixa 100)", "GLV-100", 1);

        // Poliment i fluorització
        linker.link("Poliment i fluorització", "Copa de poliment de goma", "POL-COP", 1);
        linker.link("Poliment i fluorització", "Pasta de poliment (tub 100g)", "POL-PAS", 1);
        linker.link("Poliment i fluorització", "Rotlle de cotó (caixa)", "COT-BOX", 1);
        linker.link("Poliment i fluorització", "Puntes de succió (paquet 50)", "SUC-TIP", 1);
        linker.link("Poliment i fluorització", "Guants de nitrili (caixa 100)", "GLV-100", 1);

        // Sellat de fissures
        linker.link("Sellat de fissures", "Fresa de diamant (1mm)", "BUR-DIA1", 1);
        linker.link("Sellat de fissures", "Grab estètic 37% (jeringa 1.2ml)", "ETC-37", 1);
        linker.link("Sellat de fissures", "Adhesiu dentinari (jeringa 1.5ml)", "BND-ADH", 1);
        linker.link("Sellat de fissures", "Composite A2 (jeringa 4g)", "COM-A2", 1);
        linker.link("Sellat de fissures", "Mirall intraoral", "MIR-001", 1);
        linker.link("Sellat de fissures", "Fosset estèril (caixa 100)", "FOE-100", 1);

        // Obturació simple (empastament)
        linker.link("Obturació simple (empastament)", "Fresa de diamant (1mm)", "BUR-DIA1", 1);
        linker.link("Obturació simple (empastament)", "Grab estètic 37% (jeringa 1.2ml)", "ETC-37", 1);
        linker.link("Obturació simple (empastament)", "Adhesiu dentinari (jeringa 1.5ml)", "BND-ADH", 1);
        linker.link("Obturació simple (empastament)", "Composite A2 (jeringa 4g)", "COM-A2", 1);
        linker.link("Obturació simple (empastament)", "Mirall intraoral", "MIR-001", 1);
        linker.link("Obturació simple (empastament)", "Fosset estèril (caixa 100)", "FOE-100", 1);

        // Obturació complexa
        linker.link("Obturació complexa", "Fresa de diamant (1.5mm)", "BUR-DIA15", 1);
        linker.link("Obturació complexa", "Composite A2 (jeringa 4g)", "COM-A2", 2);
        linker.link("Obturació complexa", "Composite A3 (jeringa 4g)", "COM-A3", 1);
        linker.link("Obturació complexa", "Grab estètic 37% (jeringa 1.2ml)", "ETC-37", 1);
        linker.link("Obturació complexa", "Adhesiu dentinari (jeringa 1.5ml)", "BND-ADH", 1);
        linker.link("Obturació complexa", "Mirall intraoral", "MIR-001", 1);
        linker.link("Obturació complexa", "Fosset estèril (caixa 100)", "FOE-100", 2);

        // Reconstrucció dental
        linker.link("Reconstrucció dental", "Composite A2 (jeringa 4g)", "COM-A2", 2);
        linker.link("Reconstrucció dental", "Composite A3 (jeringa 4g)", "COM-A3", 2);
        linker.link("Reconstrucció dental", "Adhesiu dentinari (jeringa 1.5ml)", "BND-ADH", 1);
        linker.link("Reconstrucció dental", "Grab estètic 37% (jeringa 1.2ml)", "ETC-37", 1);
        linker.link("Reconstrucció dental", "Mirall intraoral", "MIR-001", 1);
        linker.link("Reconstrucció dental", "Fosset estèril (caixa 100)", "FOE-100", 2);

        // Endodòncia unirradicular
        linker.link("Endodòncia unirradicular", "Limetres endodòntiques (joc 6)", "FILE-ENP", 1);
        linker.link("Endodòncia unirradicular", "Goma de dique (caixa 36)", "DAM-036", 1);
        linker.link("Endodòncia unirradicular", "Clamps de goma (paquet 5)", "CLM-RUB", 1);
        linker.link("Endodòncia unirradicular", "Gutaperxa en barres (caixa 30)", "GTA-BAR", 1);
        linker.link("Endodòncia unirradicular", "Cemento per endodòncia", "CEM-END", 1);
        linker.link("Endodòncia unirradicular", "Raspall endodòntic", "ENP-001", 3);
        linker.link("Endodòncia unirradicular", "Mirall intraoral", "MIR-001", 1);
        linker.link("Endodòncia unirradicular", "Fosset estèril (caixa 100)", "FOE-100", 1);

        // Endodòncia multirradicular
        linker.link("Endodòncia multirradicular", "Limetres endodòntiques (joc 6)", "FILE-ENP", 1);
        linker.link("Endodòncia multirradicular", "Goma de dique (caixa 36)", "DAM-036", 2);
        linker.link("Endodòncia multirradicular", "Clamps de goma (paquet 5)", "CLM-RUB", 1);
        linker.link("Endodòncia multirradicular", "Gutaperxa en barres (caixa 30)", "GTA-BAR", 2);
        linker.link("Endodòncia multirradicular", "Cemento per endodòncia", "CEM-END", 1);
        linker.link("Endodòncia multirradicular", "Raspall endodòntic", "ENP-001", 5);
        linker.link("Endodòncia multirradicular", "Mirall intraoral", "MIR-001", 1);
        linker.link("Endodòncia multirradicular", "Fosset estèril (caixa 100)", "FOE-100", 2);

        // Desinfecció i irrigació endodòntica
        linker.link("Desinfecció i irrigació endodòntica", "Raspall endodòntic", "ENP-001", 2);
        linker.link("Desinfecció i irrigació endodòntica", "Goma de dique (caixa 36)", "DAM-036", 1);
        linker.link("Desinfecció i irrigació endodòntica", "Fosset estèril (caixa 100)", "FOE-100", 1);

        // Extracció simple
        linker.link("Extracció simple", "Mirall intraoral", "MIR-001", 1);
        linker.link("Extracció simple", "Explorador dental", "EXP-001", 1);
        linker.link("Extracció simple", "Gasa estèril (paquet 100)", "GAZ-100", 3);
        linker.link("Extracció simple", "Rotlle de cotó (caixa)", "COT-BOX", 1);

        // Extracció quirúrgica
        linker.link("Extracció quirúrgica", "Fresa de carbur (1mm)", "BUR-CAR1", 1);
        linker.link("Extracció quirúrgica", "Mirall intraoral", "MIR-001", 1);
        linker.link("Extracció quirúrgica", "Gasa estèril (paquet 100)", "GAZ-100", 4);
        linker.link("Extracció quirúrgica", "Rotlle de cotó (caixa)", "COT-BOX", 2);

        // Curetaje periodontal
        linker.link("Curetaje periodontal (1 quadrant)", "Sonda periodontal", "PRD-001", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Cureta Gracey (assortiment 6 peces)", "CUR-GRY", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Mirall intraoral", "MIR-001", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Puntes de succió (paquet 50)", "SUC-TIP", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Gasa estèril (paquet 100)", "GAZ-100", 1);

        // Fèrula de descàrrega
        linker.link("Fèrula de descàrrega", "Composite A2 (jeringa 4g)", "COM-A2", 1);
        linker.link("Fèrula de descàrrega", "Adhesiu dentinari (jeringa 1.5ml)", "BND-ADH", 1);
        linker.link("Fèrula de descàrrega", "Mirall intraoral", "MIR-001", 1);

        // Blanquejament dental
        linker.link("Blanquejament dental", "Bavella dental (paquet 50)", "BIB-050", 1);
        linker.link("Blanquejament dental", "Gafes de protecció", "GLV-EYE", 1);
        linker.link("Blanquejament dental", "Guants de nitrili (caixa 100)", "GLV-100", 1);
        linker.link("Blanquejament dental", "Mascareta quirúrgica (caixa 50)", "MAS-050", 1);
    }

    private static final List<Speciality> PRESET_SPECIALITIES = List.of(
            new Speciality(null, "Odontologia general"),
            new Speciality(null, "Endodòncia"),
            new Speciality(null, "Periodòncia"),
            new Speciality(null, "Odontopediatria"),
            new Speciality(null, "Estètica dental"),
            new Speciality(null, "Cirurgia oral"),
            new Speciality(null, "Pròtesi dental")
    );

    @Transactional
    public void seedSpecialitiesIfMissingWithTreatments() {

        Map<String, Treatment> treatmentByName = new HashMap<>();
        for (Treatment t : treatmentRepository.findAll()) {
            treatmentByName.put(t.getName(), t);
        }

        Map<String, List<String>> specialityTreatmentMap = Map.of(
                "Odontologia general", List.of(
                        "Diagnòstic i exploració",
                        "Revisió i control",
                        "Radiografia periapical",
                        "Anestèsia local",
                        "Neteja dental (profilaxi)",
                        "Poliment i fluorització",
                        "Sellat de fissures",
                        "Obturació simple (empastament)",
                        "Obturació complexa",
                        "Reconstrucció dental",
                        "Extracció simple"
                ),
                "Endodòncia", List.of(
                        "Diagnòstic i exploració",
                        "Radiografia periapical",
                        "Anestèsia local",
                        "Endodòncia unirradicular",
                        "Endodòncia multirradicular",
                        "Desinfecció i irrigació endodòntica",
                        "Reconstrucció dental"
                ),
                "Periodòncia", List.of(
                        "Diagnòstic i exploració",
                        "Revisió i control",
                        "Neteja dental (profilaxi)",
                        "Curetaje periodontal (1 quadrant)",
                        "Poliment i fluorització"
                ),
                "Odontopediatria", List.of(
                        "Diagnòstic i exploració",
                        "Revisió i control",
                        "Neteja dental (profilaxi)",
                        "Poliment i fluorització",
                        "Sellat de fissures",
                        "Obturació simple (empastament)"
                ),
                "Estètica dental", List.of(
                        "Diagnòstic i exploració",
                        "Revisió i control",
                        "Neteja dental (profilaxi)",
                        "Poliment i fluorització",
                        "Blanquejament dental"
                ),
                "Cirurgia oral", List.of(
                        "Diagnòstic i exploració",
                        "Radiografia periapical",
                        "Anestèsia local",
                        "Extracció simple",
                        "Extracció quirúrgica"
                ),
                "Pròtesi dental", List.of(
                        "Diagnòstic i exploració",
                        "Revisió i control",
                        "Radiografia periapical"
                )
        );

        for (Speciality s : PRESET_SPECIALITIES) {

            Speciality speciality = specialityRepository.findByName(s.getName())
                    .orElseGet(() -> specialityRepository.save(new Speciality(null, s.getName())));

            List<String> tNames = specialityTreatmentMap.get(speciality.getName());
            if (tNames != null) {
                for (String tName : tNames) {

                    Treatment treatment = treatmentByName.get(tName);
                    if (treatment == null) {
                        throw new IllegalStateException("No existeix el tractament: " + tName);
                    }

                    if (!speciality.getTreatments().contains(treatment)) {
                        speciality.getTreatments().add(treatment);
                    }
                }
            }

            specialityRepository.save(speciality);
        }
    }

    private static final List<Availability> PRESET_AVAILABILITY = List.of(
            new Availability("MONDAY", true, false),
            new Availability("MONDAY", false, true),

            new Availability("TUESDAY", true, false),
            new Availability("TUESDAY", false, true),

            new Availability("WEDNESDAY", true, false),
            new Availability("WEDNESDAY", false, true),

            new Availability("THURSDAY", true, false),
            new Availability("THURSDAY", false, true),

            new Availability("FRIDAY", true, false),
            new Availability("FRIDAY", false, true),

            new Availability("SATURDAY", true, false),
            new Availability("SATURDAY", false, true)
    );

    @Transactional
    public void seedAvailabilityIfMissing() {
        for (Availability a : PRESET_AVAILABILITY) {
            if (!availabilityRepository.existsByDayOfWeekAndMorningAndAfternoon(
                    a.getDayOfWeek(),
                    a.isMorning(),
                    a.isAfternoon()
            )) {
                availabilityRepository.save(new Availability(
                        a.getDayOfWeek(),
                        a.isMorning(),
                        a.isAfternoon()
                ));
            }
        }
    }

    @Transactional
    public void seedStockStorageIfMissing() {
        List<Storage> storages = storageRepository.findAll();
        List<Utensil> utensils = utensilRepository.findAll();

        for (Storage storage : storages) {
            for (Utensil utensil : utensils) {
                if (!stockStorageRepository.existsByUtensilIdAndStorageId(utensil.getId(), storage.getId())) {
                    StockStorage stockStorage = new StockStorage();
                    stockStorage.setUtensil(utensil);
                    stockStorage.setStorage(storage);
                    stockStorage.setQuantity(0);
                    stockStorageRepository.save(stockStorage);
                }
            }
        }
    }
}