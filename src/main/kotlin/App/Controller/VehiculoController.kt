package App.Controller


import App.Domain.Rampa
import App.Domain.Vehiculo
import App.Service.VehiculoService
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins=["*"])
class VehiculoController {

    @Autowired
    lateinit var vehiculoService: VehiculoService


    @GetMapping("/vehiculo/{id}")
    @Operation(summary = "Devuelve un vehiculo por id")
    fun traerVehiculoID(@PathVariable id: Long): Vehiculo = vehiculoService.buscarVehiculoPorId(id)

    @PutMapping("/modificarVehiculo/{id}")
    @Operation(summary ="permite modificar los datos de un vehiculo")
    fun modificarVehiculo (@PathVariable id: Long, @RequestBody vehiculoModificado : Vehiculo) = vehiculoService.modificarVehiculos(id, vehiculoModificado)

}