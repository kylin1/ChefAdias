package dao;

import org.junit.Test;
import web.dao.BowlDao;
import web.dao.impl.BowlDaoImpl;
import web.model.po.Bowl;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class BowlDaoTest {

    private BowlDao dao = new BowlDaoImpl();

    @Test
    public void add(){
        Bowl bowl = new Bowl();
        bowl.setUser_id(3);
        bowl.setIs_return(0);
        dao.addBowl(bowl);
    }

    @Test
    public void update(){
        Bowl bowl = new Bowl();
        bowl.setUser_id(3);
        bowl.setIs_return(1);
        dao.updateBowl(bowl);
    }

    @Test
    public void get() throws ClassNotFoundException {
        List<Bowl> bowlList = dao.getBowlOfUser(1);
        System.out.println(bowlList.size());
        CheckClass.checkObject("Bowl",bowlList.get(0));
    }

}
