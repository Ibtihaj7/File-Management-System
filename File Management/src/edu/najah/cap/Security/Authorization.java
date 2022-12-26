package edu.najah.cap.Security;

import edu.najah.cap.App;
import edu.najah.cap.Users.User;
import edu.najah.cap.Users.UserRole;

public abstract class Authorization {
   public static boolean hasAdminPermission(User user){

       return (UserRole.valueOf(user.getRole()) == UserRole.ADMIN);
   }

    public static boolean hasStaffPermission(User user){
        return (UserRole.valueOf(user.getRole()) == UserRole.STAFF);
    }
    public static boolean isAuthorized(User user) {
       App.LOGGER.debug("Checking if user is authorized: " + user.getUsername());
        return Authorization.hasAdminPermission(user) || Authorization.hasStaffPermission(user);
    }
}
