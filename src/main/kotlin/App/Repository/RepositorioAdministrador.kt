package App.Repository

import App.Domain.Administrador

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface RepositorioAdministrador: CrudRepository<Administrador, Long> {

    fun findByUserNameAndContrasenia(userName: String, contrasenia: String): Optional<Administrador>


}