# Registro de prompts

- Integrantes: José Sandí Arrieta, Johan Jiménez Corella
- Herramienta(s) de IA utilizada(s): Claude Code (aplicación de escritorio Claude, pestaña Code), modelo Claude Opus 5.5
- Nota: el profesor autorizó entregar a la IA el enunciado completo del examen (PDF).

Los prompts aparecen en orden y de forma textual.

## Prompt 1

> Adjunto: `Examen_II_-_II-2026.pdf` (enunciado completo del examen)
>
> estas son las isntrucciones de mi examen, leelas profundamente, ten en cuenta las restricciones planteadas
> ya te voy a pasar los requerimientos que recolectamos.
> te vuelvo a recordar que restricciones son muy importantes

Restricciones contenidas en el PDF adjunto e indicadas a la IA en este prompt (transcripción textual de la Parte 2 del enunciado):

> - Se puede usar Java 17 o superior, o C#. La solución debe implementar sus propias estructuras de datos y algoritmos, con estas restricciones:
>   - Permitido: arreglos (por ejemplo int[] o Parada[]) y String.
>   - Prohibido en Java: cualquier colección de java.util (ArrayList, LinkedList, HashMap, HashSet, TreeMap, TreeSet, PriorityQueue, Stack, ArrayDeque, entre otras), así como Arrays.sort, Collections.sort, List.sort y sorted() de Streams.
>   - Prohibido en C#: List, LinkedList, Dictionary, HashSet, SortedDictionary, SortedSet, SortedList, PriorityQueue, Queue y Stack, así como Array.Sort, List.Sort y los ordenamientos de LINQ (OrderBy, ThenBy).
>   - Prohibido en ambos lenguajes: bibliotecas que calculen caminos más cortos o que implementen grafos.
>   - Las pruebas unitarias pueden usar, además, la biblioteca de pruebas (JUnit o NUnit).
> - Se le debe indicar a la IA que no sugiera mejoras ni cambios a los requerimientos.

## Prompt 2

> el profe dijo que podiamos subirte el examen completo
> vamos a usar java 26
> tenemos el problema B
> este es el link del git https://github.com/jsandia/examen_02_datos1
> José Sandí Arrieta
> Johan Jiménez Corella

## Prompt 3

> estan es nuestra respuesta para la parte 1 diseño
> Examen 02 - Algoritmos y estructuras de datos I
> José Sandí Arrieta
> Johan Jiménez Corella
> Caso B.
> F1
>
> *   Árbol n-ario
> *   O(n) en búsqueda.
> *   Descartado AVL porque no se usan rangos.
>
>
> F2
>
> *   Merge Sort
> *   O(n log n)
> *   Descartado Insertion porque no es estable. (en caso de empate)
>
> F3
>
> *   Grafo con pesos, no dirigido usando Dijkstra y matriz de adyacencia
> *   O((V+E) log n)
> *   Descartado BFS porque se necesita el menor tiempo posible.
>
>
> Requerimientos funcionales :
> RF - 1 (F1): el sistema tiene una estructura jerárquica que sigue el órden:
>  edificio => ala => sala
>
> RF-2 (F1): El sistema puede ver la cantidad
> de personas por sala
>
> RF-3(F1): El sistema puede ver todas las salas de en ala
>
> RF-4 (F1): El sistema se actualiza cuando una persona entra o sale de una sala.
>
> RF-5 (F1): El sistema muestra la cantidad total de personas en ala y edificio.
>
> RF-6 (F2) El sistema muestra el total de las salas ordenadas de mayor a menor según la cantidad de personas en cada sala.
>
> RF -7 (F2) En caso de que varias salas tengan la misma cantidad de personas la siquiente llave de ordenamiento es de A a Z usando su código.
>
> RF -8 (F3): Desde cada sala se indica la
> ruta más rápida a seguir hacia cualquier salida
>
> RF - 9 (F3): El sistema indica cuarto tiempo tarda en segundos la ruta
>
> RF-10 (F3): En caso de que en pasillo este bloqueado no lo muestra como opcion válida.
>
> Requerimientos no funcionales:
>
> RNF - 1:  Máximo 80 personas por sala
>
> RNF - 2: Cada RF tiene una prueba unitaria

## Prompt 4

> (Respuesta a la pregunta de la IA "El repositorio jsandia/examen_02_datos1 está vacío… ¿Cómo seguimos?": opción seleccionada "Te paso el repo base")
>
> https://github.com/ellioth/CE1103-Exame2Base

## Prompt 5

> Sí, commit y push
