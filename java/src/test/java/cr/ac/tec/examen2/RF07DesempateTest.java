package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.reporte.ReporteBrigada;
import org.junit.jupiter.api.Test;

/** RF-7 (F2): en empate de personas, se ordena por código de la A a la Z. */
class RF07DesempateTest {

    @Test
    void empatesExactosDelCsv() {
        String[] codigos = DatosPrueba.codigos(ReporteBrigada.generar(DatosPrueba.edificio()));
        // 45: LIB-001 antes que RM-103; 30: LAB-102 antes que RM-104
        assertArrayEquals(new String[] {"LIB-001", "RM-103"}, new String[] {codigos[0], codigos[1]});
        assertArrayEquals(new String[] {"LAB-102", "RM-104"}, new String[] {codigos[2], codigos[3]});
    }

    @Test
    void limiteTodasEmpatadasQuedanAlfabeticas() {
        Edificio edificio = new Edificio("E");
        edificio.agregarAla("A");
        edificio.agregarSala("A", "Z-9", "z", 10);
        edificio.agregarSala("A", "B-2", "b", 10);
        edificio.agregarSala("A", "M-5", "m", 10);
        edificio.agregarSala("A", "A-1", "a", 10);
        assertArrayEquals(new String[] {"A-1", "B-2", "M-5", "Z-9"},
                DatosPrueba.codigos(ReporteBrigada.generar(edificio)));
    }

    @Test
    void limiteMayorOcupacionGanaAunqueSuCodigoSeaMayor() {
        Edificio edificio = new Edificio("E");
        edificio.agregarAla("A");
        edificio.agregarSala("A", "A-1", "a", 1);
        edificio.agregarSala("A", "Z-9", "z", 2);
        assertArrayEquals(new String[] {"Z-9", "A-1"}, DatosPrueba.codigos(ReporteBrigada.generar(edificio)));
    }

    @Test
    void invalidoEdificioNulo() {
        assertThrows(IllegalArgumentException.class, () -> ReporteBrigada.generar(null));
    }
}
