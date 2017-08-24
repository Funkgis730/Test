package org.piesat.zzh.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.piesat.zzh.controller.WordUtil;
import org.piesat.zzh.model.CustomXWPFDocument;
import org.piesat.zzh.model.MysqlConn;
import org.piesat.zzh.model.MysqlMethod;
import org.piesat.zzh.model.User;

public class ExportWord extends HttpServlet {

	private static final long serialVersionUID = 5557178245272824969L;

	/**
	 * Constructor of the object.
	 */
	public ExportWord() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			Connection conn = MysqlConn.getConnection();
			String account = (String) request.getSession().getAttribute("account");
			MysqlMethod method = new MysqlMethod(conn);
			User user_info = method.getUserInfo(account);
			SimpleDateFormat dft=new SimpleDateFormat("yyyy年MM月dd日");
			String name, gender, age, school, major,picture;

			name = user_info.getUsername();
			gender = user_info.getGender();
			age = user_info.getAge();
			school = user_info.getSchool();
			major = user_info.getMajor();
			picture=user_info.getPicture();

			WordUtil Wutil = new WordUtil();
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("${name}", name);
			param.put("${sex}", gender);
			param.put("${age}", age);
			param.put("${city}", "南京");
			param.put("${school}", school);
			param.put("${major}", major);
			param.put("${date}", dft.format(new Date()));

			Map<String, Object> pic = new HashMap<String, Object>();
			pic.put("width", 100);
			pic.put("height", 100);
			pic.put("type", "jpg");
			pic.put("content", Wutil.inputStream2ByteArray(
					new FileInputStream(picture), true));
			param.put("${pic}", pic);

			CustomXWPFDocument doc = Wutil.generateWord(param,
					"D:\\java software\\workspace\\WebTest\\word\\example.docx");
			String downloadfile=request.getSession().getServletContext().getRealPath("")+"\\word\\"+name+".docx";
		//	System.out.println(downloadfile);
			
			FileOutputStream fopts = new FileOutputStream(downloadfile);
			doc.write(fopts);
			System.out.println("文档导出完成");
			download(downloadfile,response,name);
			fopts.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	private void download(String filepath, HttpServletResponse response,String filename) throws IOException{
		
		File file=new File(filepath);
		
		if(file.exists()){
			response.setContentType("application/x-msdownload"); //启动浏览器下载功能
	        response.setHeader("Content-Disposition", "attachment;filename=\"" + "resume.docx" + "\"");//设置下载文档的默认名及文件格式
	        
            InputStream inputStream = new FileInputStream(file);  
            ServletOutputStream outputStream = response.getOutputStream();
            byte b[] = new byte[1024];
            int n;
            while((n = inputStream.read(b)) != -1){
            	outputStream.write(b,0,n); 
            }
            outputStream.close();  
            inputStream.close(); 
		}
	}

}
