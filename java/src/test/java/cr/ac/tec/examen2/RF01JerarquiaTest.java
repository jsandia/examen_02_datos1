package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.modelo.Ala;
import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;
import org.junit.jupiter.api.Test;

/** RF-1 (F1): estructura jerárquica edificio => ala => sala (árbol n-ario). */
class RF01JerarquiaTest {

    @Test
    void edificioContieneLasTresAlasEnOrdenDeCarga() {
        Edificio edificio = DatosPrueba.edificio();
        Ala[] alas = edificio.getAlas();
        assertEquals(3, alas.length);
        assertEquals("Oeste", alas[0].getNombre());
        assertEquals("Central", alas[1].getNombre());
        assertEquals("Este", alas[2].getNombre());
        assertEquals("Edificio de Ciencias", edificio.getNombre());
    }

    @Test
    void cadaSalaCuelgaDeSuAla() {
        Edificio edificio = DatosPrueba.edificio();
        Sala sala = edificio.buscarSala("RM-104");
        assertEquals("Central", sala.getAla());
        assertEquals("Sala de seminarios", sala.getNombre());
        assertSame(sala, edificio.buscarAla("Central").buscarSala("RM-104"));
        assertArrayEquals(new String[] {"OFF-105", "LIB-001"},
                DatosPrueba.codigos(edificio.buscarAla("Este").getSalas()));
    }

    @Test
    void limiteEdificioVacioNoTieneAlas() {
        Edificio edificio = new Edificio("Vacío");
        assertEquals(0, edificio.getAlas().length);
        assertEquals(0, edificio.todasLasSalas().length);
    }

    @Test
    void limiteAlaSinSalas() {
        Edificio edificio = new Edificio("E");
        Ala ala = edificio.agregarAla("Sur");
        assertEquals(0, ala.cantidadSalas());
        assertEquals(1, edificio.getAlas().length);
    }

    @Test
    void invalidoSalaEnAlaInexistente() {
        Edificio edificio = new Edificio("E");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> edificio.agregarSala("Norte", "X-1", "Sala X", 5));
        assertEquals("No existe el ala: Norte", e.getMessage());
    }

    @Test
    void invalidoCodigoDeSalaDuplicado() {
        Edificio edificio = DatosPrueba.edificio();
        assertThrows(IllegalArgumentException.class,
                () -> edificio.agregarSala("Este", "LAB-101", "Copia", 1));
    }

    @Test
    void invalidoAlaDuplicadaYNombresVacios() {
        Edificio edificio = DatosPrueba.edificio();
        assertThrows(IllegalArgumentException.class, () -> edificio.agregarAla("Oeste"));
        assertThrows(IllegalArgumentException.class, () -> edificio.agregarAla(""));
        assertThrows(IllegalArgumentException.class, () -> edificio.agregarSala("Este", " ", "Sin código", 1));
    }

    @Test
    void buscarSalaOAlaInexistenteDevuelveNull() {
        Edificio edificio = DatosPrueba.edificio();
        assertNull(edificio.buscarSala("XYZ-999"));
        assertNull(edificio.buscarAla("Norte"));
    }
}
