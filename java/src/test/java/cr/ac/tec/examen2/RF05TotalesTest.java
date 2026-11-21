package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.modelo.Edificio;
import org.junit.jupiter.api.Test;

/** RF-5 (F1): cantidad total de personas por ala y en el edificio. */
class RF05TotalesTest {

    @Test
    void totalesExactosPorAla() {
        Edificio edificio = DatosPrueba.edificio();
        assertEquals(54, edificio.personasEnAla("Oeste"));
        assertEquals(75, edificio.personasEnAla("Central"));
        assertEquals(51, edificio.personasEnAla("Este"));
    }

    @Test
    void totalExactoDelEdificio() {
        assertEquals(180, DatosPrueba.edificio().personasEnEdificio());
    }

    @Test
    void limiteAlaVaciaYEdificioVacio() {
        Edificio edificio = new Edificio("E");
        assertEquals(0, edificio.personasEnEdificio());
        edificio.agregarAla("Vacía");
        assertEquals(0, edificio.personasEnAla("Vacía"));
        assertEquals(0, edificio.personasEnEdificio());
    }

    @Test
    void invalidoAlaInexistente() {
        Edificio edificio = DatosPrueba.edificio();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> edificio.personasEnAla("Norte"));
        assertEquals("No existe el ala: Norte", e.getMessage());
    }
}
