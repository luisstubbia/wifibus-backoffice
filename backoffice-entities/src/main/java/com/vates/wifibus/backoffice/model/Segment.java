package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Cada segmento identifica el publico destinado en funcion de ciertos
 * parametros definidos por preguntas y respuestas.
 * 
 * @author Luis Stubbia
 *
 */
@Entity
@Table(name = "SEGMENTS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Segment extends BaseEntity {

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "segment", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<SegmentItem> items;
	
}