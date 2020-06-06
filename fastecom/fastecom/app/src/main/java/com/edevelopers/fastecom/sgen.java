package com.edevelopers.fastecom;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
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
        sgen.exc_sqlite(context, "CREATE TABLE if not exists Cart(ID INTEGER PRIMARY KEY AUTOINCREMENT,P_ID TEXT,C_ID TEXT,DATE TEXT,DESCRIPTION TEXT)");
        sgen.exc_sqlite(context, "CREATE TABLE if not exists Category(ID INTEGER PRIMARY KEY AUTOINCREMENT,CA_NAME TEXT,DATE TEXT,IMG TEXT)");
        sgen.exc_sqlite(context, "CREATE TABLE if not exists Products(ID INTEGER PRIMARY KEY AUTOINCREMENT,P_NAME TEXT,P_PRICE TEXT,CATEGORY TEXT,DESCRIPTION TEXT,P_PRIORITY TEXT,P_QUANTITY TEXT,DATE TEXT,IMG TEXT)");
    }

    public static void savedata(android.content.Context context){
        try{
            sgen.exc_sqlite(context,"INSERT INTO Category(CA_NAME,DATE,IMG) VALUES" +
                    "('North Indian','06/06/2020','Set IMG')," +
                    "('South Indian','06/06/2020','Set IMG')," +
                    "('Punjabi','06/06/2020','Set IMG')," +
                    "('Pizza','06/06/2020','Set IMG')," +
                    "('Burger','06/06/2020','Set IMG')," +
                    "('Chinese','06/06/2020','Set IMG')," +
                    "('Mithai','06/06/2020','Set IMG')," +
                    "('Biryani','06/06/2020','Set IMG')," +
                    "('Fries','06/06/2020','Set IMG')," +
                    "('Ice Cream','06/06/2020','Set IMG')");
            
            sgen.exc_sqlite(context,"INSERT INTO Products(P_NAME,P_PRICE,CATEGORY,DESCRIPTION,P_PRIORITY,P_QUANTITY,DATE,IMG) VALUES" +
                    "('Cheese Potato Pops','299.00','Fries','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')," +
                    "('','','','DESCRIPTION','1','1','06/06/2020','Set IMG')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void drop_tables(android.content.Context context) {
        sgen.exc_sqlite(context,"drop table if exists Head");
        sgen.exc_sqlite(context,"drop table if exists Cart");
        sgen.exc_sqlite(context,"drop table if exists Category");
        sgen.exc_sqlite(context,"drop table if exists Products");
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
