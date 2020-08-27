package com.xdbigdata.user_manage_admin.util.masterworker;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

public class ManagerScopeVerifyTask extends Task<Boolean> {
	
	private Collection<String> allScopes;
	
	private boolean isNew;

	public ManagerScopeVerifyTask(String name, Collection<String> allScopes, boolean isNew) {
		super(name);
		this.allScopes = allScopes;
		this.isNew = isNew;
	}

	@Override
	protected Boolean execute() {
		if (CollectionUtils.isEmpty(allScopes)) {
			return false;
		}
		for (String scope : allScopes) {
			if (isNew && this.getName().equals(scope)) {
				continue;
			}
			if (this.getName().startsWith(scope) || scope.startsWith(this.getName())) {
				return true;
			}
		}
		return false;
	}

}
