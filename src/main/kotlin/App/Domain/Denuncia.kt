package App.Domain

import java.time.LocalDate

import javax.persistence.*

@Entity
class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    var fecha: LocalDate = LocalDate.now()
    var tipoDenuncia: String = ""

    var direccionRampa: String = ""

    var estadoDenuncia: String = "Pendiente"

    var dominio: String= ""

    var motivoDenuncia: String= ""

    @OneToOne(fetch= FetchType.EAGER)
    @OrderColumn
    var realizadaPor: Usuario = Usuario()

    var imagen: String = ""

    fun aprobarDenuncia(){
        estadoDenuncia = "Aprobada"
    }

    fun rechazarDenuncia(){
        estadoDenuncia = "Rechazada"
    }
}
