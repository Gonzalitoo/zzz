package App.Domain

import java.time.LocalDate
import java.time.LocalDateTime

import javax.persistence.*

@Entity
class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    @Column(length=20)
    var horaInicioReserva= 0

    @Column(length=20)
    var horaFinReserva= 0
    @Column(length=20)
    var fechaReserva: LocalDate = LocalDate.now()

    @Column(length=15)
    var importePagado: Double = 0.00

    @Column
    var pagado = false

    @Column(length=150)
    var calle: String = ""

    @Column(length=5)
    var altura: Int = 0

    @Column
    var imagenRampa:String = ""

    @Column
    var rampaNroPartida:Int = 0
}
