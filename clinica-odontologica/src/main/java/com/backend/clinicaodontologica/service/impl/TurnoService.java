package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import com.backend.clinicaodontologica.repository.PacienteRepository;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private TurnoRepository turnoRepository;
    private ModelMapper modelMapper;
    private PacienteRepository pacienteRepository;
    private OdontologoRepository odontologoRepository;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public TurnoSalidaDto crearTurno(TurnoEntradaDto turnoEntradaDto) {
        TurnoSalidaDto turnoSalidaDto = null;
        try {
            LOGGER.info("TurnoEntradaDTO: " + JsonPrinter.toString(turnoEntradaDto));
            Long idPaciente = turnoEntradaDto.getIdPaciente();
            Long idOdontologo = turnoEntradaDto.getIdOdontologo();

            // Cargar Paciente y Odontologo utilizando sus identificadores

            Paciente pacienteEncontrado = pacienteRepository.findById(idPaciente).orElse(null);
            LOGGER.info("Paciente encontrado por ID: " + idPaciente);

            Odontologo odontologoEncontrado = odontologoRepository.findById(idOdontologo).orElse(null);
            LOGGER.info("Odontologo encontrado por ID: " + idOdontologo);

            if (pacienteEncontrado != null && odontologoEncontrado != null && turnoEntradaDto.getFechaYHora() != null) {
                Paciente pacienteEntidad = modelMapper.map(pacienteEncontrado, Paciente.class);
                Odontologo odontologoEntidad = modelMapper.map(odontologoEncontrado, Odontologo.class);
                LOGGER.info("Paciente: " + pacienteEntidad);
                LOGGER.info("Odontologo: " + odontologoEntidad);

                Turno turnoNuevo = new Turno(turnoEntradaDto.getFechaYHora(), odontologoEntidad, pacienteEntidad);
                Turno turnoPersistencia = turnoRepository.save(turnoNuevo);
                LOGGER.info("turno nuevo: " + turnoNuevo);

                turnoSalidaDto = modelMapper.map(turnoNuevo, TurnoSalidaDto.class);
            }
        } catch (Exception e){
            LOGGER.error("error :" + e);
        }

        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> turnoSalidaDtos() {
        List<TurnoSalidaDto> turnoSalidaDtoList = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        return turnoSalidaDtoList;
    }

    @Override
    public String eliminarTurno(Long id) {
        String turnoEliminado = null;
        LOGGER.info("ID localizado para eliminar. ID: " + id);
        Turno turnoAEliminar = turnoRepository.findById(id).orElse(null);
        turnoRepository.delete(turnoAEliminar);
        LOGGER.info("El turno con ID: " + id + " ha sido eliminado con exito. Fecha de Turno: " + turnoAEliminar.getFechaYHora() + " " + turnoAEliminar.getOdontologo() + " " + turnoAEliminar.getPaciente());
        turnoEliminado = modelMapper.map(id, String.class);
        return turnoEliminado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(Long id, TurnoEntradaDto turnoEntradaDto) {
        Turno turnoEncontrado = turnoRepository.findById(id).orElse(null);
        Turno turnoNuevo = modelMapper.map(turnoEntradaDto, Turno.class);
        Turno turnoAPersistir = turnoRepository.save(turnoNuevo);
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoEncontrado, TurnoSalidaDto.class);
        LOGGER.info("Turno Actualizado ID: " + id);
        return turnoSalidaDto;
    }
}
