package com.jeeplus.modules.audio.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.WXUser;

/**
 * 微信公众号网页授权用户信息DAO接口
 * @author stephen
 * @version 2020-12-23
 */
@MyBatisDao
public interface WXUserDao extends CrudDao<WXUser> {

}