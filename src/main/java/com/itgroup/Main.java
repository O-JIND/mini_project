package com.itgroup;

import com.itgroup.Dao.MyGameDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

// CREATE USER oraman IDENTIFIED BY oracle DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;
//GRANT connect, resource TO oraman;
//ALTER USER oraman ACCOUNT UNLOCK;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyGamemanager magame = new MyGamemanager(new MyGameDao());

        while(true) {
            System.out.println("""
                    0 : 종료
                    1 : 전체 목록
                    2 : 게임 추가
                    3 : 정보 수정
                    4 : 게임 삭제
                    5 : 제작사 별 목록
                    6 : My best 3
                    7 : 관심 목록
                    """);


            int a = sc.nextInt();
            sc.nextLine();
            switch (a) {
                case 1:
                    magame.showList();//
                    break;
                case 2:
                    magame.addList();//
                    break;
                case 3:
                    magame.updateList();
                    break;
                case 4:
                    magame.removeList();//
                    break;
                case 5:
                    magame.SortbyMaker();//
                    break;
                case 6:
                    magame.bestGame3();
                    break;
                case 7:
                    magame.wishList();
                    break;
                case 0:
                    magame.end();//
                    break;
            }
        }






    }


}