package com.zalisoft.zalisoft.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "documents")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@SQLDelete(sql = "UPDATE documents SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Documents extends AbstractModel{

    private static final long serialVersionUID = 1L;

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "certificate")
    private String certificateUrl;

    @Column(name = "trancript")
    private String transcriptUrl;

    @Column(name = "image")
    private String userImageUrl;

    @Column( name = "english")
    private String  englishUrl;

}
