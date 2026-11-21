package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.evacuacion.GuiaEvacuacion;
import org.junit.jupiter.api.Test;

/** RF-9 (F3): el sistema indica cuánto tarda la ruta en segundos. */
class RF09TiempoRutaTest {

    private final GuiaEvacuacion guia = new GuiaEvacuacion(DatosPrueba.grafo());

    @Test
    void segundosExactosDeLasSeisSalas() {
        assertEquals(30, guia.rutaDesde("LAB-101").getSegundos());
        assertEquals(35, guia.rutaDesde("LAB-102").getSegundos());
        assertEquals(37, guia.rutaDesde("RM-103").getSegundos());
        assertEquals(27, guia.rutaDesde("RM-104").getSegundos());
        assertEquals(25, guia.rutaDesde("OFF-105").getSegundos());
        assertEquals(25, guia.rutaDesde("LIB-001").getSegundos());
    }

    @Test
    void limiteRutaDirectaContraRutaLargaMasRapida() {
        GrafoMatriz g = new GrafoMatriz();
        g.agregarPasillo("S", "Salida Norte", 50, false);
        g.agregarPasillo("S", "P", 10, false);
        g.agregarPasillo("P", "Salida Norte", 10, false);
        assertEquals(20, new GuiaEvacuacion(g).rutaDesde("S").getSegundos());
    }

    @Test
    void invalidoTiempoCeroONegativo() {
        GrafoMatriz g = new GrafoMatriz();
        assertThrows(IllegalArgumentException.class, () -> g.agregarPasillo("A", "B", 0, false));
        assertThrows(IllegalArgumentException.class, () -> g.agregarPasillo("A", "B", -5, false));
    }

    @Test
    void invalidoPasilloHaciaSiMismo() {
        GrafoMatriz g = new GrafoMatriz();
        assertThrows(IllegalArgumentException.class, () -> g.agregarPasillo("A", "A", 3, false));
    }
}
