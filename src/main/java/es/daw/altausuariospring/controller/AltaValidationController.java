package es.daw.altausuariospring.controller;

import es.daw.altausuariospring.service.AltaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor // LOMBOK: sustituye el constructor que recibe como argumento los atributos final
public class AltaValidationController {

    private static final Logger LOG = LoggerFactory.getLogger(AltaValidationController.class);

    private final AltaService service;

//    @Autowired //opcional
//    public AltaValidationController(AltaService service) {
//
//        this.service = service;
//    }
    // ----------------------------------------------------------


    @GetMapping("/alta")
    public String mostrarFormulario(Model model) {
        // 1. Añadir al modelo las listas de tecnologías y niveles
        model.addAttribute("listaNiveles",service.getNiveles());
        LOG.info("lista de niveles: {} ",service.getNiveles());
        model.addAttribute("tecnologias",service.getTecnologias());
        LOG.info("lista de tecnologias: {} ",service.getTecnologias());

        // 2. Devolver el nombre de la vista
        return "formulario"; // NO es un endpoint, es el nombre del template de thymeleaf
    }

    @PostMapping("/alta")
    public String procesarFormulario(@RequestParam String nombre,
                                     @RequestParam String email,
                                     @RequestParam(name = "tecnologia") String tecno,
                                     @RequestParam(name = "nivel", required = false) List<String> niveles,
                                     Model model) {

        // 1. RECOGER LOS VALORES.... ESO LO HACE AUTOMÁTICAMENTE SPRING CON @RequestParam

        // 2. VALIDACIONES...Si nombre está vacío → mensajeError + datos introducidos + listas → "formulario"
        if ( nombre.isBlank()){
            model.addAttribute("mensajeError", "El nombre es obligatorio");

            // Queremos mantener los datos que el usuario haya rellenado en el formulario...
            model.addAttribute("listaNiveles",service.getNiveles());
            model.addAttribute("tecnologias",service.getTecnologias()); // para cargar los options del desplegable
            model.addAttribute("nombre", nombre);
            model.addAttribute("email", email);
            model.addAttribute("tecnologia",tecno); // lo usamos para hacer selected del único valor de la lista monoselección de tecnologías

            model.addAttribute("nivelesSeleccionados",niveles); // lo usaremos para hacer selected de los options de la lista de niveles

            return "formulario"; // nombre de la vista...

        }

        // 3. PASAMOS INFORMACIÓN A LA VIST. Si no → datos al modelo → "confirmacion"
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("tecnologia",tecno); // lo usamos para hacer selected del único valor de la lista monoselección de tecnologías

        model.addAttribute("niveles",niveles);
        return "confirmacion"; // FORWARD A LA VISTA
    }
}
