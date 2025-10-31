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
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class PairesCalculeesController {

    private Plateforme plateforme;

    @FXML
    private TableView<PairRow> pairesTable;
    @FXML
    private TableColumn<PairRow, String> etudiantVisiteurColumn;
    @FXML
    private TableColumn<PairRow, String> etudiantHoteColumn;
    @FXML
    private TableColumn<PairRow, String> paysHoteColumn;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        updateTable();
    }

    @FXML
    public void initialize() {
        etudiantVisiteurColumn.setCellValueFactory(new PropertyValueFactory<>("visiteurName"));
        etudiantHoteColumn.setCellValueFactory(new PropertyValueFactory<>("hoteName"));
        paysHoteColumn.setCellValueFactory(new PropertyValueFactory<>("paysHote"));
        updateTable();
    }

    private void updateTable() {
        if (plateforme == null || plateforme.getPairs() == null) return;
        ObservableList<PairRow> data = FXCollections.observableArrayList();
        for (Map.Entry<Student, Student> entry : plateforme.getPairs().entrySet()) {
            Student hote = entry.getKey();
            Student visiteur = entry.getValue();
            data.add(new PairRow(
                visiteur.getPrenom() + " " + visiteur.getName(),
                hote.getPrenom() + " " + hote.getName(),
                hote.getCountry().getPays()
            ));
        }
        pairesTable.setItems(data);
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
    private void handleExporterCSVButton(ActionEvent event) {
        // TODO: Implémenter l'export CSV
    }

    public static class PairRow {
        private final String visiteurName;
        private final String hoteName;
        private final String paysHote;

        public PairRow(String visiteurName, String hoteName, String paysHote) {
            this.visiteurName = visiteurName;
            this.hoteName = hoteName;
            this.paysHote = paysHote;
        }

        public String getVisiteurName() { return visiteurName; }
        public String getHoteName() { return hoteName; }
        public String getPaysHote() { return paysHote; }
    }
} 