package cr.ac.tec.examen2;

import cr.ac.tec.examen2.datos.CargadorCSV;
import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.modelo.Edificio;

/** Utilidades compartidas por las pruebas: cargan los CSV de datos/version-b. */
final class DatosPrueba {

    // Maven ejecuta las pruebas desde la carpeta java/
    static final String OCUPANTES = "../datos/version-b/ocupantes.csv";
    static final String PASILLOS = "../datos/version-b/pasillos.csv";

    private DatosPrueba() {
    }

    static Edificio edificio() {
        return CargadorCSV.cargarEdificio(OCUPANTES, "Edificio de Ciencias");
    }

    static GrafoMatriz grafo() {
        return CargadorCSV.cargarGrafo(PASILLOS);
    }

    static String[] codigos(cr.ac.tec.examen2.modelo.Sala[] salas) {
        String[] codigos = new String[salas.length];
        for (int i = 0; i < salas.length; i++) {
            codigos[i] = salas[i].getCodigo();
        }
        return codigos;
    }
}
