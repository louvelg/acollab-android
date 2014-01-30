package com.akelio.android.acollab.view;

import com.akelio.android.acollab.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;


public class UsersAct extends FragmentActivity implements UsersFrag.OnUserSelectedListener{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_users);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            UsersFrag firstFragment = new UsersFrag();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

	public void onUserSelected(int position, View view) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the user fragment from the activity layout
        DetailsFrag userFrag = (DetailsFrag)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);
        TextView id = (TextView) view.findViewById(R.id.textViewInvisible);
        String idValue = (String) id.getText();
        if (userFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            userFrag.updateDetailsUserView(idValue);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            DetailsFrag newFragment = new DetailsFrag();
            Bundle args = new Bundle();
            args.putInt(DetailsFrag.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
	}

}
