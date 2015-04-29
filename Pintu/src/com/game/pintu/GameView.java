package com.game.pintu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.game.config.Config;

public class GameView extends View implements DialogInterface.OnClickListener {
	private int w,h;	//游戏区域宽高
	private Bitmap img;		//整图的图片
	private Paint paint;	//至少有一个画笔
	private Bitmap[] bitmap;	//被分割的图片的数组
	private Rect[] rect;	//对应于图片的矩形块
	private int n;		//空块
	private int array[];	//块数组
	private int o;	//这里为图片分为几*几的大小 
	private int kk;
	private int startK;	//手触摸屏幕时点击的块
	
	
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;
		String str = ""+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
		String newimg[];
		newimg = new String[1];
		newimg[0] = str;
		File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }

		com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
		
		if (Config.imageId == R.id.iv1) {
			img = BitmapFactory.decodeResource(getResources(), R.drawable.icon1);
		} else if (Config.imageId == R.id.iv2) {
			img = BitmapFactory.decodeResource(getResources(), R.drawable.icon2);
		} else if (Config.imageId == R.id.iv3) {
			img = BitmapFactory.decodeResource(getResources(), R.drawable.icon3);
		} else if (Config.imageId == R.id.iv4) {
			img = BitmapFactory.decodeResource(getResources(), R.drawable.icon4);
		} else if (Config.imageId == R.id.iv5) {
			img = BitmapFactory.decodeResource(getResources(), R.drawable.icon5);
		} else if (Config.imageId == R.id.iv6) {
			img = BitmapFactory.decodeResource(getResources(), R.drawable.icon6);
		}
		
		saveMyBitmap(str,img);
		
		paint = new Paint();	
		Config.bushu = 0;	//将步数初始化为0
		
		o = 3;	//难度,也就是图形被分成了几个格子
		
		
		rect = new Rect[o*o];	
		bitmap = new Bitmap[o*o];
		array = new int[o*o];
		
		n = 0;	
		
		//初始化游戏区域宽高
		this.w = Config.metrics.widthPixels;
		this.h = Config.metrics.heightPixels/3*2;
		//初始化图片
		paint.setColor(Color.BLACK);
		
		img = Bitmap.createScaledBitmap(img, w, h, true);
		
		
		for( int y = 0; y < o; y++){
			for(int x = 0; x < o; x++){
				//分割图片
				bitmap[y*o+x] = Bitmap.createBitmap(img,x * (w / o) + 2, y * (h / o) + 2,w / o - 2,h / o - 2);
				rect[y*o+x] = new Rect(x * (w / o) + 2, y * (h / o) + 2, (x+1) * w / o - 2, (y+1) * h / o - 2 );
				//图片将要显示的位置
			}
		}
			
		  List<Integer> samples = new ArrayList<Integer>(); 
		  for (int i = 0; i < o*o; i++)  {
			  samples.add(i ); 
		  }		//将图片打乱
		  for (int i = 0; i < o*o; i++){
			  int j = (int)(Math.random()*(o*o - i));
			  array[i] = (int) samples.get(j);
			  if(array[i] == o*o-1){
				  kk = i; 
			  }
			  samples.remove(j);
		  		
		  }
		
		  
		
		
		
	}
	
		
	public void saveMyBitmap(String bitName,Bitmap mBitmap)
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
	protected void onDraw(Canvas canvas) {
		 //TODO 自动生成的方法存根
		for( int y = 0; y < o; y++){
			for(int x = 0; x < o; x++){
				int u = array[y*o+x];
				if(o * o - 1 == u){	//如果是空块,画个个黄色的
					paint.setColor(Color.YELLOW);
					canvas.drawRect(rect[y*o+x], paint);
				}else{
					canvas.drawBitmap(bitmap[u], (float)rect[y*o+x].left, (float)rect[y*o+x].top, paint);
				
				}
				
			}
		}
		
		invalidate();
		super.onDraw(canvas);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startK = getTouchXY(event);
			break;
		case MotionEvent.ACTION_UP:
			int k = getTouchXY(event);	//被点击的块
			if(k != startK){				//如果手指点下的块与抬起时的块不同,则不算作某块被点击了
				return false;
			}
			
			
			
			if(k != -1){
				
				if(isLeft(k) ){	//空块在左边
					if( array[k -1] == o * o - 1){
						array[k-1] = array[k];
						array[k] = o * o - 1;
						Config.bushu++;
						kk = k;
						isOk();
						return true;
					}
				}
				if(isRight(k) ){
					if(array[k+1] == o * o - 1){
						array[k+1] = array[k];
						array[k] = o * o - 1;
						Config.bushu++;
						kk = k;
						isOk();
						return true;
					}
				}
				if(isTop(k)){
					if(array[k-o] == o * o - 1){
						array[k-o] = array[k];
						array[k] = o * o - 1;
						Config.bushu++;
						kk = k;
						isOk();
						return true;
					}
				}
				if(isButtom(k)){
					if(array[k+o] == o * o - 1){
						array[k+o] = array[k];
						array[k] = o * o - 1;
						Config.bushu++;
						kk = k;
						isOk();
						return true;
					}
				}
			}
			break;
		}
		return true;
		
	}
	
	private int getTouchXY(MotionEvent event){	//这个方法是取出哪个块被点击了
		
		for( int y = 0; y < o; y++){
			for(int x = 0; x < o; x++){
				if(rect[y*o+x].contains((int)event.getX(), (int)event.getY())){
					
					return y*o+x;
				}
			}
		}
		return -1;
		
	}
	/*private int find(int n){
		for(int i = 0; i < o*o ;i++){
			if(array[i] == n){
				return i;
			}
		}
		return -1;
	}*/
	
	private boolean isLeft(int k){	//判断被点的块的左边是否超出边缘
		for(int i = 0; i < o ;i++){
			if( k == o * i){
				return false;
			}
		}
		return true;	
	}
	private boolean isRight(int k){	
		for(int i = 0; i < o ;i++){
			if( k == o * (i+1)-1){
				return false;
			}
		}
		return true;	
	}
	private boolean isTop(int k){	
		for(int i = 0; i < o ;i++){
			if( k == i){
				return false;
			}
		}
		return true;	
	}
	private boolean isButtom(int k){	
		for(int i = 0; i < o ;i++){
			if( k == o * o - i-1){
				return false;
			}
		}
		return true;	
	}
	
	private boolean isOk(){
		for( int i = 0; i < array.length-1; i++){
			if(array[i+1]-array[ i] != 1 ){
				return false;	
			}	
		}
		new AlertDialog.Builder(getContext())
		.setTitle("完成")
		.setMessage("用时:"+(int)((System.currentTimeMillis()-Config.startTime)/1000))
		.setPositiveButton("确定", this)
		.setNegativeButton("取消", this)
		.show();
		Toast.makeText(getContext(), "ok", 0).show();
		return true;
	}




	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO 自动生成的方法存根
		
	}

}
