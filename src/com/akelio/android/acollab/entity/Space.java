package com.akelio.android.acollab.entity;

import java.util.List;

public class Space {
	private String spaceId;
	private String tenantId;
	private String name;
	private List<String> applications;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

}
