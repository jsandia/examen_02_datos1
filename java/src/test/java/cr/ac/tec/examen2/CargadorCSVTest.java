package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.datos.CargadorCSV;
import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Lectura de datos/ usada por RF-1 a RF-10: datos mal formados. */
class CargadorCSVTest {

    @Test
    void grafoTieneLosNodosYPasillosDelCsv() {
        GrafoMatriz g = DatosPrueba.grafo();
        assertEquals(12, g.cantidadNodos());
        assertEquals(25, g.getPeso(g.indiceDe("LIB-001"), g.indiceDe("Salida Este")));
        // no dirigido
        assertEquals(25, g.getPeso(g.indiceDe("Salida Este"), g.indiceDe("LIB-001")));
        assertEquals(-1, g.indiceDe("Pasillo Z"));
    }

    @Test
    void invalidoArchivoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> CargadorCSV.cargarEdificio("no/existe.csv", "E"));
        assertThrows(IllegalArgumentException.class, () -> CargadorCSV.cargarGrafo("no/existe.csv"));
    }

    @Test
    void invalidoColumnasFaltantes(@TempDir Path dir) throws IOException {
        Path csv = dir.resolve("ocupantes.csv");
        Files.writeString(csv, "codigo,nombre,ala,ocupantes\nS-1,Aula,Oeste\n");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CargadorCSV.cargarEdificio(csv.toString(), "E"));
        assertEquals("Línea 2: se esperaban 4 columnas y hay 3", e.getMessage());
    }

    @Test
    void invalidoNumeroMalFormado(@TempDir Path dir) throws IOException {
        Path csv = dir.resolve("pasillos.csv");
        Files.writeString(csv, "desde,hacia,segundos,estado\nA,B,diez,Abierto\n");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CargadorCSV.cargarGrafo(csv.toString()));
        assertEquals("Línea 2: número inválido 'diez'", e.getMessage());
    }

    @Test
    void limiteArchivoSoloConEncabezadoYLineasVacias(@TempDir Path dir) throws IOException {
        Path csv = dir.resolve("ocupantes.csv");
        Files.writeString(csv, "codigo,nombre,ala,ocupantes\n\n");
        assertEquals(0, CargadorCSV.cargarEdificio(csv.toString(), "E").personasEnEdificio());
    }
}
