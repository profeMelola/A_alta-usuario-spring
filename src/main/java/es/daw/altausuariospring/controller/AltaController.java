package es.daw.altausuariospring.controller;

import es.daw.altausuariospring.service.AltaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AltaController {

    // ----------------- INYECCIÓN DE DEPENDENCIAS POR PROPIEDAD ---------------------
    // NO LO VAMOS A UTILIZAR
//    // Aquí si es obligatorio indicar @Autowired
//    @Autowired
//    private AltaService service2;


    // ------------ INYECCIÓN DE DEPENDENCIAS POR CONSTRUCTOR -------------------------
    // RAZONES POR LAS QUE USAR INYECCIÓN POR CONSTRUCTOR:
    // 1. Cuando Spring carga el controlador, sabe por constructor las dependencias que tiene el controlador.
    // 2. No necesito Spring. En un test unitario basta con pasar la dependica, por ejemplo un mock:
    // AltaController c = new AltaController(mockService);

    // Una vez instanciado no cambia. Es inmutable
    private final AltaService service;

    // Inyección por constructor: NO hacemos new AltaService(), nos lo da Spring
    // Versiones anteriores a la 4.3 se necesitaba @Autowired
//    Nota: con Lombok se puede abreviar aún más usando @RequiredArgsConstructor, que genera el constructor para todos los atributos final.
//    Es inyección por constructor igualmente, solo que sin escribir el
//    constructor a mano.
    @Autowired
    public AltaController(AltaService service) {
        this.service = service;
    }
    // ----------------------------------------------------------

    @GetMapping("/")
    public String inicio() {
        return "index";              // → templates/index.html
    }

    @GetMapping("/alta")
    public String mostrarFormulario(Model model) {
        // 1. Añadir al modelo las listas de tecnologías y niveles
        model.addAttribute("tecnologias",service.getNiveles());
        model.addAttribute("listaNiveles",service.getTecnologias());

        // 2. Devolver el nombre de la vista
        return "formulario";
    }

    @PostMapping("/alta")
    public String procesarFormulario(@RequestParam String nombre,
                                     @RequestParam String email,
                                     @RequestParam String tecnologia,
                                     @RequestParam(name = "nivel", required = false) List<String> niveles,
                                     Model model) {
        // 1. Si nombre está vacío → mensajeError + datos introducidos + listas → "formulario"
        // 2. Si no → datos al modelo → "confirmacion"
        return "confirmacion";
    }
}
