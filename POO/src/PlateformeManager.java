public class PlateformeManager {
    private static PlateformeManager instance;
    private Plateforme plateforme;

    private PlateformeManager() {
        this.plateforme = new Plateforme();
        System.out.println("DEBUG: PlateformeManager - Nouvelle plateforme créée");
    }

    public static PlateformeManager getInstance() {
        if (instance == null) {
            instance = new PlateformeManager();
        }
        return instance;
    }

    public Plateforme getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
        System.out.println("DEBUG: PlateformeManager - Plateforme mise à jour avec " + 
            (plateforme != null && plateforme.getStudents() != null ? plateforme.getStudents().size() : 0) + " étudiants");
    }
} 