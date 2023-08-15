package com.quid.hawkeye.domain

enum class PhoneType(
    val udid: String,
    val osVersion: String,
) {
    GALAXY_A30(
        udid = "R59MB06MZLM",
        osVersion = "11.0",
    ),
    GALAXY_S10E(
        udid = "R39M504EM7D",
        osVersion = "12.0",
    ),
}