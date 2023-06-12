package App.Domain

import javax.persistence.*

@Entity
class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    @Column(length=150)
    var marca: String = ""

    @Column(length=150)
    var modelo: String = ""

    @Column(length=10)
    var dominio: String = ""


}
