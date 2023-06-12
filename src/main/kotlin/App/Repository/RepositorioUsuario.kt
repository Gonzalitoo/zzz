package App.Repository


import App.Domain.Usuario
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RepositorioUsuario  : CrudRepository<Usuario, Long> {

    fun findByUserNameAndContrasenia(userName: String, contrasenia:String): Optional<Usuario>

     fun findByDni(dni: Long): Usuario?

    override fun findById(id: Long): Optional<Usuario>
}