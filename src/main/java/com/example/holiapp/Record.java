package com.example.holiapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Holi_Amount_2024")
public class Record {
	@PrimaryKey(autoGenerate = true)
	private int id;

	@ColumnInfo(name = "Full_Name")
	private String fullName;

	@ColumnInfo(name = "Amount")
	private String amount;

	// Public constructor with all fields
	public Record() {
	}

	public Record(int id, String fullName, String amount) {
		this.id = id;
		this.fullName = fullName;
		this.amount = amount;
	}

	// Public constructor without id (for Room to auto-generate)
	@Ignore
	public Record(String fullName, String amount) {
		this.fullName = fullName;
		this.amount = amount;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
