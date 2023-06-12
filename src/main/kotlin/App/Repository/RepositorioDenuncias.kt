package App.Repository

import App.Domain.Denuncia
import org.springframework.data.repository.CrudRepository

interface RepositorioDenuncias: CrudRepository<Denuncia, Long> {
    fun findAllByEstadoDenuncia(estado : String): MutableIterable<Denuncia>
}