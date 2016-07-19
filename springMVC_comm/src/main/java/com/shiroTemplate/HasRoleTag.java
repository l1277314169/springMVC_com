package com.shiroTemplate;

import org.apache.shiro.subject.Subject;

public class HasRoleTag extends RoleTag
{
  protected boolean showTagBody(String roleName)
  {
    return (getSubject() != null) && (getSubject().hasRole(roleName));
  }
}