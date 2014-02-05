package com.akelio.android.acollab.view;


import com.akelio.android.acollab.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class UserMainFragment extends Fragment {
	
	@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setRetainInstance(false);
        setHasOptionsMenu(true);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users, container, false);

        MasterDetailFragments currentFragments = MasterDetailFragmentHelper
                .getCurrentFragments(R.id.usermain_fragment,
                        R.id.userdetail_fragment, FragmentUserDetails.class,
                        getChildFragmentManager());
        if ( currentFragments.master == null ) {
            currentFragments.master = FragmentListUsers.newInstance();
        }

        MasterDetailFragmentHelper.initFragments(currentFragments, R.id.usermain_fragment,
                R.id.userdetail_fragment, getResources().getConfiguration(), getChildFragmentManager());

return view ;
}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.users_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.users_menu_info:
                //SimpleDialogFragment.createBuilder(this.getActivity(), this.getActivity().getSupportFragmentManager())
                //        .setMessage(Html.fromHtml(getString(R.string.friends_info_details)))
                 //       .setTitle(R.string.friends_info_title)
                 //       .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
