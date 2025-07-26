package com.aluracursos.screenmatch.model;

import java.text.Normalizer;

public enum Categoria {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    private String categoriaOmdb;
    private String categoriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb   = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    private static String normalize(String s) {
        // Descompone acentos y luego los elimina
        String tmp = Normalizer.normalize(s, Normalizer.Form.NFD);
        return tmp.replaceAll("\\p{M}", "").toLowerCase();
    }

    public static Categoria fromEspanol(String text) {
        String buscado = normalize(text);
        for (Categoria c : values()) {
            if (normalize(c.categoriaEspanol).equals(buscado)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }

    public static Categoria fromString(String text) {
        String buscado = normalize(text);
        for (Categoria c : values()) {
            if (normalize(c.categoriaOmdb).equals(buscado)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría OMDB encontrada: " + text);
    }
}
