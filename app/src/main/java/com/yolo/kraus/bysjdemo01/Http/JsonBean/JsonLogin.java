package com.yolo.kraus.bysjdemo01.Http.JsonBean;

/**
 * Created by Kraus on 2018/4/20.
 */

public class JsonLogin {

   public static class User
   {
       /**
        * username : admin
        * password : 123456
        */

       private String username;
       private String password;

       public User(String name,String pwd)
       {
           this.username = name;
           this.password = pwd;
       }

       public String getUsername() {
           return username;
       }

       public void setUsername(String username) {
           this.username = username;
       }

       public String getPassword() {
           return password;
       }

       public void setPassword(String password) {
           this.password = password;
       }
   }



   public class loginBack
   {


       /**
        * sig : xxxx
        * status : 200
        */

       private String sig;
       private String status;

       public String getSig() {
           return sig;
       }

       public void setSig(String sig) {
           this.sig = sig;
       }

       public String getStatus() {
           return status;
       }

       public void setStatus(String status) {
           this.status = status;
       }
   }
}
