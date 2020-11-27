package by.bsuir.tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class SwitchTag extends SimpleTagSupport {
	private String checkboxName;
	private String jsOnclick;

	public void setCheckboxName(String msg) {
		this.checkboxName = msg;
	}

	public void setJsOnclick(String msg) {
		this.jsOnclick = msg;
	}
	
	private StringWriter sw = new StringWriter();
   
	public void doTag() throws JspException, IOException {
		getJspBody().invoke(sw);
		getJspContext().getOut().println("<div class=\"switch-item\">");
		getJspContext().getOut().println("<div>" + sw.toString() + "</div>");
		getJspContext().getOut().println("<label class=\"switch\">");
		if (sw.toString().equals("ru"))
			getJspContext().getOut().println("<input type=\"checkbox\" name=\"" + checkboxName + 
				"\" id=\"" + checkboxName + "\" value=\"rus\" onclick=\"" + jsOnclick + "\" checked>");
		else 
			getJspContext().getOut().println("<input type=\"checkbox\" name=\"" + checkboxName + 
				"\" id=\"" + checkboxName + "\" value=\"rus\" onclick=\"" + jsOnclick + "\">");
		getJspContext().getOut().println("<span class=\"slider round\"></span>");
		getJspContext().getOut().println("</label>");
		getJspContext().getOut().println("</div>");
	  
	}
}