# Projet : le code d'Huffman

Le but de ce projet est de travailler sur la compression de fichiers à l'aide du **code de Huffman**. À la fin de ce projet, vous serez en mesure de compresser des fichiers du monde réel en utilisant un format personnalisé (`.huf`).

## Logistique 

* Le projet peut être fait par groupe de 2 à 4 personnes.
* Le projet doit être fait en Scala 3.
* Le rendu se fera sous la forme d'un fichier zip et comportera 
  * Le code de votre projet.
  * Un rapport (à titre indicatif, 5 pages, écrit en français ou en anglais) détaillant l'architecture de votre projet, les fonctionnalités implémentées, et toute autre information que vous jugerez utile. Ne négligez pas cette partie : la clarté de la rédaction sera prise en compte !
* Vous devrez effectuer des testes et les inclure dans votre rendu. Ces derniers seront également évalués.
* Le projet doit être rendu avant le 11/03 à 14:00.

## Contexte

Le codage de Huffman est un algorithme de compression sans perte (c'est-à-dire conservant toutes les informations) qui peut être utilisé pour compresser des listes de symboles. Il est largement utilisé dans les tâches de compression de données, comme l'archivage de fichiers. Par exemple, le codage de Huffman est utilisé dans [Gzip](https://en.wikipedia.org/wiki/Gzip).  

Généralement, pour un texte normal non compressé (c'est-à-dire une chaîne de caractères), chaque caractère est un symbole représenté par le même nombre de bits (généralement huit).  

Pour le texte `"ABEACADABEA"`, en utilisant le [code ASCII](https://www.ascii-code.com/) pour chaque caractère, le texte encodé comporte $11 \times 8 = 88$ bits.  

Le codage de Huffman est conçu pour optimiser la longueur des données encodées. Dans le codage de Huffman, chaque symbole peut avoir une représentation binaire de longueur différente, en fonction des fréquences des symboles : les symboles apparaissant fréquemment dans un texte sont représentés par une séquence de bits plus courte que ceux utilisés plus rarement.  

Pour le texte `"ABEACADABEA"` :

<table>
  <thead>
    <tr><th>Symbole</th><th>Fréquence</th><th>Code Huffman</th></tr>
  </thead>
  <tbody>
    <tr><td>A</td><td>5</td><td>0</td></tr>
    <tr><td>B</td><td>2</td><td>100</td></tr>
    <tr><td>C</td><td>1</td><td>110</td></tr>
    <tr><td>D</td><td>1</td><td>111</td></tr>
    <tr><td>E</td><td>2</td><td>101</td></tr>
  </tbody>
</table>

Le texte encodé avec Huffman comporte $5 \times 1 + (2 + 1 + 1 + 2) \times 3 = 23$ bits, ce qui représente une réduction significative par rapport aux $88$ bits d'origine !  

Dans ce projet, votre tâche est d'implémenter l'algorithme de codage de Huffman.

## Théorie du codage de Huffman

Pour un texte donné, chaque code de Huffman définit une séquence de bits spécifique pour représenter un symbole. L'idée principale du codage de Huffman repose sur deux principes :

- Les symboles apparaissant plus fréquemment doivent avoir des codes plus courts ;
- Aucun code n'est le préfixe d'un autre (principe connu sous le nom de [propriété de préfixe](https://www.wikipedia.org/wiki/Prefix_code)).

<details class="note">

<summary>Pourquoi la propriété de préfixe est-elle importante ?</summary>

Prenons l'exemple de l'encodage du texte `AB`, où `A` est encodé en $10$ et `B` en $1010$. Le texte encodé serait alors $101010$. Or, en essayant de le décoder, il devient ambigu de savoir où commence et où se termine chaque code.

Cette ambiguïté est éliminée grâce à la propriété de préfixe, qui garantit qu'une séquence de codes est immédiatement et uniquement décodable sans nécessiter de délimiteurs ou de marqueurs spéciaux entre les codes individuels.

</details>

<br/>

Les codes de Huffman sont représentés par un arbre de codes, qui est un arbre binaire dont les feuilles représentent les symboles.


### Construction des arbres de codage de Huffman

Utilisons le texte `ABEACADABEA` avec les symboles `A` à `E` comme exemple. La construction de l'arbre de codage se fait en deux étapes :

1. Créer un nœud feuille pour chaque symbole, associé à un poids indiquant la fréquence d'apparition de ce symbole.

   <details class="note">

   <summary>Afficher les arbres de codes</summary>

      ```text
      A(5)   B(2)   C(1)   D(1)   E(2)
      ```
</details>

2. Les feuilles crées précédemment peuvent être vues comme des arbres indépendants, que nous souhaitons fusionner. Pour ce faire, tant qu'il reste plus d'un arbre de code, supprimer les deux arbres ayant le poids de racine le plus faible et les fusionner en un nouvel arbre en créant un nœud de branchement. Un nœud de branchement peut être considéré comme un ensemble contenant les symboles présents dans les feuilles situées en dessous de lui, son poids étant la somme des poids de ces feuilles.

<details class="note">

   <summary>Afficher la construction de l'arbre pas à pas</summary>

   Fusionner `C(1)` et `D(1)`:
   ```text
   A(5)   B(2)   E(2)   CD(2)
                       /    \
                     C(1)  D(1)
   ```
   Fusionner `B(2)` et `E(2)`:
   ```text
   A(5)    BE(4)       CD(2)
          /    \      /    \
         B(2)  E(2)  C(1)  D(1)
   ```
   Fusionner `BE(4)` et `CD(2)`:
   ```text
   A(5)          BECD(6)
                /      \
              BE(4)     CD(2)
             /   \     /   \
           B(2) E(2)  C(1) D(1)
   ```
   Fusionner `A(5)` et `BECD(6)`:
   ```text
       ABECD(11)
      /         \
     A(5)       BECD(6)
               /      \
              BE(4)    CD(2)
             /   \     /   \
           B(2) E(2)  C(1) D(1)
   ```

   </details>

<br/>

> [!NOTE]
> Chaque sous-arbre est lui-même un arbre de codage valide pour un ensemble réduit de symboles.

### Encodage

Pour un arbre de Huffman donné, on peut obtenir la représentation encodée d’un symbole en parcourant l’arbre depuis la racine jusqu'à la feuille contenant le symbole. À chaque étape, lorsqu’une branche gauche est choisie, un `0` est ajouté à la représentation, et lorsqu’une branche droite est choisie, un `1` est ajouté.

<div class="note check">

Quel est le code de Huffman pour le symbole `D` dans l’arbre de code ci-dessous ?

![Exemple d'arbre de Huffman](./figures/huffman-table.png)

<details>
    <summary>Solution</summary>
    1011
</details>
</div>

### Décodage

Le décodage commence également à la racine de l'arbre. Étant donné une séquence de bits à décoder, on lit successivement les bits : pour chaque `0`, on emprunte la branche gauche, et pour chaque `1`, on emprunte la branche droite.

Lorsqu'on atteint une feuille, on décode le symbole correspondant, puis on recommence à partir de la racine de l’arbre.

<div class="note check">

En utilisant l’arbre de Huffman du ci-dessus, à quel texte correspond la séquence de bits `1000110` ?

<details>
    <summary>Solution</summary>
    BAC
</details>
</div>

<br/>

> [!NOTE]
> Le codage de Huffman garantit que la longueur moyenne des symboles encodés est minimisée.

### Format

Voici le format des fichier `.huf` que vous allez utiliser. La première ligne sert à indiquer que le fichier est bien un fichier `.huf`, la seconde représente l'encodage (par exemple, `B` à pour code `100`), et la dernière représente le message codé (ici, `BAC`).

```
HUF
SYMBOLS: A:0 B:100 C:110 D:111 E:101 
DATA: 1000110
```

Le code d'un parseur est mis à votre disposition dans [`HuffmanParser.scala`](./src/main/scala/HuffmanParser.scala). Une fichier exemple est disponible dans [`test.huf`](./src/test/huf/test.huf). Attention: pour les caractères spéciaux, ces derniers doivent être encodés entre quote (par exemple, `'\n'` pour le retour à la ligne).


## Étapes du projet

Le plus important est d'avoir une base de projet solide. Veuillez trouve ci-dessous la liste des étapes nécessaire du projet: 

1. Création de la base du projet : 
    * Réfléchissez à l'architecture de votre projet (package, fonctionnalités, etc). Vous pouvez réaliser un diagramme de classe et l'inclure au rapport (fortement recommandé). 
    * Créer le squelette de votre projet.
2. Premier encodage : 
    * Création des premiers tests
    * Génération de l'arbre de Huffman
    * Fonctions d'encodage et de décodage
3. Lecture et écriture dans un fichier : 
    * Génération des fichiers `.huf` contenant votre arbre et le message codé.
    * Import un fichiers `.huf` et décodage du message.
    * Conseil : essayer de decoder les messages des autres groupes !

### Étapes plus avancées 

Une fois que votre base fonctionne, vous pouvez ajouter des fonctionnalités. Certaines sont plus difficiles que d'autres. Vous pouvez également rajouter des améliorations qui ne sont pas listées ci-dessous.

#### Extension de votre projet pour encoder n'importe quel types de données 

Le codage de Huffman peut gérer différents types de symboles. Par exemple, nous pouvons compresser une image où chaque symbole est une couleur représentée sous la forme d'un triplet RGB `(Rouge, Vert, Bleu)`. Chaque symbole peut être, par exemple, `(255, 0, 0)` pour le rouge pur, `(0, 255, 0)` pour le vert pur, etc.

Étendez votre architecture pour être capable d’encoder n'importe quel type d'information.

#### Réduction de la taille de l'arbre

Parfois, le point bloquant est la taille de l'arbre. Proposer une manière de représenter votre arbre qui réduise la place occupée par ce dernier. L'amélioration peut être en terme de place mémoire (en Scala) ou de représentation (dans le `.huf`).

#### Transformation de Burrows-Wheeler

Les outils de compression actuels ne reposent pas uniquement sur le codage de Huffman, car cela ne permettrait pas d'obtenir des taux de compression très efficaces. L'outil populaire [bzip2](https://en.wikipedia.org/wiki/Bzip2), par exemple, applique plusieurs étapes de compression, dont l'une est le codage de Huffman. Une autre étape utilisée dans bzip2 est la [transformation de Burrows-Wheeler (BWT)](https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform), qui est une méthode remarquablement élégante (et réversible) permettant de permuter les caractères d'une chaîne. En utilisant la BWT de manière appropriée, on peut rendre l'encodage de Huffman beaucoup plus efficace.

Votre tâche est simple : implémentez la BWT, soit en l'ajoutant à votre projet de codage de Huffman, soit en créant un nouveau projet comme on vous l’a montré. Il existe une abondante documentation en ligne (faire des recherches par soi-même est une compétence essentielle pour tout programmeur !)

#### Pré et Post-conditions

Ajouter des pré et post-conditions au endroits clés de votre programme.

#### Surprenez-moi

Si vous avez une idée d'amélioration, libre à vous de l'implémenter ! 
