package App

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class RamApp

fun main(args: Array<String>) {
    runApplication<RamApp>(*args)
}