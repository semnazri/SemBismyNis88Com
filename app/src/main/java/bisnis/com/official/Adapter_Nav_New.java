package bisnis.com.official;
/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.Table_ListBerita;
import db.Table_List_Nav;
import model.Frame_nav;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter_Nav_New extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    Table_List_Nav db ;
    private HashMap<String, List<Frame_nav>> _listDataChild;
 
    public Adapter_Nav_New(Context context, List<String> listDataHeader,
            HashMap<String, List<Frame_nav>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    	db = new Table_List_Nav(context, null, null, 0);
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).nav;
    }
    public Object getChild_icon(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).icon;
    }
    public Object getChild_id(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).id;
    }
    public Object getChild_postion(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).position;
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, final ViewGroup parent) {
    	
    	final String child_id = (String) getChild_id(groupPosition, childPosition);
    	final int child_positon = (int) getChild_postion(groupPosition, childPosition);
        final String childText = (String) getChild(groupPosition, childPosition);
        final int child_img = (int) getChild_icon(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child_nav, null);
        }
 
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        ImageView img_nav = (ImageView)convertView.findViewById(R.id.img_nav);
        CheckBox  checK_favorite = (CheckBox)convertView.findViewById(R.id.chek_favorite);
        if(groupPosition==2){
        	checK_favorite.setVisibility(View.VISIBLE);
        }else{
        	checK_favorite.setVisibility(View.GONE);
        }
        
        /*
      
        final Frame_nav data_del =  new Frame_nav(child_id, child_id, child_img, child_positon);
        
        
        checK_favorite.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					db.add_nav(new Frame_nav(child_id, childText, child_img, child_positon));
					Toast.makeText(parent.getContext(), "save", Toast.LENGTH_SHORT).show();
					View v = (View)parent.findViewById(child_positon);
					View a = (View) v.getParent();
					ExpandableListView qw = (ExpandableListView)a.findViewById(R.id.expand_list_nav);
					//Adapter_Nav_New asd = (Adapter_Nav_New) qw.getAdapter();
					//asd.notifyDataSetChanged();
				//	a.setVisibility(View.VISIBLE);
				}else{
					db.delete_nav(data_del);
					View v = (View)parent.findViewById(child_positon);
					View a = (View) v.getParent();
					ExpandableListView qw = (ExpandableListView)a.findViewById(R.id.expand_list_nav);
					//Adapter_Nav_New asd = (Adapter_Nav_New) qw.getAdapter();
					//asd.notifyDataSetChanged();
					//a.setVisibility(View.GONE);
					Toast.makeText(parent.getContext(), "delete", Toast.LENGTH_SHORT).show();
				}
			}
		});
 *
        txtListChild.setText(childText);
        img_nav.setImageResource(child_img);
        convertView.setId(child_positon);
        convertView.setContentDescription(child_id);
        
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_nav, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
*/