package cr.ac.tec.examen2.evacuacion;

import cr.ac.tec.examen2.algoritmos.Dijkstra;
import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;

/**
 * F3: guía de evacuación (RF-8, RF-9, RF-10).
 * Las salidas son los nodos cuyo nombre empieza con "Salida".
 */
public class GuiaEvacuacion {

    public static final String PREFIJO_SALIDA = "Salida";

    private final GrafoMatriz grafo;

    public GuiaEvacuacion(GrafoMatriz grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        this.grafo = grafo;
    }

    /**
     * Ruta más rápida desde el nodo indicado hasta la salida más cercana.
     * En empate de tiempo se elige la salida de nombre menor (A a Z).
     * Devuelve null si ninguna salida es alcanzable sin pasillos bloqueados.
     */
    public RutaEvacuacion rutaDesde(String sala) {
        int origen = grafo.indiceDe(sala);
        if (origen == -1) {
            throw new IllegalArgumentException("No existe la sala en el plano: " + sala);
        }
        Dijkstra.Resultado r = Dijkstra.calcular(grafo, origen);

        int mejor = -1;
        for (int i = 0; i < grafo.cantidadNodos(); i++) {
            if (!grafo.getNombre(i).startsWith(PREFIJO_SALIDA) || r.distancia(i) == Dijkstra.INFINITO) {
                continue;
            }
            if (mejor == -1 || r.distancia(i) < r.distancia(mejor)
                    || (r.distancia(i) == r.distancia(mejor)
                        && grafo.getNombre(i).compareTo(grafo.getNombre(mejor)) < 0)) {
                mejor = i;
            }
        }
        if (mejor == -1) {
            return null;
        }
        return new RutaEvacuacion(sala, grafo.getNombre(mejor), reconstruir(r, mejor), r.distancia(mejor));
    }

    /** Guía para todas las salas del edificio, en el orden del edificio. */
    public RutaEvacuacion[] guiaCompleta(Edificio edificio) {
        Sala[] salas = edificio.todasLasSalas();
        RutaEvacuacion[] rutas = new RutaEvacuacion[salas.length];
        for (int i = 0; i < salas.length; i++) {
            rutas[i] = rutaDesde(salas[i].getCodigo());
        }
        return rutas;
    }

    private String[] reconstruir(Dijkstra.Resultado r, int destino) {
        int largo = 0;
        for (int v = destino; v != -1; v = r.previo(v)) {
            largo++;
        }
        String[] camino = new String[largo];
        int k = largo - 1;
        for (int v = destino; v != -1; v = r.previo(v)) {
            camino[k--] = grafo.getNombre(v);
        }
        return camino;
    }
}
