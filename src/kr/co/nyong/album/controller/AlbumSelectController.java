package kr.co.nyong.album.controller;

import java.util.List;

import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.PlatformData;

import kr.co.nyong.album.service.AlbumService;
import kr.co.nyong.common.controller.AbstractMiPlatformController;
import kr.co.nyong.common.to.BaseBean;
import kr.co.nyong.common.to.ConditionBean;

public class AlbumSelectController extends AbstractMiPlatformController{
	private AlbumService albumService;

	public void setAlbumService(AlbumService albumService) {
		this.albumService = albumService;
	}

	@Override
	public void doExecute(PlatformData inData, PlatformData outData)
			throws Exception {
		// TODO Auto-generated method stub
		String title = inData.getVariable("title").getValue().getObject().toString();
		String name = inData.getVariable("name").getValue().getObject().toString();
		
		ConditionBean condition = new ConditionBean();
		condition.setTitle(title);
		condition.setName(name);
		
		DatasetList outDataList = outData.getDatasetList();
		
		List<BaseBean> albumList = albumService.selectAlbumList(condition);
		
	}
	
}
