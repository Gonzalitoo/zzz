package App.Service

import App.Domain.*
import App.Repository.RepositorioDenuncias
import App.Repository.RepositorioRampas
import App.Repository.RepositorioUsuario
import App.Repository.RepositorioVehiculos
import org.apache.el.parser.AstFalse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class UsuarioService {

    @Autowired
    lateinit var repositorioUsuarios: RepositorioUsuario

    @Autowired
    lateinit var repositorioVehiculo: RepositorioVehiculos

    @Autowired
    lateinit var repositorioDenuncia: RepositorioDenuncias

    @Autowired
    lateinit var repositorioRampas: RepositorioRampas

    @Transactional(readOnly = true)
    fun buscar(usuario: Usuario): Usuario =
        this.repositorioUsuarios.findByUserNameAndContrasenia(usuario.userName, usuario.contrasenia).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario o la contraseña son incorrectas")
        }

    @Transactional(readOnly = true)
    fun getUsuario(id: Long): Usuario =
        this.repositorioUsuarios.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "No existe usuario con ese id")}

    @Transactional(readOnly = true)
    fun buscarUsuaiorId(id: Long): Usuario =
        this.repositorioUsuarios.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe")
        }

    @Transactional
    fun registrarNuevoUsuario(usuario: Usuario): Usuario {
       val usuarioARegistrar = repositorioUsuarios.findByDni(usuario.dni)
        if (usuarioARegistrar == null) {
           return repositorioUsuarios.save(usuario)
     } else {
           throw ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario con dni ${usuario.dni} ya se encuentra registrado")
      }
    }
    @Transactional
   fun agregarVehiculo(idUsuario: Long, vehiculoNuevo: Vehiculo){
       val vehiculoARegistrar = repositorioVehiculo.findByDominio(vehiculoNuevo.dominio)
       if (vehiculoARegistrar === null) {
           val usuario = this.buscarUsuaiorId(idUsuario)
           usuario.vehiculos.add(vehiculoNuevo)
           repositorioVehiculo.save(vehiculoNuevo)
       } else {
         throw ResponseStatusException(HttpStatus.NOT_FOUND, "El vehiculo con dominio ${vehiculoNuevo.dominio} ya se encuentra registrado")
       }
    }
    @Transactional
    fun eliminarVehiculoPorId(idUsuario: Long, idVehiculo: Long){
        val usuario = this.buscarUsuaiorId(idUsuario)
        val vehiculo: Vehiculo= repositorioVehiculo.findById(idVehiculo).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe")
        }
        usuario.vehiculos.remove(vehiculo)
        repositorioVehiculo.delete(vehiculo)
}
    @Transactional
    fun realizarDenuncia(idUsuario: Long, denuncia: Denuncia):Denuncia {
        val usuario = this.getUsuario(idUsuario)
        val nuevaDenuncia = Denuncia().apply {
            motivoDenuncia = denuncia.motivoDenuncia
            tipoDenuncia = denuncia.tipoDenuncia
            dominio = denuncia.dominio
            direccionRampa = denuncia.direccionRampa
            realizadaPor = usuario
            imagen = denuncia.imagen
        }
        return repositorioDenuncia.save(nuevaDenuncia)
    }
    @Transactional(readOnly = true)
    fun traerRampasPropias(idUsuario: Long): List<Rampa> {
        val rampas = this.getUsuario(idUsuario).rampasPropias
        rampas.forEach {rampa -> rampa.controlarHorarios(LocalDateTime.now().hour) }
        return rampas
    }
    @Transactional(readOnly = true)
    fun traerVehiculosPropios(idUsuario: Long): List<Vehiculo> {
        val usuario = this.getUsuario(idUsuario)
        return usuario.vehiculos
    }
    @Transactional(readOnly = true)
    fun traerReservasActivas(idUsuario: Long): List<Reserva> {
        val usuario = this.getUsuario(idUsuario)
        val horaActual = LocalDateTime.now().hour
        return usuario.reservasRealizadas.filter{reserva -> reserva.horaFinReserva > horaActual && reserva.pagado }
    }
    @Transactional(readOnly = true)
    fun traerReservasNoPagas(idUsuario: Long): List<Reserva> {
        val usuario = this.getUsuario(idUsuario)
        val horaActual = LocalDateTime.now().hour
        return usuario.reservasRealizadas.filter{reserva -> reserva.horaFinReserva > horaActual &&  !reserva.pagado }
    }
    @Transactional()
    fun pagarReservasCarrito(idUsuario: Long){
        val usuario = this.getUsuario(idUsuario)
        val horaActual = LocalDateTime.now().hour
        usuario.reservasRealizadas.filter{reserva -> reserva.horaFinReserva > horaActual && !reserva.pagado }.forEach { reserva -> reserva.pagado = true }
        for (reserva in usuario.reservasRealizadas){
            if (reserva.pagado){
                val rampa = repositorioRampas.findByNroPartidaInmobiliaria(reserva.rampaNroPartida)
                if (rampa != null) {
                    rampa.realizarReserva(reserva)
                    repositorioRampas.save(rampa)
                }
            }
        }
        repositorioUsuarios.save(usuario)
    }
    @Transactional()
    fun eliminarReservaCarritoById(idUsuario: Long, idReserva:Long){
        val usuario = this.getUsuario(idUsuario)
        val reserva = usuario.reservasRealizadas.find{reserva -> reserva.id == idReserva }
        usuario.reservasRealizadas.remove(reserva)
        repositorioUsuarios.save(usuario)
    }
}