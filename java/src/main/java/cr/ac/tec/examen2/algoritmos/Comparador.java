package cr.ac.tec.examen2.algoritmos;

/** Comparador propio (no se usa java.util.Comparator). Negativo si a va antes que b. */
@FunctionalInterface
public interface Comparador<T> {
    int comparar(T a, T b);
}
