package hh.FlickMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FlickMapActivity extends Activity {
	private long minDistance = 200;
	private long minTime = 1000*60*3;
	
	private LocationManager locationManager=null;
	private MyLocationListener locationListener=null;
	
	private RefreshHandler mRedrawHandler = new RefreshHandler();
	private TextView ubicacion;

	//private RefQueueHandler mRedrawHandler = new RefreshHandler();
	
	private static final String TAG = "FlickView";
	private final static String DOMAIN = "http://api.flickr.com/services/feeds/geo/"; 
	private final static String FORMAT = "/?format=kml_nl";
	private String FURL = "";
	//url final es DOMAIN + getShortPais() + "/" + getCiudad() + FORMAT;

	private String ciudad = "";
	private String shortPais = "";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.buttonSearch).setOnClickListener(buttonSearch);
        
      //manejo de ubicación
		locationListener = new MyLocationListener();
		locationListener.setContext(getApplicationContext()); //aregar context de esta aplicacion al listener
		
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
		locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
        ubicacion = (TextView) findViewById(R.id.locationText);
        
        String direccion = locationListener.getDireccion();
        shortPais = locationListener.getShortPais();
        ciudad = locationListener.getCiudad();
        FURL = DOMAIN + shortPais + "/" + ciudad + FORMAT;
        Log.i(TAG,"Direccion: " + direccion);
        Log.i(TAG,"Pais: " + shortPais);
        Log.i(TAG, "Ciudad: " + ciudad);
        Log.i(TAG, "URL: " + FURL);
        ubicacion.setText(direccion);
        updateUI();
        
    }
    
    public final Button.OnClickListener buttonSearch = new Button.OnClickListener() {
        @Override
		public void onClick(View v) {
            Uri uri1 = Uri.parse("geo:0,0?q="+ FURL); 
            final Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri1); 
            startActivity(mapIntent);
        }
      };
      
	  class RefreshHandler extends Handler {
		    @Override
		    public void handleMessage(Message msg) {
		      FlickMapActivity.this.updateUI();
		    }

		    public void sleep(long delayMillis) {
		      this.removeMessages(0);
		      sendMessageDelayed(obtainMessage(0), delayMillis);
		    }
	  };
	
	  //Implementación de updateUI
	  private void updateUI(){
		    //String currentText = locationListener.getDireccion();
		    String currentCiudad = locationListener.getCiudad();
		    String currentShortPais = locationListener.getShortPais();
		    mRedrawHandler.sleep(1000);
		    ubicacion.setText("OK!");
		    ciudad = currentCiudad;
		    shortPais = currentShortPais;
		    FURL = DOMAIN + shortPais + "/" + ciudad + FORMAT;
		    //Log.i(TAG, "updateIU\n_______\nCiudad: " + ciudad + "\nPais: " + shortPais);
		    Log.i(TAG, "URL: " + FURL);
		    
		    
	  }  
}