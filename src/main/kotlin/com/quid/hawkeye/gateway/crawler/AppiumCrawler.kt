package com.quid.hawkeye.gateway.crawler

import com.quid.hawkeye.domain.PhoneType.GALAXY_A30
import com.quid.hawkeye.usecase.App
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AppiumCrawler(
    private val getRateList: List<App>
) {
    private val logger = LoggerFactory.getLogger(AppiumCrawler::class.java)

    @Scheduled(fixedDelay = 100000)
    fun crawl() {
        getRateList.forEach {
            logger.info("========Crawling Start ${it.javaClass.simpleName}========")
            with(it.driver(GALAXY_A30)) {
                initApp()
                getRateList()
                    .also {
//                        save(it)
                        closeApp()
                    }
                logger.info("========Crawling End ${it.javaClass.simpleName}========")
            }
        }
    }
}
