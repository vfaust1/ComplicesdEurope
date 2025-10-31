import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {

    private Student student1;

    @BeforeEach
    public void Initialisation() {
        // Initialisation des objets avant chaque test
    
        
        /* creation d'un étudiant */
        // Création de la Map pour les critères
        Map<Critere, String> critaria1 = new HashMap<>();

        // Remplissage de la Map avec des données
        critaria1.put(Critere.PAIR_GENDER, "F"); // Genre préféré pour le pair
        critaria1.put(Critere.HISTORY, "same"); // A un historique d'échange
        critaria1.put(Critere.HOBBIES, "reading,swimming,cooking"); // Liste des hobbies
        critaria1.put(Critere.GUEST_ANIMAL_ALLERGY, "yes"); //allergie aux animaux
        critaria1.put(Critere.HOST_HAS_ANIMAL, "no"); // Possède un animal
        critaria1.put(Critere.GUEST_FOOD, "vegan,vegetarian"); // Régimes alimentaires en tant que visiteur
        critaria1.put(Critere.HOST_FOOD, "vegan,vegetarian,gluten-free"); // Régimes alimentaires acceptés en tant qu'hôte

        student1 = new Student("Jean", "Dupont", "Male", LocalDate.of(2007, 10, 5),Country.FR, critaria1 );
    }


    @Test
    public void testGetName() {
        assertEquals("Jean", student1.getName());
    }

    /*regarde si il est allegirque */
    @Test
    public void testIsAllergic() {
        assertTrue(student1.isAllergic());
    }

    /* Test si l'etudiant possède un animal */
    @Test
    public void testHasAnimal() {
        assertFalse(student1.hasAnimal());
    }

    /*Test des hobbies */
    @Test
    public void testGetHobbies() {
        assertEquals(3, student1.getHobbies().size());
        assertTrue(student1.getHobbies().contains("reading"));
        assertTrue(student1.getHobbies().contains("swimming"));
        assertTrue(student1.getHobbies().contains("cooking"));
    }

    /*Test des regimes en tant que visitor */
    @Test
    public void testGetRegimeVisitor() {
        assertEquals(2, student1.getRegimeVisitor().size());
        assertTrue(student1.getRegimeVisitor().contains("vegan"));
        assertTrue(student1.getRegimeVisitor().contains("vegetarian"));
    }

    // Test des regumes en tant qu'hote
    @Test
    public void testGetRegimeHost() {
        assertEquals(3, student1.getRegimeHost().size());
        assertTrue(student1.getRegimeHost().contains("vegan"));
        assertTrue(student1.getRegimeHost().contains("vegetarian"));
        assertTrue(student1.getRegimeHost().contains("gluten-free"));
    }

    //Test de l'age
    @Test
    public void testGetAge() {
        assertEquals(211, student1.getAge());
    }
    
    // Test de la validité des critères
    @Test
    public void testIsCritereTypeValid() {
        assertTrue(student1.isCritereTypeValid());
    }
    
}
