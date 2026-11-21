package cr.ac.tec.examen2.reporte;

import cr.ac.tec.examen2.algoritmos.MergeSort;
import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;

/** F2: reporte para la brigada (RF-6 y RF-7). */
public final class ReporteBrigada {

    private ReporteBrigada() {
    }

    /** Salas de mayor a menor ocupación; en empate, código de la A a la Z. O(n log n). */
    public static Sala[] generar(Edificio edificio) {
        if (edificio == null) {
            throw new IllegalArgumentException("El edificio no puede ser nulo");
        }
        Sala[] salas = edificio.todasLasSalas();
        MergeSort.ordenar(salas, ReporteBrigada::comparar);
        return salas;
    }

    static int comparar(Sala a, Sala b) {
        if (a.getOcupantes() != b.getOcupantes()) {
            return b.getOcupantes() - a.getOcupantes(); // RF-6: descendente
        }
        return a.getCodigo().compareTo(b.getCodigo()); // RF-7: A a Z
    }
}
