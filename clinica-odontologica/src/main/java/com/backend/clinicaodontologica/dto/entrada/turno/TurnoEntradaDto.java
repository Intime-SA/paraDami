package com.backend.clinicaodontologica.dto.entrada.turno;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de ingreso del paciente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El ID del odontologo no puede ser nulo")
    private Long idOdontologo;


    @NotNull(message = "El ID del paciente no puede ser nulo")
    private Long idPaciente;



    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(Long idPaciente, Long idOdontologo, LocalDateTime fechaYHora) {
        this.idPaciente = idPaciente;
        this.idOdontologo = idOdontologo;
        this.fechaYHora = fechaYHora;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Long idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}
