package com.shiroTemplate;

import org.apache.shiro.subject.Subject;

import java.util.Map;

public class HasAnyRolesTag extends RoleTag {
    private static final String ROLE_NAMES_DELIMETER = ",";

    @Override
    String getName(Map params) {
        return super.getName(params);
    }

    protected boolean showTagBody(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();

        if (subject != null) {
            for (String role : roleNames.split(",")) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }

        return hasAnyRole;
    }
}