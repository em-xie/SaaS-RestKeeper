package com.restkeeper.operator.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.Optional;

/*@Configuration
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@ConditionalOnClass({CommonFilter.class})
@ConditionalOnProperty(
        name = {"spring.cloud.sentinel.enabled"},
        matchIfMissing = true
)*/
//@EnableConfigurationProperties({SentinelProperties.class})
public class SentinelWebAutoConfiguration {
    //private static final Logger log = LoggerFactory.getLogger(com.alibaba.cloud.sentinel.SentinelWebAutoConfiguration.class);
    //    @Autowired
    //    private SentinelProperties properties;
    @Autowired
    private Optional<UrlCleaner> urlCleanerOptional;
    @Autowired
    private Optional<UrlBlockHandler> urlBlockHandlerOptional;
    @Autowired
    private Optional<RequestOriginParser> requestOriginParserOptional;

    public SentinelWebAutoConfiguration() {
    }

    @PostConstruct
    public void init() {
        this.urlBlockHandlerOptional.ifPresent(WebCallbackManager::setUrlBlockHandler);
        this.urlCleanerOptional.ifPresent(WebCallbackManager::setUrlCleaner);
        this.requestOriginParserOptional.ifPresent(WebCallbackManager::setRequestOriginParser);
    }

    @Bean
    @ConditionalOnProperty(
            name = {"spring.cloud.sentinel.filter.enabled"},
            matchIfMissing = true
    )
    public FilterRegistrationBean sentinelFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean();
        //        com.alibaba.cloud.sentinel.SentinelProperties.Filter filterConfig = this.properties.getFilter();
        //        if (filterConfig.getUrlPatterns() == null || filterConfig.getUrlPatterns().isEmpty()) {
        //            List<String> defaultPatterns = new ArrayList();
        //            defaultPatterns.add("/*");
        //            filterConfig.setUrlPatterns(defaultPatterns);
        //        }

        //        registration.addUrlPatterns((String[])filterConfig.getUrlPatterns().toArray(new String[0]));
        Filter filter = new CommonFilter();
        registration.setFilter(filter);
        registration.setOrder(-2147483648);
        //registration.setOrder(filterConfig.getOrder());
        registration.addInitParameter("HTTP_METHOD_SPECIFY", String.valueOf(false));
        //log.info("[Sentinel Starter] register Sentinel CommonFilter with urlPatterns: {}.", filterConfig.getUrlPatterns());
        return registration;
    }
}

