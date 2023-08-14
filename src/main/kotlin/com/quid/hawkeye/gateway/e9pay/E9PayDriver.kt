package com.quid.hawkeye.gateway.e9pay

import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.lang.Thread.sleep
import java.time.Duration

interface E9PayDriver {
    fun init(phone: PhoneType)
    fun selectLanguage()
    fun permissionAllow()
    fun gotoRatePage()
    fun close()
    fun getRateList(): List<Rate>

    @Component
    class E9PayDriverImpl: E9PayDriver {
        private lateinit var config: AppiumConfig
        private lateinit var driver: WebDriverWait

        override fun init(phone: PhoneType) {
            this.config = AppiumConfig(phone, AppInfo.E9PAY)
            this.driver = WebDriverWait(config.driver(), Duration.ofSeconds(10))
        }

        override fun selectLanguage() {
            driver.until { it.findElement(AppiumBy.id(KOREA_BTN)).click() }
        }

        override fun permissionAllow() {
            driver.until { it.findElement(AppiumBy.id(PERMISSION_LIST_ALLOW)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_FOREGROUND_ONLY)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        }

        override fun gotoRatePage() {
            driver.until { it.findElement(AppiumBy.id(RATE_NAV_BTN)).click() }
        }

        override fun getRateList(): List<Rate> {
            var countryIndex = 1
            val test = mutableListOf<String>()
            driver.until { it.findElement(AppiumBy.id(SELECT_COUNTRY)).click() }
            sleep(1000)
            while (countryIndex<=14){
                val country = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex${POST_COUNTRY_CURRENCY_LIST}1]"))[0].text }
                val currency = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex${POST_COUNTRY_CURRENCY_LIST}2]"))[0].text }
                println("index: $countryIndex, country: $country, currency: $currency")
                driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex]"))[0].click() }
                sleep(3000)
                //횐율 가져오기


                test.add(country+currency)
                driver.until { it.findElement(AppiumBy.id(SELECT_COUNTRY)).click() }
                sleep(1000)
                countryIndex++
                val prevCountry = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST${countryIndex-1}${POST_COUNTRY_CURRENCY_LIST}1]"))[0].text }
                val prevCurrency = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST${countryIndex-1}${POST_COUNTRY_CURRENCY_LIST}2]"))[0].text }
                if(!test.contains(prevCountry+prevCurrency)){
                    countryIndex--
                }

                println("prev = $prevCountry$prevCurrency current = $country$currency")
            }
            println(test)
            return mutableListOf()
        }

        override fun close() {
            config.close()
        }

        companion object {
            private const val KOREA_BTN = "com.e9pay.remittance2:id/btn_ko"
            private const val PERMISSION_LIST_ALLOW = "com.e9pay.remittance2:id/btn_ok"
            private const val PERMISSION_ALLOW_FOREGROUND_ONLY = "com.android.permissioncontroller:id/permission_allow_foreground_only_button"
            private const val PERMISSION_ALLOW_BUTTON = "com.android.permissioncontroller:id/permission_allow_button"
            private const val RATE_NAV_BTN = "com.e9pay.remittance2:id/exchangeRate"
            private const val SELECT_COUNTRY = "com.e9pay.remittance2:id/nation_change_container"
            private const val PREV_COUNTRY_CURRENCY_LIST = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.ListView/android.view.ViewGroup["
            private const val POST_COUNTRY_CURRENCY_LIST = "]/android.widget.TextView["
            private const val RATE = "com.e9pay.remittance2:id/currentExchangeRateContent"
        }
    }
}