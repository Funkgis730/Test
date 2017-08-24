package org.piesat.zzh.controller;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.piesat.zzh.model.CustomXWPFDocument;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		WordUtil Wutil=new WordUtil();
		SimpleDateFormat dft=new SimpleDateFormat("yyyy年MM月dd日");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("${name}", "张三");
		param.put("${sex}", "男");
		param.put("${age}", "22");
		param.put("${city}", "南京");
		param.put("${school}", "XX大学");
		param.put("${major}", "地理学");
		param.put("${date}", dft.format(new Date()));
		
		Map<String,Object> pic = new HashMap<String, Object>();
		pic.put("width", 100);
		pic.put("height", 100);
		pic.put("type", "jpg");
		pic.put("content", Wutil.inputStream2ByteArray(new FileInputStream("D:\\java software\\workspace\\WebTest\\img\\people_pic.jpg"), true));
		param.put("${pic}",pic);
		
		
		
		CustomXWPFDocument doc = Wutil.generateWord(param, "D:\\java software\\workspace\\WebTest\\word\\example.docx");
		FileOutputStream fopts = new FileOutputStream("D:\\java software\\workspace\\WebTest\\word\\result.docx");
		doc.write(fopts);
		System.out.println("文档导出完成");
		fopts.close();
	}
}