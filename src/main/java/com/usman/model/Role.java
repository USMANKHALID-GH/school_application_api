package com.usman.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import org.hibernate.envers.Audited;


import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@SQLDelete(sql = "UPDATE role SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Role extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "role_privilege", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges;



}
