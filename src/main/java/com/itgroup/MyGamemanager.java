package com.itgroup;

import com.itgroup.Bean.MyGame;
import com.itgroup.Dao.MyGameDao;
import java.util.*;
public class MyGamemanager {
    MyGameDao Gda;
    Scanner sc =new Scanner(System.in);
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
        String genres=null;
        StringBuilder gen = new StringBuilder();
        MyGame mg = new MyGame();
        List<MyGame>viewer =Gda.viewTitle();//전체 목록
        viewone(viewer);
        int no = Gda.countList();
        mg.setNo(no);
        System.out.println("title");
        String title=sc.nextLine().trim();
        if (!Gda.SamVer(title)) {//SamVer: id 중복 확인 true;
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

        while(true){//"Object ,Object  ,Object " 형태로 전달
        System.out.println("genres");
        genres=sc.nextLine().trim();
        gen.append(genres);
        gen.append(", ");
        if(genres.isEmpty()){
            gen.deleteCharAt(gen.length()-1);//마지막 추가된 , 제거
            break;
        }
        }
        mg.setGenres(gen.toString());//StringBuilder 문자열로 변환 후 전달

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
        List<MyGame>viewer =Gda.viewTitle();//전체 목록
        viewone(viewer);
        MyGame mg = new MyGame();
        String genres=null;
        StringBuilder gen = new StringBuilder();

        System.out.println("No : ");
        int num = sc.nextInt();
        int no = Gda.countList();

        if(0<num && num<no) {
            System.out.print("Update title : ");
            mg.setNo(num);
            String title = sc.nextLine();
            if (Gda.SamVer(title)) {
                mg.setTitle(title);
            } else {
                System.out.println("목록에 없는 게임입니다.");
                return 0;
            }
        }else {
            System.out.println("no를 제대로 입력하시오.");
            return 0;
        }

        System.out.print ("price (변경 없으면 0) : ");
        double price=sc.nextDouble();
        mg.setPrice(price);
        sc.nextLine();

        System.out.print("maker (변경 없으면 enter) : ");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);

        while(true){
            System.out.println("genres (변경 없으면 enter)");
            genres=sc.nextLine().trim();
            gen.append(genres);
            gen.append(", ");
            if(genres.isEmpty()){
                gen.deleteCharAt(gen.length()-1);
                break;
            }
        }
        mg.setGenres(gen.toString());

        System.out.print("releasedate (변경 없으면 enter) : ");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.print("rate (변경 없으면 0) : ");
        int rate=sc.nextInt();
        mg.setRate(rate);
        sc.nextLine();

        cnt = Gda.updateList(mg);

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
        MyGame mg = new MyGame();
        System.out.println("title");

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
        String mk = sc.next();
        List<MyGame> makerlist=Gda.SortbyMaker(mk);
        System.out.println(makerlist);
        Gda.showList(makerlist);
        return makerlist;
    }

    public List<MyGame> genreList() {
        String search = sc.next();
        List<MyGame> genre =null;
        if(Gda.VerGenre(search)){
            genre= Gda.genreList(search);
            Gda.showList(genre);
        }else {
            System.out.println("존재 하지 않는 장르입니다.");
        }


        return genre;
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


    public int selectAll() {
        int cntall = Gda.selectAll();
        String msg = "총 게임 수 : "+ cntall;
        System.out.println(msg);

        return cntall;
    }
    public void viewone(List<MyGame> list){
        for(MyGame s : list){
            String title = s.getTitle();
            String msg = "title : "+title + "||";
            System.out.println(msg);

        }
    }
}
