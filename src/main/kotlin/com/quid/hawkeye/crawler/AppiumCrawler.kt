package com.quid.hawkeye.crawler

import com.quid.hawkeye.app.usecase.GetRateList
import com.quid.hawkeye.app.domain.PhoneType.GALAXY_A30
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AppiumCrawler(
    private val getRateList: List<GetRateList>
) {
    private val logger = LoggerFactory.getLogger(AppiumCrawler::class.java)

    @Scheduled(fixedDelay = 100000)
    fun crawl() {
        getRateList.forEach {
            logger.info("========Crawling Start ${it.javaClass.simpleName}========")
            it.execute(GALAXY_A30).also { data -> println(data) }
        }
    }
}
