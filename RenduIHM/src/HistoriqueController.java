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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.Map;

public class HistoriqueController {

    private Plateforme plateforme;
    private static final String HISTORIQUE_PATH = "/home/infoetu/aliocha.deflou.etu/C6-main (3)/POO/historique";

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        System.out.println("Plateforme définie dans HistoriqueController");
        // Recharger les données après avoir défini la plateforme
        loadHistoriqueData();
    }

    public static class HistoriqueEntry {
        private final String paysHote;
        private final String paysVisiteur;
        private final int annee;

        public HistoriqueEntry(String paysHote, String paysVisiteur, int annee) {
            this.paysHote = paysHote;
            this.paysVisiteur = paysVisiteur;
            this.annee = annee;
        }

        public String getPaysHote() { return paysHote; }
        public String getPaysVisiteur() { return paysVisiteur; }
        public int getAnnee() { return annee; }
    }

    @FXML
    private TableView<HistoriqueEntry> historiqueTable;
    @FXML
    private TableColumn<HistoriqueEntry, String> paysHoteColumn;
    @FXML
    private TableColumn<HistoriqueEntry, String> paysVisiteurColumn;
    @FXML
    private TableColumn<HistoriqueEntry, Integer> anneeColumn;
    @FXML
    private ComboBox<String> fileComboBox;
    @FXML
    private TextArea fileContentArea;

    @FXML
    public void initialize() {
        System.out.println("Initialisation du contrôleur HistoriqueController");
        // Configuration des colonnes
        paysHoteColumn.setCellValueFactory(new PropertyValueFactory<>("paysHote"));
        paysVisiteurColumn.setCellValueFactory(new PropertyValueFactory<>("paysVisiteur"));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("annee"));

        // Charger les données de l'historique
        loadHistoriqueData();
        remplirComboBoxFichiers();
    }

    private void loadHistoriqueData() {
        System.out.println("Début du chargement des données historiques...");
        List<HistoriqueEntry> entries = new ArrayList<>();
        File historiqueDir = new File(HISTORIQUE_PATH);
        
        if (historiqueDir.exists() && historiqueDir.isDirectory()) {
            System.out.println("Le dossier historique existe");
            File[] files = historiqueDir.listFiles((dir, name) -> name.endsWith(".ser") || name.endsWith(".csv"));
            if (files != null) {
                System.out.println("Nombre de fichiers trouvés : " + files.length);
                for (File file : files) {
                    String fileName = file.getName();
                    System.out.println("Traitement du fichier : " + fileName);
                    // Format attendu: PaysHote_PaysVisiteur_Annee.ser ou .csv
                    String nameSansExt = fileName.replace(".ser", "").replace(".csv", "");
                    String[] parts = nameSansExt.split("_");
                    if (parts.length == 3) {
                        try {
                            int annee = Integer.parseInt(parts[2]);
                            entries.add(new HistoriqueEntry(parts[0], parts[1], annee));
                            System.out.println("Entrée ajoutée : " + parts[0] + " - " + parts[1] + " - " + annee);
                        } catch (NumberFormatException e) {
                            System.err.println("Erreur de format pour le fichier: " + fileName);
                        }
                    } else {
                        System.out.println("Format de fichier incorrect : " + fileName);
                    }
                }
            } else {
                System.out.println("Aucun fichier trouvé dans le dossier historique");
            }
        } else {
            System.out.println("Le dossier historique n'existe pas");
        }

        System.out.println("Nombre total d'entrées chargées : " + entries.size());
        ObservableList<HistoriqueEntry> data = FXCollections.observableArrayList(entries);
        historiqueTable.setItems(data);
        System.out.println("Données chargées dans la TableView");
    }

    private void remplirComboBoxFichiers() {
        fileComboBox.getItems().clear();
        File historiqueDir = new File(HISTORIQUE_PATH);
        if (historiqueDir.exists() && historiqueDir.isDirectory()) {
            File[] files = historiqueDir.listFiles((dir, name) -> name.endsWith(".ser") || name.endsWith(".csv"));
            if (files != null) {
                for (File file : files) {
                    fileComboBox.getItems().add(file.getName());
                }
            }
        }
    }

    @FXML
    private void handleRetourButton(ActionEvent event) {
        System.out.println("Bouton Retour cliqué dans Historique !");
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

    @FXML
    private void handleChargerFichierButton(ActionEvent event) {
        String selectedFile = fileComboBox.getValue();
        if (selectedFile == null) {
            fileContentArea.setText("Veuillez sélectionner un fichier d'historique.");
            return;
        }
        File file = new File(HISTORIQUE_PATH + "/" + selectedFile);
        if (!file.exists()) {
            fileContentArea.setText("Fichier non trouvé : " + selectedFile);
            return;
        }
        if (selectedFile.endsWith(".ser")) {
            // Lecture d'un fichier de sauvegarde Plateforme
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Plateforme historiquePlateforme = (Plateforme) ois.readObject();
                StringBuilder historyText = new StringBuilder("Appariements enregistrés dans ce fichier :\n");
                for (Map.Entry<Student, Student> entry : historiquePlateforme.getPairs().entrySet()) {
                    historyText.append(entry.getKey().getName()).append(" ↔ ")
                              .append(entry.getValue().getName()).append("\n");
                }
                fileContentArea.setText(historyText.toString());
            } catch (Exception e) {
                fileContentArea.setText("Erreur lors de la lecture du fichier : " + e.getMessage());
            }
        } else if (selectedFile.endsWith(".csv")) {
            // Lecture d'un fichier CSV
            try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                StringBuilder csvText = new StringBuilder();
                while (scanner.hasNextLine()) {
                    csvText.append(scanner.nextLine()).append("\n");
                }
                fileContentArea.setText(csvText.toString());
            } catch (Exception e) {
                fileContentArea.setText("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            }
        } else {
            fileContentArea.setText("Format de fichier non supporté.");
        }
    }
} 