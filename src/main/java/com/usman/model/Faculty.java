package com.usman.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "faculty")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@SQLDelete(sql = "UPDATE faculty SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Faculty extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;


}
