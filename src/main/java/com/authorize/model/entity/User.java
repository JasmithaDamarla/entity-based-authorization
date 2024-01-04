package com.authorize.model.entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String name;
	private String email;
	@Column(length = 60)
	private String password;
	@ManyToOne
	@JoinColumn(name = "reporting_id")
	private User reportingTo;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnore
	private List<Role> role;
	@ManyToOne
	@JoinColumn(name = "org_id")
	private Organization organization;

	@Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = (prime * result) + ((getEmail() == null) ? 0 : getEmail().hashCode());
	    return result;
	  }

	  @Override
	  public boolean equals(final Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if (obj == null) {
	      return false;
	    }
	    if (getClass() != obj.getClass()) {
	      return false;
	    }
	    final User user = (User) obj;
	    if (!getEmail().equals(user.getEmail())) {
	      return false;
	    }
	    return true;
	  }
}
