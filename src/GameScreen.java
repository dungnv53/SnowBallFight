//import com.samsung.util.AudioClip;
//import com.samsung.util.Vibration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

class GameScreen
  extends Canvas
  implements Runnable
{
  private static final int DEFAULT_DEM = 12;
  RecordStore recordStore = null;
  private int game_state = 0;
  private int saved_gold = 1000; // orig.10
  private int speed = 1; // orig.4
  private int game_speed = 5; // orig.17
  private Random rnd = new Random();
  private SnowBallFight SJ;
  private Thread thread = null;
  private int screen = -1;
  private boolean gameOn = true;
  private String message;
  private int m_mode = 1;
  private int s_play = 1;
  private int v_mode = 1;
//  AudioClip audioClip = null;
  private int p_mode;
  private int ani_step;
  private Image imgLogo;
  private Image imgMM;
  private Image imgBk;
  private Image imgSl;
  private Image imgPl;
  private Image imgCh;
  private Image[] imgNum;
  private Image imgBack;
  private Image[] imgHero;
  private Image[] imgEnemy;
  private Image[] imgBoss;
  private Image imgAl;
  private Image imgShadow;
  private Image imgPok;
  private Image imgPPang;
  private Image imgPPang1;
  private Image imgH_ppang;
  private Image imgSnow_g;
  private Image imgPwd;
  private Image[] imgItem;
  private Image[] imgItem_hyo;
  private Image imgVill;
  private Image imgSchool;
  private Image imgShop;
  private Image[] imgSpecial;
  private Image imgSp;
  private Image[] imgEffect;
  private Image imgVictory;
  private Image imgV;
  private Image imgHero_v;
  private Image imgLose;
  private Image imgHero_l;
  private Image imgStage_num;
  private Image[] imgStage;
  private int stage;
  private int last_stage = 31; // orig.11
  private int tmp_stage;
  private int school;
  private int state;
  private int h_x;
  private int h_y;
  private int h_idx;
  private int h_timer;
  private int h_timer_p;
  private int pw_up;
  private int mana = 0;
  private int hp;
  private int max_hp;
  private int dem;
  private int wp;
  private int snow_pw;
  private int real_snow_pw;
  private int snow_last_y;
  private int snow_top_y;
  private int snow_gap;
  private int snow_y;
  private int snow_x;
  private int ppang;
  private int gold;
  private int[] item_slot = new int[5];
  private int ppang_item;
  private int ppang_time;
  private int special;
  private int item_mode;
  private int[] item_price = new int[8];
  private int del = -1;
  private int item_a_num;
  private int item_b_num;
  private int item_c_num = 2;
  private int item_d_num;
  private int b_item;
  private int s_item;
  private int[] e_x;
  private int[] e_y;
  private int e_num;
  private int e_t_num;
  private int[] e_idx;
  private int[] e_hp;
  private int[] max_e_hp;
  private int[] e_snow_y;
  private int[] e_snow_x;
  private int[] e_snow_top;
  private int[] e_snow_gap;
  private int[] e_snow_dx;
  private int[] e_ppang_item;
  private int[] e_ppang_time;
  private int[] e_lv;
  private int[] e_fire_time;
  private int[] e_move_dir;
  private int e_time;
  private int e_dem;
  private int[] e_wp;
  private int[] e_behv;
  private int hit_idx;
  private int[] dis_count;
  private int e_boss;
  private int e_boss_idx;
  private int e_boss_x;
  private int e_boss_y;
  private int e_boss_fire_time;
  private int e_boss_hp;
  private int max_e_boss_hp;
  private int e_boss_snow_y;
  private int e_boss_snow_x;
  private int e_boss_snow_top;
  private int e_boss_snow_gap;
  private int e_boss_snow_dx;
  private int e_boss_wp;
  private int e_boss_behv;
  private int e_boss_move_dir;
  private int boss_dis_count;
  private int al;
  private int d_gauge;
  
  public GameScreen(SnowBallFight paramSnowBallFight)
  {
    this.SJ = paramSnowBallFight;
    this.item_price[0] = 5;
    this.item_price[1] = 8;
    this.item_price[2] = 8;
    this.item_price[3] = 14;
    this.item_price[4] = 6;
    this.item_price[5] = 12;
    this.item_price[6] = 10;
    this.item_price[7] = 12;
    printScore("hero", 0);
    printScore("config", 1);
    this.item_slot[0] = 3;
    this.item_slot[1] = 5;
    this.stage = this.last_stage;
  }
  
  public void addScore(String paramString, int paramInt)
  {
    try
    {
      this.recordStore = RecordStore.openRecordStore(paramString, true);
      ByteArrayOutputStream localByteArrayOutputStream;
      DataOutputStream localDataOutputStream;
      if (this.recordStore.getNumRecords() == 0)
      {
        localByteArrayOutputStream = new ByteArrayOutputStream();
        localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        try
        {
          if (paramInt == 0)
          {
            int i = this.saved_gold * 10000 + this.last_stage * 100 + this.mana;
            localDataOutputStream.writeInt(i);
          }
          else if (paramInt == 1)
          {
            localDataOutputStream.writeInt(this.speed);
          }
          byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
          this.recordStore.addRecord(arrayOfByte1, 0, arrayOfByte1.length);
        }
        catch (Exception localException2) {}
      }
      else
      {
        localByteArrayOutputStream = new ByteArrayOutputStream();
        localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        try
        {
          if (paramInt == 0)
          {
            int j = this.saved_gold * 10000 + this.last_stage * 100 + this.mana;
            localDataOutputStream.writeInt(j);
          }
          else if (paramInt == 1)
          {
            localDataOutputStream.writeInt(this.speed);
          }
          byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
          this.recordStore.setRecord(1, arrayOfByte2, 0, arrayOfByte2.length);
        }
        catch (Exception localException3) {}
      }
    }
    catch (Exception localException1) {}finally
    {
      try
      {
        this.recordStore.closeRecordStore();
      }
      catch (Exception localException6) {}
    }
  }
  
  public void printScore(String paramString, int paramInt)
  {
    try
    {
      this.recordStore = RecordStore.openRecordStore(paramString, true);
      if (this.recordStore.getNumRecords() == 0)
      {
        if (paramInt == 0)
        {
          this.last_stage = 31; // orig.11
          this.saved_gold = 100; // orig.0
          this.mana = 0;
        }
        else if (paramInt == 1)
        {
          this.speed = 1; // orig.4
        }
      }
      else {
        try
        {
          ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.recordStore.getRecord(1));
          DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);
          if (paramInt == 0)
          {
            int i = localDataInputStream.readInt();
            this.last_stage = (i % 10000 / 100);
            this.saved_gold = (i / 10000);
            this.mana = (i % 100);
          }
          else if (paramInt == 1)
          {
            this.speed = localDataInputStream.readInt();
          }
        }
        catch (Exception localException1) {}
      }
      if (this.speed == 5) {
        this.game_speed = 8;
      }
      if (this.speed == 4) {
        this.game_speed = 17;
      }
      if (this.speed == 3) {
        this.game_speed = 24;
      }
      if (this.speed == 2) {
        this.game_speed = 31;
      }
      if (this.speed == 1) {
        this.game_speed = 38;
      }
    }
    catch (Exception localException2) {}finally
    {
      try
      {
        this.recordStore.closeRecordStore();
      }
      catch (Exception localException5) {}
    }
  }
  
  public void loadImage(int paramInt)
  {
    try
    {
      if (paramInt == 2)
      {
        this.imgMM = Image.createImage("/mm.png");
        this.imgBk = Image.createImage("/bk.png");
        this.imgSl = Image.createImage("/sl.png");
        this.imgPl = Image.createImage("/play.png");
        this.imgCh = Image.createImage("/check.png");
      }
      else
      {
        int i;
        if (paramInt == 6)
        {
          this.imgHero = new Image[5];
          this.imgEnemy = new Image[4];
          this.imgItem = new Image[9];
          this.imgItem_hyo = new Image[2];
          this.imgItem_hyo[0] = Image.createImage("/hyo0.png");
          this.imgItem_hyo[1] = Image.createImage("/hyo1.png");
          for (i = 0; i < 5; i++) {
            this.imgHero[i] = Image.createImage("/hero" + i + ".png");
          }
          if (get_random(2) == 0) {
            for (int j = 0; j < 4; j++) {
              this.imgEnemy[j] = Image.createImage("/enemy0" + j + ".png");
            }
          } else {
            for (int k = 0; k < 4; k++) {
              this.imgEnemy[k] = Image.createImage("/enemy1" + k + ".png");
            }
          }
          for (int m = 0; m < 9; m++) {
            this.imgItem[m] = Image.createImage("/item" + m + ".png");
          }
          System.gc();
          this.imgSnow_g = Image.createImage("/snow_gauge.png");
          this.imgPwd = Image.createImage("/power.png");
          this.imgShadow = Image.createImage("/shadow0.png");
          this.imgPok = Image.createImage("/pok.png");
          this.imgPPang = Image.createImage("/bbang0.png");
          this.imgPPang1 = Image.createImage("/bbang1.png");
          this.imgH_ppang = Image.createImage("/h_bbang.png");
          this.imgCh = Image.createImage("/check.png");
          this.imgAl = Image.createImage("/al.png");
          this.imgEffect = new Image[2];
          this.imgEffect[0] = Image.createImage("/effect0.png");
          this.imgEffect[1] = Image.createImage("/effect1.png");
        }
        else if (paramInt == 100)
        {
          this.imgBack = Image.createImage("/back" + this.school + ".png");
        }
        else if (paramInt == 7)
        {
          this.imgBoss = new Image[4];
          for (i = 0; i < 4; i++) {
            this.imgBoss[i] = Image.createImage("/boss" + this.e_boss + i + ".png");
          }
        }
        else if (paramInt == -6)
        {
          this.imgStage = new Image[5];
          for (i = 0; i < 5; i++) {
            this.imgStage[i] = Image.createImage("/word-" + i + ".png");
          }
          this.imgStage_num = Image.createImage("/stage" + this.tmp_stage + ".png");
        }
        else if (paramInt == 8)
        {
          this.imgSpecial = new Image[3];
          for (i = 0; i < 3; i++) {
            this.imgSpecial[i] = Image.createImage("/special" + i + ".png");
          }
          this.gameOn = true;
        }
        else if (paramInt == 9)
        {
          this.imgSp = Image.createImage("/sp" + this.special + ".png");
        }
        else if (paramInt == 3)
        {
          this.imgVill = Image.createImage("/village.png");
          this.imgCh = Image.createImage("/hero_icon.png");
          this.imgSchool = Image.createImage("/school.png");
        }
        else if (paramInt == 31)
        {
          if (this.m_mode == 1) {
            this.imgShop = Image.createImage("/shop0.png");
          }
          if (this.m_mode == 0) {
            this.imgShop = Image.createImage("/shop1.png");
          }
        }
        else if (paramInt == 200)
        {
          this.imgVictory = Image.createImage("/victory.png");
          this.imgV = Image.createImage("/v.png");
          this.imgHero_v = Image.createImage("/hero-vic.png");
        }
        else if (paramInt == 65336)
        {
          this.imgLose = Image.createImage("/lose.png");
          this.imgHero_l = Image.createImage("/hero-lose.png");
        }
        else if (paramInt == 1)
        {
          this.imgNum = new Image[10];
          for (i = 0; i < 10; i++) {
            this.imgNum[i] = Image.createImage("/" + i + ".png");
          }
          this.imgLogo = Image.createImage("/logo.png");
        }
      }
    }
    catch (Exception localException) {}
  }
  
  public void destroyImage(int paramInt)
  {
    if (paramInt == 1)
    {
      this.imgLogo = null;
    }
    else if (paramInt == 2)
    {
      this.imgMM = null;
      this.imgBk = null;
      this.imgPl = null;
      this.imgSl = null;
      this.imgCh = null;
    }
    else if (paramInt == 3)
    {
      this.imgVill = null;
      this.imgCh = null;
      this.imgSchool = null;
    }
    else if (paramInt == 31)
    {
      this.imgShop = null;
    }
    else if (paramInt == 6)
    {
      this.imgHero = null;
      this.imgEnemy = null;
      this.imgItem = null;
      this.imgSnow_g = null;
      this.imgPwd = null;
      this.imgShadow = null;
      this.imgPok = null;
      this.imgPPang = null;
      this.imgPPang1 = null;
      this.imgH_ppang = null;
      this.imgItem_hyo = null;
      this.imgCh = null;
      this.imgAl = null;
      this.imgEffect = null;
    }
    else if (paramInt == 100)
    {
      this.imgBack = null;
    }
    else if (paramInt == -6)
    {
      this.imgStage = null;
      this.imgStage_num = null;
    }
    else if (paramInt == 7)
    {
      this.imgBoss = null;
    }
    else if (paramInt == 8)
    {
      this.imgSpecial = null;
    }
    else if (paramInt == 9)
    {
      this.imgSp = null;
    }
    else if (paramInt == 200)
    {
      this.imgVictory = null;
      this.imgV = null;
      this.imgHero_v = null;
    }
    else if (paramInt == 65336)
    {
      this.imgLose = null;
      this.imgHero_l = null;
    }
    System.gc();
  }
  
  public void init_game(int paramInt)
  {
    this.screen = 77;
    repaint();
    serviceRepaints();
    this.game_state = 0;
    this.p_mode = 1;
    this.h_x = 5;
    this.h_y = 8;
    this.h_idx = 0;
    this.max_hp = 106;
    this.hp = this.max_hp;
    this.wp = 0;
    this.pw_up = 0;
    this.snow_pw = 0;
    this.real_snow_pw = 0;
    this.dem = 12;
    this.ppang = 0;
    this.al = -1;
    this.ppang_time = 0;
    this.ppang_item = 0;
    make_enemy(paramInt);
    this.d_gauge = 2;
    this.screen = 6;
    this.item_mode = 0;
    loadImage(6);
    loadImage(100);
    if (this.e_boss > 0) {
      loadImage(7);
    }
    this.state = 2;
    this.ani_step = 0;
    startThread();
    this.gameOn = true;
  }
  
  public void make_e_num(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 1)
    {
      if (paramInt1 == 1)
      {
        this.e_boss = 0;
        this.e_num = 2;
      }
      else if (paramInt1 == 2)
      {
        this.e_boss = 0;
        this.e_num = 2;
      }
      else if (paramInt1 == 3)
      {
        this.e_boss = 1;
        this.e_num = 2;
      }
      else if (paramInt1 == 4)
      {
        this.e_boss = 3;
        this.e_num = 2;
      }
    }
    else if (paramInt2 == 2)
    {
      if (paramInt1 == 1)
      {
        this.e_boss = 0;
        this.e_num = 2;
      }
      else if (paramInt1 == 2)
      {
        this.e_boss = 0;
        this.e_num = 3;
      }
      else if (paramInt1 == 3)
      {
        this.e_boss = 2;
        this.e_num = 2;
      }
      else if (paramInt1 == 4)
      {
        this.e_boss = 3;
        this.e_num = 3;
      }
    }
    else if (paramInt2 == 3)
    {
      if (paramInt1 == 1)
      {
        this.e_boss = 0;
        this.e_num = 3;
      }
      else if (paramInt1 == 2)
      {
        this.e_boss = 2;
        this.e_num = 2;
      }
      else if (paramInt1 == 3)
      {
        this.e_boss = 0;
        this.e_num = 4;
      }
      else if (paramInt1 == 4)
      {
        this.e_boss = 3;
        this.e_num = 4;
      }
    }
    else if (paramInt2 == 4) {
      if (paramInt1 == 1)
      {
        this.e_boss = 1;
        this.e_num = 3;
      }
      else if (paramInt1 == 2)
      {
        this.e_boss = 2;
        this.e_num = 3;
      }
      else if (paramInt1 == 3)
      {
        this.e_boss = 3;
        this.e_num = 4;
      }
      else if (paramInt1 == 4)
      {
        this.e_boss = 4;
        this.e_num = 4;
      }
    }
    this.e_t_num = this.e_num;
    this.tmp_stage = paramInt1;
  }
  
  public void make_enemy(int paramInt)
  {
    if (paramInt < 0) {
      make_e_num(get_random(2) + 2, this.school);
    } else {
      make_e_num(this.last_stage % 10, this.school);
    }
    this.e_x = new int[this.e_num];
    this.e_y = new int[this.e_num];
    this.e_hp = new int[this.e_num];
    this.max_e_hp = new int[this.e_num];
    this.e_lv = new int[this.e_num];
    this.e_idx = new int[this.e_num];
    this.e_behv = new int[this.e_num];
    this.e_snow_y = new int[this.e_num];
    this.e_snow_x = new int[this.e_num];
    this.e_snow_gap = new int[this.e_num];
    this.e_snow_top = new int[this.e_num];
    this.e_snow_dx = new int[this.e_num];
    this.e_fire_time = new int[this.e_num];
    this.e_wp = new int[this.e_num];
    this.e_ppang_item = new int[this.e_num];
    this.e_ppang_time = new int[this.e_num];
    this.e_move_dir = new int[this.e_num];
    this.dis_count = new int[this.e_num];
    this.e_time = 0;
    for (int i = 0; i < this.e_num; i++)
    {
      if ((this.school == 1) || (this.school == 2)) {
        this.e_hp[i] = (20 + this.school * 10);
      } else if (this.school == 3) {
        this.e_hp[i] = 54;
      } else if (this.school == 4) {
        this.e_hp[i] = 66;
      }
      this.max_e_hp[i] = this.e_hp[i];
      this.e_snow_y[i] = -10;
      this.e_behv[i] = 100;
      this.e_wp[i] = 0;
    }
    if (this.school < 3) {
      this.e_dem = (this.school + 7);
    } else if (this.school == 3) {
      this.e_dem = (this.school + 9);
    } else {
      this.e_dem = 14;
    }
    this.e_x[0] = (3 + get_random(3));
    this.e_y[0] = (1 + get_random(3));
    this.e_lv[0] = 3;
    this.e_fire_time[0] = 8;
    this.e_x[1] = (18 + get_random(3));
    this.e_y[1] = (1 + get_random(3));
    this.e_lv[1] = 3;
    this.e_fire_time[1] = 17;
    if (this.e_t_num >= 3)
    {
      this.e_x[2] = (13 + get_random(3));
      this.e_y[2] = (3 + get_random(2));
      this.e_lv[2] = 3;
      this.e_fire_time[2] = 20;
    }
    if (this.e_t_num == 4)
    {
      this.e_x[3] = 8;
      this.e_y[3] = 5;
      this.e_lv[3] = 3;
      this.e_fire_time[3] = 4;
    }
    this.e_boss_behv = 100;
    this.e_boss_snow_y = -10;
    this.e_boss_x = 10;
    this.e_boss_y = 6;
    this.e_boss_idx = 0;
    this.e_boss_hp = (this.e_boss * 10 + 30 + (this.school - 1) * 10);
    this.max_e_boss_hp = this.e_boss_hp;
    this.e_boss_fire_time = 2;
  }
  
  public void paint(Graphics paramGraphics)
  {
    int j;
    if (this.screen == 6)
    {
      paramGraphics.drawImage(this.imgBack, 0, 0, 20);
      paramGraphics.setColor(16777215);
      paramGraphics.fillRect(0, 25, 128, 84);
      paramGraphics.drawImage(this.imgHero[this.h_idx], this.h_x * 5, 83, 0x10 | 0x1);
      if (this.ppang_time > 0)
      {
        if (this.ppang_item == 1) {
          paramGraphics.drawImage(this.imgItem_hyo[0], this.h_x * 5, 74, 0x10 | 0x1);
        } else {
          paramGraphics.drawImage(this.imgItem_hyo[1], this.h_x * 5, 83, 0x10 | 0x1);
        }
        this.ppang_time -= 1;
        if (this.ppang_time == 0) {
          this.ppang_item = 0;
        }
      }
      draw_enemy(paramGraphics);
      if (this.item_mode != 0)
      {
        paramGraphics.setColor(12698049);
        if (this.message != "") {
          draw_text(paramGraphics);
        }
        for (int i = 1; i <= 5; i++) {
          paramGraphics.drawRect(i * 12 + 23, 110, 10, 9);
        }
        if (this.item_mode != 100)
        {
          paramGraphics.setColor(16711680);
          paramGraphics.drawRect(this.item_mode * 12 + 23, 110, 10, 9);
        }
        else if (this.item_mode == 100)
        {
          this.item_mode = 0;
        }
      }
      if (this.pw_up == 2)
      {
        paramGraphics.drawImage(this.imgShadow, this.snow_x * 5, this.snow_y * 7 + 4, 0x2 | 0x1);
        paramGraphics.drawImage(this.imgItem[this.wp], this.snow_x * 5, this.snow_y * 7 - this.snow_gap + 4, 0x2 | 0x1);
      }
      else if (this.pw_up == 1)
      {
        if ((this.real_snow_pw > 0) && (this.ppang_item != 1))
        {
          paramGraphics.setColor(7196662);
          if (this.h_x >= 13)
          {
            paramGraphics.fillRect(this.h_x * 5 - 16, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3);
            paramGraphics.drawImage(this.imgPwd, this.h_x * 5 - 15, 83, 0x10 | 0x1);
          }
          else
          {
            paramGraphics.fillRect(this.h_x * 5 + 14, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3);
            paramGraphics.drawImage(this.imgPwd, this.h_x * 5 + 15, 83, 0x10 | 0x1);
          }
        }
      }
      else if (this.pw_up == 0)
      {
        if (this.ppang <= -1)
        {
          paramGraphics.drawImage(this.imgPok, this.snow_x * 5, this.snow_y * 7 - 3, 0x2 | 0x1);
          this.ppang -= 1;
          if (this.ppang == -3) {
            this.ppang = 0;
          }
        }
        else if ((this.ppang >= 1) && (this.ppang <= 10))
        {
          if (this.s_item != -10)
          {
            if (this.ppang < 3) {
              paramGraphics.drawImage(this.imgPPang, this.snow_x * 5, this.snow_y * 7 - 6, 0x2 | 0x1);
            } else {
              paramGraphics.drawImage(this.imgPPang1, this.snow_x * 5, this.snow_y * 7 - 6, 0x2 | 0x1);
            }
          }
          else if (this.ppang < 4) {
            paramGraphics.drawImage(this.imgEffect[0], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
          } else {
            paramGraphics.drawImage(this.imgEffect[1], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
          }
          if (this.hit_idx != 10)
          {
            if (this.e_hp[this.hit_idx] > 0)
            {
              paramGraphics.setColor(16711680);
              paramGraphics.fillRect(this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15);
              paramGraphics.setColor(9672090);
              paramGraphics.fillRect(this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15 - 15 * this.e_hp[this.hit_idx] / this.max_e_hp[this.hit_idx]);
            }
          }
          else if (this.hit_idx == 10)
          {
            if (this.e_boss_hp > 0)
            {
              paramGraphics.setColor(16711680);
              paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15);
              paramGraphics.setColor(9672090);
              paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp);
            }
            if (this.al == 1) {
              paramGraphics.drawImage(this.imgAl, this.snow_x * 5 + 6, this.snow_y * 7 - 10, 0x2 | 0x1);
            }
          }
          this.ppang += 1;
          if (this.ppang == 6)
          {
            this.ppang = 0;
            this.s_item = 0;
            this.al = -1;
          }
        }
        else if (this.ppang >= 50)
        {
          draw_sp_hyo(paramGraphics);
        }
        if (this.message != "") {
          draw_text(paramGraphics);
        }
      }
      else if (this.pw_up == -1)
      {
        this.pw_up = 0;
      }
      if (this.p_mode == 1)
      {
        try
        {
          paramGraphics.drawImage(Image.createImage("/ui.png"), 0, 109, 20);
        }
        catch (Exception localException1) {}
        draw_item(paramGraphics);
        this.p_mode = 2;
        System.gc();
      }
      if (this.d_gauge != 0) {
        draw_gauge(paramGraphics);
      }
      for (j = 0; j < this.e_num; j++) {
        if (this.e_behv[j] != 100)
        {
          paramGraphics.drawImage(this.imgShadow, this.e_snow_x[j], this.e_snow_y[j] * 6 + 17, 0x2 | 0x1);
          paramGraphics.drawImage(this.imgItem[this.e_wp[j]], this.e_snow_x[j], this.e_snow_y[j] * 6 + 13 - this.e_snow_gap[j], 0x2 | 0x1);
        }
      }
      if ((this.e_boss_behv != 100) && (this.e_boss > 0))
      {
        paramGraphics.drawImage(this.imgShadow, this.e_boss_snow_x, this.e_boss_snow_y * 6 + 17, 0x2 | 0x1);
        paramGraphics.drawImage(this.imgItem[this.e_boss_wp], this.e_boss_snow_x, this.e_boss_snow_y * 6 + 13 - this.e_boss_snow_gap, 0x2 | 0x1);
      }
      if (this.del != -1) {
        draw_item(paramGraphics);
      }
      if (this.h_timer_p <= -1) {
        if (this.h_timer_p != -5)
        {
          paramGraphics.drawImage(this.imgH_ppang, this.h_x * 5 + 1, 81, 0x2 | 0x1);
          this.h_timer_p -= 1;
        }
        else if (this.h_timer_p == -5)
        {
          this.h_timer_p = 0;
          paramGraphics.setColor(16711680);
          paramGraphics.fillRect(5, 113, 9, 12);
          paramGraphics.setColor(9342606);
          if (this.hp > 0) {
            paramGraphics.fillRect(5, 113, 9, 12 - 12 * this.hp / this.max_hp);
          }
          if (this.hp <= 0)
          {
            this.state = 3;
            this.game_state = 1;
            this.gameOn = true;
          }
        }
      }
      if (this.state == 2)
      {
        if (this.ani_step >= 3) {
          paramGraphics.drawImage(this.imgStage[0], 20, 60, 20);
        }
        if (this.ani_step >= 6) {
          paramGraphics.drawImage(this.imgStage[1], 35, 60, 20);
        }
        if (this.ani_step >= 9) {
          paramGraphics.drawImage(this.imgStage[2], 50, 60, 20);
        }
        if (this.ani_step >= 12) {
          paramGraphics.drawImage(this.imgStage[3], 65, 60, 20);
        }
        if (this.ani_step >= 15) {
          paramGraphics.drawImage(this.imgStage[4], 80, 60, 20);
        }
        if (this.ani_step >= 19) {
          paramGraphics.drawImage(this.imgStage_num, 95, 60, 20);
        }
      }
    }
    else if (this.screen == 2)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      paramGraphics.setColor(16777164);
      paramGraphics.drawString("1.Play", 13, 23, 20);
      paramGraphics.drawString("2.Instructions", 13, 38, 20);
      paramGraphics.drawString("3.Configuration", 13, 53, 20);
      paramGraphics.drawString("4.Quit", 13, 68, 20);
      paramGraphics.drawImage(this.imgSl, 68, 115, 20);
      paramGraphics.drawImage(this.imgCh, 3, this.m_mode * 15 + 11, 20);
    }
    else if (this.screen == 3)
    {
      paramGraphics.drawImage(this.imgVill, 0, 0, 20);
      paramGraphics.setColor(14994350);
      if (this.last_stage / 10 == 1)
      {
        paramGraphics.drawImage(this.imgSchool, 78, 87, 3);
        paramGraphics.drawImage(this.imgSchool, 49, 87, 3);
        paramGraphics.drawImage(this.imgSchool, 19, 58, 3);
        paramGraphics.fillRect(76, 73, 6, 5);
        paramGraphics.fillRect(47, 73, 6, 5);
        paramGraphics.setColor(15132390);
        paramGraphics.fillRect(17, 44, 6, 5);
      }
      else if (this.last_stage / 10 == 2)
      {
        paramGraphics.drawImage(this.imgSchool, 49, 87, 3);
        paramGraphics.drawImage(this.imgSchool, 19, 58, 3);
        paramGraphics.setColor(14994350);
        paramGraphics.fillRect(47, 73, 6, 5);
        paramGraphics.setColor(15132390);
        paramGraphics.fillRect(17, 44, 6, 5);
      }
      else if (this.last_stage / 10 == 3)
      {
        paramGraphics.drawImage(this.imgSchool, 19, 58, 3);
        paramGraphics.setColor(15132390);
        paramGraphics.fillRect(17, 44, 6, 5);
      }
      paramGraphics.drawImage(this.imgCh, this.h_x, this.h_y, 20);
      if (this.m_mode != -1)
      {
        if (this.m_mode == 0) {
          this.message = "Drugstore";
        } else if (this.m_mode == 1) {
          this.message = "Item Shop";
        } else if (this.m_mode == 2) {
          this.message = "Eastern Boys";
        } else if (this.m_mode == 3) {
          this.message = "Southern Boys";
        } else if (this.m_mode == 4) {
          this.message = "Western Boys";
        } else if (this.m_mode == 5) {
          this.message = "Northern Boys";
        } else if (this.m_mode == 100) {
          this.message = "No Admittance";
        }
        if (this.message != "") {
          draw_text(paramGraphics);
        }
      }
      if ((this.ani_step == 0) && (this.last_stage > 20))
      {
        if (this.last_stage == 31) {
          draw_text_box(paramGraphics, "Western Boys");
        } else if (this.last_stage == 41) {
          draw_text_box(paramGraphics, "Northern Boys");
        } else if (this.last_stage == 21) {
          draw_text_box(paramGraphics, "Southern Boys");
        }
        this.ani_step += 1;
      }
    }
    else if (this.screen == 31)
    {
      paramGraphics.drawImage(this.imgShop, 24, 20, 20);
      paramGraphics.setColor(16777062);
      paramGraphics.drawRect(27, this.s_item * 13 + 30, 29, 10);
      paramGraphics.drawRect(28, this.s_item * 13 + 31, 27, 8);
      paramGraphics.setColor(13434777);
      paramGraphics.drawRect(this.b_item * 16 + 32, 70, 15, 15);
      paramGraphics.drawRect(this.b_item * 16 + 33, 71, 13, 13);
      draw_int(paramGraphics, this.saved_gold, 84, 96);
      if (this.m_mode == 1) {
        draw_int(paramGraphics, this.item_price[this.b_item], 42, 96);
      } else if (this.m_mode == 0) {
        draw_int(paramGraphics, this.item_price[(this.b_item + 4)], 42, 96);
      }
      if (this.message != "") {
        draw_text(paramGraphics);
      }
    }
    else if (this.screen == 100)
    {
      paramGraphics.setColor(16777215);
      paramGraphics.fillRect(1, 20, 126, 90);
      paramGraphics.setColor(0);
      paramGraphics.drawRect(0, 19, 127, 90);
      paramGraphics.drawRect(0, 21, 127, 86);
      paramGraphics.drawImage(this.imgCh, 3, this.m_mode * 14 + 18, 20);
      paramGraphics.drawString("Resume", 15, 28, 20);
      paramGraphics.drawString("MainMenu", 15, 42, 20);
      paramGraphics.drawString("Sound", 15, 56, 20);
      if (this.s_play == 1)
      {
        paramGraphics.setColor(255);
        paramGraphics.drawString("On/", 69, 56, 20);
        paramGraphics.setColor(8421504);
        paramGraphics.drawString("off", 96, 56, 20);
      }
      else
      {
        paramGraphics.setColor(8421504);
        paramGraphics.drawString("on/", 69, 56, 20);
        paramGraphics.setColor(255);
        paramGraphics.drawString("OFF", 93, 56, 20);
      }
      paramGraphics.setColor(0);
      paramGraphics.drawString("Instructions", 15, 70, 20);
      paramGraphics.drawString("Quit", 15, 84, 20);
    }
    else if (this.screen == -88)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      paramGraphics.setColor(16777164);
      paramGraphics.drawString("1.New Game", 13, 27, 20);
      paramGraphics.drawString("2.Saved Game", 13, 44, 20);
      paramGraphics.drawImage(this.imgSl, 68, 115, 20);
      paramGraphics.drawImage(this.imgBk, 2, 115, 20);
      paramGraphics.drawImage(this.imgCh, 4, this.m_mode * 17 + 14, 20);
    }
    else if (this.screen == 8)
    {
      if ((this.ani_step == 1) || (this.ani_step == 2))
      {
        paramGraphics.setColor(10173);
        paramGraphics.fillRect(0, 40, 128, 60);
        paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
        paramGraphics.drawImage(this.imgSpecial[1], 44, 89, 3);
      }
      else if (this.ani_step == 8)
      {
        paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
        paramGraphics.drawImage(this.imgSpecial[1], 48, 89, 3);
      }
      else if (this.ani_step == 16)
      {
        paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
        paramGraphics.drawImage(this.imgSpecial[1], 51, 89, 3);
      }
      else if (this.ani_step == 23)
      {
        paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
        paramGraphics.drawImage(this.imgSpecial[1], 54, 89, 3);
      }
      else if (this.ani_step == 30)
      {
        paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
        paramGraphics.drawImage(this.imgSpecial[1], 55, 89, 3);
      }
      else if (this.ani_step == 37)
      {
        paramGraphics.drawImage(this.imgSpecial[2], 58, 88, 3);
      }
      else if (this.ani_step == 50)
      {
        destroyImage(8);
        loadImage(9);
        this.ani_step = 0;
        this.screen = 9;
      }
    }
    else if (this.screen == 9)
    {
      if ((this.ani_step == 1) || (this.ani_step == 46))
      {
        if (this.ani_step == 46)
        {
          destroyImage(9);
          loadImage(100);
          this.pw_up = -1;
          paramGraphics.drawImage(this.imgBack, 0, 0, 20);
          this.screen = 6;
          this.ppang = 50;
          for (j = 0; j < this.e_num; j++)
          {
            this.e_move_dir[j] = 0;
            decs_e_hp(j);
          }
          if (this.e_boss > 0)
          {
            this.e_boss_move_dir = 0;
            decs_e_hp(10);
          }
          this.dem = 12;
          this.mana = 0;
        }
        paramGraphics.setColor(16777215);
        paramGraphics.fillRect(0, 25, 128, 84);
        for (j = 0; j < this.e_num; j++)
        {
          if (this.e_x[j] != -10) {
            paramGraphics.drawImage(this.imgEnemy[this.e_idx[j]], this.e_x[j] * 5, this.e_y[j] * 5 + 5, 0x10 | 0x1);
          }
          if (this.e_behv[j] != 100)
          {
            paramGraphics.drawImage(this.imgShadow, this.e_snow_x[j], this.e_snow_y[j] * 6 + 17, 0x2 | 0x1);
            paramGraphics.drawImage(this.imgItem[this.e_wp[j]], this.e_snow_x[j], this.e_snow_y[j] * 6 + 13 - this.e_snow_gap[j], 0x2 | 0x1);
          }
        }
        if (this.e_boss > 0) {
          paramGraphics.drawImage(this.imgBoss[this.e_boss_idx], this.e_boss_x * 5, this.e_boss_y * 5, 0x10 | 0x1);
        }
      }
      if (this.special == 1)
      {
        if (this.ani_step <= 45) {
          paramGraphics.drawImage(this.imgSp, 158 - this.ani_step * 3, 0, 20);
        }
      }
      else if (this.special == 2)
      {
        if (this.ani_step <= 45) {
          paramGraphics.drawImage(this.imgSp, 158 - this.ani_step * 3, 0, 20);
        }
      }
      else if ((this.special == 3) && (this.ani_step <= 45)) {
        paramGraphics.drawImage(this.imgSp, 168 - this.ani_step * 3, 30, 20);
      }
      paramGraphics.drawImage(this.imgHero[0], this.h_x * 5, 83, 0x10 | 0x1);
    }
    else if (this.screen == 4)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      paramGraphics.setColor(16777164);
      paramGraphics.drawString("Sound", 12, 23, 20);
      if (this.s_play == 1)
      {
        paramGraphics.drawString("ON /", 62, 23, 20);
        paramGraphics.setColor(10790052);
        paramGraphics.drawString("off", 95, 23, 20);
        paramGraphics.setColor(16777164);
      }
      if (this.s_play == 2)
      {
        paramGraphics.setColor(10790052);
        paramGraphics.drawString("on /", 62, 23, 20);
        paramGraphics.setColor(16777164);
        paramGraphics.drawString("OFF", 94, 23, 20);
      }
      paramGraphics.drawString("Vibration ", 12, 41, 20);
      if (this.v_mode == 1)
      {
        paramGraphics.drawString("ON /", 62, 59, 20);
        paramGraphics.setColor(10790052);
        paramGraphics.drawString("off", 95, 59, 20);
        paramGraphics.setColor(16777164);
      }
      if (this.v_mode == 2)
      {
        paramGraphics.setColor(10790052);
        paramGraphics.drawString("on /", 62, 59, 20);
        paramGraphics.setColor(16777164);
        paramGraphics.drawString("OFF", 94, 59, 20);
      }
      paramGraphics.drawString("Speed ", 14, 77, 20);
      paramGraphics.drawString("[ " + String.valueOf(this.speed) + " ]", 68, 77, 20);
      paramGraphics.drawImage(this.imgBk, 2, 115, 20);
      if (this.m_mode < 3) {
        paramGraphics.drawImage(this.imgCh, 4, this.m_mode * 18 + 9, 20);
      } else {
        paramGraphics.drawImage(this.imgCh, 4, this.m_mode * 18 + 27, 20);
      }
    }
    else if (this.screen == 5)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      paramGraphics.setColor(16777164);
      paramGraphics.drawString("1.Control Keys", 10, 25, 20);
      paramGraphics.drawString("2.Offense items", 10, 42, 20);
      paramGraphics.drawString("3.Defense items", 10, 59, 20);
      paramGraphics.drawImage(this.imgCh, 3, this.m_mode * 17 + 12, 20);
      paramGraphics.drawImage(this.imgSl, 68, 115, 20);
      paramGraphics.drawImage(this.imgBk, 2, 115, 20);
    }
    else if (this.screen == -33)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      paramGraphics.drawImage(this.imgBk, 2, 115, 20);
      destroyImage(2);
      paramGraphics.setColor(16777164);
      try
      {
        if (this.m_mode == 1) {
          paramGraphics.drawImage(Image.createImage("/txt4.png"), 5, 25, 20);
        }
        if (this.m_mode == 2)
        {
          paramGraphics.fillRect(6, 23, 10, 10);
          paramGraphics.fillRect(6, 45, 10, 10);
          paramGraphics.fillRect(6, 61, 10, 10);
          paramGraphics.fillRect(6, 84, 10, 10);
          paramGraphics.drawImage(Image.createImage("/item1.png"), 7, 24, 20);
          paramGraphics.drawImage(Image.createImage("/item2.png"), 7, 46, 20);
          paramGraphics.drawImage(Image.createImage("/item3.png"), 7, 62, 20);
          paramGraphics.drawImage(Image.createImage("/item4.png"), 7, 85, 20);
          paramGraphics.drawImage(Image.createImage("/txt2.png"), 23, 25, 20);
        }
        if (this.m_mode == 3)
        {
          paramGraphics.fillRect(6, 23, 10, 10);
          paramGraphics.fillRect(6, 38, 10, 10);
          paramGraphics.fillRect(6, 53, 10, 10);
          paramGraphics.fillRect(6, 67, 10, 10);
          paramGraphics.drawImage(Image.createImage("/item5.png"), 7, 24, 20);
          paramGraphics.drawImage(Image.createImage("/item6.png"), 7, 39, 20);
          paramGraphics.drawImage(Image.createImage("/item7.png"), 7, 54, 20);
          paramGraphics.drawImage(Image.createImage("/item8.png"), 7, 68, 20);
          paramGraphics.drawImage(Image.createImage("/txt1.png"), 23, 25, 20);
        }
      }
      catch (Exception localException2) {}
      System.gc();
    }
    else if (this.screen == 200)
    {
      if ((this.ani_step >= 13) && (this.ani_step < 27))
      {
        paramGraphics.setColor(16777215);
        paramGraphics.fillRect(0, 60, 128, 47);
        paramGraphics.drawImage(this.imgHero_v, this.h_x * 5, 83, 0x10 | 0x1);
      }
      else if ((this.ani_step >= 28) && (this.ani_step < 50))
      {
        paramGraphics.drawImage(this.imgV, this.h_x * 5 + 8, 87, 0x10 | 0x1);
        if (this.ani_step > 41) {
          paramGraphics.drawImage(this.imgVictory, 60, 60, 0x10 | 0x1);
        }
      }
      else if (this.ani_step == 50)
      {
        this.ani_step = -1;
      }
    }
    else if (this.screen == 65335)
    {
      int k;
      if (this.ani_step < 30)
      {
        paramGraphics.setColor(0);
        for (k = 0; k < 11; k++) {
          paramGraphics.fillRect(0, k * 10, this.ani_step * 4 + 12, 5);
        }
      }
      else if (this.ani_step < 65)
      {
        paramGraphics.setColor(0);
        for (k = 0; k < 11; k++) {
          paramGraphics.fillRect(0, k * 10 + 5, (this.ani_step - 30) * 7 - k * 10, 5);
        }
      }
      else if ((this.ani_step >= 65) && (this.ani_step <= 100))
      {
        if (this.ani_step > 90) {
          paramGraphics.drawImage(this.imgLose, 60, 60, 0x10 | 0x1);
        }
        paramGraphics.drawImage(this.imgHero_l, this.h_x * 5, 87, 0x10 | 0x1);
      }
    }
    else if (this.screen == 300)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      paramGraphics.setColor(16777164);
      paramGraphics.drawString("Good Job!", 15, 23, 20);
      paramGraphics.setColor(13434726);
      paramGraphics.drawString("Acquired", 15, 41, 20);
      paramGraphics.drawString("Gold:", 48, 57, 20);
      paramGraphics.drawString(String.valueOf(this.gold), 92, 57, 20);
      paramGraphics.setColor(16777164);
      paramGraphics.drawString("press any key", 10, 83, 20);
      paramGraphics.drawString("to continue", 37, 97, 20);
    }
    else if (this.screen == 77)
    {
      draw_text(paramGraphics);
    }
    else if (this.screen == -1)
    {
      loadImage(1);
      paramGraphics.drawImage(this.imgLogo, 0, 0, 20);
      MPlay(0);
      destroyImage(1);
    }
    else if (this.screen == -2)
    {
      paramGraphics.setColor(16777215);
      paramGraphics.fillRect(0, 0, 128, 135);
      paramGraphics.setColor(25054);
      paramGraphics.fillRect(0, 0, 128, 22);
      paramGraphics.fillRect(0, 71, 128, 84);
      try
      {
        paramGraphics.drawImage(Image.createImage("/present.png"), 64, 5, 0x10 | 0x1);
        paramGraphics.drawImage(Image.createImage("/sam_logo.png"), 64, 28, 0x10 | 0x1);
        paramGraphics.drawImage(Image.createImage("/http1.png"), 7, 77, 20);
        paramGraphics.drawImage(Image.createImage("/http2.png"), 7, 103, 20);
      }
      catch (Exception localException3) {}
      System.gc();
    }
    else if (this.screen == 1000)
    {
      paramGraphics.setColor(16777215);
      paramGraphics.fillRect(0, 25, 120, 85);
      try
      {
        paramGraphics.drawImage(Image.createImage("/allClear.png"), 64, 10, 0x10 | 0x1);
      }
      catch (Exception localException4) {}
    }
    else if (this.screen == -5)
    {
      paramGraphics.setColor(16777215);
      paramGraphics.fillRect(1, 20, 126, 90);
      paramGraphics.setColor(0);
      paramGraphics.drawRect(0, 19, 127, 90);
      paramGraphics.drawRect(0, 21, 127, 86);
      try
      {
        paramGraphics.drawImage(Image.createImage("/txt4b.png"), 4, 30, 20);
      }
      catch (Exception localException5) {}
      System.gc();
    }
    else if (this.screen == 1)
    {
      paramGraphics.drawImage(this.imgMM, 0, 0, 20);
      try
      {
        paramGraphics.drawImage(Image.createImage("/title.png"), 64, 35, 0x10 | 0x1);
      }
      catch (Exception localException6) {}
      paramGraphics.drawImage(this.imgPl, 68, 115, 20);
      paramGraphics.drawImage(this.imgBk, 2, 115, 20);
      System.gc();
    }
  }
  
  public void draw_text_box(Graphics paramGraphics, String paramString)
  {
    paramGraphics.setColor(44783);
    paramGraphics.fillRect(0, 42, 128, 38);
    paramGraphics.setColor(20361);
    paramGraphics.drawRect(0, 42, 127, 38);
    paramGraphics.setColor(0);
    paramGraphics.drawString(paramString, 9, 46, 20);
    paramGraphics.drawString("challenged you!!", 4, 60, 20);
  }
  
  public void draw_text(Graphics paramGraphics)
  {
    int i = this.message.length();
    paramGraphics.setColor(44783);
    paramGraphics.fillRect(0, 52, 128, 19);
    paramGraphics.setColor(20361);
    paramGraphics.drawRect(0, 52, 127, 19);
    paramGraphics.setColor(0);
    paramGraphics.drawString(this.message, 64, 53, 0x10 | 0x1);
    this.message = "";
  }
  
  public void draw_item(Graphics paramGraphics)
  {
    if (this.del == -1)
    {
      for (int i = 0; i < 5; i++) {
        if (this.item_slot[i] != 0) {
          paramGraphics.drawImage(this.imgItem[this.item_slot[i]], 12 * i + 37, 111, 20);
        }
      }
    }
    else
    {
      paramGraphics.setColor(6974058);
      paramGraphics.fillRect(this.del * 12 + 37, 111, 8, 8);
      this.del = -1;
    }
  }
  
  public void draw_sp_hyo(Graphics paramGraphics)
  {
    for (int i = 0; i < this.e_num; i++) {
      if (this.e_hp[i] >= 0)
      {
        paramGraphics.setColor(16711680);
        paramGraphics.fillRect(this.e_x[i] * 5 + 8, this.e_y[i] * 5 + 5, 3, 15);
        paramGraphics.setColor(9672090);
        paramGraphics.fillRect(this.e_x[i] * 5 + 8, this.e_y[i] * 5 + 5, 3, 15 - 15 * this.e_hp[i] / this.max_e_hp[i]);
        if (this.ppang <= 51) {
          paramGraphics.drawImage(this.imgEffect[0], this.e_x[i] * 5, this.e_y[i] * 5 + 5, 0x10 | 0x1);
        } else if (this.ppang <= 54) {
          paramGraphics.drawImage(this.imgEffect[1], this.e_x[i] * 5, this.e_y[i] * 5 + 5, 0x10 | 0x1);
        }
      }
    }
    if ((this.e_boss_hp >= 0) && (this.e_boss > 0))
    {
      paramGraphics.setColor(16711680);
      paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15);
      paramGraphics.setColor(9672090);
      paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp);
      if (this.ppang <= 51) {
        paramGraphics.drawImage(this.imgEffect[0], this.e_boss_x * 5, this.e_boss_y * 5 + 5, 0x10 | 0x1);
      } else if (this.ppang <= 54) {
        paramGraphics.drawImage(this.imgEffect[1], this.e_boss_x * 5, this.e_boss_y * 5 + 6, 0x10 | 0x1);
      }
    }
    if (this.ppang != 55)
    {
      this.ppang += 1;
    }
    else
    {
      for (int j = 0; j < this.e_num; j++) {
        if (this.e_hp[j] > 0)
        {
          if (this.special == 2)
          {
            this.e_ppang_time[j] = 65;
            this.e_ppang_item[j] = 2;
          }
          else if (this.special == 3)
          {
            this.e_ppang_time[j] = 80;
            this.e_ppang_item[j] = 1;
            this.e_lv[j] = (-this.e_lv[j]);
          }
          this.e_move_dir[j] = 0;
        }
      }
      this.ppang = 0;
      this.special = 0;
    }
  }
  
  public void draw_enemy(Graphics paramGraphics)
  {
    for (int i = 0; i < this.e_num; i++) {
      if (this.e_x[i] != -10)
      {
        paramGraphics.drawImage(this.imgEnemy[this.e_idx[i]], this.e_x[i] * 5, this.e_y[i] * 5 + 5, 0x10 | 0x1);
        if (this.e_ppang_time[i] > 0)
        {
          this.e_ppang_time[i] -= 1;
          paramGraphics.drawImage(this.imgItem_hyo[(this.e_ppang_item[i] - 1)], this.e_x[i] * 5, this.e_y[i] * 5 + 1, 0x10 | 0x1);
          if (this.e_ppang_time[i] == 0)
          {
            this.e_ppang_item[i] = 0;
            if (this.e_lv[i] < 0) {
              this.e_lv[i] = (-this.e_lv[i]);
            }
          }
        }
        if (this.dis_count[i] >= 1)
        {
          this.dis_count[i] += 1;
          if (this.dis_count[i] == 4)
          {
            this.dis_count[i] = 0;
            this.e_idx[i] = 0;
          }
        }
        else if (this.dis_count[i] <= -1)
        {
          this.dis_count[i] -= 1;
          if (this.dis_count[i] == -10)
          {
            this.e_x[i] = -10;
            this.dis_count[i] = 0;
            this.e_t_num -= 1;
            if ((this.e_t_num == 0) && (this.e_boss == 0))
            {
              this.item_mode = 0;
              this.game_state = 2;
              this.state = 3;
              this.gameOn = true;
            }
          }
        }
      }
    }
    if (this.e_boss > 0)
    {
      paramGraphics.drawImage(this.imgBoss[this.e_boss_idx], this.e_boss_x * 5, this.e_boss_y * 5, 0x10 | 0x1);
      if (this.boss_dis_count >= 1)
      {
        this.boss_dis_count += 1;
        if (this.boss_dis_count == 4)
        {
          this.boss_dis_count = 0;
          this.e_boss_idx = 0;
        }
      }
      else if (this.boss_dis_count <= -1)
      {
        this.boss_dis_count -= 1;
        if (this.boss_dis_count == -10)
        {
          this.e_boss = 0;
          this.boss_dis_count = 0;
          if (this.e_t_num == 0)
          {
            this.item_mode = 0;
            this.game_state = 2;
            this.state = 3;
            this.gameOn = true;
          }
        }
      }
    }
  }
  
  public void draw_gauge(Graphics paramGraphics)
  {
    if (this.d_gauge == 2)
    {
      paramGraphics.setColor(16775065);
      paramGraphics.fillRect(118, 111, 8, 8);
      if (this.wp != 0) {
        paramGraphics.drawImage(this.imgItem[this.wp], 122, 111, 0x10 | 0x1);
      }
    }
    if (this.mana != 0)
    {
      paramGraphics.setColor(16711680);
      paramGraphics.fillRect(30, 124, this.mana, 1);
      if (this.mana == 36)
      {
        paramGraphics.fillRect(39, 123, 3, 3);
        paramGraphics.fillRect(51, 123, 3, 3);
        paramGraphics.fillRect(63, 123, 3, 3);
      }
      else if (this.mana >= 24)
      {
        paramGraphics.fillRect(39, 123, 3, 3);
        paramGraphics.fillRect(51, 123, 3, 3);
      }
      else if (this.mana >= 12)
      {
        paramGraphics.fillRect(39, 123, 3, 3);
      }
    }
    else if (this.mana == 0)
    {
      paramGraphics.setColor(4960985);
      paramGraphics.fillRect(30, 124, 36, 1);
      paramGraphics.fillRect(39, 123, 3, 3);
      paramGraphics.fillRect(51, 123, 3, 3);
      paramGraphics.fillRect(63, 123, 3, 3);
    }
    this.d_gauge = 0;
  }
  
  public void draw_int(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (paramInt1 / 100 > 0) {
      i = 3;
    } else if (paramInt1 / 10 > 0) {
      i = 2;
    } else {
      i = 1;
    }
    int[] arrayOfInt = new int[i];
    if (i == 3)
    {
      arrayOfInt[2] = (paramInt1 / 100);
      arrayOfInt[1] = (paramInt1 / 10 % 10);
      arrayOfInt[0] = (paramInt1 % 10);
    }
    else if (i == 2)
    {
      arrayOfInt[1] = (paramInt1 / 10);
      arrayOfInt[0] = (paramInt1 % 10);
    }
    else if (i == 1)
    {
      arrayOfInt[0] = paramInt1;
    }
    for (int j = i; j > 0; j--) {
      if (paramInt1 < 10) {
        paramGraphics.drawImage(this.imgNum[arrayOfInt[(j - 1)]], (i - j) * 4 + paramInt2, paramInt3, 20);
      } else {
        paramGraphics.drawImage(this.imgNum[arrayOfInt[(j - 1)]], (i - j) * 4 + paramInt2 - 2, paramInt3, 20);
      }
    }
  }
  
  public void run()
  {
    for (;;)
    {
      if (this.gameOn)
      {
        if (this.screen == 6)
        {
          if (this.state == 1)
          {
            try
            {
              Thread.sleep(this.game_speed);
            }
            catch (Exception localException1) {}
            if (this.pw_up == 1)
            {
              setPower();
              if (this.h_idx == 2) {
                this.h_idx = 3;
              } else if (this.h_idx == 3) {
                this.h_idx = 2;
              }
            }
            else if (this.pw_up == 2)
            {
              if (this.h_timer < 4)
              {
                this.h_timer += 1;
                if (this.h_timer == 4) {
                  this.h_idx = 0;
                }
              }
              if (this.snow_y > this.snow_last_y)
              {
                this.snow_y -= 1;
                if (this.snow_y > this.snow_top_y) {
                  this.snow_gap += 3;
                } else if (this.snow_y < this.snow_top_y) {
                  this.snow_gap -= 3;
                }
              }
              else
              {
                check_ppang();
              }
            }
            this.e_time += 1;
            for (int i = 0; i < this.e_num; i++)
            {
              if (this.e_hp[i] >= 0)
              {
                if ((this.e_time == this.e_fire_time[i]) && (get_random(3) != 1) && (this.e_ppang_item[i] != 2)) {
                  e_attack_ai(i);
                }
                if (this.e_ppang_item[i] != 2) {
                  if (this.e_idx[i] == 0) {
                    this.e_idx[i] = 1;
                  } else if (this.e_idx[i] == 1) {
                    this.e_idx[i] = 0;
                  }
                }
              }
              if (this.e_move_dir[i] >= 100)
              {
            	System.out.println("E move dir > 100: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- "+ this.e_x[0] + " " + this.e_y[0] + " "+ this.e_x[1] + " " + this.e_y[1]);
                this.e_move_dir[i] += 1;
                if (this.e_move_dir[i] == 120) {
                  this.e_move_dir[i] = 0;
                }
              }
              else if ((this.e_move_dir[i] == 0) && (this.e_hp[i] > 0) && (this.e_ppang_item[i] != 2))
              {
            	System.out.println("E move dir = 0 AI: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- " + this.e_x[0] + " "+ this.e_x[1]);
                e_move_ai(i);
              }
              else if ((this.e_move_dir[i] < 100) && (this.e_move_dir[i] != 0) && (this.e_hp[i] > 0))
              {
            	System.out.println("E move dir < 100: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- " + this.e_x[0] + " "+ this.e_x[1]);
                e_move(i);
              }
            }
            if (this.e_boss > 0)
            {
              if (this.e_boss_hp >= 0)
              {
                if ((this.e_time == this.e_boss_fire_time) && (get_random(3) != 1)) {
                  if ((this.e_boss == 1) || (this.e_boss == 2)) {
                    e_attack_ai(101);
                  } else {
                    e_attack_ai(102);
                  }
                }
                if (this.e_boss_idx == 0) {
                  this.e_boss_idx = 1;
                } else if (this.e_boss_idx == 1) {
                  this.e_boss_idx = 0;
                }
              }
              if (this.e_boss_move_dir >= 100)
              {
                this.e_boss_move_dir += 1;
                if (this.e_boss_move_dir == 115) {
                  this.e_boss_move_dir = 0;
                }
              }
              else if ((this.e_boss_move_dir == 0) && (this.e_boss_hp > 0))
              {
                boss_move_ai();
              }
              else if ((this.e_boss_move_dir != 0) && (this.e_boss_hp > 0))
              {
                boss_move();
              }
            }
            if ((this.e_num == 3) || (this.e_num == 4))
            {
              if (this.e_time == 21) {
                this.e_time = 0;
              }
            }
            else if ((this.e_num == 2) && (this.e_time == 18)) {
              this.e_time = 0;
            }
            e_snow();
            if (this.gameOn)
            {
              repaint();
              serviceRepaints();
            }
          }
          else if (this.state == 2)
          {
            if ((this.ani_step >= 1) && (this.ani_step <= 20)) {
              this.ani_step += 1;
            }
            if (this.ani_step == 0)
            {
              loadImage(-6);
              this.ani_step = 1;
            }
            else if ((this.ani_step >= 1) && (this.ani_step <= 19))
            {
              repaint();
              serviceRepaints();
            }
            else if (this.ani_step == 20)
            {
              destroyImage(-6);
              this.state = 1;
            }
          }
          else if (this.state == 3)
          {
            if (this.game_state == 2)
            {
              this.screen = 201;
              MPlay(7);
              this.gold = (this.school * 6 + get_random(7) + 5);
            }
            else if (this.game_state == 1)
            {
              this.screen = 65336;
              this.gold = 3;
            }
          }
        }
        else if (this.screen == 8)
        {
          if ((this.ani_step < 50) && (this.ani_step > 0)) {
            this.ani_step += 1;
          }
          repaint();
          serviceRepaints();
        }
        else if (this.screen == 9)
        {
          if ((this.ani_step < 46) && (this.ani_step >= 0)) {
            this.ani_step += 1;
          }
          repaint();
          serviceRepaints();
        }
        else if (this.screen == 200)
        {
          if ((this.ani_step < 51) && (this.ani_step >= 0))
          {
            this.ani_step += 1;
            repaint();
            serviceRepaints();
          }
          else
          {
            this.gameOn = false;
            destroyImage(200);
            System.gc();
            if (this.state != 10)
            {
              loadImage(2);
              this.screen = 300;
            }
            else
            {
              this.screen = 1000;
            }
            repaint();
          }
        }
        else if (this.screen == 201)
        {
          this.ani_step = 0;
          if (this.last_stage / 10 == this.school)
          {
            if (this.stage % 10 != 4)
            {
              this.stage += 1;
            }
            else if (this.stage != 44)
            {
              this.stage += 10;
              this.stage = (this.stage - this.stage % 10 + 1);
            }
            else
            {
              this.stage = 45;
              this.state = 10;
            }
            this.last_stage = this.stage;
          }
          destroyImage(6);
          destroyImage(7);
          destroyImage(100);
          loadImage(200);
          this.screen = 200;
        }
        else if (this.screen == 65336)
        {
          this.item_mode = 0;
          this.ani_step = 0;
          destroyImage(6);
          destroyImage(7);
          destroyImage(100);
          loadImage(65336);
          MPlay(6);
          this.screen = 65335;
        }
        else if (this.screen == 65335)
        {
          if (this.ani_step <= 100)
          {
            this.ani_step += 1;
            repaint();
            serviceRepaints();
          }
          else
          {
            this.gameOn = false;
            destroyImage(65336);
            loadImage(2);
            System.gc();
            this.screen = 300;
            repaint();
          }
        }
      }
      else {
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception localException2) {}
      }
    }
  }
  
  public void setPower()
  {
    if (this.snow_pw < 22)
    {
      this.snow_pw += 1;
      this.real_snow_pw = (this.snow_pw / 3);
    }
  }
  
  public void make_attack()
  {
    this.snow_y = 12;
    this.snow_x = this.h_x;
    this.snow_last_y = (9 - this.real_snow_pw);
    if (this.real_snow_pw % 2 == 0) {
      this.snow_top_y = (10 - this.real_snow_pw / 2);
    } else {
      this.snow_top_y = (9 - this.real_snow_pw / 2);
    }
    this.snow_gap = 3;
    this.h_timer = 0;
    this.pw_up = 2;
    MPlay(2);
  }
  
  public int get_random(int paramInt)
  {
    int i = this.rnd.nextInt() % paramInt;
    if (i < 0) {
      i = -i;
    }
    return i;
  }
  
  public int get_random1(int paramInt)
  {
    int i = this.rnd.nextInt() % paramInt;
    if (i == 0) {
      i = -5;
    }
    return i;
  }
  
  public void e_attack_ai(int paramInt)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    if (paramInt > 100)
    {
      m = this.h_x - this.e_boss_x;
      j = paramInt;
    }
    else
    {
      m = this.h_x - this.e_x[paramInt];
      k = paramInt;
    }
    int n;
    if ((m >= -9) && (m <= -6))
    {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(3);
        if (n == 0) {
          i = -2;
        } else if (n == 1) {
          i = -3;
        } else {
          i = -4;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    else if ((m >= -5) && (m <= -2))
    {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(3);
        if (n == 0) {
          i = -1;
        } else if (n == 1) {
          i = -2;
        } else {
          i = -3;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    else if ((m >= -1) && (m <= 1))
    {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(3);
        if (n == 0) {
          i = -1;
        } else if (n == 1) {
          i = 0;
        } else {
          i = 1;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    else if ((m >= 2) && (m <= 5))
    {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(3);
        if (n == 0) {
          i = 1;
        } else if (n == 1) {
          i = 2;
        } else {
          i = 3;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    else if ((m >= 6) && (m <= 9))
    {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(3);
        if (n == 0) {
          i = 2;
        } else if (n == 1) {
          i = 3;
        } else {
          i = 4;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    else if (m >= 10)
    {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(3);
        if (n == 0) {
          i = 4;
        } else {
          i = 5;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    else if (m <= -10) {
      if ((this.e_lv[k] >= 2) || (j > 100))
      {
        n = get_random(2);
        if (n == 0) {
          i = -4;
        } else {
          i = -5;
        }
      }
      else
      {
        i = get_random1(6);
      }
    }
    if (j < 100)
    {
      if (this.e_lv[k] >= 2)
      {
        n = get_random(4);
        if (n == 1)
        {
          int i1 = get_random(3);
          if (i1 == 0) {
            this.e_wp[k] = 1;
          } else if (i1 == 1) {
            this.e_wp[k] = 2;
          } else {
            this.e_wp[k] = 3;
          }
        }
        else
        {
          this.e_wp[k] = 0;
        }
      }
      this.e_behv[k] = i;
      this.e_snow_y[k] = this.e_y[k];
      this.e_snow_x[k] = (this.e_x[k] * 5);
      this.e_snow_gap[k] = 0;
      this.e_snow_dx[k] = i;
      this.e_snow_top[k] = 1;
      this.e_idx[k] = 3;
      this.dis_count[k] = 1;
    }
    else
    {
      if (j < 103)
      {
        n = get_random(2);
        if (n == 0)
        {
          n = get_random(3);
          if (n == 0) {
            this.e_boss_wp = 1;
          } else if (n == 1) {
            this.e_boss_wp = 2;
          } else {
            this.e_boss_wp = 3;
          }
        }
        else
        {
          this.e_boss_wp = 0;
        }
      }
      else
      {
        n = get_random(3);
        if (n == 0) {
          this.e_boss_wp = 1;
        } else if (n == 1) {
          this.e_boss_wp = 2;
        } else {
          this.e_boss_wp = 3;
        }
      }
      this.e_boss_behv = i;
      this.e_boss_snow_y = this.e_boss_y;
      this.e_boss_snow_x = (this.e_boss_x * 5);
      this.e_boss_snow_gap = 0;
      this.e_boss_snow_dx = i;
      this.e_boss_snow_top = 1;
      this.e_boss_idx = 3;
      this.boss_dis_count = 1;
    }
  }
  
  public void e_snow()
  {
    for (int i = 0; i < this.e_num; i++) {
      if (this.e_behv[i] != 100)
      {
        this.e_snow_y[i] += 1;
        this.e_snow_x[i] += this.e_snow_dx[i];
        if ((this.e_snow_gap[i] < 10) && (this.e_snow_top[i] == 1))
        {
          this.e_snow_gap[i] += 2;
          if (this.e_snow_gap[i] == 10) {
            this.e_snow_top[i] = 2;
          }
        }
        else
        {
          this.e_snow_gap[i] -= 1;
        }
        if (this.e_snow_y[i] == 13) {
          check_hero(this.e_snow_x[i], i);
        } else if (this.e_snow_y[i] >= 16) {
          this.e_behv[i] = 100;
        }
      }
    }
    if ((this.e_boss > 0) && (this.e_boss_behv != 100))
    {
      this.e_boss_snow_y += 1;
      this.e_boss_snow_x += this.e_boss_snow_dx;
      if ((this.e_boss_snow_gap < 10) && (this.e_boss_snow_top == 1))
      {
        this.e_boss_snow_gap += 2;
        if (this.e_boss_snow_gap == 10) {
          this.e_boss_snow_top = 2;
        }
      }
      else
      {
        this.e_boss_snow_gap -= 1;
      }
      if (this.e_boss_snow_y == 13) {
        check_hero(this.e_boss_snow_x, 100);
      } else if (this.e_boss_snow_y >= 16) {
        this.e_boss_behv = 100;
      }
    }
  }
  
  public void check_hero(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j;
    if (paramInt2 != 100)
    {
      if (this.e_behv[paramInt2] <= 0)
      {
        i = 5;
        j = 9;
      }
      else
      {
        i = 9;
        j = 5;
      }
    }
    else if (this.e_boss_behv <= 0)
    {
      i = 5;
      j = 9;
    }
    else
    {
      i = 9;
      j = 5;
    }
    if ((paramInt1 - this.h_x * 5 <= j) && (paramInt1 - this.h_x * 5 >= -i))
    {
      int k = -1;
      if (paramInt2 != 100)
      {
        this.e_behv[paramInt2] = 100;
        k = this.e_wp[paramInt2];
      }
      else
      {
        this.e_boss_behv = 100;
        k = this.e_boss_wp;
      }
      this.h_timer_p = -1;
      if (this.hp > 0) {
        if (k == 0)
        {
          this.hp -= this.e_dem;
        }
        else if (k == 1)
        {
          this.ppang_item = 1;
          this.ppang_time = 20;
          this.hp -= this.e_dem * 2 / 3;
        }
        else if (k == 2)
        {
          this.ppang_item = 1;
          this.ppang_time = 20;
          this.hp -= this.e_dem;
        }
        else if (k == 3)
        {
          this.ppang_item = 2;
          this.ppang_time = 20;
          this.hp -= this.e_dem * 2 / 3;
        }
      }
      MPlay(4);
      call_vib(1);
    }
  }
  
  public void boss_move()
  {
    if ((this.e_boss_move_dir >= 1) && (this.e_boss_move_dir < 8))
    {
      this.e_boss_move_dir += 1;
      if (this.e_boss_move_dir == 8) {
        this.e_boss_move_dir = 100;
      }
    }
    else if ((this.e_boss_move_dir >= 21) && (this.e_boss_move_dir < 31))
    {
      this.e_boss_move_dir += 1;
      if ((this.e_boss_x != 2) && (this.e_boss_move_dir % 2 == 0)) {
        this.e_boss_x -= 1;
      }
      if (this.e_boss_move_dir == 31) {
        this.e_boss_move_dir = 100;
      }
    }
    else if ((this.e_boss_move_dir > -31) && (this.e_boss_move_dir <= -21))
    {
      this.e_boss_move_dir -= 1;
      if ((this.e_boss_x != 22) && (this.e_boss_move_dir % 2 == 0)) {
        this.e_boss_x += 1;
      }
      if (this.e_boss_move_dir == -31) {
        this.e_boss_move_dir = 100;
      }
    }
  }
  
  public void boss_move_ai()
  {
    if (this.e_boss_x == 2)
    {
      this.e_boss_move_dir = -21;
    }
    else if (this.e_boss_x == 22)
    {
      this.e_boss_move_dir = 21;
    }
    else
    {
      int i = get_random(6);
      if ((i == 0) || (i == 1)) {
        this.e_boss_move_dir = 21;
      } else if ((i == 2) || (i == 3)) {
        this.e_boss_move_dir = -21;
      } else {
        this.e_boss_move_dir = 1;
      }
    }
  }
  
  public void e_move_ai(int paramInt)
  {
    int i;
    if ((this.e_x[paramInt] == 2) || ((this.e_x[1] <= 9) && (paramInt == 1)))
    {
      i = get_random(4);
      if ((i == 0) || (i == 1)) {
        this.e_move_dir[paramInt] = -21;
      } else if (i == 2) {
        this.e_move_dir[paramInt] = -11;
      } else if (i == 3) {
        this.e_move_dir[paramInt] = 11;
      }
    }
    else if ((this.e_x[paramInt] == 22) || ((this.e_x[0] >= 14) && (paramInt == 0)))
    {
      i = get_random(4);
      if ((i == 0) || (i == 1)) {
        this.e_move_dir[paramInt] = 21;
      } else if (i == 2) {
        this.e_move_dir[paramInt] = -11;
      } else if (i == 3) {
        this.e_move_dir[paramInt] = 11;
      }
    }
    else if ((this.e_y[paramInt] == 6) || (this.e_y[paramInt] == 7))
    {
      i = get_random(4);
      if ((i == 1) || (i == 2)) {
        this.e_move_dir[paramInt] = 11;
      } else if (i == 0) {
        this.e_move_dir[paramInt] = 21;
      } else if (i == 1) {
        this.e_move_dir[paramInt] = -21;
      }
    }
    else
    {
      i = get_random(8);
      if ((i == 0) || (i == 1)) {
        this.e_move_dir[paramInt] = 21;
      } else if ((i == 2) || (i == 3)) {
        this.e_move_dir[paramInt] = -21;
      } else if (i == 4) {
        this.e_move_dir[paramInt] = 11;
      } else if (i == 5) {
        this.e_move_dir[paramInt] = -11;
      } else {
        this.e_move_dir[paramInt] = 1;
      }
    }
  }
  
  public void e_move(int paramInt)
  {
    int i = this.e_move_dir[paramInt];
    if ((i >= 1) && (i < 8))
    {
      i++;
      if (i == 8) {
        i = 100;
      }
    }
    else if ((i >= 21) && (i < 31))
    {
      i++;
      if ((this.e_x[paramInt] != 2) && (i % 3 == 0)) {
        this.e_x[paramInt] -= 1;
      }
      if (i == 31) {
        i = 100;
      }
    }
    else if ((i > -31) && (i <= -21))
    {
      i--;
      if ((this.e_x[paramInt] != 22) && (i % 3 == 0)) {
        this.e_x[paramInt] += 1;
      }
      if (i == -31) {
        i = 100;
      }
    }
    else if ((i >= 11) && (i < 14))
    {
      i++;
      if ((this.e_y[paramInt] != 1) && (i % 2 == 0)) {
        this.e_y[paramInt] -= 1;
      }
      if (i == 14) {
        i = 100;
      }
    }
    else if ((i > -14) && (i <= -11))
    {
      i--;
      if ((this.e_y[paramInt] != 7) && (i % 2 == 0)) {
        this.e_y[paramInt] += 1;
      }
      if (i == -14) {
        i = 100;
      }
    }
    this.e_move_dir[paramInt] = i;
  }
  
  public void use_special()
  {
    this.screen = 8;
    this.ani_step = 1;
    this.real_snow_pw = 0;
    this.snow_pw = 0;
    this.h_idx = 0;
    this.gameOn = false;
    destroyImage(100);
    loadImage(8);
    if (this.mana == 36)
    {
      this.special = 3;
      this.dem = 24;
    }
    else if (this.mana >= 24)
    {
      this.special = 2;
      this.dem = 12;
    }
    else if (this.mana >= 12)
    {
      this.special = 1;
      this.dem = 12;
    }
    this.d_gauge = 1;
    MPlay(5);
    call_vib(3);
  }
  
  public void decs_e_hp(int paramInt)
  {
    this.hit_idx = paramInt;
    if (this.mana != 36) {
      if (this.mana <= 10) {
        this.mana += 2;
      } else {
        this.mana += 1;
      }
    }
    if (paramInt != 10)
    {
      if (this.wp == 0)
      {
        this.e_hp[paramInt] -= this.dem;
      }
      else if (this.wp == 1)
      {
        this.e_ppang_time[paramInt] = 70;
        this.e_ppang_item[paramInt] = 1;
        this.e_lv[paramInt] = (-this.e_lv[paramInt]);
        this.e_hp[paramInt] -= this.dem;
      }
      else if (this.wp == 2)
      {
        this.s_item = -10;
        this.e_hp[paramInt] -= 19;
      }
      else if (this.wp == 3)
      {
        this.e_ppang_time[paramInt] = 65;
        this.e_ppang_item[paramInt] = 2;
        this.e_hp[paramInt] -= this.dem / 2;
        this.e_move_dir[paramInt] = 0;
      }
      else if (this.wp == 4)
      {
        this.e_ppang_time[paramInt] = 75;
        this.e_ppang_item[paramInt] = 1;
        this.e_lv[paramInt] = (-this.e_lv[paramInt]);
        this.s_item = -10;
        this.e_hp[paramInt] -= this.dem * 2;
      }
      if (this.e_hp[paramInt] < 0)
      {
        this.e_idx[paramInt] = 2;
        this.dis_count[paramInt] = -1;
      }
    }
    else if (paramInt == 10)
    {
      if (this.wp == 4)
      {
        this.s_item = -10;
        this.e_boss_hp -= this.dem * 2;
      }
      else if (this.wp == 2)
      {
        this.s_item = -10;
        this.e_boss_hp -= 19;
      }
      else
      {
        this.e_boss_hp -= this.dem;
      }
      if (this.e_boss_hp < 0)
      {
        this.e_boss_idx = 2;
        this.boss_dis_count = -1;
      }
      if (this.al == 1) {
        this.e_boss_hp -= 5;
      }
    }
    MPlay(3);
  }
  
  public void check_ppang()
  {
    this.d_gauge = 2;
    int j;
    for (int i = 0; i < this.e_num; i++)
    {
      j = this.e_x[i];
      if ((j - this.snow_x >= -1) && (j - this.snow_x <= 1))
      {
        int k = this.e_y[i];
        if ((k >= 0) && (k <= 4))
        {
          if ((k + this.real_snow_pw == 7) || (k + this.real_snow_pw == 8))
          {
            this.ppang = 1;
            decs_e_hp(i);
            break;
          }
        }
        else if ((k + this.real_snow_pw == 8) || (k + this.real_snow_pw == 9))
        {
          this.ppang = 1;
          decs_e_hp(i);
          break;
        }
      }
      this.ppang = -1;
    }
    if ((this.e_boss > 0) && (this.e_boss_x - this.snow_x >= -1) && (this.e_boss_x - this.snow_x <= 1))
    {
      j = this.e_boss_y + this.real_snow_pw - 1;
      if ((j == 9) || (j == 7) || (j == 8))
      {
        if (j == 7) {
          this.al = 1;
        }
        this.ppang = 1;
        decs_e_hp(10);
      }
    }
    this.pw_up = -1;
    if (this.wp != 0) {
      this.wp = 0;
    }
  }
  
  public void startThread()
  {
    if (this.thread == null)
    {
      this.thread = new Thread(this);
      this.thread.start();
    }
  }
  
  public void goto_menu()
  {
    destroyImage(6);
    destroyImage(7);
    destroyImage(100);
    loadImage(2);
    this.m_mode = 1;
    this.screen = 2;
  }
  
  public void check_building(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 43) && (paramInt2 == 22))
    {
      this.m_mode = 0;
      this.b_item = 0;
      this.s_item = 0;
    }
    else if ((paramInt1 == 71) && (paramInt2 == 22))
    {
      this.m_mode = 1;
      this.b_item = 0;
      this.s_item = 0;
    }
    else if ((paramInt1 == 92) && (paramInt2 == 46))
    {
      this.m_mode = 2;
      this.school = 1;
    }
    else if ((paramInt1 == 71) && (paramInt2 == 70))
    {
      if (this.last_stage > 20) {
        this.m_mode = 3;
      } else {
        this.m_mode = 100;
      }
      this.school = 2;
    }
    else if ((paramInt1 == 43) && (paramInt2 == 70))
    {
      if (this.last_stage > 30) {
        this.m_mode = 4;
      } else {
        this.m_mode = 100;
      }
      this.school = 3;
    }
    else if ((paramInt1 == 22) && (paramInt2 == 46))
    {
      if (this.last_stage > 40) {
        this.m_mode = 5;
      } else {
        this.m_mode = 100;
      }
      this.school = 4;
    }
    else
    {
      this.m_mode = -1;
    }
  }
  
  public int hero_move(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 == 0)
    {
      if ((paramInt2 == 46) && (paramInt1 >= 22) && (paramInt1 <= 92))
      {
        check_building(paramInt1, paramInt2);
        return 1;
      }
      return 0;
    }
    if (paramInt3 == 1)
    {
      if (((paramInt1 == 43) || (paramInt1 == 71)) && (paramInt2 >= 15) && (paramInt2 <= 71))
      {
        check_building(paramInt1, paramInt2);
        return 1;
      }
      return 0;
    }
    return 1;
  }
  
  public int input_item(int paramInt)
  {
    for (int i = 0; i < 5; i++) {
      if ((this.item_slot[i] == paramInt) && (paramInt <= 8)) {
        return 3;
      }
    }
    for (int j = 0; j < 5; j++) {
      if (this.item_slot[j] == 0)
      {
        this.item_slot[j] = paramInt;
        if (paramInt == 1) {
          this.item_a_num = 2;
        } else if (paramInt == 2) {
          this.item_b_num = 2;
        } else if (paramInt == 3) {
          this.item_c_num = 2;
        } else if (paramInt == 4) {
          this.item_d_num = 2;
        }
        return 1;
      }
    }
    return 0;
  }
  
  public void use_item(int paramInt)
  {
    if ((this.item_slot[paramInt] > 0) && (this.item_slot[paramInt] <= 4))
    {
      this.wp = this.item_slot[paramInt];
      if (this.wp == 1)
      {
        this.item_a_num -= 1;
        if (this.item_a_num == 0) {
          delete_item(1);
        }
      }
      else if (this.wp == 2)
      {
        this.item_b_num -= 1;
        if (this.item_b_num == 0) {
          delete_item(2);
        }
      }
      else if (this.wp == 3)
      {
        this.item_c_num -= 1;
        if (this.item_c_num == 0) {
          delete_item(3);
        }
      }
      else if (this.wp == 4)
      {
        this.item_d_num -= 1;
        if (this.item_d_num == 0) {
          delete_item(4);
        }
      }
      this.d_gauge = 2;
    }
    else if (this.item_slot[paramInt] == 5)
    {
      delete_item(5);
      this.hp += this.max_hp / 3;
      if (this.hp > this.max_hp) {
        this.hp = this.max_hp;
      }
      this.h_timer_p = -4;
    }
    else if (this.item_slot[paramInt] == 6)
    {
      delete_item(6);
      this.mana += 10;
      if (this.mana > 36) {
        this.mana = 36;
      }
      this.d_gauge = 1;
    }
    else if (this.item_slot[paramInt] == 7)
    {
      delete_item(7);
      this.hp = this.max_hp;
      this.h_timer_p = -4;
    }
    else if (this.item_slot[paramInt] == 8)
    {
      delete_item(8);
      this.hp += this.max_hp / 3;
      if (this.hp > this.max_hp) {
        this.hp = this.max_hp;
      }
      this.h_timer_p = -4;
      this.ppang_item = 0;
      this.ppang_time = 0;
    }
    this.item_mode = 100;
    MPlay(1);
  }
  
  public void delete_item(int paramInt)
  {
    for (int i = 0; i < 5; i++) {
      if (this.item_slot[i] == paramInt)
      {
        this.item_slot[i] = 0;
        this.del = i;
        return;
      }
    }
  }
  
  public void keyPressed(int paramInt)
  {
    int i;
    int j;
    if ((this.screen == 6) && (this.state == 1))
    {
      if ((getGameAction(paramInt) == 2) || (paramInt == 52))
      {
        if ((this.item_mode == 0) && (this.ppang_item != 2))
        {
          if (this.h_x != 2)
          {
            this.h_x -= 1;
            if (this.h_idx == 0) {
              this.h_idx = 1;
            } else if (this.h_idx == 1) {
              this.h_idx = 0;
            }
          }
        }
        else if (this.item_mode != 0)
        {
          if (this.item_mode != 1) {
            this.item_mode -= 1;
          }
          this.message = "Item Mode";
          repaint();
        }
      }
      else if ((getGameAction(paramInt) == 5) || (paramInt == 54))
      {
        if ((this.item_mode == 0) && (this.ppang_item != 2))
        {
          if (this.h_x != 23)
          {
            this.h_x += 1;
            if (this.h_idx == 0) {
              this.h_idx = 1;
            } else if (this.h_idx == 1) {
              this.h_idx = 0;
            }
          }
        }
        else if (this.item_mode != 0)
        {
          if (this.item_mode != 5) {
            this.item_mode += 1;
          }
          this.message = "Item Mode";
          repaint();
        }
      }
      else if ((getGameAction(paramInt) == 6) || (paramInt == 56))
      {
        if (this.mana >= 12) {
          use_special();
        } else {
          this.message = "Insufficient Mana";
        }
      }
      else if ((paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53))
      {
        if (this.item_mode == 0)
        {
          if ((this.pw_up == 0) && (this.ppang_item != 2))
          {
            this.snow_pw = 0;
            this.real_snow_pw = 0;
            this.pw_up = 1;
            this.h_idx = 2;
          }
          else if ((this.pw_up == 1) && (this.real_snow_pw > 0))
          {
            this.h_idx = 4;
            make_attack();
          }
        }
        else
        {
          use_item(this.item_mode - 1);
          this.gameOn = true;
        }
      }
      else if (((paramInt == 35) || (paramInt == -7)) && (this.game_state == 0))
      {
        this.m_mode = 1;
        this.gameOn = false;
        this.screen = 100;
        repaint();
      }
      else if ((paramInt == 51) && (this.game_state == 0))
      {
        i = 0;
        j = 0;
        while (i < 5)
        {
          if (this.item_slot[i] != 0)
          {
            j = 1;
            break;
          }
          i++;
        }
        if (j == 1)
        {
          this.gameOn = false;
          this.message = "Item Mode";
          this.item_mode = 1;
          repaint();
        }
        else
        {
          this.message = "No Item";
        }
      }
    }
    else if (this.screen == 100)
    {
      if (getGameAction(paramInt) == 1)
      {
        if (this.m_mode == 1) {
          this.m_mode = 5;
        } else {
          this.m_mode -= 1;
        }
      }
      else if (getGameAction(paramInt) == 6)
      {
        if (this.m_mode == 5) {
          this.m_mode = 1;
        } else {
          this.m_mode += 1;
        }
      }
      else if (getGameAction(paramInt) == 2)
      {
        if (this.m_mode == 3) {
          this.s_play = 1;
        }
      }
      else if (getGameAction(paramInt) == 5)
      {
        if (this.m_mode == 3) {
          this.s_play = 2;
        }
      }
      else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {
        if (this.m_mode == 2)
        {
          goto_menu();
        }
        else
        {
          if (this.m_mode == 5)
          {
            this.SJ.destroyApp(true);
            this.SJ.notifyDestroyed();
            return;
          }
          if (this.m_mode == 1)
          {
            this.screen = 6;
            if (this.item_mode == 0)
            {
              this.gameOn = true;
            }
            else
            {
              this.message = "Item Mode";
              repaint();
            }
          }
          else if (this.m_mode == 4)
          {
            this.screen = -5;
          }
        }
      }
      repaint();
    }
    else if (this.screen == 2)
    {
      if (getGameAction(paramInt) == 1)
      {
        if (this.m_mode <= 1) {
          this.m_mode = 4;
        } else {
          this.m_mode -= 1;
        }
      }
      else if (getGameAction(paramInt) == 6)
      {
        if (this.m_mode >= 4) {
          this.m_mode = 1;
        } else {
          this.m_mode += 1;
        }
      }
      else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || ((paramInt >= 49) && (paramInt <= 52)) || (paramInt == -7))
      {
        if (paramInt > 48) {
          this.m_mode = (paramInt - 48);
        }
        if (this.m_mode == 1) {
          this.screen = -88;
        }
        if (this.m_mode == 3)
        {
          this.screen = 4;
          this.m_mode = 1;
        }
        if (this.m_mode == 2)
        {
          this.m_mode = 1;
          this.screen = 5;
        }
        if (this.m_mode == 4)
        {
          this.SJ.destroyApp(true);
          this.SJ.notifyDestroyed();
          return;
        }
      }
      repaint();
    }
    else if (this.screen == 3)
    {
      if (getGameAction(paramInt) == 1)
      {
        j = this.h_y - 8;
        if (hero_move(this.h_x, j, 1) > 0) {
          this.h_y = j;
        }
      }
      else if (getGameAction(paramInt) == 6)
      {
        j = this.h_y + 8;
        if (hero_move(this.h_x, j, 1) > 0) {
          this.h_y = j;
        }
      }
      else if (getGameAction(paramInt) == 5)
      {
        i = this.h_x + 7;
        if (hero_move(i, this.h_y, 0) > 0) {
          this.h_x = i;
        }
      }
      else if (getGameAction(paramInt) == 2)
      {
        i = this.h_x - 7;
        if (hero_move(i, this.h_y, 0) > 0) {
          this.h_x = i;
        }
      }
      else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7))
      {
        if ((this.m_mode == 0) || (this.m_mode == 1))
        {
          loadImage(31);
          this.screen = 31;
          repaint();
        }
        else if ((this.m_mode >= 2) && (this.m_mode <= 5))
        {
          int k = -1;
          if ((this.last_stage / 10 - this.school == 0) && (this.last_stage != 45)) {
            k = this.last_stage;
          }
          destroyImage(3);
          this.message = "Loading";
          init_game(k);
        }
      }
      else if ((paramInt == 42) || (paramInt == -6))
      {
        destroyImage(3);
        loadImage(2);
        this.screen = 2;
        this.m_mode = 1;
      }
      repaint();
    }
    else if (this.screen == 31)
    {
      if (getGameAction(paramInt) == 1) {
        this.s_item = 0;
      }
      if (getGameAction(paramInt) == 6) {
        this.s_item = 1;
      }
      if (getGameAction(paramInt) == 2)
      {
        if (this.b_item != 0) {
          this.b_item -= 1;
        }
      }
      else if ((getGameAction(paramInt) == 5) && (this.b_item != 3)) {
        this.b_item += 1;
      }
      if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {
        if (this.s_item == 1)
        {
          this.m_mode = -1;
          destroyImage(31);
          this.screen = 3;
        }
        else if (this.s_item == 0)
        {
          i = 0;
          if (this.m_mode == 0) {
            i = 4;
          }
          if (this.saved_gold >= this.item_price[(this.b_item + i)])
          {
            j = input_item(this.b_item + i + 1);
            if (j == 1)
            {
              this.saved_gold -= this.item_price[(this.b_item + i)];
              this.message = "Purchasing Items";
            }
            else if (j == 0)
            {
              this.message = "Bag is full";
            }
            else if (j == 3)
            {
              this.message = "Duplicated item";
            }
          }
          else
          {
            this.message = "not enough gold";
          }
        }
      }
      repaint();
    }
    else if (this.screen == 4)
    {
      if ((paramInt == 42) || (paramInt == -6))
      {
        this.screen = 2;
        if (this.speed == 5) {
          this.game_speed = 8;
        }
        if (this.speed == 4) {
          this.game_speed = 17;
        }
        if (this.speed == 3) {
          this.game_speed = 24;
        }
        if (this.speed == 2) {
          this.game_speed = 31;
        }
        if (this.speed == 1) {
          this.game_speed = 38;
        }
        addScore("config", 1);
        this.m_mode = 3;
      }
      if (getGameAction(paramInt) == 1) {
        if (this.m_mode == 1) {
          this.m_mode = 3;
        } else {
          this.m_mode -= 1;
        }
      }
      if (getGameAction(paramInt) == 6) {
        if (this.m_mode == 3) {
          this.m_mode = 1;
        } else {
          this.m_mode += 1;
        }
      }
      if (this.m_mode == 1)
      {
        if (getGameAction(paramInt) == 2) {
          this.s_play = 1;
        }
        if (getGameAction(paramInt) == 5) {
          this.s_play = 2;
        }
      }
      if (this.m_mode == 2)
      {
        if (getGameAction(paramInt) == 2) {
          this.v_mode = 1;
        }
        if (getGameAction(paramInt) == 5) {
          this.v_mode = 2;
        }
      }
      if (this.m_mode == 3)
      {
        if ((getGameAction(paramInt) == 2) && (this.speed != 1)) {
          this.speed -= 1;
        }
        if ((getGameAction(paramInt) == 5) && (this.speed != 5)) {
          this.speed += 1;
        }
      }
      repaint();
    }
    else if (this.screen == 5)
    {
      if (getGameAction(paramInt) == 1) {
        if (this.m_mode == 1) {
          this.m_mode = 3;
        } else {
          this.m_mode -= 1;
        }
      }
      if (getGameAction(paramInt) == 6) {
        if (this.m_mode == 3) {
          this.m_mode = 1;
        } else {
          this.m_mode += 1;
        }
      }
      if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == 51) || (paramInt == -7))
      {
        if (paramInt > 48) {
          this.m_mode = (paramInt - 48);
        }
        if (this.m_mode == 1) {
          this.screen = -33;
        }
        if (this.m_mode == 2) {
          this.screen = -33;
        }
        if (this.m_mode == 3) {
          this.screen = -33;
        }
      }
      if ((paramInt == 42) || (paramInt == -6))
      {
        this.screen = 2;
        this.m_mode = 2;
      }
      repaint();
    }
    else if (this.screen == -5)
    {
      this.screen = 100;
      repaint();
    }
    else if (this.screen == -88)
    {
      if (getGameAction(paramInt) == 1) {
        if (this.m_mode <= 1) {
          this.m_mode = 2;
        } else {
          this.m_mode -= 1;
        }
      }
      if (getGameAction(paramInt) == 6) {
        if (this.m_mode >= 2) {
          this.m_mode = 1;
        } else {
          this.m_mode += 1;
        }
      }
      if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == -7))
      {
        if (paramInt > 48) {
          this.m_mode = (paramInt - 48);
        }
        if (this.m_mode == 1)
        {
          this.last_stage = 11;
          this.stage = 11;
          this.saved_gold = 0;
          this.mana = 0;
          addScore("hero", 0);
        }
        destroyImage(2);
        loadImage(3);
        this.h_x = 57;
        this.h_y = 46;
        this.m_mode = -1;
        this.screen = 3;
      }
      if ((paramInt == 42) || (paramInt == -6))
      {
        this.screen = 2;
        this.m_mode = 1;
      }
      repaint();
    }
    else if (this.screen == -33)
    {
      if ((paramInt == 42) || (paramInt == -6))
      {
        loadImage(2);
        this.m_mode = 1;
        this.screen = 5;
        repaint();
        System.gc();
      }
    }
    else if (this.screen == 300)
    {
      MPlay(3);
      this.m_mode = -1;
      destroyImage(2);
      loadImage(3);
      this.h_x = 57;
      this.h_y = 46;
      this.saved_gold += this.gold;
      addScore("hero", 0);
      this.ani_step = 0;
      this.screen = 3;
      repaint();
      System.gc();
    }
    else if (this.screen == -1)
    {
      loadImage(-2);
      this.screen = -2;
      repaint();
    }
    else if (this.screen == -2)
    {
      loadImage(2);
      this.screen = 1;
      repaint();
    }
    else if (this.screen == 1)
    {
      if ((paramInt == 35) || (paramInt == -7))
      {
        this.screen = -88;
        this.m_mode = 1;
      }
      else if ((paramInt == 42) || (paramInt == -6))
      {
        this.screen = 2;
      }
      repaint();
    }
    else if (this.screen == 1000)
    {
      loadImage(2);
      System.gc();
      this.screen = 300;
      repaint();
    }
  }
  
  public void MPlay(int paramInt)
  {
    String str1 = null;
//    this.audioClip = null;
    if (this.s_play == 1)
    {
      String str2 = null;
      try
      {
        if (paramInt == 0) {
          str2 = "/9.mmf";
        } else if (paramInt == 1) {
          str2 = "/1.mmf";
        } else if (paramInt == 2) {
          str2 = "/6.mmf";
        } else if (paramInt == 3) {
          str2 = "/5.mmf";
        } else if (paramInt == 4) {
          str2 = "/4.mmf";
        } else if (paramInt == 5) {
          str2 = "/8.mmf";
        } else if (paramInt == 6) {
          str2 = "/3.mmf";
        } else if (paramInt == 7) {
          str2 = "/0.mmf";
        }
        str1 = new String(str2);
//        this.audioClip = new AudioClip(1, str1);
      }
      catch (Exception localException) {}
//      this.audioClip.play(1, 3);
    }
  }
  
  public void call_vib(int paramInt)
  {
    if (this.v_mode == 1) {
//      Vibration.start(paramInt, 3);
    }
  }
  
  public void showNotify()
  {
    this.p_mode = 1;
    this.d_gauge = 2;
  }
}
