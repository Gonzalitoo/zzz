package App.Controller

import App.Domain.*
import App.Service.UsuarioService

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins=["*"])
class UsuarioController {

    @Autowired
    lateinit var usuarioService: UsuarioService

    @PostMapping("/usuario/login")
    @Operation(summary = "Devuelve el usuario cuyo username y contraseña coincide con lo que pasamos como parámetro")
    fun buscarLoguearse(@RequestBody usuario: Usuario): Usuario = usuarioService.buscar(usuario)

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Devuelve usuario por id")
    fun traerUsuario(@PathVariable id: Long) = usuarioService.getUsuario(id)

    @PutMapping("/usuario/registrar")
    @Operation(summary = "crea un usuario que no esta registrado con Dni")
    fun registrarUsuario(@RequestBody usuario: Usuario): Usuario = usuarioService.registrarNuevoUsuario(usuario)


    @PutMapping("/agregarVehiculo/{idUsuario}")
    @Operation(summary = "permite agregar un vehiculo no registrado")
    fun agregarVehiculoUsuario(@PathVariable idUsuario: Long, @RequestBody vehiculoNuevo: Vehiculo) =
        usuarioService.agregarVehiculo(idUsuario, vehiculoNuevo)

    @DeleteMapping("/eliminarVehiculo/{idUsuario}/{idVehiculo}")
    @Operation(summary = "Elimina un vehiculo por id")
    fun eliminarVehiculoID(@PathVariable idUsuario: Long, @PathVariable idVehiculo: Long) =
        usuarioService.eliminarVehiculoPorId(idUsuario, idVehiculo)

    @PutMapping("/realizarDenuncia/{idUsuario}")
    @Operation(summary = "registra una denuncia")
    fun crearDenuncia(@PathVariable idUsuario: Long, @RequestBody denuncia: Denuncia) =
        usuarioService.realizarDenuncia(idUsuario, denuncia)

    @GetMapping("/usuario/rampasPropias/{idUsuario}")
    @Operation(summary = "Devuelve las rampas que posee un usuario")
    fun traerRampasPropias(@PathVariable idUsuario: Long): List<Rampa> = usuarioService.traerRampasPropias(idUsuario)

    @GetMapping("/usuario/vehiculosPropios/{idUsuario}")
    @Operation(summary = "Devuelve los vehiculos de un usuario")
    fun traerVehiculosRegistrados(@PathVariable idUsuario: Long): List<Vehiculo> =
        usuarioService.traerVehiculosPropios(idUsuario)

    @GetMapping("/usuario/reservasActivas/{idUsuario}")
    @Operation(summary = "Devuelve las reservas que realizo un usuario")
    fun traerReservasRealizadas(@PathVariable idUsuario: Long): List<Reserva> = usuarioService.traerReservasActivas(idUsuario)

    @GetMapping("/usuario/carrito/{idUsuario}")
    @Operation(summary = "Devuelve las reservas (no pagas) que realizo un usuario")
    fun traerCarrito(@PathVariable idUsuario: Long): List<Reserva> = usuarioService.traerReservasNoPagas(idUsuario)


    @PostMapping("/usuario/carrito/{idUsuario}/pagar")
    @Operation(summary = "Pagar reservas del carrito")
    fun pagarReservasCarrito(@PathVariable idUsuario: Long) = usuarioService.pagarReservasCarrito(idUsuario)

    @DeleteMapping("/usuario/carrito/{idUsuario}/borrar/{idReserva}")
    @Operation(summary = "Elimina una reserva del carrito por id")
    fun eliminarReservaCarrito(@PathVariable idUsuario: Long, @PathVariable idReserva: Long) =
        usuarioService.eliminarReservaCarritoById(idUsuario, idReserva)
}