package com.quid.hawkeye.gateway.appium

import com.quid.hawkeye.domain.Rate
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep

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
                driver.until { it.findElement(AppiumBy.id(COUNTRY_CLICK_BTN)).click() }
                driver.until { it.findElement(AppiumBy.id(COUNTRY_SEARCH_BTN)).sendKeys(country) }
                val count = driver.until { it.findElements(AppiumBy.xpath(COUNTRY_LIST)).size }
                if (count==1){
                    val currency = driver.until { it.findElement(AppiumBy.id("com.sentbe:id/tv_new_country_name")).text }.split("/")[1].replace(" ","")
                    driver.until { it.findElement(AppiumBy.accessibilityId("위로 이동")).click() }
                }else{
                    for(i in 1..count){
                        println("index = $i")
                    }
                    driver.until { it.findElement(AppiumBy.accessibilityId("위로 이동")).click() }
                }

            }
            return emptyList()
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


            val  countryList = listOf("러시아", "홍콩")
//            val countryList = listOf(
//                "나이지리아", "남아프리카공화국", "네덜란드", "네팔", "뉴질랜드", "덴마크", "독일", "라트비아", "러시아", "룩셈부르크", "리투아니아", "말레이시아", "모나코", "몰타", "몽골", "미국", "미얀마","방글라데시",
//                "베트남", "벨기에", "불가리아", "스리랑카", "스웨덴", "스페인", "싱가포르", "아일랜드", "에스토니아", " 영국", "오스트리아" ,"우즈베키스탄", "우크라이나", "이탈리아", "인도","인도네시아","일본","중국",
//                "카자흐스탄", "캄보디아", "캐나다", "키르기스스탄", "키프로스", "타지키스탄", "태국", "터키", "파키스탄", "포르투갈","폴란드","프랑스","핀란드","필리핀","호주","홍콩"
//            )
        }
    }

}