package com.servir.invasivespecies;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Loginno extends AppCompatActivity {

    EditText logemailA, logpassA;
    //TextView logsignupA, logforgotA;
    Button logsigninA, logregA;
    Spinner orga, consa;
    EditText orga2, consa2;
    View View;
    String mail="";
    String pass="";
    private SQLiteDatabase spatiadb;
    locTrak2 locii =  new locTrak2(Loginno.this);
    String huyu = "user";
    String statt = "";
    String pswd = "";
    String myil = "";
    String sax = "0.0";
    String say = "0.0";
    String sorg, scon;

    public static final String URL1 = "http://mobiledata.rcmrd.org/invspec/checka.php";


    List<String> smalla2 = new ArrayList<String>();

    ProgressDialog mpd;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggah);

        overridePendingTransition(0,0);

        logsigninA = (Button) findViewById(R.id.logingia);
        logregA = (Button) findViewById(R.id.logreg);
        logpassA = (EditText) findViewById(R.id.logpass);
        logemailA = (EditText) findViewById(R.id.logemail);
        //logsignupA = (TextView) findViewById(R.id.logreg);
        //logforgotA = (TextView) findViewById(R.id.logforgot);
        orga = (Spinner) findViewById(R.id.logorg);
        consa = (Spinner) findViewById(R.id.logcon);
        orga2 = (EditText) findViewById(R.id.logorg2);
        consa2 = (EditText) findViewById(R.id.logcon2);

        spatiadb=openOrCreateDatabase("InvSpecDB", Context.MODE_PRIVATE, null);
        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userTBL(userno VARCHAR,usernem VARCHAR,usertel VARCHAR,usercntry VARCHAR,useremail VARCHAR,userpass VARCHAR);");
        spatiadb.execSQL("DROP TABLE IF EXISTS userareaTBL");
        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userareaTBL(userno VARCHAR, userorg VARCHAR, usercons VARCHAR);");

        Cursor c=spatiadb.rawQuery("SELECT * FROM userTBL WHERE userno='"+huyu+"'", null);
        if(c.moveToFirst())
        {
            mail = c.getString(4);
            pass = c.getString(5);
        }else{
            statt = "unreg";
        }


        Timer timer = new Timer("swcha");
        timer.schedule(new TimerTask() {

            public void run() {

                runOnUiThread(new Runnable() {


                    @Override
                    public void run() {

                            //locii = new locTrak2(Loginno.this);
                            if (locii.canGetLocation()) {
                                if (locii.getLatitude() != 0.0 && locii.getLongitude() != 0.0) {

                                    double slatitude = locii.getLatitude();
                                    double slongitude = locii.getLongitude();
                                    sax = String.valueOf(slongitude);
                                    say = String.valueOf(slatitude);


                                }
                            }
                    }

                });

            }

        }, 0, 2000);




        logsigninA.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                if (logemailA.getText().toString().equals("") ||
                        logpassA.getText().toString().equals("") ||
                        orga.getSelectedItem().toString().trim().equals("SELECT")||
                        consa.getSelectedItem().toString().trim().equals("SELECT")) {

                    Toast.makeText(Loginno.this, "Please complete the login detail", Toast.LENGTH_LONG).show();


                }else if(orga.getSelectedItem().toString().trim().equals("Other") &&
                        orga2.getText().toString().equals("")){

                    Toast.makeText(Loginno.this, "Please specify the organisation", Toast.LENGTH_LONG ).show();

                }else if(consa.getSelectedItem().toString().trim().equals("Other") &&
                        consa2.getText().toString().equals("")){

                    Toast.makeText(Loginno.this, "Please specify the conservancy", Toast.LENGTH_LONG ).show();


                } else {

                    pswd = logpassA.getText().toString().trim();
                    myil = logemailA.getText().toString().trim();

                    if (orga.getSelectedItem().toString().trim().equals("Other")){
                        sorg = orga2.getText().toString().trim();
                    }else{
                        sorg = orga.getSelectedItem().toString().trim();
                    }

                    if (consa.getSelectedItem().toString().trim().equals("Other")){
                        scon = consa2.getText().toString().trim();
                    }else{
                        scon = consa.getSelectedItem().toString().trim();
                    }


                    sorg = sorg.replace("'", "''");
                    scon = scon.replace("'", "''");
                    sorg = sorg.replace(",", "~");
                    scon = scon.replace(",", "~");



                    if (statt.equals("unreg")){
                        diambaidnet(View);
                    }else{

                    if (pswd.equals(pass) && myil.equals(mail)) {


                        Cursor chk3=spatiadb.rawQuery("SELECT * FROM userareaTBL WHERE userno='"+huyu+"'", null);
                        if(chk3.moveToFirst())
                        {
                            spatiadb.execSQL("DROP TABLE IF EXISTS userareaTBL");
                            spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userareaTBL(userno VARCHAR, userorg VARCHAR, usercons VARCHAR);");
                            spatiadb.execSQL("INSERT INTO userareaTBL VALUES('"+huyu+"','"+sorg+"','"+scon+"');");
                        }
                        else
                        {
                            spatiadb.execSQL("INSERT INTO userareaTBL VALUES('"+huyu+"','"+sorg+"','"+scon+"');");
                        }
                        chk3.close();

                        Intent intent = new Intent(Loginno.this, MainActivity.class);
                        startActivity(intent);
                    } else{
                            Toast.makeText(Loginno.this, "Incorrect login detail. Please try again.", Toast.LENGTH_LONG).show();
                        }

                        logemailA.setText("");
                        logpassA.setText("");
                    }
                }


            }




        });




        logregA.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                if (statt.equals("unreg")) {
                    finish();
                    Intent intent = new Intent(Loginno.this, Regista.class);
                    intent.putExtra("reggo", "login");
                    intent.putExtra("lattt", say);
                    intent.putExtra("lonnn", sax);
                    startActivity(intent);
                }else{
                    Toast.makeText(Loginno.this, "You have already registered to use the system. [Sign In] to access the Registration form through the menu.", Toast.LENGTH_LONG).show();
                }

            }

        });


        orga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> arg0,
									   android.view.View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				String mm = orga.getSelectedItem().toString();

				if (mm.equals("Other")){
                    orga2.setVisibility(android.view.View.VISIBLE);
					orga2.setEnabled(true);
				}else{
					orga2.setText("");
					orga2.setEnabled(false);
                    orga2.setVisibility(android.view.View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});


        consa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> arg0,
									   android.view.View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
                String mm = consa.getSelectedItem().toString();

                if (mm.equals("Other")){
                    consa2.setVisibility(android.view.View.VISIBLE);
                    consa2.setEnabled(true);
                }else{
                    consa2.setText("");
                    consa2.setEnabled(false);
                    consa2.setVisibility(android.view.View.GONE);
                }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});



    }






/*
    public void onClick_su(View v)
    {


    }


    public void onClick_fd(View v)
    {
     //
    }*/





    public void diambaidno(View v) {
        final Dialog mbott = new Dialog(Loginno.this, android.R.style.Theme_Translucent_NoTitleBar);
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



    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mpd = new ProgressDialog(Loginno.this);
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
                List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(2);
                nameValuePairs1.add(new BasicNameValuePair("mail", myil));
                nameValuePairs1.add(new BasicNameValuePair("pswd", pswd));

                httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1, "utf-8"));
                HttpResponse httpr1 = httpclient1.execute(httppost1);

                if (httpr1.getStatusLine().getStatusCode() != 200) {
                    Log.d("this ndio hii", "Server encountered an error");
                }


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

                String mekam2 = output1.toString().trim();

                if (mekam2.length() >= 13){

                    smalla2 = Arrays.asList(mekam2.split(","));

                    Cursor chk=spatiadb.rawQuery("SELECT * FROM userTBL WHERE userno='"+huyu+"'", null);
                    if(chk.moveToFirst())
                    {
                        spatiadb.execSQL("UPDATE userTBL SET usernem='"+smalla2.get(0)+"',usertel='"+smalla2.get(1)+"',usercntry='"+smalla2.get(2)+"',useremail='"+smalla2.get(3)+"', userpass='"+smalla2.get(4)+"' WHERE userno='"+huyu+"'");
                    }
                    else
                    {
                        spatiadb.execSQL("INSERT INTO userTBL VALUES('"+huyu+"','"+smalla2.get(0)+"','"+smalla2.get(1)+"','"+smalla2.get(2)+"','"+smalla2.get(3)+"','"+smalla2.get(4)+"');");
                    }
                    chk.close();

                    Cursor chk2=spatiadb.rawQuery("SELECT * FROM userareaTBL WHERE userno='"+huyu+"'", null);
                    if(chk2.moveToFirst())
                    {
                        spatiadb.execSQL("DROP TABLE IF EXISTS userareaTBL");
                        spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userareaTBL(userno VARCHAR, userorg VARCHAR, usercons VARCHAR);");
                        spatiadb.execSQL("INSERT INTO useraereaTBL VALUES('"+huyu+"','"+sorg+"','"+scon+"');");
                    }
                    else
                    {
                        spatiadb.execSQL("INSERT INTO userareaTBL VALUES('"+huyu+"','"+sorg+"','"+scon+"');");
                    }
                    chk2.close();


                    Intent intent = new Intent(Loginno.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(Loginno.this, "Incorrect login detail. Please try again.", Toast.LENGTH_LONG).show();

                        logemailA.setText("");
                        logpassA.setText("");
                }

            }catch(Exception x){
                //Log.e("FM_login_error_auth",x.toString());
                diambaidno(View);
            }


        }

    }


    public void diambaidnet(View v) {
        final Dialog ingia = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        ingia.setContentView(R.layout.mbaind_net);
        ingia.setCanceledOnTouchOutside(false);
        ingia.setCancelable(false);
        WindowManager.LayoutParams lp = ingia.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        ingia.getWindow().setAttributes(lp);
        ingia.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ingia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ingia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Button regno = (Button) ingia.findViewById(R.id.sawa);
        Button regyes = (Button) ingia.findViewById(R.id.rback);

        regyes.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v){

                ingia.dismiss();
                new HttpAsyncTask2().execute(new String[]{URL1});

            }
        });
        regno.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v){

                Toast.makeText(Loginno.this, "No active user account exists on the phone. Please Sign Up first ", Toast.LENGTH_LONG).show();
                ingia.dismiss();
            }
        });
        ingia.show();
    }



}
