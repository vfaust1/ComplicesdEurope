import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class WelcomeScreenController {

    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    @FXML
    private void handleEntrerButton(ActionEvent event) {
        System.out.println("Bouton Entrer cliqué !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/DataManagementMenu.fxml"));
            Parent dataManagementMenuParent = loader.load();
            
            DataManagementMenuController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene dataManagementMenuScene = new Scene(dataManagementMenuParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(dataManagementMenuScene);
            window.setTitle("Menu de Gestion des Données");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page DataManagementMenu.fxml");
        }
    }
} 