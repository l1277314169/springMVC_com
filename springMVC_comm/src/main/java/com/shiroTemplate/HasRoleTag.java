package com.shiroTemplate;

public class HasRoleTag extends RoleTag
{
  protected boolean showTagBody(String roleName)
  {
    return (getSubject() != null) && (getSubject().hasRole(roleName));
  }
}