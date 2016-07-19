package com.shiroTemplate;

public class LacksPermissionTag extends PermissionTag
{
  protected boolean showTagBody(String p)
  {
    return !isPermitted(p);
  }
}