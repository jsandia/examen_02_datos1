package cr.ac.tec.examen2.estructuras;

/**
 * Montículo binario de mínimos (cola de prioridad propia) sobre arreglos.
 * Guarda pares (nodo, prioridad). Insertar y extraer: O(log n).
 */
public class MonticuloMinimo {

    private int[] nodos = new int[16];
    private int[] prioridades = new int[16];
    private int tamano;

    public boolean estaVacio() {
        return tamano == 0;
    }

    public int tamano() {
        return tamano;
    }

    public void insertar(int nodo, int prioridad) {
        if (tamano == nodos.length) {
            crecer();
        }
        nodos[tamano] = nodo;
        prioridades[tamano] = prioridad;
        subir(tamano);
        tamano++;
    }

    /** Extrae el par de menor prioridad: {nodo, prioridad}. */
    public int[] extraerMinimo() {
        if (tamano == 0) {
            throw new IllegalStateException("El montículo está vacío");
        }
        int[] minimo = {nodos[0], prioridades[0]};
        tamano--;
        nodos[0] = nodos[tamano];
        prioridades[0] = prioridades[tamano];
        bajar(0);
        return minimo;
    }

    private void subir(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (prioridades[i] >= prioridades[padre]) {
                return;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    private void bajar(int i) {
        while (true) {
            int izq = 2 * i + 1;
            int der = izq + 1;
            int menor = i;
            if (izq < tamano && prioridades[izq] < prioridades[menor]) {
                menor = izq;
            }
            if (der < tamano && prioridades[der] < prioridades[menor]) {
                menor = der;
            }
            if (menor == i) {
                return;
            }
            intercambiar(i, menor);
            i = menor;
        }
    }

    private void intercambiar(int a, int b) {
        int n = nodos[a];
        nodos[a] = nodos[b];
        nodos[b] = n;
        int p = prioridades[a];
        prioridades[a] = prioridades[b];
        prioridades[b] = p;
    }

    private void crecer() {
        int[] n = new int[nodos.length * 2];
        int[] p = new int[nodos.length * 2];
        for (int i = 0; i < tamano; i++) {
            n[i] = nodos[i];
            p[i] = prioridades[i];
        }
        nodos = n;
        prioridades = p;
    }
}
