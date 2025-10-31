import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Map;

public class AppariementController {

    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        System.out.println("DEBUG: AppariementController - Plateforme définie avec " + 
            (plateforme != null && plateforme.getStudents() != null ? plateforme.getStudents().size() : 0) + " étudiants");
        // Essayer de populer les ComboBox immédiatement si les éléments FXML sont déjà injectés
        if (paysVisiteurComboBox != null && paysHoteComboBox != null) {
            populateCountryComboBoxes();
        } else {
            System.out.println("DEBUG: AppariementController.setPlateforme - ComboBox non encore initialisées.");
        }
    }

    @FXML
    private ComboBox<String> paysVisiteurComboBox;
    @FXML
    private ComboBox<String> paysHoteComboBox;
    @FXML
    private Slider hobbiesSlider;
    @FXML
    private Slider ageSlider;
    @FXML
    private Slider genreSlider;

    @FXML
    public void initialize() {
        System.out.println("DEBUG: AppariementController.initialize - Initialisation du contrôleur.");
        // Peupler les ComboBox si la plateforme est déjà disponible
        if (this.plateforme != null) {
            populateCountryComboBoxes();
        } else {
            System.out.println("DEBUG: AppariementController.initialize - Plateforme non encore définie.");
        }
    }

    // Nouvelle méthode pour populer les ComboBox de pays
    private void populateCountryComboBoxes() {
        if (this.plateforme != null && paysVisiteurComboBox != null && paysHoteComboBox != null) {
            System.out.println("DEBUG: AppariementController.populateCountryComboBoxes - Peuplement des ComboBox de pays.");
            paysVisiteurComboBox.getItems().setAll(this.plateforme.getCountries());
            paysHoteComboBox.getItems().setAll(this.plateforme.getCountries());
        } else {
            System.out.println("DEBUG: AppariementController.populateCountryComboBoxes - Conditions non remplies pour le peuplement.");
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

    @FXML
    private void handleAppariementManuelButton(ActionEvent event) {
        System.out.println("Bouton Appariement manuel cliqué !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/AppariementManuel.fxml"));
            Parent appariementManuelParent = loader.load();

            AppariementManuelController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene appariementManuelScene = new Scene(appariementManuelParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(appariementManuelScene);
            window.setTitle("Appariement Manuel");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page AppariementManuel.fxml");
        }
    }

    @FXML
    private void handleAppariementAutomatiqueButton(ActionEvent event) {
        System.out.println("Bouton Appariement automatique cliqué !");

        String selectedVisitorCountry = paysVisiteurComboBox.getValue();
        String selectedHostCountry = paysHoteComboBox.getValue();

        if (selectedVisitorCountry == null || selectedHostCountry == null || selectedVisitorCountry.isEmpty() || selectedHostCountry.isEmpty()) {
            System.err.println("Veuillez sélectionner un pays visiteur et un pays hôte.");
            return;
        }

        // Définir les pays dans la plateforme
        plateforme.setVisitorCountry(Country.valueOf(selectedVisitorCountry.substring(0, 2).toUpperCase()));
        plateforme.setHostCountry(Country.valueOf(selectedHostCountry.substring(0, 2).toUpperCase()));

        // Sorter les étudiants après avoir défini les pays
        plateforme.sortStudents();

        // Déterminer le critère d'appariement basé sur les sliders
        String selectedCriteria = "Sans Critère";
        if (hobbiesSlider.getValue() > 0) {
            selectedCriteria = "hobbies";
        } else if (ageSlider.getValue() > 0) {
            selectedCriteria = "age";
        } else if (genreSlider.getValue() > 0) {
            selectedCriteria = "genre";
        }

        System.out.println("Pays Visiteur: " + selectedVisitorCountry);
        System.out.println("Pays Hôte: " + selectedHostCountry);
        System.out.println("Critère choisi: " + selectedCriteria);

        // Calculer la matrice des coûts
        int[][] costMatrix = plateforme.createCostMatrix(selectedCriteria);

        // Effectuer l'appariement
        plateforme.matchStudents(costMatrix);

        // Naviguer vers la page PairesCalculees.fxml pour afficher les résultats
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/PairesCalculees.fxml"));
            Parent pairesCalculeesParent = loader.load();

            PairesCalculeesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene pairesCalculeesScene = new Scene(pairesCalculeesParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(pairesCalculeesScene);
            window.setTitle("Paires Calculées");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page PairesCalculees.fxml");
        }
    }

    @FXML
    private void handleCalculerButton(ActionEvent event) {
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
} 