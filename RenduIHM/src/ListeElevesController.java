import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ListeElevesController {

    private Plateforme plateforme;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        System.out.println("DEBUG: ListeElevesController - Plateforme définie avec " + 
            (plateforme != null && plateforme.getStudents() != null ? plateforme.getStudents().size() : 0) + " étudiants");
        // Mettre à jour la TableView dès que la plateforme est définie
        if (elevesTable != null && this.plateforme != null) {
            if (this.plateforme.getStudents() != null) {
                System.out.println("DEBUG: ListeElevesController - Nombre d'étudiants dans Plateforme : " + this.plateforme.getStudents().size());
                elevesTable.getItems().setAll(this.plateforme.getStudents());
            } else {
                System.out.println("DEBUG: ListeElevesController - plateforme.getStudents() est null.");
            }
        } else {
            System.out.println("DEBUG: ListeElevesController - elevesTable ou plateforme est null.");
        }
    }

    @FXML
    private TableView<Student> elevesTable;
    @FXML
    private TableColumn<Student, String> nomColumn;
    @FXML
    private TableColumn<Student, String> prenomColumn;
    @FXML
    private TableColumn<Student, String> paysColumn;
    @FXML
    private TableColumn<Student, String> genreColumn;

    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de l'objet Student
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        paysColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Le peuplement de la TableView se fait dans setPlateforme, car la plateforme n'est disponible qu'après l'initialisation du contrôleur.
    }

    @FXML
    private void handleAppariementButton(ActionEvent event) {
        System.out.println("Bouton Appariement cliqué dans Liste élèves !");
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
    private void handleVoirProfilButton(ActionEvent event) {
        System.out.println("Bouton Voir Profil cliqué !");
        Student selectedStudent = elevesTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/ProfilEleve.fxml"));
                Parent profilEleveParent = loader.load();

                ProfilEleveController controller = loader.getController();
                if (controller != null) {
                    controller.setStudent(selectedStudent);
                    controller.setPlateforme(this.plateforme);
                }

                Scene profilEleveScene = new Scene(profilEleveParent);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(profilEleveScene);
                window.setTitle("Profil de l'Élève");
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors du chargement de la page ProfilEleve.fxml");
            }
        } else {
            System.out.println("Veuillez sélectionner un élève pour voir son profil.");
            // Optionnel: Afficher un message d'erreur à l'utilisateur
        }
    }

    @FXML
    private void handleRetourButton(ActionEvent event) {
        System.out.println("Bouton Retour cliqué dans Liste élèves !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/FiltrageEleves.fxml"));
            Parent filtrageParent = loader.load();

            FiltrageElevesController controller = loader.getController();
            if (controller != null) {
                controller.setPlateforme(this.plateforme);
            }

            Scene filtrageScene = new Scene(filtrageParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(filtrageScene);
            window.setTitle("Filtrage des élèves");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page FiltrageEleves.fxml");
        }
    }

    // Si un bouton '+' est ajouté dynamiquement, sa logique de gestion serait ici ou dans une méthode appelée par lui
} 