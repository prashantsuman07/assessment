package com.manthan.resumeFinder.servlet;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@WebServlet("/SearchResume")
public class SearchResume extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean flag=false;
		String input=req.getParameter("search");
		String dir="C:\\Users\\CBT\\Desktop\\upload";
		File maindir= new File(dir);
		if(maindir.exists()&& maindir.isDirectory()) {
			File arr[]= maindir.listFiles();
			for(File F: arr) {
				if(F.isFile()&&F.getName().substring(F.getName().lastIndexOf(".")).equals(".pdf")) {


					PDDocument document = PDDocument.load(F);

					//System.out.println(F);
					//Instantiate PDFTextStripper class
					PDFTextStripper pdfStripper = new PDFTextStripper();

					//Retrieving text from PDF document
					String text = pdfStripper.getText(document);
					// System.out.println(text);
					String[] textArr=text.split("\\r?\\n");
					for(String s:textArr)
					{
						//System.out.println(m.group());
						String s1[]=input.split(",");
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
							System.out.println("\n\n\nfound");
							String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
							Pattern pattern = Pattern.compile(regex);
							for(String email:textArr) {
								Matcher matcher = pattern.matcher(email);
								if(matcher.matches()) {
									System.out.println(email );
									String name=email.substring(0, email.lastIndexOf("@"));
									System.out.println(name.replace("", " "));
								}
								
							}
							System.out.println(F.getAbsolutePath());
							break;
						}
					}

					//Closing the document
					document.close();
				}
			}

		}

	}
}
