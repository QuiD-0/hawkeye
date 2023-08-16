package com.quid.hawkeye.gateway.crawler

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.gateway.client.RateGateway
import com.quid.hawkeye.usecase.App
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AppiumCrawler(
    private val appList: List<App>,
    private val rateGateway: RateGateway
) {
    private val logger = LoggerFactory.getLogger(AppiumCrawler::class.java)

    @Scheduled(fixedDelay = 100000)
    fun crawl() {
        appList.forEach {
            try {
                logger.info("========Crawling Start ${it.javaClass.simpleName}========")
                it.initApp(PhoneType.GALAXY_A30)
                    .getRateList()
                    .also { data ->
                        rateGateway.save(data)
                        it.closeApp()
                    }
                logger.info("========Crawling End ${it.javaClass.simpleName}========")
            } catch (e: Exception) {
                logger.info("========Crawling Error ${it.javaClass.simpleName}========")
                e.printStackTrace()
            }
        }
    }
}
