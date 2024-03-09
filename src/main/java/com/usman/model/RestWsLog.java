package com.usman.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@Entity
@DynamicInsert
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "rest_ws_log")
public class RestWsLog {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "method")
    private String method;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "success")
    private boolean success;

    @Column(name = "request", columnDefinition = "TEXT")
    private String request;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @Column(name = "error_msg", columnDefinition = "TEXT")
    private String errorMsg;

}
