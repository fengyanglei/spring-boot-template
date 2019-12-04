package com.fyl.boot.web.aspect;


import com.alibaba.fastjson.JSONObject;
import com.fyl.boot.common.dto.SysRequestLogInfo;
import com.fyl.boot.util.chars.DateUtils;
import com.fyl.boot.util.IpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 请求日志
 * 顺序 around -> before -> around -> after -> afterreturning
 */
@Aspect
@Component
public class ControllerLogAspect {
    private static final Log logger = LogFactory.getLog(ControllerLogAspect.class);

    /**
     * 切点 @Controller @RestController
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)||@within(org.springframework.web.bind.annotation.RestController)")
    public void controller() {
    }

    /**
     * 记录请求日志
     * 环绕增强
     *
     * @param joinPoint
     */
    @Around("controller()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //请求开始时间
        long startTimeMillis = System.currentTimeMillis();
        //调用 proceed() 方法才会真正的执行实际被代理的方法
        Object result = joinPoint.proceed();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            //请求日志
            SysRequestLogInfo log = new SysRequestLogInfo();
            log.setLoginIp(IpUtil.getIp(request));
            log.setServerName(request.getServerName());
            log.setServerPort(request.getServerPort() + "");
            log.setRequestUri(request.getRequestURI());
            log.setRequestQueryString(request.getQueryString());
            log.setRequestTime(DateUtils.dateToString(new Date(startTimeMillis)));
            log.setSpendTimeMillis(System.currentTimeMillis() - startTimeMillis);
            //响应数据
            log.setResponse(result);
            //用户信息
            this.setUser(log);
            //post body
            this.setBody(log, joinPoint, request);

            //记录日志
            logger.info(JSONObject.toJSONString(log));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取用户信息
     *
     * @param log
     */
    public void setUser(SysRequestLogInfo log) {
    }

    /**
     * 获取post参数
     *
     * @param log
     * @param joinPoint
     */
    public void setBody(SysRequestLogInfo log, JoinPoint joinPoint, HttpServletRequest request) {
        if (!"POST".equals(request.getMethod())) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            return;
        }

        //文件过滤
        if (ServletFileUpload.isMultipartContent(request)) {
            return;
        }

        for (Object o : args) {
            if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                continue;
            }
            log.setRequestPostData(o);
        }
    }

}
