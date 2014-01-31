package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.UserContract;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.UserListItem;

public class UsersFragment extends ListFragment {

	public static String TAG="fragmentUsersFragment";
	private DetailsUsersFragment mDetailsUserFragment ;
	OnArticleSelectedListener mListener;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_users, container, false);
		final ListView listview = (ListView) view
				.findViewById(R.id.list_fragment);
		View listView = getActivity().findViewById(R.id.list_fragment);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		DbHelper dbHelper = new DbHelper(view.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, null, null, null, null, null,
				UserContract.DEFAULT_SORT);
		if (cursor != null)
			cursor.moveToFirst();

		while (!cursor.isAfterLast()) {


			map = new HashMap<String, String>();
			map.put("textViewName", cursor.getString(2));
			
			map.put("textViewDescription", cursor.getString(3));
			listItem.add(map);
			cursor.moveToNext();
		}

		db.close();

		SimpleAdapter mSchedule = new SimpleAdapter(view.getContext(),
				listItem, R.layout.list_user_item, new String[] {
						"textViewName", "textViewDescription" }, new int[] {
						R.id.textViewName, R.id.textViewDescription });
		listview.setAdapter(mSchedule);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
							//List<Fragment> list = getFragmentManager().getFragments();
							
							//mDetailsUserFragment = new DetailsUsersFragment();
							//if(mDetailsUserFragment !=null ){
							
	                        //FragmentTransaction ft = getFragmentManager().beginTransaction();
	                        //ft.replace(R.id.frameLayoutView, mDetailsUserFragment);
	                        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	                        //ft.commit();
							//}
						
							//mListener.onArticleSelected();

							
				}
			});
		return view;
	}

	// Container Activity must implement this interface
    public interface OnArticleSelectedListener {
        public void onArticleSelected();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    
	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	private class LoadWebPageASYNC extends
			AsyncTask<String, Void, UserListItem> {

		@Override
		protected UserListItem doInBackground(String... urls) {
			String url = "http://geb.test1.acollab.com/rest/v1/users/user/2";
			System.out.println("url : " + url);

			try {
				HttpAuthentication authHeader = new HttpBasicAuthentication(
						"admin", "admin");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				HttpEntity<?> requestEntity = new HttpEntity<Object>(
						requestHeaders);

				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new GsonHttpMessageConverter());
				ResponseEntity<UserListItem> response2 = restTemplate.exchange(
						url, HttpMethod.GET, requestEntity, UserListItem.class);

				System.out.println("url : "
						+ response2.getBody().getFirstName());

				return response2.getBody();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(UserListItem result) {
			super.onPostExecute(result);
		}
	}

}
