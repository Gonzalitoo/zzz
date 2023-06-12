package App.Repository

import App.Domain.Reserva
import org.springframework.data.repository.CrudRepository

interface RepositorioReservas: CrudRepository<Reserva, Long> {
}