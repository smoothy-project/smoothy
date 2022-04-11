package solutions.thex.smoothy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Before(value = "execution(* solutions.thex.smoothy.controller..*(..)) && args(request, payload)",//
            argNames = "joinPoint,request,payload")
    public void logBefore(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        log.info("{}: request: {}, ip: {}, user-agent: {}\n{}",//
                joinPoint.toShortString(),//
                request.getRequestURI(),//
                request.getRemoteAddr(),//
                request.getHeader("User-Agent"),//
                payload);
    }

}
