package com.example.rimsupervision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DailyAverageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton buttonHome;
    ImageButton buttonSpark;
    ImageButton buttonTable;
    ImageButton buttonGraph;

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

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(this);
        buttonSpark = findViewById(R.id.buttonSpark);
        buttonSpark.setOnClickListener(this);
        buttonTable = findViewById(R.id.buttonTable);
        buttonTable.setOnClickListener(this);
        buttonGraph = findViewById(R.id.buttonGraph);
        buttonGraph.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(buttonHome == v)
        {
            Intent switcher = new Intent(DailyAverageActivity.this, MainActivity.class);
            DailyAverageActivity.this.startActivity(switcher);
        }
        else if(buttonSpark == v)
        {
            Intent switcher = new Intent(this, PeakDateActivity.class);
            this.startActivity(switcher);
        }
        else if(buttonTable == v)
        {
            Intent switcher = new Intent(this, PredictActivity.class);
            this.startActivity(switcher);
        }
        else if(buttonGraph == v)
        {
            Intent switcher = new Intent(this, PeakLoadsActivity.class);
            this.startActivity(switcher);
        }
    }
}
