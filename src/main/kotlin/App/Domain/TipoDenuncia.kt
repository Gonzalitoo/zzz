package App.Domain

import javax.persistence.*

@Entity
class TipoDenuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    @Column(length=150)
    var nombreTipo: String = ""

    @Column(length=10)
    var compensacion: Double = 0.00
}
