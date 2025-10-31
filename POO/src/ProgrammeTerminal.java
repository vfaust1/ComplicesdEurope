import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * La classe ProgrammeTerminal représente une application console interactive
 * permettant de gérer une plateforme d'appariement entre étudiants hôtes et visiteurs.
 * Elle offre diverses fonctionnalités telles que l'importation d'étudiants, 
 * la configuration des pays, l'affichage des étudiants, l'appariement manuel ou automatique,
 * et la sauvegarde ou le chargement de la plateforme.
 * 
 * Fonctionnalités principales :
 * - Importer des étudiants depuis un fichier CSV.
 * - Configurer les pays hôte et visiteur.
 * - Afficher les étudiants, hôtes et visiteurs.
 * - Filtrer les étudiants selon des critères spécifiques.
 * - Effectuer des appariements manuels ou automatiques entre hôtes et visiteurs.
 * - Exporter les résultats d'appariement dans un fichier CSV.
 * - Sauvegarder et charger l'état de la plateforme.
 * 
 * Méthodes principales :
 * - `run()`: Point d'entrée principal de l'application. Gère le menu principal et les interactions utilisateur.
 * - `importStudents()`: Permet d'importer des étudiants depuis un fichier CSV.
 * - `configurerPays()`: Configure les pays hôte et visiteur pour l'appariement.
 * - `afficherEtudiant()`: Affiche la liste des étudiants importés.
 * - `afficherHote()`: Affiche la liste des étudiants hôtes.
 * - `afficherVisiteur()`: Affiche la liste des étudiants visiteurs.
 * - `filtrerEtudiants()`: Filtre les étudiants selon des critères spécifiques.
 * - `effectuerAppariementManuel()`: Permet de créer des appariements manuels entre hôtes et visiteurs.
 * - `effectuerAppariementAutomatique()`: Effectue des appariements automatiques basés sur des critères.
 * - `afficherAppariements()`: Affiche les appariements actuels entre hôtes et visiteurs.
 * - `exporterEtudiants()`: Exporte les résultats d'appariement dans un fichier CSV.
 * - `sauvegarderPlateforme()`: Sauvegarde l'état actuel de la plateforme dans un fichier.
 * - `chargerPlateforme()`: Charge une plateforme sauvegardée depuis un fichier.
 * 
 * Utilisation :
 * - L'utilisateur interagit avec le programme via un menu console.
 * - Les données sont manipulées via la classe `Plateforme` et ses méthodes associées.
 * 
 * Remarques :
 * - Les entrées utilisateur sont validées pour éviter les erreurs.
 * - Les fichiers de sauvegarde et d'export doivent être spécifiés avec des chemins valides.
 * - Certaines fonctionnalités nécessitent des étapes préalables (ex. configuration des pays avant l'appariement).
 */
public class ProgrammeTerminal {
    private Plateforme plateforme = new Plateforme();
    private Scanner scanner = new Scanner(System.in);
    private boolean filtrage = false;
    private boolean configpays = false;

    public static void main(String[] args) {
        ProgrammeTerminal programme = new ProgrammeTerminal();
        programme.run();
    }

    public void run() {
        afficherPageBienvenue();
        while (true) {
            afficherMenuPrincipal();
            int choix = lireEntreeEntier("Votre choix : ");
            switch (choix) {
                case 1 -> importStudents();
                case 2 -> configurerPays();
                case 3 -> afficherEtudiant();
                case 4 -> afficherHote();
                case 5 -> afficherVisiteur();
                case 6 -> filtrerEtudiants();
                case 7 -> effectuerAppariementAutomatique();
                case 8 -> effectuerAppariementManuel();
                case 9 -> afficherAppariements();
                case 10 -> exporterEtudiants();
                case 11 -> sauvegarderPlateforme();
                case 12 -> chargerPlateforme();
                case 0 -> {
                    System.out.println("Merci d'avoir utilisé le programme. Au revoir !");
                    return;
                }
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void afficherPageBienvenue() {
        System.out.println("===================================");
        System.out.println("   Bienvenue dans le programme     ");
        System.out.println("===================================");
    }

    private void afficherMenuPrincipal() {
        System.out.println("\nMenu Principal :");
        System.out.println("1. Importer les élèves");
        System.out.println("2. Configurer les pays hôte et visiteur");
        //Afficher les etudiants
        System.out.println("3. Afficher les étudiants");
        System.out.println("4. Afficher Hote");
        System.out.println("5. Afficher Visiteur");
        System.out.println("6. Filtrer les étudiants");
        System.out.println("7. Effectuer un appariement automatique");
        System.out.println("8. Effectuer un appariement manuel");
        System.out.println("9. Afficher les appariements");
        System.out.println("10. Exporter les étudiants");
        System.out.println("11. Sauvegarder la plateforme");
        System.out.println("12. Charger une plateforme");
        System.out.println("0. Quitter");
    }


    //Importer les étudiants
    private void importStudents() {
        System.out.println("Vous êtes actuellement dans le dossier : " + System.getProperty("user.dir"));
        System.out.print("Entrez le chemin du fichier d'import (CSV) : ");
        String chemin = scanner.nextLine();
        chemin = chemin.trim();
        plateforme.importStudentsFromCSV(chemin);
        System.out.println("Étudiants importés avec succès depuis : " + chemin);
        //Appuyer sur Entrée pour continuer
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void configurerPays() {
        List<String> paysDisponibles = plateforme.getCountries();
        if (paysDisponibles.isEmpty()) {
            System.out.println("Aucun pays disponible. Veuillez importer des étudiants d'abord.");
            return;
        }

        System.out.println("Pays disponibles : " + String.join(", ", paysDisponibles));

        for (int i = 0; i < paysDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + paysDisponibles.get(i));
        }

        System.out.print("Entrez le numéro correspondant au pays hôte : ");
        int choixHote = lireEntreeEntier("Votre choix : ");
        while (choixHote < 1 || choixHote > paysDisponibles.size()) {
            System.out.print("Choix invalide. Veuillez réessayer : ");
            choixHote = lireEntreeEntier("Votre choix : ");
        }
        String paysHote = paysDisponibles.get(choixHote - 1);

        System.out.print("Entrez le numéro correspondant au pays visiteur : ");
        int choixVisiteur = lireEntreeEntier("Votre choix : ");
        while (choixVisiteur < 1 || choixVisiteur > paysDisponibles.size() || choixVisiteur == choixHote) {
            System.out.print("Choix invalide ou identique au pays hôte. Veuillez réessayer : ");
            choixVisiteur = lireEntreeEntier("Votre choix : ");
        }
        String paysVisiteur = paysDisponibles.get(choixVisiteur - 1);

        try {
            plateforme.setHostCountry(Country.valueOf(paysHote.toUpperCase().substring(0, 2)));
            plateforme.setVisitorCountry(Country.valueOf(paysVisiteur.toUpperCase().substring(0, 2)));
            plateforme.sortStudents();
            System.out.println("Configuration des pays validée !");
            configpays = true;
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : Les pays sélectionnés ne sont pas valides.");
        }
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void afficherEtudiant() {
        List<Student> etudiants = plateforme.getStudents();
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant à afficher.");
        } else {
            System.out.println("Liste des étudiants :");
            for (Student etudiant : etudiants) {
                // Affichage detaillé
                System.out.println("- " + etudiant.getName() + " (Âge: " + etudiant.getAge() + ", Genre: " + etudiant.getGenre() + ", Hobbies: " + String.join(", ", etudiant.getHobbies()) + ")");
            }
        }
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
        System.out.println("Affichage des étudiants terminé.");
    }

    private void afficherHote() {
        List<Student> hotes = plateforme.getHost();
        if (hotes.isEmpty()) {
            System.out.println("Aucun hôte à afficher.");
        } else {
            System.out.println("Liste des hôtes :");
            for (Student hote : hotes) {
                // Affichage detaillé
                System.out.println("- " + hote.getName() + " (Âge: " + hote.getAge() + ", Genre: " + hote.getGenre() + ", Hobbies: " + String.join(", ", hote.getHobbies()) + ")");
            }
        }
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
        System.out.println("Affichage des hôtes terminé.");
    }

    private void afficherVisiteur() {
        List<Student> visiteurs = plateforme.getVisitor();
        if (visiteurs.isEmpty()) {
            System.out.println("Aucun visiteur à afficher.");
        } else {
            System.out.println("Liste des visiteurs :");
            for (Student visiteur : visiteurs) {
                // Affichage detaillé
                System.out.println("- " + visiteur.getName() + " (Âge: " + visiteur.getAge() + ", Genre: " + visiteur.getGenre() + ", Hobbies: " + String.join(", ", visiteur.getHobbies()) + ")");
            }
        }
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
        System.out.println("Affichage des visiteurs terminé.");
    }

    private void filtrerEtudiants() {
        plateforme.removeStudents();
        List<Student> etudiantsSupprimes = plateforme.getRemoveStudents();
        System.out.println("Étudiants supprimés :");
        for (Student etudiant : etudiantsSupprimes) {
            System.out.println("- " + etudiant.getName());
        }
        System.out.println("Total supprimés : " + etudiantsSupprimes.size());
        filtrage = true;
        


        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void effectuerAppariementManuel() {
        if (!configpays) {
            System.out.println("Veuillez configurer les pays hôte et visiteur avant de procéder à l'appariement.");
            return;
        }
        if (!filtrage) {
            System.out.println("Veuillez filtrer les étudiants avant de procéder à l'appariement.");
            return;
        }

        System.out.println("Appariement manuel en cours...");
        List<Student> hotes = plateforme.getHost();
        List<Student> visiteurs = plateforme.getVisitor();

        if (hotes.isEmpty() || visiteurs.isEmpty()) {
            System.out.println("Aucun hôte ou visiteur disponible pour l'appariement.");
            return;
        }

        System.out.println("Sélectionnez un étudiant hôte :");
        for (int i = 0; i < hotes.size(); i++) {
            System.out.println((i + 1) + ". " + hotes.get(i).getName());
        }
        int choixHote = lireEntreeEntier("Votre choix : ");
        while (choixHote < 1 || choixHote > hotes.size()) {
            System.out.println("Choix invalide. Veuillez réessayer.");
            choixHote = lireEntreeEntier("Votre choix : ");
        }
        Student hote = hotes.get(choixHote - 1);

        System.out.println("Sélectionnez un étudiant visiteur :");
        for (int i = 0; i < visiteurs.size(); i++) {
            System.out.println((i + 1) + ". " + visiteurs.get(i).getName());
        }
        int choixVisiteur = lireEntreeEntier("Votre choix : ");
        while (choixVisiteur < 1 || choixVisiteur > visiteurs.size()) {
            System.out.println("Choix invalide. Veuillez réessayer.");
            choixVisiteur = lireEntreeEntier("Votre choix : ");
        }
        Student visiteur = visiteurs.get(choixVisiteur - 1);

        if (hote.equals(visiteur)) {
            System.out.println("L'étudiant hôte et l'étudiant visiteur ne peuvent pas être les mêmes.");
            return;
        }

        Pair pair = new Pair(hote, visiteur);
        int affinite = pair.getAfinity();
        if (affinite == -1) {
            System.out.println("Les étudiants sélectionnés ne sont pas compatibles.");
            System.out.print("Voulez-vous quand même confirmer l'appariement ? (oui/non) : ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (!confirmation.equals("oui")) {
                System.out.println("Appariement annulé.");
                return;
            }
        }

        // Ajouter l'appariement à la plateforme
        plateforme.getPairs().put(hote, visiteur);
        System.out.println("Appariement manuel effectué avec succès !");
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void effectuerAppariementAutomatique() {
        if (!configpays) {
            System.out.println("Veuillez configurer les pays hôte et visiteur avant de procéder à l'appariement.");
            return;
        }
        if (!filtrage) {
            System.out.println("Veuillez filtrer les étudiants avant de procéder à l'appariement.");
            return;
        }
        System.out.println("Choisissez le critère d'appariement :");
        System.out.println("1. Âge");
        System.out.println("2. Hobbies");
        System.out.println("3. Genre");
        System.out.println("4. Sans Critère");
        int choixCritere = lireEntreeEntier("Votre choix : ");
        String critere;

        switch (choixCritere) {
            case 1 -> critere = "age";
            case 2 -> critere = "hobbies";
            case 3 -> critere = "genre";
            case 4 -> critere = "Sans Critère";
            default -> {
            System.out.println("Choix invalide. Critère par défaut 'Sans Critère' sélectionné.");
            critere = "Sans Critère";
            }
        }

        int[][] matriceCouts = plateforme.createCostMatrix(critere);
        plateforme.matchStudents(matriceCouts);
        System.out.println("Appariements effectués automatiquement !");
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void afficherAppariements() {
        Map<Student, Student> appariements = plateforme.getPairs();
        if (appariements.isEmpty()) {
            System.out.println("Aucun appariement enregistré.");
        } else {
            System.out.println("Appariements actuels :");
            for (Map.Entry<Student, Student> entry : appariements.entrySet()) {
                // Affichage détaillé sur la meme ligne
                Student hote = entry.getKey();
                Student visiteur = entry.getValue();
                System.out.println("- Hôte: " + hote.getName() + " (Âge: " + hote.getAge() + ", Genre: " + hote.getGenre() + ") <-> Visiteur: " + visiteur.getName() + " (Âge: " + visiteur.getAge() + ", Genre: " + visiteur.getGenre() + ")");

            }
        }
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void exporterEtudiants() {
        // Vérifier si des étudiants ont été importés et si un appariement a été effectué
        if (plateforme.getStudents().isEmpty() || plateforme.getPairs().isEmpty()) {
            System.out.println("Aucun étudiant ou appariement à exporter. Veuillez importer des étudiants et effectuer un appariement d'abord.");
            return;
        }
        // Afficher le repertoire courant
        System.out.println("Vous êtes actuellement dans le dossier : " + System.getProperty("user.dir"));
        System.out.print("Entrez le chemin du fichier d'export (CSV) : ");
        String chemin = scanner.nextLine();
        plateforme.exportMatchingResults(chemin);
        System.out.println("Résultats exportés dans : " + chemin);
    }

    private void sauvegarderPlateforme() {
        // Vérifier si des étudiants ont été importés et si un appariement a été effectué
        if (plateforme.getStudents().isEmpty() || plateforme.getPairs().isEmpty()) {
            System.out.println("Aucun étudiant ou appariement à sauvegarder. Veuillez importer des étudiants et effectuer un appariement d'abord.");
            return;
        }
        System.out.print("Entrez l'année de sauvegarde (ex: 2023) : ");
        String annee =scanner.nextLine();
        if (annee.isEmpty()) {
            System.out.println("Année invalide. La sauvegarde n'a pas été effectuée.");
            return;
        }
        // Verifier le format de l'année
        if (!annee.matches("\\d{4}")) {
            System.out.println("Format d'année invalide. Veuillez entrer une année valide (ex: 2023).");
            return;
        }

        // Verifier les fichiers dans le dossier
        String chemin = "../../POO/historique/" + plateforme.getPaysHost().getPays() + "_" + plateforme.getPaysVisitor().getPays() + "_" + annee+ ".ser";
        String inverse = "../../POO/historique/" + plateforme.getPaysVisitor().getPays() + "_" + plateforme.getPaysHost().getPays() + "_" + annee + ".ser";
        File file = new File(chemin);
        File fileInverse = new File(inverse);
        if (file.exists() || fileInverse.exists()) {
            System.out.println("Un fichier de sauvegarde existe déjà pour cette année et ces pays. Veuillez choisir un autre nom ou année.");
            return; // Ne pas écraser le fichier existant
        }
        
        // Transfomer string en int
        int anneeInt = Integer.parseInt(annee);

        plateforme.save(anneeInt);
        System.out.println("Plateforme sauvegardée dans POO/historique : ");
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void chargerPlateforme() {
        // Afficher tout les fichiers dans POO/historique
        System.out.println("Fichiers de sauvegarde disponibles :");
        String dossierSauvegarde = "../../POO/historique";
        java.io.File dossier = new java.io.File(dossierSauvegarde);
        String[] fichiers = dossier.list();
        if (fichiers != null && fichiers.length > 0) {
            for (String fichier : fichiers) {
                System.out.println("- " + fichier);
            }
        } else {
            System.out.println("Aucun fichier de sauvegarde trouvé.");
            return;
        }
        System.out.print("Entrez le chemin du fichier de sauvegarde : ");
        String chemin = scanner.nextLine();
        Plateforme chargee = Plateforme.load("../../POO/historique/" + chemin);
        if (chargee != null) {
            this.plateforme = chargee;
            System.out.println("Plateforme chargée avec succès.");
        }
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
        
    }

    private int lireEntreeEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        int valeur = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne
        return valeur;
    }
}