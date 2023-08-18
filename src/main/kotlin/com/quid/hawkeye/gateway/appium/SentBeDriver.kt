package com.quid.hawkeye.gateway.appium

import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.LoggerFactory

interface SentBeDriver {
    fun allowPermission()
    fun selectLanguage()
    fun selectCountry()


    class SentBeAppiumDriver(
        private val driver: WebDriverWait
    ):SentBeDriver {
        private val logger = LoggerFactory.getLogger(SentBeAppiumDriver::class.java)

        override fun allowPermission() {
            driver.until { it.findElement(AppiumBy.id(PERMISSION_LIST_ALLOW)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_FOREGROUND_ONLY)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        }

        override fun selectLanguage() {
            driver.until { it.findElement(AppiumBy.xpath(KOREA_BTN)).click() }
            driver.until { it.findElement(AppiumBy.id(LANGUAGE_NEXT_BTN)).click() }
        }

        override fun selectCountry() {
            driver.until { it.findElement(AppiumBy.xpath(SEND_COUNTRY_BTN)).click() }
            driver.until { it.findElement(AppiumBy.xpath(RECEIVE_COUNTRY_BTN)).click() }
            driver.until { it.findElement(AppiumBy.id(COUNTRY_CHECK_BTN)).click() }
        }

        companion object {
            const val PERMISSION_LIST_ALLOW = "com.sentbe:id/bt_confirm"
            const val PERMISSION_ALLOW_FOREGROUND_ONLY = "com.android.permissioncontroller:id/permission_allow_foreground_only_button"
            const val PERMISSION_ALLOW_BUTTON = "com.android.permissioncontroller:id/permission_allow_button"
            const val KOREA_BTN = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ScrollView/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]"
            const val LANGUAGE_NEXT_BTN = "com.sentbe:id/bt_redo_app"
            const val SEND_COUNTRY_BTN = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]"
            const val RECEIVE_COUNTRY_BTN = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]"
            const val COUNTRY_CHECK_BTN = "com.sentbe:id/bt_next"
        }
    }

}