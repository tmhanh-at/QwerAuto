package com.qwer.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtils {

	private String filePath;

	public ExcelUtils(String filePath) {
		this.filePath = filePath;
	}

	public List<List<String>> readAll() {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> values = Arrays.asList(line.split(","));
				records.add(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}

	public String getCellData(int row, int col) {
		List<List<String>> allData = readAll();
		if (row < allData.size() && col < allData.get(row).size()) {
			return allData.get(row).get(col);
		}
		return "";
	}

	public void setCellData(int row, int col, String value) {
		List<List<String>> allData = readAll();

		while (allData.size() <= row) {
			allData.add(new ArrayList<>());
		}

		List<String> rowData = new ArrayList<>(allData.get(row));
		while (rowData.size() <= col) {
			rowData.add("");
		}
		
		rowData.set(col, value);
		allData.set(row, rowData);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			for (List<String> record : allData) {
				bw.write(String.join(",", record));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
