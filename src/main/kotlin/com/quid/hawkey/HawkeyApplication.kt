package com.quid.hawkey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HawkeyApplication

fun main(args: Array<String>) {
    runApplication<HawkeyApplication>(*args)
}
