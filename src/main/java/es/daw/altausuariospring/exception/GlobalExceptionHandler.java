package es.daw.altausuariospring.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



/**
 * Spring detecta esta clase. Hay un @ComponentScan.... y aplica sus @ExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FicheroNoEncontradoException.class)
    public String gestionarErrorFichero(FicheroNoEncontradoException ex, Model model) {
        LOG.error("Error con los ficheros de datos ", ex);
        model.addAttribute("mensajeError", ex.getMessage());
        return "error";

    }
}
