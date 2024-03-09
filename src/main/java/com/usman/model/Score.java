package com.usman.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "score")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@SQLDelete(sql = "UPDATE score SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Score extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alex_score")
    private BigDecimal alexScore;
    @Column(name = "gpa_score")
    private BigDecimal gpaScore;
    @Column(name = "dil_score")
    private BigDecimal englishScore;
    @Column(name = "total_score")
    private BigDecimal totalScore;


}
