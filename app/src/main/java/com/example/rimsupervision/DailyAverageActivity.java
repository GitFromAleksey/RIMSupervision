package com.example.rimsupervision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DailyAverageActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBack;
    Button btn2;
    Button btn3;
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_average);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(this);
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(btnBack == v)
        {
//            Toast.makeText(getApplicationContext(), "btnBack", Toast.LENGTH_SHORT).show();
//            finish();
            Intent switcher = new Intent(DailyAverageActivity.this, MainActivity.class);
            DailyAverageActivity.this.startActivity(switcher);
        }
        else if(btn2 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn2", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(this, PeakLoadsActivity.class);
            this.startActivity(switcher);
        }
        else if(btn3 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn3", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(this, PeakDateActivity.class);
            this.startActivity(switcher);
        }
        else if(btn4 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn4", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(this, PredictActivity.class);
            this.startActivity(switcher);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "unknown", Toast.LENGTH_SHORT).show();
        }
    }
}
