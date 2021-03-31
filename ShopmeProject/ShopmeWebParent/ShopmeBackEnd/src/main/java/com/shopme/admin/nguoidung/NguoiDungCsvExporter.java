package com.shopme.admin.nguoidung;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.TaiKhoan;

public class NguoiDungCsvExporter {
	
	public void export(List<TaiKhoan> listUsers, HttpServletResponse response) throws IOException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName = "nguoidung_" + timestamp + ".csv";
		
		response.setContentType("text/csv");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		
		String [] csvHeader = {"Ma TK", "E-mail", "Ho", "Ten", "Chuc Vu", "Trang Thai"};
		String [] fieldMapping = {"maTK", "email", "ho", "ten", "phanquyen", "trangThai"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (TaiKhoan user : listUsers) {
			csvWriter.write(user, fieldMapping);
		}
		
		csvWriter.close();
	}

}
