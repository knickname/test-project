package kr.co.nyong.artist.controller;

import com.tobesoft.platform.data.PlatformData;

import kr.co.nyong.artist.service.ArtistService;
import kr.co.nyong.common.controller.AbstractMiPlatformController;

public class ArtistSaveController extends AbstractMiPlatformController{

	private ArtistService artistService;
	
	public void setArtistService(ArtistService artistService) {
		this.artistService = artistService;
	}

	@Override
	public void doExecute(PlatformData inData, PlatformData outData)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
