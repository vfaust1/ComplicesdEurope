import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class SaveExportController {
    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    @FXML
    private TextField anneeField;

    @FXML
    private void handleSaveButton(ActionEvent event) {
        String annee = anneeField.getText().trim();
        if (annee.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer une année.");
            return;
        }

        try {
            int year = Integer.parseInt(annee);
            String filename = "POO/historique/" + plateforme.getPaysHost().getPays() + "_" + 
                            plateforme.getPaysVisitor().getPays() + "_" + 
                            year + ".ser";
            
            plateforme.saveToFile(filename);
            showAlert("Succès", "Sauvegarde effectuée avec succès dans le dossier historique.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'année doit être un nombre.");
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @FXML
    private void handleExportButton(ActionEvent event) {
        String annee = anneeField.getText().trim();
        if (annee.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer une année.");
            return;
        }

        try {
            int year = Integer.parseInt(annee);
            String filename = "POO/historique/" + plateforme.getPaysHost().getPays() + "_" + 
                            plateforme.getPaysVisitor().getPays() + "_" + 
                            year + ".csv";
            
            plateforme.exportToCSV(filename);
            showAlert("Succès", "Export effectué avec succès dans le dossier historique.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'année doit être un nombre.");
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors de l'export : " + e.getMessage());
        }
    }

    @FXML
    private void handleRetourButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/Visualisation.fxml"));
            Parent visualisationParent = loader.load();

            VisualisationController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene visualisationScene = new Scene(visualisationParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(visualisationScene);
            window.setTitle("Visualisation");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Visualisation.fxml");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 