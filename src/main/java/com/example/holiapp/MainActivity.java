package com.example.holiapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.holiapp.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

	MaterialButton addRecord, showRecord;
	TextInputEditText fullName, amount;

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
		addRecord = findViewById(R.id.addRecord);
		fullName = findViewById(R.id.fullName);
		amount = findViewById(R.id.amount);
		showRecord = findViewById(R.id.showRecord);

		DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
		addRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fullNameStr = Objects.requireNonNull(fullName.getText()).toString().trim();
				String amountStr = Objects.requireNonNull(amount.getText()).toString().trim();

				if(fullNameStr.isEmpty() || amountStr.isEmpty()){
					Toast.makeText(MainActivity.this, "Fill all details...", Toast.LENGTH_SHORT).show();
				}
				else {
					Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
					intent.putExtra("FullName", fullNameStr);
					intent.putExtra("Amount",amountStr);

					databaseHelper.recordDAO().addRecord(new Record(fullNameStr, amountStr));

					startActivity(intent);
					finish();
				}
			}
		});

		showRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
				startActivity(intent);
			}
		});
	}
}