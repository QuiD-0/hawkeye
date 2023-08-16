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
    class E9PayDriverImpl : E9PayDriver {
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
            val history = mutableListOf<String>()
            selectCountry()
            germanTest()
////            val pageCount = getPageCount()
////            while (countryIndex<=pageCount){
////                val country = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex${POST_COUNTRY_CURRENCY_LIST}1]"))[0].text }
////                val currency = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex${POST_COUNTRY_CURRENCY_LIST}2]"))[0].text }
////                driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex]"))[0].click() }
////                val typeCount = getRemittanceTypeCount()
////                if (typeCount==1){
////                    sleep(500)
////                    getRateInfo(country)
////                } else{
////                    for (typeIndex in 1 .. typeCount){
////                        driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_SELECTOR)).click() }
////                        sleep(500)
////                        driver.until { it.findElements(AppiumBy.xpath("$REMITTANCE_TYPE[$typeIndex]"))[0].click() }
////                        sleep(500)
////                        getRateInfo(country)
////                    }
////                }
////                history.add(country+currency)
////                selectCountry()
////                countryIndex++
////                val prevCode = getPrevCode(countryIndex)
////                if(!history.contains(prevCode)){
////                    countryIndex--
////                }
//            }
            return mutableListOf()
        }

        private fun germanTest() {
            driver.until { it.findElement(AppiumBy.id("com.e9pay.remittance2:id/search_editText")).sendKeys("독") }
            driver.until {
                it.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.ListView/android.view.ViewGroup[1]"))
                    .click()
            }
            retry(3) {
                driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_SELECTOR)).click() }
                sleep(500)
                driver.until { it.findElements(AppiumBy.xpath("$REMITTANCE_TYPE[1]"))[0].click() }
            }
            getRateInfo("독일")
            selectCountry()
        }

        private fun getPageCount(): Int {
            return driver.until { it.findElements(AppiumBy.xpath(COUNTRY_LIST_COUNT)).size }
        }

        private fun getRateInfo(country: String) {
            val type = driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_TEXT)).text }
            val sendAmount = driver.until { it.findElement(AppiumBy.id(SEND_AMOUNT)).text }
            val sendCurrency = driver.until { it.findElement(AppiumBy.id(SEND_CURRENCY)).text }
            val receiveAmount = driver.until { it.findElement(AppiumBy.id(RECEIVE_AMOUNT)).text }
            val receiveCurrency = driver.until { it.findElement(AppiumBy.id(RECEIVE_CURRENCY)).text }
            val rate = driver.until { it.findElement(AppiumBy.id(RATE)).text }
            println("rateInfo : $country, $type, $sendAmount, $sendCurrency, $receiveAmount, $receiveCurrency, $rate")
        }

        private fun getRemittanceTypeCount(): Int {
            var typeCount = 1
            retry(3) {
                driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_SELECTOR)).click() }
                typeCount = driver.until { it.findElements(AppiumBy.xpath(REMITTANCE_TYPE_LIST)).size }
                driver.until { it.findElements(AppiumBy.xpath("$REMITTANCE_TYPE[1]"))[0].click() }
            }
            return typeCount
        }

        private fun selectCountry() {
            driver.until { it.findElement(AppiumBy.id(SELECT_COUNTRY)).click() }
            sleep(500)
        }

        private fun getPrevCode(countryIndex: Int): String {
            return driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST${countryIndex - 1}${POST_COUNTRY_CURRENCY_LIST}1]"))[0].text } +
                    driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST${countryIndex - 1}${POST_COUNTRY_CURRENCY_LIST}2]"))[0].text }
        }

        override fun close() {
            config.close()
        }

        private fun retry(i: Int, function: () -> Unit) {
            try {
                function()
            } catch (e: Exception) {
                if (i > 0) {
                    println("retry")
                    sleep(3000)
                    retry(i - 1, function)
                } else {
                    throw e
                }
            }
        }

        companion object {
            private const val KOREA_BTN = "com.e9pay.remittance2:id/btn_ko"
            private const val PERMISSION_LIST_ALLOW = "com.e9pay.remittance2:id/btn_ok"
            private const val PERMISSION_ALLOW_FOREGROUND_ONLY = "com.android.permissioncontroller:id/permission_allow_foreground_only_button"
            private const val PERMISSION_ALLOW_BUTTON = "com.android.permissioncontroller:id/permission_allow_button"
            private const val RATE_NAV_BTN = "com.e9pay.remittance2:id/exchangeRate"
            private const val SELECT_COUNTRY = "com.e9pay.remittance2:id/nation_change_container"
            private const val COUNTRY_LIST_COUNT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.ListView/*"
            private const val PREV_COUNTRY_CURRENCY_LIST = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.ListView/android.view.ViewGroup["
            private const val POST_COUNTRY_CURRENCY_LIST = "]/android.widget.TextView["
            private const val REMITTANCE_TYPE_SELECTOR = "com.e9pay.remittance2:id/spinner"
            private const val REMITTANCE_TYPE_LIST = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/*"
            private const val REMITTANCE_TYPE = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView"
            private const val REMITTANCE_TYPE_TEXT = "android:id/text1"
            private const val SEND_AMOUNT = "com.e9pay.remittance2:id/editText"
            private const val SEND_CURRENCY = "com.e9pay.remittance2:id/remittanceMoneyUnit"
            private const val RECEIVE_AMOUNT = "com.e9pay.remittance2:id/editText2"
            private const val RECEIVE_CURRENCY = "com.e9pay.remittance2:id/oCurrencyCode"
            private const val RATE = "com.e9pay.remittance2:id/currentExchangeRateContent"
        }
    }
}