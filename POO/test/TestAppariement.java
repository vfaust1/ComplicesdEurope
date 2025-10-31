import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TestAppariement {
    public static void main(String[] args) {
        Plateforme plateforme = new Plateforme();

        // Création des critères pour les étudiants hôtes
        Map<Critere, String> hostCriteria1 = new HashMap<>();
        hostCriteria1.put(Critere.HOBBIES, "lecture,musique");
        hostCriteria1.put(Critere.GENDER, "female");
        hostCriteria1.put(Critere.PAIR_GENDER, "male");
        hostCriteria1.put(Critere.HOST_HAS_ANIMAL, "no");
        hostCriteria1.put(Critere.HOST_FOOD, "vegetarian,nonuts");

        Map<Critere, String> hostCriteria2 = new HashMap<>();
        hostCriteria2.put(Critere.HOBBIES, "sport,cinema");
        hostCriteria2.put(Critere.GENDER, "male");
        hostCriteria2.put(Critere.PAIR_GENDER, "female");
        hostCriteria2.put(Critere.HOST_HAS_ANIMAL, "yes");
        hostCriteria2.put(Critere.HOST_FOOD, "vegetarian");

        Map<Critere, String> hostCriteria3 = new HashMap<>();
        hostCriteria3.put(Critere.HOBBIES, "jeuxvideo,technologie");
        hostCriteria3.put(Critere.GENDER, "male");
        hostCriteria3.put(Critere.PAIR_GENDER, "male");
        hostCriteria3.put(Critere.HOST_HAS_ANIMAL, "no");
        hostCriteria3.put(Critere.HOST_FOOD, "none");


        Map<Critere, String> hostCriteria4 = new HashMap<>();
        hostCriteria4.put(Critere.HOBBIES, "cuisine,lecture");
        hostCriteria4.put(Critere.GENDER, "female");
        hostCriteria4.put(Critere.PAIR_GENDER, "male");
        hostCriteria4.put(Critere.HOST_HAS_ANIMAL, "no");
        hostCriteria4.put(Critere.HOST_FOOD, "nonuts");

        // Création des hôtes
        Student host1 = new Student("A1", "Dupont", "female", LocalDate.of(2007, 6, 15), Country.FR, hostCriteria1);
        Student host2 = new Student("B1", "Martin", "male", LocalDate.of(2006, 9, 10), Country.FR, hostCriteria2);
        Student host3 = new Student("C1", "Lemoine", "male", LocalDate.of(2007, 1, 12), Country.FR, hostCriteria3);
        Student host4 = new Student("D1", "Garcia", "female", LocalDate.of(2006, 12, 1), Country.FR, hostCriteria4);

        // Création des critères pour les visiteurs
        Map<Critere, String> visitorCriteria1 = new HashMap<>();
        visitorCriteria1.put(Critere.HOBBIES, "cinema,science");
        visitorCriteria1.put(Critere.GENDER, "male");
        visitorCriteria1.put(Critere.PAIR_GENDER, "female");
        visitorCriteria1.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        visitorCriteria1.put(Critere.GUEST_FOOD, "vegetarian");

        Map<Critere, String> visitorCriteria2 = new HashMap<>();
        visitorCriteria2.put(Critere.HOBBIES, "lecture,sport");
        visitorCriteria2.put(Critere.GENDER, "female");
        visitorCriteria2.put(Critere.PAIR_GENDER, "male");
        visitorCriteria2.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        visitorCriteria2.put(Critere.GUEST_FOOD, "none");


        Map<Critere, String> visitorCriteria3 = new HashMap<>();
        visitorCriteria3.put(Critere.HOBBIES, "technologie,randonnee");
        visitorCriteria3.put(Critere.GENDER, "male");
        visitorCriteria3.put(Critere.PAIR_GENDER, "male");
        visitorCriteria3.put(Critere.GUEST_ANIMAL_ALLERGY, "yes");
        visitorCriteria3.put(Critere.GUEST_FOOD, "nonuts");

        Map<Critere, String> visitorCriteria4 = new HashMap<>();
        visitorCriteria4.put(Critere.HOBBIES, "cuisine,musique");
        visitorCriteria4.put(Critere.GENDER, "female");
        visitorCriteria4.put(Critere.PAIR_GENDER, "male");
        visitorCriteria4.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        visitorCriteria4.put(Critere.GUEST_FOOD, "vegetarian");

        // Création des visiteurs
        Student visitor1 = new Student("W1", "Lopez", "male", LocalDate.of(2007, 3, 20), Country.ES, visitorCriteria1);
        Student visitor2 = new Student("X1", "Fernandez", "female", LocalDate.of(2008, 6, 5), Country.ES, visitorCriteria2);
        Student visitor3 = new Student("Y1", "Cruz", "male", LocalDate.of(2006, 11, 10), Country.ES, visitorCriteria3);
        Student visitor4 = new Student("Z1", "Sanchez", "female", LocalDate.of(2007, 9, 7), Country.ES, visitorCriteria4);

        // Ajout des étudiants à la plateforme
        plateforme.addStudentHost(host1);
        plateforme.addStudentHost(host2);
        plateforme.addStudentHost(host3);
        plateforme.addStudentHost(host4);
        plateforme.addStudentVisitor(visitor1);
        plateforme.addStudentVisitor(visitor2);
        plateforme.addStudentVisitor(visitor3);
        plateforme.addStudentVisitor(visitor4);

        // Génération de la matrice de coût basée sur l'affinité
        int[][] costMatrix = plateforme.createCostMatrix("");


        //Affiche l'afinité entre l'hote 1 et le visiteur 1
        Pair pair = new Pair(host1, visitor1);
        pair.setAfinity("genre");
        System.out.println("Affinité entre " + host1.getName() + " et " + visitor1.getName() + ": " + pair.getAfinity());


        // **Affichage de la matrice des affinités**
        plateforme.printCostMatrix(costMatrix);        

        /* 
        // Exécution de l’algorithme hongrois
        HungarianAlgorithm hungarian = new HungarianAlgorithm(costMatrix);
        int[] assignments = hungarian.findOptimalAssignment();

        // Affichage des résultats
        System.out.println("Appariements optimaux :");
        for (int i = 0; i < assignments.length; i++) {
            if (assignments[i] != -1) {
                System.out.println("Hôte: " + plateforme.getHost().get(i).getName() + " ↔ Visiteur: " + plateforme.getVisitor().get(assignments[i]).getName());
            }
        } */
    }
}
