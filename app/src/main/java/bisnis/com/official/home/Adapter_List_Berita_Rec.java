package bisnis.com.official.home;

/**
 * Riki Setiyawan
 * Email: rzgonz@gmail.com
 *
 * @copyright 2014
 * PT. Bisnis Indonesia Sibertama
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bisnis.com.official.Activity_Detail_Backup;
import bisnis.com.official.ImageDownloaderTaksHeadline;
import bisnis.com.official.R;
import db.Table_Setting;
import model.Frame_ListBerita;

public class Adapter_List_Berita_Rec extends RecyclerView.Adapter<Adapter_List_Berita_Rec.ViewHolder> {
    int image_position;
    Typeface open_sans;
    OnItemClickListener mItemClickListener;
    private ArrayList<Frame_ListBerita> mDataset;
    private int canalId;


    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter_List_Berita_Rec(ArrayList<Frame_ListBerita> myDataset) {

        mDataset = myDataset;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_berita_new, viewGroup, false);
/*		Animation visible = AnimationUtils.loadAnimation(viewGroup.getContext(), R.animator.visable);
        v.setAnimation(visible);*/
        v.setContentDescription("" + i);
        ViewHolder rootView = new ViewHolder(v);

        return rootView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Frame_ListBerita berita = mDataset.get(position);
        holder.txtdate.setText(berita.getDatepost());
        holder.txtjudul.setText(berita.getJudulberita());
        holder.txt_subtitle.setText(berita.getSubtitle());
        Spannable spanna = new SpannableString(holder.txtjudul.getText());
        spanna.setSpan(new BackgroundColorSpan(0xCCffc208), 0, holder.txtjudul.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtjudul.setText(spanna);

        String subs = holder.txt_subtitle.getText().toString();
        Log.d("subs", subs);
        if (berita.getSubtitle().equals("")) {
//			holder.txt_subtitle.setVisibility(View.GONE);
            holder.sub_layout.setVisibility(View.GONE);
        }

        if (position == 0) {

            if (!holder.imgberita.getContentDescription().equals("list")) {
                holder.imgberita.setContentDescription("head");

            }
            if (holder.imgberita.getContentDescription().equals("head")) {
                image_position = position;
                float width = holder.imgberita.getResources().getDisplayMetrics().widthPixels / 200;
                if (width < 2) {
                    holder.imgberita.getLayoutParams().height = (int) (width * 90);
                } else {
                    holder.imgberita.getLayoutParams().height = (int) (width * 150);
                }
            }


            if ((berita.getImage_content().toString().length() > 4)) {
                {


                    image_position = position;
                    holder.imgberita.setVisibility(View.GONE);
                    holder.txtdate.setVisibility(View.GONE);
                    holder.table_headline.setVisibility(View.GONE);
                    holder.row_bates.setVisibility(View.GONE);
                    holder.linerberita.setVisibility(View.VISIBLE);
                    String a = Html.fromHtml(berita.getParent_category()).toString();
                    holder.txt_canal_subcanal.setText(a);
                    holder.txtdate2.setText(berita.getTglberita());

                    holder.imgberita.setContentDescription("list");
                    if (mDataset.get(position).getLive().equals("0")) {
                        holder.img_live2.setVisibility(View.GONE);
                        holder.txtjudul2.setText(berita.getJudulberita());
                    } else {
                        holder.txtjudul2.setText("      " + berita.getJudulberita());
                    }

                    holder.txtminut.setText(berita.getPukul());


                    if (berita.getImage_content().toString().equals("populer2")) {
                        holder.txt_views.setVisibility(View.VISIBLE);
                        holder.txt_views.setText("(" + berita.getSlug() + " view )");
                        holder.txtminut.setVisibility(View.GONE);
                        holder.txt_canal_subcanal.setVisibility(View.GONE);
                    } else if (berita.getImage_content().toString().length() > 4) {
                        holder.txt_canal_subcanal.setVisibility(View.GONE);
                        holder.txtminut.setVisibility(View.GONE);
                    }

                }
            }

        }

        if (mDataset.get(position).getLive().equals("0")) {
            holder.img_live.setVisibility(View.GONE);
        }
        if (position > 0) {

            image_position = position;
            holder.imgberita.setVisibility(View.GONE);
            holder.txtdate.setVisibility(View.GONE);
            holder.table_headline.setVisibility(View.GONE);
            holder.row_bates.setVisibility(View.GONE);
            holder.linerberita.setVisibility(View.VISIBLE);
            String a = Html.fromHtml(berita.getParent_category()).toString();
            holder.txt_canal_subcanal.setText(a);
            holder.txtdate2.setText(berita.getTglberita());

            holder.imgberita.setContentDescription("list");
            if (mDataset.get(position).getLive().equals("0")) {

                holder.img_live2.setVisibility(View.GONE);
                holder.txtjudul2.setText(berita.getJudulberita());
            } else {
                holder.txtjudul2.setText("      " + berita.getJudulberita());
            }

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(sdf.format(cal.getTime()));
            if (sdf.format(cal.getTime()).equals(berita.getTglberita())) {
                holder.txtminut.setText(" - " + berita.getPukul().substring(2));

            } else {
                holder.txtminut.setText(" - " + berita.getTglberita() + berita.getPukul().substring(2));

            }


            if (berita.getImage_content().toString().equals("populer2")) {

                holder.txt_views.setVisibility(View.VISIBLE);
                holder.txt_views.setText("(" + berita.getSlug() + " view )");
                holder.txtminut.setVisibility(View.GONE);
                holder.txt_canal_subcanal.setVisibility(View.GONE);
            } else if (berita.getImage_content().toString().length() > 4) {

                holder.txt_canal_subcanal.setVisibility(View.GONE);
                holder.txtminut.setVisibility(View.GONE);
                holder.txtjudul2.setTypeface(open_sans, Typeface.NORMAL);
            }


            if ((position == 1 | position == 2) & (berita.getImage_content().toString().length() < 4)) {
                System.out.println("CANAL " + berita.getImage_content().toString().length());
                holder.row_bates_berita.setVisibility(View.GONE);
                holder.sub_layout.setVisibility(View.GONE);
                holder.txtminut.setVisibility(View.GONE);
                holder.txt_canal_subcanal.setVisibility(View.GONE);
                Spannable spanna2 = new SpannableString(holder.txtjudul2.getText());
                spanna2.setSpan(new BackgroundColorSpan(0xCCffc208), 0, holder.txtjudul2.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txtjudul2.setText(spanna2);
                if (position == 2) {
                    holder.row_bates.setVisibility(View.VISIBLE);
                }

            }


        }

        if (position == 0) {
            new ImageDownloaderTaksHeadline(holder.imgberita, null, berita.getImage_content()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, berita.getImgberita());
        }

        holder.linerberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation visible = AnimationUtils.loadAnimation(v.getContext(), R.anim.onclick);
                v.startAnimation(visible);
                Intent intent = new Intent();
                intent.setClass(v.getContext(), Activity_Detail_Backup.class);
                intent.putExtra("POST_ID", (mDataset.get(position).getIdberita()));
                // intent.putExtra("CANAL",canal);
                intent.putExtra("DATA", mDataset);
                intent.putExtra("NAV_ID", canalId);
                v.getContext().startActivity(intent);
            }
        });

        holder.imgberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation visible = AnimationUtils.loadAnimation(v.getContext(), R.anim.onclick);
                v.startAnimation(visible);
                Intent intent = new Intent();
                intent.setClass(v.getContext(), Activity_Detail_Backup.class);
                intent.putExtra("POST_ID", (mDataset.get(position).getIdberita()));
                // intent.putExtra("CANAL",canal);
                intent.putExtra("DATA", mDataset);
                intent.putExtra("NAV_ID", canalId);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(Frame_ListBerita item) {
        mDataset.add(item);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        // int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);

    }

    public void clean() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    public void addCanalId(int canalId) {
        this.canalId = canalId;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imgberita, imgberita2, img_live, img_live2;
        public TextView txtjudul, txtdate, txtjudul2, txtdate2, txtminut, txt_canal_subcanal, txt_views, txt_subtitle;
        public LinearLayout linerberita;
        public TableRow table_headline, row_bates_berita;
        public LinearLayout row_bates, sub_layout;


        public ViewHolder(View v) {
            super(v);
            txt_subtitle = (TextView) v.findViewById(R.id.subtitle);
            imgberita = (ImageView) v.findViewById(R.id.imgfitured);
            imgberita2 = (ImageView) v.findViewById(R.id.imgfitured2);
            txtdate = (TextView) v.findViewById(R.id.txtdate);
            txtjudul = (TextView) v.findViewById(R.id.txtjudul);
            txtdate2 = (TextView) v.findViewById(R.id.txtdate2);
            txtjudul2 = (TextView) v.findViewById(R.id.txtjudul2);
            linerberita = (LinearLayout) v.findViewById(R.id.LinierBerita);
            txtminut = (TextView) v.findViewById(R.id.txtminut);
            row_bates = (LinearLayout) v.findViewById(R.id.tableRow3);
            txt_canal_subcanal = (TextView) v.findViewById(R.id.txt_canal_subcanal);
            table_headline = (TableRow) v.findViewById(R.id.table_headline);
            img_live = (ImageView) v.findViewById(R.id.img_live_home);
            img_live2 = (ImageView) v.findViewById(R.id.img_live_home2);
            row_bates_berita = (TableRow) v.findViewById(R.id.row_bates_berita);
            txt_views = (TextView) v.findViewById(R.id.txt_views);
            sub_layout = (LinearLayout) v.findViewById(R.id.subtitle_layuot);


            Table_Setting db = new Table_Setting(v.getContext(), null, null, 0);
            int size_font = db.get_setting_font_size();
            db.close();
            open_sans = Typeface.createFromAsset(v.getContext().getAssets(), "OpenSans-Regular.ttf");
            switch (size_font) {
                case 0:
                    size_font = 16;
                    break;
                case 1:
                    size_font = 18;
                    break;
                case 2:
                    size_font = 20;
                    break;
                case 3:
                    size_font = 22;
                    break;

                default:
                    break;
            }

            txtdate.setTypeface(open_sans);
            txtjudul.setTypeface(open_sans, Typeface.BOLD);
            txtdate2.setTypeface(open_sans);
            txtjudul2.setTypeface(open_sans, Typeface.BOLD);
            txtminut.setTypeface(open_sans);
            txt_canal_subcanal.setTypeface(open_sans);
            txtdate.setTextSize(size_font);
            txtjudul.setTextSize(size_font);

            txtdate2.setTextSize(size_font - 4);
            txtjudul2.setTextSize(size_font);
            txtminut.setTextSize(size_font - 4);
            txt_canal_subcanal.setTextSize(size_font - 4);


            Log.d("getposition", " " + v.getContentDescription());


        }

    }
}
