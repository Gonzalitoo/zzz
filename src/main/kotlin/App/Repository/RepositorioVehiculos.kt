package App.Repository


import App.Domain.Vehiculo
import org.springframework.data.repository.CrudRepository

interface RepositorioVehiculos : CrudRepository<Vehiculo, Long> {
    fun findByDominio(dominio: String): Vehiculo?
}