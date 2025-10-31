import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Label;

public class ProfilEleveController {

    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label paysLabel;
    @FXML
    private Label dateNaissanceLabel;
    @FXML
    private Label allergicLabel;
    @FXML
    private Label hasAnimalLabel;
    @FXML
    private Label foodLabel;
    @FXML
    private Label hobbiesLabel;

    private Student student;
    private Plateforme plateforme; // Pour le retour à la page précédente

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            nomLabel.setText("Nom: " + student.getName());
            prenomLabel.setText("Prénom: " + student.getPrenom());
            genreLabel.setText("Genre: " + student.getGenre());
            paysLabel.setText("Pays: " + student.getCountry().getPays());
            dateNaissanceLabel.setText("Date de naissance: " + student.getBirthDate().toString());
            allergicLabel.setText("Allergique: " + student.getCritaria().get(Critere.GUEST_ANIMAL_ALLERGY));
            hasAnimalLabel.setText("Possède un animal: " + student.getCritaria().get(Critere.HOST_HAS_ANIMAL));
            foodLabel.setText("Préférences alimentaires: " + student.getCritaria().get(Critere.GUEST_FOOD));
            hobbiesLabel.setText("Hobbies: " + student.getCritaria().get(Critere.HOBBIES));
        }
    }

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    // Méthode pour le bouton "Retour"
    @FXML
    private void handleRetourButton(ActionEvent event) {
        System.out.println("Bouton Retour cliqué dans Profil Eleve !");
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

    // Méthode pour le bouton "Changer"
    @FXML
    private void handleChangeButton(ActionEvent event) {
        System.out.println("Bouton Changer cliqué !");
        // Logique pour permettre la modification des informations du profil de l'élève
        // Cela pourrait impliquer de passer les Labels en TextField éditables, etc.
    }
} 