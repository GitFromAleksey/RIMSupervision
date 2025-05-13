package com.example.rimsupervision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PredictActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton buttonHome;
    ImageButton buttonSpark;
    ImageButton buttonGraph;
    ImageButton buttonChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_predict);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonHome = (ImageButton) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(this);
        buttonSpark = (ImageButton) findViewById(R.id.buttonSpark);
        buttonSpark.setOnClickListener(this);
        buttonGraph = (ImageButton) findViewById(R.id.buttonGraph);
        buttonGraph.setOnClickListener(this);
        buttonChart = (ImageButton) findViewById(R.id.buttonChart);
        buttonChart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(buttonHome == v)
        {
            Intent switcher = new Intent(this, MainActivity.class);
            this.startActivity(switcher);
        }
        if(buttonSpark == v)
        {
            Intent switcher = new Intent(this, PeakDateActivity.class);
            this.startActivity(switcher);
        }
        if(buttonGraph == v)
        {
            Intent switcher = new Intent(this, PeakLoadsActivity.class);
            this.startActivity(switcher);
        }
        if(buttonChart == v)
        {
            Intent switcher = new Intent(this, DailyAverageActivity.class);
            this.startActivity(switcher);
        }
    }
}