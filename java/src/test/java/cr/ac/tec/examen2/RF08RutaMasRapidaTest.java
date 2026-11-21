package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.evacuacion.GuiaEvacuacion;
import cr.ac.tec.examen2.evacuacion.RutaEvacuacion;
import org.junit.jupiter.api.Test;

/** RF-8 (F3): desde cada sala se indica la ruta más rápida hacia cualquier salida (Dijkstra). */
class RF08RutaMasRapidaTest {

    private final GuiaEvacuacion guia = new GuiaEvacuacion(DatosPrueba.grafo());

    @Test
    void rutasExactasDeLasSeisSalas() {
        assertRuta("LAB-101", "Salida Norte", "LAB-101", "Pasillo A", "Salida Norte");
        assertRuta("LAB-102", "Salida Norte", "LAB-102", "Pasillo A", "Salida Norte");
        assertRuta("RM-103", "Salida Sur", "RM-103", "RM-104", "Pasillo C", "Salida Sur");
        assertRuta("RM-104", "Salida Sur", "RM-104", "Pasillo C", "Salida Sur");
        assertRuta("OFF-105", "Salida Sur", "OFF-105", "Pasillo C", "Salida Sur");
        assertRuta("LIB-001", "Salida Este", "LIB-001", "Salida Este");
    }

    @Test
    void guiaCompletaEnElOrdenDelEdificio() {
        RutaEvacuacion[] rutas = guia.guiaCompleta(DatosPrueba.edificio());
        assertEquals(6, rutas.length);
        assertEquals("LAB-101", rutas[0].getSala());
        assertEquals("Salida Este", rutas[5].getSalida());
    }

    @Test
    void limiteOrigenIgualADestino() {
        RutaEvacuacion ruta = guia.rutaDesde("Salida Norte");
        assertEquals("Salida Norte", ruta.getSalida());
        assertArrayEquals(new String[] {"Salida Norte"}, ruta.getCamino());
        assertEquals(0, ruta.getSegundos());
    }

    @Test
    void limiteEmpateEntreSalidasEligeLaPrimeraAlfabeticamente() {
        GrafoMatriz g = new GrafoMatriz();
        g.agregarPasillo("S-1", "Salida Sur", 10, false);
        g.agregarPasillo("S-1", "Salida Este", 10, false);
        RutaEvacuacion ruta = new GuiaEvacuacion(g).rutaDesde("S-1");
        assertEquals("Salida Este", ruta.getSalida());
        assertEquals(10, ruta.getSegundos());
    }

    @Test
    void limiteSalaAisladaNoTieneRuta() {
        GrafoMatriz g = new GrafoMatriz();
        g.agregarNodo("Aislada");
        g.agregarPasillo("X", "Salida Norte", 5, false);
        assertNull(new GuiaEvacuacion(g).rutaDesde("Aislada"));
    }

    @Test
    void invalidoSalaInexistente() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> guia.rutaDesde("LAB-999"));
        assertEquals("No existe la sala en el plano: LAB-999", e.getMessage());
        assertThrows(IllegalArgumentException.class, () -> new GuiaEvacuacion(null));
    }

    private void assertRuta(String sala, String salida, String... camino) {
        RutaEvacuacion ruta = guia.rutaDesde(sala);
        assertEquals(sala, ruta.getSala());
        assertEquals(salida, ruta.getSalida());
        assertArrayEquals(camino, ruta.getCamino());
    }
}
