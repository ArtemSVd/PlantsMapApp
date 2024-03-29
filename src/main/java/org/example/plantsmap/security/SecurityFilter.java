package org.example.plantsmap.security;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.RequestFacade;
import org.example.plantsmap.dto.User;
import org.example.plantsmap.exception.InvalidDataException;
import org.example.plantsmap.service.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    private final UserContext userContext;
    private final UserService userService;

    private final String ALLOW_URI = "/api/plants/files/";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Cookie deviceCookie = extractCookie((HttpServletRequest) req, "device");
        Cookie userNameCookie = extractCookie((HttpServletRequest) req, "user");

        if (((RequestFacade) req).getRequestURI().contains(ALLOW_URI)) {
            chain.doFilter(req, resp);
            return;
        }

        if (deviceCookie == null) {
            log.error("unauthorized request");
            ((HttpServletResponse) resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            User user = userService.getOrCreateUser(deviceCookie.getValue(), userNameCookie != null ? new String(Base64.decode(userNameCookie.getValue())) : null);
            userContext.setUser(user);
            log.info(user.toString());
        } catch (InvalidDataException e) {
            ((HttpServletResponse) resp).sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        chain.doFilter(req, resp);
    }

    private Cookie extractCookie(HttpServletRequest request, String cookieKey) {
        if (request == null) {
            return null;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieKey)) {
                    return cookie;
                }
            }
        }

        return null;
    }


    @Override
    public void destroy() {
    }
}