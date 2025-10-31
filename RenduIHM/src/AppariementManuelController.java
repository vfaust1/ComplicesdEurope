import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.util.StringConverter;
import java.io.IOException;
import java.util.List;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AppariementManuelController {

    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        System.out.println("DEBUG: AppariementManuelController - Plateforme définie avec " + 
            (plateforme != null && plateforme.getStudents() != null ? plateforme.getStudents().size() : 0) + " étudiants");
        
        if (etudiantVisiteurComboBox != null && etudiantHoteComboBox != null && this.plateforme != null) {
            // S'assurer que les étudiants sont triés
            this.plateforme.sortStudents();
            
            // Afficher les étudiants dans les ComboBox
            System.out.println("DEBUG: Nombre d'hôtes : " + this.plateforme.getHost().size());
            System.out.println("DEBUG: Nombre de visiteurs : " + this.plateforme.getVisitor().size());
            
            etudiantVisiteurComboBox.getItems().setAll(this.plateforme.getVisitor());
            etudiantHoteComboBox.getItems().setAll(this.plateforme.getHost());
        }
    }

    @FXML
    private ComboBox<Student> etudiantVisiteurComboBox;
    @FXML
    private ComboBox<Student> etudiantHoteComboBox;

    @FXML
    public void initialize() {
        // Configurer le StringConverter pour les ComboBox
        StringConverter<Student> studentConverter = new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student != null ? student.getPrenom() + " " + student.getName() : "";
            }

            @Override
            public Student fromString(String string) {
                return null; // Pas besoin d'implémenter cette méthode pour notre cas d'usage
            }
        };

        // Appliquer le converter aux ComboBox
        etudiantVisiteurComboBox.setConverter(studentConverter);
        etudiantHoteComboBox.setConverter(studentConverter);

        // Si la plateforme est déjà définie
        if (this.plateforme != null) {
            etudiantVisiteurComboBox.getItems().setAll(this.plateforme.getVisitor());
            etudiantHoteComboBox.getItems().setAll(this.plateforme.getHost());
        }
    }

    @FXML
    private void handleRetourButton(ActionEvent event) {
        System.out.println("Bouton Retour cliqué dans Appariement Manuel !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/Appariement.fxml"));
            Parent appariementParent = loader.load();

            AppariementController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene appariementScene = new Scene(appariementParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(appariementScene);
            window.setTitle("Appariement");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Appariement.fxml");
        }
    }

    @FXML
    private void handleApparierButton(ActionEvent event) {
        System.out.println("Bouton Apparier cliqué !");
        Student selectedVisiteur = etudiantVisiteurComboBox.getValue();
        Student selectedHote = etudiantHoteComboBox.getValue();

        if (selectedVisiteur != null && selectedHote != null) {
            Pair pair = new Pair(selectedHote, selectedVisiteur);
            if (!pair.isCompatible()) {
                // Afficher une boîte de confirmation
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Incompatibilité détectée");
                alert.setHeaderText("Les étudiants sélectionnés ne sont pas compatibles.");
                alert.setContentText("Voulez-vous quand même confirmer l'appariement ?");
                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non", ButtonType.CANCEL.CLOSE.getButtonData());
                alert.getButtonTypes().setAll(yesButton, noButton);
                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        this.plateforme.modifyPair(selectedHote, selectedVisiteur);
                        etudiantHoteComboBox.getItems().remove(selectedHote);
                        etudiantVisiteurComboBox.getItems().remove(selectedVisiteur);
                    } else {
                        System.out.println("Appariement annulé par l'utilisateur.");
                    }
                });
            } else {
                this.plateforme.modifyPair(selectedHote, selectedVisiteur);
                etudiantHoteComboBox.getItems().remove(selectedHote);
                etudiantVisiteurComboBox.getItems().remove(selectedVisiteur);
            }
        } else {
            System.out.println("Veuillez sélectionner un étudiant visiteur et un étudiant hôte.");
        }
    }

    @FXML
    private void handleExporterCSVButton(ActionEvent event) {
        System.out.println("Bouton Exporter CSV cliqué !");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier CSV");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"),
            new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        
        // Définir le nom par défaut du fichier
        fileChooser.setInitialFileName("paires_appariement.csv");
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                this.plateforme.exportMatchingResults(file.getAbsolutePath());
                System.out.println("Fichier CSV exporté avec succès : " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Erreur lors de l'exportation du fichier CSV : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleModifierButton(ActionEvent event) {
        System.out.println("Bouton Modifier cliqué !");
        
        // Vérifier s'il y a des paires à modifier
        if (plateforme.getPairs().isEmpty()) {
            System.out.println("Aucune paire à modifier.");
            return;
        }
        
        // Créer une nouvelle fenêtre pour la modification
        Stage modifyStage = new Stage();
        modifyStage.setTitle("Modifier une Paire");

        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        // ComboBox pour sélectionner la paire à modifier
        ComboBox<Map.Entry<Student, Student>> pairComboBox = new ComboBox<>();
        pairComboBox.setPromptText("Sélectionner une paire à modifier");
        
        // Convertir les paires en entrées de ComboBox
        for (Map.Entry<Student, Student> entry : plateforme.getPairs().entrySet()) {
            pairComboBox.getItems().add(entry);
        }

        // ComboBox pour le nouvel hôte
        ComboBox<Student> newHostComboBox = new ComboBox<>();
        newHostComboBox.setPromptText("Sélectionner le nouvel hôte");
        newHostComboBox.getItems().addAll(plateforme.getHost());

        // ComboBox pour le nouveau visiteur
        ComboBox<Student> newVisitorComboBox = new ComboBox<>();
        newVisitorComboBox.setPromptText("Sélectionner le nouveau visiteur");
        newVisitorComboBox.getItems().addAll(plateforme.getVisitor());

        // Bouton de confirmation
        Button confirmButton = new Button("Confirmer la modification");
        confirmButton.setOnAction(e -> {
            Map.Entry<Student, Student> selectedPair = pairComboBox.getValue();
            Student newHost = newHostComboBox.getValue();
            Student newVisitor = newVisitorComboBox.getValue();

            if (selectedPair != null && newHost != null && newVisitor != null) {
                // Supprimer l'ancienne paire
                plateforme.getPairs().remove(selectedPair.getKey());
                
                // Ajouter la nouvelle paire
                plateforme.modifyPair(newHost, newVisitor);
                
                // Mettre à jour les listes
                plateforme.getHost().remove(newHost);
                plateforme.getVisitor().remove(newVisitor);
                
                // Rafraîchir les ComboBox
                etudiantHoteComboBox.getItems().setAll(plateforme.getHost());
                etudiantVisiteurComboBox.getItems().setAll(plateforme.getVisitor());
                
                modifyStage.close();
            } else {
                System.out.println("Veuillez sélectionner une paire et les nouveaux étudiants.");
            }
        });

        root.getChildren().addAll(
            new Label("Paire à modifier :"),
            pairComboBox,
            new Label("Nouvel hôte :"),
            newHostComboBox,
            new Label("Nouveau visiteur :"),
            newVisitorComboBox,
            confirmButton
        );

        Scene scene = new Scene(root, 400, 300);
        modifyStage.setScene(scene);
        modifyStage.show();
    }
} 