package es.daw.altausuariospring.controller;

import es.daw.altausuariospring.dto.UsuarioDto;
import es.daw.altausuariospring.service.AltaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor // LOMBOK: sustituye el constructor que recibe como argumento los atributos final
@RequestMapping("/alta-validacion")
public class AltaValidationController {

    private static final Logger LOG = LoggerFactory.getLogger(AltaValidationController.class);

    private final AltaService service;

    // @ModelAttribute en un método, Spring lo ejecuta antes de cada método (endpoint) del controlador.
    // Añade el resultado al Model
    @ModelAttribute("tecnologias")
    public List<String> getTecnologias() {
        return service.getTecnologias();
    }

    @ModelAttribute("listaNiveles")
    public List<String> getListaNiveles() {
        return service.getNiveles();
    }

    //@GetMapping("/alta-validacion")
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario",new UsuarioDto());
        return "formulario-validacion";
    }


    @PostMapping
    public String procesarFormulario(
                                        @Valid @ModelAttribute("usuario") UsuarioDto usuario,
                                        // BindingResult guarda los errores de eso objeto.
                                        // Tiene que ir justo después del @ModelAttribute
                                        // Si usamos @Valid y hay errores de validación y no ponemos BindingResult,
                                        // Spring lanza error 400 (MethodArgumentNotValidExcepcion)
                                        BindingResult bindingResult
                                    ) {

        LOG.info("Alta recibida (Bean Validation): {}",usuario);

        if (bindingResult.hasErrors()) {

            LOG.error("Errores de validacion 1: {}", bindingResult.getAllErrors());
            LOG.error("Errores de validacion 2: {}", bindingResult.getFieldErrors());

            return "formulario-validacion";
        }


        return "confirmacion-validacion";
    }
}
