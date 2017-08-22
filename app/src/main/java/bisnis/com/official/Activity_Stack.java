package bisnis.com.official;
import java.util.ArrayList;

import example.CustomExpandListView;

import bisnis.com.official.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.StackView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

public class Activity_Stack extends ActionBarActivity {
 StackView stkView;

 ArrayList<StackItem2> items2;
 StackAdapter2 adapt2 ;
 Animation animation;
 static StackAdapter adapt;
 @SuppressLint("NewApi")
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_stack);
  if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
              .add(R.id.container,new Home())
              .commit();
  }
  stkView = (StackView) findViewById(R.id.stackView1);

  
  
  ArrayList<StackItem> items = new ArrayList<StackItem>();
  items.add(new StackItem("text1", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text2", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text3", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text4", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text5", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text6", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text7", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text8", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  items.add(new StackItem("text9", this.getResources().getDrawable(
    R.drawable.ic_launcher)));
  for (int i = 0; i < 30; i++) {
	  items.add(new StackItem("text9", this.getResources().getDrawable(
			    R.drawable.bisnis_splash)));
	
}
   adapt= new StackAdapter(this, R.layout.activity_stack_item, items);
  
  stkView.setAdapter(adapt);
  

  items2 = new ArrayList<StackItem2>();
  for (int i = 0; i <100; i++) {
	  items2.add(new StackItem2("text9", this.getResources().getDrawable(
			    R.drawable.finansial),items));
}
  adapt2 = new StackAdapter2(this, R.layout.activity_stack_item2, items2);


   
  model();
  //list_contoh.setExpanded(true);
//  stkv.showNext();
 // final RelativeLayout rLayout=(RelativeLayout)findViewById(R.id.dicelayout);
//  final Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_out_top);
//  
//   animation=new TranslateAnimation(0, 0,stkView.getLayoutParams().height,0);
//  animation.setFillEnabled(true);
//
//  animation.setDuration(1000);  
//  animation.setAnimationListener(new AnimationListener() {
//
//      @Override
//      public void onAnimationStart(Animation animation) {
//          // TODO Auto-generated method stub
//
//      }
//
//      @Override
//      public void onAnimationRepeat(Animation animation) {
//          // TODO Auto-generated method stub
//
//      }
//
//      @Override
//      public void onAnimationEnd(Animation animatiofillAftern) {
//          // TODO Auto-generated method stub
//          //                      mainBoardLinear.removeView(rLayout);
//       //   stkView.setVisibility(View.GONE);
//
//      }
//  });
  
//  list_contoh.setOnScrollListener(new OnScrollListener() {
//	int start;
//	@Override
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//		// TODO Auto-generated method stub
//		start = scrollState;
//	}
//	
//	@Override
//	public void onScroll(AbsListView view, int firstVisibleItem,
//			int visibleItemCount, int totalItemCount) {
//		// TODO Auto-generated method stub
//		System.out.println("last "+list_contoh.getLastVisiblePosition()+"fitsr "+ firstVisibleItem +"total "+totalItemCount);
//		if(firstVisibleItem==2){
//			//for (int i = 0; i <100; i++) {
//				//stkView.setVisibility(View.GONE);
//			stkView.startAnimation(slide);
//				 // items2.add(new StackItem("text "+i, getResources().getDrawable(R.drawable.finansial)));
//			//}
//			//adapt2.notifyDataSetChanged();
//			//list_contoh.setExpanded(true);
//		}else if(firstVisibleItem==0){
//			stkView.setVisibility(View.VISIBLE);
//		}
//		
//	}
//});
  
 }
 
 
 
 void model(){
	 
	 new CountDownTimer(30000,3000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			stkView.showNext();
		}
		
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
		model();
		}
	}.start();
	 
 }
 public class StackItem2 {
	  public String itemText;
	  public Drawable itemPhoto;
	  ArrayList<StackItem> items;

	  public StackItem2(String text, Drawable photo,ArrayList<StackItem> item) {
	   this.itemPhoto = photo;
	   this.itemText = text;
	   this.items =item;
	  }

	 }

 public class StackItem {
  public String itemText;
  public Drawable itemPhoto;

  public StackItem(String text, Drawable photo) {
   this.itemPhoto = photo;
   this.itemText = text;
  }

 }

 public class StackAdapter extends ArrayAdapter<StackItem> {

  private ArrayList<StackItem> items;
  private Context ctx;

  public StackAdapter(Context context, int textViewResourceId,
    ArrayList<StackItem> objects) {
   super(context, textViewResourceId, objects);
   this.items = objects;
   this.ctx = context;
  }

  public View getView(int position, View convertView, ViewGroup parent) {

   View v = convertView;
   if (v == null) {
    LayoutInflater layinfl = (LayoutInflater) ctx
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    v = layinfl.inflate(R.layout.activity_stack_item, null);

   }
   StackItem sitm = items.get(position);
   if (sitm != null) {
    TextView textv = (TextView) v.findViewById(R.id.textView1);
    ImageView imgv = (ImageView) v.findViewById(R.id.imageView1);
    textv.setRotation(-90);
    imgv.setRotation(-90);
    if (textv != null) {
     textv.setText(sitm.itemText);
     imgv.setImageDrawable(sitm.itemPhoto);

    }

   }
   return v;
  }

 }
 
 public class StackAdapter2 extends ArrayAdapter<StackItem2> {

	  private ArrayList<StackItem2> items;
	  private Context ctx;
	  StackView stack;

	  public StackAdapter2(Context context, int textViewResourceId,
	    ArrayList<StackItem2> objects) {
	   super(context, textViewResourceId, objects);
	   this.items = objects;
	   this.ctx = context;
	  }

	  public View getView(int position, View convertView, ViewGroup parent) {

	   View v = convertView;
	   if (v == null) {
	    LayoutInflater layinfl = (LayoutInflater) ctx
	    		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	v = layinfl.inflate(R.layout.activity_stack_item, null);
	    
	   }
	   StackItem2 sitm = items.get(position);
	   if(position==-1){
		    LayoutInflater layinfl = (LayoutInflater) ctx
		    		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   v = layinfl.inflate(R.layout.activity_stack_item2, null);
		   stack = (StackView)v.findViewById(R.id.stack_list_contoh);
	    	 StackAdapter adapt = new StackAdapter(getApplication(), R.layout.activity_stack_item, sitm.items);
		     stack.setAdapter(adapt);
		     stack.setVisibility(View.VISIBLE);
	    	  model2();
	   }else{
		   LayoutInflater layinfl = (LayoutInflater) ctx
		    		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   v = layinfl.inflate(R.layout.activity_stack_item, null);
	   }
	   if (sitm != null) {
	    TextView textv = (TextView) v.findViewById(R.id.textView1);
	    ImageView imgv = (ImageView) v.findViewById(R.id.imageView1);
	  
	    if (textv != null) {
	     textv.setText(sitm.itemText);
	     imgv.setImageDrawable(sitm.itemPhoto);
	    }
	   }
	   return v;
	  }
	  void model2(){
			 
			 new CountDownTimer(30000,3000) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					stack.showNext();
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
				model();
				}
			}.start();
			 
		 }
	  

	 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  //getMenuInflater().inflate(R.menu.stack_view, menu);
  return true;
 }
 
 
 public static class Home extends Fragment{
	 CustomExpandListView list_contoh;
	  ArrayList<StackItem> da;
	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 
		 View rootview = inflater.inflate(R.layout.rzgonz_list, container, false);
		  list_contoh = (CustomExpandListView)rootview.findViewById(R.id.list_contoh);
		  		  
		  
		  ArrayList<String> list_data= new ArrayList<String>();
		  for (int i = 0; i < 30; i++) {
			  list_data.add("asd");
		  }
		
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,list_data);
		  list_contoh.setAdapter(adapt);
		  list_contoh.setExpanded(true);
		
		return rootview;
	}
	 
	 
 }

}

