package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public interface SponsorDAO {
	public void saveSponsor(List<BaseBean> sponsorList) throws DAOException;
}
