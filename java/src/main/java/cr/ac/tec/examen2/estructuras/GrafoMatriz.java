package cr.ac.tec.examen2.estructuras;

/**
 * Grafo con pesos, no dirigido, representado con matriz de adyacencia (F3).
 * Cada pasillo guarda sus segundos y si está bloqueado.
 */
public class GrafoMatriz {

    public static final int SIN_ARISTA = 0;

    private String[] nombres;
    private int[][] pesos;
    private boolean[][] bloqueados;
    private int cantidad;

    public GrafoMatriz() {
        this(8);
    }

    public GrafoMatriz(int capacidadInicial) {
        int capacidad = Math.max(1, capacidadInicial);
        nombres = new String[capacidad];
        pesos = new int[capacidad][capacidad];
        bloqueados = new boolean[capacidad][capacidad];
    }

    /** Agrega un nodo (sala, pasillo o salida) y devuelve su índice; si ya existe, devuelve el existente. */
    public int agregarNodo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del nodo no puede estar vacío");
        }
        int existente = indiceDe(nombre);
        if (existente != -1) {
            return existente;
        }
        if (cantidad == nombres.length) {
            crecer();
        }
        nombres[cantidad] = nombre;
        return cantidad++;
    }

    /** Agrega (o reemplaza) un pasillo en ambos sentidos. */
    public void agregarPasillo(String desde, String hacia, int segundos, boolean bloqueado) {
        if (segundos <= 0) {
            throw new IllegalArgumentException("Los segundos deben ser positivos: " + segundos);
        }
        if (desde != null && desde.equals(hacia)) {
            throw new IllegalArgumentException("Un pasillo no puede unir un nodo consigo mismo: " + desde);
        }
        int a = agregarNodo(desde);
        int b = agregarNodo(hacia);
        pesos[a][b] = segundos;
        pesos[b][a] = segundos;
        bloqueados[a][b] = bloqueado;
        bloqueados[b][a] = bloqueado;
    }

    /** Búsqueda lineal O(V); -1 si no existe. */
    public int indiceDe(String nombre) {
        for (int i = 0; i < cantidad; i++) {
            if (nombres[i].equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public String getNombre(int indice) {
        return nombres[indice];
    }

    public int cantidadNodos() {
        return cantidad;
    }

    /** Segundos del pasillo entre i y j, o SIN_ARISTA (0) si no hay pasillo. */
    public int getPeso(int i, int j) {
        return pesos[i][j];
    }

    public boolean estaBloqueado(int i, int j) {
        return bloqueados[i][j];
    }

    /** RF-10: un pasillo es transitable solo si existe y no está bloqueado. */
    public boolean esTransitable(int i, int j) {
        return pesos[i][j] != SIN_ARISTA && !bloqueados[i][j];
    }

    private void crecer() {
        int nueva = nombres.length * 2;
        String[] n = new String[nueva];
        int[][] p = new int[nueva][nueva];
        boolean[][] b = new boolean[nueva][nueva];
        for (int i = 0; i < cantidad; i++) {
            n[i] = nombres[i];
            for (int j = 0; j < cantidad; j++) {
                p[i][j] = pesos[i][j];
                b[i][j] = bloqueados[i][j];
            }
        }
        nombres = n;
        pesos = p;
        bloqueados = b;
    }
}
