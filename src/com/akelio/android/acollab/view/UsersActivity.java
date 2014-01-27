package com.akelio.android.acollab.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.akelio.android.acollab.R;

public class UsersActivity extends FragmentActivity {
	
	
	private DetailsUsersFragment mDetailsUserFragment;
	private UsersFragment mUsersFragment;
	private String mFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
		
		
		mFragment = getIntent().getStringExtra("fragment");
       setupFragments();
        if (mFragment.equals("UsersFragment")) {
            	showFragment(this.mUsersFragment);
        } else if (mFragment.equals("DetailsUsersFragment")) {
        		showFragment(this.mDetailsUserFragment);
        }
	}
        
    private void setupFragments() {
            final FragmentManager fmFrg1 = getSupportFragmentManager();
            this.mUsersFragment = (UsersFragment) fmFrg1.findFragmentByTag(UsersFragment.TAG);
            if (this.mUsersFragment == null) {
                this.mUsersFragment = new UsersFragment();
            }
            this.mDetailsUserFragment = (DetailsUsersFragment) fmFrg1.findFragmentByTag(DetailsUsersFragment.TAG);
            if (this.mDetailsUserFragment == null) {
                this.mDetailsUserFragment = new DetailsUsersFragment();
            }
    }
        
    private void showFragment(final Fragment fragment) {
            if (fragment == null)
                return;
            final FragmentManager fmFrg2 = getSupportFragmentManager();
            final FragmentTransaction ft = fmFrg2.beginTransaction();
            // We can also animate the changing of fragment
            ft.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            ft.replace(R.id.frameLayoutView, fragment);
            ft.commit();
    }
        
    public void goToUsers(View v) {
            showFragment(this.mUsersFragment);
    }
    public void goToUsersDetails(View v) {
            showFragment(this.mDetailsUserFragment);
    }


}
