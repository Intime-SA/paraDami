package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import com.backend.clinicaodontologica.service.IOdontologoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    private OdontologoRepository odontologoRepository;

    private ModelMapper modelMapper;

    public OdontologoService() {
    }

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        return null;
    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {

        LOGGER.info("Odontologo entrada DTO: " + JsonPrinter.toString(odontologo));
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        Odontologo odontologoAPersistir = odontologoRepository.save(odontologoEntidad);
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoAPersistir, OdontologoSalidaDto.class);
        LOGGER.info("Odontologo salida DTO: " +  JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        OdontologoSalidaDto odontologo = null;
        Odontologo odontologoEntidad = odontologoRepository.getReferenceById(id);
        odontologo = modelMapper.map(odontologoEntidad, OdontologoSalidaDto.class);
        return odontologo;
    }
    

    @Override
    public void eliminarOdontologo(Long id) {
        if (odontologoRepository.findById(id).orElse(null) != null) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el odontologo con id {}", id);
            //excepcion a lanzar aqui
        }

    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontologoModificacionEntradaDto) {

        OdontologoSalidaDto odontologoSalidaDto = null;
        Odontologo odontologo = modelMapper.map(odontologoModificacionEntradaDto, Odontologo.class);
        Odontologo odontologoEncontrado = odontologoRepository.findById(odontologo.getId()).orElse(null);
        if (odontologoEncontrado != null) {
            odontologoEncontrado = odontologo;
            odontologoRepository.save(odontologoEncontrado);
            odontologoSalidaDto = modelMapper.map(odontologo, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo Actualizado ID: " + odontologoEncontrado.getId());
            return odontologoSalidaDto;
        }
        else return odontologoSalidaDto;
    }


}
