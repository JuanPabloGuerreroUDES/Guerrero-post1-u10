package com.guerrero.tareas.service;

import com.guerrero.tareas.entity.Tarea;
import com.guerrero.tareas.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de TareaService usando Mockito.
 * Checkpoint 1 — Patrón: método_condición_resultado
 */
@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    TareaRepository repo;

    @InjectMocks
    TareaService service;

    // --- crear() ---

    @Test
    void crear_conTituloValido_guardaYRetorna() {
        Tarea t = new Tarea();
        t.setTitulo("Estudiar JUnit");
        when(repo.save(any())).thenReturn(t);

        Tarea resultado = service.crear(t);

        assertThat(resultado.getTitulo()).isEqualTo("Estudiar JUnit");
        verify(repo).save(t);
    }

    @Test
    void crear_conTituloVacio_lanzaIllegalArgumentException() {
        Tarea t = new Tarea();
        t.setTitulo("   ");

        assertThrows(IllegalArgumentException.class, () -> service.crear(t));
        verify(repo, never()).save(any());
    }

    @Test
    void crear_conTituloNull_lanzaIllegalArgumentException() {
        Tarea t = new Tarea();
        t.setTitulo(null);

        assertThrows(IllegalArgumentException.class, () -> service.crear(t));
        verify(repo, never()).save(any());
    }

    // --- buscarPorId() ---

    @Test
    void buscarPorId_existeTarea_retornaTarea() {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Tarea existente");
        when(repo.findById(1L)).thenReturn(Optional.of(t));

        Tarea resultado = service.buscarPorId(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getTitulo()).isEqualTo("Tarea existente");
    }

    @Test
    void buscarPorId_noExiste_lanzaEntityNotFoundException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(99L));
    }

    // --- completar() ---

    @Test
    void completar_tareaExistente_marcaComoCompletada() {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Tarea pendiente");
        t.setCompletada(false);

        when(repo.findById(1L)).thenReturn(Optional.of(t));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Tarea resultado = service.completar(1L);

        assertThat(resultado.isCompletada()).isTrue();
        verify(repo).save(t);
    }

    @Test
    void completar_tareaNoExiste_lanzaEntityNotFoundException() {
        when(repo.findById(50L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.completar(50L));
        verify(repo, never()).save(any());
    }
}
