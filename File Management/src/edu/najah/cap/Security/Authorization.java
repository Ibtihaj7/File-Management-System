package edu.najah.cap.Security;

import edu.najah.cap.users.UserRole;

public abstract class Authorization {
   public static boolean hasAdminPermission(){
       return UserRole.valueOf(Authentication.getUserRole()) == UserRole.ADMIN;
   }

    public static boolean hasStaffPermission(){
        return UserRole.valueOf(Authentication.getUserRole()) == UserRole.STAFF;
    }
}
