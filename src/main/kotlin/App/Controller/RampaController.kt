package App.Controller

import App.Domain.Horarios
import App.Domain.Rampa
import App.Domain.Reserva

import App.Domain.Usuario
import App.Service.RampaService
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins=["*"])
class RampaController {

    @Autowired
    lateinit var rampaService: RampaService

    @GetMapping("/rampasDisponibles")
    @Operation(summary ="Devuelve todas las rampas que se encuentran Disponible")
    fun traerTodaLasRampasDisponibles():List<Rampa> = rampaService.traerRampasParaRamapasDisponibles()

    @GetMapping("/rampaDisponible/{id}")
    @Operation(summary ="permite buscar una rampa por su id")
    fun buscarUnaRampa(@PathVariable id: Long): Rampa = rampaService.traerRampaPorID(id)

    @PutMapping("/agregarRampa/{idUsuario}")
    @Operation(summary ="crea una rampa y se la agrega al usuario")
    fun registrarRampa(@PathVariable idUsuario: Long, @RequestBody rampaNueva : Rampa) = rampaService.registrarNuevaRampa(idUsuario,rampaNueva)

    @PutMapping("/verificarPropiedadRampa/{idUsuario}")
    @Operation(summary ="crea una rampa ya registrada y se la agrega al usuario")
    fun verificarPropiedadRampa(@PathVariable idUsuario: Long, @RequestBody rampaNueva : Rampa) = rampaService.verificarPropiedadRampa(idUsuario,rampaNueva)

//    @PutMapping("/modificarHorarioRampa/{idRampa}")
//    @Operation(summary ="permite agregar o quitar un horario de disponibilidad de la rampa")
//    fun modifiarHorarioRampa(@PathVariable idRampa: Long, @RequestBody rampaModificadora: Rampa): Rampa = rampaService.modificarHorariosRampa(idRampa,rampaModificadora)

//    @PutMapping("/agregarHorarioRampa/{idRampa}")
//    @Operation(summary ="permite agregar un horario de disponibilidad de la rampa")
//    fun agregarHorarioRampa(@PathVariable idRampa: Long, @RequestBody horarioAAgregar: Horarios): Rampa = rampaService.agregarHorariosRampa(idRampa,horarioAAgregar)

    //Para varios horarios
    @PutMapping("/agregarHorarioRampa/{idRampa}")
    @Operation(summary ="permite agregar un horario de disponibilidad de la rampa")
    fun agregarHorarioRampa(@PathVariable idRampa: Long, @RequestBody horariosAAgregar: List<Horarios>): Rampa = rampaService.agregarHorariosRampa(idRampa,horariosAAgregar)

    @DeleteMapping("/eliminarHorarioRampa/{idRampa}")
    @Operation(summary ="permite eliminar un horario de disponibilidad de la rampa")
    fun quitarHorarioRampa(@PathVariable idRampa: Long): Rampa = rampaService.quitarHorariosRampa(idRampa)

//    @PutMapping("/reservarRampa/{idRampa}/{idUsuario}")
//    @Operation(summary ="permite agregar reservar una rampa")
//    fun reservarRampa(@PathVariable idRampa: Long, @PathVariable idUsuario: Long, @RequestBody reserva: Reserva): Rampa = rampaService.reservarRampa(idRampa,idUsuario,reserva)

    @PutMapping("/reservarRampa/{idRampa}/{idUsuario}/{dominioVehiculo}")
    @Operation(summary ="permite agregar reservar una rampa")
    fun reservarRampa(@PathVariable idRampa: Long, @PathVariable idUsuario: Long, @RequestBody reservas: List<Reserva>,@PathVariable dominioVehiculo:String ): Rampa = rampaService.reservarRampa(idRampa,idUsuario,reservas,dominioVehiculo)
}