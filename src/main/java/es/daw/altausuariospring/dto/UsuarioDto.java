package es.daw.altausuariospring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioDto(
        @NotBlank
        @Size(min = 3, max = 50)
        String nombre,
        @Email
        String email,
        String tecnologia,
        List<String> niveles
) {
}
