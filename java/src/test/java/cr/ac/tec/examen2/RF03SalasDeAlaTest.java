package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;
import org.junit.jupiter.api.Test;

/** RF-3 (F1): ver todas las salas de un ala. */
class RF03SalasDeAlaTest {

    @Test
    void listaExactaDeCadaAla() {
        Edificio edificio = DatosPrueba.edificio();
        assertArrayEquals(new String[] {"LAB-101", "LAB-102"},
                DatosPrueba.codigos(edificio.listarSalas("Oeste")));
        assertArrayEquals(new String[] {"RM-103", "RM-104"},
                DatosPrueba.codigos(edificio.listarSalas("Central")));
        assertArrayEquals(new String[] {"OFF-105", "LIB-001"},
                DatosPrueba.codigos(edificio.listarSalas("Este")));
    }

    @Test
    void limiteAlaVaciaDevuelveArregloVacio() {
        Edificio edificio = DatosPrueba.edificio();
        edificio.agregarAla("Sótano");
        assertEquals(0, edificio.listarSalas("Sótano").length);
    }

    @Test
    void laListaEsUnaCopiaQueNoAlteraElAla() {
        Edificio edificio = DatosPrueba.edificio();
        Sala[] salas = edificio.listarSalas("Oeste");
        salas[0] = null;
        assertEquals("LAB-101", edificio.listarSalas("Oeste")[0].getCodigo());
    }

    @Test
    void limiteAlaConMuchasSalasCreceDinamicamente() {
        Edificio edificio = new Edificio("E");
        edificio.agregarAla("Grande");
        for (int i = 0; i < 50; i++) {
            edificio.agregarSala("Grande", "S-" + i, "Sala " + i, 1);
        }
        Sala[] salas = edificio.listarSalas("Grande");
        assertEquals(50, salas.length);
        assertEquals("S-49", salas[49].getCodigo());
    }

    @Test
    void invalidoAlaInexistente() {
        Edificio edificio = DatosPrueba.edificio();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> edificio.listarSalas("Norte"));
        assertEquals("No existe el ala: Norte", e.getMessage());
    }
}
