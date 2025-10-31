// import de la libraire des fichiers jar

import java.io.Serializable;

public class Pair implements Serializable {
    private Student host;
    private Student visitor;
    private int afinity = 0;

    public Pair(Student host, Student visitor){
        this.host = host;
        this.visitor = visitor;
    }

    public Student getHost(){
        return this.host;
    }

    public Student getVisitor(){
        return this.visitor;
    }

    // Nouvelles méthodes pour l'affichage dans TableView
    public String getHostName() {
        return this.host.getName();
    }

    public String getVisitorName() {
        return this.visitor.getName();
    }

    public String getHostCountryName() {
        return this.host.getCountryName();
    }

    public int getAfinity(){
        return this.afinity;
    }

    /* Verifie si les 2 etudiants sont compatibles en fonction des allergies et animaux possédés , les regimes alimentaires et leur historique */
    public boolean isCompatible(){
        boolean isCompatible = true;
        if (this.visitor.isAllergic() && this.host.hasAnimal()){
            isCompatible = false;
        }
        //verifie sur les regimes du visiteur != none
        if (!this.visitor.getRegimeVisitor().contains("none")){
            for (String regime : this.visitor.getRegimeVisitor()){
                if (!this.host.getRegimeHost().contains(regime)){
                    isCompatible = false;
                }
        }
        }

        return isCompatible;
    }

    //Verifie si les 2 etudiants ont au moins un passe temps en commun
    public boolean hasCommonHobbies(){
        for (String hobby : this.visitor.getHobbies()){
            if (this.host.getHobbies().contains(hobby)){
                return true;
            }
        }
        return false;
    }

    //Verifie si l'hote propose des regimes alimentaires compatibles avec le visiteur
    public boolean hasCompatibleRegime(){
        for (String regime : this.visitor.getRegimeVisitor()){
            if (!this.host.getRegimeHost().contains(regime)){
                return false;
            }
        }
        return true;
    }

    //Nombre de hobbies en commun
    public int getCommonHobbies(){
        int count = 0;
        for (String hobby : this.visitor.getHobbies()){
            // On enlève les espaces avant et après le hobby avec trim
            String trimmedHobby = hobby.trim();
            for (String hostHobby : this.host.getHobbies()){
                if (hostHobby.trim().equalsIgnoreCase(trimmedHobby)){
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString(){
        return "Host : " + this.host.getName() + " Visitor : " + this.visitor.getName() + " Afinity : " + this.afinity;
    }


    //Verifie si la pair a deja existé 
    public boolean hasHistory() {
        return false;
    }

    //Version final de calcul afinité
    public void setAfinity(String criterePrioritaire) {
            int score = 100;

            //si visiteur refuse un ancien correspondant ET hôte est cet ancien correspondant alors retourner -1
            if (((this.visitor.getPreferenceHistory().equals("no") || this.host.getPreferenceHistory().equals("no"))) && this.hasHistory() ){
                this.afinity = -1;
                return;
            }
    
            // Modèle de pondération dynamique basé sur le critère choisi
            double poidsAge = criterePrioritaire.equals("age") ? 1.5 : 1.0;
            double poidsGenre = criterePrioritaire.equals("genre") ? 1.5 : 1.0;
            double poidsHobbies = criterePrioritaire.equals("hobbies") ? 1.5 : 1.0;
    
            // Différence d'âge pondérée
            if (Math.abs(this.host.getAge() - this.visitor.getAge()) < 18) {
                score -= 20 * poidsAge;
            }
    
            // Respect du genre pondéré si par autre que none
            if ( !this.visitor.getPairGender().equals("none") && this.host.getGenre().equals(this.visitor.getPairGender())) {
                score -= 25 * poidsGenre;
            }
            if ( !this.host.getPairGender().equals("none") && this.visitor.getGenre().equals(this.host.getPairGender())) {
                score -= 25 * poidsGenre;
            }
    
            // Hobbies en commun pondérés
            int n = this.getCommonHobbies();
            if (n > 0) {
                score = (int) Math.ceil(score / (1 + (n * 0.5 * poidsHobbies)));
            }
            

            
            //Allergie au animaux
            if (this.visitor.isAllergic() && this.host.hasAnimal()) {
                if (score <= 20) {
                    score += 15; // Compensation maximale
                } else if (score <= 40) {
                    score += 25; // Compensation partielle
                } else {
                    this.afinity = -1; // Incompatibilité rédhibitoire
                    return;
                }
            }

            // Régimes alimentaires
            if (!this.visitor.getRegimeVisitor().contains("none")) {
                for (String regime : this.visitor.getRegimeVisitor()) {
                    if (!this.host.getRegimeHost().contains(regime)) {
                        if (score <= 20) {
                            score += 15; // Compensation maximale
                        } else if (score <= 40) {
                            score += 25; // Compensation partielle
                        } else {
                            this.afinity = -1; // Incompatibilité rédhibitoire
                            return;
                        }
                    }
                }
            }
            
    
            this.afinity = score;
        }
}
  


