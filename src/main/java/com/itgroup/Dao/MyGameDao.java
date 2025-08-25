package com.itgroup.Dao;

import com.itgroup.Bean.MyGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class MyGameDao extends SuperDao{
    public MyGameDao() {super();}


    public List<MyGame> showList() {
        List<MyGame> mylist=new ArrayList<>();
        Connection conn = null;
        String sql = "SELECT distinct g.no,g.title ,g.price,g.maker,listagg(gg.genre,',')" +
                "WITHIN GROUP (ORDER BY gg.genre) AS genres,g.releasedate,g.rate\n" +
                "FROM MyGame g\n" +
                "left JOIN GameGenre gg ON g.no = gg.Mygame_no\n" +
                "GROUP BY g.no, g.title, g.price, g.maker, g.releasedate, g.rate\n" +
                "ORDER BY g.no;";
        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                MyGame bean =makeList(rs);
                mylist.add(bean);
            }
            conn.commit();
        } catch (SQLException e) {
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
        return mylist;
    }

    public int addList(MyGame mg) {
        int add =-1;
        Connection conn = null;
        String sql = "insert into MyGame" ;
        sql+="values (?,?,?,?,?,?,?)";

        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int num=mg.getNo();
            pstmt.setInt(1,num);
            String title=mg.getTitle();
            pstmt.setString(2,title);
            double price=mg.getPrice();
            pstmt.setDouble(3,price);
            String maker=mg.getMaker();
            pstmt.setString(4,maker);
            String genres=mg.getGenres();
            pstmt.setString(5,genres);
            String releasedate=mg.getreleasedate();
            pstmt.setString(6,releasedate);
            int rate=mg.getRate();
            pstmt.setInt(7,rate);
            add = pstmt.executeUpdate();
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
        try{
            mapgame = new HashMap<>();
            conn = super.getConnection();
            String maker=null,releasedate=null;
            String title = mg.getTitle();
            int rate =0;
            double price =0;

            mapgame.put("title",title);
            if(mg.getPrice()!=0){  price= mg.getPrice();
                mapgame.put("price",price);}

            if(!mg.getMaker().isEmpty()){  maker = mg.getMaker();
            mapgame.put("maker",maker);}

            if(!mg.getreleasedate().isEmpty()){   releasedate = mg.getreleasedate();
            mapgame.put("releasedate",releasedate);}

            if(mg.getRate()!=0){    rate = mg.getRate();mapgame.put("rate",rate);    }

            int i = 0;
            for (Object s: mapgame.keySet()){
                String plus = s + "=?";
                sql.append(plus);
                if(i< mapgame.size()-1){
                    plus = " , ";
                    sql.append(plus);
                }
                i++;
            } sql.append(" where title = ? ");
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int j = 1;
            for (Object s: mapgame.values()){
                pstmt.setObject(j++,s);
            }pstmt.setString(j,title);
            update = pstmt.executeUpdate();
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
        return update;
    }

    public int removeList(String id) {
        int remove =-1;
        Connection conn = null;
        String sql = "delete from MyGame where title = ?";
        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            remove = pstmt.executeUpdate();
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
        String sql = "select *from MyGame where maker = ? ";

        try{
            conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,maker);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                MyGame mg = makeList(rs);
                mylist.add(mg);
            }
        }  catch (SQLException e) {
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



        return mylist;
    }

    public Boolean SamVer(String id){
        String sql = "select count(*) as cnt from MYGAME where title=?";

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
    public Boolean VerGenre(String genre){
        String sql = "select count(*) as cnt from MyGame where genre=?";

        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,genre);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int a = rs.getInt("cnt");
                if (a > 0) {
                    return true;
                }
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

            while (rs.next()){
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
            ml.setTitle(rs.getString("title"));
            ml.setPrice(rs.getDouble("price"));
            ml.setMaker(rs.getString("maker"));
            ml.setGenres(rs.getString("genres"));
            ml.setreleasedate(rs.getString("releasedate"));
            ml.setRate(rs.getInt("rate"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ml;
    }

    public void showList(List<MyGame> list){
        for(MyGame s : list){
            String title = s.getTitle();
            double price =  s.getPrice();
            String maker =  s.getMaker();
            String genres = s.getGenres();
            String releasedate = s.getreleasedate();
            int rate = s.getRate();


            String msg = "title : "+title + "||";
            msg +=" price : "+price+ "||";
            msg +=" maker : "+maker+ "||";
            msg +=" genres : "+genres+ "||";
            msg +=" releasedate : "+releasedate+ "||";
            msg +=" rate : "+rate+ "||";
            System.out.println(msg);

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

    public List<MyGame> genreList(String search) {
        List<MyGame>genre=new ArrayList<>();
        String sql = "SELECT g.no, g.title,gg.genre\n" +
                "FROM MyGame g\n" +
                "JOIN GameGenre gg ON g.no = gg.Mygame_no\n" +
                "WHERE gg.genre = ?;";
        try{
            Connection conn = super.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,search);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                MyGame bean = makeList(rs);
                genre.add(bean);
            }
        }catch (SQLException e) {


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return genre;
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

}
