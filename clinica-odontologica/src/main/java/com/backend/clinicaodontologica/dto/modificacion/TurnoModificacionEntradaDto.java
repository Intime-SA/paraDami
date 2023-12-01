package com.backend.clinicaodontologica.dto.modificacion;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoModificacionEntradaDto {

    @NotNull(message = "Debe proveerse el id")
    private Long id;


    private PacienteModificacionEntradaDto pacienteModificacionEntradaDto;

    private OdontologoModificacionEntradaDto odontologoModificacionEntradaDto;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de turno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaYHora;


    public TurnoModificacionEntradaDto(Long id, PacienteModificacionEntradaDto pacienteModificacionEntradaDto, OdontologoModificacionEntradaDto odontologoModificacionEntradaDto, LocalDateTime fechaYHora) {
        this.id = id;
        this.pacienteModificacionEntradaDto = pacienteModificacionEntradaDto;
        this.odontologoModificacionEntradaDto = odontologoModificacionEntradaDto;
        this.fechaYHora = fechaYHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PacienteModificacionEntradaDto getPacienteModificacionEntradaDto() {
        return pacienteModificacionEntradaDto;
    }

    public void setPacienteModificacionEntradaDto(PacienteModificacionEntradaDto pacienteModificacionEntradaDto) {
        this.pacienteModificacionEntradaDto = pacienteModificacionEntradaDto;
    }

    public OdontologoModificacionEntradaDto getOdontologoModificacionEntradaDto() {
        return odontologoModificacionEntradaDto;
    }

    public void setOdontologoModificacionEntradaDto(OdontologoModificacionEntradaDto odontologoModificacionEntradaDto) {
        this.odontologoModificacionEntradaDto = odontologoModificacionEntradaDto;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}