package cr.ac.tec.examen2.datos;

import cr.ac.tec.examen2.estructuras.GrafoMatriz;
import cr.ac.tec.examen2.modelo.Edificio;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/** Lee los CSV de datos/version-b (ocupantes.csv y pasillos.csv). */
public final class CargadorCSV {

    private CargadorCSV() {
    }

    /** ocupantes.csv: codigo,nombre,ala,ocupantes. Las alas se crean en el orden en que aparecen. */
    public static Edificio cargarEdificio(String ruta, String nombreEdificio) {
        Edificio edificio = new Edificio(nombreEdificio);
        String[] lineas = leerLineas(ruta);
        for (int i = 1; i < lineas.length; i++) { // la línea 0 es el encabezado
            if (lineas[i].trim().isEmpty()) {
                continue;
            }
            int numero = i + 1;
            String[] c = columnas(lineas[i], numero);
            try {
                if (edificio.buscarAla(c[2]) == null) {
                    edificio.agregarAla(c[2]);
                }
                edificio.agregarSala(c[2], c[0], c[1], entero(c[3], numero));
            } catch (IllegalArgumentException e) {
                if (e.getMessage().startsWith("Línea ")) {
                    throw e;
                }
                throw new IllegalArgumentException("Línea " + numero + ": " + e.getMessage(), e);
            }
        }
        return edificio;
    }

    /** pasillos.csv: desde,hacia,segundos,estado (Abierto | Bloqueado...). */
    public static GrafoMatriz cargarGrafo(String ruta) {
        GrafoMatriz grafo = new GrafoMatriz();
        String[] lineas = leerLineas(ruta);
        for (int i = 1; i < lineas.length; i++) {
            if (lineas[i].trim().isEmpty()) {
                continue;
            }
            int numero = i + 1;
            String[] c = columnas(lineas[i], numero);
            boolean bloqueado = estado(c[3], numero);
            try {
                grafo.agregarPasillo(c[0], c[1], entero(c[2], numero), bloqueado);
            } catch (IllegalArgumentException e) {
                if (e.getMessage().startsWith("Línea ")) {
                    throw e;
                }
                throw new IllegalArgumentException("Línea " + numero + ": " + e.getMessage(), e);
            }
        }
        return grafo;
    }

    private static boolean estado(String texto, int numero) {
        String t = texto.toLowerCase();
        if (t.equals("abierto")) {
            return false;
        }
        if (t.startsWith("bloqueado")) {
            return true;
        }
        throw new IllegalArgumentException("Línea " + numero + ": estado inválido '" + texto + "'");
    }

    private static String[] columnas(String linea, int numero) {
        String[] c = linea.split(",", -1);
        if (c.length != 4) {
            throw new IllegalArgumentException("Línea " + numero + ": se esperaban 4 columnas y hay " + c.length);
        }
        for (int i = 0; i < c.length; i++) {
            c[i] = c[i].trim();
        }
        return c;
    }

    private static int entero(String texto, int numero) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Línea " + numero + ": número inválido '" + texto + "'");
        }
    }

    /** Lee todo el archivo a un arreglo de String que crece por duplicación (sin java.util). */
    private static String[] leerLineas(String ruta) {
        String[] lineas = new String[16];
        int cantidad = 0;
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(new FileInputStream(ruta), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (cantidad == 0 && !linea.isEmpty() && linea.charAt(0) == '﻿') {
                    linea = linea.substring(1); // BOM de UTF-8
                }
                if (cantidad == lineas.length) {
                    String[] mayor = new String[lineas.length * 2];
                    for (int i = 0; i < cantidad; i++) {
                        mayor[i] = lineas[i];
                    }
                    lineas = mayor;
                }
                lineas[cantidad++] = linea;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo leer el archivo: " + ruta, e);
        }
        String[] resultado = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = lineas[i];
        }
        return resultado;
    }
}
