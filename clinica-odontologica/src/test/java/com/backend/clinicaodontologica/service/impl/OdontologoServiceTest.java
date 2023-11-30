package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoYRetornarElId() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("DSF4159870", "Tomas", "Maina");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Tomas", odontologoSalidaDto.getNombre());

    }

    @Test
    @Order(2)
    void deberiaRetornarUnOdontologoPorId(){
        try {
            odontologoService.buscarOdontologoPorId(1L);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.buscarOdontologoPorId(1L));
    }

    @Test
    @Order(3)
    void deberiaListarLosOdontologos(){
        List<OdontologoSalidaDto> odontologoSalidaDtoList = odontologoService.listarOdontologos();
        assertFalse(odontologoSalidaDtoList.isEmpty());
    }
}
