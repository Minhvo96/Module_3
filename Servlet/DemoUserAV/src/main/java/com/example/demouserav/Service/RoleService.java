package Service;

import DAO.RoleDAO;
import Model.Role;

public class RoleService {
    public static List<Role> getRoles(){
        return new RoleDAO().getRoles();
    }
}
