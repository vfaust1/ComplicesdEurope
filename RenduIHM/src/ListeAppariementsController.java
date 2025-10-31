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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListeAppariementsController {
    private Plateforme plateforme;

    @FXML
    private TableView<AppariementRow> appariementsTable;
    @FXML
    private TableColumn<AppariementRow, String> hoteColumn;
    @FXML
    private TableColumn<AppariementRow, String> visiteurColumn;
    @FXML
    private TableColumn<AppariementRow, Double> scoreColumn;

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        if (plateforme != null && plateforme.getPairs() != null) {
            List<AppariementRow> rows = new ArrayList<>();
            for (Map.Entry<Student, Student> entry : plateforme.getPairs().entrySet()) {
                Student hote = entry.getKey();
                Student visiteur = entry.getValue();
                double score = plateforme.calculateScore(hote, visiteur);
                rows.add(new AppariementRow(hote, visiteur, score));
            }
            appariementsTable.getItems().setAll(rows);
        }
    }

    @FXML
    public void initialize() {
        hoteColumn.setCellValueFactory(new PropertyValueFactory<>("hoteName"));
        visiteurColumn.setCellValueFactory(new PropertyValueFactory<>("visiteurName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
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

    // Classe interne pour représenter une ligne dans le tableau
    public static class AppariementRow {
        private final String hoteName;
        private final String visiteurName;
        private final double score;

        public AppariementRow(Student hote, Student visiteur, double score) {
            this.hoteName = hote.getName() + " " + hote.getPrenom();
            this.visiteurName = visiteur.getName() + " " + visiteur.getPrenom();
            this.score = score;
        }

        public String getHoteName() { return hoteName; }
        public String getVisiteurName() { return visiteurName; }
        public double getScore() { return score; }
    }
} 