package com.quid.hawkeye.crawler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AppiumCrawler(
    private val getRateList: List<GetRateList>
) {

    @Scheduled(fixedDelay = 100000)
    fun crawl() {
        getRateList.forEach{
            println("START Crawling $it")
            it.execute()
        }
    }

}
