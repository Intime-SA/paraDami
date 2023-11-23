package com.backend.clinicaodontologica.controller;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/turnos")
public class TurnosController {

    private ITurnoService iTurnoService;


    public TurnosController(ITurnoService iTurnoService) {
        this.iTurnoService = iTurnoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> crearTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto){
        return new ResponseEntity<>(iTurnoService.crearTurno(turnoEntradaDto), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public  ResponseEntity<List<TurnoSalidaDto>> listar() {
        return new ResponseEntity<>(iTurnoService.turnoSalidaDtos(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable @Valid Long id){
        iTurnoService.eliminarTurno(id);
        return "Eliminado con exito. IDENTIFICACION " + id;
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoSalidaDto> actualizar (@PathVariable @Valid @RequestBody Long id, TurnoEntradaDto turnoEntradaDto) {
        return new ResponseEntity<TurnoSalidaDto>(iTurnoService.actualizarTurno(id, turnoEntradaDto), HttpStatus.OK);
    }
}
