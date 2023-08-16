package com.quid.hawkeye.gateway.appium

import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.LoggerFactory

interface SentBeDriver {

    class SentBeAppiumDriver(
        private val driver: WebDriverWait
    ):SentBeDriver {
        private val logger = LoggerFactory.getLogger(SentBeAppiumDriver::class.java)
    }
}