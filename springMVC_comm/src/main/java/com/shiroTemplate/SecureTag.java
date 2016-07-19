package com.shiroTemplate;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;


import java.io.IOException;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public abstract class SecureTag
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
    throws TemplateException, IOException
  {
    verifyParameters(params);
    render(env, params, body);
  }

  public abstract void render(Environment paramEnvironment, Map paramMap, TemplateDirectiveBody paramTemplateDirectiveBody) throws IOException, TemplateException;

  protected String getParam(Map params, String name) {
    Object value = params.get(name);

    if (value instanceof SimpleScalar) {
      return ((SimpleScalar)value).getAsString();
    }

    return null;
  }

  protected Subject getSubject() {
    return SecurityUtils.getSubject();
  }

  protected void verifyParameters(Map params) throws TemplateModelException {
  }

  protected void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
    if (body != null)
      body.render(env.getOut());
  }
}