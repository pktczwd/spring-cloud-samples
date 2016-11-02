package org.pankai.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Created by pktczwd on 2016/11/2.
 */
public class ErrorHandlerFileter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlerFileter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        Throwable throwable = requestContext.getThrowable();
        if (throwable != null) {
            log.info("Filter execution summary:" + requestContext.getFilterExecutionSummary().toString());
            log.error("Uncaught throwable find:", throwable);
            requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return null;
    }
}
