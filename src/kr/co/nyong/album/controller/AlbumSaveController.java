package kr.co.nyong.album.controller;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.PlatformData;

import kr.co.nyong.album.service.AlbumService;
import kr.co.nyong.common.controller.AbstractMiPlatformController;
import kr.co.nyong.common.db.AlbumImageUpload;

public class AlbumSaveController extends AbstractMiPlatformController{
	
	private AlbumService albumService;
	private AlbumImageUpload albumImageUpload;
	
	public void setAlbumService(AlbumService albumService) {
		this.albumService = albumService;
	}

	public void setAlbumImageUpload(AlbumImageUpload albumImageUpload) {
		this.albumImageUpload = albumImageUpload;
	}

	@Override
	public void doExecute(PlatformData inData, PlatformData outData)
			throws Exception {
		// TODO Auto-generated method stub
		
		Dataset albumData = inData.getDataset("ds_album");
	}

}
