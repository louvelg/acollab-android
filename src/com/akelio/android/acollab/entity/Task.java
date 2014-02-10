package com.akelio.android.acollab.entity;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private Long				taskId;
	private Long				tasklistId;
	private Long				tenantId;
	private String				projectId;
	private String				title;
	private String				description;
	private String				userId;
	private String				taskListOrder;
	private String				startDate;
	private String				endDate;
	private String				priority;
	private String				progress;
	private String				status;
	private String				moduleId;
	private String				moduleType;
	private String				moduleParentId;
	private String				moduleParentType;

	public Task() {
	}

	public Task(String title, String taskListId) {
		this.title = title;
		tasklistId = Long.valueOf(tasklistId);
	}

	public Long getTaskListId() {
		return tasklistId;
	}

	public String getTaskListIdS() {
		return String.valueOf(tasklistId);
	}

	public void setTaskListId(Long tasklistId) {
		this.tasklistId = tasklistId;
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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTasklistId() {
		return tasklistId;
	}

	public void setTasklistId(Long tasklistId) {
		this.tasklistId = tasklistId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getModuleParentId() {
		return moduleParentId;
	}

	public void setModuleParentId(String moduleParentId) {
		this.moduleParentId = moduleParentId;
	}

	public String getModuleParentType() {
		return moduleParentType;
	}

	public void setModuleParentType(String moduleParentType) {
		this.moduleParentType = moduleParentType;
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