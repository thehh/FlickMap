package hh.FlickMap;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class DialogBuilder {
	Context appContext;
	String TAG;
	
	public DialogBuilder(){
		
	}
	
	public DialogBuilder(Context c, String S){
		this.appContext = c;
		this.TAG = S;
	}
	
    void showDialog(int title, String message) {
    	try{
    	    AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
    	    builder.setTitle(title);
    	    builder.setMessage(message);
    	    builder.setPositiveButton("OK", null);
    	    builder.show();
    	}
    	catch (Exception e){
    	    Log.i(TAG, e.toString());
    	}
	}
    
    void setContext(Context c){
    	appContext = c;
    }
    
    Context getContext(){
    	return this.appContext;
    }
    
    void setTag(String S){
    	this.TAG = S;
    }
    
    String getTag(){
    	return this.TAG;
    }

}