package edu.najah.cap.Security;

import edu.najah.cap.users.UserRole;

public abstract class Authorization {
   public static boolean hasAdminPermission(){
       if(UserRole.valueOf(Authentication.getUserRole())!=UserRole.ADMIN) {
           System.err.println("You do not have the authority to access.");
           return false;
       }
       return true;
    }

    public static boolean hasStaffPermission(){
        if(UserRole.valueOf(Authentication.getUserRole())!=UserRole.STAFF) {
            System.err.println("You do not have the authority to access.");
            return false;
        }
        return true;
    }
}
