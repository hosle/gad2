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
	private int w,h;	//��Ϸ������
	private Bitmap img;		//��ͼ��ͼƬ
	private Paint paint;	//������һ������
	private Bitmap[] bitmap;	//���ָ��ͼƬ������
	private Rect[] rect;	//��Ӧ��ͼƬ�ľ��ο�
	private int n;		//�տ�
	private int array[];	//������
	private int o;	//����ΪͼƬ��Ϊ��*���Ĵ�С 
	private int kk;
	private int startK;	//�ִ�����Ļʱ����Ŀ�
	
	
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
		t.setToNow(); // ȡ��ϵͳʱ�䡣
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
		Config.bushu = 0;	//��������ʼ��Ϊ0
		o = Config.nandu;	//�Ѷ�,Ҳ����ͼ�α��ֳ��˼�������
		
		
		rect = new Rect[o*o];	
		bitmap = new Bitmap[o*o];
		array = new int[o*o];
		
		n = 0;	
		
		//��ʼ����Ϸ������
		this.w = Config.metrics.widthPixels;
		this.h = Config.metrics.heightPixels/3*2;
		//��ʼ��ͼƬ
		paint.setColor(Color.BLACK);
		
		img = Bitmap.createScaledBitmap(img, w, h, true);
		
		
		for( int y = 0; y < o; y++){
			for(int x = 0; x < o; x++){
				//�ָ�ͼƬ
				bitmap[y*o+x] = Bitmap.createBitmap(img,x * (w / o) + 2, y * (h / o) + 2,w / o - 2,h / o - 2);
				rect[y*o+x] = new Rect(x * (w / o) + 2, y * (h / o) + 2, (x+1) * w / o - 2, (y+1) * h / o - 2 );
				//ͼƬ��Ҫ��ʾ��λ��
			}
		}
			
		  List<Integer> samples = new ArrayList<Integer>(); 
		  for (int i = 0; i < o*o; i++)  {
			  samples.add(i ); 
		  }		//��ͼƬ����
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
		 //TODO �Զ����ɵķ������
		for( int y = 0; y < o; y++){
			for(int x = 0; x < o; x++){
				int u = array[y*o+x];
				if(o * o - 1 == u){	//����ǿտ�,��������ɫ��
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
		// TODO �Զ����ɵķ������
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startK = getTouchXY(event);
			break;
		case MotionEvent.ACTION_UP:
			int k = getTouchXY(event);	//������Ŀ�
			if(k != startK){				//�����ָ���µĿ���̧��ʱ�Ŀ鲻ͬ,������ĳ�鱻�����
				return false;
			}
			
			
			
			if(k != -1){
				
				if(isLeft(k) ){	//�տ������
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
	
	private int getTouchXY(MotionEvent event){	//���������ȡ���ĸ��鱻�����
		
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
	
	private boolean isLeft(int k){	//�жϱ���Ŀ������Ƿ񳬳���Ե
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
		.setTitle("���")
		.setMessage("��ʱ:"+(int)((System.currentTimeMillis()-Config.startTime)/1000))
		.setPositiveButton("ȷ��", this)
		.setNegativeButton("ȡ��", this)
		.show();
		Toast.makeText(getContext(), "ok", 0).show();
		return true;
	}




	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO �Զ����ɵķ������
		
	}

}
