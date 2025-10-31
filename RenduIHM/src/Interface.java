import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Interface extends Application {
    private Plateforme plateforme = new Plateforme();
    private TextArea outputArea = new TextArea();
    private ComboBox<String> critereComboBox = new ComboBox<>();
    private TextField yearInput = new TextField();
    private ComboBox<String> hostCountryComboBox = new ComboBox<>();
    private ComboBox<String> visitorCountryComboBox = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource("/RenduIHM/WelcomeScreen.fxml");
            if (fxmlUrl == null) {
                System.out.println("Impossible de trouver WelcomeScreen.fxml. Vérifiez le chemin et le classpath.");
                System.exit(1);
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            WelcomeScreenController controller = loader.getController();
            controller.setPlateforme(plateforme);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Bienvenue - Appariement des Étudiants");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----- Configuration & Importation -----
    private VBox getConfigLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label configLabel = new Label("Configuration des pays");
        hostCountryComboBox.setPromptText("Pays Hôte");
        visitorCountryComboBox.setPromptText("Pays Visiteur");
        yearInput.setPromptText("Entrez une année");

        HBox countrySelection = new HBox(10, hostCountryComboBox, visitorCountryComboBox, yearInput);
        Button validButton = new Button("Valider Configuration");
        validButton.setOnAction(e -> validCountry());

        Button importButton = new Button("Importer CSV");
        importButton.setOnAction(e -> importStudents(new Stage()));

        layout.getChildren().addAll(configLabel, countrySelection, validButton, importButton, outputArea);
        return layout;
    }

    // ----- Affichage des Étudiants -----
    private VBox getDisplayLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label displayLabel = new Label("Affichage des étudiants");
        Button showStudentsButton = new Button("Afficher tous les Étudiants");
        Button showHostButton = new Button("Afficher les Hôtes");
        Button showVisitorButton = new Button("Afficher les Visiteurs");

        showStudentsButton.setOnAction(e -> showStudent());
        showHostButton.setOnAction(e -> showHost());
        showVisitorButton.setOnAction(e -> showVisitor());

        HBox displayOptions = new HBox(10, showStudentsButton, showHostButton, showVisitorButton);
        layout.getChildren().addAll(displayLabel, displayOptions, outputArea);
        return layout;
    }

    // ----- Gestion des Appariements -----
    private VBox getMatchingLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label matchingLabel = new Label("Gestion des appariements");
        Button matchStudentsButton = new Button("Effectuer Correspondance Automatique");
        matchStudentsButton.setOnAction(e -> matchStudents());

        Button createPairButton = new Button("Créer un Appariement Manuellement");
        createPairButton.setOnAction(e -> showPairCreationWindow());

        Button modifyPairButton = new Button("Modifier un Appariement");
        modifyPairButton.setOnAction(e -> modifyPair());

        Button showMatchingButton = new Button("Afficher Appariements actuels");
        showMatchingButton.setOnAction(e -> showMatching());

        HBox matchingOptions = new HBox(10, matchStudentsButton, createPairButton, modifyPairButton, showMatchingButton);
        layout.getChildren().addAll(matchingLabel, matchingOptions, outputArea);
        return layout;
    }

    // ----- Historique & Exportation -----
    private VBox getHistoryLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label historyLabel = new Label("Gestion de l'historique et exportation");
        Button loadHistoryButton = new Button("Charger Historique");
        loadHistoryButton.setOnAction(e -> loadMatchingHistory());

        Button exportButton = new Button("Exporter Résultats");
        exportButton.setOnAction(e -> exportResults(new Stage()));

        Button showFullHistoryButton = new Button("Voir tout l'historique");
        showFullHistoryButton.setOnAction(e -> showHistoryWindow());

        HBox historyOptions = new HBox(10, loadHistoryButton, exportButton, showFullHistoryButton);
        layout.getChildren().addAll(historyLabel, historyOptions, outputArea);
        return layout;
    }

    // ----- Réinitialisation & Fermeture -----
    private VBox getResetLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label resetLabel = new Label("Réinitialisation de la plateforme");
        Button resetButton = new Button("Réinitialiser la Plateforme");
        resetButton.setOnAction(e -> {
            plateforme = new Plateforme();
            outputArea.setText("Plateforme réinitialisée.");
        });

        Button closeButton = new Button("Enregistrer et Fermer");
        closeButton.setOnAction(e -> close());

        layout.getChildren().addAll(resetLabel, resetButton, closeButton, outputArea);
        return layout;
    }





    private void close() {
        // Enregistrer les appariements avant de fermer
        save();
        System.exit(0);
    }

    private void save(){

        
        // Verifier les fichiers dans le dossier
        String chemin = "POO/historique/" + plateforme.getPaysHost().getPays() + "_" + plateforme.getPaysVisitor().getPays() + "_" + yearInput.getText().trim() + ".ser";
        String inverse = "POO/historique/" + plateforme.getPaysVisitor().getPays() + "_" + plateforme.getPaysHost().getPays() + "_" + yearInput.getText().trim() + ".ser";
        File file = new File(chemin);
        File fileInverse = new File(inverse);
        if (file.exists() || fileInverse.exists()) {
            updateOutput("Il existe deja un appariement pour cette année et ces pays.");
            return; // Ne pas écraser le fichier existant
        }else{
            try {
                int year = Integer.parseInt(yearInput.getText().trim());
                plateforme.save(year);
                updateOutput("Appariements enregistrés pour l'année : " + year);
            } catch (NumberFormatException e) {
                updateOutput("Veuillez entrer une année valide.");
            }

        }    

        
    }

    private void showPairCreationWindow() {
        Stage pairStage = new Stage();
        pairStage.setTitle("Création d'un Appariement");
    
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));
    
        Label hostLabel = new Label("Sélectionnez un étudiant hôte :");
        ComboBox<Student> hostComboBox = new ComboBox<>();
        hostComboBox.getItems().addAll(plateforme.getHost()); // Ajouter les étudiants hôtes
    
        Label visitorLabel = new Label("Sélectionnez un étudiant visiteur :");
        ComboBox<Student> visitorComboBox = new ComboBox<>();
        visitorComboBox.getItems().addAll(plateforme.getVisitor()); // Ajouter les étudiants visiteurs
    
        Button validateButton = new Button("Valider l'Appariement");
    
        validateButton.setOnAction(e -> {
            Student host = hostComboBox.getValue();
            Student visitor = visitorComboBox.getValue();
    
            if (host != null && visitor != null) {
                createManualPair(host, visitor);
                pairStage.close(); // Fermer la fenêtre après validation
            } else {
                System.out.println("Veuillez sélectionner deux étudiants.");
            }
        });
    
        layout.getChildren().addAll(hostLabel, hostComboBox, visitorLabel, visitorComboBox, validateButton);
        Scene scene = new Scene(layout, 400, 250);
        pairStage.setScene(scene);
        pairStage.show();
    }

    private void createManualPair(Student host, Student visitor) {
        // Ajouter la paire à la plateforme
        plateforme.getPairs().put(host, visitor);
    
        // Supprimer les étudiants des listes correspondantes
        plateforme.getHost().remove(host);
        plateforme.getVisitor().remove(visitor);
    
        updateOutput("Nouvel appariement ajouté : " + host.getName() + " ↔ " + visitor.getName());
    }
    
    

    private void showHistoryWindow() {
        Stage historyStage = new Stage();
        historyStage.setTitle("Historique des Appariements");
    
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));
    
        Label selectLabel = new Label("Sélectionnez un appariement :");
        ComboBox<String> historyComboBox = new ComboBox<>();
        TextArea historyOutput = new TextArea();
        historyOutput.setEditable(false);
        historyOutput.setPrefHeight(200);
    
        Button loadButton = new Button("Charger l'Historique");
        
        // Charger les fichiers disponibles dans le ComboBox
        File historiqueDir = new File("POO/historique");
        if (historiqueDir.exists() && historiqueDir.isDirectory()) {
            File[] files = historiqueDir.listFiles((dir, name) -> name.endsWith(".ser"));
            if (files != null) {
                for (File file : files) {
                    historyComboBox.getItems().add(file.getName());
                }
            }
        }
    
        // Action du bouton pour charger un historique sélectionné
        loadButton.setOnAction(e -> {
            String selectedFile = historyComboBox.getValue();
            if (selectedFile != null) {
                loadSelectedHistory(selectedFile, historyOutput);
            } else {
                historyOutput.setText("Veuillez sélectionner un fichier d'historique.");
            }
        });
    
        layout.getChildren().addAll(selectLabel, historyComboBox, loadButton, historyOutput);
        Scene scene = new Scene(layout, 400, 300);
        historyStage.setScene(scene);
        historyStage.show();
    }

    private void loadSelectedHistory(String fileName, TextArea outputArea) {
        File selectedFile = new File("POO/historique/" + fileName);

        if (!selectedFile.exists()) {
            outputArea.setText("Fichier non trouvé : " + fileName);
            return;
        }


        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
            Plateforme historiquePlateforme = (Plateforme) ois.readObject();

            StringBuilder historyText = new StringBuilder("Historique de l'appariement :\n");
            for (Map.Entry<Student, Student> entry : historiquePlateforme.getPairs().entrySet()) {
                historyText.append(entry.getKey().getName()).append(" ↔ ")
                            .append(entry.getValue().getName()).append("\n");
            }
            outputArea.setText(historyText.toString());
        } catch (IOException | ClassNotFoundException e) {
            outputArea.setText("Erreur lors du chargement du fichier.");
        }
    }

    
    
    


    private void validCountry() {
        String hostCountry = hostCountryComboBox.getValue();
        String visitorCountry = visitorCountryComboBox.getValue();
    
        if (hostCountry != null && visitorCountry != null) {
            try {
                // Utilise directement les valeurs sélectionnées dans les ComboBox
                plateforme.setHostCountry(Country.valueOf(hostCountry.substring(0,2).toUpperCase()));
                plateforme.setVisitorCountry(Country.valueOf(visitorCountry.substring(0,2).toUpperCase()));
                plateforme.sortStudents();
                outputArea.setText("Configuration des pays validée : Hôte - " + hostCountry + ", Visiteur - " + visitorCountry);
            } catch (IllegalArgumentException e) {
                outputArea.setText("Erreur : Les pays sélectionnés ne sont pas valides.");
            }
        } else {
            outputArea.setText("Veuillez sélectionner un pays hôte et un pays visiteur.");
        }
    }

    private void showHost() {
        StringBuilder hostsOutput = new StringBuilder("Liste des Hôtes :\n");
        if (plateforme.getHost().isEmpty()) {
            hostsOutput.append("Aucun hôte enregistré.\n");
            outputArea.setText(hostsOutput.toString());
            return;
        }else{
            for (Student student : plateforme.getHost()) {
                hostsOutput.append(student.getName()).append(" - ").append(student.getCountry()).append("\n");
            }
            outputArea.setText(hostsOutput.toString());
        }
    
    }

    private void showVisitor() {
        StringBuilder visitorsOutput = new StringBuilder("Liste des Visiteurs :\n");
        if (plateforme.getVisitor().isEmpty()) {
            visitorsOutput.append("Aucun visiteur enregistré.\n");
            outputArea.setText(visitorsOutput.toString());
            return;
        }else{
            for (Student student : plateforme.getVisitor()) {
                visitorsOutput.append(student.getName()).append(" - ").append(student.getCountry()).append("\n");
            }
            outputArea.setText(visitorsOutput.toString());            
        }
    }

    private void showStudent() {
        StringBuilder studentsOutput = new StringBuilder("Liste des Étudiants :\n");
        if (plateforme.getStudents().isEmpty()) {
            studentsOutput.append("Aucun étudiant enregistré.\n");
            outputArea.setText(studentsOutput.toString());
            return;
        }
        for (Student student : plateforme.getStudents()) {
            studentsOutput.append(student.getName()).append(" - ").append(student.getCountry()).append("\n");
        }
        outputArea.setText(studentsOutput.toString());
    }


    private void showMatching() {
        StringBuilder matchesOutput = new StringBuilder("Appariements Actuels :\n");
        for (Map.Entry<Student, Student> entry : plateforme.getPairs().entrySet()) {
            matchesOutput.append(entry.getKey().getName()).append(" ↔ ").append(entry.getValue().getName()).append("\n");
        }
        outputArea.setText(matchesOutput.toString());
    }

    private void removeStudents() {
        plateforme.removeStudents();
        StringBuilder removedStudentsOutput = new StringBuilder("Étudiants supprimés :\n");
        for (Student student : plateforme.getRemoveStudents()) {
            removedStudentsOutput.append(student.getName()).append("\n");
        }
        removedStudentsOutput.append("\nTotal supprimés : ").append(plateforme.getRemoveStudents().size());
        outputArea.setText(removedStudentsOutput.toString());
    }

    private void importStudents(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            plateforme.importStudentsFromCSV(file.getAbsolutePath());

            // Reinitialiser et remplir les ComboBox de pays
            hostCountryComboBox.getItems().clear();
            visitorCountryComboBox.getItems().clear();
            hostCountryComboBox.getItems().addAll(plateforme.getCountries());
            visitorCountryComboBox.getItems().addAll(plateforme.getCountries());

            updateOutput("Étudiants importés avec succès depuis : " + file.getName());
        }
    }

    

    private void calculateCostMatrix(String selectedCriteria) {
        if (plateforme.getHost().isEmpty() || plateforme.getVisitor().isEmpty()) {
            updateOutput("Veuillez importer des étudiants hôtes et visiteurs avant de calculer la matrice des coûts.");
            return;
        }

        int[][] costMatrix = plateforme.createCostMatrix(selectedCriteria);
    
        // 🔹 Générer l'affichage de la matrice
        StringBuilder matrixOutput = new StringBuilder("Matrice des coûts (" + selectedCriteria + "):\n");
        for (int[] row : costMatrix) {
            for (int value : row) {
                matrixOutput.append(value).append("\t");
            }
            matrixOutput.append("\n");
        }
    
        // 🔹 Mettre à jour l'affichage
        outputArea.setText(matrixOutput.toString());
        updateOutput("Matrice des coûts calculée avec le critère : " + selectedCriteria);
    }   

    private void matchStudents() {
        String selectedCriteria = critereComboBox.getValue();
        int[][] costMatrix = plateforme.createCostMatrix(selectedCriteria);
        plateforme.matchStudents(costMatrix);
        saveMatchingHistory();

        // 🔹 Construire l'affichage des appariements
        StringBuilder matchesOutput = new StringBuilder("Appariements :\n");
        for (Map.Entry<Student, Student> entry : plateforme.getPairs().entrySet()) {
            matchesOutput.append(entry.getKey().getName()).append(" ↔ ").append(entry.getValue().getName()).append("\n");
        }

        // 🔹 Afficher les étudiants non appariés
        matchesOutput.append("\n❌ Visiteurs sans hôte :\n");
        for (Student v : plateforme.getUnpairedVisitors()) {
            matchesOutput.append(v.getName()).append("\n");
        }

        matchesOutput.append("\n❌ Hôtes sans visiteur :\n");
        for (Student h : plateforme.getUnpairedHosts()) {
            matchesOutput.append(h.getName()).append("\n");
        }

        // 🔹 Mettre à jour l'affichage
        outputArea.setText(matchesOutput.toString());
    }

    private void saveMatchingHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("POO/csv/historique_appariements.csv", true))) {
            for (Map.Entry<Student, Student> entry : plateforme.getPairs().entrySet()) {
                writer.write(entry.getKey().getName() + ";" + entry.getValue().getName());
                writer.newLine();
            }
        } catch (IOException e) {
            outputArea.setText("Erreur lors de la sauvegarde de l'historique !");
        }
    }

    private void loadMatchingHistory() {
        StringBuilder historyOutput = new StringBuilder("Historique des Appariements :\n");
        try (BufferedReader reader = new BufferedReader(new FileReader("historique_appariements.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historyOutput.append(line).append("\n");
            }
        } catch (IOException e) {
            historyOutput.append("Aucun historique trouvé.");
        }

        outputArea.setText(historyOutput.toString());
    }



    
    

    private void exportResults(Stage stage) {
        plateforme.updateStudentHistory();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner l'emplacement d'export");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        
        File file = fileChooser.showSaveDialog(stage);
    
        if (file != null) {
            plateforme.exportMatchingResults(file.getAbsolutePath());
            outputArea.setText("Résultats exportés dans : " + file.getAbsolutePath());
        }
    }

    private void modifyPair() {
        Stage modifyStage = new Stage();
        modifyStage.setTitle("Modifier une Appariement");

        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        ComboBox<Student> visitorComboBox = new ComboBox<>();
        visitorComboBox.getItems().addAll(plateforme.getVisitor());
        visitorComboBox.setPromptText("Sélectionner un Visiteur");

        ComboBox<Student> hostComboBox = new ComboBox<>();
        hostComboBox.getItems().addAll(plateforme.getHost());
        hostComboBox.setPromptText("Sélectionner un Hôte");

        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> {
            Student selectedVisitor = visitorComboBox.getValue();
            Student selectedHost = hostComboBox.getValue();

            if (selectedVisitor != null && selectedHost != null) {
                plateforme.modifyPair(selectedVisitor, selectedHost);
                updateOutput("Appariement modifié : " + selectedVisitor.getName() + " ↔ " + selectedHost.getName());
                modifyStage.close();
            } else {
                updateOutput("Veuillez sélectionner un visiteur et un hôte.");
            }
        });

        root.getChildren().addAll(visitorComboBox, hostComboBox, confirmButton);

        Scene scene = new Scene(root, 300, 200);
        modifyStage.setScene(scene);
        modifyStage.show();
    }
    

    private void updateOutput(String message) {
        outputArea.setText(message);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
