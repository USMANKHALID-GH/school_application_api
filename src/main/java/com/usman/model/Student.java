package com.usman.model;

import com.usman.enums.ApplicationStatus;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "students")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@SQLDelete(sql = "UPDATE students SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Student extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_no")
    private  String studentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", columnDefinition = "VARCHAR(255) DEFAULT 'WAITING'")
    private ApplicationStatus applicationStatus = ApplicationStatus.WAITING;

    @Embedded
    private UserInformation userInformation;

    @OneToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true ,fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private Score score;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true ,fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Documents documents;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;
}
