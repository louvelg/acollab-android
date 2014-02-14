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
package com.akelio.android.acollab.view;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.entity.TaskList;
import de.greenrobot.event.EventBus;

public class FragmentListTaskList extends ListFragment {
	static final String		URL	= "http://geb.test1.acollab.com/rest/v1/1/project/1/tasklists";
	OnUserSelectedListener	mCallback;
	private boolean			dualPanel;

	public interface OnUserSelectedListener {
		public void onUserSelected(int position, ListView l);
	}

	public static FragmentListTaskList newInstance() {
		FragmentListTaskList frag = new FragmentListTaskList();
		Bundle args = new Bundle();
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
		HashMap obj = (HashMap) l.getItemAtPosition(position);
		System.out.println(obj);

		String idValue = (String) obj.get("textViewId");
		FragmentListTaskListTasks detailFragment = FragmentListTaskListTasks.newInstance(idValue);

		EventBus.getDefault().postSticky(new String(idValue));
//		if (!this.dualPanel) {
			getFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentListTaskListTasks()).addToBackStack(null).commit();
			// FragmentHelper.initFragmentWithBackstack(detailFragment, R.id.usermain_fragment, this.getParentFragment().getChildFragmentManager());
//		} else {
//			// FragmentHelper.initFragment(detailFragment, R.id.userdetail_fragment, this.getParentFragment().getChildFragmentManager());
//		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.fragment_list_tasklist, container, false);
		fillData();
		return mainView;
	}

	private void fillData() {
		new BigCalcul().execute();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.dualPanel = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	private class BigCalcul extends AsyncTask<Void, Integer, Void> {

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

			int progress;
			for (progress = 0; progress <= 100; progress++) {
				for (int i = 0; i < 1000000; i++) {}
				// la méthode publishProgress met à jour l'interface en invoquant la méthode onProgressUpdate
				publishProgress(progress);
				progress++;
			}

			HashMap<String, String> map = null;
			TaskList[] tl = getTaskList();
			if (tl != null) {
				for (TaskList taskList : tl) {
					map = new HashMap<String, String>();
					map.put("textViewName", taskList.getTitle() + " id: " + taskList.getTaskListIdS());
					map.put("textViewId", taskList.getTaskListIdS());
					listItem.add(map);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItem, R.layout.list_tasklist_item, new String[] { "textViewName", "textViewId" }, new int[] { R.id.textViewName,
					R.id.textViewId });
			getListView().setAdapter(mSchedule);
			// FragmentListTaskList.this.setListAdapter(mSchedule);
			// Toast.makeText(getActivity(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
		}

		public TaskList[] getTaskList() {
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

				ResponseEntity<TaskList[]> res = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, TaskList[].class);
				return res.getBody();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}