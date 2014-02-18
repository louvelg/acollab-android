package com.akelio.android.acollab.core.menu.view;

public interface NavDrawerItem {
	public int getId();

	public String getLabel();

	public int getType();

	public boolean isEnabled();

	public boolean updateActionBarTitle();
}
