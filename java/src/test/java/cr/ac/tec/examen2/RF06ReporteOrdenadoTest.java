package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.algoritmos.MergeSort;
import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;
import cr.ac.tec.examen2.reporte.ReporteBrigada;
import org.junit.jupiter.api.Test;

/** RF-6 (F2): salas ordenadas de mayor a menor cantidad de personas (Merge Sort). */
class RF06ReporteOrdenadoTest {

    @Test
    void ordenExactoDelReporte() {
        Sala[] reporte = ReporteBrigada.generar(DatosPrueba.edificio());
        assertArrayEquals(new String[] {"LIB-001", "RM-103", "LAB-102", "RM-104", "LAB-101", "OFF-105"},
                DatosPrueba.codigos(reporte));
        int[] ocupantes = new int[reporte.length];
        for (int i = 0; i < reporte.length; i++) {
            ocupantes[i] = reporte[i].getOcupantes();
        }
        assertArrayEquals(new int[] {45, 45, 30, 30, 24, 6}, ocupantes);
    }

    @Test
    void elReporteReflejaActualizaciones() {
        Edificio edificio = DatosPrueba.edificio();
        edificio.actualizarOcupantes("OFF-105", 80);
        assertEquals("OFF-105", ReporteBrigada.generar(edificio)[0].getCodigo());
    }

    @Test
    void limiteEdificioVacioYUnaSola() {
        Edificio edificio = new Edificio("E");
        assertEquals(0, ReporteBrigada.generar(edificio).length);
        edificio.agregarAla("A");
        edificio.agregarSala("A", "S-1", "Única", 3);
        assertArrayEquals(new String[] {"S-1"}, DatosPrueba.codigos(ReporteBrigada.generar(edificio)));
    }

    @Test
    void limiteCuatrocientasSalasQuedanOrdenadas() {
        Edificio edificio = new Edificio("Campus");
        edificio.agregarAla("A");
        for (int i = 0; i < 400; i++) {
            edificio.agregarSala("A", String.format("S-%03d", i), "Sala", (i * 37) % 81);
        }
        Sala[] reporte = ReporteBrigada.generar(edificio);
        assertEquals(400, reporte.length);
        for (int i = 1; i < reporte.length; i++) {
            assertEquals(true, reporte[i - 1].getOcupantes() >= reporte[i].getOcupantes());
        }
    }

    @Test
    void mergeSortOrdenaEnterosConComparadorPropio() {
        Integer[] datos = {5, 1, 4, 1, 3};
        MergeSort.ordenar(datos, (a, b) -> a - b);
        assertArrayEquals(new Integer[] {1, 1, 3, 4, 5}, datos);
    }

    @Test
    void invalidoEdificioNulo() {
        assertThrows(IllegalArgumentException.class, () -> ReporteBrigada.generar(null));
        assertThrows(IllegalArgumentException.class, () -> MergeSort.ordenar(null, (Integer a, Integer b) -> 0));
    }
}
