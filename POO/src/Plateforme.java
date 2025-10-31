import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Plateforme implements Serializable {
    private ArrayList<Student> students;
    private ArrayList<Student> host;
    private ArrayList<Student> visitor;
    private Country paysHost;
    private Country paysVisitor;
    private Map<Student, Student> pairs;
    private List<Student> unpairedVisitors;
    private List<Student> unpairedHosts;
    private List<Student> removeStudents;


    public Plateforme(){
        this.students = new ArrayList<Student>();
        this.host = new ArrayList<Student>();
        this.visitor = new ArrayList<Student>();
        this.pairs = new HashMap<Student, Student>();
        this.unpairedVisitors = new ArrayList<>();
        this.unpairedHosts = new ArrayList<>();
        this.removeStudents = new ArrayList<>();
        this.paysHost = null;
        this.paysVisitor = null;
    }




    public void addStudentHost(Student student){
        this.host.add(student);
    }

    public void addStudentVisitor(Student student){
        this.visitor.add(student);
    }

    public void addStudent(Student student){
        this.students.add(student);
    }


    //Get pair
    public Map<Student, Student> getPairs() {
        return pairs;
    }

    //Get unpaired visitors and hosts
    public List<Student> getUnpairedVisitors() {
        return unpairedVisitors;
    }
    public List<Student> getUnpairedHosts() {
        return unpairedHosts;
    }
    public ArrayList<Student> getHost(){
        return this.host;
    }

    public ArrayList<Student> getVisitor(){
        return this.visitor;
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }

    public List<Student> getRemoveStudents(){
        return this.removeStudents;
    }

    public void setHostCountry(Country paysHost) {
        this.paysHost = paysHost;
    }

    public void setVisitorCountry(Country paysVisitor) {
        this.paysVisitor = paysVisitor;
    }

    // Retourne le pays d'hôte
    public Country getPaysHost() {
        return paysHost;
    }
    // Retourne le pays de visiteur
    public Country getPaysVisitor() {
        return paysVisitor;
    }

    //Renvoie la liste des pays
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        System.out.println("DEBUG: Plateforme.getCountries() - this.students est null ou vide : " + (this.students == null || this.students.isEmpty()));
        if (this.students == null || this.students.isEmpty()) {
            return countries; // Retourne une liste vide si aucun étudiant n'est présent
            
        }
        System.out.println("DEBUG: Plateforme.getCountries() - Nombre d'étudiants : " + this.students.size());
        for (Student student : this.students) {
            if (!countries.contains(student.getCountry().getPays())) {
                countries.add(student.getCountry().getPays());
            }
        }
        System.out.println("DEBUG: Plateforme.getCountries() - Pays trouvés : " + countries);
        return countries;
    }

    // Re initialise les listes d'étudiants
    public void clearStudents() {
        this.host.clear();
        this.visitor.clear();
        this.removeStudents.clear();
        this.pairs.clear();
        this.unpairedVisitors.clear();
        this.unpairedHosts.clear();
        this.students = new ArrayList<Student>();
        this.paysHost = null;
        this.paysVisitor = null;  
    }

    // Importe des students depuis un fichier CSV
    public void importStudentsFromCSV(String filePath){
        // Re initialise les listes d'étudiants
        clearStudents();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // Pour ignorer la première ligne (en-tête)
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Passe la première ligne
                }
                String[] values = line.split(";");
                // Remplace les valeurs nulles ou vides par "none"
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null || values[i].trim().isEmpty()) {
                        values[i] = "none";
                    }
                }

                //Si la 11e case n'existe pas on la remplace par none
                if (values.length < 12) {
                    String[] newValues = new String[12];
                    System.arraycopy(values, 0, newValues, 0, values.length);
                    for (int i = values.length; i < 12; i++) {
                        newValues[i] = "none";
                    }
                    values = newValues;
                }


                // Assuming the CSV has columns: name, age, isAllergic, hasAnimal, critereTypeValid
                String nom = values[0];
                String prenom = values[1];
                String genre = values[9];
                LocalDate birth = LocalDate.parse(values[3]);
                // Seulement prendre les 3 premiers lettres de la valeur du pays
                String paysReduit = values[2].substring(0, 2).toUpperCase();
                // Création du pays
                Country pays = Country.valueOf(paysReduit);
                
                // Création des critères à partir de la ligne CSV
                Map<Critere, String> criteria = new HashMap<>();
                criteria.put(Critere.GUEST_ANIMAL_ALLERGY, values[4]);
                criteria.put(Critere.HOST_HAS_ANIMAL, values[5]);
                criteria.put(Critere.GUEST_FOOD, values[6]);
                criteria.put(Critere.HOST_FOOD, values[7]);
                criteria.put(Critere.HOBBIES, values[8]);
                criteria.put(Critere.GENDER, values[9]);
                criteria.put(Critere.PAIR_GENDER, values[10]);
                criteria.put(Critere.HISTORY, values[11]);

                Student student = new Student(nom, prenom, genre, birth, pays, criteria);
                this.addStudent(student);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from the CSV file: " + e.getMessage());
        }
    }

    // Ranger les étudiants dans les listes d'hôte et de visiteur
    public void sortStudents() {
        // Vider les listes avant de les remplir à nouveau
        this.host.clear();
        this.visitor.clear();
        
        for (Student student : this.students) {
            if(student.getCountry().name().equals(this.paysHost.name())){
                this.addStudentHost(student);
            } else if (student.getCountry().name().equals(this.paysVisitor.name())){
                this.addStudentVisitor(student);
            } else {
                System.out.println("L'étudiant " + student.getName() + " n'appartient ni au pays hôte ni au pays visiteur.");
            }
        }
    }

    // Verifie les adolescents a retirer
    public void removeStudents(){
        this.removeAllergicStudents(this.students);
        this.removeBadTypeStudents(this.students);
    }

    // Si l'etudiant est allergique et possède un animal
    public void removeAllergicStudents(ArrayList<Student> students){
        for (int i = 0; i < students.size(); i++){
            if (students.get(i).isAllergic() && students.get(i).hasAnimal()){
                System.out.println("L'étudiant " + students.get(i).getName() + " a été retiré car il est allergique et possède un animal.");
                this.removeStudents.add(students.get(i)); // Ajoute l'étudiant à la liste des étudiants retirés
                // Verifier si le étudiant est dans la liste des hôtes ou des visiteurs
                if (this.host.contains(students.get(i))) {
                    this.host.remove(students.get(i));
                } else if (this.visitor.contains(students.get(i))) {
                    this.visitor.remove(students.get(i));
                }
                
                students.remove(i);
                i--;
            }
        }
    }

    // Si un des critères de l'adolescent est d'un mauvais type
    public void removeBadTypeStudents(ArrayList<Student> students){
        for (int i=0; i<students.size(); i++){
            // verifie si chaque critere est d'un bon type
            if(students.get(i).isCritereTypeValid()== false){
                System.out.println("L'étudiant " + students.get(i).getName() + " a été retiré car il possède un critère de type incorrect.");
                this.removeStudents.add(students.get(i)); // Ajoute l'étudiant à la liste des étudiants retirés
                // Verifier si le étudiant est dans la liste des hôtes ou des visiteurs
                if (this.host.contains(students.get(i))) {
                    this.host.remove(students.get(i));
                } else if (this.visitor.contains(students.get(i))) {
                    this.visitor.remove(students.get(i));
                }
                students.remove(i);
                i--; // Réduire l'index pour compenser la suppression
            }
        }
    }    


    // Créé la matrice de cout entre hote et visiteur en fonction d'un critère prioritaire
    public int[][] createCostMatrix(String criterePrio){
        int[][] costMatrix = new int[this.visitor.size()][this.host.size()];
        for (int i = 0; i < this.visitor.size(); i++){
            for (int j = 0; j < this.host.size(); j++){
                Pair p = new Pair(this.host.get(j), this.visitor.get(i));
                p.setAfinity(criterePrio);
                costMatrix[i][j] = p.getAfinity();
            }
        }
        return costMatrix;
    }

    // Affiche la matrice de cout avec les prénoms des visiteurs et hôtes
    public void printCostMatrix(int[][] costMatrix){
        System.out.println("Matrice de cout :");

        // Afficher la première ligne avec les prénoms des hôtes
        System.out.print("   "); // Espace pour aligner avec la première colonne
        for (Student host : this.host) {
            System.out.print(host.getName() + " ");
        }
        System.out.println();

        // Afficher les prénoms des visiteurs dans la première colonne et les valeurs de la matrice
        for (int i = 0; i < costMatrix.length; i++){
            System.out.print(this.visitor.get(i).getName() + " "); // Prénom du visiteur
            for (int j = 0; j < costMatrix[i].length; j++){
                System.out.print(costMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Création d'un fichier CSV a partir de la matrice de cout
    // (Cette méthode n'est pas implémentée dans le code original, mais pourrait être ajoutée si nécessaire)
    public void createCSVFromCostMatrix(int[][] costMatrix, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Écrire la première ligne avec les prénoms des hôtes
            writer.write("Visitor\\Host;");
            for (Student host : this.host) {
                writer.write(host.getName() + ";");
            }
            writer.newLine();

            // Écrire les prénoms des visiteurs dans la première colonne et les valeurs de la matrice
            for (int i = 0; i < costMatrix.length; i++) {
                writer.write(this.visitor.get(i).getName() + ";"); // Prénom du visiteur
                for (int j = 0; j < costMatrix[i].length; j++) {
                    writer.write(costMatrix[i][j] + ";");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e.getMessage());
        }

        
    }

    public void matchStudents(int[][] costMatrix) {
        MatchingAlgorithm matcher = new MatchingAlgorithm(costMatrix);
        Map<Integer, Integer> pairsIndex = matcher.findOptimalMatching();
    
        // 🔹 Ajout des appariements
        for (Map.Entry<Integer, Integer> entry : pairsIndex.entrySet()) {
            Student visiteur = visitor.get(entry.getKey());
            Student hote = host.get(entry.getValue());
            pairs.put(visiteur, hote);
        }
    
        // 🔹 Identifier les étudiants non appariés
    
        for (int v = 0; v < visitor.size(); v++) {
            if (!pairs.containsKey(visitor.get(v))) {
                unpairedVisitors.add(visitor.get(v));
            }
        }
    
        for (int h = 0; h < host.size(); h++) {
            if (!pairs.containsValue(host.get(h))) {
                unpairedHosts.add(host.get(h));
            }
        }
    
        // 🔹 Afficher les étudiants non appariés
        System.out.println("\n❌ Visiteurs sans hôte :");
        for (Student v : unpairedVisitors) {
            System.out.println(v.getName());
        }
    
        System.out.println("\n❌ Hôtes sans visiteur :");
        for (Student h : unpairedHosts) {
            System.out.println(h.getName());
        }
    }
    

    public void exportMatchingResults(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 🔹 En-tête du fichier CSV
            writer.write("Host;Visitor\n");
    
            // 🔹 Écrire chaque paire
            for (Map.Entry<Student, Student> entry : pairs.entrySet()) {
                writer.write(entry.getKey().getName() + ";" + entry.getValue().getName() + "\n");
            }
    
            // 🔹 Ajout d'une ligne vide pour séparer les non-appariés
            writer.newLine();
            writer.write("Unpaired Students\n");
    
            // 🔹 Ajouter les étudiants visiteur non appariés
            for (Student v : unpairedVisitors) {
                writer.write("Visitor: " + v.getName() + "\n");
            }
            // 🔹 Ajouter les étudiants hôte non apparié
            for (Student h : unpairedHosts) {
                writer.write("Host: " + h.getName() + "\n");
            }
    
            System.out.println("✅ Résultats d'appariement exportés avec les non-appariés !");
            System.out.println("Mise a jout de l'historique des apparaiement.");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'exportation : " + e.getMessage());
        }

    }

    // Met a jour l'historique de chaque etudiant
    public void updateStudentHistory() {
        for (Map.Entry<Student, Student> entry : pairs.entrySet()) {
            Student host = entry.getKey();
            Student visitor = entry.getValue();
            host.addHistory(visitor);
            visitor.addHistory(host);
        }
    }

    //Modifier pair
    public void modifyPair(Student host, Student visitor) {
        if (pairs.containsKey(visitor)) {
            pairs.put(visitor, host);
        } else {
            pairs.put(host, visitor);
        }
    }


    // Méthode pour sauvegarder l'objet Plateforme dans un fichier
    public void save(int année) {
        try {
            // Création automatique du dossier historique si besoin
            new File("POO/historique").mkdirs();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("POO/historique/"+ getPaysHost().getPays()  + "_" + getPaysVisitor().getPays()+"_"+ année + ".ser"));
            updateStudentHistory();
            oos.writeObject(this);
            oos.close();
            System.out.println("✅ Plateforme sauvegardée avec succès dans le fichier : " + oos.toString());
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la sauvegarde de la Plateforme : " + e.getMessage());
        }
    }

    // Méthode pour charger un objet Plateforme depuis un fichier
    public static Plateforme load(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Plateforme plateforme = (Plateforme) ois.readObject();
            System.out.println("✅ Plateforme chargée avec succès depuis le fichier : " + filePath);
            return plateforme;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Erreur lors du chargement de la Plateforme : " + e.getMessage());
            return null;
        }
    }

    public void saveToFile(String filename) throws IOException {
        // Création automatique du dossier historique si besoin
        new File("POO/historique").mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public void exportToCSV(String filename) throws IOException {
        // Création automatique du dossier historique si besoin
        new File("POO/historique").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Écrire l'en-tête
            writer.write("Hôte,Visiteur,Score");
            writer.newLine();
            for (Map.Entry<Student, Student> entry : pairs.entrySet()) {
                Student hote = entry.getKey();
                Student visiteur = entry.getValue();
                double score = 0;
                try {
                    score = calculateScore(hote, visiteur);
                } catch (Exception e) {
                    // ignore
                }
                writer.write(hote.getName() + " " + hote.getPrenom() + "," +
                             visiteur.getName() + " " + visiteur.getPrenom() + "," +
                             String.format("%.2f", score));
                writer.newLine();
            }
        }
    }

    public double calculateScore(Student hote, Student visiteur) {
        double score = 0.0;
        
        // Critères de base
        if (hote.getGenre().equals(visiteur.getGenre())) {
            score += 1.0;
        }
        
        // Préférences alimentaires
        ArrayList<String> regimesHote = hote.getRegimeHost();
        ArrayList<String> regimesVisiteur = visiteur.getRegimeVisitor();
        for (String regime : regimesHote) {
            if (regimesVisiteur.contains(regime)) {
                score += 2.0;
                break;
            }
        }
        
        // Hobbies
        List<String> hobbiesCommuns = new ArrayList<>(hote.getHobbies());
        hobbiesCommuns.retainAll(visiteur.getHobbies());
        score += hobbiesCommuns.size() * 0.5;
        
        // Allergies et animaux
        if (hote.isAllergic() && visiteur.hasAnimal()) {
            score -= 3.0;
        }
        if (visiteur.isAllergic() && hote.hasAnimal()) {
            score -= 3.0;
        }
        
        return score;
    }
    
    
    
    

}