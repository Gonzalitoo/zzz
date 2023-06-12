package App.Domain

import javax.persistence.*

@Entity
class Administrador {

//    companion object {
 //       @JvmStatic var instance:Administrador = Administrador()
 //   }
 //   init {
 //       instance = this
 //   }

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column(length=150)
    var nombre: String = ""

    @Column(length=150)
    var apellido: String = ""

    @Column(length=20)
    var cuil: Long = 0

    @Column(length=150)
    var userName: String = ""

    @Column(length=150)
    var contrasenia: String = ""

}