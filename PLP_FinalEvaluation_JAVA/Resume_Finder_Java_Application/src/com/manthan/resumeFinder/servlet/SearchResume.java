package com.manthan.resumeFinder.servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.manthan.resumeFinder.bean.ResumeBean;

@WebServlet("/SearchResume")
public class SearchResume extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ResumeBean resumeBean=null;
		ArrayList<ResumeBean> resumeArray=new ArrayList<ResumeBean>();
		boolean flag=false;
		String input=req.getParameter("search");
		String input1=input.trim();
		String dir="C:\\Users\\CBT\\Desktop\\upload";
		File maindir= new File(dir);
		if(maindir.exists()&& maindir.isDirectory()) {
			File arr[]= maindir.listFiles();
			for(File F: arr) {
				if(F.isFile()&&F.getName().substring(F.getName().lastIndexOf(".")).equals(".pdf")) {

					if(!input1.isEmpty()) {
						PDDocument document = PDDocument.load(F);

						//Instantiate PDFTextStripper class
						PDFTextStripper pdfStripper = new PDFTextStripper();

						//Retrieving text from PDF document
						String text = pdfStripper.getText(document);
						String[] textArr=text.split("\\r?\\n");
						for(String s:textArr)
						{
							//split if input is two comma seperated word
							String s1[]=input1.split(",");
							for(String match:s1) {

								if(s.contains(match)) {
									flag=true;

								}
								else {
									flag=false;
									break;
								}
							}
							if(flag) {
								resumeBean=new ResumeBean();
								//matching the email pattern in file
								String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
								Pattern pattern = Pattern.compile(regex);
								for(String email:textArr) {
									Matcher matcher = pattern.matcher(email);
									if(matcher.matches()) {


										resumeBean.setEmail(email);
										//fetching name from email
										String name=email.substring(0, email.lastIndexOf("@"));
										String tempname=name.replace(".", " ");
										String username="";
										Scanner scanner=new Scanner(tempname);
										while(scanner.hasNext()) {
											String word=scanner.next();
											username +=Character.toUpperCase(word.charAt(0))+word.substring(1)+" ";
										}
										resumeBean.setName(username);
										String fileName=F.getName();
										resumeBean.setFilename(fileName);

										resumeArray.add(resumeBean);
										break;
									}

								}
								break;
							}

						}

						//Closing the document
						if(!resumeArray.isEmpty()) {
							document.close();
							req.setAttribute("msg",
									"FILES LIST");
						}
						else {
							req.setAttribute("msg",
									"NO FILES FOUND");
						}
					}
					else {
						req.setAttribute("msg",
								"NO FILES FOUND");
					}
				}
					
				
			}
		}//end of dir check if
			
		req.setAttribute("resumeBean",
				resumeArray);
		req.getRequestDispatcher("/SearchResumeJsp").forward(req, resp);
		
	}//end of doget
}//end of class
