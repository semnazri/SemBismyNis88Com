package model;
/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

public class Frame_DetailSlider {
	
	public String date,catagoty_id,post_id;
/*
	public Frame_DetailBerita(int id,String judul, String date) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.judul=judul;
		this.date=date;
	}
	*/
	public Frame_DetailSlider(String date,String catagoty_id,String post_id) {
		// TODO Auto-generated constructor stub
		this.catagoty_id=catagoty_id;
		this.date=date;
		this.post_id=post_id;
	}

}
