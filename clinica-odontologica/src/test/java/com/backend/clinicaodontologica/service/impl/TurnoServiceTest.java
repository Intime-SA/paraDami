package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    void deberiaAgendarUnTurnoYRetornarlo() {

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2024, 02, 13, 18, 30, 00), 2L, 3L);

        TurnoSalidaDto turnoSalidaDto = turnoService.crearTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto.getId());
        assertEquals("2024-02-13T18:30:00", turnoSalidaDto.getFechaYHora());

    }

    ;

    @Test
    @Order(2)
    void deberiaModificarUnTurno() {
        TurnoModificacionEntradaDto turnoModificacionEntradaDto = new TurnoModificacionEntradaDto(LocalDateTime.of(2024, 8, 22, 19, 20, 00), 1L, 3);

        TurnoSalidaDto turnoSalidaDto = turnoService.actualizarTurno(turnoModificacionEntradaDto);

        assertNotNull(turnoSalidaDto.getId());
        assertEquals("2024-8-22T19:20:00", turnoSalidaDto.getFechaYHora());
    }

    @Test
    @Order(3)
    public void deberiaEliminarUnTurno (){
        try {
            turnoService.eliminarTurno(1L);
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    };



}