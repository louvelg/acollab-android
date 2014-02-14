package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.entity.Task;

public class TaskAddFragment extends Fragment implements android.view.View.OnClickListener {

	public TaskAddFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_task, container, false);
		Button addButton = (Button) view.findViewById(R.id.buttonAdd);

		addButton.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonAdd:
				System.out.println("clic buttonAdd test");
				// getFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentListTaskList()).addToBackStack(null).commit();
				new TaskAddWS().execute();
				break;
		}
	}

	private class TaskAddWS extends AsyncTask<Void, Integer, Void> {

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
			createTask();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItem, R.layout.list_tasks_item, new String[] { "textViewName" }, new int[] { R.id.textViewTaskName });
			// getListView().setAdapter(mSchedule);
			// FragmentListTaskList.this.setListAdapter(mSchedule);
			// Toast.makeText(getActivity(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
		}

		public void createTask() {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			String password = prefs.getString("password", "admin");
			String login = prefs.getString("login", "admin");
			try {
				Task task = new Task();
				task.setTitle("Une tache test");
				task.setTenantId(new Long(1));
				task.setProjectId("1");
				task.setTasklistId(new Long(8));
				task.setPriority("1");

				HttpAuthentication authHeader = new HttpBasicAuthentication(login, password);
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(new MediaType("application","json"));
				requestHeaders.setAuthorization(authHeader);
//				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
				HttpEntity<Task> requestEntity = new HttpEntity<Task>(task, requestHeaders);
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//				String temp = (String) EventBus.getDefault().getStickyEvent(String.class);
//				 ResponseEntity<Task[]> res = restTemplate.exchange("", HttpMethod.GET, requestEntity, Task[].class);
				// return res.getBody();
				ResponseEntity<String> responseEntity = restTemplate.exchange("http://geb.test1.acollab.com/rest/v1/1/tasks", HttpMethod.POST, requestEntity, String.class);
//				String result = restTemplate.postForObject("http://geb.test1.acollab.com/rest/v1/1/tasks", task, String.class, requestEntity);
				System.out.println("Résult : " + responseEntity.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
