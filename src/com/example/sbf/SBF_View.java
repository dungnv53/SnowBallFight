package com.example.sbf;

import java.util.Random;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


class SBF_View extends SurfaceView implements SurfaceHolder.Callback {
	/**
	 * View that draws, takes keystrokes, etc. for a simple LL game.
	 *
	 * Has a mode which RUNNING, PAUSED, etc. Has a x, y, dx, dy, ... capturing the
	 * current ship physics. All x/y etc. are measured with (0,0) at the lower left.
	 * LOWER LEFT! cho k fai uper left ?
	 * lieu j2me co the k?
	 * nhung hinh nhu OpenGL la uperLeft ?
	 * updatePhysics() advances the physics based on realtime.
	 *  draw() renders the
	 * ship, and does an invalidate() to prompt another draw() as soon as possible
	 * by the system.
	 */
//	bo tam static
     class SBFThread extends Thread {
    	/** mp de choi nhac
    	 * co ve MP cua android ho tro rat nhieu dinh dang khac nhau
    	 */
    	
    	/** 6 image de gia lap animation cho hero
    	 * thc ra can nhieu hon nhung add sau
    	 */
    	private Bitmap[] mHeroMoving = new Bitmap[7];
    	
    	/**
    	 * 5 img cho Enemy
    	 */
    	private Bitmap[] mEnemy = new Bitmap[5];
    	
    	private Bitmap[] mBoss = new Bitmap[5];
    	/**
    	 * chua dung 
    	 */
    	private Bitmap[] item = new Bitmap[5];
    	/**
    	 * vi tri snow cua hero 
    	 */
    	private Bitmap snow_h;
    	/**
    	 * bong cho snow 
    	 */
    	private Bitmap snow_shadow;
    	/**
    	 * Moi chi dung 1 trong cac img Special
    	 */
    	private Bitmap[] img_Special = new Bitmap[4];
    	private Bitmap img_sp1;
    	private Bitmap img_sp2;
    	private Bitmap img_sp3;
    	/**
    	 * dung luu 3 kieu spec tuy vao mana
    	 * Dung ra fai dung RMS nhung o day tam test cai int nay da.
    	 * 
    	 */
    	private int use_special = 0;	// integer for store 3 type of special follow the
    	// mana degree
    	/**
    	 * mRes dung load resources
    	 */
    	Resources mRes;
    	
    	/**
    	 * = 1
    	 */
        public static final int STATE_LOSE = 1;
        public static final int STATE_PAUSE = 2;
        public static final int STATE_READY = 3;
        public static final int STATE_RUNNING = 4;
        public static final int STATE_WIN = 5;
        
//        private Bitmap[] mHero_moving = new Bitmap[4];
        
        private static final String KEY_X = "h_x";
        private static final String KEY_Y = "h_y";

        /*
         * Member (state) fields
         */
        /** The drawable to use as the background of the animation canvas */
        private Bitmap mBackgroundImage;

        /**
         * Current height of the surface/canvas.
         */
        private int mCanvasHeight = 1;

        /**
         * Current width of the surface/canvas.
         */
        private int mCanvasWidth = 1;

        /** Message handler used by thread to interact with TextView */
        private  Handler mHandler;

        /** Pixel height of lander image. */
        private  int mLanderHeight;

        /** What to draw for the title game in its normal state */
        private Bitmap mTitleImage;

        /** Pixel width of lander image. */
        private int mLanderWidth;
        
        
//        DacMedia media = new DacMedia();
//	    try {
//			media.playLocalAudio_UsingDescriptor();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        /** Used to figure out elapsed time between frames */
        private  long mLastTime;

        /** The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN */
        private  int mMode;

        /** Indicate whether the surface has been created & is ready to draw */
        private boolean mRun = true;

        /** Handle to the surface manager object we interact with */
        private  SurfaceHolder mSurfaceHolder;

        /** X of hero center.
         * Muon dung mCanvasHeight , width de chay dc nhieu size cua man hinh nhung
         * co bug nen dung gan luon. 
         * */
        private  int h_x = 100;

        /** Y of hero center. */
        private  int h_y = 190;
        
        /**
         * dung cho animate hero
         */
        private int mHeroIndex = 0;
        private int m_snow_fire = 0;
        
        private int e_snow_fired = 0;
        /**
         * start h_y 250 (gan bottom)
         */
        int snow_h_y = 250;
        int h_hp = 84; // hp cua hero
        /**
         * random, ko biet xai chua
         */
		private Random rnd = new Random();
		/**
		 * = 600 ms , 
		 */
		public static final int GAME_THREAD_DELAY = 600;
		  /**
		   * donald
		   * 
		   */
		  private Donald donald;
		  /**
		   * Mang donald luie
		   */
		  private Donald[] luie = new Donald[3];
		  /**
		   * bomb
		   */
		  Bomb bomb;
		  /**
		   * Vi tri bat dau nem cua boss.
		   */
		  private int test_snow_h_y = 100;

		private Bitmap allclear;

		private int[] test_snow_e_y = new int[] {70, 70, 70};

		private int win_sound_flag = 0;

		private int lose_flag_sound = 0;

		private Bitmap v;
		
//		test_snow_e_y[0] = 70;
//		for(int zz = 0; zz <= 3; zz ++) {
//        	test_snow_e_y[zz] = 70;
//        }
		
		/**
		 * Con can them 1 array luu vi tri attack cho luie[3]
		 */
		  
//		    private RefreshHandler mRedrawHandler = new RefreshHandler();

//		    class RefreshHandler extends Handler {
//
//		        @Override
//		        public void handleMessage(Message msg) {
//		            SBF_View.this.update();
//		            SBF_View.this.invalidate();
//		        }
//
//		        public void sleep(long delayMillis) {
//		        	this.removeMessages(0);
//		            sendMessageDelayed(obtainMessage(0), delayMillis);
//		        }
//		    };
		   
        public SBFThread(SurfaceHolder surfaceHolder, Context context,
                Handler handler) {
            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;

            Resources res = context.getResources();
            
            // Doan duoi nen cho vao ham LoadImage thi hon
            // Nhu SBF vi nhu the gon gon va do fai load tat ca
            
            mHeroMoving[0] = BitmapFactory.decodeResource(res, R.drawable.hero3_0);
            mHeroMoving[1] = BitmapFactory.decodeResource(res, R.drawable.hero3_1);
            mHeroMoving[2] = BitmapFactory.decodeResource(res, R.drawable.hero3_2);
            mHeroMoving[3] = BitmapFactory.decodeResource(res, R.drawable.hero3_3);
            mHeroMoving[4] = BitmapFactory.decodeResource(res, R.drawable.hero3_4);
            mHeroMoving[5] = BitmapFactory.decodeResource(res, R.drawable.hero_vic3);
            mHeroMoving[6] = BitmapFactory.decodeResource(res, R.drawable.hero_lose3);
            
            mEnemy[0] = BitmapFactory.decodeResource(res, R.drawable.enemy00_3);
            mEnemy[1] = BitmapFactory.decodeResource(res, R.drawable.enemy01_3);
            mEnemy[2] = BitmapFactory.decodeResource(res, R.drawable.enemy02_3);
            mEnemy[3] = BitmapFactory.decodeResource(res, R.drawable.enemy03_3);
            // [4] la de tranh loi voi boundary cua mang
            // cu de sau bo dan vi chay emulator mat TIME
            mEnemy[4] = BitmapFactory.decodeResource(res, R.drawable.enemy00_3);
            
            mBoss[0] = BitmapFactory.decodeResource(res, R.drawable.boss20_3);
            mBoss[1] = BitmapFactory.decodeResource(res, R.drawable.boss21_3);
            mBoss[2] = BitmapFactory.decodeResource(res, R.drawable.boss22_3);
            mBoss[3] = BitmapFactory.decodeResource(res, R.drawable.boss23_3);
            
            mBoss[4] = BitmapFactory.decodeResource(res, R.drawable.boss20_3);
            
            snow_h = BitmapFactory.decodeResource(res, R.drawable.item3_0);
            snow_shadow = BitmapFactory.decodeResource(res, R.drawable.shadow0_3);
            
            // Mana hero cung voi ban tay = 2 PNG
            img_Special[0] = BitmapFactory.decodeResource(res, R.drawable.special0_3);
            img_Special[1] = BitmapFactory.decodeResource(res, R.drawable.special1_3);
            img_Special[2] = BitmapFactory.decodeResource(res, R.drawable.special2_3);
            
            // sp la 3 special tuy vao MANA, co the cho vo mang;
            img_sp1 = BitmapFactory.decodeResource(res, R.drawable.sp1_3);
            img_sp2 = BitmapFactory.decodeResource(res, R.drawable.sp2_3);
            img_sp3 = BitmapFactory.decodeResource(res, R.drawable.sp3_3);
            
            item[0] = BitmapFactory.decodeResource(res, R.drawable.item3_0);
            item[1] = BitmapFactory.decodeResource(res, R.drawable.item1_3);
            item[2] = BitmapFactory.decodeResource(res, R.drawable.item2_3);
            item[3] = BitmapFactory.decodeResource(res, R.drawable.item3_3);
            item[4] = BitmapFactory.decodeResource(res, R.drawable.item4_3);
            
            // cache handles to our key sprites & other drawables
            mTitleImage = BitmapFactory.decodeResource(res, R.drawable.title_bg_hori);

            // load background image as a Bitmap instead of a Drawable b/c
            // we don't need to transform it and it's faster to draw this way
            mBackgroundImage = BitmapFactory.decodeResource(res,
                    R.drawable.bck01);
            
            allclear = BitmapFactory.decodeResource(res, R.drawable.allclear);
            // Da co load thi nen co DESTROY
            
            v = BitmapFactory.decodeResource(res, R.drawable.v);

            // Use the regular lander image as the model size for all sprites
            mLanderWidth = mTitleImage.getWidth();
//            Log.d("width", " = : " + mLanderWidth);
            mLanderHeight = mTitleImage.getHeight();
            

//            mScratchRect = new RectF(0, 0, 0, 0);

//            mWinsInARow = 0;
//            h_x = mLanderWidth;
//            h_y = mLanderHeight * 2; // mat me height cua screen roai
//            mFuel = PHYS_FUEL_INIT;
//            try {
//				handler.wait(600);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            donald = new Donald(50, 50, mBoss);
            for(int kk = 0; kk < 3; kk ++) {
            	luie[kk] = new Donald (60*kk, kk*25, mEnemy);
            }
            bomb = new Bomb (50, 30, context);
            bomb.setImage(item);
            
        }

        /**
         * Starts the game, setting parameters for the current difficulty.
         */
        public  void doStart() {
            synchronized (mSurfaceHolder) {
//                h_x = mCanvasWidth / 2;
//                h_y = 2*mLanderHeight;
                mLastTime = System.currentTimeMillis() + 100;
                setState(STATE_RUNNING);
            }
        }
        /** Draw enemy follow level n STAGE of game
         1st draw enemy simple random()
         Cha ro width height the nao nen lay tam 220 = width height lay 400
         */
        public void draw_enemy(Canvas canvas) {
        	donald.act(1);
        	donald.move();
        	Paint paint = new Paint(); 
        	paint.setStyle(Style.FILL_AND_STROKE);
        	paint.setColor(Color.RED);
        	if (donald.getHp() > 0) {
        	canvas.drawBitmap(donald.getBossImage(), donald.getDonaldX(), 100, null);
        	canvas.drawRect((float) donald.getDonaldX()-5,
        			98, 
        			(float) donald.getDonaldX(), 140, paint);
        	}
//        	Log.d("donald rect ]]]]]]]]]]", donald.getDonaldX() + "yyyyyyyyyyy " + donald.getDonaldY());
        	for(int ii = 0; ii < 3; ii ++) {
        		luie[ii].act(1);
        		luie[ii].move();
//        		canvas.drawBitmap(luie[ii].getBossImage(), luie[ii].getDonaldX(), luie[ii].getDonaldY(), null);
            	
            	paint.setStyle(Style.FILL_AND_STROKE);
            	paint.setColor(Color.RED);
//            	canvas.drawRect((float) luie[ii].getDonaldX()-5,
////            			luie[ii].getDonaldY() - 2,
//            			luie[ii].getDonaldY() + (luie[ii].getHp()*4/7) - 2,
//            			(float) luie[ii].getDonaldX(), 30 + luie[ii].getDonaldY(), paint);
            	// maxhp / 32 = 56/32 = 1.75 
            	// -> hp_view = hp*7/8 - 2
            	// top = getY + hp_view
            	
            	luie[ii].setBomb(bomb);
            	luie[ii].item.dropBomb(luie[ii].getDonaldY(), 200);
//            	canvas.drawBitmap(luie[ii].item.getImage(), luie[ii].item.getX(), luie[ii].item.getY(),null);
            	if (luie[ii].getHp() > 0) {
            		canvas.drawBitmap(luie[ii].getBossImage(), luie[ii].getDonaldX(), luie[ii].getDonaldY(), null);
//            	canvas.drawBitmap(item[2], luie[ii].item.getX(), luie[ii].item.getY(),null);
//            	canvas.drawRect((float) luie[ii].getDonaldX()-5,
////            			luie[ii].getDonaldY() - 2,
//            			luie[ii].getDonaldY() + (luie[ii].getHp()*4/7) - 34,
//            			(float) luie[ii].getDonaldX(), 30 + luie[ii].getDonaldY(), paint);
//            	Log.d("luie hp top top top ++++++++++++", " " +  (luie[ii].getDonaldY() + (luie[ii].getHp()*4/7 - 2) ) );
            	}
//            	Log.d("luie huey n dwell", luie[ii].item.getX() + "|||||||||| ||||||||||| " + luie[ii].item.getY() );
        	}
        	
        }
        public int get_random(int paramInt) {
        	int i = this.rnd.nextInt() % paramInt;
    	    if (i < 0)
    	      i = -i;
    	    return i;
        }
        public int get_random1(int paramInt) {
        	int i = this.rnd.nextInt() % paramInt;
     	    if (i == 0)
     	      i = -5;
     	    return i;
        }
        /**
         * Pauses the physics update & animation.
         */
        public  void pause() {
            synchronized (mSurfaceHolder) {
                if (mMode == STATE_RUNNING) setState(STATE_PAUSE);
            }
        }

        /**
         * Restores game state from the indicated Bundle. Typically called when
         * the Activity is being restored after having been previously
         * destroyed.
         *
         * @param savedState Bundle containing the game state
         */
        public synchronized void restoreState(Bundle savedState) {
            synchronized (mSurfaceHolder) {
                setState(STATE_PAUSE);
//                h_x = savedState.getDouble(KEY_X);
//                h_y = savedState.getDouble(KEY_Y);
            }
        }

        /**
         * Ham run nay rat co the fai Chinh lai cho hop voi SBF
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
//                    	Log.d("mode", "" + mMode);
                        if (mMode == STATE_RUNNING) updatePhysics();
                        doDraw(c);
//                        boss_attack(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }  // hay bi thread end
            }
        }

        /**
         * Dump game state to the provided Bundle. Typically called when the
         * Activity is being suspended.
         *
         * @return Bundle with this view's state
         */
        public Bundle saveState(Bundle map) {
            synchronized (mSurfaceHolder) {
                if (map != null) {
                    map.putDouble(KEY_X, Double.valueOf(h_x));
                    map.putDouble(KEY_Y, Double.valueOf(h_y));
                }
            }
            return map;
        }

        /**
         * Sets if the engine is currently firing.
         * co the TH1 la setFiring = true roi load 1 PNG cho Firing
         */
        public void setFiring(boolean firing) {
            synchronized (mSurfaceHolder) {
            }
        }

        /**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         *
         * @param b true to run, false to shut down
         */
        public void setRunning(boolean b) {
            mRun = b;
        }

        /**
         * Sets the game mode. That is, whether we are running, paused, in the
         * failure state, in the victory state, etc.
         *
         * @see #setState(int, CharSequence)
         * @param mode one of the STATE_* constants
         */
        public  void setState(int mode) {
            synchronized (mSurfaceHolder) {
                setState(mode, null);
            }
        }

        /**
         * Sets the game mode. That is, whether we are running, paused, in the
         * failure state, in the victory state, etc.
         * ham nay can fai sua de hop voi SBF thay vi LL
         *
         * @param mode one of the STATE_* constants
         * @param message string to add to screen or null
         */
        public void setState(int mode, CharSequence message) {
            /*
             * This method optionally can cause a text message to be displayed
             * to the user when the mode changes. Since the View that actually
             * renders that text is part of the main View hierarchy and not
             * owned by this thread, we can't touch the state of that View.
             * Instead we use a Message + Handler to relay commands to the main
             * thread, which updates the user-text View.
             * 
             */
            synchronized (mSurfaceHolder) {
                mMode = mode;

                if (mMode == STATE_RUNNING) {
                    Message msg = mHandler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("text", "");
                    b.putInt("viz", View.INVISIBLE);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                } else {
                    Resources res = mContext.getResources();
                    CharSequence str = "";
                    if (mMode == STATE_READY)
                        str = res.getText(R.string.mode_ready);
                    else if (mMode == STATE_PAUSE)
                        str = res.getText(R.string.mode_pause);
                    else if (mMode == STATE_LOSE)
                        str = res.getText(R.string.mode_lose);
                    else if (mMode == STATE_WIN)
                        str = res.getString(R.string.mode_win_prefix)
                                + "Acquired: "
                                + res.getString(R.string.mode_win_suffix);

                    if (message != null) {
                        str = message + "\n" + str;
                    }

                    if (mMode == STATE_LOSE) 
                    {
                    	str = "\n\n" + "You lose!" + "\n Good job!";
                    }

                    Message msg = mHandler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("text", str.toString());
                    b.putInt("viz", View.VISIBLE);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                }
            }
        }

        /** Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;

                // don't forget to resize the background image
                mBackgroundImage = Bitmap.createScaledBitmap(
                        mBackgroundImage, width, height, true);
            }
            // Tai sao de bck co bot len tren 60 thi hero lai gay ra VET BONG 
            // khi moving / nen den do height chua ra ?
        }

        /**
         * Resumes from a pause.
         */
        public void unpause() {
            // Move the real time clock up to now
            synchronized (mSurfaceHolder) {
                mLastTime = System.currentTimeMillis() + 100;
            }
            setState(STATE_RUNNING);
        }

        /**
         * Handles a key-down event.
         *
         * @param keyCode the key that was pressed
         * @param msg the original event object
         * @return true
         */
        boolean doKeyDown(int keyCode, KeyEvent msg) {
            synchronized (mSurfaceHolder) {
            	
                boolean okStart = false;
                if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_O) 
                	okStart  = true;
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) 
                    okStart  = true;
                if (keyCode == KeyEvent.KEYCODE_S) 
                    okStart  = true;

                if ( (okStart) && (mMode == STATE_READY || mMode == STATE_LOSE || mMode == STATE_WIN) ) {
                    // ready-to-start -> start
                    doStart();
                    return true;
                } else if (mMode == STATE_PAUSE && okStart) {		// bo && okStart
                    // paused -> running
                    unpause();
                    return true;
                
                } else if (mMode == STATE_RUNNING) {
                    // center/space -> fire
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                            || keyCode == KeyEvent.KEYCODE_SPACE 
                            || keyCode == KeyEvent.KEYCODE_O) {
                    	// O day co cach nao TEST previous KEY_DPAD ?
                    	// lieu fai them bien ko ?
                    	// chinh mHeroIndex da luu state cua key roai ?
//                        setFiring(true);
                    	if ( (mHeroIndex == 3) || (mHeroIndex == 2))
                    	{
                    		mHeroIndex = 4;
                    	}
                    	if(mHeroIndex == 4)
                    		mHeroIndex = 3;
                    	if(mHeroIndex == 1)
                    		mHeroIndex = 3;
                    	m_snow_fire = 10;	// De cho snow bay
                    	
//                    	make_attack()
                    	// pause 1 chut ko thi cha thay gi ngoai index = 1
                    	mHeroIndex = 2; // cho ve 0 hay hon 1 vi thuc te nem xong thi tay fai ve sau de nem tiep
                        return true;
                        // left/q -> left
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT 
                    		|| keyCode == KeyEvent.KEYCODE_A) {
                        if (h_x > 13)
                        {
                        	h_x -= 12;
//                        	Log.d("PAD LEFT ", " hero x --------: " + h_x + " hero y ^^^^^" + h_y);
                        	if(mHeroIndex < 2)
                        		mHeroIndex ++;
                        	else
                        	{
                        		mHeroIndex = 0;
                        		mHeroIndex ++;
                        	}
                        	if (mHeroIndex == 2)
                        		mHeroIndex = 0;
                        	else if (mHeroIndex == 0)
                        		mHeroIndex = 1;
                        	//Log.d("Index test", " = " + mHeroIndex);
                        	// nen goi ham hero_move()
                        }
                        return true;
                        // right/w -> right
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_E) {
                        if (h_x < 220)
                        {
                        	h_x += 12;
//                        	Log.d("PAD RIGHT ", " hero x --------: " + h_x + " hero y ^^^^^" + h_y);
                        	if (mHeroIndex < 2)
                        	{
                        		mHeroIndex ++;
                        		if (mHeroIndex == 2)
                            		mHeroIndex = 0;
                            	else if (mHeroIndex == 0)
                            		mHeroIndex = 1;
                        	}
                        	else 
                        	{
                        		mHeroIndex = 1;
                        		mHeroIndex ++;
                        		if (mHeroIndex == 2)
                        		mHeroIndex = 0;
                        	else if (mHeroIndex == 0)
                        		mHeroIndex = 1;
                        	}
                        	
                        	// hero_move(keycode)
                        	//Log.d("Index test", " = " + mHeroIndex);
                        }
                        return true;
                        // up -> pause
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_P) {
                        pause();
//                    	mHeroIndex = 2;=
//                    	Log.d("PAD UP ", " mHero index **** : " + mHeroIndex);
//                    	if (mHeroIndex < 6)
//                    		mHeroIndex ++;
//                    	else {
//                    		mHeroIndex = 2;
//                    		mHeroIndex ++;
//                    	}
//                    	if(mHeroIndex == 4) // nhu vay dau "<" giup loai bo cac th index tang bat thuong
//                    		mHeroIndex = 2;

//                    	Canvas c = null;
//                        make_attack();
                    	// Rat nan vi bug o ham make_attack()
                    	// neu ko goi dc ham khac thi gay
                    	// coi Game SNAKE roai nhung no ko co vd gi khi goi ham init va UPDATE
                        return true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN ) {
                    	// make_effect()
//                    	use_special(null);
                    	// thay vi goi null -> bug thi change var -> change effect
                    	use_special = 1; // khi nao chay OK thi them if-else de tuy mana
                    	return true;
                    }
                    /*
                     * Cac menu va OPTIONS khac danh fai cho vao menu cua android 
                     * Vi cha con fim nao tren Emulator dung dc ca;
                     * */
                  } // end mMode = STATE_RUNNING

                return false;
            }
        }

        /**
         * Handles a key-up event.
         *
         * @param keyCode the key that was pressed
         * @param msg the original event object
         * @return true if the key was handled and consumed, or else false
         * 
         * Cai key nay rat khac nhau tuy thuoc vao key nao da nhan truoc do
         * hay trong SCREEN nao, do vay VD se kho hon
         */
        boolean doKeyUp(int keyCode, KeyEvent msg) {
            boolean handled = false;

            synchronized (mSurfaceHolder) {
                if (mMode == STATE_RUNNING) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                            || keyCode == KeyEvent.KEYCODE_SPACE) {
//                        setFiring(false);
                        // qua kho vi neu de ok la fire lan vo tuyet
                        // thi ko ro xu ly ra sao
                        // con de UP lam attack thi ok ?
                    	// cung make_attack(false)
                    	// LL la ban lien tuc con SBF dau co the nen khoi can ?
                        handled = true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                            || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        handled = true;
                    }
                    else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    	handled = true;
                    }
                }
            }

            return handled;
        }

        public void dropBombing(Canvas canvas, int targetX, int targetY) {
        	do {
        		canvas.drawBitmap(bomb.getImage(0), targetX, targetY+=5, null);
//        		Log.d("drop bomb", "in the minarets");
        	}
        	while (targetY <= 190); 
        }
        /**
         * Draws the hero, enemy, and background to the provided
         * Canvas.
         * Mau cua enemy van chua chuan: Hp giam lien tuc nhu kieu loop neu trung dan nhieu
         * GameState reload -> hp cua enemy cung full trog khi hp dang < full.
         * De test -> truoc tien giam nhanh hp de test win state
         */
        public void doDraw(Canvas canvas) {
//        	mMode = STATE_WIN;
        	if (mMode == STATE_RUNNING) {
//        	canvas.drawBitmap(mHeroMoving[mHeroIndex], h_x, h_y, null);
            // Draw the background image. Operations on the Canvas accumulate
            // so this is like clearing the screen.
//        	dropBombing(canvas, (int) h_x, (int) h_y);
        	boss_attack(canvas);
            canvas.drawBitmap(mBackgroundImage, 0, 0, null);
//            draw_enemy(canvas);
            // do ham draw_e bi goi lai nen enemy chet lai hoi nen fai tat ham nay di va 
            // ve truc tiep trong doDraw
//            playTitleSound();
//            stopSound();
            donald.act(1);
        	donald.move();
        	Paint paint = new Paint(); 
        	paint.setStyle(Style.FILL_AND_STROKE);
        	paint.setColor(Color.RED);
        	// In Running mode
        
        	canvas.drawBitmap(mHeroMoving[mHeroIndex], h_x, h_y, null);
        	
        	if (donald.getHp() > 0) {
        	canvas.drawBitmap(donald.getBossImage(), donald.getDonaldX(), 85, null);
        	canvas.drawRect((float) donald.getDonaldX()-5,
//        			98, 
        			126 - (donald.getHp()*9/11),
        			(float) donald.getDonaldX(), 126, paint); // 140
        	
//        	Log.d("donald dddddddddddd hppppp hphphphp " ," ===== " + donald.getHp());
        	}
//        	Log.d("donald rect ]]]]]]]]]]", donald.getDonaldX() + "yyyyyyyyyyy " + donald.getDonaldY());
        	for(int ii = 0; ii < 3; ii ++) {
        		luie[ii].act(1);
        		luie[ii].move();
        		int luie_temp_x = luie[ii].getDonaldX();
        		int luie_temp_y = luie[ii].getDonaldY();
//        		Log.d("Vi tri luii" + ii + "", luie_temp_x + " va Y:= " + luie_temp_y);
//        		canvas.drawBitmap(luie[ii].getBossImage(), luie[ii].getDonaldX(), luie[ii].getDonaldY(), null);
            	
            	paint.setStyle(Style.FILL_AND_STROKE);
            	paint.setColor(Color.RED);
//            	canvas.drawRect((float) luie[ii].getDonaldX()-5,
////            			luie[ii].getDonaldY() - 2,
//            			luie[ii].getDonaldY() + (luie[ii].getHp()*4/7) - 2,
//            			(float) luie[ii].getDonaldX(), 30 + luie[ii].getDonaldY(), paint);
            	// maxhp / 32 = 56/32 = 1.75 
            	// -> hp_view = hp*7/8 - 2
            	// top = getY + hp_view
            	
            	luie[ii].setBomb(bomb);
            	luie[ii].item.dropBomb(luie[ii].getDonaldY(), 200);
//            	if(luie[ii].getBomb().isDestroyed()) {
//            		dropBombing(canvas, luie[ii].getDonaldX(), luie[ii].getBomb().getY());
//            	}
            	int luie_x = luie[ii].getDonaldX();
//            	e_attack(canvas, luie_x, 200);
            	
            	
            	/**
            	 * Doan nay test viec ve snow bay cheo screen
            	 */
//	   			 test_snow_h_y += 6;
//	     		if (test_snow_h_y >= 205) 
//	     				test_snow_h_y = 80;
//	//     		test_snow_h_y += 6;
//	     				
//	     		Log.d(" ", test_snow_h_y + " eeeeeeeeeeeeeeee");
//	     		canvas.drawBitmap(snow_h, donald.getDonaldX(), test_snow_h_y-22, null);
//	     		// 22 la distance giua snow va shadow
//	     		canvas.drawBitmap(snow_shadow, donald.getDonaldX(), test_snow_h_y, null);
//            	int rand_h_x = get_random(10);
//            	int enm_x = luie[ii].getDonaldX();
//            	int enm_y = luie[ii].getDonaldY();
//            	int h_temp_x = (int) (h_x + rand_h_x);
//            	int h_temp_y = (int)h_y;
////            	Log.d("fire ->>>>>>>>", enm_x + " y " + enm_y + " hero " + h_temp_x + " yy " + h_temp_y);
//            	e_attack_ai(canvas, enm_x, enm_y, h_temp_x, h_temp_y);
            	
            	
//            	canvas.drawBitmap(luie[ii].item.getImage(), luie[ii].item.getX(), luie[ii].item.getY(),null);
            	if (luie[ii].getHp() > 0) {
            		canvas.drawBitmap(luie[ii].getBossImage(), luie[ii].getDonaldX(), luie[ii].getDonaldY(), null);
//            	canvas.drawBitmap(item[2], luie[ii].item.getX(), luie[ii].item.getY(),null);
            	canvas.drawRect((float) luie[ii].getDonaldX()-5,
//            			luie[ii].getDonaldY() - 2,
            			luie[ii].getDonaldY() - (luie[ii].getHp()*4/7) + 30,
            			(float) luie[ii].getDonaldX(), 30 + luie[ii].getDonaldY(), paint);
//            	Log.d("luie hp top top top ++++++++++++", " " +  (luie[ii].getDonaldY() + (luie[ii].getHp()*4/7 - 2) ) );
            	}
//            	Log.d("luie huey n dwell", luie[ii].item.getX() + "|||||||||| ||||||||||| " + luie[ii].item.getY() );
        	}
        	
            // ham nay co 0 0 la left va top, co the am (-) duoc
            // chi ra vi tri cach top va left bao nhieu pixel
            // nhung anh bck van nhu nhau, van scale nhu nhau
            
//        	for (int jj = 0; jj < 6; jj ++) {
//        		if (luie[0].item.isDestroyed()) {
//        			canvas.drawBitmap(item[2], luie[0].item.getX(), 30*jj,null); // luie[ii].item.getY()
//        			Log.d("luie huey n dwell", luie[0].item.getX() + "|||||||||| ||||||||||| " + luie[0].item.getY() );
//        		}
//        	}
//            bomb.setX(luie[0].getDonaldX());
//            int mmy = bomb.getY();
//            	mmy += 5;
//            	bomb.setY(mmy);
//            	if (bomb.isDestroyed()) {
//            		dropBombing(canvas, bomb.getX(), bomb.getY());
//            	canvas.drawBitmap(item[2], bomb.getX(), mmy,null);
//            }
//            if (mmy >= 180) {
//            	bomb.setDestroyed(true);
//            }
//            else
//            	bomb.setY(mmy);
            // Dung nhu du doan viec de mmx = luie.getX la viec ko don gian
            // vi no dan toi viec item TRUOT chu ko bay nhu da dinh
            // vi cac tham so x y bi change ma chua control dc
//            Log.d("luie huey n dwell", luie[0].item.getX() + "|||||||||| ||||||||||| " + luie[0].item.getY() );
            
            int yTop = mCanvasHeight - ((int) h_y + mLanderHeight) - 25;
            // bo /2 o fan tren
            int xLeft = (int) h_x - mLanderWidth / 2;

            // Draw the speed gauge, with a two-tone effect
            canvas.save();
//            if (mMode == STATE_LOSE) {
//            	// left top right bottom
//                mCrashedImage.setBounds(xLeft, yTop, xLeft + mLanderWidth, yTop
//                        + mLanderHeight);
//               } else {
//                mLanderImage.setBounds(xLeft, yTop, xLeft + mLanderWidth, yTop
//                        + mLanderHeight);
//                mLanderImage.draw(canvas);
//            }

            // draw hero at center
        	
//            
//            e_idx_t ++;
//            if (e_idx_t == 2) 
//            	e_idx_t = 0;
            // muon repeat chi 2 img dau thi chi can cho index thanh 0 1 thoai
//            canvas.drawBitmap(mEnemy[e_idx_t], 50, 50, null);

//            b_idx ++;
//            if (b_idx == 2)
//            	b_idx = 0;
//            canvas.drawBitmap(mBoss[b_idx], 100, 100, null);
            
            // draw the hero follow key moving pad
            // o day ve ship theo vi tri asteroid nen fai thay gia tri
            // Rat co the fai viet ham moi handle hero move vi no phuc tap hon nhieu
            // vi du move n fire ...
//            canvas.drawBitmap(mHeroMoving[mShipIndex], mJetBoyX, mJetBoyY, null);

            // Neu firing thi moi ve bong snow
//            if (mLaserOn) {
//                canvas.drawBitmap(mLaserShot, mJetBoyX + mShipFlying[0].getWidth(), mJetBoyY
//                        + (mShipFlying[0].getHeight() / 2), null);
//            }
            
            /**
             * Them 1 bien boolean de biet khi nao ve ball va khi nao dung
             * vi du m_ball;
             * */
            
            int snow_h_x = h_x + 12;   // snow_h_x va y fai ko dc change du xLeft top change
            // lieu co cach nao ? kieu static co dc khong ?
            // hay fai goi ham khac ?
            if (m_snow_fire == 10) 
            {
//            	make_attack(canvas);
            	int mTop = 200;
                // bo /2 o fan tren
                int mLeft = h_x + 20; // vi snow lech ra 1 chut
//            	 int yTop =  (int)(h_y);
                 // bo /2 o fan tren
//                 int xLeft = (int) h_x;
//                 Log.d("")
             	snow_h_y -= 12;
        		if (snow_h_y < 25)
        			snow_h_y = 180;
//        		canvas.drawBitmap(snow_h, snow_h_x, snow_h_y-22, null);
        		int rand_img = get_random(4);
        		canvas.drawBitmap(bomb.getImage(2), h_x + 22, snow_h_y-22, null);
        		// 22 la distance giua snow va shadow
        		canvas.drawBitmap(snow_shadow, h_x + 22, snow_h_y, null);
        		
    	             for (int ii = 0; ii <= (mTop/12); ii ++)
    	             {
//    		            mTop -= 5;
//    		            if (mTop <= 20) 
//    		            	mTop = 200;
    	            	 // thay mLeft -> h_x
//    	             	canvas.drawBitmap(snow_h, h_x + 20, 165 - (12*ii), null);
    	             	
//    	             	canvas.drawBitmap(snow_shadow, h_x + 10, 180 -(20*ii) + 22, null);
//    		            canvas.drawBitmap(snow_h, mLeft, mTop, null);
//    	             	canvas.drawBitmap(snow_shadow, mLeft, mTop + 22, null);
    	             	// chinh mLeft va Top gay ra loi hp
    	             	// do left top da dc -> dung h_x h_y
    	             	if ((donald.getDonaldX()+20 >= mLeft) && (donald.getDonaldX() - 10) <= mLeft) {
    	             		if( (donald.getDonaldY()+10 >= (180 - 12*ii)) && (donald.getDonaldY()-20) <= (180 - 12*ii)) {
//    	             		if( (donald.getDonaldY()+10 >= mTop) && ( (donald.getDonaldY()-20) <= mTop) ) {
    	             			donald.setHp(donald.getHp() - 4);
    	             			m_snow_fire = 0;
    	             		}
    	             	}
//    	             	Log.d("Vi tri snow la ", "++++++++++++ : " + mLeft + " Y -- : " + (mTop) );
    	             	if ( (mLeft <= (luie[0].getDonaldX() + 20)) && (mLeft >= (luie[0].getDonaldX() - 10))) {
                    		if ( ((180 - 12*ii) <= (luie[0].getDonaldY() + 20)) && ((180 - 12*ii) >= (luie[0].getDonaldY() - 10))) {
                    			luie[0].setHp(luie[0].getHp() - 6);
                    			mTop = 180; // fai set lai snow_y ko thi hp mat lien tuc
//                    			Log.d("snow wwwwwwwww ", "bomb bbbbbb " + luie[0].getHp());
                    			Log.d("Enemy xxxxx000000000xxx", luie[0].getDonaldX() + " yyyy0000000yy " + luie[0].getDonaldY() + " h x " + mLeft);
                    		}
                    		m_snow_fire = 0;
                    	}
                    	if ( (mLeft <= (luie[1].getDonaldX() + 10)) && (mLeft >= (luie[1].getDonaldX() - 10))) {
                    		if ( ((180 - 12*ii) <= (luie[1].getDonaldY() + 20)) && ((180 - 12*ii) >= (luie[1].getDonaldY() - 10))) {
                    			luie[1].setHp(luie[1].getHp() - 6);
                    			mTop = 180; // fai set lai snow_y ko thi hp mat lien tuc
//                    			Log.d("snow wwwwwwwwwwwwwww ", snow_h_x + "     " + snow_h_y);
//                    			Log.d("luieeeee 11111w ", "bomb bbbbbb11111 " + luie[1].getHp() + " h_x " + h_x);
                    			Log.d("Enemy xx11111111111111", luie[1].getDonaldX() + " yyy1111111111yy " + 
                    					luie[1].getDonaldY() + " mLeft " + mLeft);
                    		}
                    		m_snow_fire = 0;
                    	}
                    	if ( (mLeft <= (luie[2].getDonaldX() + 10)) && (mLeft >= (luie[2].getDonaldX() - 10))) {
                    		if ( ((180 - 12*ii) <= (luie[2].getDonaldY() + 20)) && ((180 - 12*ii) >= (luie[2].getDonaldY() - 10))) {
                    			luie[2].setHp(luie[2].getHp() - 6);
                    			mTop = 165; // fai set lai snow_y ko thi hp mat lien tuc
//                    			Log.d("snow wwwww222222222222 ", snow_h_x + "     " + snow_h_y);
//                    			Log.d("luieeeee 2222222w ", "bomb bb222bb " + luie[2].getHp());
                    			Log.d("Enemy xx2222222222222x", luie[2].getDonaldX() + " yyy222222222yyy " +
                    					luie[2].getDonaldY() + " h_x " + mLeft);
                    		}
                    		
                    	m_snow_fire = 0;
    	             }
    	             // if (yTop < 25) ...
                   
    	             m_snow_fire = 0;
            	
    	             } // end for loop
            }    	             
//            	if(m_snow_fire == 10) {
//            		snow_h_y -= 10;
//            		if (snow_h_y < 25)
//            			snow_h_y = 200;
////            		canvas.drawBitmap(snow_h, snow_h_x, snow_h_y-22, null);
//            		int rand_img = get_random(4);
//            		canvas.drawBitmap(bomb.getImage(rand_img), h_x + 12, snow_h_y-22, null);
//            		// 22 la distance giua snow va shadow
//            		canvas.drawBitmap(snow_shadow, h_x + 12, snow_h_y, null);
//            	}
            	if (donald.getHp() <= 0) {
            		if (luie[0].getHp() <= 0 && (luie[1].getHp() <= 0) && (luie[2].getHp() <= 0)) {
            			mMode = STATE_WIN;
            		}
            	}
            	// Boss attack 
            	if (donald.getHp() >= 0) {
            		test_snow_h_y += 6;
            		if (test_snow_h_y >= 205) 
            				test_snow_h_y = 80;
//            		test_snow_h_y += 6;
            				
//            		Log.d(" ", "test_snow_h_y " + test_snow_h_y + " " + h_y +
//            				"donald x " + donald.getDonaldX() + " " + h_x);
            		canvas.drawBitmap(snow_h, donald.getDonaldX() + get_random1(10), test_snow_h_y-22, null);
            		// 22 la distance giua snow va shadow
            		canvas.drawBitmap(snow_shadow, donald.getDonaldX(), test_snow_h_y, null);
            		if ((h_x + 12) >= donald.getDonaldX() && ((h_x - 12) <= donald.getDonaldX()) 
                			&& (h_y + 12 >= test_snow_h_y) && (h_y - 12 <= test_snow_h_y)) {
                		h_hp -= 4;
                	}
            	}
            	// check snow hit hero
            	// Van chua su dung dc tinh nang isDestroyed cua Bomb Class de tranh viec
            	// hp giam lien tuc do snow ko huy.
            	
            	
            	Log.d("hero hp", "hp " + h_hp);
            	// hero die ?
            	if (h_hp <= 0) {
            		mMode = STATE_LOSE;
            	}
            		
            	// enemy attack
            	// cause can not call e_attack_ai() propertly
            	if(luie[0].getHp() > 0) {
            			test_snow_e_y [0] += 6;
            			if(test_snow_e_y[0] >= 205) 
            				test_snow_e_y[0] = 70;
            			canvas.drawBitmap(snow_h, luie[0].getDonaldX() + get_random1(8), test_snow_e_y[0]-22, null);
                		// 22 la distance giua snow va shadow
                		canvas.drawBitmap(snow_shadow, luie[0].getDonaldX(), test_snow_e_y[0], null);
                		Log.d(" ", "test_snow_h_y " + test_snow_e_y[0] + " "  + h_y +
                				 " luie 0 x " + luie[0].getDonaldX()  + " " + h_x);
                		if ((h_x - 18) <= luie[0].getDonaldX() && ((h_x + 18) >= luie[0].getDonaldX()) 
                    			&& (h_y - 18 <= test_snow_e_y[0]) && (h_y + 18 >= test_snow_e_y[0])) {
                    		h_hp -= 3;
                    	}
                    
           		}
            	if(luie[1].getHp() > 0) {
        			test_snow_e_y [1] += 6;
        			if(test_snow_e_y[1] >= 205) 
        				test_snow_e_y[1] = 70;
        			canvas.drawBitmap(snow_h, luie[1].getDonaldX() + get_random1(8), test_snow_e_y[1]-22, null);
            		// 22 la distance giua snow va shadow
            		canvas.drawBitmap(snow_shadow, luie[1].getDonaldX(), test_snow_e_y[1], null);
            		if ((h_x - 10) <= luie[1].getDonaldX() && ((h_x + 10) >= luie[1].getDonaldX()) 
                			&& (h_y - 10 <= test_snow_e_y[1]) && (h_y + 10 >= test_snow_e_y[1])) {
                		h_hp -= 3;
                	}
                	
       		   }
            	if(luie[2].getHp() > 0) {
        			test_snow_e_y [2] += 6;
        			if(test_snow_e_y[2] >= 205) 
        				test_snow_e_y[2] = 70;
        			canvas.drawBitmap(snow_h, luie[2].getDonaldX(), test_snow_e_y[2]-22, null);
            		// 22 la distance giua snow va shadow
            		canvas.drawBitmap(snow_shadow, luie[2].getDonaldX(), test_snow_e_y[2], null);
            		
            		if ((h_x - 8) <= luie[2].getDonaldX() && ((h_x + 8) >= luie[2].getDonaldX()) 
                			&& (h_y - 8 <= test_snow_e_y[2]) && (h_y + 8 >= test_snow_e_y[2])) {
                		h_hp -= 3;
                	}
       		    }
//            		Log.d("snow wwwwwwwwwwwwwww ", snow_h_x + "     " + snow_h_y);
//            		Log.d("Snow y", " =: " + snow_h_y);
//            		if (snow_h_y == 80)
//            			m_snow_fire = 0;
//            	}
//            	while (snow_h_y > 80);
//            	canvas.drawBitmap(snow_h, xLeft, yTop, null);
//            		Paint paint = new Paint();
//                    for(int ii = 0; ii < 3; ii ++) {
//                		luie[ii].act(1);
//                		luie[ii].move();
//                		canvas.drawBitmap(luie[ii].getBossImage(), luie[ii].getDonaldX(), luie[ii].getDonaldY(), null);
//                		int enemy_x = luie[ii].getDonaldX();
//                		int enemy_y = luie[ii].getDonaldY();
                    	if ( (snow_h_x <= (luie[0].getDonaldX() + 20)) && (snow_h_x >= (luie[0].getDonaldX() - 10))) {
                    		if ( (snow_h_y <= (luie[0].getDonaldY() + 30)) && (snow_h_y >= (luie[0].getDonaldY() - 10))) {
                    			luie[0].setHp(luie[0].getHp() - 8);
                    			snow_h_y = 200; // fai set lai snow_y ko thi hp mat lien tuc
//                    			Log.d("snow wwwwwwwwwwwwwww ", snow_h_x + "     " + snow_h_y);
//                    			Log.d("Enemy xxxxx000000000xxx", luie[0].getDonaldX() + " yyyy0000000yy " + luie[0].getDonaldY());
                    		}
                    	}
                    	if ( (snow_h_x <= (luie[1].getDonaldX() + 20)) && (snow_h_x >= (luie[1].getDonaldX() - 10))) {
                    		if ( (snow_h_y <= (luie[1].getDonaldY() + 30)) && (snow_h_y >= (luie[1].getDonaldY() - 10))) {
                    			luie[0].setHp(luie[1].getHp() - 12);
                    			snow_h_y = 200; // fai set lai snow_y ko thi hp mat lien tuc
//                    			Log.d("snow wwwwwwwwwwwwwww ", snow_h_x + "     " + snow_h_y);
//                    			Log.d("Enemy xx11111111111111", luie[1].getDonaldX() + " yyy1111111111yy " + luie[1].getDonaldY());
                    		}
                    	}
                    	if ( (snow_h_x <= (luie[2].getDonaldX() + 20)) && (snow_h_x >= (luie[2].getDonaldX() - 10))) {
                    		if ( (snow_h_y <= (luie[2].getDonaldY() + 30)) && (snow_h_y >= (luie[2].getDonaldY() - 10))) {
                    			luie[0].setHp(luie[2].getHp() - 12);
                    			snow_h_y = 200; // fai set lai snow_y ko thi hp mat lien tuc
//                    			Log.d("snow wwwww222222222222 ", snow_h_x + "     " + snow_h_y);
//                    			Log.d("Enemy xx2222222222222x", luie[2].getDonaldX() + " yyy222222222yyy " + luie[2].getDonaldY());
                    		}
                    	}
                    	paint.setStyle(Style.FILL_AND_STROKE);
                    	paint.setColor(Color.RED);
//                    	canvas.drawRect((float) luie[ii].getDonaldX()-5,
//                    			luie[ii].getDonaldY() - 2, 
//                    			(float) luie[ii].getDonaldX(), 30 + luie[ii].getDonaldY(), paint);
                    	// maxhp / 32 = 56/32 = 1.75 
                    	// -> hp_view = hp*7/8 - 2
                    	// top = getY + hp_view

//                    	int e_hp = luie[0].getHp();
//                    	if(e_hp <= 0) {
//                    		luie[0].set_idx(4);
//                    	}
//                    	
//                    	Log.d("hpppppppppppppppppp === = === ====",  " 0 --------- " + luie[0].getHp() );
//                    	Log.d("hpppppppppppppppppp === = === ====",  " 1 --------- " + luie[1].getHp() );
//                    	Log.d("hpppppppppppppppppp === = === ====",  " 2 --------- " + luie[2].getHp() );
                    	
//                    	luie[ii].setBomb(bomb);
//                    	luie[ii].item.dropBomb(5, 180);
//                    	if (luie[ii].getHp() > 0) {
//                    		canvas.drawBitmap(luie[ii].item.getImage(), luie[ii].item.getX(), luie[ii].item.getY(),null);
//                    	}

                    	
//                	}
            		
//            		draw special 
            		if (use_special == 1)
            		{
            			use_special(canvas);
//            			for(int ii = 0; ii <=3 ; ii ++) {
//            				luie[ii].setHp(luie[ii].getHp() - 8);
//            			}
//            			donald.setHp(donald.getHp() - 8);
            			
            		}
                canvas.save();
                
        	} // end running
        	
        	else if(mMode == STATE_LOSE) {
        		lose_flag_sound ++;
        		mpx = MediaPlayer.create(mContext, R.raw.s_lose);
        		if (lose_flag_sound  == 1) {
        			mpx.start();
        		}
        		else {
        			mpx.stop();
        		}
            	canvas.drawBitmap(mHeroMoving[6], h_x, h_y, null);
            	String text = "You lose ...";
            	Paint p = new Paint();
            	p.setColor(Color.RED);
            	canvas.drawText(text, h_x - 5, h_y - 40, p);
            	canvas.restore();
        		
            } else  if(mMode == STATE_WIN) {
            	/**
            	 * Do ham doDraw() lap lai nen play music cung lap nen nhac chong cheo
            	 * do vay chi dat 1 gia tri canh de play only 1 loop;
            	 */
            	win_sound_flag ++;
            	mpx = MediaPlayer.create(mContext, R.raw.sbf_win);
            	if (win_sound_flag == 1) {
            		mpx.start();
            	}
            	else {
            		mpx.stop();
            	}
//            	Log.d("hero x y", h_x + " va y = " + h_y);
            	//mHeroIndex = 0;
//            	canvas.drawBitmap(allclear, 50, 50, null);
            	String text = "Victory !... \n";
            	Paint p = new Paint();
            	p.setColor(Color.RED);
            	canvas.drawBitmap(allclear, 0, 30, null);
            	canvas.drawBitmap(mHeroMoving[5], 100, 190, null);
            	canvas.drawBitmap(v, 118, 183, null);
            	canvas.drawText(text, 100, 140, p);
            	String text2 = "Acquired 32 golds.";
            	canvas.drawText(text2, 80, 160, p);
            	canvas.restore();
            }
            else if (mMode == STATE_PAUSE) {
            	canvas.save();
            }
            else {
            	canvas.drawBitmap(mTitleImage, 0, 60, null);
            }
        	canvas.restore();
        }
        /** ve special bay ngang man hinh roi quay lai 1 lan roi bien mat;
        co ve ok nhung nhanh qua can fai delay DELAY
         Ko biet dung IF ELSE dc ko nhung thu thi ko thay animation ANIMATION
         */
        public void use_special(Canvas canvas) {
//        	playHitTargetSound();
        	int sp_Left = 200;
        	int sp_Top = 100;
//        		sp_Left -= 5;
        	for (int i = 0; i < 200; i ++) {
        		sp_Left --;
//        		Log.d("Use Special ", " := " + sp_Left);
        		canvas.drawBitmap(img_sp3, i, sp_Top, null);
        		// if (sp_Left >= 200) use_special = 0;
        	}
        	if (sp_Left <= 1) {
        		
        		for (int j = 200; j > 0; j --)
        		{
        			canvas.drawBitmap(img_sp2, j, sp_Top, null);
        		}
        		use_special = 0;
        	}
//        	Log.d("Spec outside loop ", " equal ? : " + sp_Left);
        }
        
        /** draw snow ball from hero x, y to the target
         1st we make target length is random n then use the POWER later
         */
        public void make_attack(Canvas canvas) {
//        	playFiringSound();
        	// Do LOAN h_y h_x nen lay tam 2 gia tri nay de TINH toan
        	// vi tri hero
        	int yTop = mCanvasHeight - ((int) h_y + mLanderHeight);
            // bo /2 o fan tren
            int xLeft = ((int) h_x - mLanderWidth / 2) + 20;
//        	 int yTop =  (int)(h_y);
             // bo /2 o fan tren
//             int xLeft = (int) h_x;
//             Log.d("")
	             for (int ii = 0; ii <= (yTop/20); ii ++)
	             {
//		            yTop -= 5;
	             	canvas.drawBitmap(snow_h, xLeft, 180 - (20*ii), null);
	             	canvas.drawBitmap(snow_shadow, xLeft, 180 -(20*ii) + 22, null);
//	             	Log.d("Vi tri snow la ", "++++++++++++ : " + xLeft + " Y -- : " + yTop);
	             }
	             // if (yTop < 25) ...
	             m_snow_fire = 0;
        }
        /**
         * Nguyen nhan snow ko bay dung nhu y la do co nhieu tham chieu toi bien
         * luu state cua snow va cac gia tri khoi tao.
         * @param canvas
         * @param targetX
         * @param targetY
         */
        public void e_attack(Canvas canvas, int targetX, int targetY) {
        	// dat co flag de chi cho enemy nem tuyet khi snow da ve vi tri dich
        	// co se dc bat lai khi snow trung dich hay roi xuong
        	for (int ii = 0; ii <= (targetY/20); ii ++)
            {
//	            yTop -= 5;
            	canvas.drawBitmap(snow_h, targetX,  (20*ii), null);
            	canvas.drawBitmap(snow_shadow, targetX, (20*ii) + 22, null);
//            	Log.d("Vi tri snow la ", "++++++++++++ : " + xLeft + " Y -- : " + yTop);
            }
        	// Test draw snow gap
        	canvas.drawBitmap(snow_h, targetX += 3, targetX += 3, null);
        }
        public void e_attack_ai(Canvas canvas, int enemy_x, int enemy_y, int hero_x, int hero_y) {
        	int delta_x = (int)(Math.abs(enemy_x - hero_x));
        	int delta_y = (int)(Math.abs(enemy_y - hero_y));
        	// tinh duong cheo tao boi hero va enemy
        	int c = (int) (Math.sqrt(delta_x*delta_x + delta_y*delta_y));
        	// Tinh Delta X, Delta Y de ve duong dan cho enemy nem vao hero
        	int d_x = 12 * delta_x / c; // d_x de cho snow bay lech theo phuong ngang 
        	// = Delta * sin(alpha)
        	int d_y = 12 * delta_y / c;
        	int snow_e_xx = enemy_x;  // de ve trog canvas
        	int snow_e_yy = enemy_y;
        	if(e_snow_fired == 10) {
        		snow_e_xx += d_x;
        		snow_e_yy += d_y;
        		Log.d("fighter " , snow_e_xx + " uuuuuuuuuu " + snow_e_yy + " e snow fire " + e_snow_fired);
        		
//        			snow_e_yy = 200;
        		canvas.drawBitmap(snow_h, snow_e_xx, snow_e_yy-22, null);
        		// 22 la distance giua snow va shadow
        		canvas.drawBitmap(snow_shadow, snow_e_xx, snow_e_yy, null);
        	}
        	if ( snow_e_yy > 205 ){
//        		snow_e_yy = 50;
//        		snow_e_yy += 12;
        		e_snow_fired = 0;
        	}
        	e_snow_fired = 10;
        }
        /**
         * Them ham boss_attack() voi random target, sound play
         * test hp ... va vi tri nem
         * ko run co le do chi dc goi 1 lan
         * Neu dat vao ham run() chay chung voi doDraw() thi lam game speed nhanh gap co 2x
         * -> tam thoi cho chay trong ham doDraw()
         */
        public void boss_attack(Canvas canvas) {
        	/**
        	 * neu boss con mau thi moi dc nem.
        	 */
        	if (donald.getHp() >= 0) {
		 	    test_snow_h_y += 6;
				if (test_snow_h_y >= 205) 
						test_snow_h_y = 100;
		//		test_snow_h_y += 6;
//				playFiringSound();
//				playHitTargetSound();
//				Log.d(" ", test_snow_h_y + " eeeeeeeeeeeeeeee");
				canvas.drawBitmap(snow_h, donald.getDonaldX() + 12, test_snow_h_y-22, null);
				// 22 la distance giua snow va shadow
				canvas.drawBitmap(snow_shadow, donald.getDonaldX() + 12, test_snow_h_y, null);
        	}
        }
        /**
         * Figures the lander state (x, y, fuel, ...) based on the passage of
         * realtime. Does not invalidate(). Called at the start of draw().
         * Detects the end-of-game and sets the UI to the next state.
         */
        private void updatePhysics() {
            long now = System.currentTimeMillis();

            // Do nothing if mLastTime is in the future.
            // This allows the game-start to delay the start of the physics
            // by 100ms or whatever.
            if (mLastTime > now) return;

            double elapsed = (now - mLastTime) / 1000.0;

            double ddx = 0.0;

            mLastTime = now;

                    return;
        }
        
    }
    // end class SBF_Thread

    /** Handle to the application context, used to e.g. fetch Drawables. 
     * bo static thu nhung van bug
     * 
     * */
    private  Context mContext;

    /** Pointer to the text view to display "Paused.." etc. */
    private TextView mStatusText;

    /** The thread that actually draws the animation */
    private SBFThread thread;

    
	private MediaPlayer mpx;

    public SBF_View(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new SBFThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                mStatusText.setVisibility(m.getData().getInt("viz"));
                mStatusText.setText(m.getData().getString("text"));
            }
        });
        try {
			SBFThread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setFocusable(true); // make sure we get key events
        
//        SoundManager sm = new SoundManager(context);
//    	sm.addSound(0, R.raw.explosion);
//    	sm.playSound(0);
    	
    	mpx = MediaPlayer.create(context, R.raw.night);
    	mpx.start();
//    	mpx = MediaPlayer.create(context, R.raw.three);
//    	mpx.start();

//    	DacMedia media = new DacMedia();
//    	try {
//    		Log.d("try sound", "cooooooooooooolllllllllllll");
//			media.playLocalAudio_UsingDescriptor();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            SBF_View.this.update();
            SBF_View.this.invalidate();
        }

        public void sleep(long delayMillis) {
        	this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    
    public void update() {
          mRedrawHandler.sleep(600);
	}

	/**
     * Fetches the animation thread corresponding to this LunarView.
     *
     * @return the animation thread
     */
    public SBFThread getThread() {
        return thread;
    }

    /**
     * Standard override to get key-press events.
     * trong SBF la goi effect
     * thuc ra co nen cho tat vao 1 ham keyPressed nhu SBF ko ?
     * va lieu no co nhanh hon trog th J2ME ?
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return thread.doKeyDown(keyCode, msg);
    }

    /**
     * Standard override for key-up. We actually care about these, so we can
     * turn off the engine or stop rotating.
     * hero attack
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        return thread.doKeyUp(keyCode, msg);
    }

    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) thread.pause();
    }

    /**
     * Installs a pointer to the text view used for messages.
     */
    public void setTextView(TextView textView) {
        mStatusText = textView;
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        thread.setSurfaceSize(width, height);
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    
    public  void playTitleSound(){
    	mpx = MediaPlayer.create(mContext, R.raw.sbf_win);
	    	mpx.start();
    		mpx.setLooping(false);
    }
    public  void playFiringSound(){
    	mpx = MediaPlayer.create(mContext, R.raw.s_fire);
    	mpx.start();
    }
    public  void playHitTargetSound(){
    	mpx = MediaPlayer.create(mContext, R.raw.s_hit);
    	mpx.start();
    }
    public  void stopSound(){
    	
    	if(mpx != null){
    		mpx.stop();
    		mpx.release();
    		mpx = null;
    	}
    }
    public  void clickSound(){
    	stopSound();
    	mpx = MediaPlayer.create(mContext, R.raw.five);
    	mpx.start();
    }
}

