package model;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

public class Frame_nav {
	
	public String id,nav;
	public int icon,position,status;
/*
	public Frame_DetailBerita(int id,String judul, String date) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.judul=judul;
		this.date=date;
	}
	*/
	public Frame_nav(String id, String nav_title, int icon,int position,int status){
		// TODO Auto-generated constructor stub
		this.id=id;
		this.nav=nav_title;
		this.icon=icon;
		this.position=position;
		this.status=status;
	}

}
