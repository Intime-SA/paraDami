package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;


    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    @Test
    @Order(1)
    void DeberiaRegistrarUnTurno_DevolverDatosDePacienteYOdontologo() {
        OdontologoSalidaDto odontologo;
        odontologo = odontologoService.registrarOdontologo(new OdontologoEntradaDto("ASD1234567", "Rama", "Piola"));
        PacienteSalidaDto paciente = pacienteService.registrarPaciente(new PacienteEntradaDto("Ramiro", "Arce", 39349791, LocalDate.of(1995, 12, 07), new DomicilioEntradaDto("Mendoza", 2070, "Mar del Plata", "Buenos Aires")));
        LOGGER.info("odontologo " + odontologo);
        LOGGER.info("paciente " + paciente);

        LocalDateTime fechaYHoraEsperada = LocalDateTime.of(2024, 02, 13, 18, 30, 30);

        LOGGER.info("FECHA ESPERADA = " + fechaYHoraEsperada);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(fechaYHoraEsperada, 1L, 2L);

        TurnoSalidaDto turnoSalidaDto = turnoService.crearTurno(turnoEntradaDto);

        LOGGER.info("TURNO CREADO POR TEST = " + turnoSalidaDto);

        assertNotNull(turnoSalidaDto);
        assertEquals("ASD1234567", turnoSalidaDto.getOdontologo().getMatricula());
        assertEquals("Ramiro", turnoSalidaDto.getPaciente().getNombre());
    };

    @Test
    @Order(2)
    public void deberiaActualizarTurnoExistente() {
        // Registrar odontólogo y paciente
        OdontologoSalidaDto odontologo = odontologoService.registrarOdontologo(new OdontologoEntradaDto("ASD1234567", "Rama", "Piola"));
        PacienteSalidaDto paciente = pacienteService.registrarPaciente(new PacienteEntradaDto("Ramiro", "Arce", 39349791, LocalDate.of(1995, 12, 07), new DomicilioEntradaDto("Mendoza", 2070, "Mar del Plata", "Buenos Aires")));

        LOGGER.info("Odontólogo: " + odontologo);
        LOGGER.info("Paciente: " + paciente);

        // Crear turno
        LocalDateTime fechaYHoraEsperada = LocalDateTime.of(2024, 02, 13, 18, 30, 30);
        LOGGER.info("Fecha y Hora Esperada: " + fechaYHoraEsperada);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(fechaYHoraEsperada, 1L, 2L);
        TurnoSalidaDto turnoSalidaDto = turnoService.crearTurno(turnoEntradaDto);

        // Modificar turno
        LocalDateTime fechaYHoraEsperada2 = LocalDateTime.of(2025, 02, 13, 18, 30, 30);
        LOGGER.info("Nueva Fecha y Hora Esperada: " + fechaYHoraEsperada2);

        TurnoModificacionEntradaDto turnoEntradaDtoMod = new TurnoModificacionEntradaDto(
                1L,
                new PacienteModificacionEntradaDto(2L,"Ramiro", "Martin", 39349791, LocalDate.of(1995, 12, 07), new DomicilioEntradaDto("Mendoza", 2070, "Buenos Aires", "Mar del Plata")),
                new OdontologoModificacionEntradaDto(1L, "ASD1234567", "Rodrigo", "Miguelez"),
                fechaYHoraEsperada2
        );
        // Actualizar turno
        TurnoSalidaDto turnoSalidaDtoActualizado = turnoService.actualizarTurno(turnoEntradaDtoMod);
        LOGGER.info("Turno actualizado DTO = " + turnoSalidaDtoActualizado);

        // Verificar la actualización
        assertNotNull(turnoSalidaDtoActualizado);
        assertEquals(fechaYHoraEsperada2, turnoSalidaDtoActualizado.getFechaYHora());
        assertEquals("Rodrigo", turnoSalidaDtoActualizado.getOdontologo().getNombre());
        assertEquals("Martin", turnoSalidaDtoActualizado.getPaciente().getApellido());

    }


    @Test
    @Order(3)
    public void deberiaEliminarUnTurno() {




        try {
            turnoService.eliminarTurno(1L);
        } catch (Exception exception) {
            exception.printStackTrace();
        }



        List<TurnoSalidaDto> turnosDespuesDeEliminar = turnoService.turnoSalidaDtos();
        LOGGER.info("Turnos despues de eliminar =" + turnosDespuesDeEliminar);
        assertEquals(0, turnosDespuesDeEliminar.size(), "El tamaño de la lista debería ser menor después de eliminar un turno");
    }
}