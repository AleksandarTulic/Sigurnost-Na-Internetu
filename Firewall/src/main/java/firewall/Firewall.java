package firewall;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import jakarta.servlet.ServletException;
import service.*;

public class Firewall extends ValveBase{
	private static DOS dos = new DOS();
	private static XSS xss = new XSS();
	private static SQLI sqli = new SQLI();
	private static ParameterTampering pt = new ParameterTampering();
	
	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		boolean flag = false;
		
		if (!request.equals(null)) {
			flag = dos.newRequest(request) || xss.check(request) || sqli.check(request) || pt.check(request);
		}
		
		if (!flag) {
			getNext().invoke(request, response);
		}else {
			OutputStream out = response.getOutputStream();
			out.write("Something strange is happening ...".getBytes());
			out.flush();
			out.close();
		}
	}

}
