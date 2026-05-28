package com.example.zakatgoldcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etPrice;
    private RadioButton rbKeep;
    private TextView tvTotalValue, tvPayableValue, tvTotalZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding UI items to Java variables
        etWeight = findViewById(R.id.etWeight);
        etPrice = findViewById(R.id.etPrice);
        rbKeep = findViewById(R.id.rbKeep);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvPayableValue = findViewById(R.id.tvPayableValue);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);
        Button btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(v -> calculateZakat());
    }

    private void calculateZakat() {
        String weightStr = etWeight.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        // 1. Input Field Validation / Error Messages (Good Design Practice Requirement)
        if (weightStr.isEmpty()) {
            etWeight.setError("Please enter the gold weight!");
            etWeight.requestFocus();
            return;
        }
        if (priceStr.isEmpty()) {
            etPrice.setError("Please enter the current gold price!");
            etPrice.requestFocus();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double price = Double.parseDouble(priceStr);

        // Setting up uruf threshold values (X) based on category choice
        double X = rbKeep.isChecked() ? 85.0 : 200.0;

        // Core business logic formulas
        double totalValue = weight * price;
        double urufWeight = weight - X;

        double zakatPayableValue = 0.0;
        double totalZakat = 0.0;

        // Ensure calculations map zero boundaries correctly if weight is beneath threshold limit
        if (urufWeight > 0) {
            zakatPayableValue = urufWeight * price;
            totalZakat = zakatPayableValue * 0.025;
        }

        // 2. Output Data Formatting (Appropriate Double Decimal Currencies Requirement)
        tvTotalValue.setText(String.format("Total Gold Value: RM %.2f", totalValue));
        tvPayableValue.setText(String.format("Zakat Payable Value: RM %.2f", zakatPayableValue));
        tvTotalZakat.setText(String.format("Total Zakat: RM %.2f", totalZakat));
    }

    // Creating the top header Action Bar toolbar menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Monitoring menu clicks to handle Sharing tool operations and Activity Page intents
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            // NOTE: Replace the link below with your actual GitHub repository link later!
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Track your gold zakat accurately via my mobile tool. Source Repository: https://github.com/yourusername/zakat-gold-calc");
            startActivity(Intent.createChooser(shareIntent, "Share Application Link via"));
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}