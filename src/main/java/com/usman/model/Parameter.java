package com.usman.model;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@SQLDelete(sql = "UPDATE parameter SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Parameter extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "key")
    private String key;


    @NotNull
    @Column(name = "value", columnDefinition = "varchar(1000)")
    private String value;

    @Column(name = "value2", columnDefinition = "varchar(1000)")
    private String value2;

    @Column(name = "description", columnDefinition = "varchar(1000)")
    private String description;

}
