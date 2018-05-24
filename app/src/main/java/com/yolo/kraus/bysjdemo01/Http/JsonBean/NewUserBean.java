package com.yolo.kraus.bysjdemo01.Http.JsonBean;

/**
 * Created by Kraus on 2018/5/22.
 */

public class NewUserBean {

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

    public class newBack
    {
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private int code;
    }
}
