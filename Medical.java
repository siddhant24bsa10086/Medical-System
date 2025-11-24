import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

// ========================= Utility: Logo + Loading =========================
class UIEffects {

    public static void showLogo() {
        System.out.println(
                " ██████╗ ██╗████████╗██╗   ██╗    ██╗  ██╗ ██████╗ ██████╗ ████████╗ █████╗ ██╗     \n" +
                        "██╔════╝ ██║╚══██╔══╝╚██╗ ██╔╝    ██║  ██║██╔═══██╗██╔══██╗╚══██╔══╝██╔══██╗██║     \n" +
                        "██║  ███╗██║   ██║    ╚████╔╝     ███████║██║   ██║██████╔╝   ██║   ███████║██║     \n" +
                        "██║   ██║██║   ██║     ╚██╔╝      ██╔══██║██║   ██║██╔══██╗   ██║   ██╔══██║██║     \n" +
                        "╚██████╔╝██║   ██║      ██║       ██║  ██║╚██████╔╝██║  ██║   ██║   ██║  ██║███████╗\n" +
                        " ╚═════╝ ╚═╝   ╚═╝      ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝\n" +
                        "                               CITY HOSPITAL SYSTEM\n");
    }

    public static void loadingAnimation() {
        String[] bars = {
                "[□□□□□□□□□□] 0%",
                "[■■□□□□□□□□] 20%",
                "[■■■■□□□□□□] 40%",
                "[■■■■■■□□□□] 60%",
                "[■■■■■■■■□□] 80%",
                "[■■■■■■■■■■] 100%"
        };

        System.out.println("Loading System...");
        for (String b : bars) {
            System.out.print("\r" + b);
            try {
                Thread.sleep(350);
            } catch (Exception e) {
            }
        }
        System.out.println("\nSystem Ready!\n");
    }
}

// ========================= Doctor Class =========================
class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String phone;
    private String availableTime; // HH:MM-HH:MM
    private String roomNo;

    public Doctor(int doctorId, String name, String specialization,
            String phone, String availableTime, String roomNo) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.availableTime = availableTime;
        this.roomNo = roomNo;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String toCSV() {
        return doctorId + "," + name + "," + specialization + "," +
                phone + "," + availableTime + "," + roomNo;
    }

    public static Doctor fromCSV(String line) {
        String[] p = line.split(",");
        if (p.length < 6)
            return null;
        return new Doctor(
                Integer.parseInt(p[0]), p[1], p[2], p[3], p[4], p[5]);
    }

    @Override
    public String toString() {
        return String.format("%-3d | %-15s | %-18s | %-11s | %-4s",
                doctorId, name, specialization, availableTime, roomNo);
    }
}

// ========================= Patient Class =========================
class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String problem;

    public Patient(int patientId, String name, int age, String gender,
            String phone, String problem) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.problem = problem;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String toCSV() {
        return patientId + "," + name + "," + age + "," + gender + "," +
                phone + "," + problem;
    }

    public static Patient fromCSV(String line) {
        String[] p = line.split(",");
        if (p.length < 6)
            return null;
        return new Patient(
                Integer.parseInt(p[0]),
                p[1],
                Integer.parseInt(p[2]),
                p[3],
                p[4],
                p[5]);
    }
}

// ========================= Appointment Class =========================
class Appointment {
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private String date;
    private String timeSlot;
    private String status;

    public Appointment(int appointmentId, int doctorId, int patientId,
            String date, String timeSlot, String status) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        status = s;
    }

    public String toCSV() {
        return appointmentId + "," + doctorId + "," + patientId + "," +
                date + "," + timeSlot + "," + status;
    }

    public static Appointment fromCSV(String line) {
        String[] p = line.split(",");
        if (p.length < 6)
            return null;
        return new Appointment(
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]),
                p[3],
                p[4],
                p[5]);
    }
}

// ========================= Hospital System =========================
class HospitalSystem {

    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Patient> patients = new ArrayList<>();
    ArrayList<Appointment> appointments = new ArrayList<>();

    String doctorFile = "doctors.csv";
    String patientFile = "patients.csv";
    String appointmentFile = "appointments.csv";

    public HospitalSystem() {
        loadDoctors();
        loadPatients();
        loadAppointments();
    }

    public Doctor getDoctorById(int id) {
        for (Doctor d : doctors) {
            if (d.getDoctorId() == id)
                return d;
        }
        return null;
    }

    public Patient getPatientById(int id) {
        for (Patient p : patients) {
            if (p.getPatientId() == id)
                return p;
        }
        return null;
    }

    void addDefaultDoctors() {
        doctors.clear();
        doctors.add(new Doctor(1, "Dr. Mehta", "Cardiologist", "9991112233", "10:00-13:00", "101"));
        doctors.add(new Doctor(2, "Dr. Riya", "Dentist", "8882223344", "09:00-12:00", "102"));
        doctors.add(new Doctor(3, "Dr. Arjun", "Orthopedic", "7773334455", "14:00-17:00", "103"));
        doctors.add(new Doctor(4, "Dr. Kavya", "Neurologist", "6664445566", "10:00-12:00", "201"));
        doctors.add(new Doctor(5, "Dr. Suresh", "ENT", "5556667788", "15:00-17:00", "202"));
        doctors.add(new Doctor(6, "Dr. Nidhi", "Physician", "4445556677", "11:00-14:00", "203"));
        doctors.add(new Doctor(7, "Dr. Varun", "Dermatologist", "3334445566", "12:00-15:00", "204"));
        saveDoctors();
    }

    void addDefaultPatients() {
        patients.clear();
        patients.add(new Patient(1, "Amit Sharma", 30, "Male", "9876543210", "Chest pain"));
        patients.add(new Patient(2, "Neha Verma", 25, "Female", "9876500000", "Tooth ache"));
        patients.add(new Patient(3, "Rajesh Kumar", 40, "Male", "9876511111", "Knee pain"));
        patients.add(new Patient(4, "Priya Singh", 28, "Female", "9876522222", "Headache"));
        patients.add(new Patient(5, "Rohan Gupta", 35, "Male", "9876533333", "Ear pain"));
        savePatients();
    }

    void addDefaultAppointments() {
        appointments.clear();
        appointments.add(new Appointment(1, 1, 1, "11/20/2025", "10:30-10:45", "BOOKED"));
        appointments.add(new Appointment(2, 2, 2, "11/20/2025", "09:00-09:15", "BOOKED"));
        appointments.add(new Appointment(3, 3, 3, "11:21:2025", "14:00-14:15", "BOOKED"));
        appointments.add(new Appointment(4, 4, 4, "11/22/2025", "11:00-11:15", "BOOKED"));
        appointments.add(new Appointment(5, 5, 5, "11/22/2025", "15:00-15:15", "BOOKED"));
        saveAppointments();
    }

    void loadDoctors() {
        doctors.clear();
        try {
            File f = new File(doctorFile);
            if (!f.exists()) {
                addDefaultDoctors();
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            boolean any = false;
            while ((line = br.readLine()) != null) {
                Doctor d = Doctor.fromCSV(line);
                if (d != null) {
                    doctors.add(d);
                    any = true;
                }
            }
            br.close();
            if (!any)
                addDefaultDoctors();
        } catch (Exception e) {
            addDefaultDoctors();
        }
    }

    void saveDoctors() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(doctorFile));
            for (Doctor d : doctors)
                pw.println(d.toCSV());
            pw.close();
        } catch (Exception e) {
        }
    }

    void loadPatients() {
        patients.clear();
        try {
            File f = new File(patientFile);
            if (!f.exists()) {
                addDefaultPatients();
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            boolean any = false;
            while ((line = br.readLine()) != null) {
                Patient p = Patient.fromCSV(line);
                if (p != null) {
                    patients.add(p);
                    any = true;
                }
            }
            br.close();
            if (!any)
                addDefaultPatients();
        } catch (Exception e) {
            addDefaultPatients();
        }
    }

    void savePatients() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(patientFile));
            for (Patient p : patients)
                pw.println(p.toCSV());
            pw.close();
        } catch (Exception e) {
        }
    }

    void loadAppointments() {
        appointments.clear();
        try {
            File f = new File(appointmentFile);
            if (!f.exists()) {
                addDefaultAppointments();
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            boolean any = false;
            while ((line = br.readLine()) != null) {
                Appointment a = Appointment.fromCSV(line);
                if (a != null) {
                    appointments.add(a);
                    any = true;
                }
            }
            br.close();
            if (!any)
                addDefaultAppointments();
        } catch (Exception e) {
            addDefaultAppointments();
        }
    }

    void saveAppointments() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(appointmentFile));
            for (Appointment a : appointments)
                pw.println(a.toCSV());
            pw.close();
        } catch (Exception e) {
        }
    }

    public boolean isSlotTaken(int doctorId, String date, String ts) {
        for (Appointment a : appointments) {
            if (a.getDoctorId() == doctorId &&
                    a.getDate().equals(date) &&
                    a.getTimeSlot().equals(ts) &&
                    a.getStatus().equalsIgnoreCase("BOOKED"))
                return true;
        }
        return false;
    }

    public void addDoctor(Doctor d) {
        doctors.add(d);
        saveDoctors();
    }

    public void addPatient(Patient p) {
        patients.add(p);
        savePatients();
    }

    public void bookAppointment(Appointment a) {
        appointments.add(a);
        saveAppointments();
    }

    public void cancelAppointment(int id) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == id) {
                a.setStatus("CANCELLED");
                break;
            }
        }
        saveAppointments();
    }

    public void showAppointmentsByDoctor(int docId) {
        System.out.println("\n════════ APPOINTMENTS FOR DOCTOR ID " + docId + " ════════");
        System.out.println("ID | Date       | Time         | Patient        | Status");
        System.out.println("------------------------------------------------------------");

        boolean found = false;

        for (Appointment a : appointments) {
            if (a.getDoctorId() == docId) {
                Patient p = getPatientById(a.getPatientId());
                String pname = (p != null) ? p.getName() : "Unknown";

                System.out.printf("%-2d | %-10s | %-11s | %-13s | %-8s%n",
                        a.getAppointmentId(),
                        a.getDate(),
                        a.getTimeSlot(),
                        pname,
                        a.getStatus());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments found for this doctor.");
        }
    }
}

// ========================= MAIN PROGRAM =========================
public class MedicalSystem {

    public static void printDoctorList(HospitalSystem hs) {
        System.out.println("\n════════════ AVAILABLE DOCTORS ════════════");
        System.out.println("ID  | Name            | Specialization      | Timings     | Room");
        System.out.println("--------------------------------------------------------------");

        for (Doctor d : hs.doctors)
            System.out.println(d);

        System.out.println("--------------------------------------------------------------");
    }

    public static void printPatientList(HospitalSystem hs) {
        System.out.println("\n════════════ PATIENT LIST ════════════");
        System.out.println("ID  | Name            | Gender | Phone");
        System.out.println("----------------------------------------------");

        for (Patient p : hs.patients) {
            System.out.printf("%-3d | %-15s | %-6s | %-10s%n",
                    p.getPatientId(), p.getName(), p.getGender(), p.getPhone());
        }

        System.out.println("----------------------------------------------");
    }

    public static List<String> generateTimeSlots(String availableTime) {
        List<String> slots = new ArrayList<>();
        try {
            String[] parts = availableTime.split("-");
            LocalTime start = LocalTime.parse(parts[0]);
            LocalTime end = LocalTime.parse(parts[1]);

            LocalTime current = start;
            while (current.plusMinutes(15).isBefore(end)
                    || current.plusMinutes(15).equals(end)) {

                LocalTime next = current.plusMinutes(15);
                slots.add(current.toString() + "-" + next.toString());
                current = next;
            }
        } catch (Exception e) {
            slots.add("Invalid Timing Format");
        }
        return slots;
    }

    public static String chooseDate(Scanner sc) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate after = today.plusDays(2);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        String d1 = today.format(fmt);
        String d2 = tomorrow.format(fmt);
        String d3 = after.format(fmt);

        System.out.println("\nSelect Appointment Date:");
        System.out.println("1. Today (" + d1 + ")");
        System.out.println("2. Tomorrow (" + d2 + ")");
        System.out.println("3. Day After Tomorrow (" + d3 + ")");
        System.out.println("4. Enter Custom Date");
        System.out.print("Choice: ");

        int ch = sc.nextInt();
        sc.nextLine();

        switch (ch) {
            case 1:
                return d1;
            case 2:
                return d2;
            case 3:
                return d3;
            case 4:
                System.out.print("Enter (MM/DD/YYYY): ");
                return sc.nextLine().trim();
            default:
                return d1;
        }
    }

    public static void main(String[] args) {

        UIEffects.showLogo();
        UIEffects.loadingAnimation();

        HospitalSystem hs = new HospitalSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n════════════ MAIN MENU ════════════");
            System.out.println("1. Add Doctor");
            System.out.println("2. Register Patient");
            System.out.println("3. View Doctor List");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. View Appointments by Doctor");
            System.out.println("7. Exit");
            System.out.println("8. Generate Appointment Slip");
            System.out.print("Choice: ");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    sc.nextLine();
                    System.out.print("Doctor Name: ");
                    String dn = sc.nextLine();
                    System.out.print("Specialization: ");
                    String sp = sc.nextLine();
                    System.out.print("Phone: ");
                    String dp = sc.nextLine();
                    System.out.print("Available Time (HH:MM-HH:MM): ");
                    String dt = sc.nextLine();
                    System.out.print("Room: ");
                    String rm = sc.nextLine();

                    int newDocId = hs.doctors.size() + 1;
                    hs.addDoctor(new Doctor(newDocId, dn, sp, dp, dt, rm));

                    System.out.println("Doctor Added Successfully!");
                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Patient Name: ");
                    String pn = sc.nextLine();

                    System.out.print("Age: ");
                    int ag = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Gender: 1.Male 2.Female 3.Other");
                    int gg = sc.nextInt();
                    sc.nextLine();

                    String gdr = (gg == 1 ? "Male" : gg == 2 ? "Female" : "Other");

                    System.out.print("Phone: ");
                    String ph = sc.nextLine();

                    System.out.print("Problem: ");
                    String pr = sc.nextLine();

                    int newPatId = hs.patients.size() + 1;
                    hs.addPatient(new Patient(newPatId, pn, ag, gdr, ph, pr));

                    System.out.println("\nPatient Registered Successfully!");

                    printDoctorList(hs);
                    break;

                case 3:
                    printDoctorList(hs);
                    break;
                case 4:
                    if (hs.patients.isEmpty()) {
                        System.out.println("No patients found. Please register first!");
                        break;
                    }

                    printPatientList(hs);

                    System.out.print("Enter Patient ID: ");
                    int pid = sc.nextInt();
                    sc.nextLine();

                    Patient pat = hs.getPatientById(pid);

                    if (pat == null) {
                        System.out.println("Patient not found!");

                        System.out.print("Register new patient? (Y/N): ");
                        String cho = sc.nextLine();

                        if (cho.equalsIgnoreCase("Y")) {
                            System.out.print("Enter Name: ");
                            String nm = sc.nextLine();
                            System.out.print("Enter Age: ");
                            int ag2 = sc.nextInt();
                            sc.nextLine();

                            System.out.println("Gender: 1.Male 2.Female 3.Other");
                            int g2 = sc.nextInt();
                            sc.nextLine();
                            String gdr2 = (g2 == 1 ? "Male" : g2 == 2 ? "Female" : "Other");

                            System.out.print("Phone: ");
                            String ph2 = sc.nextLine();

                            System.out.print("Problem: ");
                            String pr2 = sc.nextLine();

                            int newId = hs.patients.size() + 1;
                            pat = new Patient(newId, nm, ag2, gdr2, ph2, pr2);
                            hs.addPatient(pat);

                            pid = newId;
                            System.out.println("Patient registered successfully!");
                        } else {
                            break;
                        }
                    }

                    printDoctorList(hs);

                    System.out.print("Select Doctor ID: ");
                    int did = sc.nextInt();
                    sc.nextLine();

                    Doctor doc = hs.getDoctorById(did);
                    if (doc == null) {
                        System.out.println("Invalid doctor ID!");
                        break;
                    }

                    String date = chooseDate(sc);

                    List<String> slots = generateTimeSlots(doc.getAvailableTime());

                    System.out.println("\nSelect Time Slot:");
                    for (int i = 0; i < slots.size(); i++) {
                        System.out.println((i + 1) + ". " + slots.get(i));
                    }

                    System.out.print("Choice: ");
                    int sch = sc.nextInt();
                    sc.nextLine();

                    if (sch < 1 || sch > slots.size()) {
                        System.out.println("Invalid slot choice!");
                        break;
                    }

                    String timeSlot = slots.get(sch - 1);

                    if (hs.isSlotTaken(did, date, timeSlot)) {
                        System.out.println("Slot already booked!");
                        break;
                    }

                    System.out.println("\n══════ BOOKING SUMMARY ══════");
                    System.out.println("Patient: " + pat.getName());
                    System.out.println("Doctor : " + doc.getName() + " (" + doc.getSpecialization() + ")");
                    System.out.println("Date   : " + date);
                    System.out.println("Slot   : " + timeSlot);
                    System.out.print("Confirm? (Y/N): ");
                    String ok = sc.nextLine();

                    if (ok.equalsIgnoreCase("Y")) {
                        int newAppId = hs.appointments.size() + 1;
                        hs.bookAppointment(new Appointment(newAppId, did, pid, date, timeSlot, "BOOKED"));
                        System.out.println("Appointment Booked Successfully!");
                    } else {
                        System.out.println("Booking Cancelled.");
                    }
                    break;

                case 5:
                    System.out.println("\n════════════ ALL APPOINTMENTS ════════════");
                    System.out.println("ID | Doctor        | Patient        | Date       | Slot          | Status");
                    System.out.println("----------------------------------------------------------------------");

                    for (Appointment a : hs.appointments) {
                        Patient p = hs.getPatientById(a.getPatientId());
                        Doctor d = hs.getDoctorById(a.getDoctorId());

                        System.out.printf("%-2d | %-13s | %-14s | %-10s | %-13s | %-8s%n",
                                a.getAppointmentId(),
                                (d != null ? d.getName() : "Unknown"),
                                (p != null ? p.getName() : "Unknown"),
                                a.getDate(),
                                a.getTimeSlot(),
                                a.getStatus());
                    }

                    System.out.println("----------------------------------------------------------------------");

                    while (true) {
                        System.out.print("\nEnter Appointment ID to cancel: ");
                        int cid = sc.nextInt();
                        sc.nextLine();

                        Appointment found = null;
                        for (Appointment a : hs.appointments) {
                            if (a.getAppointmentId() == cid) {
                                found = a;
                                break;
                            }
                        }

                        if (found != null) {
                            System.out.println("\n════════ APPOINTMENT FOUND ════════");
                            Patient p = hs.getPatientById(found.getPatientId());
                            Doctor d = hs.getDoctorById(found.getDoctorId());

                            System.out.println("Doctor : " + d.getName());
                            System.out.println("Patient: " + p.getName());
                            System.out.println("Date   : " + found.getDate());
                            System.out.println("Slot   : " + found.getTimeSlot());

                            System.out.print("Confirm cancel? (Y/N): ");
                            String cn = sc.nextLine();

                            if (cn.equalsIgnoreCase("Y")) {
                                hs.cancelAppointment(cid);
                                System.out.println("Appointment Cancelled Successfully!");
                            } else {
                                System.out.println("Cancellation Aborted.");
                            }
                            break;
                        }

                        System.out.println("\n❌ Appointment ID not found!");
                        System.out.println("1. View All Appointments");
                        System.out.println("2. Try Again");
                        System.out.println("3. Back to Menu");
                        System.out.print("Choice: ");
                        int opt = sc.nextInt();
                        sc.nextLine();

                        if (opt == 1) {
                            System.out.println("\n════════════ ALL APPOINTMENTS ════════════");
                            System.out.println(
                                    "ID | Doctor        | Patient        | Date       | Slot          | Status");
                            System.out
                                    .println("----------------------------------------------------------------------");

                            for (Appointment a : hs.appointments) {
                                Patient p2 = hs.getPatientById(a.getPatientId());
                                Doctor d2 = hs.getDoctorById(a.getDoctorId());

                                System.out.printf("%-2d | %-13s | %-14s | %-10s | %-13s | %-8s%n",
                                        a.getAppointmentId(),
                                        (d2 != null ? d2.getName() : "Unknown"),
                                        (p2 != null ? p2.getName() : "Unknown"),
                                        a.getDate(),
                                        a.getTimeSlot(),
                                        a.getStatus());
                            }
                            System.out
                                    .println("----------------------------------------------------------------------");
                        } else if (opt == 2)
                            continue;
                        else
                            break;
                    }
                    break;

                case 6:
                    printDoctorList(hs);
                    System.out.print("Enter Doctor ID: ");
                    int dd = sc.nextInt();
                    hs.showAppointmentsByDoctor(dd);
                    break;

                case 7:
                    System.out.println("Thank you for using the system!");
                    return;

                case 8:
                    // Show full patient list first
                    printPatientList(hs);

                    System.out.print("Enter Appointment ID for slip: ");
                    int sid = sc.nextInt();
                    sc.nextLine();

                    Appointment slip = null;
                    for (Appointment a : hs.appointments) {
                        if (a.getAppointmentId() == sid) {
                            slip = a;
                            break;
                        }
                    }

                    if (slip == null) {
                        System.out.println("Invalid Appointment ID!");
                        break;
                    }

                    Patient slipPat = hs.getPatientById(slip.getPatientId());
                    Doctor slipDoc = hs.getDoctorById(slip.getDoctorId());

                    System.out.println("\n🏥 CITY HOSPITAL - APPOINTMENT SLIP");
                    System.out.println("--------------------------------------------------");
                    System.out.println("Patient Details:");
                    System.out.println("Name       : " + slipPat.getName());
                    System.out.println("Age        : " + /* age not stored? add if in class */ "N/A");
                    System.out.println("Gender     : " + slipPat.getGender());
                    System.out.println("Phone      : " + slipPat.getPhone());
                    System.out.println("Problem    : " + /* problem not stored? */ "N/A");
                    System.out.println();
                    System.out.println("Doctor     : " + slipDoc.getName() + " (" + slipDoc.getSpecialization() + ")");
                    System.out.println("Date       : " + slip.getDate());
                    System.out.println("Time       : " + slip.getTimeSlot());
                    System.out.println("Room       : " + slipDoc.getRoomNo());
                    System.out.println("Status     : " + slip.getStatus());
                    System.out.println("--------------------------------------------------");
                    System.out.println("Thank you for visiting!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
