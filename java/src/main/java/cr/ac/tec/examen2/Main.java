package cr.ac.tec.examen2;

import cr.ac.tec.examen2.datos.CargadorCSV;
import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.evacuacion.GuiaEvacuacion;
import cr.ac.tec.examen2.evacuacion.RutaEvacuacion;
import cr.ac.tec.examen2.modelo.Ala;
import cr.ac.tec.examen2.modelo.Edificio;
import cr.ac.tec.examen2.modelo.Sala;
import cr.ac.tec.examen2.reporte.ReporteBrigada;

/** SalidaSegura: ejecuta las tres corridas pedidas en el examen (versión B). */
public class Main {

    public static void main(String[] args) {
        String carpeta = args.length > 0 ? args[0] : "../datos/version-b";
        Edificio edificio = CargadorCSV.cargarEdificio(carpeta + "/ocupantes.csv", "Edificio de Ciencias");
        GrafoMatriz grafo = CargadorCSV.cargarGrafo(carpeta + "/pasillos.csv");

        System.out.println("=== Personas por ala y en todo el edificio ===");
        for (Ala ala : edificio.getAlas()) {
            System.out.printf("%-8s %3d%n", ala.getNombre(), edificio.personasEnAla(ala.getNombre()));
        }
        System.out.printf("%-8s %3d%n%n", "Edificio", edificio.personasEnEdificio());

        System.out.println("=== Reporte para la brigada ===");
        Sala[] reporte = ReporteBrigada.generar(edificio);
        for (int i = 0; i < reporte.length; i++) {
            System.out.printf("%d. %-8s %-24s %-8s %2d%n", i + 1, reporte[i].getCodigo(),
                    reporte[i].getNombre(), reporte[i].getAla(), reporte[i].getOcupantes());
        }
        System.out.println();

        System.out.println("=== Guía de evacuación ===");
        GuiaEvacuacion guia = new GuiaEvacuacion(grafo);
        RutaEvacuacion[] rutas = guia.guiaCompleta(edificio);
        Sala[] salas = edificio.todasLasSalas();
        for (int i = 0; i < rutas.length; i++) {
            System.out.println(rutas[i] != null ? rutas[i]
                    : salas[i].getCodigo() + ": sin salida alcanzable");
        }
    }
}
