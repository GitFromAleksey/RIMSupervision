package com.example.rimsupervision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    ImageButton btnGraph;
    ImageButton btnSpark;
    ImageButton btnTable;
    ImageButton btnChart;

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
        btnGraph = (ImageButton) findViewById(R.id.buttonGraph);
        btnGraph.setOnClickListener(this);
        btnSpark = (ImageButton) findViewById(R.id.buttonSpark);
        btnSpark.setOnClickListener(this);
        btnTable = (ImageButton) findViewById(R.id.buttonTable);
        btnTable.setOnClickListener(this);
        btnChart = (ImageButton) findViewById(R.id.buttonChart);
        btnChart.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View v)
    {
        if(btnGraph == v)
        {
            Intent switcher = new Intent(MainActivity.this, PeakLoadsActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(btnSpark == v)
        {
            Intent switcher = new Intent(MainActivity.this, PeakDateActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(btnTable == v)
        {
            Intent switcher = new Intent(MainActivity.this, PredictActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(btnChart == v)
        {
            Intent switcher = new Intent(MainActivity.this, DailyAverageActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else if(emerBtn == v)
        {
            Intent switcher = new Intent(MainActivity.this, BLEActivity.class);
            MainActivity.this.startActivity(switcher);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "unknown", Toast.LENGTH_SHORT).show();
        }

    }
}