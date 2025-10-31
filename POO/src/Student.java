/**
 * Classe représentant un étudiant avec ses informations personnelles, ses critères et son historique.
 * 
 * <p>Cette classe permet de gérer les informations d'un étudiant, telles que son nom, prénom, genre, 
 * date de naissance, pays d'origine, critères spécifiques, et historique des interactions avec d'autres étudiants.</p>
 * 
 * <p>Elle fournit également des méthodes pour accéder à ces informations, vérifier la validité des critères, 
 * et effectuer diverses opérations comme l'ajout à l'historique ou l'obtention de listes spécifiques.</p>
 * 
 * <p>Les critères sont définis sous forme de clés-valeurs dans une map, où chaque clé est un objet de type {@link Critere} 
 * et la valeur est une chaîne de caractères.</p>
 * 
 * <p>Les principales fonctionnalités incluent :</p>
 * <ul>
 *   <li>Accès aux informations personnelles de l'étudiant (nom, prénom, genre, etc.).</li>
 *   <li>Calcul de l'âge en mois.</li>
 *   <li>Obtention des hobbies et des régimes alimentaires.</li>
 *   <li>Vérification des allergies et possession d'animaux.</li>
 *   <li>Validation des types de critères.</li>
 *   <li>Gestion de l'historique des interactions avec d'autres étudiants.</li>
 * </ul>
 * 
 * <p>Exemple d'utilisation :</p>
 * <pre>
 * {@code
 * Student student = new Student("Dupont", "Jean", "Homme", LocalDate.of(2000, 1, 15), Country.FRANCE, critaria);
 * int age = student.getAge();
 * ArrayList<String> hobbies = student.getHobbies();
 * boolean isAllergic = student.isAllergic();
 * }
 * </pre>
 * 
 * @author Alex Marescaux
 * @version 1.0
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Map;



public class Student implements Serializable {
    private String nom;
    private String prenom;
    private String genre;
    private LocalDate Birth;
    private Country pays;
    private Map<Critere, String> critaria;
    private ArrayList<Student> history;

    /*Constructeur des etudiants */
    public Student(String nom, String prenom, String genre, LocalDate birth ,Country pays, Map<Critere,String> critaria){
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.Birth = birth;
        this.pays = pays;
        this.critaria = critaria;
        this.history = new ArrayList<>();
    }

    /* Créer les autres constructeurs... */


    /* Getter */
    public Map<Critere, String> getCritaria() {
        return this.critaria;
    }

    public String getName(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public String getGenre(){
        return this.genre;
    }

    public LocalDate getBirthDate(){
        return this.Birth;
    }

    public Country getCountry(){
        return this.pays;
    }

    // Nouvelle méthode pour obtenir le nom du pays en String
    public String getCountryName() {
        return this.pays.getPays();
    }

    public String getPairGender() {
        return this.critaria.get(Critere.PAIR_GENDER);
    }

    public String getPreferenceHistory() {
        return critaria.get(Critere.HISTORY);
    }

    public ArrayList<Student> getHistory() {
        return history;
    }

    public void addHistory(Student student) {
        history.add(student);
    }

    //Obtenir l'age en mois
    public int getAge() {
        LocalDate today = LocalDate.now();
        Period age = Period.between(this.Birth, today);
        return age.getYears() * 12 + age.getMonths();
        
    }

    /*Obtenir une liste de tout les hobbies de l'etudiant */
    public ArrayList<String> getHobbies(){
        return getListCritere(Critere.HOBBIES);
    }

    /*Verifie si l'etudiant est allergique */
    public boolean isAllergic(){
        return "yes".equals(critaria.get(Critere.GUEST_ANIMAL_ALLERGY));
    }

    /*Verifie si l'etudiant possède un animal */
    public boolean hasAnimal(){
        return "yes".equals(critaria.get(Critere.HOST_HAS_ANIMAL));
    }
    

    /* Renvoie la liste des regimes alimentaires en tant que visiteur*/
    public ArrayList<String> getRegimeVisitor(){
        return getListCritere(Critere.GUEST_FOOD);
    }

    /* Renvoie la liste des regimes alimentaires accéptés ent tant que host A COMPLETER */
    public ArrayList<String> getRegimeHost(){
        return getListCritere(Critere.HOST_FOOD);
    }

    //Permet d'obtenir la liste de soit les regimes alimentaires en tant que hote, soit en tant que visituer, soit les hobbies
    private ArrayList<String> getListCritere(Critere critere){
        ArrayList<String> critereList = new ArrayList<String>();
        String chaine = this.critaria.get(critere);
        String h = "";
        for(int i = 0; i<chaine.length(); i++){
               if(chaine.charAt(i) == ','){
                    critereList.add(h);
                    h = "";
                }else if (i == chaine.length()-1){
                    h += chaine.charAt(i);
                    critereList.add(h);
                    h = "";
                }
                else{
                    h += chaine.charAt(i);
                }         
        }
        return critereList;
    }

    //Verifie si les critères de l'etudiant sont d'un bon type
    public boolean isCritereTypeValid(){
        for (Critere critere : critaria.keySet()) {
            String value = critaria.get(critere);
            if (critere.isBoolean()) {
                if (!value.equals("yes") && !value.equals("no")) {
                    System.out.println(this.nom + " " + this.prenom);
                    System.out.println("Critère " + critere + " a une valeur incorrecte : " + value);
                    return false; // Mauvais type pour un critère booléen
                }
            }
        }
        return true; // Tous les critères sont valides
    }

        

    /* Affichage de l'etudiant */
    @Override
    public String toString(){
        return getName() + " " + getPrenom() +" : " +  getBirthDate() + "-->" + "(" + getCountry() +")";
    }

    //Egalité entre deux étudiants
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return nom.equals(student.nom) && prenom.equals(student.prenom);
    }

}