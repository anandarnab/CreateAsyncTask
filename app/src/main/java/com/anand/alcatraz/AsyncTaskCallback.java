package com.anand.alcatraz;

public abstract class AsyncTaskCallback {

	public static boolean preExecuteStarted = false;

	void onPreExecute(){
		preExecuteStarted = true;
	}

	 Object doInBackground(Void... params){

		return null;
	 }

	void onPostExecute(){

	}

}
