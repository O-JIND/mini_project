package com.itgroup;

import com.itgroup.Bean.MyGame;
import com.itgroup.Dao.MyGameDao;
import java.util.*;
public class MyGamemanager {
    MyGameDao Gda;
    Scanner sc =null;
    public MyGamemanager(MyGameDao Gda) {
        this.Gda=Gda;
    }

    public List<MyGame> showList() {
        List<MyGame> malist = Gda.showList();
        Gda.showList(malist);
        return malist;
    }


    public int addList() {
        int cnt =-1 ;
        MyGame mg = new MyGame();

        System.out.println("title");
        String title=sc.nextLine().trim();
        if (!Gda.SamVer(title)) {
            mg.setTitle(title);
        }else {
            System.out.println("중복되는 게임입니다.");
            return 0;
        }

        System.out.println("price");
        double price=sc.nextDouble();
        mg.setPrice(price);
        sc.nextLine();

        System.out.println("maker");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);

        System.out.println("releasedate");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.println("rate");
        int rate=sc.nextInt();
        mg.setRate(rate);
        sc.nextLine();

        cnt = Gda.addList(mg);
        if(cnt==0){
            System.out.println("오류 발생");
        }else if(cnt==-1){
            System.out.println("추가 실패");
        }else{
            System.out.println("추가 성공");
        }

        return cnt;

    }


    public int updateList() {
        int cnt =-1 ;
        MyGame mg = new MyGame();
        System.out.println("title");
        String title=sc.nextLine().trim();
        if (Gda.SamVer(title)) {
            mg.setTitle(title);
        }else {
            System.out.println("목록에 없는 게임입니다.");
            return 0;
        }


        System.out.println("price (변경 없으면 enter)");
        double price=sc.nextDouble();
        mg.setPrice(price);
        sc.nextLine();

        System.out.println("maker (변경 없으면 enter)");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);

        System.out.println("releasedate (변경 없으면 enter)");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.println("rate (변경 없으면 enter)");
        int rate=sc.nextInt();
        mg.setRate(rate);
        sc.nextLine();

        cnt = Gda.updateList(mg);

        if(cnt>0){
            System.out.println("추가 성공!");
        }else {
            System.out.println("실패");
        }


        return cnt;

    }


    public int removeList() {
        int cnt =-1 ;

        MyGame mg = new MyGame();
        System.out.println("title");
        String title=sc.nextLine().trim();
        if (Gda.SamVer(title)) {
            mg.setTitle(title);
        }else {
            System.out.println("존재하지 않는 게임입니다.");
            return 0;
        }
        cnt = Gda.removeList(title);
        if(cnt>0){
            System.out.println("삭제 성공");
        }else {
            System.out.println("삭제 실패");
        }

        return cnt;
    }


    public List<MyGame> SortbyMaker() {
        String mk = sc.next();
        List<MyGame> makerlist=Gda.SortbyMaker(mk);
        Gda.showList(makerlist);
        return makerlist;
    }


    public void bestGame3() {


    }


    public void wishList() {


    }


    public void end() {
        sc=new Scanner(System.in);
       try {
           System.out.println("종료? 1 Yes / 2 No");
            int a = sc.nextInt();

            if (a==1) {
                System.exit(0);
            }
        } catch (Exception e) {
           System.out.println("다시 입력하세요.");
       }
    }



}
