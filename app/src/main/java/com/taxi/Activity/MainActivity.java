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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.taxi.R;
import com.taxi.adapter.ViewPagerAdapter;
import com.taxi.utils.Constants;

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


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //Constants

    //Views
    private ViewPager myViewPager;
    private ViewPagerAdapter adapter;
    private Button circle1, circle2, circle3, circle4, circle5, circle6, circle7;
    private int minWidth;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    private int desiredImageWidth = 300, desiredImageHeight = 300;
    private static final String TAG = MainActivity.class.getName();
    private String selectedImagePath = "";
    private Bitmap rotatedBitmap;
    private ImageView imgProfilePic;
    private boolean isFileImageUploaded;
    private ProgressDialog dialog;
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmail;
    EditText edtPwd;
    EditText edtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//		instance = this;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        minWidth = width / 48;
        Log.d("", "screen width: " + width);
        init();
        setTab();

    }

    public void registerUser(View v) {
        //startScreen(RegistrationActivity.class);
        showRegisterDialog();
    }

    public void loginUser(View v) {
        //startScreen(LoginActivity.class);
        showLoginDialog();
    }

    private void startScreen(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
        finish();
    }

    private void setTab() {
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                btnAction(position);
            }

        });

    }

    private void btnAction(int action) {
        switch (action) {
            case 0:
                setButton(circle1, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 1:
                setButton(circle2, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 2:
                setButton(circle3, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;
            case 3:
                setButton(circle4, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;
            case 4:
                setButton(circle5, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 5:
                setButton(circle6, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 6:
                setButton(circle7, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;


        }
    }

    /**
     * initializing views
     */
    private void init() {
        myViewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);
        initButton();
    }

    private void initButton() {
        circle1 = (Button) findViewById(R.id.btn1);
        circle2 = (Button) findViewById(R.id.btn2);
        circle3 = (Button) findViewById(R.id.btn3);
        circle4 = (Button) findViewById(R.id.btn4);
        circle5 = (Button) findViewById(R.id.btn5);
        circle6 = (Button) findViewById(R.id.btn6);
        circle7 = (Button) findViewById(R.id.btn7);
        setButton(circle1, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
        setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
    }

    private void setButton(Button btn, String text, int h, int w, int selectedCircle) {
        btn.setWidth(w);
        btn.setHeight(h);
        btn.setBackgroundResource(selectedCircle);

        btn.setText(text);
    }

    private void showLoginDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login_layout);
        dialog.show();
    }

    private void showRegisterDialog() {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_profile_pic:
                selectImage();
                break;

            case R.id.app_register_btn:
                if (isFileImageUploaded) {
                    new ToRegisterAsync().execute();
                } else {
//                    serviceToUpdateUserDetails();
                }
                break;
            default:
                break;
        }
    }

    /**
     * This method call will update the user details
     */

    class ToRegisterAsync extends AsyncTask<Void, Integer, String> {

        Map<String, String> params = new HashMap<String, String>();

        public ToRegisterAsync() {

            /*http://wowads.asia/taxidriver/register.php?
            newemail=ashish@dkslakds.com&newfullname=dsfsdsaf
            &newpassword=sdhjlashdh&
            newphonenumber=324234324&
            type=androidoriphone&file=phone.jpg&*/
            params.put("newemail", edtEmail.getText().toString().trim());
            params.put("newfullname", edtFirstName.getText().toString().trim());
            params.put("newpassword", edtPwd.getText().toString().trim());
            params.put("newphonenumber", edtMobile.getText().toString().trim());
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
     * responce after calling update user web service.
     * @param jsonResponse
     */
    public void ResponceAfterRegisterUser(String jsonResponse) {
        Log.v("", "json responce: " + jsonResponse);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonResponse);

            if (jsonObject!=null) {
                String resStr = jsonObject.getString("responseinfo");
                Log.v("", "Responce Str: " + resStr);
                if (resStr.equalsIgnoreCase("Success")) {
//                    UIHelper.showToastMessage(getApplicationContext(), "Succussfully updated");
                    Toast.makeText(MainActivity.this, "Succussfully registered", Toast.LENGTH_LONG).show();
                } else if (resStr.equalsIgnoreCase("invalid input")) {
//                    UIHelper.showToastMessage(ProfileActivity.this,"invalid inputs please check");
                    Toast.makeText(MainActivity.this, "invalid inputs please check", Toast.LENGTH_LONG).show();
                } else if(resStr.equalsIgnoreCase("Email Exists")){
//                    UIHelper.showToastMessage(ProfileActivity.this,"email already exist please check");
                    Toast.makeText(MainActivity.this, "email already exist please check", Toast.LENGTH_LONG).show();
                }
            } else {
//                UIHelper.showToastMessage(getApplicationContext(), "Some thing went wrong at server side");
                Toast.makeText(MainActivity.this, "Some thing went wrong at server side", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
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
            httpConnection = (HttpURLConnection)url.openConnection();
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
            dos.writeBytes("Content-Disposition: form-data; name=\"profile_picture\";filename=\"" + tempFile.getPath() +"\"" + lineEnd);
            dos.writeBytes(lineEnd);
            bytesAvailable = fis.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fis.read(buffer, 0, bufferSize);
            while(bytesRead > 0) {
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
            if (responce== null || responce.equalsIgnoreCase("")) {
                responce = convertStreamToString(httpConnection.getInputStream());
            } else {
                fis.close();
                dos.flush();
                dos.close();
            }
        } catch(IOException e ) {
            e.printStackTrace();
            //			Utils.showToast("IO Exception");
        } catch (Exception e) {
            e.printStackTrace();
            //			Utils.showToast("Exception");
        }finally {
            tempFile.delete();
        }

        return responce;
    }

    /**
     * decoding bitmap from its path
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


    /**
     * creating temp file.
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

    private void selectImage() {

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
                //				savebitmap(selectedImage.getPath(), rotatedBitmap);
                //				setBitmap(rotatedBitmap);
                //				img_view.setImageBitmap(model.getBitmap());
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
                //				savebitmap(selectedImagePath, rotatedBitmap);
                //				model.setBitmap(rotatedBitmap);
                //				img_view.setImageBitmap(model.getBitmap());
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
}