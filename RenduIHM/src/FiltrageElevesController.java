import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.io.IOException;

public class FiltrageElevesController {
    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    @FXML
    private void handleSupprimerElevesIncorrectsButton(ActionEvent event) {
        if (plateforme != null) {
            plateforme.removeStudents();
            StringBuilder removedStudentsOutput = new StringBuilder("Étudiants supprimés :\n");
            for (Student student : plateforme.getRemoveStudents()) {
                removedStudentsOutput.append(student.getName()).append("\n");
            }
            removedStudentsOutput.append("\nTotal supprimés : ").append(plateforme.getRemoveStudents().size());
            
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

    @FXML
    private void handleRetourButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/ListeEleves.fxml"));
            Parent listeElevesParent = loader.load();

            ListeElevesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene listeElevesScene = new Scene(listeElevesParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(listeElevesScene);
            window.setTitle("Liste des élèves");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page ListeEleves.fxml");
        }
    }

    @FXML
    private void handleSuivantButton(ActionEvent event) {
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
} 