package com.akelio.android.acollab.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.dao.SpaceDAO;
import com.akelio.android.acollab.entity.Space;
import com.akelio.android.acollab.service.ActivityStreamService;
import com.akelio.android.acollab.service.ContactService;
import com.akelio.android.acollab.service.SpaceService;

public class MainActivity extends AbstractNavDrawerActivity {

	private SpaceDAO	spaceDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ActivityStreamFragment()).addToBackStack(null).commit();
	}

	@Override
	// test
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {

		spaceDAO = new SpaceDAO(getApplicationContext());
		this.spaces = spaceDAO.getSpaces();
		spaceDAO.close();

		int nbFixMenu = 6;

		NavDrawerItem[] menuSpaces = new NavDrawerItem[this.spaces.size() + nbFixMenu];
		menuSpaces[0] = NavMenuSection.create(-1, "MENU");
		menuSpaces[1] = NavMenuItem.create(-2, "Users", "navdrawer_airport", true, this);
		menuSpaces[2] = NavMenuItem.create(-3, "Settings", "navdrawer_airport", true, this);
		menuSpaces[3] = NavMenuItem.create(-4, "Activity stream", "navdrawer_airport", true, this);
		menuSpaces[4] = NavMenuSection.create(-5, "MES ESPACES");
		int i = nbFixMenu - 1;
		for (Space space : this.spaces) {
			menuSpaces[i] = NavMenuItem.create(Integer.valueOf(space.getSpaceId()), space.getName(), "navdrawer_rating", true, this);
			i++;
		}
		menuSpaces[i] = NavMenuItem.create(-nbFixMenu, "Quit", "navdrawer_quit", false, this);

		NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
		navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
		navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
		navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
		navDrawerActivityConfiguration.setNavItems(menuSpaces);
		navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);
		navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
		navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_close);
		navDrawerActivityConfiguration.setBaseAdapter(new NavDrawerAdapter(this, R.layout.navdrawer_item, menuSpaces));
		return navDrawerActivityConfiguration;
	}

	@Override
	protected void onNavItemSelected(int id) {

		if (id < 0) {

			switch ((int) id) {
				case -2:
					getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserMainFragment()).addToBackStack(null).commit();
					break;
				case -3:
					getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).addToBackStack(null).commit();
					break;
				case -4:
					getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ActivityStreamFragment()).addToBackStack(null).commit();
					break;
				case -6:
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
					break;
			}
		} else {
			spaceDAO = new SpaceDAO(getApplicationContext());
			Space space = spaceDAO.getSpace(String.valueOf(id));
			spaceDAO.close();
			if (space != null) {
				SpaceBoardFragment frag = new SpaceBoardFragment();
				Bundle args = new Bundle();
				args.putSerializable("space", space);
				frag.setArguments(args);
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).addToBackStack(null).commit();
			}
		}
	}
}
