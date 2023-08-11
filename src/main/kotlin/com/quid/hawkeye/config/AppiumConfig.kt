package com.quid.hawkeye.config

import com.quid.hawkeye.app.AppInfo
import com.quid.hawkeye.app.PhoneType
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.remote.MobilePlatform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

class AppiumConfig(
    private val phone: PhoneType,
    private val app: AppInfo
) {

    private lateinit var driver: WebDriver

    private fun getOptions(): DesiredCapabilities =
        DesiredCapabilities().apply {
            setCapability("udid", phone.udid)
            setCapability("platformVersion", phone.osVersion)
            setCapability("appPackage", app.packageName)
            setCapability("appActivity", app.activityName)
            setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID)
            setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2)
        }

    fun driver(): WebDriver = AndroidDriver(
        URL("http://127.0.0.1:4725"), getOptions()
    ).also { driver = it }

    fun close() {
        driver.quit()
    }

}