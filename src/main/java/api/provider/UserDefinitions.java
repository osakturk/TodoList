package api.provider;

import model.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

public class UserDefinitions {
    public static boolean createPersonnelDefinition(HttpServletRequest request, MultivaluedMap<String, String> form) {
        UserRepository repository = new UserRepository();
        return repository.insertUser(form,request);
    }

    public static JSONObject getUsers() {
        UserRepository repository = new UserRepository();
        JSONObject result = new JSONObject();
        JSONArray resultArray = new JSONArray();
        for (UserInfo user: repository.getUsers()) {
            JSONObject userObject = new JSONObject();
            userObject.put("id",user.getUserId());
            userObject.put("full_name",user.getFirstname());
            userObject.put("username",user.getUsername());
            userObject.put("locale",user.getCurrentLocale());
            resultArray.put(userObject);
        }
        result.put("data",resultArray);
        return result;
    }
}
