package com.itgroup.Dao;

import com.itgroup.Bean.MyGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class MyGameDao extends SuperDao{
    public MyGameDao() {super();}


    public List<MyGame> showList(String sort) {
        String order;
        List<MyGame> mylist=new ArrayList<>();
        switch (sort){
            case "date":
                order ="rel_dt  desc";
                break;
            case "score":
                order ="g.rate desc";
                break;
            default:
                order = "g.no";
        }
        String sql = "SELECT distinct g.no,g.title ,g.price,g.maker,listagg(gg.genre,',')" ;
        sql+= " WITHIN GROUP (ORDER BY gg.genre) AS genres," +
                "To_char(g.releasedate,'YYYY-MM-DD') as Released_date,g.rate, g.releasedate AS rel_dt " ;
        sql+="FROM MyGame g " ;
        sql+="left JOIN GameGenre gg ON g.no = gg.MyGame_no " ;
        sql+="GROUP BY g.no, g.title, g.price, g.maker, g.releasedate, g.rate " ;
        sql+="ORDER BY " + order ;
        //Distinct 때문에 오류 || rel_dt => 정렬용 데이터 추가
        try{

            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                MyGame bean =makeList(rs);
                mylist.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mylist;
    }

    public int addList(MyGame mg) {
        int add =-1;
        Connection conn = null;
        String sql = "insert into MyGame(no,title,price,maker,To_char(g.releasedate,'YYYY-MM-DD') as Released_date,rate)" ;
        sql+="values (?,?,?,?,?,?)";
        String sql1 = "insert into gamegenre";
        sql1+=" values (?,?)";
        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            int num=mg.getNo();
            pstmt.setInt(1,num);
            String title=mg.getTitle();
            pstmt.setString(2,title);
            double price=mg.getPrice();
            pstmt.setDouble(3,price);
            String maker=mg.getMaker();
            pstmt.setString(4,maker);
            String releasedate=mg.getreleasedate();
            pstmt.setString(5,releasedate);
            int rate=mg.getRate();
            pstmt.setInt(6,rate);
            add = pstmt.executeUpdate();

            int no=mg.getNo();
            String genres = mg.getGenres();

            if(genres.contains(",")){
            for(String s: genres.split(",")) {
                pstmt1.setInt(1,no);
                pstmt1.setString(2,s.trim());
                add += pstmt1.executeUpdate();
            }
            }else{
                pstmt1.setString(2,genres);
                add += pstmt1.executeUpdate();
            }
            conn.commit();
        }catch (SQLException e) {
            try{
                conn.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return add;
    }

    public int updateList(MyGame mg) {
        Map<Object,Object> mapgame = null;
        Connection conn =null;
        int update =-1;


        StringBuilder sql =new StringBuilder("Update MyGame set ");
        String sql1 = "Update GameGenre set genre = ? where UPPER(MyGame_no) = UPPER(?)";

        try{
            mapgame = new HashMap<>();
            conn = super.getConnection();
            String maker=null,releasedate=null,genre=null;
            String title = mg.getTitle();
            int rate =0,no=mg.getNo();
            double price =0;

            mapgame.put("no",no );
            mapgame.put("title",title);
            if(mg.getPrice()!=0){  price= mg.getPrice();
                mapgame.put("price",price);}

            if(!mg.getMaker().isEmpty()){  maker = mg.getMaker();
            mapgame.put("maker",maker);}

            if(!mg.getreleasedate().isEmpty()){   releasedate = mg.getreleasedate();
            mapgame.put("releasedate",releasedate);}

            if(mg.getRate()!=0){rate = mg.getRate();
            mapgame.put("rate",rate);    }

            int i = 0;//genre X
            for (Object s: mapgame.keySet()){
                String plus = s + "=?";
                sql.append(plus);
                if(i< mapgame.size()-1){
                    String comma = " , ";
                    sql.append(comma);
                }
                i++;
            } sql.append(" where no = ? ");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            int j = 1;
            for (Object s: mapgame.values()){
                pstmt.setObject(j++,s);
            }pstmt.setString(j,title);
            update = pstmt.executeUpdate();
            conn.commit();

            PreparedStatement pstmt1 = conn.prepareStatement(sql1);


            genre=mg.getGenres();
            if(!genre.isEmpty()) {
                if(InGenre(no,genre)){
                if (genre.contains(",")) {
                    for (String s : genre.split(",")) {
                        pstmt1.setString(1, s.trim());
                        pstmt1.setInt(2, no);
                        update += pstmt1.executeUpdate();
                    }
                } else {
                    pstmt1.setString(1, genre);
                    pstmt1.setInt(2, no);
                    update += pstmt1.executeUpdate();
                }}else {
                    System.out.println("변경할 대상이 없습니다.");
                }
            }
        }catch (SQLException e) {
            try{
                conn.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            try {
                if(conn!=null){
                    conn.close();

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return update;
    }

    public int removeList(int no) {
        int remove =-1;
        Connection conn = null;
        String sql = "delete from GameGenre where UPPER(MyGame_no) =UPPER(?)";
        String sql1 = "delete from MyGame where UPPER(no) = UPPER(?)";
        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt.setInt(1,no);
            remove = pstmt.executeUpdate();
            pstmt1.setInt(1,no);
            remove += pstmt1.executeUpdate();
            conn.commit();
        }catch (SQLException e) {
            try {
                conn.rollback();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  remove;
    }

    public List<MyGame> SortbyMaker(String maker) {
        List<MyGame> mylist =new ArrayList<>();;
        Connection conn = null;
        String sql = "SELECT distinct g.no,g.title ,g.price,g.maker,listagg(gg.genre,',')" ;
        sql+= " WITHIN GROUP (ORDER BY gg.genre) AS genres,g.To_char(g.releasedate,'YYYY-MM-DD') as Released_date,g.rate" ;
        sql+= " FROM MyGame g" ;
        sql+=" left JOIN GameGenre gg ON g.no = gg.MyGame_no" ;
        sql+= " where UPPER(maker) like UPPER(?)" ;
        sql+=" GROUP BY g.no, g.title, g.price, g.maker, g.releasedate, g.rate" ;
        sql+=" ORDER BY g.no";
        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String put = "%" +maker.trim() +"%";
            pstmt.setString(1,put);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                MyGame mg = makeList(rs);
                mylist.add(mg);
            }
        }  catch (SQLException e) {
            try{
                conn.rollback();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mylist;
    }


    public List<MyGame> genreList(String search) {
        List<MyGame>genre=new ArrayList<>();
        String sql = "SELECT distinct g.no,g.title ,g.price,g.maker,listagg(gg.genre,',')" ;
        sql+= " WITHIN GROUP (ORDER BY gg.genre) AS genres,g.To_char(g.releasedate,'YYYY-MM-DD') as Released_date,g.rate" ;
        sql+= " FROM MyGame g" ;
        sql+=" left JOIN GameGenre gg ON g.no = gg.MyGame_no" ;
        sql+= " where UPPER(gg.genre) like UPPER(?)" ;
        sql+=" GROUP BY g.no, g.title, g.price, g.maker, g.releasedate, g.rate" ;
        sql+=" ORDER BY g.no";
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String put ="%"+search+"%";
            pstmt.setString(1,put);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                MyGame bean = makeList(rs);
                genre.add(bean);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (Exception e) {
          e.printStackTrace();
        }

        return genre;
    }

    public Boolean SamVer(String id){//verify title in MyGame
        String sql = "select count(*) as cnt from MyGame where UPPER(title)=UPPER(?)";

        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int a = rs.getInt("cnt");
                if (a == 1) {
                    return true;
                }
            }
        }catch (SQLException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;

    }
    public Boolean VerGenre(String genre){//verify genre in MyGame
        String sql = "select count(*) as cnt from GameGenre where UPPER(genre) like UPPER(?)";
        int a=-1;
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String put = "%"+genre+"%";
            pstmt.setString(1,put);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                a = rs.getInt("cnt");

            }
            if (a > 0) {
                return true;
            }
        }catch (SQLException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;

    }
    public List<MyGame> viewTitle() {
        String sql = "select title from MyGame ";
        List<MyGame> justtitle = new ArrayList<>();
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MyGame bean = new MyGame();
                bean.setTitle(rs.getString("title"));
                justtitle.add(bean);
            }
        }catch (SQLException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  justtitle;
    }


    public MyGame makeList(ResultSet rs){
        MyGame ml=new MyGame();
        try{
            ml.setNo(rs.getInt("no"));
            ml.setTitle(rs.getString("title"));
            ml.setPrice(rs.getDouble("price"));
            ml.setMaker(rs.getString("maker"));
            ml.setGenres(rs.getString("genres"));
            ml.setreleasedate(rs.getString("Released_date"));
            ml.setRate(rs.getInt("rate"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ml;
    }

    public void show(List<MyGame> list){
        for(MyGame s : list){
            int no = s.getNo();
            String title = s.getTitle();
            double price =  s.getPrice();
            String maker =  s.getMaker();
            String genres = s.getGenres();
            String releasedate = s.getreleasedate();
            int rate = s.getRate();
            String macut=fixLength(maker,30);
            System.out.printf("%-5d %-45s %-8.2f %-30s %-30s %-20s %-5d%n",
                    no,title,price,macut,genres,releasedate,rate
            );

        }
    }


    public int selectAll() {
        String sql = "select count(*) as cnt from MyGame";
        int maa =0;
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                maa = rs.getInt("cnt");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            return maa;
    }


    public int countList(){
        int count=-1;
        String sql = "select count(*)+100 as cnt from MYGAME ";
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                count=rs.getInt("cnt");
            }


        }catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }
 public Boolean InGenre(int no , String gnre){
        String sql = "select count(*) as cnt from GameGenre where UPPER(MyGame_no)=UPPER(?) and UPPER(genre)=UPPER(?)";
         int count = -1;
        try{
             Connection conn = super.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1,no);
             pstmt.setString(2,gnre);
             ResultSet rs = pstmt.executeQuery();
             if(rs.next()){
                 count=rs.getInt("cnt");
             }

             if(count==1){
                 return true;
             }
         }catch (SQLException e) {


         } catch (Exception e) {
             throw new RuntimeException(e);
         }

        return false;
 }
public int getNotoTitle(String name){
        String sql = "select no,title from MyGame where UPPER(title) = UPPER(?) ";
        int getnum = 0;
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                getnum = rs.getInt("no");
            }



        }catch (SQLException e) {


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getnum;
}

public String fixLength(String s, int len){
        if(s==null){return "" ;}

        return (s.length()>len)?s.substring(0,len):String.format("%-"+len+"s",s);
}


}
