package App.Repository


import App.Domain.Rampa
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface RepositorioRampas : CrudRepository<Rampa, Long> {

    // Esto no va a funcionar voy a hacer una query
    fun findAllByEstadoRampaEquals(estado: String): List<Rampa>
    fun findByNroPartidaInmobiliaria(nroPartidaInmobiliaria: Int): Rampa?
    fun findAllByReservasRealizadasFechaReservaEquals(fechaBusqueda: LocalDate): List<Rampa>
}