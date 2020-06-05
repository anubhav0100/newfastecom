package com.edevelopers.fastecom;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.edevelopers.fastecom.classes.WifiAddresses;
import com.edevelopers.fastecom.models.Doctors_Team;
import com.edevelopers.fastecom.models.Team;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anubhav Singh on 3/18/2020.
 */

public class sgen {

    /*************************************** Tracking interval variable*****************************************/

    public static String ATOKEN = "", CTOKEN = "", Subscribe = "ttt", unq_type = "",mq5 = "",Menu_T1 = "",senderpage = "",viewId="",backview = "";
    public static ArrayList<Doctors_Team> B_Bitmap;
    private static final String TAG = "file";
    public static android.content.Context Context;
    public static String  squery = "";
    public static String muname = "";
    public static String textseprator = "!~!~!", sptext2 = "#~#~#";
    public static WifiAddresses au;
    public static String mypreferencegroup = "mypref";
    public static DbHelper dbh;
    public static SQLiteDatabase db;
    public static String actionbartext = "";
    public static void alertbox(android.content.Context cnt, String title, String mymessage) {
        new AlertDialog.Builder(cnt)
                .setMessage(mymessage)
                .setTitle(title)
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .show();
    }

    public static String deDup(String s) {
        return new LinkedHashSet<String>(Arrays.asList(s.split("-"))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", "-");
    }

    public static Boolean appendFile(String fname, String fcontent) {
        try {
            String fpath = Environment.getExternalStorageDirectory() + File.separator + fname + ".txt";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(fcontent);
            bw.close();
            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            File file;
            FileOutputStream outputStream;
            try {
                // file = File.createTempFile("MyCache", null, getCacheDir());
                file = new File(Context.getCacheDir(), fname + ".txt");

                outputStream = new FileOutputStream(file);
                outputStream.write(fcontent.getBytes());
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public static Boolean writefile(String fname, String fcontent) {
        try {
            String fpath = Environment.getExternalStorageDirectory() + File.separator + fname + ".txt";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();
            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            File file;
            FileOutputStream outputStream;
            try {
                // file = File.createTempFile("MyCache", null, getCacheDir());
                file = new File(Context.getCacheDir(), fname + ".txt");

                outputStream = new FileOutputStream(file);
                outputStream.write(fcontent.getBytes());
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public static String readfile(String fname) {

        BufferedReader br = null;
        String response = null;

        try {
            //String fpath = "/sdcard/" + fname + ".txt";
            String fpath = Environment.getExternalStorageDirectory() + File.separator + fname + ".txt";

            File file = new File(fpath);
            if (!file.exists()) {
                if (!sgen.writefile(fname, "")) {
                    return "SD card not Readable";
                }
            }


            StringBuffer output = new StringBuffer();

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            response = output.toString();
        } catch (Exception e) {
            BufferedReader input = null;
            File file = null;
            try {
                file = new File(Context.getCacheDir(), fname + ".txt"); // Pass getFilesDir() and "MyFile" to read file

                input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = input.readLine()) != null) {
                    buffer.append(line);
                }

                Log.d(TAG, buffer.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
                response = e1.toString();
                response = "";

            }
        }


        return response;

    }

    public static String gettodaydate() {
        Date tdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String cdate = sdf.format(tdate).toString().trim();
        return cdate;

    }

    public static String gettodaydate_time() {
        Date tdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cdate = sdf.format(tdate).toString().trim();
        return cdate;

    }

    public static String getcurrentTime() {
        Date tdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String cdate = sdf.format(tdate).toString().trim();
        return cdate;

    }


    public static Double make_double(String Val) {
        Double result = 0.00;
        try {
            if (Val.contains(",")) {
                Val = Val.replace(",", "");
            }

            result = Double.valueOf(Val);
        } catch (Exception e) {
            result = 0.00;
        }

        return result;
    }

    public static Double round_off(double d, Integer places) {
        Double result = 0.00;
        double newKB = Math.round(d * 100.0) / 100.0;
        String format = Lpad("", places, "#");
        format = "###." + format;
        DecimalFormat df = new DecimalFormat(format);
        result = Double.valueOf(df.format(d));
        return result;
    }

    public static Integer make_Int(String Val) {
        Integer result = 0;
        try {
            if (Val.contains(",")) {
                Val = Val.replace(",", "");
            }
            result = Integer.valueOf(Val);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public static Float make_Float(String Val) {
        Float result = Float.valueOf(0);
        try {
            if (Val.contains(",")) {
                Val = Val.replace(",", "");
            }
            result = Float.valueOf(Val);
        } catch (Exception e) {
            result = Float.valueOf(0);
        }
        return result;
    }

    public static String getmac(android.content.Context context) {
        String androidID = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidID;
    }

    public static boolean ping1(android.content.Context c, String IP) {
        boolean ping;
        ping = false;
        au = new WifiAddresses(c);
        if (au.pingCmd(IP)) ping = true;
        else ping = false;
        return ping;
    }

    public static void Save_Login() {
    }

    public static void Clean_Login() {
        SharedPreferences preferences = Context.getSharedPreferences(mypreferencegroup, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static ArrayList<Team> Teamsearch(ArrayList<Team> mainfeed, String srchtxt) {
        ArrayList<Team> Filtered_Names = new ArrayList<>();
        Team filterableString;
        for (int i = 0; i < mainfeed.size(); i++) {
            filterableString = mainfeed.get(i);
            String temp = srchtxt.toLowerCase().trim();

            if ("AAR AAR TECHHNOPLAST PVT. LTD.".trim().toLowerCase().contains("chh"))

            {
                filterableString = mainfeed.get(i);
                temp = srchtxt.toLowerCase().trim();

            }

            if (filterableString.getcol1().toString().trim().toLowerCase().contains(temp) ||
                    filterableString.getcol2().toString().trim().toLowerCase().contains(temp) ||
                    filterableString.getcol3().toString().trim().toLowerCase().contains(temp) ||
                    filterableString.getcol4().toString().trim().toLowerCase().contains(temp) ||
                    filterableString.getcol5().toString().trim().toLowerCase().contains(temp)) {
                Filtered_Names.add(filterableString);
            }
        }
        return Filtered_Names;
    }

    public static int check_wifi_signal() {
        int result = 0;
        //max level is 5
        WifiManager wifiManager = (WifiManager) Context.getSystemService(Context.WIFI_SERVICE);

// Level of a Scan Result
        @SuppressLint("MissingPermission") List<ScanResult> wifiList = wifiManager.getScanResults();
        for (ScanResult scanResult : wifiList) {
            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            result = level;
        }

// Level of current connection
        @SuppressLint("MissingPermission") int rssi = wifiManager.getConnectionInfo().getRssi();
        int level = WifiManager.calculateSignalLevel(rssi, 5);
        result = level;
        return result;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int check_mobile_signal() {
        int result = 0;
        TelephonyManager telephonyManager = (TelephonyManager) Context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
        CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
        result = cellSignalStrengthGsm.getDbm();
        return result;
    }

    public long Daybetween(String date1, String date2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null, Date2 = null;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static void exc_sqlite(android.content.Context usercontext, String query) {
        dbh = new DbHelper(usercontext);
        db = dbh.getWritableDatabase();
        db.execSQL(query);
    }

    public static void create_tables( android.content.Context context) {
        sgen.exc_sqlite(context, "CREATE TABLE if not exists Head(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,IMG TEXT,DATE TEXT,DESCRIPTION TEXT)");
    }

    public static void savedata(android.content.Context context){
        sgen.exc_sqlite(context,"INSERT INTO Head(TITLE,IMG,DATE,DESCRIPTION)VALUES('food main content'," +
               "'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'," +
                "'"+ getcurrentTime()+"'," +
                "'MGM confirmed the breach but stated they could not say exactly how many people may have been affected because the information could be duplicated.\"Last summer, we discovered unauthorized access to a cloud server that contained a limited amount of information for certain previous guests of MGM Resorts. We are confident that no financial, payment card or password data was involved in this matter,\" a spokesperson for MGM Resorts said.')");
        sgen.exc_sqlite(context,"INSERT INTO Head(TITLE,IMG,DATE,DESCRIPTION)VALUES('food main content'," +
                "'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',"+
                "'"+ getcurrentTime()+"'," +
                "'MGM confirmed the breach but stated they could not say exactly how many people may have been affected because the information could be duplicated.\"Last summer, we discovered unauthorized access to a cloud server that contained a limited amount of information for certain previous guests of MGM Resorts. We are confident that no financial, payment card or password data was involved in this matter,\" a spokesperson for MGM Resorts said.')");
        sgen.exc_sqlite(context,"INSERT INTO Head(TITLE,IMG,DATE,DESCRIPTION)VALUES('food main content'," +
                "'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',"+
                "'"+ getcurrentTime()+"'," +
                "'MGM confirmed the breach but stated they could not say exactly how many people may have been affected because the information could be duplicated.\"Last summer, we discovered unauthorized access to a cloud server that contained a limited amount of information for certain previous guests of MGM Resorts. We are confident that no financial, payment card or password data was involved in this matter,\" a spokesperson for MGM Resorts said.')");
        sgen.exc_sqlite(context,"INSERT INTO Head(TITLE,IMG,DATE,DESCRIPTION)VALUES('food main content'," +
                "'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',"+
                "'"+ getcurrentTime()+"'," +
                "'MGM confirmed the breach but stated they could not say exactly how many people may have been affected because the information could be duplicated.\"Last summer, we discovered unauthorized access to a cloud server that contained a limited amount of information for certain previous guests of MGM Resorts. We are confident that no financial, payment card or password data was involved in this matter,\" a spokesperson for MGM Resorts said.')");
    }

    public static void drop_tables() {
        sgen.exc_sqlite(Context, "drop table if exists Head");
    }
    public  static boolean isInternetConnection(android.content.Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        return connected;
    }

    public static String seek_iname_sqlite(String query) {

        dbh = new DbHelper(Context);
        db = dbh.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String result = "0";
        try {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(0);
                }
                while (cursor.moveToNext());
            }
            if (result.equals(null)) result = "0";
            if (result.equals("")) result = "0";
        } catch (Exception e) {
            result = "0";
        }
        return result;

    }

    public static String zerotodash(String txt) {

        String result = "";
        if (txt.trim().equals("0")) result = "";
        else result = txt.trim();
        return result;

    }

    public static byte[] seek_iname_sqlite_image(String query) {

        dbh = new DbHelper(Context);
        db = dbh.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        byte[] result = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getBlob(0);
                }
                while (cursor.moveToNext());
            }
            if (result.equals(null)) result = null;
        } catch (Exception e) {
            result = null;
        }
        return result;

    }

    public static String Lpad(String Text, Integer length, String padwith) {
        String Result = "";

        StringBuilder sb = new StringBuilder();

        for (int toPrepend = length - Text.length(); toPrepend > 0; toPrepend--) {
            sb.append(padwith);
        }

        sb.append(Text);
        Result = sb.toString();

        return Result;
    }


    public static long getMaxId(android.content.Context usercontext, String query) {
        dbh = new DbHelper(usercontext);
        db = dbh.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            }
            while (cursor.moveToNext());
        }
        return id;
    }

    public static String NextNumber(android.content.Context usercontext, String query, Integer length) {
        dbh = new DbHelper(usercontext);
        db = dbh.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            }
            while (cursor.moveToNext());
        }

        if (id == 0) {
            id = 1;
        } else {
            id = id + 1;
        }
        return Lpad(String.valueOf(id), length, "0");
    }

    public static Cursor getdata_all(android.content.Context usercontext, String squery) {
        dbh = new DbHelper(usercontext);
        db = dbh.getReadableDatabase();
        Cursor mCursor = db.rawQuery(squery, null);
        return mCursor;

    }

    public static ArrayList<Team> getdata_fromsql(android.content.Context usercontext, String squery) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        String col1 = "";
        ArrayList<Team> feedList = new ArrayList<Team>();

        try {
            dbh = new DbHelper(usercontext);
            db = dbh.getReadableDatabase();
            Cursor mCursor = db.rawQuery(squery, null);

            if (mCursor.moveToFirst()) {

                do {

                    try {
                        Date date = null;
                        try {
                            date = formatter.parse(mCursor.getString(0));
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        col1 = formatter1.format(date);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        col1 = "";
                    }

                    if (col1.equals("")) col1 = mCursor.getString(0);
                    Team t = new Team(col1, mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getString(4), false);
                    feedList.add(t);
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        } catch (Exception e) {

            e.printStackTrace();
        }


        return feedList;

    }

    public static ArrayList<Doctors_Team> getdata_fromsql_doctors(android.content.Context usercontext, String squery) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        String col1 = "";
        byte[] img = null;
        ArrayList<Doctors_Team> feedList = new ArrayList<Doctors_Team>();

        dbh = new DbHelper(usercontext);
        db = dbh.getReadableDatabase();
        Cursor mCursor = db.rawQuery(squery, null);

        byte[] bt = sgen.seek_iname_sqlite_image("select image from tbprofiles");
        if (mCursor.moveToFirst()) {

            do {

                try {
                    Date date = null;
                    try {
                        date = formatter.parse(mCursor.getString(0));
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    col1 = formatter1.format(date);
                } catch (Exception e) {
                    //e.printStackTrace();
                    col1 = "";
                }
                try {
                    img = mCursor.getBlob(5);

                } catch (Exception e) {
                    String str = "Compname";
                    img = str.getBytes();
                }

                if (col1.equals("")) col1 = mCursor.getString(0);
                Doctors_Team t = new Doctors_Team(col1, mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getString(4), img);
                feedList.add(t);
            } while (mCursor.moveToNext());
        }

        mCursor.close();
        return feedList;

    }

    public static String exporttoexcel(String filename, String Squery)

    {
        dbh = new DbHelper(Context);
        db = dbh.getReadableDatabase();
        Cursor c = null;
        String res = "";

        try {
            c = db.rawQuery(squery, null);
            int rowcount = 0;
            int colcount = 0;
            String fpath = Environment.getExternalStorageDirectory() + File.separator + filename + ".csv";
            File saveFile = new File(fpath);
            FileWriter fw = new FileWriter(saveFile);

            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();

                for (int i = 0; i < colcount; i++) {
                    if (i != colcount - 1) {

                        bw.write(c.getColumnName(i) + ",");

                    } else {

                        bw.write(c.getColumnName(i));

                    }
                }
                bw.newLine();

                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);

                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ",");
                        else
                            bw.write(c.getString(j));
                    }
                    bw.newLine();
                }
                bw.flush();
                res = "Exported Successfully.";
            }
        } catch (Exception ex) {
            if (db.isOpen()) {
                db.close();
                res = ex.getMessage().toString();
            }

        } finally {

        }
        return res;
    }

    public static boolean isvaliddate(String dateToValdate) {

        SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YYYY");
        //To make strict date format validation
        formatter.setLenient(false);
        Date parsedDate = null;
        Boolean result = true;
        try {
            parsedDate = formatter.parse(dateToValdate);
            //System.out.println("++validated DATE TIME ++"+formatter.format(parsedDate));
        } catch (ParseException e) {
            result = false;
            //Handle exception
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static Bitmap create_thumbnail(Bitmap image) {
        int h = 480; // height in pixels
        int w = 300;
        h = 360;
        w = 225;// width in pixels
        //Bitmap scaled = Bitmap.createScaledBitmap(image, h, w, true);
        //return scaled;
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();

        m.setScale((float) w / image.getWidth(), (float) h / image.getHeight());

        canvas.drawBitmap(image, m, new Paint());

        return output;
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
//        maxHeight = 270;
//        maxWidth = 270;
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
    public static Bitmap resize270(Bitmap image) {
      int  maxHeight = 270;
      int  maxWidth = 270;
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public static Bitmap resize960(Bitmap image) {
       int maxHeight = 1480;
       int maxWidth = 1080;
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static boolean isLegalDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }

    public static String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        return deviceName;
    }


    public static String sendMail_mobile(android.content.Context cntx, String Location, String fname, String to, String cc, String body) {

        String result = "";
        try {


            String mybody = "<style type=\"text/css\"> .Table  {     display: table;\n" +
                    "    }\n" +
                    "    .Title\n" +
                    "    {\n" +
                    "        display: table-caption;\n" +
                    "        text-align: center;\n" +
                    "        font-weight: bold;\n" +
                    "        font-size: larger;\n" +
                    "    }\n" +
                    "    .Heading\n" +
                    "    {\n" +
                    "        display: table-row;\n" +
                    "        font-weight: bold;\n" +
                    "        text-align: center;\n" +
                    "    }\n" +
                    "    .Row\n" +
                    "    {\n" +
                    "        display: table-row;\n" +
                    "    }\n" +
                    "    .Cell\n" +
                    "    {\n" +
                    "        display: table-cell;\n" +
                    "        border: solid;\n" +
                    "        border-width: thin;\n" +
                    "        padding-left: 5px;\n" +
                    "        padding-right: 5px;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "<div class=\"Table\">\n" +
                    "    <div class=\"Title\">\n" +
                    "        <p>This is a Table</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"Heading\">\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Heading 1</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Heading 2</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Heading 3</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"Row\">\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Row 1 Column 1</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Row 1 Column 2</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Row 1 Column 3</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"Row\">\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Row 2 Column 1</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Row 2 Column 2</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"Cell\">\n" +
                    "            <p>Row 2 Column 3</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>";

            String filename = fname;
            File filelocation = new File(Location);
            Uri path = Uri.fromFile(filelocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to1[] = {""};
            String cc1[] = {""};
            try {

                to1 = to.replace(",", ";").split(";");
            } catch (Exception e) {

            }
            try {

                cc1 = cc.replace(",", ";").split(";");
            } catch (Exception e) {

            }

            emailIntent.setType("text/html");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to1);
            emailIntent.putExtra(Intent.EXTRA_CC, cc1);
            emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
// the attachment
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Barcode Scanned File");
            cntx.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            result = "OK";
        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }


   /* public static String sqltocsv(String pquery, String Filename) {
        ExportDatabaseToCSV toCSV = new ExportDatabaseToCSV(Context);
        mq = toCSV.exportDataBaseIntoCSV(pquery, Filename);
        return mq;
    }*/

    public static String getUsername(android.content.Context cntx) {
        AccountManager manager = AccountManager.get(cntx);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type
            // values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

    public static void ActionBarTitleGravity(android.content.Context cntx, ActionBar bar, String title, int color_b0_w1) {
        // TODO Auto-generated method stub

        //this function Support ActionBarActivity Only
        ActionBar actionbar = bar;
        sgen.actionbartext = title;

        TextView textview = new TextView(cntx);

        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        textview.setLayoutParams(layoutparams);
        textview.setText(title);
        textview.setTextColor(Color.BLACK);
        if(color_b0_w1==1) textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(18);
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setCustomView(textview);
        actionbar.setTitle(title);
    }


    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String make_Inr(double value) {

        DecimalFormat df = new DecimalFormat("##,##,##,##,##,##,##0.00");
        return df.format(value);
    }


    public static String remove_decimal(String s) {

        String res = "0";
        try {
            res = s.split("\\.")[0];
        } catch (Exception e) {

            e.printStackTrace();

        }
        return res;
    }

    public static String get_firm(String co_cd) {
        String fname = "";
        //Con2OLE(co_cd);
        switch (co_cd.trim()) {
            case "NIRM":
            case "PRAG":
                fname = "NIRMATI/PRAGATI";
                break;
            default:
                fname = "XXXX";
                break;
        }

        return fname;
    }

    public static void make_query_heads() {


    }

    public static String getFileName(android.content.Context cotext, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = cotext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static Spannable setColorForPath(String text, String[] paths, int color) {

        Spannable spannable = new SpannableString(text);
        for (int i = 0; i < paths.length; i++) {
            int indexOfPath = spannable.toString().indexOf(paths[i]);
            if (indexOfPath == -1) {
                return spannable;
            }
            spannable.setSpan(new ForegroundColorSpan(color), indexOfPath,
                    indexOfPath + paths[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        }
        return spannable;

    }

    public static SpannableStringBuilder setColorofText(String[] texts, int[] colors ) {

        SpannableStringBuilder builder = new SpannableStringBuilder();

        for (int i = 0; i < texts.length; i++) {
            String red = texts[i].toString();
            SpannableString redSpannable = new SpannableString(red);
            redSpannable.setSpan(new ForegroundColorSpan(colors[i]), 0, red.length(), 0);
            builder.append(redSpannable);
        }
        return builder;

    }

    public  static String ImageToBase64(String path) {

        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }
    public  static String ImageToBase64(Bitmap img) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap Base64ToImage(String encodedImage)
    {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public static <T> ArrayList<T> removeDuplicatesTeam(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }


}
