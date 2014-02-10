package com.akelio.android.acollab.entity;

import java.io.Serializable;

public class TaskList implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private Long				taskListId;
	private Long				tenantId;
	private String				projectId;
	private String				title;
	private String				description;
	private String				userId;
	private String				taskListOrder;
	private boolean				root;

	public TaskList() {
	}

	public TaskList(String title, String taskListId) {
		this.title = title;
		this.taskListId = Long.valueOf(taskListId);
	}

	public Long getTaskListId() {
		return taskListId;
	}

	public String getTaskListIdS() {
		return String.valueOf(taskListId);
	}

	public void setTaskListId(Long tasklistId) {
		this.taskListId = tasklistId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskListOrder() {
		return taskListOrder;
	}

	public void setTaskListOrder(String taskListOrder) {
		this.taskListOrder = taskListOrder;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantIdS() {
		return String.valueOf(tenantId);
	}

}