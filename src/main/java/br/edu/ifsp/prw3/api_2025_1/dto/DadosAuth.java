package br.edu.ifsp.prw3.api_2025_1.dto;


import jakarta.validation.constraints.NotBlank;

public record DadosAuth (
        @NotBlank
        String login,

        @NotBlank
        String password){}

