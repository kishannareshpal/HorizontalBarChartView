package com.kishannareshpal.horizontalbarchartview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int APPS_DETAILID   = 1;
    private static final int MEDIA_DETAILID  = 2;
    private static final int MAIL_DETAILID   = 3;
    private static final int OTHERS_DETAILID = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init the component
        final HorizontalBarChartView hbcv = findViewById(R.id.sdv_details);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add as many details as you'd like...
                hbcv.addData(APPS_DETAILID,  12, getResources().getColor(R.color.red));
                hbcv.addData(MEDIA_DETAILID,  14, getResources().getColor(R.color.yellow));
                hbcv.addData(MAIL_DETAILID,  5, getResources().getColor(R.color.blue));
                hbcv.addData(OTHERS_DETAILID, 42, getResources().getColor(R.color.grey));
                // ...

                // Call .show() to draw the details on the view.
                hbcv.show();
            }
        });

    }
}
