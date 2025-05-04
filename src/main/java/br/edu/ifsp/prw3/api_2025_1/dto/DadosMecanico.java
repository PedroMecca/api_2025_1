package br.edu.ifsp.prw3.api_2025_1.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosMecanico(
        @NotBlank(message = "Nome do mecânico é obrigatório")
        String nome,

        int anosExperiencia) {

}
