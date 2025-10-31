
public enum Country {
    FR("France"),
    ES("Espagne"),
    IT("Italia"),
    GE("Germany");

    private final String pays;

    private Country(String pays){
        this.pays = pays;
    }

    public String getPays(){
        return this.pays;
    }

    public boolean  isValidCountry(String country) {
        for (Country c : Country.values()) {
            if (c.name().equalsIgnoreCase(country)) {
                return true;
            }
        }
        return false;
    }

}
