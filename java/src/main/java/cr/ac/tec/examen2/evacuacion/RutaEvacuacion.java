package cr.ac.tec.examen2.evacuacion;

/** Ruta más rápida de una sala a una salida (RF-8) y su tiempo en segundos (RF-9). */
public class RutaEvacuacion {

    private final String sala;
    private final String salida;
    private final String[] camino;
    private final int segundos;

    public RutaEvacuacion(String sala, String salida, String[] camino, int segundos) {
        this.sala = sala;
        this.salida = salida;
        this.camino = camino;
        this.segundos = segundos;
    }

    public String getSala() {
        return sala;
    }

    public String getSalida() {
        return salida;
    }

    /** Copia del camino, desde la sala hasta la salida. */
    public String[] getCamino() {
        String[] copia = new String[camino.length];
        for (int i = 0; i < camino.length; i++) {
            copia[i] = camino[i];
        }
        return copia;
    }

    public int getSegundos() {
        return segundos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camino.length; i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(camino[i]);
        }
        return sala + ": " + salida + " en " + segundos + " s  [" + sb + "]";
    }
}
