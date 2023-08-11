package com.quid.hawkeye.domain

enum class AppInfo(
    val packageName: String,
    val activityName: String,
) {
    E9PAY(
        packageName = "com.e9pay.remittance2",
        activityName = "com.e9pay.remittance.main.IntroActivity",
    ),
}