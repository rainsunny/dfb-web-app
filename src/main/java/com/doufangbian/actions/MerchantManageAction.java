package com.doufangbian.actions;

import com.doufangbian.dao.Dfb_merchant_catDao;
import com.doufangbian.dao.Dfb_merchant_groupDao;
import com.doufangbian.dao.MerchantinfoDao;
import com.doufangbian.entity.Dfb_merchant_cat;
import com.doufangbian.entity.Dfb_merchant_group;
import com.doufangbian.entity.MerchantInfo;
import com.doufangbian.entity.PageModel;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User: Jie
 * Date: 12/14/13
 * Time: 4:29 PM
 */
public class MerchantManageAction extends BaseAction {

    private static Logger logger = Logger.getLogger(MerchantManageAction.class);

    @Autowired
    private Dfb_merchant_catDao dfb_merchant_catDao;
    @Autowired
    private Dfb_merchant_groupDao dfb_merchant_groupDao;
    @Autowired
    private MerchantinfoDao merchantinfoDao;

    private String keywords;
    private String pageNo;

    private PageModel<MerchantInfo> plist;
    private List<Dfb_merchant_cat> listCat;
    private List<Dfb_merchant_group> listGroup;

    public String execute() throws UnsupportedEncodingException {

        //获取当前管理员权限
        int level = 0;
        if (session.get("level") != null && session.get("level") != "") {
            level = Integer.parseInt(session.get("level") + "");
        }
        //获取当前管理员areaid
        int areaID = 0;
        if (session.get("areaID") != null && session.get("areaID") != "") {
            areaID = Integer.parseInt(session.get("areaID") + "");
        }

        logger.debug("keywords before: " + keywords);

        if (StringUtils.isNotBlank(keywords)) {
            keywords = new String(keywords.getBytes("iso-8859-1"), "utf-8");
        } else {
            keywords = "";
        }
        logger.debug("keywords after: " + keywords);

        String catId = request.getParameter("catId");
        String groupId = request.getParameter("groupId");

        String state = request.getParameter("state");
        if (state == null || state.equals("")) {
            state = "1";
        }

        //转换成int
        Integer no = 1;
        if (pageNo != null && !pageNo.equals("")) {
            no = Integer.parseInt(pageNo);
        }

        plist = merchantinfoDao.getMerchantList(state, no, level, areaID, keywords, catId, groupId);

        listCat = dfb_merchant_catDao.queryAll();

        listGroup = dfb_merchant_groupDao.queryByCatId(catId);

        return SUCCESS;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public List<Dfb_merchant_group> getListGroup() {
        return listGroup;
    }

    public List<Dfb_merchant_cat> getListCat() {
        return listCat;
    }

    public PageModel<MerchantInfo> getPlist() {
        return plist;
    }
}
