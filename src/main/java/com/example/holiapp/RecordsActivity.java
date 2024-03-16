package com.example.holiapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
	RecyclerView recyclerView;
	TextView showAmounts;
	ImageButton edit, delete;
	DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_records);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		recyclerView = findViewById(R.id.recyclerView);
		showAmounts = findViewById(R.id.totalAmount);

		//view helps us to get id from another xml file
		View  view = new View(getApplicationContext());//View viewName = new View(contextName)
		edit = view.findViewById(R.id.editRecord);
		delete = view.findViewById(R.id.deleteRecord);

		databaseHelper = DatabaseHelper.getDB(this);

		//get all data in form of array list from the room database
		ArrayList<Record> recordArrayList = (ArrayList<Record>)databaseHelper.recordDAO().getAllRecord();

		//get all values of amount column into arraylist from room database
		ArrayList<String> amountRecordArrayList = (ArrayList<String>)databaseHelper.recordDAO().getAmountOnly();


		int total = sumOfAmounts(amountRecordArrayList);
		showAmounts.setText("₹ " + total);

		MyCustomAdapter adapter = new MyCustomAdapter(recordArrayList);

		recyclerView.setAdapter(adapter);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		//delete button clicked into recycler view
		adapter.setOnDeleteButtonClickListener(new MyCustomAdapter.OnDeleteButtonClickListener() {
			@Override
			public void onDeleteButtonClick(int position) {
				Record clickedRecord = recordArrayList.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);
				builder.setTitle("Delete Record");
				builder.setMessage("Are you want to delete this record?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteRecord(clickedRecord);
						Toast.makeText(RecordsActivity.this, "Record deleted successfully...", Toast.LENGTH_SHORT).show();
						Intent intent1  = new Intent(RecordsActivity.this, MainActivity.class);
						startActivity(intent1);
						finish();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@SuppressLint("UnsafeIntentLaunch")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		
		//to edit the record into room database
		adapter.setOnEditButtonClickListener(new MyCustomAdapter.OnEditButtonClickListener() {
			@Override
			public void onEditButtonClick(int position) {
				Record clickedRecord = recordArrayList.get(position);
				showUpdateDialog(clickedRecord.getId());
			}
		});

	}

	private void deleteRecord(Record clickedRecord)
	{
		databaseHelper.recordDAO().deleteRecord(clickedRecord);
	}

	protected static int sumOfAmounts(ArrayList<String> arrayList)
	{
		int sum = 0;
		for(String str : arrayList){
			if (!str.isEmpty()) {
				try {
					sum += Integer.parseInt(str);
				} catch (NumberFormatException e) {
					// Handle invalid numeric strings
					e.printStackTrace(); // Print stack trace for debugging
				}
			}
		}
		return sum;
	}


	private void editData(Record clickedRecord)
	{
		databaseHelper.recordDAO().updateRecord(clickedRecord);
	}


	private void editData(int id, String name, String amount)
	{
		databaseHelper.recordDAO().updateRecord(id, name, amount);
	}

	private void editData(int id, String amount)
	{
		databaseHelper.recordDAO().updateRecord(id, amount);
	}

	private void showUpdateDialog(int pos)
	{
		Dialog dialog = new Dialog(RecordsActivity.this);
		dialog.setContentView(R.layout.updated_record_layout);
		dialog.setCancelable(false);

		EditText updatedName = dialog.findViewById(R.id.updatedName);
		EditText updateAmount = dialog.findViewById(R.id.updatedAmount);
		Button update = dialog.findViewById(R.id.update);
		Button noUpdate = dialog.findViewById(R.id.noUpdate);

		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String updatedNameStr = updatedName.getText().toString().trim();
				String updatedAmountStr = updateAmount.getText().toString().trim();

				if (!updatedNameStr.isEmpty())
				{
					if (!updatedAmountStr.isEmpty())
					{
							editData(pos, updatedNameStr, updatedAmountStr);
							Toast.makeText(RecordsActivity.this, "Record update successfully...", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(RecordsActivity.this, "Amount Required...", Toast.LENGTH_SHORT).show();
					}
				}
				else 
				{
					editData(pos, updatedAmountStr);
					Toast.makeText(RecordsActivity.this, "Amount successfully updated...", Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent(RecordsActivity.this, MainActivity.class);
				startActivity(intent);
				dialog.dismiss();
			}
		});

		noUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}