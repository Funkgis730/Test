package org.piesat.zzh.controller;

import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.piesat.zzh.model.MysqlConn;
import org.piesat.zzh.model.User;
import org.piesat.zzh.model.MysqlMethod;;

public class Register extends HttpServlet {

	private static final long serialVersionUID = -900165859803339634L;

	private static final String UPLOAD_DIRECTORY = "upload";

	// 上传配置
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/**
	 * Constructor of the object.
	 */
	public Register() {
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
			User u = new User();
			Connection conn = MysqlConn.getConnection();
			MysqlMethod method = new MysqlMethod(conn);

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			if (method.searchUser(request.getParameter("account"))) {
				request.getSession().setAttribute("isRepeat", "true");
				System.out.println("账号重复");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			} else {
				request.getSession().setAttribute("isRepeat", "false");
			}

			HashMap<String, String> map = getFormData(request);

			String[] f = new String[10];
			f[0] = map.get("favorites");

			u.setAccount(map.get("account"));
			u.setUsername(map.get("username"));
			u.setPassword(map.get("password"));
			u.setAge(map.get("age"));
			u.setEmail(map.get("email"));
			u.setGender(map.get("gender"));
			u.setMajor(map.get("major"));
			u.setSchool(map.get("school"));
			u.setFavorites(f);
			u.setPicture(map.get("picture"));

			request.getSession().setAttribute("account", u.getAccount());

			int isComplete = method.insert(u);
			if (isComplete > 0) {
				System.out.println("数据库填充完成");
				request.getRequestDispatcher("/regComplete.jsp").forward(request, response);
			} else {
				System.out.println("数据库填充失败");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	/**
	 * 
	 * @param request
	 * @return 上传文件的服务器保存地址
	 */
	private HashMap<String, String> getFormData(HttpServletRequest request) {
		String filePath = null;
		HashMap<String, String> info_map = new HashMap<String, String>();

		if (!ServletFileUpload.isMultipartContent(request)) {
			// 如果不是则停止
			System.out.println("Error: 表单必须包含 enctype=multipart/form-data");
			System.out.flush();
		}

		// 配置上传参数
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 设置临时存储目录
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置最大文件上传值
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// 设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// 中文处理
		upload.setHeaderEncoding("UTF-8");

		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录，tomcat中该servlet内创建upload文件夹
		String uploadPath = request.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;

		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// 解析请求的内容提取文件数据，提取表单内提交的所有数据
			List<FileItem> formItems = upload.parseRequest(request);
			String fileName = "";

			if (formItems != null && formItems.size() > 0) {
				// 迭代表单数据
				for (FileItem item : formItems)
					if (item.isFormField() && item.getFieldName().equals("username")) {
						fileName = item.getString("UTF-8");
					}

				for (FileItem item : formItems) {
					// 处理不在表单中的字段
					if (!item.isFormField()) {
						String[] filenames = new File(item.getName()).getName().split("\\.");
						String format=filenames[1];
						filePath = uploadPath + File.separator + fileName+"."+format;
						File storeFile = new File(filePath);
						// 保存文件到硬盘
						item.write(storeFile);
						info_map.put("picture", filePath);
					} else {
						String attributeName = item.getFieldName();
						String attributeValue = item.getString("UTF-8");
						info_map.put(attributeName, attributeValue);
					}
				}
				return info_map;
			}
		} catch (Exception ex) {
			request.setAttribute("message", "错误信息: " + ex.getMessage());
		}
		return info_map;
	}

}
