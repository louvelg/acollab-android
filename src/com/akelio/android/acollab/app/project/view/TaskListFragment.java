/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.akelio.android.acollab.app.project.view;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.entity.Task;
import de.greenrobot.event.EventBus;

public class TaskListFragment extends ListFragment implements android.view.View.OnClickListener {
	static final String		URL	= "http://geb.test1.acollab.com/rest/v1/1/tasklist/{taskListId}/tasks";
	OnUserSelectedListener	mCallback;
	private boolean			dualPanel;

	public interface OnUserSelectedListener {
		public void onUserSelected(int position, ListView l);
	}

	public static TaskListFragment newInstance() {
		TaskListFragment frag = new TaskListFragment();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}

	public static TaskListFragment newInstance(String idTasklist) {
		TaskListFragment frag = new TaskListFragment();
		Bundle args = new Bundle();
		args.putString("taskListId", idTasklist);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onStart() {
		super.onStart();
		// if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
		// getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// }
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// HashMap obj = (HashMap) l.getItemAtPosition(position);
		// String idValue = (String) obj.get("textViewInvisible");
		// FragmentUserDetails detailFragment = FragmentUserDetails.newInstance(idValue);
		// if (!this.dualPanel) {
		// FragmentHelper.initFragmentWithBackstack(detailFragment, R.id.usermain_fragment, this.getParentFragment().getChildFragmentManager());
		// } else {
		// FragmentHelper.initFragment(detailFragment, R.id.userdetail_fragment, this.getParentFragment().getChildFragmentManager());
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_tasklist, container, false);
		Button addButton = (Button) view.findViewById(R.id.addTask);
		addButton.setOnClickListener(this);

		new TaskWS().execute();
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.addTask:
				System.out.println("clic addTask");
				getFragmentManager().beginTransaction().replace(R.id.content_frame, new TaskAddFragment()).addToBackStack(null).commit();
				break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.dualPanel = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	private class TaskWS extends AsyncTask<Void, Integer, Void> {

		ArrayList<HashMap<String, String>>	listItem	= new ArrayList<HashMap<String, String>>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Toast.makeText(getActivity(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			// Mise à jour de la ProgressBar
			// mProgressBar.setProgress(values[0]);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			HashMap<String, String> map = null;
			Task[] tl = getTask();
			if (tl != null) {
				for (Task task : tl) {
					map = new HashMap<String, String>();
					map.put("textViewName", task.getTitle());
					listItem.add(map);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItem, R.layout.list_tasks_item, new String[] { "textViewName" }, new int[] { R.id.textViewTaskName });
			getListView().setAdapter(mSchedule);
			// FragmentListTaskList.this.setListAdapter(mSchedule);
			// Toast.makeText(getActivity(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
		}

		public Task[] getTask() {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			String password = prefs.getString("password", "admin");
			String login = prefs.getString("login", "admin");
			try {
				HttpAuthentication authHeader = new HttpBasicAuthentication(login, password);
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
				String temp = (String) EventBus.getDefault().getStickyEvent(String.class);
				ResponseEntity<Task[]> res = restTemplate.exchange(URL.replace("{taskListId}", temp), HttpMethod.GET, requestEntity, Task[].class);
				return res.getBody();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public void onDestroyView() {
		// EventBus.getDefault().unregister(this);
		super.onDestroyView();
	}
}