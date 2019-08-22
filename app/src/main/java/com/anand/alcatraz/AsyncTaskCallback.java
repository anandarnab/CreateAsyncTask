package com.anand.alcatraz;

import java.util.ArrayList;

public abstract class AsyncTaskCallback {

	public static boolean preExecuteStarted = false;
	private ArrayList<String> arrayList = new ArrayList<>();

	void onPreExecute(){
		preExecuteStarted = true;
	}

	 Object doInBackground(Void... params){

		return arrayList;
	 }

	void onPostExecute(){

	}

}
