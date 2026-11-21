package cr.ac.tec.examen2.algoritmos;

/**
 * Merge Sort propio, estable. O(n log n) en el peor caso, O(n) de memoria auxiliar.
 */
public final class MergeSort {

    private MergeSort() {
    }

    public static <T> void ordenar(T[] datos, Comparador<T> comparador) {
        if (datos == null || comparador == null) {
            throw new IllegalArgumentException("Los datos y el comparador no pueden ser nulos");
        }
        if (datos.length < 2) {
            return;
        }
        Object[] auxiliar = new Object[datos.length];
        ordenar(datos, auxiliar, 0, datos.length - 1, comparador);
    }

    private static <T> void ordenar(T[] datos, Object[] aux, int inicio, int fin, Comparador<T> c) {
        if (inicio >= fin) {
            return;
        }
        int medio = (inicio + fin) >>> 1;
        ordenar(datos, aux, inicio, medio, c);
        ordenar(datos, aux, medio + 1, fin, c);
        mezclar(datos, aux, inicio, medio, fin, c);
    }

    @SuppressWarnings("unchecked")
    private static <T> void mezclar(T[] datos, Object[] aux, int inicio, int medio, int fin, Comparador<T> c) {
        for (int k = inicio; k <= fin; k++) {
            aux[k] = datos[k];
        }
        int i = inicio;
        int j = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (i > medio) {
                datos[k] = (T) aux[j++];
            } else if (j > fin) {
                datos[k] = (T) aux[i++];
            } else if (c.comparar((T) aux[j], (T) aux[i]) < 0) {
                datos[k] = (T) aux[j++];
            } else {
                // "<=" a favor de la izquierda: mantiene la estabilidad
                datos[k] = (T) aux[i++];
            }
        }
    }
}
