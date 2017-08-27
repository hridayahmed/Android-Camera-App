package com.hridha.hridhacamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button btn;
    int flag = 0;
    Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.photo);
        btn = (Button) findViewById(R.id.takephoto);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0){
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i,99);
                }

                else if (flag == 1){
                    savephotosdcard(b);
                    Toast.makeText(getApplicationContext(),"Photo save in sd card",Toast.LENGTH_SHORT).show();
                    flag = 0;
                    btn.setText("Take Photo");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==99 && resultCode == RESULT_OK && data != null){
            b = (Bitmap) data.getExtras().get("data");

            img.setImageBitmap(b);

            flag = 1;
            btn.setText("Save Photo");
        }
    }

    private void savephotosdcard(Bitmap bitmap){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String pname = simpleDateFormat.format(new Date());

        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root+"Hridha");
        folder.mkdirs();

        File my_file = new File(folder,pname+".png");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(my_file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
