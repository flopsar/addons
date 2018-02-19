package org.flopsar.ext.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;


public class HttpFormatter {

    private static final char PARAMETER_SEPARATOR = 0x1E;




    public static String reqresp(Object[] input){
        try {
            HttpServletRequest req = (HttpServletRequest)input[0];

            /*
				try {
					req.setCharacterEncoding("windows-1250");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            */

            StringBuilder output = new StringBuilder("URL");
            output.append(PARAMETER_SEPARATOR);
            output.append(req.getRequestURL().toString());

            Enumeration hnames = req.getHeaderNames();
            output.append(PARAMETER_SEPARATOR);
            output.append("HEADER");
            output.append(PARAMETER_SEPARATOR);
            while (hnames.hasMoreElements()){
                String name = (String)hnames.nextElement();
                output.append(name);
                output.append("=");
                output.append(req.getHeader(name));
                output.append("+");
            }

            Enumeration pnames = req.getParameterNames();
            output.append(PARAMETER_SEPARATOR);
            output.append("REQPARAMS");
            output.append(PARAMETER_SEPARATOR);
            while(pnames.hasMoreElements()){
                String name = (String)pnames.nextElement();
                output.append(name);
                output.append("=");
                output.append(req.getParameter(name));
                output.append("+");
            }

            HttpSession session = req.getSession(false);
            output.append(PARAMETER_SEPARATOR);
            output.append("SID");
            output.append(PARAMETER_SEPARATOR);

            if (session == null)
                output.append("null");
            else {
                output.append(session.getId());
                output.append(PARAMETER_SEPARATOR);
                output.append("ATTRS");
                output.append(PARAMETER_SEPARATOR);
                Enumeration snames = session.getAttributeNames();
                while(snames.hasMoreElements()){
                    String name = (String)snames.nextElement();
                    output.append(name);
                    output.append("=");
                    output.append(String.valueOf(session.getAttribute(name)));
                    output.append("+");
                }
            }

            return output.toString();
        } catch (Throwable e) {
            return "EXCEPTION"+PARAMETER_SEPARATOR+e.getMessage();
        }
    }


}
