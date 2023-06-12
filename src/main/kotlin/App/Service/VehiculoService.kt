package App.Service

import App.Domain.Vehiculo
import App.Repository.RepositorioVehiculos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class VehiculoService {

    @Autowired
    lateinit var repositorioVehiculo: RepositorioVehiculos

    @Transactional(readOnly = true)
    fun buscarVehiculoPorId(id: Long): Vehiculo =
        this.repositorioVehiculo.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el vehiculo con ese id")
        }

    @Transactional
    fun modificarVehiculos(id: Long, vehiculoModificado: Vehiculo): Vehiculo {
        return repositorioVehiculo
            .findById(id)
            .map {
                it.marca = vehiculoModificado.marca
                it.modelo = vehiculoModificado.modelo
                it.dominio = vehiculoModificado.dominio
                repositorioVehiculo.save(it)
                it
            }
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "El vehiculo con identificador $id no existe")
            }
    }

}