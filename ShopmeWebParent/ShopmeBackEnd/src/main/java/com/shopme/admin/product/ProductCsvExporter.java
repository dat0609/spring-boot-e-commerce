package com.shopme.admin.product;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.commom.entity.Product;

public class ProductCsvExporter {
	
	public void export(List<Product> lisProducts, HttpServletResponse response) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String timestamp = dateFormat.format(new Date());
		String fileName = "products_" + timestamp + ".csv";
		
		response.setContentType("text/csv");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ID", "Name", "Alias",  "Create Time", "Update Time", "Enable", "Price", "Discount"};
		String[] fieldMapping = {"id", "name", "alias", "createTime", "updateTime", "enable", "price", "discountPercent"};
		
		csvBeanWriter.writeHeader(csvHeader);
		
		for (Product product: lisProducts) {
			csvBeanWriter.write(product, fieldMapping);
		}
		
		csvBeanWriter.close();
	}
}
