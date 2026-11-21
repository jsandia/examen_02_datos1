package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.tec.examen2.datos.CargadorCSV;
import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.evacuacion.GuiaEvacuacion;
import cr.ac.tec.examen2.evacuacion.RutaEvacuacion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** RF-10 (F3): un pasillo bloqueado no se muestra como opción válida. */
class RF10PasilloBloqueadoTest {

    @Test
    void elPasilloBloqueadoSeCargaComoBloqueado() {
        GrafoMatriz g = DatosPrueba.grafo();
        assertTrue(g.estaBloqueado(g.indiceDe("Pasillo B"), g.indiceDe("Salida Este")));
    }

    @Test
    void rm103NoUsaPasilloBSalidaEste() {
        // Con Pasillo B -> Salida Este abierto, RM-103 tardaría 8 + 20 = 28 s.
        RutaEvacuacion ruta = new GuiaEvacuacion(DatosPrueba.grafo()).rutaDesde("RM-103");
        assertEquals(37, ruta.getSegundos());
        assertEquals("Salida Sur", ruta.getSalida());
        for (int i = 1; i < ruta.getCamino().length; i++) {
            boolean usaBloqueado = ruta.getCamino()[i - 1].equals("Pasillo B")
                    && ruta.getCamino()[i].equals("Salida Este");
            assertEquals(false, usaBloqueado);
        }
    }

    @Test
    void siSeDesbloqueaElPasilloLaRutaCambiaA28() {
        GrafoMatriz g = DatosPrueba.grafo();
        g.agregarPasillo("Pasillo B", "Salida Este", 20, false);
        RutaEvacuacion ruta = new GuiaEvacuacion(g).rutaDesde("RM-103");
        assertEquals(28, ruta.getSegundos());
        assertArrayEquals(new String[] {"RM-103", "Pasillo B", "Salida Este"}, ruta.getCamino());
    }

    @Test
    void limiteTodasLasSalidasBloqueadasNoHayRuta() {
        GrafoMatriz g = new GrafoMatriz();
        g.agregarPasillo("S-1", "Salida Norte", 5, true);
        g.agregarPasillo("S-1", "Salida Sur", 9, true);
        assertNull(new GuiaEvacuacion(g).rutaDesde("S-1"));
    }

    @Test
    void invalidoEstadoDesconocidoEnCsv(@TempDir Path dir) throws IOException {
        Path csv = dir.resolve("pasillos.csv");
        Files.writeString(csv, "desde,hacia,segundos,estado\nA,Salida Norte,5,Cerrado?\n");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CargadorCSV.cargarGrafo(csv.toString()));
        assertEquals("Línea 2: estado inválido 'Cerrado?'", e.getMessage());
    }
}
