package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private TurnoRepository turnoRepository;

    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    private ModelMapper modelMapper;


    @Autowired
    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public TurnoSalidaDto crearTurno(TurnoEntradaDto turnoEntradaDto) {

        TurnoSalidaDto turnoSalidaDto = null;

        try {
            LOGGER.info("TurnoEntradaDTO: " + JsonPrinter.toString(turnoEntradaDto));
            Long idPaciente = turnoEntradaDto.getPaciente();
            Long idOdontologo = turnoEntradaDto.getOdontologo();

            // Cargar Paciente y Odontologo utilizando sus identificadores

            PacienteSalidaDto pacienteEncontrado = pacienteService.buscarPacientePorId(idPaciente);
            LOGGER.info("Paciente encontrado por ID: " + pacienteEncontrado);

            OdontologoSalidaDto odontologoEncontrado = odontologoService.buscarOdontologoPorId(idOdontologo);
            LOGGER.info("Odontologo encontrado por ID: " + odontologoEncontrado);


            if (pacienteEncontrado != null && odontologoEncontrado != null) {
                Odontologo odontologoEntidad = modelMapper.map(odontologoEncontrado, Odontologo.class);
                Paciente pacienteEntidad = modelMapper.map(pacienteEncontrado, Paciente.class);
                Turno turnoNuevo = new Turno(turnoEntradaDto.getFechaYHora(), odontologoEntidad, pacienteEntidad);

                Turno turnoPersistencia = turnoRepository.save(turnoNuevo);
                turnoSalidaDto = modelMapper.map(turnoPersistencia, TurnoSalidaDto.class);
                LOGGER.info("PacienteSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));

                return turnoSalidaDto;

            }
            //agregar mas validaciones



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
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turnoEntradaDto) {
        Turno turnoEncontrado = turnoRepository.findById(turnoEntradaDto.getId()).orElse(null);
        Turno turnoNuevo = modelMapper.map(turnoEntradaDto, Turno.class);
        Turno turnoAPersistir = turnoRepository.save(turnoNuevo);
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
        LOGGER.info("Turno Actualizado ID: " + turnoEncontrado.getId());
        return turnoSalidaDto;
    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getPaciente, Turno::setPaciente));

        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getOdontologo, Turno::setOdontologo));

        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getPaciente, TurnoSalidaDto::setPaciente));

        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologo));
    }


}
