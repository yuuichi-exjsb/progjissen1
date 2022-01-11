import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.JOptionPane;
//import javax.swing.Jlabel;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameMaster2 extends Frame implements ActionListener{
  // ■ フィールド変数
  Panel p_center, p_south;
  Button bt1, bt2, bt3, bt4, bt5, bt6, bt0,bt7,bt8,bt9,btent,btclr,btreset;
  TextArea txt,txt2;
  String s_num="";
  String ss="";
  int[] ans = new int[3];
  //int[] ans_pre = new int[3];
  int eat;
  int bite;
  int ans_cnt = 0;
  int mode; //0:ゲーム中、-1:ゲーム終了
  MyCanvas mc;
  public static void main(String [] args) {
    GameMaster2 scl = new GameMaster2();
    //GameMaster2 scl1 = new GameMaster2();
    scl.GameProcess();
    //scl1.GameProcess();
    scl.CPUnumber();
    //scl1.CPUnumber();
  }

  // ■ コンストラクタ
  GameMaster2() {
    super("NUMERON");
    this.setSize(600, 500); 
    // それぞれのカードにTextAreaを配置する
    // カードに名称をつけて登録する
    txt= new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
    txt2 = new TextArea("",10,40,TextArea.SCROLLBARS_VERTICAL_ONLY);
    mc = new MyCanvas();
    mc.setSize(100, 150);
    mc.setBackground(Color.white);
    add(txt);                   // テキストエリアの設定
    add(txt2);


    // パネルp_south内をFlowLayoutにしボタンを6つ設定する
    p_south = new Panel();
    p_south.setLayout(new FlowLayout());

    btent = new Button("Enter");p_south.add(btent);
    btclr = new Button("All Clear"); p_south.add(btclr);
    bt0 = new Button("0");     p_south.add(bt0);// ボタンを順に配置
    bt1 = new Button("1");     p_south.add(bt1);
    bt2 = new Button("2");     p_south.add(bt2);
    bt3 = new Button("3");     p_south.add(bt3);
    bt4 = new Button("4");     p_south.add(bt4);
    bt5 = new Button("5");  p_south.add(bt5);
    bt6 = new Button("6");  p_south.add(bt6);
    bt7 = new Button("7");      p_south.add(bt7);
    bt8 = new Button("8");      p_south.add(bt8);
    bt9 = new Button("9");       p_south.add(bt9);
    btreset = new Button("Reset"); p_south.add(btreset);


    // ボタン操作用にリスナ登録する
    btent.addActionListener(this);
    btclr.addActionListener(this);
    bt0.addActionListener(this); 
    bt1.addActionListener(this);
    bt2.addActionListener(this);
    bt3.addActionListener(this);
    bt4.addActionListener(this);
    bt5.addActionListener(this);
    bt6.addActionListener(this);
    bt7.addActionListener(this);
    bt8.addActionListener(this);
    bt9.addActionListener(this); 
    btreset.addActionListener(this); 

    // アプレット全体をBorderLayoutにしp_centerとp_southを登録する
    setLayout(new BorderLayout());
  add(mc,BorderLayout.NORTH);
   add(p_south,  BorderLayout.SOUTH); 
   add(txt, BorderLayout.WEST);
   add(txt2,BorderLayout.CENTER);
   

    // カードの先頭ページを表示する
    //card1.first(p_center);

    // フレームを表示する
    this.setVisible(true);    
  }

  // ■ メソッド
  public void actionPerformed(ActionEvent e) {
    int stt = txt.getSelectionStart();
    int end = txt.getSelectionEnd();
    Button btn = (Button)e.getSource();

    if(btn == btent){
      if(numChk(ss)) {
        JOptionPane.showMessageDialog(null, "異なる3桁の数字を予想してください！！"); 
        ss = "";
        s_num = "";
        txt.setText("");
        return;}
      ans_cnt++;
      checkAnswer(ans,ss);
       ss = "";
       s_num = "";
       txt.setText("");
    }
    else if(btn == btreset){
      //正解の数字配列をリセット
      //ans（解答回数）を変更
      CPUnumber();
      int stt3 = txt.getSelectionStart();
      int end3 = txt.getSelectionEnd();
      txt.setText("");
      txt2.setText("");
      txt.append("ゲームをリセットしました\n");
      ans_cnt = 0;
      ss = "";
      s_num = "";
      txt.requestFocusInWindow(); // テキストエリアにフォーカスを与える
      txt2.requestFocusInWindow(); // テキストエリアにフォーカスを与える
      //System.out.println(Arrays.toString(ans));
      
    }
    else if(btn == btclr){
        ss = "";
        s_num = "";
        txt.setText("");
    }
    else{
      String number = btn.getLabel();
      ss = ss+number+" ";
      s_num = s_num+number;
      txt.append(number);}
    System.out.println(ss);
    System.out.println(s_num);
    txt.requestFocusInWindow(); // テキストエリアにフォーカスを与える
  }

    void GameProcess(){
    int stt = txt.getSelectionStart();
    int end = txt.getSelectionEnd();
    txt.append("これから数あてゲームを開始します\n");
    try {Thread.sleep(2000);}catch(Exception e) {System.out.println(e);}
    txt.insert("コンピュータが異なる3桁の数字を選びました\nでは、数字を三つ選んでenterを押してください\n",txt.getSelectionStart());
    txt.requestFocusInWindow(); // テキストエリアにフォーカスを与える
  }

    void CPUnumber(){
      for (int i = 0; i < ans.length; i++) {
            //自分より前の要素にかぶるやつがないか確かめる。
            //あったらもう1回random
            boolean flag = false;
            ans[i] = (int) (Math.random() * 6 + 1);
            do {
                flag = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (ans[i] == ans[j]) {
                        flag = true;
                        ans[i] = (int) (Math.random() * 6 + 1);
                    }
                }

            } while (flag == true);
        }
        System.out.println(Arrays.toString(ans));
    }
    void checkAnswer(int[] right_answer,String ss){

      eat = 0;
      bite = 0;

    int[] user_answer = new int[3];
    String[] user_answer_str = ss.split(" ", -1);
    System.out.println(Arrays.toString(user_answer_str));
    for(int k=0;k<ans.length;k++){
      user_answer[k] = Integer.parseInt(user_answer_str[k]);
    }

    System.out.println(Arrays.toString(user_answer));

    for(int i = 0; i < right_answer.length; i++) {
      for(int j = 0; j < user_answer.length; j++) {
        if(right_answer[j] == user_answer[i]) {
        if(j==i) {
        eat++;
        }else {
        bite++;
        }
        }
        }
      }
        System.out.println(eat+" eat  "+bite+" bite");
        if(eat == 3){
          JOptionPane.showMessageDialog(null, "3 eat あなたの勝利です！！\nまた遊ぶ場合はリセットを押してください");
        }

        ope_txt2(user_answer,eat,bite);
    }

    void ope_txt2(int[] user_answer, int eat,int bite){
      int stt2 = txt2.getSelectionStart();
      int end2 = txt2.getSelectionEnd();
      txt2.append("=="+ans_cnt+"回目の予想結果==\n");
      txt2.append("予想内容:"+Arrays.toString(user_answer)+",       ");
      txt2.append(eat+"eat      "+bite+"bite\n");
      txt2.append("\n");
      txt2.requestFocusInWindow(); // テキストエリアにフォーカスを与える
    }

    boolean numChk(String ss){
      boolean ans_flag = false;
      String[] user_answer_chk_str = ss.split(" ", -1); 
      int [] user_answer_chk = new int[user_answer_chk_str.length-1];

      for(int i=0;i<user_answer_chk.length;i++){
        user_answer_chk[i] = Integer.parseInt(user_answer_chk_str[i]);
        for(int j=i-1;j>=0;j--){
          if(user_answer_chk[i] == user_answer_chk[j]){
            ans_flag = true;
            break;
          }
        }
      }

      if(user_answer_chk.length != 3)ans_flag = true;

      return ans_flag;
    }

    class MyCanvas extends Canvas {
      //Image img = this.getToolkit().getImage("./src/img/nume_attachan1.png");

    public void paint(Graphics g) {
        Dimension d = getSize();                  // キャンバスサイズ取得 
       try {
            BufferedImage image = ImageIO.read(new File("./img/nume_attachan1.png"));
            g.drawImage(image, 0,0,100*600/100,100*338/150, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}