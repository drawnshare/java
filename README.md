# Draw'nShare
Ceci est la branche master du projet annuel.

## Fonctionnement du repo
Le projet est construit de façon à ce que chaque branche correspondent à un module du projet, et seule la branche master regroupe tous les modules, à l'occasion de releases.

Pour travailler sur une partie du projet, il faut donc récupérer la branche correspondante.

```bash
# Cloner le repo
$ git clone https://github.com/drawnshare/java.git

# Créer la branche qui possède la feature sur laquelle on veut travailler
$ git checkout -b <module name>

# Récupérer le code de la branche distante
$ git pull origin <module branch>

# Créer la branche de développement local
$ git checkout -b <module name/develop>
```

Une fois ces commandes tapée, vous avez une branche fonctionnelle avec le dernier code en date. Il est conseillé de créer une branche dédiée à une feature depuis cette branche là, et là merger avec la branche ```origin <module branch>```.
```
master ---------------\------------------------M/------------  
module branch ---------\----------------M/-----/  
module develop branch --\--C----C--C--C/  
C = commit
M = merge
```
