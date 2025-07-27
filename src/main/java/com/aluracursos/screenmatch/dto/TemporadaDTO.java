package com.aluracursos.screenmatch.dto;

import java.util.List;

public record TemporadaDTO(
        Integer numeroTemporada,
        List<EpisodioDTO> episodios
) {}