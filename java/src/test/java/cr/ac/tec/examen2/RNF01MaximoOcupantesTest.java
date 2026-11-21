package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.datos.CargadorCSV;
import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** RNF-1: máximo 80 personas por sala. */
class RNF01MaximoOcupantesTest {

    @Test
    void elLimiteEs80() {
        assertEquals(80, Sala.MAX_OCUPANTES);
    }

    @Test
    void limite80SeAceptaYLaEntrada81SeRechaza() {
        Edificio edificio = DatosPrueba.edificio();
        edificio.actualizarOcupantes("LAB-101", 80);
        assertEquals(80, edificio.personasEnSala("LAB-101"));
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> edificio.registrarEntrada("LAB-101"));
        assertEquals("La sala LAB-101 ya tiene el máximo de 80 personas", e.getMessage());
        assertEquals(80, edificio.personasEnSala("LAB-101"));
    }

    @Test
    void invalidoActualizarA81() {
        Edificio edificio = DatosPrueba.edificio();
        assertThrows(IllegalArgumentException.class, () -> edificio.actualizarOcupantes("LAB-101", 81));
        assertEquals(24, edificio.personasEnSala("LAB-101"));
    }

    @Test
    void invalidoCrearSalaCon81() {
        Edificio edificio = new Edificio("E");
        edificio.agregarAla("A");
        assertThrows(IllegalArgumentException.class, () -> edificio.agregarSala("A", "S-1", "s", 81));
    }

    @Test
    void invalidoCsvConMasDe80(@TempDir Path dir) throws IOException {
        Path csv = dir.resolve("ocupantes.csv");
        Files.writeString(csv, "codigo,nombre,ala,ocupantes\nS-1,Aula,Oeste,95\n");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CargadorCSV.cargarEdificio(csv.toString(), "E"));
        assertEquals("Línea 2: Ocupantes fuera de rango (0-80): 95", e.getMessage());
    }
}
