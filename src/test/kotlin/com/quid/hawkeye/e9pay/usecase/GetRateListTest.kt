package com.quid.hawkeye.e9pay.usecase

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.remote.MobilePlatform
import org.junit.jupiter.api.Test
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration


class GetRateListTest {

    @Test
    fun `test execute`() {
        val options = DesiredCapabilities().apply {
            setCapability("udid", "R59MB06MZLM")
            setCapability("appPackage", "com.e9pay.remittance2")
            setCapability("appActivity", "com.e9pay.remittance.main.IntroActivity")
            setCapability("platformVersion", "11.0")
            setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID)
            setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2)
        }

        val driver = AndroidDriver(URL("http://127.0.0.1:4725"), options)

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until{ driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[1]")).text.also { print(it) } }
//        wait.until { driver.findElement(AppiumBy.id("com.e9pay.remittance2:id/btn_ko")).text.also { println(it) } }
    }
}