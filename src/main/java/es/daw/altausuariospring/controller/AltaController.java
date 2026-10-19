package es.daw.altausuariospring.controller;

import es.daw.altausuariospring.service.AltaService;
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
public class AltaController {

    private static final Logger LOG = LoggerFactory.getLogger(AltaController.class);

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

//    @GetMapping("/")
//    public String inicio() {
//        return "index";              // → templates/index.html
//    }

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
