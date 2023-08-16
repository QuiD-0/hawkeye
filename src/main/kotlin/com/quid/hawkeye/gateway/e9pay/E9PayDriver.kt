package com.quid.hawkeye.gateway.e9pay

import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.Rate
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import java.math.BigDecimal

interface E9PayDriver {
    fun selectLanguage()
    fun permissionAllow()
    fun gotoRatePage()
    fun getRateList(): List<Rate>

    class E9PayAppiumDriver(
        private val driver: WebDriverWait
    ) : E9PayDriver {
        private val logger = LoggerFactory.getLogger(E9PayAppiumDriver::class.java)

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
            val rateList = mutableListOf<Rate>()
            selectCountry()
            val pageCount = getPageCount()
            while (countryIndex<=pageCount){
                val country = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex${POST_COUNTRY_CURRENCY_LIST}1]"))[0].text }
                val currency = driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex${POST_COUNTRY_CURRENCY_LIST}2]"))[0].text }
                driver.until { it.findElements(AppiumBy.xpath("$PREV_COUNTRY_CURRENCY_LIST$countryIndex]"))[0].click() }
                val typeCount = getRemittanceTypeCount()
                if (typeCount==1){
                    sleep(500)
                    rateList.add(getRateInfo(country))
                } else{
                    for (typeIndex in 1 .. typeCount){
                        sleep(500)
                        driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_SELECTOR)).click() }
                        sleep(500)
                        driver.until { it.findElements(AppiumBy.xpath("$REMITTANCE_TYPE[$typeIndex]"))[0].click() }
                        sleep(500)
                        rateList.add(getRateInfo(country))
                    }
                }
                history.add(country+currency)
                selectCountry()
                countryIndex++
                val prevCode = getPrevCode(countryIndex)
                if(!history.contains(prevCode)){
                    countryIndex--
                }
            }
            return rateList
        }

        private fun getPageCount(): Int {
            return driver.until { it.findElements(AppiumBy.xpath(COUNTRY_LIST_COUNT)).size }
        }

        private fun getRateInfo(country: String): Rate {
            val type = driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_TEXT)).text }
            val sendAmount = driver.until { it.findElement(AppiumBy.id(SEND_AMOUNT)).text }
            val sendCurrency = driver.until { it.findElement(AppiumBy.id(SEND_CURRENCY)).text }
            val receiveAmount = driver.until { it.findElement(AppiumBy.id(RECEIVE_AMOUNT)).text }
            val receiveCurrency = driver.until { it.findElement(AppiumBy.id(RECEIVE_CURRENCY)).text }
            return Rate(
                appName = AppInfo.E9PAY.name,
                country = country,
                remittanceType = type,
                sendAmount = sendAmount.replace(",","").toBigDecimal(),
                sendCurrency = sendCurrency,
                receiveAmount = receiveAmount.replace(",","").toBigDecimal(),
                receiveCurrency = receiveCurrency,
            ).also { logger.info("$it") }
        }


        private fun getRemittanceTypeCount(): Int {
            var typeCount = 1
            retry(3) {
                driver.until { it.findElement(AppiumBy.id(REMITTANCE_TYPE_SELECTOR)).click() }
                sleep(500)
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

        private fun retry(i: Int, function: () -> Unit) {
            try {
                function()
            } catch (e: Exception) {
                if (i > 0) {
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