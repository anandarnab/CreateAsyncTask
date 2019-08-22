package com.anand.alcatraz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	public static String TAG = MainActivity.class.getName();
	public ArrayList<Integer> al = new ArrayList<>();
	public int[] RemArr;

	AsyncTaskCallback asyncTaskCallback;
	RecyclerView recyclerView;
	ListAdapter listAdapter;
	EditText inputArrayTxt, positionTxt;
	Button resultBtn;
	String[] inputSplitString, positionSplit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inputArrayTxt = findViewById(R.id.input_array);
		positionTxt = findViewById(R.id.position_array);
		recyclerView = findViewById(R.id.sorted_list);
		resultBtn = findViewById(R.id.result_btn);

		inputArrayTxt.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				Log.i(TAG, "onTextChanged: "+ charSequence);

			}

			@Override
			public void afterTextChanged(Editable editable) {
				Log.i(TAG, "afterTextChanged: "+ editable);
				inputSplitString = editable.toString().split(",");
			}
		});

		positionTxt.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {

				if (inputSplitString!=null && !editable.toString().equals("")){
					String[] splitEditable = editable.toString().split(",");
					for (int i=0; i< splitEditable.length; i++){
						Log.i(TAG, "Step 2: ");
						if(Integer.parseInt(splitEditable[i]) > inputSplitString.length) {
							Log.i(TAG, "Error: Length Exceeded ");
							Toast.makeText(MainActivity.this, "Position not found",Toast.LENGTH_SHORT).show();
						}
					}
				} else if (inputSplitString == null){
					Toast.makeText(MainActivity.this, "Empty Array",Toast.LENGTH_SHORT).show();
				}
			}
		});


		asyncTaskCallback = new AsyncTaskCallback() {

			@Override
			void onPreExecute() {
				super.onPreExecute();

				//write your code

				for (int i=0; i<positionSplit.length; i++) {
					if (!positionSplit[i].equalsIgnoreCase("")){
						if (Integer.parseInt(positionSplit[i]) < inputSplitString.length){
							RemArr[i] = Integer.parseInt(positionSplit[i]);
						}
					}
				}

				for(int i = 0; i< inputSplitString.length ; i++) {
					al .add(Integer.parseInt(inputSplitString[i]));
				}

			}

			@Override
			Object doInBackground(Void... params) {

				//write your own code

				for(int i = 0; i< RemArr.length; i++){
					RemArr[i] = al.get(RemArr[i]-1);
				}

				for(int i = 0; i< RemArr.length; i++){
					al.remove(al.indexOf(RemArr[i]));
				}

				return super.doInBackground(params);
			}

			@Override
			void onPostExecute() {
				super.onPostExecute();

				//write your own code

				Log.i(TAG, "ArrayList "+ al);

				listAdapter = new ListAdapter(al, MainActivity.this);
				recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL, false));
				recyclerView.setAdapter(listAdapter);
				recyclerView.setVisibility(View.VISIBLE);
				String updateStr = "";
				for (int i = 0; i< al.size(); i++){
					updateStr = updateStr+al.get(i).toString()+",";
				}
				inputArrayTxt.setText(updateStr);
			}
		};

		resultBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				inputSplitString = inputArrayTxt.getText().toString().split(",");
				positionSplit = positionTxt.getText().toString().split(",");
				RemArr = new int[positionSplit.length];
				runTask();
			}
		});
	}


	public void runTask() {
		asyncTaskCallback.onPreExecute();

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				asyncTaskCallback.doInBackground();
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();

		Log.d(TAG, "runTask: "+ thread.getState()+"mmmm"+thread.isAlive() );

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				asyncTaskCallback.onPostExecute();
			}
		});
	}

}
