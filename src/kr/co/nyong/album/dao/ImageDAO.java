package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public interface ImageDAO {
	public void saveImage(List<BaseBean> imageList) throws DAOException;
}
