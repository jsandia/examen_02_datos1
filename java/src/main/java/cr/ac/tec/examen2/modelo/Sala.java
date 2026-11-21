package cr.ac.tec.examen2.modelo;

/** Hoja del árbol n-ario: una sala con su cantidad actual de ocupantes. */
public class Sala {

    /** RNF-1: máximo de personas por sala. */
    public static final int MAX_OCUPANTES = 80;

    private final String codigo;
    private final String nombre;
    private final String ala;
    private int ocupantes;

    Sala(String codigo, String nombre, String ala, int ocupantes) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ala = ala;
        setOcupantes(ocupantes);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    /** Nombre del ala (nodo padre) a la que pertenece la sala. */
    public String getAla() {
        return ala;
    }

    public int getOcupantes() {
        return ocupantes;
    }

    final void setOcupantes(int ocupantes) {
        validarOcupantes(ocupantes);
        this.ocupantes = ocupantes;
    }

    static void validarOcupantes(int ocupantes) {
        if (ocupantes < 0 || ocupantes > MAX_OCUPANTES) {
            throw new IllegalArgumentException("Ocupantes fuera de rango (0-" + MAX_OCUPANTES + "): " + ocupantes);
        }
    }

    @Override
    public String toString() {
        return codigo + " (" + nombre + ", " + ala + "): " + ocupantes;
    }
}
