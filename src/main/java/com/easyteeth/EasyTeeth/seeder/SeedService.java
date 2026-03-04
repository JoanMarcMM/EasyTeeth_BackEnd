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
            SpecialityRepository specialityRepository
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
    }

    private static final List<User> PRESET_USERS = List.of(
            new User("user1", "aula123"),
            new User("user2", "aula123"),
            new User("user3", "aula123")
    );

    @Transactional
    public void seedUsersIfMissing() {
        for (User u : PRESET_USERS) {
            if (!userRepository.existsByUsername(u.getUsername())) {
                userRepository.save(new User(u.getUsername(), u.getPassword()));
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
            new Box(102),
            new Box(103),
            new Box(104),
            new Box(105)
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
            new Storage(201),
            new Storage(202)
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
            new Treatment("Diagnòstic i exploració", 20),
            new Treatment("Revisió i control", 15),
            new Treatment("Radiografia periapical", 10),
            new Treatment("Anestèsia local", 5),
            new Treatment("Neteja dental (profilaxi)", 40),
            new Treatment("Poliment i fluorització", 15),
            new Treatment("Sellat de fissures", 30),
            new Treatment("Obturació simple (empastament)", 45),
            new Treatment("Obturació complexa", 60),
            new Treatment("Reconstrucció dental", 60),
            new Treatment("Endodòncia unirradicular", 90),
            new Treatment("Endodòncia multirradicular", 120),
            new Treatment("Desinfecció i irrigació endodòntica", 20),
            new Treatment("Extracció simple", 45),
            new Treatment("Extracció quirúrgica", 75),
            new Treatment("Curetaje periodontal (1 quadrant)", 60),
            new Treatment("Fèrula de descàrrega", 30),
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
            new Pathology("Càries inicial"),
            new Pathology("Càries superficial"),
            new Pathology("Càries mitjana"),
            new Pathology("Càries profunda"),
            new Pathology("Pulpitis reversible"),
            new Pathology("Pulpitis irreversible"),
            new Pathology("Necrosi pulpar"),
            new Pathology("Abscés periapical"),
            new Pathology("Hipersensibilitat dentinària"),
            new Pathology("Síndrome del queixal esquerdat")
    );

    @Transactional
    public void seedPathologyIfMissingWithTreatments() {

        Map<String, Treatment> treatmentByName = new HashMap<>();
        for (Treatment tr : treatmentRepository.findAll()) {
            treatmentByName.put(tr.getName(), tr);
        }

        Map<String, List<String>> relations = Map.of(
                "Càries inicial", List.of("Diagnòstic i exploració", "Obturació simple (empastament)", "Revisió i control"),
                "Càries superficial", List.of("Diagnòstic i exploració", "Obturació simple (empastament)"),
                "Càries mitjana", List.of("Diagnòstic i exploració", "Obturació complexa", "Reconstrucció dental"),
                "Càries profunda", List.of("Diagnòstic i exploració", "Endodòncia unirradicular", "Reconstrucció dental"),
                "Pulpitis reversible", List.of("Diagnòstic i exploració", "Obturació simple (empastament)", "Revisió i control"),
                "Pulpitis irreversible", List.of("Endodòncia unirradicular", "Reconstrucció dental"),
                "Necrosi pulpar", List.of("Endodòncia multirradicular", "Reconstrucció dental"),
                "Abscés periapical", List.of("Endodòncia unirradicular", "Revisió i control"),
                "Hipersensibilitat dentinària", List.of("Diagnòstic i exploració", "Revisió i control", "Poliment i fluorització"),
                "Síndrome del queixal esquerdat", List.of("Diagnòstic i exploració", "Reconstrucció dental", "Endodòncia unirradicular")
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
            new Supplier("DentPro Catalunya", "contacte@dentprocatalunya.cat", 931234567),
            new Supplier("Material Dental Pirineus", "info@materialdentalpirineus.cat", 972345678),
            new Supplier("OrtoLab Barcelona", "suport@ortolabbarcelona.cat", 934567890),
            new Supplier("EndoTech Girona", "hola@endotechgirona.cat", 972112233),
            new Supplier("Clínic Supplies BCN", "servei@clinicsuppliesbcn.cat", 933221100)
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
                new UtensilPreset("Turbina d'alta velocitat", "KaVo", "EXPERTtorque E680", 1290.00, "DentPro Catalunya"),
                new UtensilPreset("Contraangle", "NSK", "Ti-Max X95L", 690.00, "DentPro Catalunya"),
                new UtensilPreset("Micromotor", "Bien-Air", "MX2", 890.00, "Clínic Supplies BCN"),
                new UtensilPreset("Làmpada de fotopolimerització", "Ivoclar", "Bluephase PowerCure", 980.00, "Material Dental Pirineus"),
                new UtensilPreset("Apex locator", "VDW", "Raypex 6", 750.00, "OrtoLab Barcelona"),
                new UtensilPreset("Motor d'endodòncia", "VDW", "Reciproc Gold", 1350.00, "EndoTech Girona"),
                new UtensilPreset("Localitzador de càries", "KaVo", "DIAGNOdent", 1150.00, "DentPro Catalunya"),
                new UtensilPreset("Càmera intraoral", "Dürr Dental", "VistaCam iX", 1850.00, "Material Dental Pirineus"),
                new UtensilPreset("Ultrasons d'higiene", "EMS", "Piezon 150", 2100.00, "Clínic Supplies BCN"),
                new UtensilPreset("Joc de miralls intraorals", "Hu-Friedy", "MIR-SET01", 79.90, "Clínic Supplies BCN"),
                new UtensilPreset("Sonda periodontal", "Hu-Friedy", "PCP-UNC15", 34.50, "Clínic Supplies BCN"),
                new UtensilPreset("Dique de goma (kit)", "Coltene", "Dam Kit", 59.00, "EndoTech Girona"),
                new UtensilPreset("Xeringa d'irrigació endodòntica", "Ultradent", "Endo-Eze", 12.50, "EndoTech Girona"),
                new UtensilPreset("Kit de blanquejament (làmpada)", "Philips", "Zoom WhiteSpeed", 2990.00, "Material Dental Pirineus"),
                new UtensilPreset("Gafes de protecció", "UVEX", "i-vo", 9.90, "Clínic Supplies BCN"),
                new UtensilPreset("Joc de puntes d'ultrasons", "EMS", "PS Tips", 149.00, "Clínic Supplies BCN")
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

        linker.link("Diagnòstic i exploració", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Diagnòstic i exploració", "Càmera intraoral", "VistaCam iX", 1);
        linker.link("Diagnòstic i exploració", "Localitzador de càries", "DIAGNOdent", 1);
        linker.link("Diagnòstic i exploració", "Gafes de protecció", "i-vo", 1);

        linker.link("Revisió i control", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Revisió i control", "Càmera intraoral", "VistaCam iX", 1);
        linker.link("Revisió i control", "Gafes de protecció", "i-vo", 1);

        linker.link("Radiografia periapical", "Càmera intraoral", "VistaCam iX", 1);
        linker.link("Radiografia periapical", "Gafes de protecció", "i-vo", 1);

        linker.link("Anestèsia local", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Anestèsia local", "Gafes de protecció", "i-vo", 1);

        linker.link("Neteja dental (profilaxi)", "Ultrasons d'higiene", "Piezon 150", 1);
        linker.link("Neteja dental (profilaxi)", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Neteja dental (profilaxi)", "Joc de puntes d'ultrasons", "PS Tips", 1);
        linker.link("Neteja dental (profilaxi)", "Gafes de protecció", "i-vo", 1);

        linker.link("Poliment i fluorització", "Ultrasons d'higiene", "Piezon 150", 1);
        linker.link("Poliment i fluorització", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Poliment i fluorització", "Gafes de protecció", "i-vo", 1);

        linker.link("Sellat de fissures", "Contraangle", "Ti-Max X95L", 1);
        linker.link("Sellat de fissures", "Làmpada de fotopolimerització", "Bluephase PowerCure", 1);
        linker.link("Sellat de fissures", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Sellat de fissures", "Gafes de protecció", "i-vo", 1);

        linker.link("Obturació simple (empastament)", "Turbina d'alta velocitat", "EXPERTtorque E680", 1);
        linker.link("Obturació simple (empastament)", "Làmpada de fotopolimerització", "Bluephase PowerCure", 1);
        linker.link("Obturació simple (empastament)", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Obturació simple (empastament)", "Gafes de protecció", "i-vo", 1);

        linker.link("Obturació complexa", "Turbina d'alta velocitat", "EXPERTtorque E680", 1);
        linker.link("Obturació complexa", "Contraangle", "Ti-Max X95L", 1);
        linker.link("Obturació complexa", "Làmpada de fotopolimerització", "Bluephase PowerCure", 1);
        linker.link("Obturació complexa", "Micromotor", "MX2", 1);
        linker.link("Obturació complexa", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Obturació complexa", "Gafes de protecció", "i-vo", 1);

        linker.link("Reconstrucció dental", "Contraangle", "Ti-Max X95L", 1);
        linker.link("Reconstrucció dental", "Làmpada de fotopolimerització", "Bluephase PowerCure", 1);
        linker.link("Reconstrucció dental", "Micromotor", "MX2", 1);
        linker.link("Reconstrucció dental", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Reconstrucció dental", "Gafes de protecció", "i-vo", 1);

        linker.link("Endodòncia unirradicular", "Apex locator", "Raypex 6", 1);
        linker.link("Endodòncia unirradicular", "Motor d'endodòncia", "Reciproc Gold", 1);
        linker.link("Endodòncia unirradicular", "Dique de goma (kit)", "Dam Kit", 1);
        linker.link("Endodòncia unirradicular", "Xeringa d'irrigació endodòntica", "Endo-Eze", 3);
        linker.link("Endodòncia unirradicular", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Endodòncia unirradicular", "Gafes de protecció", "i-vo", 1);

        linker.link("Endodòncia multirradicular", "Apex locator", "Raypex 6", 1);
        linker.link("Endodòncia multirradicular", "Motor d'endodòncia", "Reciproc Gold", 1);
        linker.link("Endodòncia multirradicular", "Dique de goma (kit)", "Dam Kit", 1);
        linker.link("Endodòncia multirradicular", "Xeringa d'irrigació endodòntica", "Endo-Eze", 5);
        linker.link("Endodòncia multirradicular", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Endodòncia multirradicular", "Gafes de protecció", "i-vo", 1);

        linker.link("Desinfecció i irrigació endodòntica", "Xeringa d'irrigació endodòntica", "Endo-Eze", 4);
        linker.link("Desinfecció i irrigació endodòntica", "Dique de goma (kit)", "Dam Kit", 1);
        linker.link("Desinfecció i irrigació endodòntica", "Gafes de protecció", "i-vo", 1);

        linker.link("Extracció simple", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Extracció simple", "Micromotor", "MX2", 1);
        linker.link("Extracció simple", "Gafes de protecció", "i-vo", 1);

        linker.link("Extracció quirúrgica", "Micromotor", "MX2", 1);
        linker.link("Extracció quirúrgica", "Contraangle", "Ti-Max X95L", 1);
        linker.link("Extracció quirúrgica", "Gafes de protecció", "i-vo", 1);

        linker.link("Curetaje periodontal (1 quadrant)", "Sonda periodontal", "PCP-UNC15", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Ultrasons d'higiene", "Piezon 150", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Joc de puntes d'ultrasons", "PS Tips", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Curetaje periodontal (1 quadrant)", "Gafes de protecció", "i-vo", 1);

        linker.link("Fèrula de descàrrega", "Joc de miralls intraorals", "MIR-SET01", 1);
        linker.link("Fèrula de descàrrega", "Càmera intraoral", "VistaCam iX", 1);
        linker.link("Fèrula de descàrrega", "Gafes de protecció", "i-vo", 1);

        linker.link("Blanquejament dental", "Kit de blanquejament (làmpada)", "Zoom WhiteSpeed", 1);
        linker.link("Blanquejament dental", "Gafes de protecció", "i-vo", 2);
        linker.link("Blanquejament dental", "Càmera intraoral", "VistaCam iX", 1);
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
}