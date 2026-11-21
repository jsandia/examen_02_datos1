package cr.ac.tec.examen2.algoritmos;

import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.estructuras.MonticuloMinimo;

/**
 * Dijkstra propio con montículo de mínimos sobre la matriz de adyacencia.
 * Ignora los pasillos bloqueados (RF-10).
 */
public final class Dijkstra {

    public static final int INFINITO = Integer.MAX_VALUE;

    private Dijkstra() {
    }

    /** Resultado: distancia mínima a cada nodo y el nodo previo en el camino (-1 si no hay). */
    public static final class Resultado {
        private final int[] distancias;
        private final int[] previos;

        Resultado(int[] distancias, int[] previos) {
            this.distancias = distancias;
            this.previos = previos;
        }

        public int distancia(int nodo) {
            return distancias[nodo];
        }

        public int previo(int nodo) {
            return previos[nodo];
        }
    }

    public static Resultado calcular(GrafoMatriz grafo, int origen) {
        int n = grafo.cantidadNodos();
        if (origen < 0 || origen >= n) {
            throw new IllegalArgumentException("Origen inválido: " + origen);
        }
        int[] dist = new int[n];
        int[] previo = new int[n];
        boolean[] visitado = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = INFINITO;
            previo[i] = -1;
        }
        dist[origen] = 0;
        MonticuloMinimo cola = new MonticuloMinimo();
        cola.insertar(origen, 0);

        while (!cola.estaVacio()) {
            int[] par = cola.extraerMinimo();
            int u = par[0];
            if (visitado[u]) {
                continue; // entrada vieja (inserción perezosa)
            }
            visitado[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visitado[v] && grafo.esTransitable(u, v)) {
                    int nueva = dist[u] + grafo.getPeso(u, v);
                    if (nueva < dist[v]) {
                        dist[v] = nueva;
                        previo[v] = u;
                        cola.insertar(v, nueva);
                    }
                }
            }
        }
        return new Resultado(dist, previo);
    }
}
