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

//    @NotBlank(message = "Majete, no puede estar vacío el nombre!")
//    @Size(min = 3, max = 10, message = "El nombre debe tener al menos {min} caracteres y {max} caracteres como máximo")
    //@Pattern('')
    @NotBlank(message = "{usuario.nombre.obligatorio}")
    @Size(min = 3, max = 10, message = "{usuario.nombre.tamano}")
    private String nombre;

    @NotBlank(message = "{usuario.email.obligatorio}")
    @Email(message = "{usuario.email.formato}")
    private String email;

    @NotBlank(message = "{usuario.tecnologia.obligatoria}")
    private String tecnologia;

    private List<String> niveles = new ArrayList<>();


}
