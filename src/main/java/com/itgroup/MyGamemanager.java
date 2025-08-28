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
        System.out.print("\ntitle : ");
        String title=sc.nextLine().trim();
        if (!Gda.SamVer(title)) {//SamVer: id 중복 확인 true;
            mg.setTitle(title);
        }else {
            String msg = title + " is exist ";
            System.out.println(msg);
            return 0;
        }

        System.out.print("Enter price : ");
        double price=sc.nextDouble();
        mg.setPrice(price);
        sc.nextLine();

        System.out.print("Enter maker : ");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);
        int i = 0;
        while(true){//"Object ,Object  ,Object " 형태로 전달
            System.out.print("Enter genres (press Enter to skip) : ");
            genres=sc.nextLine().trim();
            if(genres.isEmpty()){break;}
            if(i>0){
                gen.append(",");
            }
            gen.append(genres);
            i++;
        }
        System.out.println(gen.toString());
        mg.setGenres(gen.toString());
        //StringBuilder 문자열로 변환 후 전달

        System.out.print("Enter  released date : ");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.print("Enter rate : ");
        int rate=sc.nextInt();
        mg.setRate(rate);
        sc.nextLine();

        cnt = Gda.addList(mg);
        if(cnt==0){
            System.out.println("An error occurred.");
        }else if(cnt==-1){
            System.out.println("Failed to Add.");
        }else{
            System.out.println("Added Successfully");
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
        System.out.print("\nUpdate title : ");
        String title = sc.nextLine().trim();
        int num = Gda.getNotoTitle(title);
        int no = Gda.MaxNo();
        if (Gda.SamVer(title)) {
            if(0<num && num<=no) {
                mg.setTitle(title);
                mg.setNo(num);

            }else {
                System.out.print("Please try again.");
                return 0;
            }
        } else {
            System.out.print("Title not found in the list.");
            return 0;
        }
        System.out.print ("Enter price (press 0 to skip) : ");
        double price=sc.nextDouble();
        if(price!=0){
            mg.setPrice(price);
        }
        sc.nextLine();
        System.out.print("Enter maker (press enter to skip) : ");
        String maker=sc.nextLine().trim();
        mg.setMaker(maker);
        int i = 0;
        while(true){
            System.out.print("Enter genres (press enter to skip) : ");
            genres=sc.nextLine().trim();
            if(genres.isEmpty()){break;}
            if(i>0){
                gen.append(",");
            }
            gen.append(genres);
            i++;
        }
        mg.setGenres(gen.toString());
        System.out.print("Enter released date (press enter to skip) : ");
        String releasedate=sc.nextLine().trim();
        mg.setreleasedate(releasedate);

        System.out.print("Enter rate (press 0 to skip) : ");
        int rate = sc.nextInt();
        sc.nextLine();
        mg.setRate(rate);
        cnt = Gda.updateList(mg);

        if(cnt==-1){
            System.out.println("Fail to update");
        }else if(cnt ==0){
            System.out.println("An error occurred");
        }
        else {
            System.out.println("Update Successfully");
        }
        return cnt;

    }



    public int removeList() {
        int cnt =-1 ;
        Gda.viewTitle();
        System.out.print("Enter title to delete : ");

        String title=sc.nextLine();
        int no = Gda.getNotoTitle(title);
        if (Gda.SamVer(title)) {
            cnt = Gda.removeList(no);
        }else {
            System.out.println(title + " does not exist");
            return 0;
        }

        if(cnt>0){
            System.out.println("Delete completed");
        }else {
            System.out.println("Delete failed");
        }

        return cnt;
    }


    public List<MyGame> SortbyMaker() {
        System.out.print("Enter maker : ");
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
            System.out.println( search +" does not  exist.");
        }


        return genre;
    }

    public void end() {
        sc=new Scanner(System.in);
       try {
           System.out.println("Exit ?  1 Yes / 2 No");
            int a = sc.nextInt();

            if (a==1) {
                System.out.println("System shutting down...");
                System.exit(0);
            }
        } catch (Exception e) {
           System.out.println("Please try again.");
       }
    }


    public int selectAll() {
        int cntall = Gda.selectAll();
        String msg = "Total games : "+ cntall;
        System.out.println(msg);

        return cntall;
    }
    public void viewone(List<MyGame> list){
        int sort = 1;
        for(MyGame s : list){
            String title = s.getTitle();

            String fixtitle=Gda.fixLength(title,30);
            if (sort%3!=0) {
                System.out.printf("title :  %-30s     " ,fixtitle);
            }else {
                System.out.printf("title :  %-30s     %n" ,fixtitle);
            }
            sort++;

        }
    }
}
