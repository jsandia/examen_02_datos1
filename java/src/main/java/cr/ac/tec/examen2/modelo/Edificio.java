package cr.ac.tec.examen2.modelo;

/**
 * Raíz del árbol n-ario edificio => ala => sala (RF-1).
 * Las búsquedas recorren el árbol en O(n).
 */
public class Edificio {

    private final String nombre;
    private Ala[] alas = new Ala[4];
    private int cantidad;

    public Edificio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // ---------- Construcción del árbol (RF-1) ----------

    public Ala agregarAla(String nombreAla) {
        validarTexto(nombreAla, "nombre de ala");
        if (buscarAla(nombreAla) != null) {
            throw new IllegalArgumentException("El ala ya existe: " + nombreAla);
        }
        if (cantidad == alas.length) {
            Ala[] mayor = new Ala[alas.length * 2];
            for (int i = 0; i < cantidad; i++) {
                mayor[i] = alas[i];
            }
            alas = mayor;
        }
        Ala ala = new Ala(nombreAla);
        alas[cantidad++] = ala;
        return ala;
    }

    public Sala agregarSala(String nombreAla, String codigo, String nombreSala, int ocupantes) {
        Ala ala = alaExistente(nombreAla);
        validarTexto(codigo, "código de sala");
        if (buscarSala(codigo) != null) {
            throw new IllegalArgumentException("La sala ya existe: " + codigo);
        }
        Sala sala = new Sala(codigo, nombreSala, nombreAla, ocupantes);
        ala.agregarSala(sala);
        return sala;
    }

    public Ala[] getAlas() {
        Ala[] copia = new Ala[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = alas[i];
        }
        return copia;
    }

    /** O(número de alas); null si no existe. */
    public Ala buscarAla(String nombreAla) {
        for (int i = 0; i < cantidad; i++) {
            if (alas[i].getNombre().equals(nombreAla)) {
                return alas[i];
            }
        }
        return null;
    }

    /** Recorre todo el árbol: O(n); null si no existe. */
    public Sala buscarSala(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            Sala sala = alas[i].buscarSala(codigo);
            if (sala != null) {
                return sala;
            }
        }
        return null;
    }

    /** Todas las salas del edificio en orden de ala y de inserción. */
    public Sala[] todasLasSalas() {
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += alas[i].cantidadSalas();
        }
        Sala[] resultado = new Sala[total];
        int k = 0;
        for (int i = 0; i < cantidad; i++) {
            Sala[] hijas = alas[i].getSalas();
            for (int j = 0; j < hijas.length; j++) {
                resultado[k++] = hijas[j];
            }
        }
        return resultado;
    }

    // ---------- Consultas (RF-2, RF-3, RF-5) ----------

    /** RF-2. */
    public int personasEnSala(String codigo) {
        return salaExistente(codigo).getOcupantes();
    }

    /** RF-3. */
    public Sala[] listarSalas(String nombreAla) {
        return alaExistente(nombreAla).getSalas();
    }

    /** RF-5. */
    public int personasEnAla(String nombreAla) {
        return alaExistente(nombreAla).totalPersonas();
    }

    /** RF-5. */
    public int personasEnEdificio() {
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += alas[i].totalPersonas();
        }
        return total;
    }

    // ---------- Actualización (RF-4, RNF-1) ----------

    /** Una persona entra a la sala; devuelve la nueva cantidad. */
    public int registrarEntrada(String codigo) {
        Sala sala = salaExistente(codigo);
        if (sala.getOcupantes() >= Sala.MAX_OCUPANTES) {
            throw new IllegalStateException("La sala " + codigo + " ya tiene el máximo de "
                    + Sala.MAX_OCUPANTES + " personas");
        }
        sala.setOcupantes(sala.getOcupantes() + 1);
        return sala.getOcupantes();
    }

    /** Una persona sale de la sala; devuelve la nueva cantidad. */
    public int registrarSalida(String codigo) {
        Sala sala = salaExistente(codigo);
        if (sala.getOcupantes() == 0) {
            throw new IllegalStateException("La sala " + codigo + " ya está vacía");
        }
        sala.setOcupantes(sala.getOcupantes() - 1);
        return sala.getOcupantes();
    }

    /** Fija la cantidad de ocupantes de una sala (0 a 80). */
    public void actualizarOcupantes(String codigo, int ocupantes) {
        salaExistente(codigo).setOcupantes(ocupantes);
    }

    // ---------- Auxiliares ----------

    private Ala alaExistente(String nombreAla) {
        Ala ala = buscarAla(nombreAla);
        if (ala == null) {
            throw new IllegalArgumentException("No existe el ala: " + nombreAla);
        }
        return ala;
    }

    private Sala salaExistente(String codigo) {
        Sala sala = codigo == null ? null : buscarSala(codigo);
        if (sala == null) {
            throw new IllegalArgumentException("No existe la sala: " + codigo);
        }
        return sala;
    }

    private static void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El " + campo + " no puede estar vacío");
        }
    }
}
