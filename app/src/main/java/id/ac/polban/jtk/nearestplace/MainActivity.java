package id.ac.polban.jtk.nearestplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    private Button btnNearest;
    private EditText limitItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnNearest = findViewById(R.id.btn_nearestplace);
        this.limitItem = findViewById(R.id.limit_item);

        this.btnNearest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, NearestPlace.class);

                intent.putExtra("limit", limitItem.getText());

                startActivity(intent);
            }

        });
    }
}