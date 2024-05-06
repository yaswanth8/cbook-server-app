package com.careerit.cbook.util;

import com.careerit.cbook.auth.domain.AppUser;

import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityContextUtil {

    public static AppUser getLoginUser() {

        AppUser user=(AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
