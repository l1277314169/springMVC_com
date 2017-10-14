package cn.mobilizer.jagregory.shiro.freemarker;

import org.apache.shiro.subject.Subject;

public class HasAnyPermissionTag extends PermissionTag {
	public HasAnyPermissionTag() {
	}

	protected boolean showTagBody(String permissions) {
		boolean hasAnyPermissions = false;
		Subject subject = getSubject();
		if (subject != null) {
			for (String role : permissions.split(",")) {
				if (subject.isPermitted(role.trim())) {
					hasAnyPermissions = true;
					break;
				}
			}
		}
		return hasAnyPermissions;
	}
}