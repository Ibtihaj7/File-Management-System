package edu.najah.cap.Security;

import edu.najah.cap.users.User;
import edu.najah.cap.users.UserRole;

public abstract class Authorization {
   public static boolean hasAdminPermission(User user){
       return UserRole.valueOf(user.getRole()) == UserRole.ADMIN;
   }

    public static boolean hasStaffPermission(User user){
        return UserRole.valueOf(user.getRole()) == UserRole.STAFF;
    }
}
