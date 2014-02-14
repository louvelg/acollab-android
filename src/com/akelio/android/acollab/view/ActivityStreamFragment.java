package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.dao.ActivityDAO;
import com.akelio.android.acollab.entity.Activity;
import com.akelio.android.acollab.service.ContactService;

public class ActivityStreamFragment extends Fragment {

	private ActivityDAO	activityDAO;
	private FragmentActivity fa;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_activity_stream, container, false);
		final ListView listview = (ListView) view.findViewById(R.id.listViewActivityStream);
		
		setHasOptionsMenu(true);
		
		fa = super.getActivity();
		activityDAO = new ActivityDAO(getActivity());
		List<Activity> activitys = activityDAO.getActivitys();
		activityDAO.close();

		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		System.out.println("Nb activity : " + activitys.size());
		for (Activity activity : activitys) {
			map = new HashMap<String, String>();
			map.put("textViewActivityName", activity.getTitle());
			map.put("textViewActivityDescription", activity.getUserName());
			listItem.add(map);
		}
		
		SimpleAdapter mSchedule = new SimpleAdapter(view.getContext(), listItem, R.layout.list_activity_stream, new String[] { "textViewActivityName", "textViewActivityDescription" }, new int[] {
				R.id.textViewActivityName, R.id.textViewActivityDescription });
		listview.setAdapter(mSchedule);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Toast.makeText(v.getContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
			}
		});

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_stream_menu, menu);
	}
	
	private void startRequest() {
		Toast.makeText(getActivity(), "toto",Toast.LENGTH_LONG).show();
		Intent explicitIntent = new Intent(fa, ContactService.class);
		fa.startService(explicitIntent);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.news_menuRefreshUsers:
			this.startRequest();
			return true;

        }
		return super.onOptionsItemSelected(item);
	}
	
	public void setText(String item) {
	}
}
