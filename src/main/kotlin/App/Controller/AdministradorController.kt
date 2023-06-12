package App.Controller

import App.Domain.*
import App.Service.AdministradorService
import App.Service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@CrossOrigin(origins=["*"])
class AdministradorController {

    @Autowired
    lateinit var administradorService: AdministradorService

    @PostMapping("/administrador/login")
    @Operation(summary ="Devuelve el admin cuyo username y contraseña coincide con lo que pasamos como parámetro")
    fun buscarLoguearse(@RequestBody admin: Administrador): Administrador = administradorService.buscar(admin)

    @GetMapping("/administrador/habilitarRampa/{idRampa}")
    @Operation(summary ="El admin habilita una rampa pendiente de aprobacion ")
    fun habilitarRampa(@PathVariable idRampa: Long) = administradorService.habilitarRampa(idRampa)

    @GetMapping("/administrador/rechazarRampa/{idRampa}")
    @Operation(summary ="El admin rechaza una rampa pendiente de aprobacion ")
    fun rchazarRampa(@PathVariable idRampa: Long) = administradorService.rechazarRampa(idRampa)

    @GetMapping("/administrador/rampasPendientesAprobacion")
    @Operation(summary ="Devuelve todas las rampas que se encuentran pendientes de aprobacion<")
    fun traerTodaLasRampasPendientesDeAprobacion():MutableIterable<RampaPendienteAprobacion> = administradorService.traerRampasPendientesAprobacion()

    @GetMapping("/administrador/rampasPendientesAprobacion/{idRampa}")
    @Operation(summary ="Devuelve una rampa pendiente")
    fun traerunRampasPendientesDeAprobacion(@PathVariable idRampa: Long):RampaPendienteAprobacion = administradorService.traerRampaPendientesAprobacionPorId(idRampa)

    @GetMapping("/administrador/denunciasPendientesAprobacion")
    @Operation(summary ="Devuelve todas las denuncias que se encuentran pendientes de aprobacion<")
    fun traerTodaLasDenunciasPendientesDeAprobacion():MutableIterable<Denuncia> = administradorService.traerDenunciasPendientesAprobacion()

    @GetMapping("/administrador/traerDenuncia/{idDenuncia}")
    @Operation(summary ="Devuelve una denincia por id")
    fun traerUnaDenuncia(@PathVariable idDenuncia: Long): Denuncia = administradorService.traerUnaDenuncia(idDenuncia)


    @GetMapping("/administrador/aprobarDenuncia/{idDenuncia}")
    @Operation(summary ="El admin aprueba una denuncia pendiente")
    fun aprobarUnaDenuncia(@PathVariable idDenuncia: Long): Denuncia = administradorService.aprobarDenuncia(idDenuncia)

    @GetMapping("/administrador/rechazarDenuncia/{idDenuncia}")
    @Operation(summary ="El admin rechaza una denuncia pendiente")
    fun rechazarUnaDenuncia(@PathVariable idDenuncia: Long): Denuncia = administradorService.rechazarDenuncia(idDenuncia)

    @GetMapping("/administrador/obtenerBalance/{fechaBusqueda}")
    @Operation(summary ="El admin obtiene el balance del uso de rampas")
    fun obtenerBalance(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")fechaBusqueda: LocalDate): MutableCollection<Reserva> = administradorService.obtenerBalance(fechaBusqueda)

}