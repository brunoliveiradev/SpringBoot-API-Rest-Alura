package com.alura.forum.repository;

import com.alura.forum.model.Curso;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Devolve o Curso ao buscar pelo nome")
    public void findByNome() {
        String nomeCurso = "HTML 5";

        Curso html5 = new Curso();
        html5.setNome(nomeCurso);
        html5.setCategoria("Programação");
        em.persist(html5);

        Curso curso = cursoRepository.findByNome(nomeCurso);

        assertNotNull(curso);
        assertEquals(nomeCurso, curso.getNome());
    }
    
    @Test
    @DisplayName("Devolve null em Curso nao cadastrado")
    void devolveNullEmCursoNaoCadastrado(){
        String nomeCurso = "JPA";
        Curso curso = cursoRepository.findByNome(nomeCurso);

        assertNull(curso);
    }
}