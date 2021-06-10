package com.alura.forum.controller;

import com.alura.forum.DTO.response.DetalhesTopicoDto;
import com.alura.forum.DTO.response.TopicoDto;
import com.alura.forum.DTO.request.TopicoFormDto;
import com.alura.forum.DTO.request.UpdateTopicoFormDto;
import com.alura.forum.model.Topico;
import com.alura.forum.repository.CursoRepository;
import com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/topics")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping //findAll
    public List<TopicoDto> lista(String nomeCurso) {
        List<Topico> topicos;
        if (nomeCurso == null) {
            topicos = topicoRepository.findAll();
        } else {
            topicos = topicoRepository.findByCursoNome(nomeCurso);
        }
        return TopicoDto.converter(topicos);
    }

    @PostMapping //create
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoFormDto form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}") //findById
    public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable("id") Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        //Verifica se o valor do id existe, se sim, retorna os detalhes do tópico correspondente
        // orElse, caso não existe, retorna status 404
        return topico.map(value -> ResponseEntity.ok(new DetalhesTopicoDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")    //update
    @Transactional //avisa o spring que é uma transação, para realizar um commit ao fim do método
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid UpdateTopicoFormDto form ) {
        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // exclusão
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);

        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
