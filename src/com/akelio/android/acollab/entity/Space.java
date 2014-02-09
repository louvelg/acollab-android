package com.akelio.android.acollab.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class Space implements Serializable {
	private static final long	serialVersionUID	= -8049476985770209700L;
	private String				spaceId;
	private String				tenantId;
	private String				name;
	private List<String>		applications;

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

	public void setApplications(String applications) {
		this.applications = new ArrayList<String>();
		for (String s : StringUtils.commaDelimitedListToStringArray(applications)) {
			this.applications.add(s);
		}
	}

}
