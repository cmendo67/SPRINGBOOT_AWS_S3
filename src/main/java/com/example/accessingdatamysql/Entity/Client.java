package com.example.accessingdatamysql.Entity;

public class Client{
	  private String Fname; 
	  private String Minit; 
	  private String Lname; 
	  private Integer id; 
	  private String Bdate; 
	  private String Address; 
	  private String Sex;
	  
	  public Client(String Fname, String Minit, String Lname, Integer id, String Bdate, String Address,String Sex) {
		  this.Fname = Fname;
		  this.Minit = Minit;
		  this.Lname = Lname;
		  this.id = id;
		  this.Bdate = Bdate;
		  this.Address = Address;
		  this.Sex = Sex;
	  }
	  public Client() {
	  }
	//getters
	  public String getFname() {
		  return Fname;
	  }
	  public String getMinit() {
			return Minit;
		}
	  public String getLname() {
			return Lname;
		}
	  public Integer getId() {
			return id;
		}
	  public String getBdate() {
			return Bdate;
		}
	  public String getAddress() {
			return Address;
		}
	  public String getSex() {
			return Sex;
		}
	  
	  //setters
	  public void setFname(String Fname) {
		    this.Fname = Fname;
		  }
	  public void setMinit(String Minit) {
		    this.Minit = Minit;
		  }
	  public void setLname(String Lname) {
		    this.Lname = Lname;
		  }
	  public void setId(Integer id) {
		    this.id = id;
		  }
	  public void setBdate(String Bdate) {
		    this.Bdate = Bdate;
		  }
	  public void setAddress(String Address) {
		    this.Address = Address;
		  }
	  public void setSex(String Sex) {
		    this.Sex = Sex;
		  }
}
