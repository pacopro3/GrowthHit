package com.example.hit.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity // This tells Hibernate to make a table out of this class
public class HitEntity{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  private String hits;

  private int counts;


  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public String getHits() {
    return hits;
  }

  public void setHits(String hits) {
    this.hits = hits;
  }

  public int getCount() {
    return counts;
  }

  public void setCount(int counts) {
    this.counts = counts;
  }
}