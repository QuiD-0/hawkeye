package com.quid.hawkeye

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HawkeyeApplication

fun main(args: Array<String>) {
    runApplication<HawkeyeApplication>(*args)
}
