package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;

import java.util.List;

public interface ITurnoService {

    TurnoSalidaDto crearTurno(TurnoEntradaDto turnoEntradaDto);

    List<TurnoSalidaDto> turnoSalidaDtos();

    String eliminarTurno (Long id);

    TurnoSalidaDto actualizarTurno(Long id, TurnoEntradaDto turnoEntradaDto);

}
