package App.Domain

import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalQueries.localDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Horarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    var id: Long = 0

    var fecha = LocalDate.now()

    var horarioDesde = 0
    var horarioHasta = 0


}
