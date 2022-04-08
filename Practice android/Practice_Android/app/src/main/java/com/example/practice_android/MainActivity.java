package com.example.practice_android;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText productInput, quantityInput;
    Button addBtn, viewBtn;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();
    }

    private void initListener() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = productInput.getText().toString();
                int productQuantity = Integer.parseInt(quantityInput.getText().toString());

                Product product = new Product();
                product.setName(productName);
                product.setQuantity(productQuantity);
                db.productDao().insertProduct(product);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListView.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void initView() {
        productInput = (EditText) findViewById(R.id.name_input);
        quantityInput = (EditText) findViewById(R.id.quantity_input);
        addBtn = (Button) findViewById(R.id.add_product);
        viewBtn = (Button) findViewById(R.id.view_product);
        db = AppDatabase.getAppDatabase(this);
    }
}