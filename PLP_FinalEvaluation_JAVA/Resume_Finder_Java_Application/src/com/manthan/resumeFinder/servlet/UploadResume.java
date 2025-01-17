package com.manthan.resumeFinder.servlet;


import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


@WebServlet("/UploadResume")
public class UploadResume extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//uploading the resume pdf/doc
			MultipartRequest mp=new MultipartRequest(request, "C:\\Users\\CBT\\Desktop\\upload");
			String s = mp.getOriginalFileName("file");
			System.out.println(s);
			request.setAttribute("message",
					"Uploaded Successfully");
		} catch(Exception ex) {
			request.setAttribute("message",
					"Not Uploaded Successfully" + ex);
		}
		request.getRequestDispatcher("/UploadResumeJsp").forward(request, response);

	}

}
