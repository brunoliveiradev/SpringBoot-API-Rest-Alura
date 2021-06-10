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

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping //findAll
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

    @PostMapping //create
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoFormDto form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}") //findById
    public DetalhesTopicoDto detalhar(@PathVariable("id") Long id) {
        Topico byId = topicoRepository.getById(id);
        return new DetalhesTopicoDto(byId);
    }

    @PutMapping("/{id}")    //update
    @Transactional //avisa o spring que é uma transação, para commitar ao fim do método
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid UpdateTopicoFormDto form ) {
        Topico topico = form.atualizar(id, topicoRepository);

        return ResponseEntity.ok(new TopicoDto(topico));
    }
}
