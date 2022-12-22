package edu.najah.cap.Security;

import edu.najah.cap.users.User;
import edu.najah.cap.users.UserRole;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Authorization {
   public static HashMap< String, ArrayList<User>> users ;
   static{
       users=new HashMap<String, ArrayList<User>>();
       users.put("Admin",new ArrayList<User>());
       users.put("Staff",new ArrayList<User>());
       users.put("Reader",new ArrayList<User>());
   }
   public static void hasImportExportPermission(User createdBy){
       if(!Authorization.users.get("Admin").contains(createdBy)||!Authorization.users.get("Staff").contains(createdBy))
           return;
   }

    public static void hasDeletePermission(User createdBy){
        if(!Authorization.users.get("Admin").contains(createdBy));
        return;
    }
}
