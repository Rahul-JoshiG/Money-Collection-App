package com.example.holiapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface RecordDAO
{
	@Query("select * from Holi_Amount_2024")
	List<Record> getAllRecord();

	@Query("select Amount from Holi_Amount_2024")
	List<String> getAmountOnly();

	@Insert
	void addRecord(Record record);

	@Query("update HOLI_AMOUNT_2024 set Full_Name = :updatedName, Amount = :amount where id = :Id")
	void updateRecord(int Id, String updatedName, String amount);

	@Query("update HOLI_AMOUNT_2024 set Amount = :amount where id = :Id")
	void updateRecord(int Id,String amount);

	@Update
	void updateRecord(Record record);

	@Delete
	void deleteRecord(Record record);


}
