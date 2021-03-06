package com.binfen.im.gamead.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.binfen.im.gamead.R;

public class LoadImageActivity extends Activity implements OnCheckedChangeListener {
	

	LoadImageActivity(String gameImagePath)
	{
		String imgName[];
		imgName = new String[6];
		int num = 0;

		imgName[0] = Integer.toString(R.id.iv1);
		imgName[1] = Integer.toString(R.id.iv2);
		imgName[2] = Integer.toString(R.id.iv3);
		imgName[3] = Integer.toString(R.id.iv4);
		imgName[4] = Integer.toString(R.id.iv5);
		imgName[5] = Integer.toString(R.id.iv6);
		
		for(int i=0;i<6;i++)
		{
			if(imgName[i].equals(gameImagePath))//相等为true
			{
				num = i+1;
				break;
			}
		}
		int icon = 0;
		if(num == 1)
		{
		    icon = R.drawable.icon1;
		}
		else if(num == 2)
		{
		    icon = R.drawable.icon2;
		}
		else if(num == 3)
		{
		    icon = R.drawable.icon3;
		}
		else if(num == 4)
		{
		    icon = R.drawable.icon4;
		}
		else if(num == 5)
		{
		    icon = R.drawable.icon5;
		}
		else if(num == 6)
		{
		    icon = R.drawable.icon6;
		}
		else
		{
			icon = 2131427498;
		}
		
		
		String PnameImg = imgName[num-1];
		
		String newimg[];
		newimg = new String[1];
		newimg[0] = PnameImg;//str
		File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }

		com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
		Bitmap img = BitmapFactory.decodeResource(this.getResources(), icon);
		
		//saveMyBitmapxxh(nameImg,img);
		saveMyBitmapxxh(PnameImg,img);//用于定制进读取
		saveMyBitmapxxh("offical",img);//用于定制进读取
	}
	
	public void saveMyBitmapxxh(String bitName,Bitmap mBitmap)
	{
		File f = new File("/mnt/sdcard/gameimage/" + bitName + ".jpg");
	    try {
		   f.createNewFile();
		}catch (IOException e) {
		   // TODO Auto-generated catch block
		}
		FileOutputStream fOut = null;
		try {
		   fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
		   e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
		    fOut.flush();
		} catch (IOException e) {
		e.printStackTrace();
		}
	    try {
		   fOut.close();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}	
	
	

}
