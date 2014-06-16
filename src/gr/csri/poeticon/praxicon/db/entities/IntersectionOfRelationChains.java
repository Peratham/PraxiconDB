/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.csri.poeticon.praxicon.db.entities;

import gr.csri.poeticon.praxicon.Globals;
import gr.csri.poeticon.praxicon.db.dao.RelationChainDao;
import gr.csri.poeticon.praxicon.db.dao.implSQL.RelationChainDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author dmavroeidis
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "intersection_of_relation_chains",
        namespace = "http://www.csri.gr/intersection_of_relation_chains")
@Entity
@Table(name = "IntersectionsOfRelationChains", indexes = {
    @Index(columnList = "Name"),
    @Index(columnList = "IntersectionOfRelationChainsId")})
public class IntersectionOfRelationChains implements Serializable {

    public static enum inherent {

        YES, NO, UNKNOWN;

        @Override
        public String toString() {
            return this.name();
        }
    }

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "CUST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CUST_SEQ")
    @Column(name = "IntersectionOfRelationChainsId")
    private Long Id;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Inherent")
    @Enumerated(EnumType.STRING)
    protected inherent Inherent;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Intersection_Relation",
            joinColumns = {
                @JoinColumn(name = "IntersectionId")},
            inverseJoinColumns = {
                @JoinColumn(name = "RelationId")}
    )
    private List<RelationChain> RelationChains;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    //@JoinColumn(name="ConceptId")
    private Concept Concept;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "LanguageRepresentation_IntersectionOfRelationChains",
            joinColumns = {
                @JoinColumn(name = "LanguageRepresentationId")},
            inverseJoinColumns = {
                @JoinColumn(name = "IntersectionOfRelationChainsId")}
    )
    private List<LanguageRepresentation> LanguageRepresentationNames;

    public IntersectionOfRelationChains() {
        RelationChains = new ArrayList<>();
//        unions = new ArrayList<UnionOfIntersections>();  //Obsolete
        LanguageRepresentationNames = new ArrayList<>();
    }

    /**
     * @return the relevant relation chains.
     * @xmlcomments.args xmltag="&lt;relation_chain&gt;" xmldescription="This
     * tag defines a relation"
     */
    public List<RelationChain> getRelationChains() {
        return RelationChains;
    }

    @XmlTransient
    public List<LanguageRepresentation> getLanguageRepresentationNames() {
        return LanguageRepresentationNames;
    }

    /**
     * @return a list of language representation names.
     * @xmlcomments.args xmltag="&lt;Language_representation_names&gt;"
     * xmldescription="This tag defines the names of the
     * LanguageRepresentationGroup that should be used to express this
     * intersection"
     */
    public List<String> getLanguageRepresentationNames_() {
        List<String> language_representation_names_ = new ArrayList<>();
        for (LanguageRepresentation LanguageRepresentationName
                : LanguageRepresentationNames) {
            language_representation_names_.add(LanguageRepresentationName.
                    getText());
        }
        return language_representation_names_;
    }

    public void setLanguageRepresentationNames(
            List<LanguageRepresentation> languageRepresentationNames) {
        this.LanguageRepresentationNames = languageRepresentationNames;
    }

    public void addRelationChain(RelationChain relationChain) {
        relationChain.getIntersections().add(this);
        RelationChains.add(relationChain);
    }

    public void addRelationChain1way(RelationChain relationChain) {
        RelationChains.add(relationChain);
    }

    public void setRelationChains(List<RelationChain> relationChains) {
        this.RelationChains = relationChains;
    }

    @XmlAttribute
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @XmlTransient
    public Concept getConcept() {
        return Concept;
    }

    public void setConcept(Concept concept) {
        this.Concept = concept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - method won't work in case the id fields are not set
        if (!(object instanceof IntersectionOfRelationChains)) {
            return false;
        }
        IntersectionOfRelationChains other =
                (IntersectionOfRelationChains)object;
        if (this.RelationChains.size() == other.RelationChains.size()) {
            boolean eq = true;
            for (RelationChain RelationChain : this.RelationChains) {
                if (!other.RelationChains.contains(RelationChain)) {
                    eq = false;
                    break;
                }
            }
            if (eq) {
                return true;
            }
        }
        if ((this.Id == null && other.Id != null) ||
                (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }

        if (this.Id == null && other.Id == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.csri.poeticon.praxicon.db.entities." +
                "IntersectionOfRelationChains[id=" + Id + "]";
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        if (Globals.ToMergeAfterUnMarshalling) {
            RelationChainDao rcDao = new RelationChainDaoImpl();
            for (int i = 0; i < this.getRelationChains().size(); i++) {
                RelationChain rc = rcDao.getEntity(this.getRelationChains().
                        get(i));
                this.getRelationChains().set(i, rc);
            }
        } else {
            for (int i = 0; i < this.getRelationChains().size(); i++) {
                this.getRelationChains().get(i).getIntersections().add(this);
            }
        }
    }
}