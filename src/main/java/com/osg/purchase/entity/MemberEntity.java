package com.osg.purchase.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity(name = "master_users")
@Table(name = "master_users")
public class MemberEntity implements UserDetails
{
	@ManyToOne
	@JoinColumn(name = "departmentId", referencedColumnName="id")
	private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "member")
    private List<PurchaseEntity> purchaseEntity;

    private static final long serialVersionUID = 1667698003975566301L;

    public enum Authority {ROLE_USER, ROLE_ADMIN}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int isDeleted=0;

    @Column(insertable = false)
    private Timestamp createdAt;

    @Column
    private String createdUserId;

    @Column(insertable = false)
    private Timestamp updatedAt;

    @Column
    private String updatedUserId;

    public int getId()
   {
       return this.id;
   }
    public void setId(int id)
   {
       this.id = id;
   }

    public String getUserId()
   {
       return this.userId;
   }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsDeleted()
    {
        return this.isDeleted;
    }
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getCreatedAt()
   {
       return this.createdAt;
   }
    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }
    public String getCreatedUserId()
   {
       return this.createdUserId;
   }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Timestamp getUpdatedAt()
   {
       return this.updatedAt;
   }
    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }
    public String getUpdatedUserId()
   {
       return this.updatedUserId;
   }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<Authority> authorities;

    public MemberEntity() {}

    public MemberEntity(int id, String password) {
        this.id = id;
        this.password = password;
        this.isDeleted = 0;
        this.authorities = EnumSet.of(Authority.ROLE_USER);
    }
    
    public boolean isAdmin() {
        return this.authorities.contains(Authority.ROLE_ADMIN);
    }

    public void setAdmin(boolean isAdmin) {
        if (isAdmin) {
            this.authorities.add(Authority.ROLE_ADMIN);
        } else {
            this.authorities.remove(Authority.ROLE_ADMIN);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Authority authority : this.authorities) {
            authorities.add(new SimpleGrantedAuthority(authority.toString()));
        }
        return authorities;
    }

    public DepartmentEntity getDepartment() {
    	return this.departmentEntity;
	}
    	 
	public void setDepartment(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}

}