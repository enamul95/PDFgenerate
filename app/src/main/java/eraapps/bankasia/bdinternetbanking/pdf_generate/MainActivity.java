package eraapps.bankasia.bdinternetbanking.pdf_generate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.util.Log;
import android.widget.Toast;

import com.uttampanchasara.pdfgenerator.CreatePdf;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class MainActivity extends AppCompatActivity {
    PDFViewPager pdfViewPager;
    BasePDFPagerAdapter adapter;
String file_name_path= "";
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!ManifestPermistion.hasPermissions(MainActivity.this, PERMISSIONS)){
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        }

        File file = new File(Environment.getExternalStorageDirectory(), "visacard_receit");
        if (!file.exists()) {
            file.mkdir();
        }

        pdfViewPager = findViewById(R.id.pdfViewPager);


        createPdf();
    }

    public void createPdf(){
        final String fileName= getCurDateTime();
        new CreatePdf(this)
                .setPdfName(fileName)
                .openPrintDialog(false)
                .setContentBaseUrl(null)
                .setPageSize(PrintAttributes.MediaSize.ISO_A4)

                .setContent(String.valueOf(R.layout.report))
                .setFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/visacard_receit")
                .setCallbackListener(new CreatePdf.PdfCallbackListener() {
                    @Override
                    public void onFailure(String s) {

                    }

                    @Override
                    public void onSuccess(String s) {
                      Toast.makeText(getApplicationContext(),"Pdf saveed",Toast.LENGTH_LONG).show();
                        file_name_path ="/visacard_receit/"+fileName+".pdf";
                        Log.e("file_name_path-->",file_name_path);

                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+file_name_path);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                })
                .create();
    }


    public static String getCurDateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }



    protected String getPdfPathOnSDCard() {
        File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+file_name_path);
       // File f = new File(getExternalFilesDir("pdf"), "adobe.pdf");
        return file1.getAbsolutePath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter.close();
    }
}