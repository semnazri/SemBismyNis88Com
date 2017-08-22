package model;

/**
 * Riki Setiyawan
 * Email: rzgonz@gmail.com
 *
 * @copyright 2014
 * PT. Bisnis Indonesia Sibertama
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Frame_ListBerita implements Parcelable {
    @SuppressWarnings("rawtypes")
    public static final Creator CREATOR = new Creator() {
        public Frame_ListBerita createFromParcel(Parcel in) {
            return new Frame_ListBerita(in);
        }

        public Frame_ListBerita[] newArray(int size) {
            return new Frame_ListBerita[size];
        }
    };
    private String idberita;
    private String imgberita;
    private String tglberita;
    private String judulberita;
    private String pukul;
    private String category;
    private String datepost;
    private String image_content;
    private String parent_category;
    private String slug;
    private String live;
    private String subtitle;


    public Frame_ListBerita(
            String idberita, String category, String datepost, String imgberita, String tglberita, String pukul, String judulberita, String image_content, String parent, String slug, String live, String subtitle) {
        // TODO Auto-generated constructor stub
        this.idberita = idberita;
        this.imgberita = imgberita;
        this.judulberita = judulberita;
        this.pukul = pukul;
        this.tglberita = tglberita;
        this.category = category;
        this.datepost = datepost;
        this.image_content = image_content;
        this.parent_category = parent;
        this.slug = slug;
        this.live = live;
        this.subtitle = subtitle;
    }

    public Frame_ListBerita(Parcel in) {
        String[] data = new String[12];

        in.readStringArray(data);
        this.idberita = data[0];
        this.imgberita = data[1];
        this.judulberita = data[2];
        this.pukul = data[3];
        this.tglberita = data[4];
        this.category = data[5];
        this.datepost = data[6];
        this.image_content = data[7];
        this.parent_category = data[8];
        this.slug = data[9];
        this.live = data[10];
        this.subtitle = data[11];
    }


    public Frame_ListBerita() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.idberita,
                this.imgberita,
                this.judulberita,
                this.pukul,
                this.tglberita,
                this.category,
                this.datepost,
                this.image_content,
                this.parent_category,
                this.slug,
                this.live
                ,
                this.subtitle
        });
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIdberita() {
        return idberita;
    }

    public void setIdberita(String idberita) {
        this.idberita = idberita;
    }

    public String getImgberita() {
        return imgberita;
    }

    public void setImgberita(String imgberita) {
        this.imgberita = imgberita;
    }

    public String getTglberita() {
        return tglberita;
    }

    public void setTglberita(String tglberita) {
        this.tglberita = tglberita;
    }

    public String getJudulberita() {
        return judulberita;
    }

    public void setJudulberita(String judulberita) {
        this.judulberita = judulberita;
    }

    public String getPukul() {
        return pukul;
    }

    public void setPukul(String pukul) {
        this.pukul = pukul;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatepost() {
        return datepost;
    }

    public void setDatepost(String datepost) {
        this.datepost = datepost;
    }

    public String getImage_content() {
        return image_content;
    }

    public void setImage_content(String image_content) {
        this.image_content = image_content;
    }

    public String getParent_category() {
        return parent_category;
    }

    public void setParent_category(String parent_category) {
        this.parent_category = parent_category;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }


}
