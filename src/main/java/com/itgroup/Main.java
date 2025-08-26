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
                    6 : 장르 별 게임 검색
                    7 : 모든 게임 개수
                    8 : 출시일 순으로 보기
                    9 : 메타크리틱순으로 보기
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
                    magame.updateList();//
                    break;
                case 4:
                    magame.removeList();//
                    break;
                case 5:
                    magame.SortbyMaker();//
                    break;
                case 6:
                    magame.genreList();//
                    break;
                case 7:
                    magame.selectAll();//
                    break;
                case 8:
                    magame.SortbyDate();//
                    break;
                case 9:
                    magame.SortbyScore();//
                    break;



                case 0:
                    magame.end();//
                    break;
            }
        }






    }


}