package com.itgroup;

import com.itgroup.Bean.MyGame;
import com.itgroup.Dao.MyGameDao;

import java.sql.SQLException;
import java.util.*;
public class MyGamemanager {
    MyGameDao Gda;
    Scanner sc =new Scanner(System.in);
    public MyGamemanager(MyGameDao Gda) {
        this.Gda=Gda;
    }

    public List<MyGame> showList()  {
        String sort = "g.no";
        List<MyGame> malist = Gda.showList(sort);
        Gda.show(malist);
        return malist;
    }


    public int addList() {
        int cnt =-1 ;
        String genres=null;
        StringBuilder gen = new StringBuilder();
        MyGame mg = new MyGame();
        List<MyGame>viewer =Gda.viewTitle();//전체 목록
        viewone(viewer);
        int no = Gda.countList();
        int Max = Gda.MaxNo();
        if(!(no>Max)){ no=Max+1; }
        mg.setNo(no);
        System.out.print("title : ");
        String title=sc.nextLine().trim();
        if (!Gda.SamVer(title)) {//SamVer: id 중복 확인 true;
            mg.setTitle(title);
        }else {
            System.out.println("중복되는 게임입니다.");
            return 0;
        }

        System.out.print("input price : ");
        double price=sc.nextDouble();
        mg.setPrice(price);
        sc.nextLine();

        System.out.print("input maker : ");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);

        while(true){//"Object ,Object  ,Object " 형태로 전달
        System.out.print("input genres (No one else, press enter): ");
        genres=sc.nextLine().trim();
        gen.append(genres);
        gen.append(", ");
        if(genres.isEmpty()){
            gen.deleteCharAt(gen.length()-1);//마지막 추가된 , 제거
            break;
        }
        }
        mg.setGenres(gen.toString());//StringBuilder 문자열로 변환 후 전달

        System.out.print("input  releasedate : ");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.print("input rate : ");
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
        List<MyGame>viewer =Gda.viewTitle();//전체 목록
        viewone(viewer);
        MyGame mg = new MyGame();
        String genres=null;
        StringBuilder gen = new StringBuilder();

        System.out.print("Update title : ");
        String title = sc.next();
        int num = Gda.getNotoTitle(title);
        int no = Gda.countList();
        if (Gda.SamVer(title)) {
            if(0<num && num<=no) {
                mg.setTitle(title);
                mg.setNo(num);
                sc.nextLine();

            }else {
                System.out.print("Try again.");
                return 0;
            }
        } else {
            System.out.print("No one in List");
            return 0;
        }
        System.out.print ("price (No update, press 0) : ");
        double price=sc.nextDouble();
        if(price!=0){
            mg.setPrice(price);
        }
        sc.nextLine();
        System.out.print("maker (No update, press  enter) : ");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);
        int i = 0;
        while(true){
            System.out.print("genres (No update, press enter) : ");
            genres=sc.nextLine().trim();
            if(genres.isEmpty()){break;}
            if(i>0){
                gen.append(", ");
            }
            gen.append(genres);
            i++;
        }
        mg.setGenres(gen.toString());

        System.out.print("releasedate (No update, press enter) : ");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.print("rate (No update, press 0) : ");
        int rate = sc.nextInt();
        sc.nextLine();
        mg.setRate(rate);

        System.out.println("시작");
        cnt = Gda.updateList(mg);
        System.out.println("끝");

        if(cnt==-1){
            System.out.println("실패");
        }else if(cnt ==0){
            System.out.println("문제 발생");
        }
        else {
            System.out.println("변경 성공!");
        }
        return cnt;

    }



    public int removeList() {
        int cnt =-1 ;
        Gda.viewTitle();
        System.out.print("Remove title : ");

        String title=sc.nextLine();
        int no = Gda.getNotoTitle(title);
        if (Gda.SamVer(title)) {
            cnt = Gda.removeList(no);
        }else {
            System.out.println("존재하지 않는 게임입니다.");
            return 0;
        }

        if(cnt>0){
            System.out.println("삭제 성공");
        }else {
            System.out.println("삭제 실패");
        }

        return cnt;
    }


    public List<MyGame> SortbyMaker() {
        System.out.print("Enter Maker : ");
        String mk = sc.next();
        sc.nextLine();
        List<MyGame> makerlist=Gda.SortbyMaker(mk);
        Gda.show(makerlist);
        return makerlist;
    }


    public List<MyGame> SortbyDate() {
        String sort = "date";
        List<MyGame> malist = Gda.showList(sort);
        Gda.show(malist);
        return malist;
    }

    public List<MyGame> SortbyScore() {
        String rate = "score";
        List<MyGame> malist = Gda.showList(rate);
        Gda.show(malist);
        return malist;
    }

    public List<MyGame> genreList() {
        System.out.print("Enter Genre : ");
        String search = sc.next();
        sc.nextLine();
        List<MyGame> genre =null;
        if(Gda.VerGenre(search)){
            genre= Gda.genreList(search);
            Gda.show(genre);
        }else {
            System.out.println( search +" doesn't  exist.");
        }


        return genre;
    }

    public void end() {
        sc=new Scanner(System.in);
       try {
           System.out.println("Exit ?  1 Yes / 2 No");
            int a = sc.nextInt();

            if (a==1) {
                System.out.println("See you");
                System.exit(0);
            }
        } catch (Exception e) {
           System.out.println("Try again.");
       }
    }


    public int selectAll() {
        int cntall = Gda.selectAll();
        String msg = "Total games : "+ cntall;
        System.out.println(msg);

        return cntall;
    }
    public void viewone(List<MyGame> list){
        for(MyGame s : list){
            String title = s.getTitle();
            String msg = "title : "+title;
            System.out.println(msg);

        }
    }
}
