package App

import App.Domain.*
import App.Repository.RepositorioAdministrador
import App.Repository.RepositorioRampas
import App.Repository.RepositorioUsuario
import App.Repository.RepositorioVehiculos
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Bootstrap : InitializingBean {

    @Autowired
    private lateinit var repositorioUsuarios: RepositorioUsuario

    @Autowired
    lateinit var repositorioRampas: RepositorioRampas

    @Autowired
    lateinit var repositorioVehiculo: RepositorioVehiculos

    @Autowired
    lateinit var repositorioAdministrador: RepositorioAdministrador

    fun crearUsuario() {
        var usuario1 = Usuario().apply {
            nombre = "Tomas"
            apellido = "Gomez"
            dni = 34101124
            fechaDeNacimiento= LocalDate.now().minusYears(26)
            userName = "TGomez"
            contrasenia = "1"
            email= "tgomez@ramapp.com"
        }

        val usuario2 = Usuario().apply {
            nombre = "Jane"
            apellido = "Fernadez"
            dni = 33211124
            fechaDeNacimiento= LocalDate.now().minusYears(19)
            userName = "JFernandez"
            contrasenia = "2"
            email= "jfernandez@ramapp.com"

        }

        val usuario3 =Usuario().apply {
            nombre = "Ezequiel"
            apellido = "Gago"
            dni = 31211124
            fechaDeNacimiento= LocalDate.now().minusYears(62)
            userName = "EGago"
            contrasenia = "3"
            email= "Egago@ramapp.com"

        }

        val usuario4 = Usuario().apply {
            nombre = "Pablo"
            apellido = "Acme"
            dni = 29211124
            fechaDeNacimiento= LocalDate.now().minusYears(35)
            userName = "PAcme"
            contrasenia = "4"
            email= "Pacme@ramapp.com"

        }

        val rampa1 = Rampa().apply{
            calle = "corrientes"
            altura = 132
            posx= "-34.6175963"
            posy= "-58.5579883"
            imagenRampa = "https://imgur.com/gallery/kgDiYpo"
            nroPartidaInmobiliaria = 24124
        }

        val rampa2 = Rampa().apply{
            calle = "Borges"
            altura = 5215
            nroPartidaInmobiliaria = 26434
        }

        val rampa3 = Rampa().apply{
            calle = "Santa Fe"
            altura = 4234
            posx= "-34.6175963"
            posy= "-58.5579883"
            nroPartidaInmobiliaria = 46032
        }

        val vehiculo1 = Vehiculo().apply{
            marca = "Ford"
            modelo = "Fiesta"
            dominio = "KEQ989"
        }

        val vehiculo2 = Vehiculo().apply{
            marca = "Ford"
            modelo = "Mondeo"
            dominio = "PEO342"
        }



        usuario1.rampasPropias.add(rampa1)
        usuario2.rampasPropias.add(rampa3)
        usuario2.rampasPropias.add(rampa2)
        usuario1.vehiculos.add(vehiculo1)
        usuario4.vehiculos.add(vehiculo2)

        repositorioUsuarios.save(usuario1)
        repositorioUsuarios.save(usuario2)
        repositorioUsuarios.save(usuario3)
        repositorioUsuarios.save(usuario4)

        repositorioVehiculo.save(vehiculo1)
        repositorioVehiculo.save(vehiculo2)
        repositorioRampas.save(rampa1)
        repositorioRampas.save(rampa2)
        repositorioRampas.save(rampa3)



    }

    override fun afterPropertiesSet() {
        crearUsuario()
        crearAdministrador()
        crearEstadoDenuncia()
    }

    fun crearAdministrador() {
        val admin1 = Administrador().apply {
            nombre = "Edgardo"
            apellido = "Ramirez "
            cuil = 30292111241
            userName = "admin"
            contrasenia = "admin"
        }

        repositorioAdministrador.save(admin1)

    }

    fun crearEstadoDenuncia() {
        val denunciaPendienteAprobacion = EstadoDenuncia().apply {
            id = 1
            nombreDeEstado = "Denuncia Pendiente Aprobacion"
        }

        val denunciaAprobada = EstadoDenuncia().apply {
            id = 2
            nombreDeEstado = "Denuncia Aprobada"
        }

        val denunciaRechazada = EstadoDenuncia().apply {
            id = 3
            nombreDeEstado = "Denuncia Rechazada"
        }

    }
}