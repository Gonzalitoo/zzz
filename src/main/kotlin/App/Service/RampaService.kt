package App.Service

import App.Domain.*
import App.Repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class RampaService {

    @Autowired
    lateinit var repositorioRampa: RepositorioRampas

    @Autowired
    lateinit var repositorioRampaPendienteAprobacion: RepositorioRampasPendienteAprobacion

    @Autowired
    lateinit var repositorioHorarios: RepositorioAdministrador

    @Autowired
    lateinit var repositorioUsuario: RepositorioUsuario

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var vehiculoRepository: RepositorioVehiculos

    @Autowired
    lateinit var reservasRepository: RepositorioReservas


    @Transactional
    fun traerRampasParaRamapasDisponibles(): List<Rampa> {
        val horaActual = LocalDateTime.now().hour
        val todasLasRampas = repositorioRampa.findAll()
        todasLasRampas.forEach { rampa -> rampa.controlarHorarios(horaActual) }
        repositorioRampa.saveAll(todasLasRampas)
        val estadoABuscar = EstadoRampa().apply {
            nombreDeEstado = "Disponible"
        }
        return repositorioRampa.findAllByEstadoRampaEquals(estadoABuscar.nombreDeEstado)
    }

    @Transactional(readOnly = true)
    fun traerRampaPorID(id: Long): Rampa {
        val rampa = repositorioRampa.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "La rampa con identificador $id no existe") }
        rampa.controlarEstadoRampa(LocalDateTime.now().hour)
        rampa.horariosDisponibles.forEach {
            rampa.verificarHorarios(it)
        }

        return rampa
    }


    @Transactional(readOnly = false)
    fun registrarNuevaRampa(idUsuario: Long, rampaNueva: Rampa) {
        var rampaARegistrar: Rampa? =
            this.repositorioRampa.findByNroPartidaInmobiliaria(rampaNueva.nroPartidaInmobiliaria)
        if (rampaARegistrar === null) {
            val locador = usuarioService.buscarUsuaiorId(idUsuario)
            val rampaPendiente = RampaPendienteAprobacion(
                rampaNueva.posx.take(11),
                rampaNueva.posy.take(11),
                rampaNueva.calle,
                rampaNueva.altura,
                rampaNueva.nroPartidaInmobiliaria,
                rampaNueva.imagenRampa,
                rampaNueva.imagenDni,
                rampaNueva.imagenEscritura,
                locador
            )
            repositorioRampaPendienteAprobacion.save(rampaPendiente)
        } else {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La rampa con partida inmobiliaria ${rampaNueva.nroPartidaInmobiliaria} ya se encuentra registrada"
            )
        }
    }

    @Transactional(readOnly = false)
    fun verificarPropiedadRampa(idUsuario: Long, rampaNueva: Rampa) {
        val locador = usuarioService.buscarUsuaiorId(idUsuario)
        val rampaPendiente = RampaPendienteAprobacion(
            rampaNueva.posx,
            rampaNueva.posy,
            rampaNueva.calle,
            rampaNueva.altura,
            rampaNueva.nroPartidaInmobiliaria,
            rampaNueva.imagenRampa,
            rampaNueva.imagenDni,
            rampaNueva.imagenEscritura,
            locador
        )
        repositorioRampaPendienteAprobacion.save(rampaPendiente)
    }


    @Transactional
    fun reservarRampa(idRampa: Long, idUsuario: Long, reservas: List<Reserva>, dominio: String): Rampa {
        val rampa = repositorioRampa.findById(idRampa).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La Rampa con identificador $idRampa no existe"
            )
        }
        reservas.forEach {
            it.altura = rampa.altura
            it.calle = rampa.calle
            it.imagenRampa = rampa.imagenRampa
            it.rampaNroPartida = rampa.nroPartidaInmobiliaria
        }
        repositorioUsuario
            .findById(idUsuario)
            .map {
                reservasRepository.saveAll(reservas)
                it.reservasRealizadas.addAll(reservas)
                repositorioUsuario.save(it)
            }
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario con identificador $idUsuario no existe")
            }

        if (vehiculoRepository.findByDominio(dominio) == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "El vehiculo enviado es invalido")
        }

        return rampa
    }


    @Transactional
    fun agregarHorariosRampa(idRampa: Long, horariosAAgregar: List<Horarios>): Rampa {
        val rampa = this.traerRampaPorID(idRampa)
        rampa.horariosDisponibles.clear()
        val horarios = horariosAAgregar.sortedBy { it.horarioDesde }.distinctBy { Pair(it.horarioDesde, it.horarioHasta) }
        val resetHorarios = mutableListOf<Horarios>()
        for (hora in horarios){
            resetHorarios.add(Horarios().apply {
                horarioDesde = hora.horarioDesde
                horarioHasta = hora.horarioHasta
            })
        }
        resetHorarios.forEach{
            rampa.verificarHorarios(it)
        }
        repositorioRampa.save(rampa)
        //rampa.horariosDisponibles.addAll(resetHorarios)
        return rampa
    }

    @Transactional
    fun quitarHorariosRampa(idRampa: Long): Rampa {
        val rampa = this.traerRampaPorID(idRampa)
        rampa.horariosDisponibles.clear()
        return rampa
    }


}