package com.whr.rbac_shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
public class CustomSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "token";

    public CustomSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        log.info("进入 getSessionId()");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

        // 根据 token 取到 sessionId
        String sessionId = httpServletRequest.getHeader(AUTHORIZATION);
        if (!StringUtils.isEmpty(sessionId)) {
            log.info("取到了sessionId");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);

            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        } else {
            log.info("没取到sessionId，默认调用父类方法");
            return super.getSessionId(request, response);
        }

        // always set rewrite flag - SHIRO-361
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());

        return sessionId;
    }
}
