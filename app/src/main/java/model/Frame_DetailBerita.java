package model;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

public class Frame_DetailBerita {
	
	public int id;
	public String judul,date,penulis,image,img_caption,content,editor,slug,image_content;
/*
	public Frame_DetailBerita(int id,String judul, String date) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.judul=judul;
		this.date=date;
	}
	*/
	public Frame_DetailBerita(int id,String date,String judul,String penulis,String editor,String img,String img_caption,String content,String slug,String image_content) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.date=date;
		this.judul=judul;
		this.image=img;
		this.editor=editor;
		this.penulis=penulis;
		this.content=content;
		this.img_caption=img_caption;
		this.slug=slug;
		this.image_content=image_content;
	}

}
