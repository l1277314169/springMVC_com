package com.shiroTemplate;

import org.apache.shiro.subject.Subject;

public class LacksRoleTag extends RoleTag
{
  protected boolean showTagBody(String roleName)
  {
    boolean hasRole = (getSubject() != null) && (getSubject().hasRole(roleName));
    return !hasRole;
  }
}