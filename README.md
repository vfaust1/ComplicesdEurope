# Complices d'Europe
Une application (Java / JavaFX) d'aide à la décision pour organiser et optimiser des séjours linguistiques par appariement d'étudiants « hôtes » et « visiteurs ». Elle contient :

- une interface graphique JavaFX,
- une version terminal/console,

Ce README explique comment comprendre le dépôt, compiler et exécuter l'application, et où trouver les ressources utiles.

## Aperçu

L'objectif du projet est de fournir un outil pour charger des listes d'étudiants (CSV), définir pays hôte/visiteur, filtrer les candidats et produire des appariements (manuels ou automatiques) en tenant compte de critères (âge, hobbies, genre, etc.).

Le dépôt contient aussi la partie IHM (interfaces FXML + contrôleurs Java), des exemples de données et des sauvegardes sérialisées.

## Structure du dépôt (résumé)

- `POO/` : code source et binaires de la version console (contient `src/`, `bin/`, `historique/`, etc.).
- `RenduIHM/` : ressources JavaFX, fichiers `.fxml`, contrôleurs Java et images pour l'interface graphique.
- `src/` : classes Java générales (algorithme d'appariement, modèles comme `Pair`, `Student`, `Plateforme`, etc.).
- `lib/` : bibliothèques (JavaFX jars, autres dépendances si présentes).
- `csv/` : jeux de données / exemples CSV (`JeuDeDonnée.csv`, `matching_results.csv`).
- `POO/bin/` : répertoire cible des classes compilées (utilisé par les scripts).
- `POO/historique/` : sauvegardes sérialisées (.ser) d'exemples d'exécution.
- `run.sh`, `compile.sh` : scripts Unix (bash) fournis pour compiler et lancer.
- `ProgrammeTerminal.jar` : jar exécutable (si présent)
- `GRAPHES/`, `RenduIHM/` (ressources pour la partie IHM et rapport).

## Principales fonctionnalités

- Import et export d'étudiants au format CSV.
- Configuration des pays hôte et visiteur.
- Filtrage des étudiants selon des critères.
- Appariements manuels et automatiques (algorithme basé sur une matrice de coûts).
- Interface graphique en JavaFX et interface console.
- Sauvegarde / chargement d'état via sérialisation.

## Prérequis

- Java 11 ou plus récent (JDK).
- JavaFX SDK correspondant à la version de votre JDK (si vous utilisez l'IHM JavaFX).
- Un shell Unix (Git Bash / WSL) est recommandé pour utiliser les scripts `compile.sh` / `run.sh`. Des instructions PowerShell sont fournies ci‑dessous.

## Compiler (option recommandée : utiliser le script)

- Sous Linux / macOS / Git Bash / WSL :

	- Compilation (depuis la racine du dépôt) :

		```bash
		./compile.sh
		```

	- Le script compile les sources situées dans `POO/src` vers `POO/bin` et utilise les jars présents dans `lib/`.

- Sous Windows PowerShell (exemple minimal) :

	1. Construire une variable CLASSPATH contenant tous les jars de `lib` séparés par `;` :

		 ```powershell
		 $jars = Get-ChildItem -Path .\lib -Filter '*.jar' -Recurse | ForEach-Object { $_.FullName }
		 $env:CLASSPATH = $jars -join ';'
		 ```

	2. Compiler les sources Java (adapté si vous avez javac sur le PATH) :

		 ```powershell
		 javac -cp $env:CLASSPATH -sourcepath POO/src -d POO/bin (Get-ChildItem -Path POO/src -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })
		 ```

	3. Note : l'IHM JavaFX nécessite le `--module-path` pointant vers le dossier `lib`/JavaFX et l'option `--add-modules`. Il est souvent plus simple d'utiliser Git Bash/WSL pour reprendre les scripts fournis.

## Exécuter

- Exécution via script (Unix-like) :

	```bash
	./run.sh
	```

- Exécution sous Windows (PowerShell) — exécution de la version console (`ProgrammeTerminal`) :

	1. S'assurer que `POO/bin` est dans le classpath et que `$env:CLASSPATH` contient les jars JavaFX si nécessaire.
	2. Exemple minimal (console, sans IHM JavaFX) :

		 ```powershell
		 java -cp "POO/bin;$env:CLASSPATH" ProgrammeTerminal
		 ```

	Si vous souhaitez lancer l'interface JavaFX, adaptez la commande en ajoutant `--module-path` vers le dossier contenant les modules JavaFX et `--add-modules javafx.controls,javafx.fxml`.

- Si `ProgrammeTerminal.jar` est utilisable (exécutable), on peut tenter :

	```powershell
	java -jar ProgrammeTerminal.jar
	```

	mais selon la construction du jar et la présence de modules JavaFX, il faudra peut‑être fournir le `--module-path` et `--add-modules`.

## Données d'exemple

- Les fichiers CSV d'exemple se trouvent dans `csv/` et `POO/bin/` contient des jeux de données et des sérialisations dans `POO/historique/`.
- Pour tester rapidement : importer `csv/JeuDeDonnée.csv` via le menu du `ProgrammeTerminal` ou via l'IHM.

## Tests

- Des fichiers de test JUnit sont présents dans le dossier `test/` (`StudentTest.java`, `CritereTest.java`, etc.). Pour lancer les tests, utilisez votre IDE (IntelliJ/VSCode + extensions Java) ou configurez un runner JUnit sur la classpath.

## Notes sur le code (points importants trouvés)

- Le cœur applicatif est composé de classes : `Plateforme`, `PlateformeManager`, `Student`, `Pair`, `MatchingAlgorithm` qui gèrent la logique d'appariement et la représentation des données.
- L'IHM JavaFX se trouve dans `RenduIHM/` : fichiers `.fxml` et contrôleurs Java correspondants.
- Le programme console principal est `POO/src/ProgrammeTerminal.java` (menu interactif, import/export, appariement manuel/automatique).

---