package com.cms_jsp.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FrontApiInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        log.info("== Interceptor Start ==");

                        
            String contentType = req.getHeader("Content-Type");
            String accept = req.getHeader("Accept");
            String UUID = req.getHeader("UUID");
            String clientId = req.getHeader("Client-ID");
            String clientName = req.getHeader("Client-Name");
            String clientTime = req.getHeader("Client-Time");
            String userServiceNum = req.getHeader("User-Service-Num");
            String apiId = req.getHeader("API-ID");
                  
            
            
            log.info("contentType : {} , accept : {} , UUID : {}, clientId : {}, clientName : {} , clientTime : {} ,  userServiceNum : {} , apiId : {} "
                    , contentType, accept, UUID, clientId, clientName, clientTime, userServiceNum, apiId );
           
        log.info("== Interceptor End ==");
        
        return true;
    }

    @Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) {
        
    }
}
