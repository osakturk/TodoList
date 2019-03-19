package api.helper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

public class RequestObject {

    public static String[] get(String paramName, MultivaluedMap<String, String> form, HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("get")) {
            if (form.containsKey(paramName)) {
                return form.get(paramName).toArray(new String[form.get(paramName).size()]);
            }
        }
        return (String[]) request.getAttribute(paramName);
    }

    public static String getString(String paramName, MultivaluedMap<String, String> form, HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("get") && !request.getMethod().equalsIgnoreCase("put")) {
            if (form.containsKey(paramName)) {
                return form.get(paramName).toString().replace("[", "").replace("]", "");
            }
        }else{
            if (request.getAttribute(paramName) != null){
                return request.getAttribute(paramName).toString().replace("[", "").replace("]", "");
            }
            if (request.getParameter(paramName) != null){
                return request.getParameter(paramName).replace("[", "").replace("]", "");
            }
        }
        if (paramName.contains("min")) {
            return "0";
        }
        return "-1";
    }

    public static Integer getInt(String paramName, MultivaluedMap<String, String> form, HttpServletRequest request) {
        return Integer.parseInt(getString(paramName, form, request));
    }

    public static Double getDouble(String paramName, MultivaluedMap<String, String> form, HttpServletRequest request) {
        return Double.parseDouble(getString(paramName, form, request));
    }

    public static boolean hasKey(String paramName, MultivaluedMap<String, String> form, HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("get")) {
            return form.containsKey(paramName);
        }
        return request.getAttribute(paramName) != null || request.getParameter(paramName) != null;
    }



}
