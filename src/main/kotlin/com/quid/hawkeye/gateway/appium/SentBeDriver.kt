package com.quid.hawkeye.gateway.appium

import com.quid.hawkeye.domain.Rate
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import java.math.BigDecimal

interface SentBeDriver {
    fun allowPermission()
    fun selectLanguage()
    fun selectCountry()
    fun getRateList(): List<Rate>

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

        override fun getRateList(): List<Rate> {
            sleep(10000)
            val result = mutableListOf<Rate>()
            countryList.forEach { country ->
                selectCountry(country)
                val count = driver.until { it.findElements(AppiumBy.xpath(COUNTRY_LIST)).size }
                if (count==1){
                    click(0)
                    result.add(parseRate(country))
                }else{
                    click(1)
                    result.add(parseRate(country))
                    selectCountry(country)
                    click(2)
                    result.add(parseRate(country))
                }
            }
            return result
        }

        private fun parseRate(country: String): Rate = Rate(
            appName = "SentBe",
            country = country,
            sendAmount = driver.until { it.findElement(AppiumBy.id(SEND_AMOUNT)).text }.replace(",", "").toBigDecimal(),
            sendCurrency = driver.until { it.findElement(AppiumBy.id(SEND_CURRENCY)).text },
            receiveAmount = driver.until { it.findElement(AppiumBy.id(RECEIVE_AMOUNT)).text }.replace(",", "").toBigDecimal(),
            receiveCurrency = driver.until { it.findElement(AppiumBy.id(RECEIVE_CURRENCY)).text },
            remittanceType = "Bank",
        ).also { logger.info("SentBe Rate: $it") }

        private fun selectCountry(country: String) {
            driver.until { it.findElement(AppiumBy.id(COUNTRY_CLICK_BTN)) }.click()
            driver.until { it.findElement(AppiumBy.id(COUNTRY_SEARCH_BTN)) }.sendKeys(country)
        }

        private fun click(i: Int) {
            if(i==0){
                driver.until { it.findElement(AppiumBy.xpath(COUNTRY_SELECT)).click() }
            } else{
                val currency = driver.until { it.findElement(AppiumBy.xpath("$COUNTRY_CURRENCY_PREFIX[$i]$COUNTRY_CURRENCY_SUFFIX")).text }.split("/")[1].replace(" ","")
                driver.until { it.findElement(AppiumBy.xpath("$COUNTRY_SELECT[$i]")).click() }
                if(currency == "USD"){
                    driver.until { it.findElement(AppiumBy.id(PROCEED_BTN)).click() }
                }
            }
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
            const val COUNTRY_CLICK_BTN = "com.sentbe:id/cl_recieve_amount_country"
            const val COUNTRY_SEARCH_BTN = "com.sentbe:id/et_search_country"
            const val COUNTRY_LIST = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/*"
            const val COUNTRY_SELECT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup"
            const val COUNTRY_CURRENCY_PREFIX = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup"
            const val COUNTRY_CURRENCY_SUFFIX = "/android.widget.TextView"
            const val PROCEED_BTN = "com.sentbe:id/tv_proceed_button"
            const val SEND_AMOUNT = "com.sentbe:id/sending_amount_input"
            const val RECEIVE_AMOUNT = "com.sentbe:id/recieve_amount_input"
            const val SEND_CURRENCY = "com.sentbe:id/sending_amount_country_text"
            const val RECEIVE_CURRENCY = "com.sentbe:id/recieve_amount_country_text"

            val countryList = listOf(
                "나이지리아", "남아프리카공화국", "네덜란드", "네팔", "뉴질랜드", "덴마크", "독일", "라트비아", "러시아", "룩셈부르크", "리투아니아", "말레이시아", "모나코", "몰타", "몽골", "미국", "미얀마","방글라데시",
                "베트남", "벨기에", "불가리아", "스리랑카", "스웨덴", "스페인", "싱가포르", "아일랜드", "에스토니아", "영국", "오스트리아" ,"우즈베키스탄", "우크라이나", "이탈리아", "인도","인도네시아","일본","중국",
                "카자흐스탄", "캄보디아", "캐나다", "키르기스스탄", "키프로스", "타지키스탄", "태국", "터키", "파키스탄", "포르투갈","폴란드","프랑스","핀란드","필리핀","호주","홍콩"
            )
        }
    }

}