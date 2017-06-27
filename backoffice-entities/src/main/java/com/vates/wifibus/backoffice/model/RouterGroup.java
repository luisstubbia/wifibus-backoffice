package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Modela los grupos de routers.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "ROUTER_GROUPS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class RouterGroup extends BaseEntity {

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@JsonIgnore
    private Set<Router> routers;
	
	@ManyToOne
    @JoinColumn(name = "CAMPAIGN_ID")
	@JsonIgnore
	private Campaign campaign;
	
	@ManyToOne
    @JoinColumn(name = "BRAND_ID")
	@JsonIgnore
	private Brand brand;
	
	@ManyToOne
    @JoinColumn(name = "TERM_AND_CONDITION_ID")
	@JsonIgnore
	private ServiceTerm termAndCondition;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ROUTER_GROUPS_QUESTIONS",
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
    private Set<Question> questions;
	
	@ElementCollection(targetClass = ButtonType.class)
	@CollectionTable(name = "ROUTER_GROUPS_BUTTONS", 
	        joinColumns = @JoinColumn(name = "GROUP_ID")
	)
	@Column(name = "BUTTON")
	private Set<ButtonType> buttons;
}
