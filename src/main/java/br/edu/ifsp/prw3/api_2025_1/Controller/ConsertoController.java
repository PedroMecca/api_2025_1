package br.edu.ifsp.prw3.api_2025_1.Controller;

import br.edu.ifsp.prw3.api_2025_1.Models.Conserto;
import br.edu.ifsp.prw3.api_2025_1.Repository.ConsertoRepository;
import br.edu.ifsp.prw3.api_2025_1.dto.ConsertoResumo;
import br.edu.ifsp.prw3.api_2025_1.dto.DadosAtualizacaoConserto;
import br.edu.ifsp.prw3.api_2025_1.dto.DadosConserto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        Conserto novoConserto = new Conserto(conserto);
        consertoRepository.save(novoConserto);
        URI uri = uriBuilder.path("/consertos/{id}").buildAndExpand(novoConserto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/todos")
    public ResponseEntity<Page<Conserto>> listarTodos(Pageable pageable) {
        Page<Conserto> consertos = consertoRepository.findAllByAtivoTrue(pageable);
        return ResponseEntity.ok(consertos);
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<ConsertoResumo>> listarResumo() {
        List<ConsertoResumo> resumo = consertoRepository.findAllByAtivoTrue()
                .stream()
                .map(ConsertoResumo::new)
                .toList();
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conserto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.of(consertoRepository.findById(id));
    }

    @PutMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoConserto dados) {
        try {
            Conserto conserto = consertoRepository.findById(id).orElse(null);
            if (conserto == null) {
                return ResponseEntity.status(404).body("Conserto não encontrado!");
            }

            conserto.setDataSaida(dados.dataSaida());
            conserto.getMecanico().setNome(dados.nomeMecanico());
            conserto.getMecanico().setAnosExperiencia(dados.anosExperiencia());

            DadosAtualizacaoConserto atualizado = new DadosAtualizacaoConserto(
                    id, dados.dataSaida(), dados.nomeMecanico(), dados.anosExperiencia());

            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro na atualização: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Conserto conserto = consertoRepository.findById(id).orElse(null);
        if (conserto == null) {
            return ResponseEntity.status(404).body("Conserto não encontrado!");
        }

        conserto.setAtivo(false);
        return ResponseEntity.noContent().build();
    }
}