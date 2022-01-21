package com.servir.invasivespecies;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.servir.invasivespecies.utils.ApplicationContextor;
import com.servir.invasivespecies.utils.AsyncTaskCompleteListener;
import com.servir.invasivespecies.utils.Constantori;
import com.servir.invasivespecies.utils.DatabaseHandler;
import com.servir.invasivespecies.utils.NetPost;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Loginno extends AppCompatActivity implements AsyncTaskCompleteListener {

    TextInputLayout Tlogphone, Tlogpass, Tlogorg, Tlogcons, Tlogorg_other, Tlogcons_other;
    EditText edtPhone, edtPass, edtOrg_other, edtCons_other;
    AutoCompleteTextView actOrg, actCons;
    View View;

    String dbPhone = "";
    String dbPass = "";
    String strPhone = "";
    String strPass= "";
    String strOrg = "";
    String strCons= "";

    String userRefo = "";

    String userstatus, usertoken, userid, locno;

    private boolean isLocationRequestShowing = false;

    DatabaseHandler db = DatabaseHandler.getInstance(this);

    String sLat = "0.0";
    String sLon = "0.0";
    String REQUEST_PERM4 = "";
    private final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 13;
    GoogleApiClient client;
    Context mContext;

    private Button logsigninA;
    private View parent_view;

    private ArrayList<String> URL_LINKS = new ArrayList<String>();
    private String URL_LINK = "";

    String userTel, userEmail;

    Context context = this;

    boolean first_login = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggah_new);
        mContext = this;

        parent_view = findViewById(android.R.id.content);

        overridePendingTransition(0,0);

        if (!Constantori.allPermissionsGranted(context)) {
            reqPermission("loginnoTOP");
        }else{

            //ensure all folders exist
            if(Constantori.isExternalStorageWritable()) {

                if (Constantori.createAllFolders()) {
                    Log.e(Constantori.APP_ERROR_PREFIX + "_AllFolders", "Done");
                } else {
                    Log.e(Constantori.APP_ERROR_PREFIX + "_AllFolders", "Not Done");
                    Snackbar.make(parent_view, Constantori.ERROR_NO_MEDIA, Snackbar.LENGTH_LONG).show();
                }
            }else{
                Log.e(Constantori.APP_ERROR_PREFIX + "_AllFolders", "Not Readable");
                Snackbar.make(parent_view, Constantori.ERROR_NO_WRITE_ACCESS, Snackbar.LENGTH_LONG).show();
            }

        }


        if (!Constantori.isgpsa(this)) {
            Snackbar.make(parent_view, getResources().getString(R.string.general_googleplay), Snackbar.LENGTH_SHORT).show();
        }

        Tlogphone = (TextInputLayout)  findViewById(R.id.textILphone);
        Tlogpass = (TextInputLayout)  findViewById(R.id.textILpass);
        Tlogorg = (TextInputLayout)  findViewById(R.id.textILorg);
        Tlogorg_other = (TextInputLayout)  findViewById(R.id.textILother_org);
        Tlogcons = (TextInputLayout)  findViewById(R.id.textILcons);
        Tlogcons_other = (TextInputLayout)  findViewById(R.id.textILother_cons);
        parent_view = findViewById(android.R.id.content);
        logsigninA = (Button) findViewById(R.id.logingia);
        edtPass = (EditText) findViewById(R.id.edtUserpassword);
        edtPhone = (EditText) findViewById(R.id.edtUserphone);
        actOrg = (AutoCompleteTextView) findViewById(R.id.actUserorg);
        edtOrg_other = (EditText) findViewById(R.id.edtUserother_org);
        actCons = (AutoCompleteTextView) findViewById(R.id.actUsercons);
        edtCons_other = (EditText) findViewById(R.id.edtUserother_cons);
        edtPhone.addTextChangedListener(new MyTextWatcher(Tlogphone));
        edtPass.addTextChangedListener(new MyTextWatcher(Tlogpass));
        edtOrg_other.addTextChangedListener(new MyTextWatcher(Tlogorg_other));
        edtCons_other.addTextChangedListener(new MyTextWatcher(Tlogcons_other));

        URL_LINKS = Constantori.getURLs(Constantori.getFromSharedPreference(Constantori.KEY_USERCNTRYCODE));
        URL_LINK = URL_LINKS.get(0);

        doDBstuff("Initial");

        if(ApplicationContextor.getGoogleApiHelper().isConnected())
        {

            Log.e(Constantori.APP_ERROR_PREFIX + "_Location", "one");

            ApplicationContextor.getGoogleApiHelper().onGPSOFF(context, Loginno.this);


            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Loginno.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        Constantori.REQUEST_LOCATION);

                ActivityCompat.requestPermissions(Loginno.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        Constantori.REQUEST_LOCATION);
            } else {

                client = ApplicationContextor.getGoogleApiHelper().getGoogleApiClient();
                Location ll = LocationServices.FusedLocationApi.getLastLocation(client);

                if (ll != null) {
                    sLon = String.valueOf(ll.getLongitude());
                    sLat = String.valueOf(ll.getLatitude());
                }
            }

        }else{


            final Timer timer = new Timer("swcha");
            timer.schedule(new TimerTask() {

                public void run() {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            if (!sLat.equals("0.0") && !sLon.equals("0.0")) {

                                timer.cancel();

                            } else {

                                ArrayList<String> coords =  Constantori.nonGPlayServices(context, Loginno.this);

                                if(coords.size() != 2){
                                    if(!isLocationRequestShowing) {
                                        ApplicationContextor.getGoogleApiHelper().onGPSOFF(context, Loginno.this);
                                        isLocationRequestShowing = true;
                                    }
                                }else{
                                    sLon = coords.get(1);
                                    sLat = coords.get(0);
                                }


                            }


                        }

                    });

                }

            }, 0, 2000);

        }


        logsigninA.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                if (!validatePhone()) {
                    return;
                }

                if (!validatePass()) {
                    return;
                }

                if (!validate_orgs()) {
                    return;
                }

                if (!validate_cons()) {
                    return;
                }

                if (Constantori.allPermissionsGranted(context)) {
                    loginnoINGIA();
                }else{
                    reqPermission("loginnoINGIA");
                }

            }

        });

    }



    public void onClick_su(View v)
    {

        if (Constantori.allPermissionsGranted(context)) {
            loginnoREG();
        }else{
            reqPermission("loginnoREG");
        }

    }


    public void onClick_fd(View v)
    {
        dlg_forgot_password(View);
    }

    public void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private void loginnoINGIA(){

        hideSoftKeyboard();

        if (edtPhone.getText().toString().equals("")
                || edtPass.getText().toString().equals("")
        ) {

            Snackbar.make(parent_view, getResources().getString(R.string.signin_fill_all), Snackbar.LENGTH_SHORT).show();

        } else {


            strPass = edtPass.getText().toString().trim();
            strPhone = edtPhone.getText().toString().trim();

            if (db.CheckIsDataAlreadyInDBorNot(Constantori.TABLE_LOC, Constantori.KEY_LOCCON, strCons)) {




            }else{
                //add the conservancy


            }

            if (userstatus.equals(Constantori.USERINACTIVE)){

                try {
                    JSONArray json = new JSONArray();
                    JSONObject json_ = new JSONObject();
                    json_.put(Constantori.KEY_USERTEL, strPhone);
                    json_.put(Constantori.KEY_USERPASS, strPass);
                    json.put(json_);
                    new NetPost(context, "login_CheckJSON", json, getResources().getString(R.string.signin_message), "", "", Loginno.this).execute(new String[]{URL_LINK});
                }catch(Exception e){

                }

            }else{

                if (strPass.equals(dbPass) && strPhone.equals(dbPhone)) {

                    if(Tlogorg_other.getVisibility() == android.view.View.VISIBLE && Tlogcons_other.getVisibility() == android.view.View.VISIBLE) {
                        insertOrgConToDB(strOrg, strCons);
                    }else{
                        List<HashMap<String, String>> allData = db.GetAllData(Constantori.TABLE_LOC, Constantori.KEY_LOCORG, strOrg);
                        HashMap<String, String> allDetails = allData.get(0);
                        locno = allDetails.get(Constantori.KEY_LOCNO);
                        Constantori.setSharedPreference(Constantori.KEY_LOCNO, locno);
                    }

                    Intent intent = new Intent(Loginno.this, MainActivity.class);
                    startActivity(intent);
                } else if (!strPass.equals(dbPass) && strPhone.equals(dbPhone)) {
                    Snackbar.make(parent_view, getResources().getString(R.string.signin_password_wrong), Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(parent_view, getResources().getString(R.string.signin_details_wrong), Snackbar.LENGTH_SHORT).show();
                }

                //edtPass.setText("");
            }
        }

    }

    private void loginnoREG(){

        finish();

        Intent intent = new Intent(Loginno.this, Regista.class);
        intent.putExtra(Constantori.PAGE_REDIRECT, Constantori.PAGE_REDIRECT_SIGNIN);
        intent.putExtra(Constantori.INTENT_LAT, sLat);
        intent.putExtra(Constantori.INTENT_LON, sLon);
        startActivity(intent);

    }


    @SuppressLint("NewApi")
    private void reqPermission(final String nini) {

        REQUEST_PERM4 = nini;

        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, android.Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Cell Network");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = getResources().getString(R.string.general_permission) + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        return;

    }

    @SuppressLint("NewApi")
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message);
        builder.setPositiveButton("OK", okListener);
        builder.setNeutralButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.create();
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        positiveButton.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
        positiveButton.setBackgroundResource(R.drawable.btn_rounded);

        neutralButton.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
        neutralButton.setBackgroundResource(R.drawable.btn_rounded);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    if (REQUEST_PERM4.equals("loginnoINGIA")) {
                        loginnoINGIA();
                    }

                    if (REQUEST_PERM4.equals("loginnoREG")) {
                        loginnoREG();
                    }

                    Constantori.createAllFolders();

                    Snackbar.make(parent_view, getResources().getString(R.string.general_permission_enabled), Snackbar.LENGTH_SHORT).show();

                } else {
                    Snackbar.make(parent_view, getResources().getString(R.string.general_permission_denied), Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.textILphone:
                    validatePhone();
                    break;
                case R.id.textILpass:
                    validatePass();
                    break;
                case R.id.textILother_cons:
                    validateOther_cons();
                    break;
                case R.id.textILother_org:
                    validateOther_orgs();
                    break;
            }
        }
    }


    private boolean validatePhone() {
        String phoneNumber = edtPhone.getText().toString().trim();

        if (phoneNumber.isEmpty() || !isValidPhone(phoneNumber)) {
            Tlogphone.setError(getString(R.string.err_msg_phone));
            requestFocus(edtPhone);
            return false;
        } else {
            Tlogphone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOther_orgs() {

        strOrg = edtOrg_other.getText().toString().trim();

        if (strOrg.isEmpty()) {
            Tlogorg_other.setError(getString(R.string.err_msg_org));
            requestFocus(edtOrg_other);
            return false;
        } else {
            Tlogorg_other.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOther_cons() {

        strCons = edtCons_other.getText().toString().trim();

        if (strCons.isEmpty()) {
            Tlogcons_other.setError(getString(R.string.err_msg_cons));
            requestFocus(edtCons_other);
            return false;
        } else {
            Tlogcons_other.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validate_orgs() {

        strOrg = actOrg.getText().toString().trim();

        if (strOrg.equals("SELECT")) {
            Tlogorg.setError(getString(R.string.err_msg_org_sel));
            requestFocus(actOrg);
            return false;
        }else if(strOrg.equals("OTHER")){
            validateOther_orgs();
        } else {
            Tlogorg.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validate_cons() {

        strCons = actCons.getText().toString().trim();

        if (strCons.equals("SELECT")) {
            Tlogcons.setError(getString(R.string.err_msg_cons_sel));
            requestFocus(actCons);
            return false;
        }else if(strCons.equals("OTHER")){
            validateOther_cons();
        } else {
            Tlogcons.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePass() {
        if (edtPass.getText().toString().trim().isEmpty()) {
            Tlogpass.setError(getString(R.string.err_msg_password));
            requestFocus(edtPass);
            return false;
        } else {
            Tlogpass.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidPhone(String phoneN) {
        return !TextUtils.isEmpty(phoneN) && Patterns.PHONE.matcher(phoneN).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public void AsyncTaskCompleteListener(String result, String sender, String TableName, String FieldName)
    {
        switch (sender){

            case "loginLoc_PostJSON":

                Log.e(Constantori.APP_ERROR_PREFIX + "_Loc", result);

                if (result.equals("202")) {
                    Snackbar.make(parent_view, Constantori.ERROR_APPROVAL, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("101")) {
                    Snackbar.make(parent_view, Constantori.ERROR_PASSWORD, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals(null) || result.equals("303")) {
                    Snackbar.make(parent_view, Constantori.ERROR_SERVER_ISSUE, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("Issue")) {
                    Constantori.dlgNoNet(View, context);

                }else {

                    try {

                        JSONArray receivedResponse = new JSONArray(result);

                        Log.e(Constantori.APP_ERROR_PREFIX + "_JSONLOC_1", receivedResponse.toString());

                        if (receivedResponse.getJSONObject(0).getBoolean(Constantori.RECEIVED_SUCCESS)) {

                            String jsonIntegrity = receivedResponse.getJSONObject(0).getString(Constantori.KEY_LOC_INTEGRITY);

                            Log.e(Constantori.APP_ERROR_PREFIX + "_JSONLOC_2", jsonIntegrity);

                            if (Constantori.checkSharedPreference(Constantori.KEY_LOC_INTEGRITY)){

                                if(!Constantori.getFromSharedPreference(Constantori.KEY_LOC_INTEGRITY).equals(jsonIntegrity)) {

                                    db.resetTable(Constantori.TABLE_LOC, "", "");
                                    JSONArray jsonLOC = receivedResponse.getJSONObject(0).getJSONArray(Constantori.RECEIVED_DATA);
                                    db.insertDataToTable(Constantori.TABLE_LOC, jsonLOC);

                                    addOrgs();

                                    Log.e(Constantori.APP_ERROR_PREFIX + "_JSONLOC_3", db.GetAllData(Constantori.TABLE_LOC, "", "").toString());
                                    Log.e(Constantori.APP_ERROR_PREFIX + "_JSONLOC_4", jsonLOC.toString());

                                    Constantori.setSharedPreference(Constantori.KEY_LOC_INTEGRITY, jsonIntegrity);

                                    if(first_login) {
                                        finish();

                                        if(Tlogorg_other.getVisibility() == android.view.View.VISIBLE && Tlogcons_other.getVisibility() == android.view.View.VISIBLE) {
                                            insertOrgConToDB(strOrg, strCons);
                                        }else{
                                            List<HashMap<String, String>> allData = db.GetAllData(Constantori.TABLE_LOC, Constantori.KEY_LOCORG, strOrg);
                                            HashMap<String, String> allDetails = allData.get(0);
                                            locno = allDetails.get(Constantori.KEY_LOCNO);
                                            Constantori.setSharedPreference(Constantori.KEY_LOCNO, locno);
                                        }

                                        Intent intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);
                                    }

                                }

                            } else { //new forms

                                db.resetTable(Constantori.TABLE_LOC, "", "");

                                //fill LOC table
                                JSONArray jsonLOC = receivedResponse.getJSONObject(0).getJSONArray(Constantori.RECEIVED_DATA);

                                db.insertDataToTable(Constantori.TABLE_LOC, jsonLOC);

                                Log.e(Constantori.APP_ERROR_PREFIX + "_JSONLOC_3", db.GetAllData(Constantori.TABLE_LOC, "", "").toString());

                                Log.e(Constantori.APP_ERROR_PREFIX + "_JSONLOC_4", jsonLOC.toString());

                                addOrgs();

                                Constantori.setSharedPreference(Constantori.KEY_LOC_INTEGRITY, jsonIntegrity);

                                if(first_login) {
                                    finish();

                                    if(Tlogorg_other.getVisibility() == android.view.View.VISIBLE && Tlogcons_other.getVisibility() == android.view.View.VISIBLE) {
                                        insertOrgConToDB(strOrg, strCons);
                                    }else{
                                        List<HashMap<String, String>> allData = db.GetAllData(Constantori.TABLE_LOC, Constantori.KEY_LOCORG, strOrg);
                                        HashMap<String, String> allDetails = allData.get(0);
                                        locno = allDetails.get(Constantori.KEY_LOCNO);
                                        Constantori.setSharedPreference(Constantori.KEY_LOCNO, locno);
                                    }

                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }

                            }

                        } else {
                            Snackbar.make(parent_view, receivedResponse.getJSONObject(0).getString(Constantori.RECEIVED_MESSAGE), Snackbar.LENGTH_SHORT).show();
                        }


                    } catch (Exception xx) {
                        Log.e(Constantori.APP_ERROR_PREFIX + "_LocErrorRes0", result);
                        Log.e(Constantori.APP_ERROR_PREFIX + "_LocErrorRes1", "exception", xx);
                    }
                }

                break;

            case "login_CheckJSON":

                if (result.equals("202")) {
                    Snackbar.make(parent_view, Constantori.ERROR_APPROVAL, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("101")) {
                    Snackbar.make(parent_view, Constantori.ERROR_PASSWORD, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals(null) || result.equals("303")) {
                    Snackbar.make(parent_view, Constantori.ERROR_SERVER_ISSUE, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("Issue")) {
                    Constantori.dlgNoNet(View, context);

                }else {

                    try {

                        JSONArray receivedResponse = new JSONArray(result);

                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginno_1", receivedResponse.toString());

                        String userId = String.valueOf(receivedResponse.getJSONObject(0).getInt(Constantori.KEY_USERID));
                        String userName = receivedResponse.getJSONObject(0).getString(Constantori.KEY_USERNEM);
                        userTel = receivedResponse.getJSONObject(0).getString(Constantori.KEY_USERTEL);
                        userEmail = receivedResponse.getJSONObject(0).getString(Constantori.KEY_USEREMAIL);
                        String userStatus = String.valueOf(receivedResponse.getJSONObject(0).getInt(Constantori.KEY_USERSTATUS));

                        if(userStatus.equals("1")){
                            userStatus = Constantori.USERACTIVE;
                        }else if (userStatus.equals("0")){
                            userStatus = Constantori.USERINACTIVE;
                        }else{
                            userStatus = Constantori.USERINACTIVE;
                        }

                        userRefo = "IS_" + userName.replaceAll(" ", "_") + "_" + userTel;

                        if (userStatus.equals(Constantori.USERACTIVE)) {

                            Log.e(Constantori.APP_ERROR_PREFIX + "_loginno", "inside");

                            Constantori.setSharedPreference(Constantori.KEY_USERID, userId);
                            Constantori.setSharedPreference(Constantori.KEY_USERNEM, userName);
                            Constantori.setSharedPreference(Constantori.KEY_USERTEL, userTel);
                            Constantori.setSharedPreference(Constantori.KEY_USERPASS, strPass);
                            Constantori.setSharedPreference(Constantori.KEY_USEREMAIL, userEmail);
                            Constantori.setSharedPreference(Constantori.KEY_USERSTATUS, userStatus);
                            Constantori.setSharedPreference(Constantori.KEY_USERREF, userRefo);

                            Log.e(Constantori.APP_ERROR_PREFIX + "_loginno_4", Constantori.getFromSharedPreference(Constantori.KEY_USERNEM));

                            try {
                                JSONArray json = new JSONArray();
                                JSONObject json_ = new JSONObject();
                                json_.put(Constantori.DATA_LOCFETCH, Constantori.data_LOCFETCH);
                                json.put(json_);
                                new NetPost(context, "loginLoc_PostJSON", json, getResources().getString(R.string.signin_locations), "", "", Loginno.this).execute(new String[]{URL_LINK});
                            }catch(Exception e){

                            }

                        } else if (userStatus.equals(Constantori.USERINACTIVE)) {

                            Snackbar.make(parent_view, getResources().getString(R.string.signin_userinactive), Snackbar.LENGTH_SHORT).show();

                        } else if (userStatus.equals(Constantori.USERSUSPENDED)) {

                            Snackbar.make(parent_view, getResources().getString(R.string.signin_usersuspended), Snackbar.LENGTH_SHORT).show();

                        }


                    } catch (Exception xx) {

                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginnoErrorRes", result);
                        Snackbar.make(parent_view, getResources().getString(R.string.signin_dev_error), Snackbar.LENGTH_SHORT).show();
                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginnoError", "exception", xx);

                    }

                }
                break;


            case "loginforgotemail_PostJSON":

                if (result.equals("202")) {
                    Snackbar.make(parent_view, Constantori.ERROR_APPROVAL, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("101")) {
                    Snackbar.make(parent_view, Constantori.ERROR_PASSWORD, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals(null) || result.equals("303")) {
                    Snackbar.make(parent_view, Constantori.ERROR_SERVER_ISSUE, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("Issue")) {
                    Constantori.dlgNoNet(View, context);

                }else {

                    try {

                        JSONArray receivedResponse = new JSONArray(result);

                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginno_code", receivedResponse.toString());

                        String code = receivedResponse.getJSONObject(0).getString(Constantori.KEY_USERCODE);

                        Constantori.setSharedPreference(Constantori.KEY_USERCODE, code);


                    } catch (Exception xx) {

                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginnoErrorCode", result);
                        Snackbar.make(parent_view, getResources().getString(R.string.signin_dev_error), Snackbar.LENGTH_SHORT).show();
                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginnoError", "exception", xx);

                    }

                }
                break;

            case "loginforgotpassword_PostJSON":

                if (result.equals("202")) {
                    Snackbar.make(parent_view, Constantori.ERROR_APPROVAL, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("101")) {
                    Snackbar.make(parent_view, Constantori.ERROR_PASSWORD, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals(null) || result.equals("303")) {
                    Snackbar.make(parent_view, Constantori.ERROR_SERVER_ISSUE, Snackbar.LENGTH_SHORT).show();
                }else if(result.equals("Issue")) {
                    Constantori.dlgNoNet(View, context);

                }else {

                    try {

                        JSONArray receivedResponse = new JSONArray(result);

                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginno_password", receivedResponse.toString());

                        Snackbar.make(parent_view, Constantori.MSG_PASSWORD_CHANGED, Snackbar.LENGTH_LONG).show();

                    } catch (Exception xx) {

                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginnoErrorCode", result);
                        Snackbar.make(parent_view, getResources().getString(R.string.signin_dev_error), Snackbar.LENGTH_SHORT).show();
                        Log.e(Constantori.APP_ERROR_PREFIX + "_loginnoError", "exception", xx);

                    }

                }
                break;

            default:

                break;

        }

    }



    public void doDBstuff(String where){


        switch (where){

            case "Initial":

                addOrgs();

                actOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                            long id) {

                        String org = actOrg.getText().toString().trim();

                        if (org.equals("OTHER")){
                            Tlogorg_other.setVisibility(android.view.View.VISIBLE);
                        }else{
                            edtOrg_other.setText("");
                            Tlogorg_other.setVisibility(android.view.View.GONE);
                        }

                        //populate cons
                        addCons(org);
                    }
                });

                actCons.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                            long id) {
                        String con = actCons.getText().toString().trim();

                        if (con.equals("OTHER")){
                            Tlogcons_other.setVisibility(android.view.View.VISIBLE);
                        }else{
                            edtCons_other.setText("");
                            Tlogcons_other.setVisibility(android.view.View.GONE);
                        }

                    }
                });


                try {

                    userstatus = Constantori.getFromSharedPreference(Constantori.KEY_USERSTATUS);
                    userid = Constantori.getFromSharedPreference(Constantori.KEY_USERID);
                    userTel = Constantori.getFromSharedPreference(Constantori.KEY_USERTEL);
                    userEmail = Constantori.getFromSharedPreference(Constantori.KEY_USEREMAIL);


                    if (userstatus.equals(Constantori.USERACTIVE)) {

                        edtPhone.setText(Constantori.getFromSharedPreference(Constantori.KEY_USERTEL));
                        dbPhone = Constantori.getFromSharedPreference(Constantori.KEY_USERTEL);
                        dbPass = Constantori.getFromSharedPreference(Constantori.KEY_USERPASS);

                        if(Constantori.isConnectedToInternet()) {

                            try {
                                JSONArray json = new JSONArray();
                                JSONObject json_ = new JSONObject();
                                json_.put(Constantori.DATA_LOCFETCH, Constantori.data_LOCFETCH);
                                json.put(json_);
                                new NetPost(context, "loginLoc_PostJSON", json, getResources().getString(R.string.signin_locations), "", "", Loginno.this).execute(new String[]{URL_LINK});
                            } catch (Exception e) {

                            }
                        }

                    }


                }catch (Exception xx){

                    userstatus = Constantori.USERINACTIVE;
                    usertoken = "";

                }

                break;


            default:
                break;
        }

    }



    public void dlg_forgot_password(View v) {
        final Dialog mbott = new Dialog(Loginno.this, android.R.style.Theme_Translucent_NoTitleBar);
        mbott.setContentView(R.layout.mbaind_forgot_password);
        mbott.setCanceledOnTouchOutside(false);
        mbott.setCancelable(false);
        WindowManager.LayoutParams lp = mbott.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        mbott.getWindow().setAttributes(lp);
        mbott.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mbott.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button mbano = (Button) mbott.findViewById(R.id.rback);
        Button mbaok = (Button) mbott.findViewById(R.id.sawa);
        Button mbasubmit = (Button) mbott.findViewById(R.id.submitcode);

        EditText edtUsermail2 = (EditText) mbott.findViewById(R.id.edtUseremail2);
        EditText edtUsercode = (EditText) mbott.findViewById(R.id.edtUsercode);
        EditText edtUserpass = (EditText) mbott.findViewById(R.id.edtUserpass);
        EditText edtUserpassc = (EditText) mbott.findViewById(R.id.edtUserpassconfirm);

        mbasubmit.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (Constantori.isConnectedToInternet()) {

                    String email = edtUsermail2.getText().toString().trim();

                    if (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                        try {
                            JSONArray json = new JSONArray();
                            JSONObject json_ = new JSONObject();
                            json_.put(Constantori.KEY_USEREMAIL, email);
                            json.put(json_);
                            new NetPost(context, "loginforgotemail_PostJSON", json, getResources().getString(R.string.signin_getcode), "", "", Loginno.this).execute(new String[]{URL_LINK});

                            mbasubmit.setText("CODE SENT");
                            mbasubmit.setEnabled(false);
                            edtUsermail2.setEnabled(false);

                        } catch (Exception e) {

                        }

                    } else {
                        Snackbar.make(parent_view, Constantori.ERROR_EMAIL, Snackbar.LENGTH_LONG).show();
                    }

                }else{
                    Constantori.dlgNoNet(View, context);
                }
            }
        });


        mbaok.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v){

                String pass = edtUserpass.getText().toString().trim();
                String passc = edtUserpassc.getText().toString().trim();
                String code = edtUsercode.getText().toString().trim();
                String code_ = Constantori.getFromSharedPreference(Constantori.KEY_USERCODE);
                String mail = edtUsermail2.getText().toString().trim();

                if (Constantori.isConnectedToInternet()) {

                    if(pass.equals(passc)){

                        if(code.equals(code_)){

                            try {
                                JSONArray json = new JSONArray();
                                JSONObject json_ = new JSONObject();
                                json_.put(Constantori.KEY_USEREMAIL, mail);
                                json_.put(Constantori.KEY_USERPASS, pass);
                                json.put(json_);
                                new NetPost(context, "loginforgotpassword_PostJSON", json, getResources().getString(R.string.signin_setpass), "", "", Loginno.this).execute(new String[]{URL_LINK});
                            }catch(Exception e){

                            }

                            mbott.dismiss();

                        }else{
                            Snackbar.make(parent_view, Constantori.ERROR_CODE, Snackbar.LENGTH_LONG).show();
                        }

                    }else{
                        Snackbar.make(parent_view, Constantori.ERROR_PASSWORD_CONF, Snackbar.LENGTH_LONG).show();
                    }

                }else{
                    Constantori.dlgNoNet(View, context);
                }

            }
        });

        mbano.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mbott.dismiss();

            }
        });

        mbott.show();
    }


    private void addOrgs(){

        ArrayList<String> orgs_arr = new ArrayList<String>();

        orgs_arr.add("SELECT");

        List<HashMap<String, String>> allData = db.GetAllData(Constantori.TABLE_LOC, "", "");

        if(allData.size() > 0) {

            for(int i = 0; i < allData.size(); i++) {

                HashMap<String, String> allDetails = allData.get(i);

                orgs_arr.add(allDetails.get(Constantori.KEY_LOCORG));

            }

            orgs_arr.add("OTHER");

            String[] orgs = new String[orgs_arr.size()];

            ArrayAdapter<String> adapterOrgs =
                    new ArrayAdapter<String>(
                            this,
                            R.layout.dropdown_menu_popup_item,
                            orgs);

            actOrg.setAdapter(adapterOrgs);
        }
    }

    private void addCons(String org){

        List<HashMap<String, String>> allData = db.GetAllData(Constantori.TABLE_LOC, Constantori.KEY_LOCORG, org);

        if(allData.size() > 0) {

            HashMap<String, String> allDetails = allData.get(0);
            String strCons = allDetails.get(Constantori.KEY_LOCCON);

            assert strCons != null;
            String[] bareCons = strCons.split(",");
            List<String> listCons = Arrays.asList(bareCons);
            listCons.add(0,"SELECT");
            String[] cons = listCons.toArray(new String[0]);

            ArrayAdapter<String> adapterCons =
                    new ArrayAdapter<String>(
                            this,
                            R.layout.dropdown_menu_popup_item,
                            cons);

            actCons.setAdapter(adapterCons);

        }else{

            String[] cons = new String[]{"SELECT","OTHER"};

            ArrayAdapter<String> adapterCons =
                    new ArrayAdapter<String>(
                            this,
                            R.layout.dropdown_menu_popup_item,
                            cons);

            actCons.setAdapter(adapterCons);

        }
    }


    private void insertOrgConToDB(String org, String con){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", java.util.Locale.getDefault());
        locno = "locno_" + userRefo + "_" + dateFormat.format(new Date());

        try {

            JSONObject submap = new JSONObject();
            submap.put(Constantori.KEY_LOCORG,org);
            submap.put(Constantori.KEY_LOCNO,locno);
            submap.put(Constantori.KEY_LOCCON,con);
            submap.put(Constantori.KEY_LOCCTRY,Constantori.getFromSharedPreference(Constantori.KEY_USERCNTRY));

            JSONArray subArray = new JSONArray();
            subArray.put(submap);

            db.insertDataToTable(Constantori.TABLE_LOC, subArray);

        } catch (JSONException e) {
            Log.e(Constantori.APP_ERROR_PREFIX + "_OrgError", "exception", e);
        }


    }


}
