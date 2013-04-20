package pandox.china.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pandox.china.model.User;

public abstract class BaseController {

    @Autowired
    private ReloadableResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    private ReloadableResourceBundleMessageSource config;

    protected User getLoggedUser(){
   	 SecurityContext context = SecurityContextHolder.getContext();
 		if (context != null) {
 			Authentication authentication = context.getAuthentication();
 			if (authentication != null) {
 				Object securityUser = authentication.getPrincipal();
 				if (securityUser != null && securityUser instanceof User) {
 					return (User) securityUser;
 				}
 			}
 		}
 		
 		return null;
    }
    
    protected String getMessage(String keyMessage) {
        return resourceBundleMessageSource.getMessage(keyMessage, null, null);
    }

    protected String getMessage(String keyMessage, Object... object) {
        return resourceBundleMessageSource.getMessage(keyMessage, object, null);
    }

    protected String getPropertyConfig(String key) {
        return config.getMessage(key, null, null);
    }

    protected String getPropertyConfig(String keyMessage, Object... object) {
        return config.getMessage(keyMessage, object, null);
    }

    protected String getRemoteIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
