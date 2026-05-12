package com.guerrero.tareas.repository;

import com.guerrero.tareas.entity.Tarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de la capa de repositorio con H2 en memoria.
 * Checkpoint 2 — @DataJpaTest (transacciones revertidas automáticamente)
 */
@DataJpaTest
class TareaRepositoryTest {

    @Autowired
    TareaRepository repo;

    @Autowired
    TestEntityManager em;

    @BeforeEach
    void setUp() {
        Tarea pendiente = new Tarea();
        pendiente.setTitulo("Pendiente");
        pendiente.setCompletada(false);
        em.persistAndFlush(pendiente);

        Tarea completada = new Tarea();
        completada.setTitulo("Completada");
        completada.setCompletada(true);
        em.persistAndFlush(completada);
    }

    @Test
    void findByCompletada_false_retornaUnaTarea() {
        List<Tarea> result = repo.findByCompletada(false);

        assertThat(result).hasSize(1)
                .extracting("titulo")
                .containsExactly("Pendiente");
    }

    @Test
    void findByCompletada_true_retornaUnaTarea() {
        List<Tarea> result = repo.findByCompletada(true);

        assertThat(result).hasSize(1)
                .extracting("titulo")
                .containsExactly("Completada");
    }

    @Test
    void save_nuevaTarea_persisteCorrectamente() {
        Tarea nueva = new Tarea();
        nueva.setTitulo("Nueva tarea de repo");
        Tarea guardada = repo.save(nueva);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getTitulo()).isEqualTo("Nueva tarea de repo");
        assertThat(guardada.isCompletada()).isFalse();
    }

    @Test
    void findById_existente_retornaTarea() {
        Tarea t = new Tarea();
        t.setTitulo("Buscar por ID");
        Tarea guardada = em.persistAndFlush(t);

        Optional<Tarea> resultado = repo.findById(guardada.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTitulo()).isEqualTo("Buscar por ID");
    }

    @Test
    void findById_noExistente_retornaVacio() {
        Optional<Tarea> resultado = repo.findById(9999L);
        assertThat(resultado).isEmpty();
    }

    @Test
    void delete_tareaExistente_eliminaCorrectamente() {
        Tarea t = new Tarea();
        t.setTitulo("Para eliminar");
        Tarea guardada = em.persistAndFlush(t);

        repo.deleteById(guardada.getId());
        em.flush();

        assertThat(repo.findById(guardada.getId())).isEmpty();
    }
}
