package pl.dgadecki.springworkshoprestapi.configuration.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dgadecki.springworkshoprestapi.common.webfilter.RequestExecutionTimeFilter;
import pl.dgadecki.springworkshoprestapi.common.webfilter.RequestResponseLoggingFilter;

@Configuration
public class WebFiltersConfiguration {

    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> requestResponseLoggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestResponseLoggingFilter());
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RequestExecutionTimeFilter> requestExecutionTimeFilter() {
        FilterRegistrationBean<RequestExecutionTimeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestExecutionTimeFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
