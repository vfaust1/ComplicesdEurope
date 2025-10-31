import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.List;

public class SelectionPaysController {
    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        if (plateforme != null) {
            List<String> countries = plateforme.getCountries();
            paysHoteComboBox.getItems().addAll(countries);
            paysVisiteurComboBox.getItems().addAll(countries);
        }
    }

    @FXML
    private ComboBox<String> paysHoteComboBox;
    @FXML
    private ComboBox<String> paysVisiteurComboBox;

    @FXML
    private void handleValiderButton(ActionEvent event) {
        String paysHote = paysHoteComboBox.getValue();
        String paysVisiteur = paysVisiteurComboBox.getValue();

        if (paysHote == null || paysVisiteur == null) {
            showAlert("Erreur", "Veuillez sélectionner les deux pays.");
            return;
        }

        if (paysHote.equals(paysVisiteur)) {
            showAlert("Erreur", "Le pays hôte et le pays visiteur doivent être différents.");
            return;
        }

        try {
            // Mettre à jour les pays dans la plateforme
            plateforme.setHostCountry(Country.valueOf(paysHote.substring(0, 2).toUpperCase()));
            plateforme.setVisitorCountry(Country.valueOf(paysVisiteur.substring(0, 2).toUpperCase()));
            
            // Trier les étudiants selon leur pays
            plateforme.sortStudents();

            // Aller à la page de filtrage des élèves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/FiltrageEleves.fxml"));
            Parent filtrageParent = loader.load();

            FiltrageElevesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene filtrageScene = new Scene(filtrageParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(filtrageScene);
            window.setTitle("Filtrage des élèves");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page FiltrageEleves.fxml");
        }
    }

    @FXML
    private void handleRetourButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/DataManagementMenu.fxml"));
            Parent dataManagementParent = loader.load();

            DataManagementMenuController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene dataManagementScene = new Scene(dataManagementParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(dataManagementScene);
            window.setTitle("Gestion des Données");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page DataManagementMenu.fxml");
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