package org.example.plantsmap.security;

import lombok.Getter;
import lombok.Setter;
import org.example.plantsmap.dto.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class UserContext {
    private User user;
}
