package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

public class TestFavoritesService extends AndroidTestCase {

	public void test() throws NetException, JSONException{
		
		FavoritesService favoritesService=new FavoritesService(getContext());
		List<MenuItem> items=favoritesService.getFavorites();
		items.size();
	}

}
