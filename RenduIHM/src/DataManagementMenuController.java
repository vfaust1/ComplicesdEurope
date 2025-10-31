import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;

public class DataManagementMenuController {

    private Plateforme plateforme;

    public DataManagementMenuController() {
        this.plateforme = PlateformeManager.getInstance().getPlateforme();
        System.out.println("DEBUG: DataManagementMenuController - Plateforme récupérée du manager");
    }

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        PlateformeManager.getInstance().setPlateforme(plateforme);
        System.out.println("DEBUG: DataManagementMenuController - Plateforme définie avec " + 
            (plateforme != null && plateforme.getStudents() != null ? plateforme.getStudents().size() : 0) + " étudiants");
    }

    @FXML
    private void handleImportButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(((javafx.scene.Node)event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Importation des étudiants
                plateforme.importStudentsFromCSV(selectedFile.getAbsolutePath());
                
                // Rediriger vers la page de sélection des pays
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/SelectionPays.fxml"));
                Parent selectionPaysParent = loader.load();

                SelectionPaysController controller = loader.getController();
                if (controller != null) {
                    controller.setPlateforme(this.plateforme);
                }

                Scene selectionPaysScene = new Scene(selectionPaysParent);
                Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
                window.setScene(selectionPaysScene);
                window.setTitle("Sélection des Pays");
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'import du fichier CSV");
            }
        }
    }

    @FXML
    private void handleHistoriqueButton(ActionEvent event) {
        System.out.println("Bouton Historique cliqué dans Data Management Menu !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/Historique.fxml"));
            Parent historiqueParent = loader.load();

            HistoriqueController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene historiqueScene = new Scene(historiqueParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(historiqueScene);
            window.setTitle("Historique");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Historique.fxml");
        }
    }

    @FXML
    private void handleListeElevesButton(ActionEvent event) {
        System.out.println("Bouton Liste élèves cliqué dans Data Management Menu !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/ListeEleves.fxml"));
            Parent listeElevesParent = loader.load();

            ListeElevesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene listeElevesScene = new Scene(listeElevesParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(listeElevesScene);
            window.setTitle("Liste des Élèves");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page ListeEleves.fxml");
        }
    }

    @FXML
    private void handleRetourButton(ActionEvent event) {
        System.out.println("Bouton Retour cliqué dans Data Management Menu !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/MainMenu.fxml"));
            Parent mainMenuParent = loader.load();

            MainMenuController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene mainMenuScene = new Scene(mainMenuParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainMenuScene);
            window.setTitle("Menu Principal");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page MainMenu.fxml");
        }
    }

    @FXML
    private void handleSupprimerElevesIncorrectsButton(ActionEvent event) {
        System.out.println("Bouton Supprimer les Étudiants incorrects cliqué !");
        if (plateforme != null) {
            plateforme.removeStudents();
            StringBuilder removedStudentsOutput = new StringBuilder("Étudiants supprimés :\n");
            for (Student student : plateforme.getRemoveStudents()) {
                removedStudentsOutput.append(student.getName()).append("\n");
            }
            removedStudentsOutput.append("\nTotal supprimés : ").append(plateforme.getRemoveStudents().size());
            
            // Afficher le résultat dans une fenêtre de dialogue
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Résultat de la suppression");
            alert.setHeaderText("Étudiants supprimés");
            alert.setContentText(removedStudentsOutput.toString());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de suppression");
            alert.setContentText("Aucune plateforme n'est initialisée.");
            alert.showAndWait();
        }
    }
} 