# Exécution du jar IHM

Pour lancer l'application graphique :

```bash
java --module-path /chemin/vers/javafx-sdk-21.0.7/lib --add-modules javafx.controls,javafx.fxml -jar ihm-app.jar
```

- Remplace `/chemin/vers/javafx-sdk-21.0.7/lib` par le chemin réel vers le dossier `lib` de JavaFX sur ta machine.
- Le jar doit être lancé depuis le dossier `POO/jar` ou en adaptant le chemin relatif.

**Prérequis :**
- Java 21 
- JavaFX 21.0.7 installé sur la machine 