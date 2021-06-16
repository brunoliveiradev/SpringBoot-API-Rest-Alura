package com.alura.forum.controller;

import com.alura.forum.DTO.request.TopicoFormDto;
import com.alura.forum.DTO.request.UpdateTopicoFormDto;
import com.alura.forum.DTO.response.DetalhesTopicoDto;
import com.alura.forum.DTO.response.TopicoDto;
import com.alura.forum.model.Topico;
import com.alura.forum.repository.CursoRepository;
import com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping //findAll
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                                 @PageableDefault(page = 0, size = 10, sort = "id",
                                         direction = Sort.Direction.DESC) Pageable paginacao) {

//        Pageable paginacao = PageRequest.of(pagina, qtd, Sort.Direction.ASC, orderBy);

        Page<Topico> topicos;
        if (nomeCurso == null) {
            topicos = topicoRepository.findAll(paginacao);
        } else {
            topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
        }
        return TopicoDto.converter(topicos);
    }

    @PostMapping //create
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoFormDto form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/{id}").buildAndExpand(topico.getId()).toUri();

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
