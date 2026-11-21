package cr.ac.tec.examen2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cr.ac.tec.examen2.modelo.Edificio;
import org.junit.jupiter.api.Test;

/** RF-4 (F1): el sistema se actualiza cuando una persona entra o sale de una sala. */
class RF04ActualizacionTest {

    @Test
    void entradaSumaUnaPersonaExacta() {
        Edificio edificio = DatosPrueba.edificio();
        assertEquals(25, edificio.registrarEntrada("LAB-101"));
        assertEquals(25, edificio.personasEnSala("LAB-101"));
        assertEquals(55, edificio.personasEnAla("Oeste"));
        assertEquals(181, edificio.personasEnEdificio());
    }

    @Test
    void salidaRestaUnaPersonaExacta() {
        Edificio edificio = DatosPrueba.edificio();
        assertEquals(5, edificio.registrarSalida("OFF-105"));
        assertEquals(50, edificio.personasEnAla("Este"));
        assertEquals(179, edificio.personasEnEdificio());
    }

    @Test
    void actualizarOcupantesAUnValorExacto() {
        Edificio edificio = DatosPrueba.edificio();
        edificio.actualizarOcupantes("RM-104", 12);
        assertEquals(12, edificio.personasEnSala("RM-104"));
        assertEquals(57, edificio.personasEnAla("Central"));
    }

    @Test
    void limiteSalidaDesdeSalaVaciaEsRechazada() {
        Edificio edificio = DatosPrueba.edificio();
        edificio.actualizarOcupantes("OFF-105", 0);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> edificio.registrarSalida("OFF-105"));
        assertEquals("La sala OFF-105 ya está vacía", e.getMessage());
        assertEquals(0, edificio.personasEnSala("OFF-105"));
    }

    @Test
    void limiteEntrarYSalirDejaElMismoValor() {
        Edificio edificio = DatosPrueba.edificio();
        edificio.registrarEntrada("RM-103");
        edificio.registrarSalida("RM-103");
        assertEquals(45, edificio.personasEnSala("RM-103"));
    }

    @Test
    void invalidoSalaInexistente() {
        Edificio edificio = DatosPrueba.edificio();
        assertThrows(IllegalArgumentException.class, () -> edificio.registrarEntrada("NO-000"));
        assertThrows(IllegalArgumentException.class, () -> edificio.registrarSalida("NO-000"));
        assertThrows(IllegalArgumentException.class, () -> edificio.actualizarOcupantes("NO-000", 3));
    }

    @Test
    void invalidoOcupantesNegativos() {
        Edificio edificio = DatosPrueba.edificio();
        assertThrows(IllegalArgumentException.class, () -> edificio.actualizarOcupantes("RM-103", -1));
        assertEquals(45, edificio.personasEnSala("RM-103"));
    }
}
