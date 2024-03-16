package com.example.holiapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.RecordViewHolder> {

	private List<Record> recordList;
	private OnItemClickListener mListener, dListener;
	private OnEditButtonClickListener mEditListener;
	private OnDeleteButtonClickListener mDeleteListener;

	// Interface for item click
	public interface OnItemClickListener {
		void onItemClick(int position);
	}

	// Interface for edit button click
	public interface OnEditButtonClickListener {
		void onEditButtonClick(int position);
	}

	public interface OnDeleteButtonClickListener {
		void onDeleteButtonClick(int position);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mListener = listener;
		dListener = listener;
	}

	public void setOnEditButtonClickListener(OnEditButtonClickListener listener) {
		mEditListener = listener;
	}

	public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
		mDeleteListener = listener;
	}

	public MyCustomAdapter(List<Record> recordList) {
		this.recordList = recordList;
	}

	@NonNull
	@Override
	public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.record_layout, parent, false);
		return new RecordViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull RecordViewHolder holder, @SuppressLint("RecyclerView") int position) {
		Record record = recordList.get(position);
		holder.fullName.setText(record.getFullName());
		holder.amount.setText("₹ " + record.getAmount());

		// Implement OnClickListener for whole card
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemClick(position);
				}
			}
		});

		// Implement OnClickListener for the edit button
		holder.editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mEditListener != null) {
					mEditListener.onEditButtonClick(position);
				}
			}
		});

		holder.deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDeleteListener != null){
					mDeleteListener.onDeleteButtonClick(position);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return recordList.size();
	}

	public static class RecordViewHolder extends RecyclerView.ViewHolder {
		TextView fullName;
		TextView amount;
		ImageButton editButton, deleteButton;

		public RecordViewHolder(@NonNull View itemView) {
			super(itemView);
			fullName = itemView.findViewById(R.id.fName);
			amount = itemView.findViewById(R.id.amount);
			editButton = itemView.findViewById(R.id.editRecord); // Assuming the id of the ImageButton is editButton
			deleteButton = itemView.findViewById(R.id.deleteRecord);
		}
	}
}
