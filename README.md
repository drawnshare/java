Cette branche aura pour but de faire le parseur d'annotation.
Les travaux vont se baser sur le tuto suivant: http://hannesdorfmann.com/annotation-processing/annotationprocessing101

Le processeur d'annotation est separes des annotations en elle meme afin de pouvoir les utilises sans pour autant devoir importer le processeur.
La processeur d'annotation sera dans un fichier .jar separes de l'application en elle meme par restriction du code Java: En effet, un parseur est lancer sur une JVM differente de l'application principale. Il faut l'inclure dans le path avec des metadonnees particuliere et il se lancera automatiquement en paralele