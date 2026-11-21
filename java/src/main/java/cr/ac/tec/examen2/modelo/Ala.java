package cr.ac.tec.examen2.modelo;

/** Nodo intermedio del árbol n-ario: un ala con sus salas hijas (arreglo dinámico propio). */
public class Ala {

    private final String nombre;
    private Sala[] salas = new Sala[4];
    private int cantidad;

    Ala(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    void agregarSala(Sala sala) {
        if (cantidad == salas.length) {
            Sala[] mayor = new Sala[salas.length * 2];
            for (int i = 0; i < cantidad; i++) {
                mayor[i] = salas[i];
            }
            salas = mayor;
        }
        salas[cantidad++] = sala;
    }

    /** Búsqueda lineal O(n) entre las salas hijas; null si no existe. */
    public Sala buscarSala(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (salas[i].getCodigo().equals(codigo)) {
                return salas[i];
            }
        }
        return null;
    }

    /** Copia de las salas del ala, en orden de inserción. */
    public Sala[] getSalas() {
        Sala[] copia = new Sala[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = salas[i];
        }
        return copia;
    }

    public int cantidadSalas() {
        return cantidad;
    }

    /** Suma de ocupantes de todas las salas hijas: O(n). */
    public int totalPersonas() {
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += salas[i].getOcupantes();
        }
        return total;
    }
}
