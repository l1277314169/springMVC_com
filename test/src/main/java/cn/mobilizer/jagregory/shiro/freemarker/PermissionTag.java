package cn.mobilizer.jagregory.shiro.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.PermissionTag}</p>
 */
public abstract class PermissionTag extends SecureTag {
	private static final Log LOG = LogFactory.getLog(PermissionTag.class);
	
    String getName(Map params) {
        return getParam(params, "name");
    }
    
    @Override
    protected void verifyParameters(Map params) throws TemplateModelException {
        String permission = getName(params);

        if (permission == null || permission.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = getName(params);

        boolean show = showTagBody(p);
        if (show) {
            renderBody(env, body);
        }
    }

    protected boolean isPermitted(String p) {
    	boolean isPermitted = false;
    	isPermitted = getSubject() != null && getSubject().isPermitted(p);
    	if(!isPermitted) {
    		if (getSubject() == null){
    			LOG.info("没有取得subject");
    		} 
    	}
        return isPermitted;
    }

    protected abstract boolean showTagBody(String p);
}
