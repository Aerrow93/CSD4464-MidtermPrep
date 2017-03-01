/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermprep;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0587637
 */
@Named
@ApplicationScoped
public class Guestbook {
    
    private List<GuestbookPost> guestbookPosts = new ArrayList<>();
    private Guestbook currentPost;

    public Guestbook() {
        //currentPost = new Guestbook(-1,-1,"",null,"");
        refresh();
    }

    public List<GuestbookPost> getGuestbookPosts() {
        return guestbookPosts;
    }

    public Guestbook getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(Guestbook currentPost) {
        this.currentPost = currentPost;
    }
    
    private void refresh() {
        try {
            guestbookPosts.clear();
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guestbook");
            while (rs.next()) {
                GuestbookPost gbp = new GuestbookPost(
                        rs.getInt("id"),
                        rs.getString("author"),
                        rs.getString("message")
                );
                guestbookPosts.add(gbp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Guestbook.class.getName()).log(Level.SEVERE, null, ex);
            guestbookPosts.clear();
        }
    }
    
    public GuestbookPost getPostById(int id){
        for (GuestbookPost g: guestbookPosts){
            if (g.getId() == id){
                return g;
            }
        }
        return null;
    }
    
    public String addPost(){
        return "editPost";
    }
    
    public String editPost(){
        return "editPost";
    }
    
//    public String cancelPost() {
//        int id = currentPost.getId();
//        refresh();
//        currentPost = getPostById(id);
//        return "viewPost";
//    }
}
