package com.servir.invasivespecies;


        import java.io.BufferedInputStream;
        import java.io.BufferedOutputStream;
        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.Timer;
        import java.util.TimerTask;
        import java.util.zip.ZipEntry;
        import java.util.zip.ZipOutputStream;

        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GoogleApiAvailability;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.PendingResult;
        import com.google.android.gms.common.api.Status;
        import com.google.android.gms.location.LocationListener;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;

        import android.app.Activity;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentSender;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.location.Location;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.appcompat.app.AppCompatActivity;
        import android.util.Base64;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.WindowManager;
        import android.view.View.OnClickListener;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextSwitcher;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    TextSwitcher textSwitcher;
    Animation slide_in_left, slide_out_right;
    LinearLayout btnreg;
    View View;
    static double longitude = 0.0;
    static double latitude = 0.0;
    private SQLiteDatabase spatiadb;
    LinearLayout btnview, btnniko;
    public static final int confail = 9000;
    public static final String URL_tuma = "http://mobiledata.rcmrd.org/invspec/senda.php";
    public static final String URL_tumapic = "http://mobiledata.rcmrd.org/invspec/sendapic.php";
    String zipfilo = "";
    LocationRequest mlr;
    GoogleApiClient mgac;
    Location cl;
    List<String> stora  = new ArrayList<String>();
    List<String> storapic  = new ArrayList<String>();
    TextView title, sitato;
    String lefile;
    ProgressDialog mpd;
    String say = "0.0";
    String sax = "0.0";
    String dater,pswd,pswda,ioyote;
    String nani="";
    String simu="";
    String mail="";
    String xorg = "";
    String xcon = "";
    String depty="";
    String huyu = "user";
    String responder = "";
    String fili = "";
    ImageView taftaa;
    private final static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;
    private final static int REQUEST_LOCATION = 2;
    private final static int REQUEST_PERMISSIONS = 24;


    protected void createlocreq(){
        mlr = new LocationRequest();
        mlr.setInterval(2000);
        mlr.setFastestInterval(1000);
        mlr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    String[] TextToSwitched = { "...map your world", "...easy visualisation", "...convenient system", "...ready information",
            "...stay connected", "...access resources" };

    int curIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        if (!isgpsa(this)){
            Toast.makeText(this, "Google Play Services is disabled on your phone", Toast.LENGTH_LONG).show();
        }


        textSwitcher = (TextSwitcher) findViewById(R.id.textswitcher);
        btnreg = (LinearLayout) findViewById(R.id.btn1);
        btnview = (LinearLayout) findViewById(R.id.btn2);
        btnniko = (LinearLayout) findViewById(R.id.btn3);
        title = (TextView) findViewById(R.id.title);
        taftaa = (ImageView) findViewById(R.id.procss);
        sitato = (TextView) findViewById(R.id.sitato);

        createlocreq();

        spatiadb = openOrCreateDatabase("InvSpecDB", Context.MODE_PRIVATE, null);
        //spatiadb.execSQL("DROP TABLE IF EXISTS picTBL");
        //spatiadb.execSQL("DROP TABLE IF EXISTS datTBL");

        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userTBL(userno VARCHAR,usernem VARCHAR,usertel VARCHAR,usercntry VARCHAR,useremail VARCHAR,userpass VARCHAR);");
        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS datTBL(datno VARCHAR,datftrname VARCHAR,datcnt VARCHAR,datiar VARCHAR,datgar VARCHAR,datcc VARCHAR,dathab VARCHAR,databd VARCHAR,datown VARCHAR,datara VARCHAR,datset VARCHAR,datcom VARCHAR,datx VARCHAR,daty VARCHAR,datpicnm VARCHAR,datorg VARCHAR,datcon VARCHAR);");

        Cursor ca1 = spatiadb.rawQuery("SELECT * FROM datTBL", null);

        if (ca1 == null){
            spatiadb.execSQL("CREATE TABLE IF NOT EXISTS datTBL(datno VARCHAR,datftrname VARCHAR,datcnt VARCHAR,datiar VARCHAR,datgar VARCHAR,datcc VARCHAR,dathab VARCHAR,databd VARCHAR,datown VARCHAR,datara VARCHAR,datset VARCHAR,datcom VARCHAR,datx VARCHAR,daty VARCHAR,datpicnm VARCHAR,datorg VARCHAR,datcon VARCHAR);");
        }else if (ca1.getColumnIndex("datset")== -1){
            spatiadb.execSQL("DROP TABLE IF EXISTS datTBL");
            spatiadb.execSQL("CREATE TABLE IF NOT EXISTS datTBL(datno VARCHAR,datftrname VARCHAR,datcnt VARCHAR,datiar VARCHAR,datgar VARCHAR,datcc VARCHAR,dathab VARCHAR,databd VARCHAR,datown VARCHAR,datara VARCHAR,datset VARCHAR,datcom VARCHAR,datx VARCHAR,daty VARCHAR,datpicnm VARCHAR,datorg VARCHAR,datcon VARCHAR);");
        }else{
            spatiadb.execSQL("CREATE TABLE IF NOT EXISTS datTBL(datno VARCHAR,datftrname VARCHAR,datcnt VARCHAR,datiar VARCHAR,datgar VARCHAR,datcc VARCHAR,dathab VARCHAR,databd VARCHAR,datown VARCHAR,datara VARCHAR,datset VARCHAR,datcom VARCHAR,datx VARCHAR,daty VARCHAR,datpicnm VARCHAR,datorg VARCHAR,datcon VARCHAR);");
        }

        ca1.close();


        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS picTBL(userpic VARCHAR, userpicpath VARCHAR, sendstat VARCHAR);");
        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userareaTBL(userno VARCHAR, userorg VARCHAR, usercons VARCHAR);");


        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(slide_in_left);
        textSwitcher.setOutAnimation(slide_out_right);

        Cursor c=spatiadb.rawQuery("SELECT * FROM userTBL WHERE userno='"+huyu+"'", null);
        if(c.moveToFirst())
        {
            nani = c.getString(1);
            simu = c.getString(2);
            mail = c.getString(4);
            pswda = c.getString(5);



            Cursor ca=spatiadb.rawQuery("SELECT * FROM userareaTBL WHERE userno='"+huyu+"'", null);
            if(ca.moveToFirst())
            {
                xorg = ca.getString(1);
                xcon = ca.getString(2);
                Log.d("culprit",xorg+xcon);
            }else{
                diambaidweni2(View);
            }
            ca.close();


        }else{
            diambaidweni(View);
        }
        c.close();






        textSwitcher.setFactory(new ViewFactory(){

            @Override
            public View makeView() {
                TextView textView = new TextView(MainActivity.this);
                textView.setTextSize(16);
                textView.setTextColor(Color.rgb(0,0,0));
                //textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
                // textView.setShadowLayer(10, 10, 10, Color.rgb(255,204,51));
                return textView;
            }});

        curIndex = 0;
        textSwitcher.setText(TextToSwitched[curIndex]);



        mgac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        	  /*final Animation in = new AlphaAnimation(0.0f, 1.0f);
        	  in.setDuration(3000);

        	  final Animation out = new AlphaAnimation(1.0f, 0.0f);
        	  out.setDuration(3000);

        	  AnimationSet as = new AnimationSet(true);
        	  as.addAnimation(out);
        	  in.setStartOffset(3000);
        	  as.addAnimation(in);*/
        taftaa.setVisibility(android.view.View.GONE);

        Timer timer = new Timer("swcha");
        timer.schedule(new TimerTask() {

            public void run() {

                runOnUiThread(new Runnable() {


                    @Override
                    public void run() {

                        updateUI();

                        //Toast.makeText(MainActivity.this,"tLat :"+  latitude + "\n" + "tLon : "+longitude ,Toast.LENGTH_LONG).show();


                        if(curIndex == TextToSwitched.length-1){
                            curIndex = 0;
                            textSwitcher.setText(TextToSwitched[curIndex]);
                        }else{
                            textSwitcher.setText(TextToSwitched[++curIndex]);
                        }

                        if (latitude!=0.0 && longitude!=0.0 ){
                            sitato.setText("GPS Location : Detected");
                        }else{
                            sitato.setText("GPS Location : Searching...");

                            //taftaa.setVisibility(View.VISIBLE);


                    	          		 /*sitato.startAnimation(out);
                    	          		 sitato.setText("Scanning Location");
                    	                 sitato.startAnimation(in);*/
                            //createlocreq();
                            //sitato2.setText("INACTIVE (location not detected)");
                        }

                        // Toast.makeText(MainActivity.this,"LAT:"+String.valueOf(latitude)+"\n"+"LON:"+String.valueOf(longitude),Toast.LENGTH_SHORT).show();

                    }



                });

            }

        }, 0, 2000);





        btnreg.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

               // diaingia(View);

                if (sax.equals("0.0") || say.equals("0.0")){
                    updateUI();
                    Toast.makeText(MainActivity.this,"Please turn on GPS then try again / Or wait for GPS Location to be detected if ON",Toast.LENGTH_LONG).show();
                }else{

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", java.util.Locale.getDefault());
                    dater = "IS_" + nani + "_" + simu + "_" + dateFormat.format(new Date());

                        Intent intent = new Intent (MainActivity.this, Selekta.class);
                        intent.putExtra("lattt", String.valueOf(latitude));
                        intent.putExtra("lonnn", String.valueOf(longitude));
                        intent.putExtra("datno", dater);
                        intent.putExtra("orgaa", xorg);
                        intent.putExtra("consa", xcon);
                        startActivity(intent);

                }

            }
        });

        btnniko.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

                if (latitude!=0.0 && longitude!=0.0 ){

                    Intent intent = new Intent (MainActivity.this, Mapper.class);
                    Bundle b = new Bundle();
                    Bundle a = new Bundle();
                    a.putDouble("lattt", latitude);
                    b.putDouble("lonnn", longitude);

                    intent.putExtras(a);
                    intent.putExtras(b);
                    intent.putExtra("simu", simu);
                    intent.putExtra("nani", nani);
                    startActivity(intent);

                }else{

                    updateUI();
                    Toast.makeText(MainActivity.this,"Please turn on GPS then try again / Or wait for GPS Location to be detected if ON",Toast.LENGTH_LONG).show();


                }


            }
        });

        btnview.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

                if (nani.equals("") || simu.equals("") || mail.equals("")){
                    diambaidweni(View);
                }else{
                    Cursor c5=spatiadb.rawQuery("SELECT * FROM datTBL", null);
                    stora.clear();
                    if(c5!=null && c5.getCount()>0)
                    {
						
						 if(c5.moveToFirst())
                        {
                            stora.add(c5.getString(0));
                            stora.add(c5.getString(1));
                            stora.add(c5.getString(2));
                            stora.add(c5.getString(3));
                            stora.add(c5.getString(4));
                            stora.add(c5.getString(5));
                            stora.add(c5.getString(6));
                            stora.add(c5.getString(7));
                            stora.add(c5.getString(8));
                            stora.add(c5.getString(9));
                            stora.add(c5.getString(10));
                            stora.add(c5.getString(11));
                            stora.add(c5.getString(12));
							stora.add(c5.getString(13));
							stora.add(c5.getString(14));
							stora.add(c5.getString(15));
							stora.add(c5.getString(16));
                        }

                        while(c5.moveToNext())
                        {
                            stora.add(c5.getString(0));
                            stora.add(c5.getString(1));
                            stora.add(c5.getString(2));
                            stora.add(c5.getString(3));
                            stora.add(c5.getString(4));
                            stora.add(c5.getString(5));
                            stora.add(c5.getString(6));
                            stora.add(c5.getString(7));
                            stora.add(c5.getString(8));
                            stora.add(c5.getString(9));
                            stora.add(c5.getString(10));
                            stora.add(c5.getString(11));
                            stora.add(c5.getString(12));
							stora.add(c5.getString(13));
							stora.add(c5.getString(14));
							stora.add(c5.getString(15));
							stora.add(c5.getString(16));
                        }
						
						c5.close();
						
                        ioyote = stora.toString().replace("[", "");
                        ioyote = ioyote.replace("]", "");
                        Log.e("ioyote",ioyote);

                        new HttpAsyncTasktuma().execute(new String[]{URL_tuma});

                    }else{
                        Toast.makeText(getBaseContext(), "No data in internal database", Toast.LENGTH_LONG).show();
                    }

                    stora.clear();
					
					

					
					
                }

            }
        });

        updateUI();

    }
	
	public Bitmap ShrinkBitmap(String file, int width, int height)
    {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if(heightRatio > 1 || widthRatio > 1)
        {
            if(heightRatio > widthRatio)
            {
                bmpFactoryOptions.inSampleSize = heightRatio;
            }
            else
            {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }


    private boolean isgpsa(Activity activity) {
        // TODO Auto-generated method stub
         GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }





    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub

        startLocupdates();

    }

    @SuppressWarnings({"MissingPermission"})
    private void startLocupdates() {
        // TODO Auto-generated method stub

      if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);
        } else {

            PendingResult<Status> pr = LocationServices.FusedLocationApi.requestLocationUpdates(mgac, mlr, this);

            if (latitude!=0.0 && longitude!=0.0 ){
                sitato.setText("GPS Location : Detected");

            }else{
                sitato.setText("GPS Location : Searching...");
            }

        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
           mgac.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
        if (arg0.hasResolution()){

            try{
                arg0.startResolutionForResult(this, confail);
            } catch (IntentSender.SendIntentException e){
                e.printStackTrace();
            }

        }else{
            diambaidno(View);
        }
    }


    public void onDisconnected() {
        // TODO Auto-generated method stub
        updateUI();
    }


    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub
        cl = arg0;
        updateUI();
    }


    private void updateUI() {
        // TODO Auto-generated method stub


        if (null != cl){
            latitude = cl.getLatitude();
            longitude = cl.getLongitude();

            sax = String.valueOf(longitude);
            say = String.valueOf(latitude);
        }
    }

    // @Override
    // public void onBackPressed(){

    // }
	
	 public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    /*public void diaingia(View v) {
        final Dialog ingia = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        ingia.setContentView(R.layout.selchat);
        ingia.setCanceledOnTouchOutside(false);
        ingia.setCancelable(false);
        WindowManager.LayoutParams lp = ingia.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        ingia.getWindow().setAttributes(lp);
        updateUI();
        ingia.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ingia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ingia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Button regok = (Button) ingia.findViewById(R.id.sawa);
        Button regno = (Button) ingia.findViewById(R.id.rback);
        final EditText upwd = (EditText) ingia.findViewById(R.id.upwd);

        regok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



                if (nani.equals("") || simu.equals("")){

                    diambaidweni(View);

                }else if (upwd.getText().toString().equals("") ){

                    Toast.makeText(MainActivity.this,"Please fill in the login detail",Toast.LENGTH_LONG).show();

                }else {

                    //Toast.makeText(MainActivity.this,sax + " ----- " + say,Toast.LENGTH_LONG).show();

                    if (sax.equals("0.0") || say.equals("0.0")){
                        updateUI();
                        Toast.makeText(MainActivity.this,"Please turn on GPS then try again",Toast.LENGTH_LONG).show();
                    }else{
                        pswd = upwd.getText().toString().trim();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", java.util.Locale.getDefault());
                        dater = "IS_" + nani + "_" + simu + "_" + dateFormat.format(new Date());




                            if (pswda.equals(pswd)){

                                Intent intent = new Intent (MainActivity.this, Selekta.class);
                                intent.putExtra("lattt", String.valueOf(latitude));
                                intent.putExtra("lonnn", String.valueOf(longitude));
                                intent.putExtra("datno", dater);
                                startActivity(intent);

                            }else{
                                Toast.makeText(MainActivity.this,"Please fill in the correct password",Toast.LENGTH_LONG).show();
                            }



                    }
                }

            }
        });
        regno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ingia.dismiss();
            }
        });
        ingia.show();
    }
*/



    /*private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mpd = new ProgressDialog(MainActivity.this);
            mpd.setMessage("Authenticating. Make sure internet connection is active");
            mpd.setIndeterminate(true);
            mpd.setCanceledOnTouchOutside(false);
            mpd.setMax(100);
            mpd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mpd.show();
        }



        @Override
        protected String doInBackground(String... urls) {

            String output1=null;
            for (String url:urls){
                output1 = getOutputFromUrl(url);
            }

            return output1;
        }

        private String getOutputFromUrl(String url){
            String output1=null;
            StringBuilder sb1 = new StringBuilder();


            try {

                HttpClient httpclient1 = new DefaultHttpClient();
                HttpPost httppost1 = new HttpPost(url);
                List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(6);
                nameValuePairs1.add(new BasicNameValuePair("uname", nani));
                nameValuePairs1.add(new BasicNameValuePair("usimu", simu));
                nameValuePairs1.add(new BasicNameValuePair("mail", mail));
                nameValuePairs1.add(new BasicNameValuePair("pswd", pswd));
                nameValuePairs1.add(new BasicNameValuePair("usx", sax));
                nameValuePairs1.add(new BasicNameValuePair("usy", say));

                httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1, "utf-8"));
                HttpResponse httpr1 = httpclient1.execute(httppost1);

                if (httpr1.getStatusLine().getStatusCode() != 200) {
                    Log.d("this ndio hii", "Server encountered an error");
                }

     	       *//*HttpEntity he = httpr.getEntity();
     	       output = EntityUtils.toString(he);*//*

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(httpr1.getEntity().getContent(), "UTF8"));
                sb1 = new StringBuilder();
                sb1.append(reader1.readLine() + "\n");
                String line1 = null;

                while ((line1 = reader1.readLine()) != null) {
                    sb1.append(line1 + "\n");
                }

                output1 = sb1.toString();

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output1;

        }

        @SuppressWarnings("unused")
        protected void onProgressUpdate(int...progress) {
            mpd.setProgress(progress[0]);

        }



        @Override
        protected void onPostExecute(String output1) {

            try {
                mpd.dismiss();

                String vitingapi = output1.toString().trim();

                if (vitingapi.equals("pass")){

                    Cursor chk=spatiadb.rawQuery("SELECT * FROM pwdTBL WHERE userno='"+huyu+"'", null);
                    if(chk.moveToFirst())
                    {
                        spatiadb.execSQL("UPDATE pwdTBL SET usernem='"+nani+"',usertel='"+simu+"',pswd='"+pswd+"' WHERE userno='"+huyu+"'");
                    }
                    else
                    {
                        spatiadb.execSQL("INSERT INTO pwdTBL VALUES('"+huyu+"','"+nani+"','"+simu+"','"+pswd+"');");
                    }
					chk.close();


                    Intent intent = new Intent (MainActivity.this, Colecta.class);
                    intent.putExtra("lattt", String.valueOf(latitude));
                    intent.putExtra("lonnn", String.valueOf(longitude));
                    intent.putExtra("datno", dater);
                    startActivity(intent);


                }else if(vitingapi.equals("imereach")){
                    Toast.makeText(MainActivity.this,"Please fill in the correct password",Toast.LENGTH_LONG).show();
                }else{
                    diambaidno(View);
                }

            }catch(Exception x){
                Log.e("tf",x.toString());
                diambaidno(View);
            }


        }

    }*/



    public void diambaidweni(View v) {
        final Dialog mbott = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        mbott.setContentView(R.layout.mbaind_nowe);
        mbott.setCanceledOnTouchOutside(false);
        mbott.setCancelable(false);
        WindowManager.LayoutParams lp = mbott.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        mbott.getWindow().setAttributes(lp);
        mbott.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mbott.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button mbaok = (Button) mbott.findViewById(R.id.mbabtn1);
        mbaok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent (MainActivity.this, Regista.class);
                intent.putExtra("lattt", String.valueOf(latitude));
                intent.putExtra("lonnn", String.valueOf(longitude));
                intent.putExtra("reggo","main");
                startActivity(intent);

                mbott.dismiss();
            }
        });
        mbott.show();
    }


    public void diambaidweni2(View v) {
        final Dialog mbott = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        mbott.setContentView(R.layout.mbaind_no_org);
        mbott.setCanceledOnTouchOutside(false);
        mbott.setCancelable(false);
        WindowManager.LayoutParams lp = mbott.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        mbott.getWindow().setAttributes(lp);
        mbott.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mbott.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button mbaok = (Button) mbott.findViewById(R.id.mbabtn1);
        mbaok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent (MainActivity.this, Loginno.class);
                startActivity(intent);

                mbott.dismiss();
            }
        });
        mbott.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        if (id == R.id.itop1) {
            Intent intent = new Intent (MainActivity.this, Regista.class);
            intent.putExtra("lattt", String.valueOf(latitude));
            intent.putExtra("lonnn", String.valueOf(longitude));
            intent.putExtra("reggo","main");
            startActivity(intent);
            return true;
        }

        if (id == R.id.itop2) {
            Intent intent = new Intent (MainActivity.this, AboutUs.class);
            startActivity(intent);
            return true;
        }




        return super.onOptionsItemSelected(item);
    }


    public void diambaidno(View v) {
        final Dialog mbott = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        mbott.setContentView(R.layout.mbaind_nonet3);
        mbott.setCanceledOnTouchOutside(false);
        mbott.setCancelable(false);
        WindowManager.LayoutParams lp = mbott.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        mbott.getWindow().setAttributes(lp);
        mbott.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mbott.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button mbaok = (Button) mbott.findViewById(R.id.mbabtn1);
        mbaok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mbott.dismiss();
            }
        });
        mbott.show();
    }




    private class HttpAsyncTasktuma extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mpd = new ProgressDialog(MainActivity.this);
            mpd.setMessage("Sending... Make sure internet connection is active");
            mpd.setIndeterminate(true);
            mpd.setCanceledOnTouchOutside(false);
            mpd.setMax(100);
            mpd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mpd.show();
        }



        @Override
        protected String doInBackground(String... urls) {

            String output1=null;
            for (String url:urls){
                output1 = getOutputFromUrl(url);
            }

            return output1;
        }

        private String getOutputFromUrl(String url){
            String output1=null;
            StringBuilder sb1 = new StringBuilder();


            try {

                HttpClient httpclient1 = new DefaultHttpClient();
                HttpPost httppost1 = new HttpPost(url);
                List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(1);
                nameValuePairs1.add(new BasicNameValuePair("ioyote", ioyote));
                httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1, "utf-8"));
                HttpResponse httpr1 = httpclient1.execute(httppost1);

                if (httpr1.getStatusLine().getStatusCode() != 200) {
                    Log.d("this ndio hii", "Server encountered an error");
                }

        	       /*HttpEntity he = httpr.getEntity();
        	       output = EntityUtils.toString(he);*/

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(httpr1.getEntity().getContent(), "UTF8"));
                sb1 = new StringBuilder();
                sb1.append(reader1.readLine() + "\n");
                String line1 = null;

                while ((line1 = reader1.readLine()) != null) {
                    sb1.append(line1 + "\n");
                }

                output1 = sb1.toString();

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output1;

        }

        @SuppressWarnings("unused")
        protected void onProgressUpdate(int...progress) {
            mpd.setProgress(progress[0]);

        }



        @Override
        protected void onPostExecute(String output1) {

            try {
                mpd.dismiss();
                String vitingapi = output1.toString().trim();

                if (vitingapi.equals("pass")){
                    spatiadb.execSQL("DROP TABLE IF EXISTS datTBL");
                    spatiadb.execSQL("CREATE TABLE IF NOT EXISTS datTBL(datno VARCHAR,datftrname VARCHAR,datcnt VARCHAR,datiar VARCHAR,datgar VARCHAR,datcc VARCHAR,dathab VARCHAR,databd VARCHAR,datown VARCHAR,datara VARCHAR,datset VARCHAR,datcom VARCHAR,datx VARCHAR,daty VARCHAR,datpicnm VARCHAR,datorg VARCHAR,datcon VARCHAR);");

                    Cursor c5q=spatiadb.rawQuery("SELECT * FROM picTBL", null);

                    if(c5q!=null && c5q.getCount()>0) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss", java.util.Locale.getDefault());
                        zipfilo = "IS_"  +  simu + "_" + dateFormat.format(new Date()) + ".zip";


                        lapica();
                        new HttpAsyncTasktumapcha().execute(new String[]{URL_tumapic});


                    }else{
                        Toast.makeText(getBaseContext(), "No pending images in internal database", Toast.LENGTH_LONG).show();
                    }

                    c5q.close();

                    diambaids(View);
                }else if (vitingapi.equals("zae")){
                    diambaidno(View);
                }else{
                    Toast.makeText(MainActivity.this, vitingapi, Toast.LENGTH_SHORT).show();
                }

            }catch(Exception x){
                Log.e("tf",x.toString());
                diambaidno(View);
            }


        }

    }


    public void diambaids(View v) {
        final Dialog mbott = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        mbott.setContentView(R.layout.mbaind_sent);
        mbott.setCanceledOnTouchOutside(false);
        mbott.setCancelable(false);
        WindowManager.LayoutParams lp = mbott.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        mbott.getWindow().setAttributes(lp);
        mbott.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mbott.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button mbaok = (Button) mbott.findViewById(R.id.mbabtn1);
        mbaok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent (MainActivity.this, MainActivity.class);
                startActivity(intent);

                mbott.dismiss();
            }
        });
        mbott.show();
    }

    public void onResume(){
        super.onResume();

        if (mgac.isConnected()){
            startLocupdates();
        }

        //new HttpAsyncTask0().execute(new String[]{URL2});
    }

    public void onStart(){
        super.onStart();
        mgac.connect();
        //new HttpAsyncTask0().execute(new String[]{URL2});
    }

    public void onPause(){
        super.onPause();
        try{

            if (mgac.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mgac, this);
                mgac.disconnect();
            }
        }catch(Exception x){

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RECOVER_PLAY_SERVICES) {
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mgac.isConnecting() &&
                        !mgac.isConnected()) {
                    mgac.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Google Play Services must be installed.",
                        Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }


    @SuppressWarnings({"MissingPermission"})
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                PendingResult<Status> pr = LocationServices.FusedLocationApi.requestLocationUpdates(mgac, mlr, this);

                if (latitude!=0.0 && longitude!=0.0 ){
                    sitato.setText("GPS Location : Detected");

                }else{
                    sitato.setText("GPS Location : Searching...");
                }


            } else {
                Toast.makeText(MainActivity.this, "GPS Location services must be detected first. If GPS is ON kindly wait as location is detected.", Toast.LENGTH_SHORT).show();
            }
        }
    }
	
	
	 private class HttpAsyncTasktumapcha extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           mpd = new ProgressDialog(MainActivity.this);
            mpd.setMessage("Sending Images... Make sure internet connection is active");
            mpd.setIndeterminate(true);
            mpd.setCanceledOnTouchOutside(false);
            mpd.setMax(100);
            mpd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mpd.show();
        }



        @Override
        protected String doInBackground(String... urls) {

            String output1=null;
            for (String url:urls){
                output1 = getOutputFromUrl(url);
            }

            return output1;
        }

        private String getOutputFromUrl(String url){
            String output1=null;
            StringBuilder sb1 = new StringBuilder();


            try {



                HttpClient httpclient1 = new DefaultHttpClient();
                HttpPost httppost1 = new HttpPost(url);
                List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(2);
                nameValuePairs1.add(new BasicNameValuePair("pichan", zipfilo));
                nameValuePairs1.add(new BasicNameValuePair("pichar", lefile));
                httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1, "utf-8"));
                HttpResponse httpr1 = httpclient1.execute(httppost1);

                if (httpr1.getStatusLine().getStatusCode() != 200) {
                    Log.d("this ndio hii", "Server encountered an error");
                }

        	       /*HttpEntity he = httpr.getEntity();
        	       output = EntityUtils.toString(he);*/

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(httpr1.getEntity().getContent(), "UTF8"));
                sb1 = new StringBuilder();
                sb1.append(reader1.readLine() + "\n");
                String line1 = null;

                while ((line1 = reader1.readLine()) != null) {
                    sb1.append(line1 + "\n");
                }

                output1 = sb1.toString();

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output1;

        }

        @SuppressWarnings("unused")
        protected void onProgressUpdate(int...progress) {
            mpd.setProgress(progress[0]);

        }



        @Override
        protected void onPostExecute(String output1) {

            mpd.dismiss();

            try {
                String vitingapi = output1.toString().trim();

                if (vitingapi.contains("inflating")){
                    /*spatiadb.execSQL("DROP TABLE IF EXISTS picTBL");
                    spatiadb.execSQL("CREATE TABLE IF NOT EXISTS picTBL(userpic VARCHAR);");*/
/*
                    String basi = "done";
                    sjdbajs

                    Cursor chk2=spatiadb.rawQuery("SELECT * FROM picTBL WHERE userpic='"+pico+"'", null);
                    if(chk2.moveToFirst())
                    {
                        spatiadb.execSQL("UPDATE picTBL SET sendstat='"+basi+"' WHERE userpic='"+pico+"'");

                        String picok = chk2.getString(0);
                        String picopk = chk2.getString(1);
                        String picopkk = chk2.getString(2);

                        Log.e(" IMAGES SUCCESS ---- ", picok + " ---------- " + picopk + " ---------- " + picopkk);


                    }
                    chk2.close();*/

                    spatiadb.execSQL("DROP TABLE IF EXISTS picTBL");
                    spatiadb.execSQL("CREATE TABLE IF NOT EXISTS picTBL(userpic VARCHAR, userpicpath VARCHAR, sendstat VARCHAR);");


              //      diambaids(View);
                }else{
                 //   diambaidno(View);
                }

            }catch(Exception x){
               // diambaidno(View);
            }
        }

    }


    private void lapica(){

        storapic.clear();
        Cursor chk2f=spatiadb.rawQuery("SELECT * FROM picTBL", null);

        if (chk2f.moveToFirst())
        {
            String picok = chk2f.getString(0);
            String picopk = chk2f.getString(1);
            String picopkk = chk2f.getString(2);

           // Log.e(" IMAGES AS IS ---- ", picok + " ---------- " + picopk + " ---------- " + picopkk);


            Bitmap iopic = ShrinkBitmap(picopk, 200, 200);
            File file = new File(picopk);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                iopic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }


            storapic.add(picopk);

        }



        while (chk2f.moveToNext())
        {
            String picok = chk2f.getString(0);
            String picopk = chk2f.getString(1);
            String picopkk = chk2f.getString(2);

           // Log.e(" IMAGES AS IS ---- ", picok + " ---------- " + picopk + " ---------- " + picopkk);


            Bitmap iopic = ShrinkBitmap(picopk, 200, 200);
            File file = new File(picopk);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                iopic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }


            storapic.add(picopk);

        }

        chk2f.close();

        /*for(int i=0; i < storapic.size(); i++) {
            Log.e(" Loops ---- ", String.valueOf(i));
        }*/

        String root = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM +"/invspc";
        File myDir = new File(root,zipfilo);
        if (!myDir.exists()) {
            myDir.getParentFile().mkdirs();
        }else{
            myDir.getParentFile().delete();
            myDir.getParentFile().mkdirs();
        }
        String zippath = myDir.getAbsolutePath();

        try  {

            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zippath);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[2048];

            for(int i=0; i < storapic.size(); i++) {
                //Log.e("Compress", "Adding: " + storapic.get(i));
                FileInputStream fi = new FileInputStream(storapic.get(i));
                origin = new BufferedInputStream(fi, 2048);
                ZipEntry entry = new ZipEntry(storapic.get(i).substring(storapic.get(i).lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();


            String encodeFileToBase64Binary = encodeFileToBase64Binary(myDir);

            lefile = encodeFileToBase64Binary;




        } catch(Exception e) {
            //Log.e(" error ---- ", e.toString());
            e.printStackTrace();
        }
    }

    private static String encodeFileToBase64Binary(File fileName) throws IOException {
        //byte[] bytes = loadFile(fileName);

        FileInputStream fileInputStream=null;


        byte[] bFile = new byte[(int) fileName.length()];

        try {
            //convert file into array of bytes

            fileInputStream = new FileInputStream(fileName);
            fileInputStream.read(bFile);
            fileInputStream.close();

            /*for (int i = 0; i < bFile.length; i++) {
                System.out.print((char)bFile[i]);
            }*/

        }catch(Exception e){
            e.printStackTrace();
        }

         //below byte to string
        //String str = new String(bFile, StandardCharsets.UTF_8);

        String encodedString  = Base64.encodeToString(bFile, Base64.DEFAULT);
        return encodedString;
    }




/*    public static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }*/






}