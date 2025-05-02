package com.example.rimsupervision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton emerBtn;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emerBtn = (ImageButton) findViewById(R.id.imageButtonEmergency);
        emerBtn.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.buttonBack);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View v)
    {
        int btn1Id = btn1.getId();

        if(btn1 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn1 == v", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(MainActivity.this, DailyAverageActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(btn2 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn2 == v", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(MainActivity.this, PeakLoadsActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(btn3 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn3 == v", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(MainActivity.this, PeakDateActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(btn4 == v)
        {
//            Toast.makeText(getApplicationContext(), "btn4 == v", Toast.LENGTH_SHORT).show();
            Intent switcher = new Intent(MainActivity.this, PredictActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(emerBtn == v)
        {
            Toast.makeText(getApplicationContext(), "Экстренное оповещение!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "unknown", Toast.LENGTH_SHORT).show();
        }

    }
}