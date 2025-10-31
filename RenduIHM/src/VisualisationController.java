import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class VisualisationController {
    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    @FXML
    private void handleVoirHotesButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/ListeHotes.fxml"));
            Parent listeHotesParent = loader.load();

            ListeHotesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene listeHotesScene = new Scene(listeHotesParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(listeHotesScene);
            window.setTitle("Liste des Hôtes");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page ListeHotes.fxml");
        }
    }

    @FXML
    private void handleVoirVisiteursButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/ListeVisiteurs.fxml"));
            Parent listeVisiteursParent = loader.load();

            ListeVisiteursController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene listeVisiteursScene = new Scene(listeVisiteursParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(listeVisiteursScene);
            window.setTitle("Liste des Visiteurs");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page ListeVisiteurs.fxml");
        }
    }

    @FXML
    private void handleVoirAppariementsButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/PairesCalculees.fxml"));
            Parent pairCalculeParent = loader.load();

            PairesCalculeesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene pairCalculeScene = new Scene(pairCalculeParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(pairCalculeScene);
            window.setTitle("Paires Calculées");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page PairesCalculees.fxml");
        }
    }

    @FXML
    private void handleAppariementButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/Appariement.fxml"));
            Parent appariementParent = loader.load();

            AppariementController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene appariementScene = new Scene(appariementParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(appariementScene);
            window.setTitle("Gestion des Appariements");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Appariement.fxml");
        }
    }

    @FXML
    private void handleSaveExportButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/SaveExport.fxml"));
            Parent saveExportParent = loader.load();

            SaveExportController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene saveExportScene = new Scene(saveExportParent);
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(saveExportScene);
            window.setTitle("Sauvegarder/Exporter");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page SaveExport.fxml");
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
} 