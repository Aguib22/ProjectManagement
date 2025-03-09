package com.ProjectManagement.digitalis.entitie;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @author Aguibou sow
 * @date 2025-02-08 23:20
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */
@Table
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String description;

    private String captureUrl;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date uploadAt;

    @ManyToOne
    @JoinColumn(name = "reported_by", nullable = false)
    private User testeur;
    @ManyToOne
    @JoinColumn(name = "stId", nullable = false)
    private SousTache sousTache;

    @Enumerated(EnumType.STRING)
    private BugStatus status;
}
