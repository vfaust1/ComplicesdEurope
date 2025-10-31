enum Critere {
    GUEST_ANIMAL_ALLERGY('B'),
    HOST_HAS_ANIMAL('B'),
    GUEST_FOOD('T'),
    HOST_FOOD('T'),
    HOBBIES('T'),
    GENDER('T'),
    PAIR_GENDER('T'),
    HISTORY('T');
    
    private final char TYPE;

    private Critere(char type) {
        this.TYPE = type;
    }

    public char getType(){
        return this.TYPE;
    }

    public boolean isBoolean(){
        if (getType() == 'B'){
            return true;
        }else{
            return false;
        }
    }

    
}