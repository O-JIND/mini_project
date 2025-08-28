package com.itgroup;

import com.itgroup.Dao.MyGameDao;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

// CREATE USER oraman IDENTIFIED BY oracle DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;
//GRANT connect, resource TO oraman;
//ALTER USER oraman ACCOUNT UNLOCK;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        MyGamemanager magame = new MyGamemanager(new MyGameDao());
        int a =-1;
        while(true) {
            System.out.println("""
                    0 : Exit
                    1 : Entire List
                    2 : Add Game
                    3 : Update Game
                    4 : Delete Game
                    5 : List by Maker
                    6 : List by Genre
                    7 : All Game
                    8 : Sort by Released Date
                    9 : Sort by Metacritic Score
                    """);
            try{
            a = sc.nextInt();
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
                    break;}

            }catch (InputMismatchException e) {
                System.out.println("Please enter Integer.");
                sc.nextLine();
                continue;
            }
            if (a<0||a>9){
                System.out.println("Please enter Integer in range");
                continue;
            }
        }
    }
}