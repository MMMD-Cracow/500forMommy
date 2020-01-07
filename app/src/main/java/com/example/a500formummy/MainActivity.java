package com.example.a500formummy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int money =0;
    int addValue = 500;
    Button button;
    TextView textView;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    int puppyNumber = 0;

    //variables for newAmountPopUp
    PopupWindow popupWindow;
    EditText editAmount;
    Button okAmount;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.resetAmount:
                money=0;
                sharedPreferences.edit().putInt("money",money).apply();
                textView.setText("You earned: "+money+ " PLN");
                return true;
            case R.id.changeValue:
                changeValuePopUpWindow();
                return true;
            case R.id.information:
                Toast.makeText(this,"Made by MMMD. Program version: 1."+BuildConfig.VERSION_CODE+".0",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.quit:
                finish();
                moveTaskToBack(true);
                return true;
            default:
                return false;

        }

    }

    public void changeValuePopUpWindow()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView  = inflater.inflate(R.layout.ownamount_popup, null);

        //create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        //show the popup window
        //which view you pass in doesn't matter it is only used for the window
        popupWindow.showAtLocation(button, Gravity.CENTER_HORIZONTAL,0,-200);

        editAmount = popupView.findViewById(R.id.editAmount);

        //dismiss the popup window is set inside "setAmount" function
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

    }

    public void setAmount(View view) {
        //for not having empty, or less than 1 or bigger than 100000
        if(!editAmount.getText().toString().equals("")){
            if(Integer.parseInt(editAmount.getText().toString()) < 1){
                addValue=1;
            }else if(Integer.parseInt(editAmount.getText().toString())<=100000){
                addValue = Integer.parseInt(editAmount.getText().toString());
            } else{ addValue=100000;}
        }
        button.setText("EARN "+addValue+ " PLN");
        sharedPreferences.edit().putInt("addValue",addValue).apply();
        popupWindow.dismiss();
    }

    public void add500 (View view){
        money=money+addValue;
        textView.setText("You earned: "+money+ " PLN");
        sharedPreferences.edit().putInt("money",money).apply();
        switch(puppyNumber){
            case 0:
                imageView.setImageResource(R.drawable.puppy2);
                textView.setTextColor(getResources().getColor(android.R.color.holo_purple));
                puppyNumber =1;
                break;
            case 1:
                imageView.setImageResource(R.drawable.puppy3);
                textView.setTextColor(Color.WHITE);
                puppyNumber =2;
                break;
            case 2:
                imageView.setImageResource(R.drawable.puppy1);
                textView.setTextColor(Color.BLACK);
                puppyNumber =0;
                break;
            default:
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView  = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        sharedPreferences = this.getSharedPreferences("com.example.a500formummy", Context.MODE_PRIVATE);

        money = sharedPreferences.getInt("money",0);
        addValue = sharedPreferences.getInt("addValue",500);
        textView.setText("You earned: "+money+ " PLN");
        button.setText("EARN "+addValue+ " PLN");

    }
}
