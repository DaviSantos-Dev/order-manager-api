package davisantos.dev.OrderManager.shared.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
public interface GenericController {

    default URI generateUri(Long responseId){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseId)
                .toUri();
    }
}
