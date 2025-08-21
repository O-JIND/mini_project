package com.itgroup.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SuperDao {
    public SuperDao() {
        //드라이버 관련 OracleDriver는 ojdbc6.jar파일에 포함되어 있는 자바클래스
        String driver = "oracle.jdbc.OracleDriver";
        try {
            Class.forName(driver);//동적 객체 형성
        } catch (ClassNotFoundException e) {
            System.out.println("해당 드라이버가 존재하지 않습니다.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn = null; //접속 객체
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "ichigo";
        String password = "strawberry";

        try {
            conn = DriverManager.getConnection(url, id, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

}



