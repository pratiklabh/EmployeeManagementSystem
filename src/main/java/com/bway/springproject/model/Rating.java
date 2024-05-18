package com.bway.springproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data

@Entity

public class Rating {
	
	@Id
	@GeneratedValue
	private int id;
	private double rate;
	private int count;
	

}
