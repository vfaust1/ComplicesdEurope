
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PairTest {

    private Student host1, host2, host3;
    private Student visitor1, visitor2, visitor3;
    private Pair Pair1, Pair2, Pair3;

    @BeforeEach
    public void Initialisation() {
        // Initialisation des objets avant chaque test

        // Création des étudiants hôtes
        Map<Critere, String> critariaHost1 = new HashMap<>();
        critariaHost1.put(Critere.PAIR_GENDER, "F");
        critariaHost1.put(Critere.HISTORY, "yes");
        critariaHost1.put(Critere.HOBBIES, "reading,swimming,cooking");
        critariaHost1.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        critariaHost1.put(Critere.HOST_HAS_ANIMAL, "yes");
        critariaHost1.put(Critere.GUEST_FOOD, "vegan,vegetarian");
        critariaHost1.put(Critere.HOST_FOOD, "vegan,vegetarian,gluten-free");
        host1 = new Student("Jean", "Dupont", "Male", LocalDate.of(2007, 10, 5), Country.FR, critariaHost1);

        Map<Critere, String> critariaHost2 = new HashMap<>();
        critariaHost2.put(Critere.PAIR_GENDER, "M");
        critariaHost2.put(Critere.HISTORY, "no");
        critariaHost2.put(Critere.HOBBIES, "sports,music");
        critariaHost2.put(Critere.GUEST_ANIMAL_ALLERGY, "yes");
        critariaHost2.put(Critere.HOST_HAS_ANIMAL, "no");
        critariaHost2.put(Critere.GUEST_FOOD, "omnivore");
        critariaHost2.put(Critere.HOST_FOOD, "omnivore,vegetarian");
        host2 = new Student("Alice", "Martin", "Female", LocalDate.of(2006, 3, 15), Country.ES, critariaHost2);

        Map<Critere, String> critariaHost3 = new HashMap<>();
        critariaHost3.put(Critere.PAIR_GENDER, "F");
        critariaHost3.put(Critere.HISTORY, "yes");
        critariaHost3.put(Critere.HOBBIES, "traveling,photography");
        critariaHost3.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        critariaHost3.put(Critere.HOST_HAS_ANIMAL, "yes");
        critariaHost3.put(Critere.GUEST_FOOD, "gluten-free");
        critariaHost3.put(Critere.HOST_FOOD, "vegan,gluten-free");
        host3 = new Student("Maria", "Lopez", "Female", LocalDate.of(2005, 7, 20), Country.ES, critariaHost3);

        // Création des étudiants visiteurs
        Map<Critere, String> critariaVisitor1 = new HashMap<>();
        critariaVisitor1.put(Critere.PAIR_GENDER, "M");
        critariaVisitor1.put(Critere.HISTORY, "yes");
        critariaVisitor1.put(Critere.HOBBIES, "reading,video games,cooking");
        critariaVisitor1.put(Critere.GUEST_ANIMAL_ALLERGY, "yes");
        critariaVisitor1.put(Critere.HOST_HAS_ANIMAL, "no");
        critariaVisitor1.put(Critere.GUEST_FOOD, "vegan,vegetarian");
        critariaVisitor1.put(Critere.HOST_FOOD, "vegan,vegetarian,gluten-free");
        visitor1 = new Student("Paul", "Smith", "Male", LocalDate.of(2007, 10, 5), Country.FR, critariaVisitor1);

        Map<Critere, String> critariaVisitor2 = new HashMap<>();
        critariaVisitor2.put(Critere.PAIR_GENDER, "F");
        critariaVisitor2.put(Critere.HISTORY, "no");
        critariaVisitor2.put(Critere.HOBBIES, "sports,traveling");
        critariaVisitor2.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        critariaVisitor2.put(Critere.HOST_HAS_ANIMAL, "yes");
        critariaVisitor2.put(Critere.GUEST_FOOD, "omnivore");
        critariaVisitor2.put(Critere.HOST_FOOD, "omnivore,vegetarian");
        visitor2 = new Student("Emma", "Brown", "Female", LocalDate.of(2006, 5, 10), Country.GE, critariaVisitor2);

        Map<Critere, String> critariaVisitor3 = new HashMap<>();
        critariaVisitor3.put(Critere.PAIR_GENDER, "M");
        critariaVisitor3.put(Critere.HISTORY, "yes");
        critariaVisitor3.put(Critere.HOBBIES, "photography,swimming");
        critariaVisitor3.put(Critere.GUEST_ANIMAL_ALLERGY, "no");
        critariaVisitor3.put(Critere.HOST_HAS_ANIMAL, "yes");
        critariaVisitor3.put(Critere.GUEST_FOOD, "gluten-free");
        critariaVisitor3.put(Critere.HOST_FOOD, "vegan,gluten-free");
        visitor3 = new Student("Liam", "Garcia", "Male", LocalDate.of(2005, 8, 25), Country.ES, critariaVisitor3);

        // Création des objets Pair
        Pair1 = new Pair(host1, visitor1);
        Pair2 = new Pair(host2, visitor2);
        Pair3 = new Pair(host3, visitor3);


    }

    /* Test de la compatibilité entre plusieurs étudiants */
    @Test
    public void testCompatibilityScenarios() {
        assertFalse(Pair1.isCompatible());
        assertTrue(Pair2.isCompatible());
        assertTrue(Pair3.isCompatible());
    }

    //Test de l'afinité entre 2 étudiants
    @Test
    public void testAfinity() {
        Pair1.setAfinity("");
        Pair2.setAfinity("");
        Pair3.setAfinity("");

        assertEquals(-1, Pair1.getAfinity());
        assertTrue(Pair2.getAfinity() > 0);
        assertTrue(Pair3.getAfinity() > 0);


    }

    //Test le nombre de hobbies en commun
    @Test
    public void testCommonHobbies() {
        assertEquals(2, Pair1.getCommonHobbies());
        assertEquals(1, Pair2.getCommonHobbies());
        assertEquals(1, Pair3.getCommonHobbies());
    }

    



}