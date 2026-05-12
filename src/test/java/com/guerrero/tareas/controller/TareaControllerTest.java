package com.guerrero.tareas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guerrero.tareas.entity.Tarea;
import com.guerrero.tareas.exception.GlobalExceptionHandler;
import com.guerrero.tareas.service.TareaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de la capa web en aislamiento usando @WebMvcTest.
 * Checkpoint 2
 */
@WebMvcTest(TareaController.class)
@Import(GlobalExceptionHandler.class)
class TareaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TareaService service;

    // --- GET /api/tareas/{id} ---

    @Test
    void get_tareaExiste_retorna200() throws Exception {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Test");
        when(service.buscarPorId(1L)).thenReturn(t);

        mockMvc.perform(get("/api/tareas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test"));
    }

    @Test
    void get_noExiste_retorna404() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new EntityNotFoundException("no encontrada"));

        mockMvc.perform(get("/api/tareas/99"))
                .andExpect(status().isNotFound());
    }

    // --- GET /api/tareas ---

    @Test
    void getAll_retornaListaYStatus200() throws Exception {
        Tarea t1 = new Tarea(); t1.setId(1L); t1.setTitulo("Tarea 1");
        Tarea t2 = new Tarea(); t2.setId(2L); t2.setTitulo("Tarea 2");
        when(service.listarTodas()).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Tarea 1"));
    }

    // --- POST /api/tareas ---

    @Test
    void post_tareaValida_retorna201() throws Exception {
        Tarea input = new Tarea(); input.setTitulo("Nueva tarea");
        Tarea saved = new Tarea(); saved.setId(1L); saved.setTitulo("Nueva tarea");
        when(service.crear(any())).thenReturn(saved);

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    // --- PATCH /api/tareas/{id}/completar ---

    @Test
    void patch_completar_tareaExistente_retorna200() throws Exception {
        Tarea t = new Tarea(); t.setId(1L); t.setTitulo("T"); t.setCompletada(true);
        when(service.completar(1L)).thenReturn(t);

        mockMvc.perform(patch("/api/tareas/1/completar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completada").value(true));
    }
}
