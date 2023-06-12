package App.Repository

import App.Domain.Rampa
import App.Domain.RampaPendienteAprobacion
import org.springframework.data.repository.CrudRepository

interface RepositorioRampasPendienteAprobacion : CrudRepository<RampaPendienteAprobacion, Long> {
}