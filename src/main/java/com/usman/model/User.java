package com.usman.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.springframework.util.CollectionUtils;



import java.util.HashSet;
import java.util.Set;
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class User extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "is_active", columnDefinition = "boolean default false")
    private Boolean active = Boolean.FALSE;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    private Set<String> privileges;

    public Set<String> getPrivileges() {
        if (privileges == null) {
            privileges = new HashSet<>();
            if (!CollectionUtils.isEmpty(getRoles())) {
                getRoles().stream().forEach(role -> {
                    role.getPrivileges().forEach(privilege -> privileges.add(privilege.getName()));
                });
            }
        }
        return privileges;
    }



}
