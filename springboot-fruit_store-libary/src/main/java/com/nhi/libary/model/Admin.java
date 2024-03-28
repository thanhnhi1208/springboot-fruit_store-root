package com.nhi.libary.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private Long id;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String username;

	private String password;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "city_id")
	private City city;
	
	private LocalDate birthDay;
	
	private String phoneNumber;
	
	private String gender;

	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "admins_roles", joinColumns = @JoinColumn(name="admin_id", referencedColumnName = "admin_id"),
				inverseJoinColumns = @JoinColumn(name= "role_id", referencedColumnName = "role_id"))
	private List<Role> roleList;

}
