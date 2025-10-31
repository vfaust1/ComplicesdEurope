import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.input.MouseEvent;

public class ListeHotesController {
    private Plateforme plateforme;

    @FXML
    private TableView<Student> hotesTable;
    @FXML
    private TableColumn<Student, String> nomColumn;
    @FXML
    private TableColumn<Student, String> prenomColumn;
    @FXML
    private TableColumn<Student, String> paysColumn;
    @FXML
    private TableColumn<Student, String> genreColumn;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        if (plateforme != null) {
            hotesTable.getItems().setAll(plateforme.getHost());
        }
    }

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        paysColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

    @FXML
    private void handleVoirProfilButton(ActionEvent event) {
        Student selectedStudent = hotesTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenduIHM/ProfilEleve.fxml"));
                Parent profilParent = loader.load();
                ProfilEleveController controller = loader.getController();
                controller.setStudent(selectedStudent);
                controller.setPlateforme(this.plateforme);
                Scene profilScene = new Scene(profilParent);
                Stage window = (Stage) hotesTable.getScene().getWindow();
                window.setScene(profilScene);
                window.setTitle("Profil de l'élève");
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
} 