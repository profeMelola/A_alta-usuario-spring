package es.daw.altausuariospring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//@Data: toString, @EqualsAndHashCode, Getters, setters, @RequiredArgsConstructor (constructor de atributos final)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioDto {

    @NotBlank
    @Size(min = 3, max = 10)
    //@Pattern('')
    private String nombre;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String tecnologia;

    private List<String> niveles = new ArrayList<>();


}
