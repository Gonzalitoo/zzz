package App.Domain

import javax.persistence.*

@Entity
class EstadoDenuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    @Column(length=150)
    var nombreDeEstado: String = ""
}
