package com.demo.mummyding.imagechooser;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{
    private final int PICK_PHOTO = 1;
    private final int CAMERA_PHOTO=2;
    private final int MAX_IMAGE = 3;
    private LinearLayout imageSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSet = (LinearLayout) findViewById(R.id.imageSet);

    }
    public void onPhoto(View v){
        try{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, PICK_PHOTO);
        }catch (Exception e){

        }
    }
    public void onCamera(View v){
        try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_PHOTO);
        }catch (Exception e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_PHOTO:
                if(data == null) return;
                if(imageSet.getChildCount() >= MAX_IMAGE){
                    Toast.makeText(MainActivity.this,"最多只能添加"+MAX_IMAGE+"张图片",Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri= data.getData();
                ImageView imageView= new ImageView(this);
                imageView.setImageURI(uri);
                imageView.setLongClickable(true);
                imageView.setOnLongClickListener(this);
                imageSet.addView(imageView);
                break;
            case CAMERA_PHOTO:
                Bitmap bitmap = data.getParcelableExtra("data");
                if(bitmap == null) return;
                if(imageSet.getChildCount() >= MAX_IMAGE){
                    Toast.makeText(MainActivity.this,"最多只能添加"+MAX_IMAGE+"张图片",Toast.LENGTH_SHORT).show();
                    return;
                }
                imageView= new ImageView(this);
                imageView.setImageBitmap(bitmap);
                imageView.setLongClickable(true);
                imageView.setOnLongClickListener(this);
                imageSet.addView(imageView);
                break;
        }
    }
    @Override
    public boolean onLongClick(final View v) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                imageSet.removeView(v);
                return false;
            }
        });
        popupMenu.show();
        return true;
    }
}
