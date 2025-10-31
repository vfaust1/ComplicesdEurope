import java.util.ArrayList;

public class TestCSV {
    public static void main(String[] args) {
        // Création de la plateforme
        Plateforme plateforme = new Plateforme();


        // Chemin du fichier CSV (modifie selon ton système)
        String filePath = "POO/csv/JeuDeDonnée.csv"; 

        // Importation des étudiants depuis le fichier CSV
        System.out.println("Importation des étudiants depuis le fichier CSV...");
        plateforme.importStudentsFromCSV(filePath);

        // Affichage des étudiants importés
        System.out.println("Étudiants importés :");
        for (Student student : plateforme.getStudents()) {
            System.out.println("Nom: " + student.getName() + ", Prénom: " + student.getPrenom() + ", Genre: " + student.getGenre() +
                    ", Date de naissance: " + student.getBirthDate() + ", Pays: " + student.getCountry().getPays() +
                    ", Hobbies: " + student.getHobbies());
        }

        //List des pays 
        System.out.println("\nListe des pays disponibles :");
        for (String country : plateforme.getCountries()) {
            System.out.println(country);
        }

        String hostCountry = "France";
        String visitorCountry = "Germany";

        plateforme.setHostCountry(Country.valueOf(hostCountry.substring(0,2).toUpperCase()));
        plateforme.setVisitorCountry(Country.valueOf(visitorCountry.substring(0,2).toUpperCase()));

        // Affichage des étudiants hôtes et visiteurs
        System.out.println("\nÉtudiants hôtes :");
        printStudents(plateforme.getHost());
        System.out.println("\nÉtudiants visiteurs :");
        printStudents(plateforme.getVisitor());

        plateforme.sortStudents();
        // Affichage des étudiants triés
        System.out.println("\nÉtudiants hôtes :");
        printStudents(plateforme.getHost());
        System.out.println("\nÉtudiants visiteurs :");
        printStudents(plateforme.getVisitor());
        

        /*
        //Afficher les critères des étudiants importés
        System.out.println("\nCritères des étudiants importés :");
        for (Student student : plateforme.getHost()) {
            System.out.println(student.getName() + " - Critères :");
            for (Map.Entry<Critere, String> entry : student.getCritaria().entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
            }
            System.out.println("");
        }

        for (Student student : plateforme.getVisitor()) {
            System.out.println(student.getName() + " - Critères :");
            for (Map.Entry<Critere, String> entry : student.getCritaria().entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
            }
            System.out.println("");
        } */

        /*
        //Affichage de badtypes par etudiants
        System.out.println("\nBadtypes des étudiants importés :");
        for (Student student : plateforme.getHost()) {
            System.out.println(student.getName() + " - Badtypes : " + student.isCritereTypeValid());
        }
        for (Student student : plateforme.getVisitor()) {
            System.out.println(student.getName() + " - Badtypes : " + student.isCritereTypeValid());
        }


        //Suppression des adolescents à retirer
        plateforme.removeStudents();

        // Affichage des étudiants importés
        System.out.println("\nÉtudiants hôtes :");
        printStudents(plateforme.getHost());

        System.out.println("\nÉtudiants visiteurs :");
        printStudents(plateforme.getVisitor());

        
        


        

        // Création et affichage de la matrice des coûts avec critère "genre"
        System.out.println("\nCalcul de la matrice des coûts basée sur le critère 'Non'...");
        int[][] costMatrix = plateforme.createCostMatrix("");
        plateforme.printCostMatrix(costMatrix);
        
        plateforme.createCSVFromCostMatrix(costMatrix, "POO/csv/cost_matrix.csv");
        plateforme.matchStudents(costMatrix);
        plateforme.exportMatchingResults("POO/csv/matching_results.csv");*/
    }

    // Méthode pour afficher les étudiants
    private static void printStudents(ArrayList<Student> students) {
        for (Student student : students) {
            System.out.println("Nom: " + student.getName() + ", Prénom: " + student.getPrenom() + ", Genre: " + student.getGenre() +
                    ", Date de naissance: " + student.getBirthDate() + ", Pays: " + student.getCountry().getPays() +
                    ", Hobbies: " + student.getHobbies());       
        }
    }
}
