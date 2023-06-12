package App.Domain

//import app.serializers.View
//import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDate
import javax.persistence.*
//import javax.validation.constraints.Email

@Entity
open class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var id: Long = 0

    @Column(length=150)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var nombre: String = ""

    @Column(length=150)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var apellido: String = ""

    @Column(length=12)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var dni: Long = 0

    @Column(length=12)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var fechaDeNacimiento: LocalDate = LocalDate.now()

    @Column(length=150)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var userName: String = ""

    @Column(length=150)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var contrasenia: String = ""

    @Column(length=150)
//    @JsonView(View.Usuario.DatosBasicos::class)
    var email: String = ""

    @OneToMany(fetch=FetchType.EAGER, cascade= [CascadeType.ALL])
    @OrderColumn
    @JoinColumn(name = "usuarioId")
    var rampasPropias= mutableListOf<Rampa>()


    @OneToMany(fetch= FetchType.EAGER, cascade= [CascadeType.ALL])
    @OrderColumn
    @JoinColumn(name = "usuarioId")
    var vehiculos= mutableListOf<Vehiculo>()

    @OneToMany(fetch= FetchType.EAGER)
    @OrderColumn
    @JoinColumn(name = "usuarioId")
    var reservasRealizadas= mutableListOf<Reserva>()
}
