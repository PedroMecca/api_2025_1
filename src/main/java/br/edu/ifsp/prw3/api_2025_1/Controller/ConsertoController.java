package br.edu.ifsp.prw3.api_2025_1.Controller;

import br.edu.ifsp.prw3.api_2025_1.Models.Conserto;
import br.edu.ifsp.prw3.api_2025_1.Repository.ConsertoRepository;
import br.edu.ifsp.prw3.api_2025_1.dto.DadosConserto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/consertos")
public class ConsertoController {

    private final ConsertoRepository consertoRepository;

    public ConsertoController(ConsertoRepository consertoRepository) {
        this.consertoRepository = consertoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosConserto conserto, UriComponentsBuilder uriBuilder) {
        try {
            Conserto novoConserto = new Conserto(conserto);
            consertoRepository.save(novoConserto);

            URI uri = uriBuilder.path("/consertos/{id}").buildAndExpand(novoConserto.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar conserto: " + e.getMessage());
        }
    }
}