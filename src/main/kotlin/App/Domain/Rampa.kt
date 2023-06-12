package App.Domain


import java.time.LocalDate
import javax.persistence.*

@Entity
class Rampa {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    @Column(length = 150)
    var posx: String = ""

    @Column(length = 150)
    var posy: String = ""

    @Column(length = 150)
    var calle: String = ""

    @Column(length = 5)
    var altura: Int = 0

    @Column
    var imagenRampa: String = ""

    @Column
    var imagenDni: String = ""

    @Column
    var imagenEscritura: String = ""

    @Column(length = 5)
    var nroPartidaInmobiliaria: Int = 0

    var estadoRampa: String = "Disponible"

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var horariosDisponibles = mutableListOf<Horarios>()


    @OneToMany(fetch = FetchType.EAGER)
    var reservasRealizadas: MutableCollection<Reserva> = mutableListOf()


    fun realizarReserva(unaReserva: Reserva) {
        reservasRealizadas.add(unaReserva)
        var horarioAAgregar = mutableListOf<Horarios>()
        var horasABorrar = mutableListOf<Horarios>()
        loop@ for (horario in horariosDisponibles) {
            if (unaReserva.horaFinReserva < horario.horarioDesde || unaReserva.horaInicioReserva > horario.horarioHasta) {
                continue@loop
            }
            if (unaReserva.horaInicioReserva > horario.horarioDesde) {
                horarioAAgregar.add(Horarios().apply {
                    horarioDesde = horario.horarioDesde
                    horarioHasta = unaReserva.horaInicioReserva
                })
                if (unaReserva.horaFinReserva < horario.horarioHasta) {
                    horarioAAgregar.add(Horarios().apply {
                        horarioDesde = unaReserva.horaFinReserva
                        horarioHasta = horario.horarioHasta
                    })
                }
            } else {
                if (unaReserva.horaFinReserva < horario.horarioHasta) {
                    horarioAAgregar.add(Horarios().apply {
                        horarioDesde = unaReserva.horaFinReserva
                        horarioHasta = horario.horarioHasta
                    })
                }
            }
            horasABorrar.add(horario)
        }
        horariosDisponibles.removeAll(horasABorrar)
        horariosDisponibles.addAll(horarioAAgregar)
    }

    fun controlarHorarios(hora: Int) {
        var horasABorrar = mutableListOf<Horarios>()
        for (horario in horariosDisponibles) {
            if (hora > horario.horarioDesde) {
                if (hora >= horario.horarioHasta) {
                    horasABorrar.add(horario)
                } else {
                    horario.horarioDesde = hora
                }
            }
        }
        if (horasABorrar.isNotEmpty()) {
            horariosDisponibles.removeAll(horasABorrar)
        }
        controlarEstadoRampa(hora)
    }


    fun controlarEstadoRampa(hora: Int) {
        if (horariosDisponibles.isNotEmpty()) {
            this.estadoRampa = "Disponible"
        } else {
            if (reservasRealizadas.any { reserva -> reserva.horaInicioReserva == hora }) {
                this.estadoRampa = "Alquilada"
            } else {
                this.estadoRampa = "No Disponible"
            }
        }
    }

    fun verificarHorarios(hora: Horarios){
        val listaReal = mutableListOf<Horarios>()
        if (horariosDisponibles.isEmpty()) {
            listaReal.add(hora)
        }else{
            for (horario in horariosDisponibles) {
                if (hora.horarioDesde == horario.horarioDesde && hora.horarioHasta == horario.horarioHasta) {
                    listaReal.add(horario)
                }
                if (hora.horarioHasta < horario.horarioDesde || hora.horarioDesde > horario.horarioHasta) {
                    listaReal.add(hora)
                }
                if (hora.horarioDesde <= horario.horarioDesde) {
                    if (hora.horarioHasta >= horario.horarioHasta) {
                        listaReal.add(hora)
                    } else {
                        listaReal.add(Horarios().apply {
                            horarioDesde = hora.horarioDesde
                            horarioHasta = horario.horarioHasta
                        })
                    }
                } else {
                    if (hora.horarioHasta >= horario.horarioHasta) {
                        listaReal.add(Horarios().apply {
                            horarioDesde = horario.horarioDesde
                            horarioHasta = hora.horarioHasta
                        })
                    } else {
                        listaReal.add(horario)
                    }
                }
            }
        }
        horariosDisponibles.clear()
        horariosDisponibles.addAll(listaReal.sortedBy { it.horarioDesde }.distinctBy { Pair(it.horarioDesde, it.horarioHasta)})
    }
}

@Entity
class RampaPendienteAprobacion(

    @Column(length = 150)
    var posx: String = "",

    @Column(length = 150)
    var posy: String = "",

    @Column(length = 150)
    var calle: String = "",

    @Column(length = 5)
    var altura: Int = 0,

    @Column(length = 5)
    var nroPartidaInmobiliaria: Int = 0,


    @Column
    var imagenRampa: String = "",

    @Column
    var imagenDni: String = "",

    @Column
    var imagenEscritura: String = "",

    @OneToOne(fetch = FetchType.EAGER)
    var usuarioPropietario: Usuario = Usuario()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0
    var fechaCarga = LocalDate.now()

    var nombrePropietario = usuarioPropietario.nombre
}

