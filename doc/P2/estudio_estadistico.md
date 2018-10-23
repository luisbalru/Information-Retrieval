---
header-includes:
 - \usepackage{float}
---

ESTUDIO ESTADÍSTICO SOBRE DISTINTOS ANALIZADORES TEXTUALES LUCENE 
==========================================

Recuperación de Información. Práctica 2. Luis Balderas Ruiz
-----------------------------------------------------------------------------

\par
\noindent\rule{\textwidth}{0.8pt}

Introducción
------------

\par
\noindent\rule{\textwidth}{0.4pt}

En esta práctica utilizamos analizadores del producto software Lucene para estudiar el tratamiento que se realiza sobre distintos textos. En mi caso, he tomado seis libros, cada uno en un idioma distinto, del [Proyecto Gutenberg](https://www.gutenberg.org/) que son los siguientes:

- *Alice's Adventures in Wonderland*, Lewis Carroll (inglés).  
- *Compendio di Chimica Fisiologica*, A. Cominelli (italiano).  
- *Bailén*, Benito Pérez Galdos (español).  
- *L'hérésiarque et Cie*, Guillaume Apollinaire (francés).  
- *Eskimomärchen*, Paul Sock (alemán).  
- *Quincas Borba*, Machado de Assis (portugués).  

Sobre esos textos utilizo cinco analizadores Lucene, que son WhiteSpaceAnalyzer, SimpleAnalyzer, StopAnalyzer, StandardAnalyzer y UAX29URLEmailAnalyzer. Adicionalmente, utilizo los analizadores correspondientes al idioma en cada caso, esto es, SpanishAnalyzer, EnglishAnalyzer, PortugueseAnalyzer, ItalianAnalyzer, FrenchAnalyzer y GermanAnalyzer.

Para cada texto seleccionaré ciertos tokens para comparar sus frecuencias dadas en cada analizador (excluyo el portugués).

Resultados del análisis
--------------------------------

\par
\noindent\rule{\textwidth}{0.4pt}

En primer lugar, observo que las lenguas romances están tratadas de una manera similar, haciendo especial mención en el tratamiento de tildes. Por eso, sólo reflejo los cinco de los seis libros. 

#### Alice's Adventures in Wonderland

|    | WhiteSpaceAnalyzer | SimpleAnalyzer | StopAnalyzer | StandardAnalyzer | UAX29URLEmailAnalyzer | EnglishAnalyzer |
| --- | ---- | ---- | ---- | ----- | ---- | ---- |
 | Nº tokens | 5948 | 3012 | 2979 | 3081 | 3084 | 2868 |
 | **Alice** | "Alice":221, "Alice,":76, "Alice.":54, "Alice;":16, "Alice’s":12, "Alice:":7,  "(Alice":4, "Alice!":3, "Alice,)":2, "ALICE'S"=2, "Alice!’":1, "Alice)—‘and":1, "‘Alice!’": 1,  "Alice’s,":1 | "alice": 404 | "alice": 404 | "alice": 386, "alice’s": 15, "alice's":3 |  "alice": 386, "alice’s": 15, "alice's":3 | "alice’s": 15, "alice's":3 |
 | **little** | "little":119, "little,":3, "Little":2, "little,’":1 "little—“’":1, "little.":1, "little!":1,"little!'":1 | "little":129 | "little":129 | "little":129 | "little":129 | "littl":129 |
 | **adventures** | "Adventures":4, "ADVENTURES":3, "adventures":2, "Adventures,":1, "adventures—beginning":1, "adventures.’":1 | "adventures":12 | "adventures":12 | "adventures":12 | "adventures":12 | "adventur":12 | 
| **the** | "the":1671, "The":106, "(‘the":1 | "the":1825 | 0 | 0 | "the":1825 |

#### Compendio di Chimica Fisiologica

|    | WhiteSpaceAnalyzer | SimpleAnalyzer | StopAnalyzer | StandardAnalyzer | UAX29URLEmailAnalyzer | ItalianAnalyzer |
| --- | ---- | ---- | ---- | ----- | ---- | ---- |
 | Nº tokens | 6433 | 4095 | 4064 | 4487 | 4491 | 4392 |
 | **sostanza** | "sostanza":32, "Sostanza":3, "(sostanza":2, "Amiloide-sostanza":1, "sostanza,":1, "       Sostanza":1 | "sostanza"=40 | "sostanza"=40 | "sostanza"=40 | "sostanza"=40 | "sostanza"=40 | 
 | **nell'alcool** | "nell'alcool,": 18, "nell'alcool":15, "nell'alcool.":8 | 0 | 0 | "nell'alcool":41  | "nell'alcool":41  | "nell'alcool":41 |
 | **glucosio** | "glucosio":25, "glucosio.":6, "glucosio,":5, "Glucosio:"1, "   Glucosio":1 | "glucosio":38 | "glucosio":38 | "glucosio":38 | "glucosio":38 | "glucosio":38 |
 | **di** | "di":821,"di)":8 , "(di":4, "Di":6| "di":839 | "di":839 | "di":839 | "di":839 | "di":839 | 
 
#### Bailén 
 
 |    | WhiteSpaceAnalyzer | SimpleAnalyzer | StopAnalyzer | StandardAnalyzer | UAX29URLEmailAnalyzer | SpanishAnalyzer |
| --- | ---- | ---- | ---- | ----- | ---- | ---- |
| Nº tokens | 16549 | 11244 | 11213 | 11333 | 11336 | 10806 |
| **así** | "así":37, "así:":18, "Así":10, "así,":5, "—Así":4, "Así,":4, "así?":1, "¡Así":1 | "así":80 | "así":80 | "así":80 |  "así":80 | "asi":80 (sin tilde) |
| **sombra** | "sombra":4, "sombras":2, "sombras,":1 | "sombra":4, "sombras":3 | "sombra":4, "sombras":3 | "sombra":4, "sombras":3 | "sombra":4, "sombras":3 | "sombra":4, "sombras":3 | 
| **hábil** | "hábil":3 | "hábil":3 | "hábil":3 | "hábil":3 | "hábil":3 |  0 ("habil":5) |

#### L'hérésiarque et Cie 

 |    | WhiteSpaceAnalyzer | SimpleAnalyzer | StopAnalyzer | StandardAnalyzer | UAX29URLEmailAnalyzer | FrenchAnalyzer |
| --- | ---- | ---- | ---- | ----- | ---- | ---- |
| Nº tokens | 13707 | 9270 | 9240 | 9821 | 9823 | 8718 |
| **prérogue** | "Prérogue.":6, "Prérogue":5, "Prérogue,":4 | "prérogue":15 | "prérogue":15 | "prérogue":15 | "prérogue":15 | 0 ("prérogu":15) |
| **Apôtres** | "Apôtres,":2, "apôtres,":2, "apôtres":1, "Apôtres":1 | "apôtres":6 | "apôtres":6  | "apôtres":6 | "apôtres":6 | 0 ("apotr":6) |
| **poète** | "poètes":6, "poète":3, "poète.":2, "poètes,":2, "poète,":1, "POÈTES":1 | "poète":6, "poètes":9 | "poète":6, "poètes":9 | "poète":6, "poètes":9 | "poète":6, "poètes":9 | 0 |

#### Eskimomärchen

 |    | WhiteSpaceAnalyzer | SimpleAnalyzer | StopAnalyzer | StandardAnalyzer | UAX29URLEmailAnalyzer | GermanAnalyzer |
| --- | ---- | ---- | ---- | ----- | ---- | ---- |
| Nº tokens | 8788 | 5936 | 5906 | 6058 | 6061 | 4729 | 
| **bär** | "Bär":24 | "bär":24 | "bär":24 | "bär":24 | "bär":24 | 0 ("bar":52) |
| **ließ** | "ließ":13, "ließ,":3, "ließ.":2 | "ließ":18 | "ließ":18 | "ließ":18 | 0 |

Conclusiones
-----------------------

A la luz de los datos, WhiteSpaceAnalyzer siempre genera muchos más tokens que los demás analizadores. SimpleAnalyzer y StopAnalyzer suelen generar un número parejo de tokens mientras que StandardAnalyzer se queda entre medias. Se puede observar que StandardAnalyzer es el que realiza un análisis más completo.  Llama la atención el comportamiento de los analizadores específicos de cada idioma, dado que eliminan tildes, trabajan como un stemmer en algunas palabras e ignoran caracteres propios de los idiomas (como  "ß" alemana).