package com.taxi.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.taxi.R;
import com.taxi.adapter.ViewPagerAdapter;
import com.taxi.application.AppConfig;
import com.taxi.utils.AppUtils;
import com.taxi.utils.Constants;
import com.taxi.utils.CustomLog;
import com.taxi.utils.Preferences;
import com.taxi.utils.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity {

    //Constants

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    private static final String TAG = MainActivity.class.getName();
    public final String TAG_RESPONSEINFO = "responseinfo";
    public final String TAG_PHONE_INFO = "phonenumber";
    public final String TAG_USER_ID = "userid";

    @NonNull
    @Bind(R.id.login_email)
    EditText edtLoginMail;
    @NonNull
    @Bind(R.id.login_pwd)
    EditText edtLoginPwd;
    @NonNull
    @Bind(R.id.app_forgot)
    TextView tvForgot;
    @NonNull
    @Bind(R.id.app_login_btn)
    Button btnLogin;

    @NonNull
    @Bind(R.id.register_fname)
    EditText edtFirstName;
    @NonNull
    @Bind(R.id.register_lname)
    EditText edtLastName;
    @NonNull
    @Bind(R.id.register_email)
    EditText edtMail;
    @NonNull
    @Bind(R.id.register_pwd)
    EditText edtPwd;
    @NonNull
    @Bind(R.id.register_mobile)
    EditText edtMobile;
    @NonNull
    @Bind(R.id.register_profile_pic)
    ImageView imgProfilePic;
    @NonNull
    @Bind(R.id.signup_btn)
    Button btnRegister;

    @NonNull
    @Bind(R.id.app_login_layout)
    RelativeLayout app_login_layout;
    @NonNull
    @Bind(R.id.app_register_layout)
    RelativeLayout app_register_layout;

    @Bind(R.id.login_llayout)
    ScrollView loginScrollViewLayout;

    @Bind(R.id.register_llayout)
    ScrollView registerScrollViewLayout;

    private ProgressDialog dialog;

    private JsonObjectRequest jsonObjectRequest;

    private int desiredImageWidth = 300, desiredImageHeight = 300;
    private String selectedImagePath = "";
    private Bitmap rotatedBitmap;
    private boolean isFileImageUploaded;

    @Override
    protected void onStart() {
        super.onStart();
        AppConfig.setCurentContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.setCurentContext(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        AppConfig.setCurentContext(this);
        init();
    }

    public void registerUser(View v) {
        //startScreen(RegistrationActivity.class);
        //showRegisterDialog();
        loginScrollViewLayout.setVisibility(View.GONE);
        registerScrollViewLayout.setVisibility(View.VISIBLE);
        app_login_layout.setVisibility(View.GONE);
        app_register_layout.setVisibility(View.VISIBLE);
        btnRegister.setBackgroundResource(R.drawable.btn_bg_hover);
        btnLogin.setBackgroundResource(R.drawable.btn_bg);
        Log.v("main", "register");
    }

    public void loginUser(View v) {
        //startScreen(LoginActivity.class);
        //showLoginDialog();
        Log.v("main", "login");
        loginScrollViewLayout.setVisibility(View.VISIBLE);
        registerScrollViewLayout.setVisibility(View.GONE);
        app_login_layout.setVisibility(View.VISIBLE);
        app_register_layout.setVisibility(View.GONE);
        btnRegister.setBackgroundResource(R.drawable.btn_bg);
        btnLogin.setBackgroundResource(R.drawable.btn_bg_hover);
    }

    private void startScreen(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
        finish();
    }

    /**
     * initializing views
     */
    private void init() {
       /* app_login_layout = (RelativeLayout) findViewById(R.id.app_login_layout);
        app_register_layout = (RelativeLayout) findViewById(R.id.app_register_layout);

        btnLogin = (Button) findViewById(R.id.login_btn);
        btnRegister = (Button) findViewById(R.id.signup_btn);

        edtLoginMail = (EditText) findViewById(R.id.login_email);
        edtLoginPwd = (EditText) findViewById(R.id.login_pwd);
        Button btnLogin = (Button) findViewById(R.id.app_login_btn);
        TextView tvForgot = (TextView) findViewById(R.id.app_forgot);

        edtFirstName = (EditText) findViewById(R.id.register_fname);
        edtLastName = (EditText) findViewById(R.id.register_lname);
        edtMail = (EditText) findViewById(R.id.register_email);
        edtPwd = (EditText) findViewById(R.id.register_pwd);
        edtMobile = (EditText) findViewById(R.id.register_mobile);
        Button btnRegister = (Button) findViewById(R.id.app_register_btn);
        btnRegister.setOnClickListener(this);
        imgProfilePic = (ImageView) findViewById(R.id.register_profile_pic);
        imgProfilePic.setOnClickListener(this);

        btnLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);*/
    }

    /*private void showLonDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login_layout);
        Button btnLogin = (Button) dialog.findViewById(R.id.app_login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailStr = getText(edtLoginMail);
                String pwdStr = getText(edtLoginPwd);
                if (!AppUtils.chkStatus(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Check network connection", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mailStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!AppUtils.isValidEmail(mailStr)) {
                    Toast.makeText(MainActivity.this, "Please enter valied email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pwdStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                serviceToLogin(mailStr, pwdStr);
            }
        });
        dialog.show();
    }*/
    @OnClick(R.id.app_forgot)
    public void serviceToForget() {
        //TODO change this
        final String mailStr = "gv@gv.com";

        if (!AppUtils.chkStatus(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Check network connection", Toast.LENGTH_LONG).show();
            return;
        }
        if (mailStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Webservices.BASE_URL + Webservices.FORGET_URL;

        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Requesting...");
        pd.setMessage("Forget password requesting...Wait");
        pd.setCancelable(false);
        pd.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        forgetResponse(response);
                        pd.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pd.hide();
                AppConfig.getInstance().cancelPendingRequests("taxi_forget");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<String, String>();
                params.put("email", mailStr);
                return params;
            }

        };
        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "taxi_forget");
    }

    private void forgetResponse(JSONObject jsonObject) {
        CustomLog.v("TAXI_FORGET", "forget" + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);
            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("success")) {
                Toast.makeText(MainActivity.this, "Sending pwd to mail succeeded", Toast.LENGTH_LONG).show();
            } else if (responseInfo.equalsIgnoreCase("failure")) {
                Toast.makeText(MainActivity.this, "Sending pwd to mail failed", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Something went wrong server side", Toast.LENGTH_LONG).show();
        }
    }

    private void serviceToLogin(String email, String pwd) {
        String url = Webservices.BASE_URL + Webservices.LOGIN_URL +
                "?email=" + email + "&password=" + pwd;


        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Requesting...");
        pd.setMessage("Login to InstantTaxi..Wait");
        pd.setCancelable(false);
        pd.show();


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        pd.dismiss();
                        loginUser(jsonObj);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                pd.dismiss();
                CustomLog.v("", "Volley Error: " + arg0);
                AppConfig.getInstance().cancelPendingRequests("taxi_login");
            }

        });

        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "taxi_login");
    }

    private void loginUser(JSONObject jsonObject) {
        CustomLog.v("TAXI_LOGIN", "login" + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);
            String resPhnum = jsonObject.getString(TAG_PHONE_INFO);
            String userId = jsonObject.getString(TAG_USER_ID);
            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("success")) {
                Preferences.setUserPhNum(getApplicationContext(), resPhnum);
                Preferences.setUserId(getApplicationContext(), userId);
                startScreen(HomeScreenActivity.class);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*private void showRegisterDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register_layout);
        edtFirstName = (EditText) dialog.findViewById(R.id.register_fname);
        edtLastName = (EditText) dialog.findViewById(R.id.register_lname);
        edtEmail = (EditText) dialog.findViewById(R.id.register_email);
        edtPwd = (EditText) dialog.findViewById(R.id.register_pwd);
        edtMobile = (EditText) dialog.findViewById(R.id.register_mobile);
        Button btnRegister = (Button) dialog.findViewById(R.id.app_register_btn);
        btnRegister.setOnClickListener(this);
        imgProfilePic = (ImageView) dialog.findViewById(R.id.register_profile_pic);
        imgProfilePic.setOnClickListener(this);
        dialog.show();
    }*/

    @OnClick(R.id.app_login_btn)
    public void loginUser() {
        String mailStr = getText(edtLoginMail);
        String pwdStr = getText(edtLoginPwd);
        if (!AppUtils.chkStatus(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Check network connection", Toast.LENGTH_LONG).show();
            return;
        }
        if (mailStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (!AppUtils.isValidEmail(mailStr)) {
            Toast.makeText(MainActivity.this, "Please enter valied email", Toast.LENGTH_LONG).show();
            return;
        }
        if (pwdStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        serviceToLogin(mailStr, pwdStr);
    }

    private String getText(@NonNull EditText edt) {
        return edt.getText().toString().trim();
    }

    @OnClick(R.id.app_register_btn)
    public void registerUser() {
        String fName = getText(edtFirstName);
        String lName = getText(edtLastName);
        String mailStr = getText(edtMail);
        String pwdStr = getText(edtPwd);
        String phNumStr = getText(edtMobile);
        if (!AppUtils.chkStatus(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Check network connection", Toast.LENGTH_LONG).show();
            return;
        }
        if (fName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter firstname", Toast.LENGTH_LONG).show();
            return;
        }
        if (lName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter last name", Toast.LENGTH_LONG).show();
            return;
        }
        if (mailStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (!AppUtils.isValidEmail(mailStr)) {
            Toast.makeText(MainActivity.this, "Please enter valied email", Toast.LENGTH_LONG).show();
            return;
        }
        if (pwdStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (phNumStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter Phone number", Toast.LENGTH_LONG).show();
            return;
        }
        if (!isFileImageUploaded) {
            Toast.makeText(MainActivity.this, "Please pic your image", Toast.LENGTH_LONG).show();
            return;
        }
        new ToRegisterAsync().execute();
    }

    /**
     * responce after calling update user web service.
     *
     * @param jsonResponse
     */
    public void ResponceAfterRegisterUser(String jsonResponse) {
        CustomLog.v("TAXI_REGISTER", "register" + jsonResponse);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonResponse);

            if (jsonObject != null) {
                String resStr = jsonObject.getString("responseinfo");
                Log.v("", "Responce Str: " + resStr);
                if (resStr.equalsIgnoreCase("Success")) {
//                    UIHelper.showToastMessage(getApplicationContext(), "Succussfully updated");
                    Toast.makeText(MainActivity.this, "Succussfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                } else if (resStr.equalsIgnoreCase("invalid input")) {
//                    UIHelper.showToastMessage(ProfileActivity.this,"invalid inputs please check");
                    Toast.makeText(MainActivity.this, "invalid inputs please check", Toast.LENGTH_LONG).show();
                } else if (resStr.equalsIgnoreCase("Email Exists")) {
//                    UIHelper.showToastMessage(ProfileActivity.this,"email already exist please check");
                    Toast.makeText(MainActivity.this, "email already exist please check", Toast.LENGTH_LONG).show();
                }
            } else {
//                UIHelper.showToastMessage(getApplicationContext(), "Some thing went wrong at server side");
                Toast.makeText(MainActivity.this, "Some thing went wrong at server side", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public String uploadProfilePic(String sourceUri, String uploadServer, Map<String, String> params) {
        String responce = "";
        int serverResponceCode = 0;
        HttpURLConnection httpConnection = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String hyphens = "--";
        String boundary = "*****";
        int bytesRead;
        int bytesAvailable;
        int bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;

        File sourceFile = new File(sourceUri);
        if (!sourceFile.isFile()) {
            return String.valueOf(0);
        }

        File tempFile = createTempFile(sourceUri);
        try {
            //getting http connection
            FileInputStream fis = new FileInputStream(tempFile);
            URL url = new URL(uploadServer);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("connection", "Keep-Alive");
            httpConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            httpConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            httpConnection.setRequestProperty("profile_picture", sourceUri);

            //uploading user profile pic
            dos = new DataOutputStream(httpConnection.getOutputStream());
            dos.writeBytes(hyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"profile_picture\";filename=\"" + tempFile.getPath() + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            bytesAvailable = fis.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fis.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);

            //uploading user description
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);
                dos.writeBytes(hyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(value);
                dos.writeBytes(lineEnd);
            }
            dos.writeBytes(hyphens + boundary + hyphens + lineEnd);
            serverResponceCode = httpConnection.getResponseCode();

            if (200 != serverResponceCode) {
                //				Utils.showToast("Error at server side in signup..!");
            } else {
                responce = convertStreamToString(httpConnection.getInputStream());
                Log.v("", "Responce Success : " + responce);
            }

            Log.d("", "Responce returned: " + httpConnection.getInputStream());
            if (responce == null || responce.equalsIgnoreCase("")) {
                responce = convertStreamToString(httpConnection.getInputStream());
            } else {
                fis.close();
                dos.flush();
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //			Utils.showToast("IO Exception");
        } catch (Exception e) {
            e.printStackTrace();
            //			Utils.showToast("Exception");
        } finally {
            tempFile.delete();
        }

        return responce;
    }

    /**
     * creating temp file.
     *
     * @param filePath
     * @return
     */
    public File createTempFile(String filePath) {
        File tempFile = null;
        try {
            Log.v("", "TempFile Bitmap Before: " + filePath);
            Bitmap bitmap = decodeSampledBitmapFromPath(filePath, desiredImageWidth, desiredImageHeight);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            tempFile = new File(getExternalCacheDir() + File.separator + new Date().getTime() + ".jpg");
            tempFile.createNewFile();
            FileOutputStream fo = new FileOutputStream(tempFile);
            fo.write(bytes.toByteArray());
            fo.close();
            Log.v("", "Tempfile Bitmap After: " + tempFile.getPath());
            return tempFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * converting the inputstream after signup(username webservice)
     *
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        try {
            Log.v("", "input stream: " + is.available());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("", "Responce convert to stream: " + sb.toString());
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * checking whether sd card exists or not
     *
     * @return
     */
    private boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    @OnClick(R.id.register_profile_pic)
    public void selectImage() {
        final CharSequence[] options = {"Capture From Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Upload Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Capture From Camera")) {
                    if (isSDCARDMounted()) {
                        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        /*File tempfile = new File(Environment.getExternalStorageDirectory()+Utils.PROFILEIMAGEPATH, "sportsprofilepic.jpg");
                        Uri uritemp = Uri.fromFile(tempfile);*/
                        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempFile());
                        photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        photoPickerIntent.putExtra("return-data", true);
                        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE);
                        Log.d(TAG, isSDCARDMounted() + " ; " + REQUEST_IMAGE_CAPTURE);
                    } else {
                        Toast.makeText(MainActivity.this, "You need to insert SD Card", Toast.LENGTH_LONG).show();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, REQUEST_IMAGE_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    /**
     * creating temp file.
     *
     * @return
     */
    private Uri getTempFile() {
        File root = new File(Environment.getExternalStorageDirectory() + Constants.PROFILEIMAGEPATH, "profile_pic");
        if (!root.exists()) {
            root.mkdirs();
        }
        String filename = "" + System.currentTimeMillis();
        File file = new File(root, filename + ".jpeg");
        Uri muri = Uri.fromFile(file);
        selectedImagePath = muri.getPath();
        Log.v("Pic paht from Camera", selectedImagePath);
        return muri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivity: " + requestCode + " : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            //			CalenderModel model = new CalenderModel();
            if (REQUEST_IMAGE_GALLERY == requestCode) {
                Uri selectedImage = data.getData();
                Log.v("", "selected Image: " + selectedImage);
                selectedImagePath = getPath(selectedImage);
                rotatedBitmap = null;
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(0);
                    bitmap = Bitmap.createScaledBitmap(bitmap, desiredImageWidth, desiredImageHeight, true);
                    rotatedBitmap = Bitmap
                            .createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                    Log.v("", "Bitmap After Compression: " + rotatedBitmap.getWidth() + " : " + rotatedBitmap.getHeight() + "\n " + "Before " + selectedImagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_main);
                }

                Log.d("PATH", "PATH when choose from gallery: " + selectedImagePath);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.close();
                imgProfilePic.setImageBitmap(rotatedBitmap);
                isFileImageUploaded = true;
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Log.d(TAG, "onActivity Image capture");
                int rotate = 0;
                Log.d(": ", "PATH when choosed from camera" + selectedImagePath);
                int index = selectedImagePath.lastIndexOf("/");
                String filename = selectedImagePath.substring(index + 1, selectedImagePath.length());
                String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.PROFILEIMAGEPATH + "/profile_pic/";
                File file = new File(filepath, filename);
                ExifInterface exif;
                try {
                    exif = new ExifInterface(file.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //				createTempFile(selectedImagePath);
                FileInputStream fs = null;
                try {
                    fs = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
//                    UIHelper.showToastMessage(ProfileActivity.this, "File not found");
                    Toast.makeText(MainActivity.this, "File not found", Toast.LENGTH_LONG).show();
                }
                BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                bfOptions.inJustDecodeBounds = false;
                bfOptions.inTempStorage = new byte[32 * 1024];
                Bitmap bitmap = null;
                rotatedBitmap = null;
                try {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    bitmap = Bitmap.createScaledBitmap(
                            BitmapFactory.decodeFile(selectedImagePath),
                            desiredImageWidth, desiredImageHeight, false);
                    rotatedBitmap = Bitmap
                            .createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                    Log.v("", "Bitmap After Compression: " + rotatedBitmap.getWidth() + " : " + rotatedBitmap.getHeight() + " : " + selectedImagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_main);
                }
                imgProfilePic.setImageBitmap(rotatedBitmap);
                isFileImageUploaded = true;
            }
        } else {
            Log.v("", "OnActivityResult: " + resultCode);
        }
    }

    // for getting path of image
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * This method call will update the user details
     */

    class ToRegisterAsync extends AsyncTask<Void, Integer, String> {

        Map<String, String> params = new HashMap<String, String>();

        public ToRegisterAsync() {
            params.put("newemail", getText(edtMail));
            params.put("newfullname", getText(edtFirstName));
            params.put("newpassword", getText(edtPwd));
            params.put("newphonenumber", getText(edtMobile));
            params.put("type", "androidoriphone");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Requesting");
            dialog.setMessage("Wait this may take few seconds..!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                if (result != null && !result.equalsIgnoreCase("")) {
                    ResponceAfterRegisterUser(result);
                } else {
//                    UIHelper.showToastMessage(ProfileActivity.this, "Something went wrong at server side..!");
                    Toast.makeText(MainActivity.this, "Something went wrong at server side..!", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected String doInBackground(Void... params0) {
            String url = getString(R.string.common_url) + getString(R.string.register_service);
            Log.v("", "ImagePath: " + selectedImagePath + "::" + url);
            return uploadProfilePic(selectedImagePath, url, params);
        }
    }

    /**
     * decoding bitmap from its path
     *
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }

    /**
     * Calculating inSample size
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}