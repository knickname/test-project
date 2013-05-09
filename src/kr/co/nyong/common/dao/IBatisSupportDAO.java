package kr.co.nyong.common.dao;

import org.springframework.context.MessageSource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class IBatisSupportDAO extends SqlMapClientDaoSupport{
	protected MessageSource messageSource;
	
	public void setMessageSource(MessageSource messageSource){
		this.messageSource=messageSource;
	}
	
}