# **OTHELLO : PROJET JAVA LICENCE INFORMATIQUE**


Créé par Thomas HODSON et Othman BENCHEFRIF [11-2022 → 01-2023]. Programmé avec JAVA via l'architecture M.V.C.

Projet étudiant Licence 2 semestre 3 -- Université Paris Est Créteil Val-de-Marne (UPEC) (Paris XII).

# **DESCRIPTION**

Ce projet implémente le jeu de l'othello en Java, incluant les modes suivants;:
- Joueur contre Joueur
- Joueur contre IA
- IA contre Joueur
- IA contre IA 

Le projet dispose de trois niveaux d'IA et de la sauvegarde des comptes ainsi que de leurs performances.

# **COMMENCER**

Pour commencer cloner le projet sur votre machine et installer les dépendances suivantes.

## **DEPENDANCES**

Le projet utilise les librairies suivantes:
- [sqlite-jdbc-3.40.0.0.jar](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc) : API de communication avec la base de données
- [json-simple-1.1.1.jar](https://code.google.com/archive/p/json-simple/) : Lecture des fichiers de configurations JSON.

## **LANCER LE PROGRAMME**

Il n'y a pas de procédure particulière pour lancer le programme



# **CONFIGURATION**


## **CONFIGURATION GENERALE**

L'application dispose d'un fichier de configuration qui se situe dans le répertoire */config*.
Le fichier de configuration permet de choisir les paramètres suivants :

| clé | Valeur attendue | Description |
|--|--|--|
| version | "dev" | **NE PAS CHANGER** |
| debug | Boolean | Affiche dans la console des messages de debug pendant l'execution du programme |
| fullScreen | Boolean | Pour mettre l'application en plein écran. |
| windowWidth | Integer > 0 | Largeur de la fenêtre de l'application (1920 recommandé)
| windowHeight | Integer > 0 | Hauteur de la fenêtre de l'application (1080 recommandé)
| tailleTableau | Integer > 4 ( pair ) | Taille du plateau de l'othellier (8 recommandé)
| iaDelay | Integer > 0 | Pause entre chaque coups de l'IA.
| minMaxCeil | Integer > 0 | Profondeur d'analyse de l'IA difficile
| pathToGameSaved | String | **NE PAS CHANGER**
| lang | String | Nom du fichier json contenant les textes dans langue ciblée
| theme | String | Nom du fichier json contenant les valeurs du texte ciblé


## **LANGUES**

Les fichiers contenant les textes traduits se trouvent dans le répertoire *resources/i18n/*.
Pour ajouter une langue il suffit de créer un nouveau fichier dans ce répertoire nommé avec le format suivant *abréviation.json*. Vous trouverez l'ensemble des abréviations [ici](https://fr.wikipedia.org/wiki/Liste_des_codes_ISO_639-1). Attention ! ne pas changer la première valeur. IL suffit de copier coller le code suivant et de compléter les valeurs avec le texte traduit:

```json
{
    "type": "lang",
    "othello": "",
    "credits": "",
    "play": "",
    "username": "",
    "gameAsPlayerOne": "",
    "gameAsPlayerTwo": "",
    "amountVictories": "",
    "amountDefeates": "",
    "amountDraws": "",
    "selectPlayer":"",
    "scorePlayer":"",
    "victories": "",
    "defeates": "",
    "draws": "",
    "search": "",
    "accounts": "",
    "menu": "",
    "easyIA": "",
    "normalIA": "",
    "hardIA": "",
    "enterUsername": "",
    "scoreIaEasyP1": "",
    "scoreIaEasyP2": "",
    "scoreIaNormalP1": "",
    "scoreIaNormalP2": "",
    "scoreIaHardP1": "",
    "scoreIaHardP2": "",
    "scoreP1": "",
    "scoreP2": "",
    "startGame": "",
    "mustSelectUsers": "",
    "waitingForGame": "",
    "turns": "",
    "player": "",
    "victoryPlayer": "",
    "draw": "",
    "saveGame": "",
    "yes" : "",
    "no": "",
    "saveGameAs": "",
    "loadAGame": "",
    "loadGame" : "",
    "noGameSaved": ""
}
```

## **THEMES**

Les fichiers contenant les thèmes se trouvent dans le répertoire *resources/themes/*.
Pour ajouter un thème il suffit de créer un nouveau fichier dans ce répertoire nommé avec le format suivant *nom_du_theme.json*. Attention ! les couleurs doivent être enregistrées sous le format hexadécimal. Attention ! ne pas changer la première valeur. IL suffit de copier coller le code suivant et de compléter les valeurs de thèmes désirées:

```json
{
    "type": "theme",
    "backgroundClr": "",
    "complementaryClr": "",
    "compelementaryTextColor": "",
    "player1Clr": "",
    "player2Clr": "",
    "evenTileClr": "",
    "oddTileClr": "",
    "BoardClr": "",
    "standartTextClr": "",
    "titleFontWeight": "",
    "titleFontSize": "",
    "titleFontFamily": "",
    "playerInfoFontFamily": "",
    "playerInfoFontWeight": "",
    "playerInfoFontSize": "",
    "creditFontWeight": "",
    "creditFontFamilly": "",
    "creditFontSize": "",
    "captionFontWeight": "",
    "captionFontFamily": "",
    "captionFontSize": "",
    "victoryRateChartBackground": "",
    "victoryRateChartOutline": "",
    "defeateRateChartBackground": "",
    "defeateRateChartOutline": "",
    "drawRateChartBackground": "",
    "drawRateChartOutline": "",
    "chartFontFamily": "",
    "chartFontSize": "",
    "chartFontWeight": "",
    "searchUsernameFontSize": "",
    "searchUsernameFontWeight": "",
    "searchUsernameFontFamily": "",
    "startGameButtonFontSize": "",
    "startGameButtonFontWeight": "",
    "startGameButtonFontFamily": "",
    "boardTileColor": "",
    "possibleMoveColor": "",
    "boardBackgroundColor": "",
    "boardForegroundColor": "",
    "boardFontSize": "",
    "boardFontWeight": "",
    "boardFontFamily": ""
}
```

# **APPERCU**

Page d'accueil :

![Page d'accueil](https://cdn.discordapp.com/attachments/886601548484968499/1060941903278899240/image.png)

---

Page des comptes :

![Page des comptes](https://cdn.discordapp.com/attachments/886601548484968499/1060942253796896868/image.png)

---

Page de jeu:

![Page de jeu](https://cdn.discordapp.com/attachments/886601548484968499/1060942514208649296/image.png)


---

En partie:
![En partie](https://cdn.discordapp.com/attachments/886601548484968499/1060942745939759134/image.png)