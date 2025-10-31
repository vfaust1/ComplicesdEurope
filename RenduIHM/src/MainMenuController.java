import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class MainMenuController {

    private Plateforme plateforme;

    public MainMenuController() {
        this.plateforme = PlateformeManager.getInstance().getPlateforme();
        System.out.println("DEBUG: MainMenuController - Plateforme récupérée du manager");
    }

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        PlateformeManager.getInstance().setPlateforme(plateforme);
        System.out.println("DEBUG: MainMenuController - Plateforme définie avec " + 
            (plateforme != null && plateforme.getStudents() != null ? plateforme.getStudents().size() : 0) + " étudiants");
    }

    // Méthode pour le bouton "Appariement"
    @FXML
    private void handleAppariementButton(ActionEvent event) {
        System.out.println("Bouton Appariement cliqué !");
        // Logique pour naviguer vers la page d'appariement
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/Appariement.fxml"));
            Parent appariementParent = loader.load();

            AppariementController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene appariementScene = new Scene(appariementParent);

            // Cette ligne récupère l'information du Stage
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(appariementScene);
            window.setTitle("Gestion des Appariements");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Appariement.fxml");
        }
    }

    // Méthode pour le bouton "Liste élèves"
    @FXML
    private void handleListeElevesButton(ActionEvent event) {
        System.out.println("Bouton Liste élèves cliqué !");
        // Logique pour naviguer vers la page de liste des élèves
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

    // Méthode pour le bouton "Gestion des données"
    @FXML
    private void handleGestionDonneesButton(ActionEvent event) {
        System.out.println("Bouton Gestion des données cliqué !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/DataManagementMenu.fxml"));
            Parent dataManagementParent = loader.load();

            DataManagementMenuController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene dataManagementScene = new Scene(dataManagementParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(dataManagementScene);
            window.setTitle("Gestion des Données");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page DataManagementMenu.fxml");
        }
    }

    // Méthode pour le bouton "Historique"
    @FXML
    private void handleHistoriqueButton(ActionEvent event) {
        System.out.println("Bouton Historique cliqué !");
        // Logique pour naviguer vers la page d'historique
        try {
            Parent historiqueParent = FXMLLoader.load(getClass().getResource("/RenduIHM/Historique.fxml"));
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
} 