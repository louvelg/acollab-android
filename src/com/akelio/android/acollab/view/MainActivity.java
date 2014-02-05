package com.akelio.android.acollab.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.service.ActivityStreamService;
import com.akelio.android.acollab.service.ContactService;
import com.akelio.android.acollab.service.SpaceService;


public class MainActivity extends AbstractNavDrawerActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		//final Button buttonUsers = (Button) findViewById(R.id.buttonUsers);
		//final Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
		//final Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
		//final Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
		//final Button buttonActivityStream = (Button) findViewById(R.id.buttonActivityStream);
		//final Button buttonRefreshContactService = (Button) findViewById(R.id.buttonRefrechContactService);
		//final Button buttonRefreshSpaceService = (Button) findViewById(R.id.buttonRefrechSpaceService);
		//final Button buttonRefreshActivityStreamService = (Button) findViewById(R.id.buttonRefrechActivityStreamService);
		//buttonUsers.setOnClickListener(this);
		//buttonMenu.setOnClickListener(this);
		//buttonSettings.setOnClickListener(this);
		//buttonActivityStream.setOnClickListener(this);
		//buttonRefreshContactService.setOnClickListener(this);
		//buttonRefreshSpaceService.setOnClickListener(this);
		//buttonRefreshActivityStreamService.setOnClickListener(this);
		//buttonLogin.setOnClickListener(this);
	}

	@Override
	// test
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		  NavDrawerItem[] menu = new NavDrawerItem[] {
	                NavMenuSection.create( 100, "MENU"),
	                NavMenuItem.create(101,"Login", "navdrawer_friends", false, this),
	                NavMenuItem.create(102, "Users", "navdrawer_airport", true, this),
	                NavMenuItem.create(103, "Settings", "navdrawer_airport", true, this),
	                NavMenuSection.create(200, "ESPACE DE TRAVAIL"),
	                NavMenuItem.create(202, "Espace 1", "navdrawer_rating", false, this),
	                NavMenuItem.create(203, "Espace 2", "navdrawer_eula", false, this),
	                NavMenuItem.create(204, "Quit", "navdrawer_quit", false, this)};
	       
	        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
	        navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
	        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
	        navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
	        navDrawerActivityConfiguration.setNavItems(menu);
	        navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);      
	        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
	        navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_close);
	        navDrawerActivityConfiguration.setBaseAdapter(
	            new NavDrawerAdapter(this, R.layout.navdrawer_item, menu ));
	        return navDrawerActivityConfiguration;
	}

	@Override
	protected void onNavItemSelected(int id) {
		// TODO Auto-generated method stub
		 switch ((int)id) {
	        case 102:
	            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentUserDetails()).commit();
	            break;
	        case 103:
	            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
	            break;
		 }
	}
}
