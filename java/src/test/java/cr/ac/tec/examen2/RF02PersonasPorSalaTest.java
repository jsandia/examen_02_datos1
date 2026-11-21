package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.modelo.Edificio;
import org.junit.jupiter.api.Test;

/** RF-2 (F1): ver la cantidad de personas por sala. */
class RF02PersonasPorSalaTest {

    @Test
    void valoresExactosDelCsv() {
        Edificio edificio = DatosPrueba.edificio();
        assertEquals(24, edificio.personasEnSala("LAB-101"));
        assertEquals(30, edificio.personasEnSala("LAB-102"));
        assertEquals(45, edificio.personasEnSala("RM-103"));
        assertEquals(30, edificio.personasEnSala("RM-104"));
        assertEquals(6, edificio.personasEnSala("OFF-105"));
        assertEquals(45, edificio.personasEnSala("LIB-001"));
    }

    @Test
    void limiteSalaVacia() {
        Edificio edificio = new Edificio("E");
        edificio.agregarAla("Oeste");
        edificio.agregarSala("Oeste", "S-0", "Sala vacía", 0);
        assertEquals(0, edificio.personasEnSala("S-0"));
    }

    @Test
    void invalidoSalaInexistente() {
        Edificio edificio = DatosPrueba.edificio();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> edificio.personasEnSala("LAB-999"));
        assertEquals("No existe la sala: LAB-999", e.getMessage());
    }

    @Test
    void invalidoCodigoNulo() {
        Edificio edificio = DatosPrueba.edificio();
        assertThrows(IllegalArgumentException.class, () -> edificio.personasEnSala(null));
    }
}
