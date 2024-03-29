/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.csri.poeticon.praxicon.db.entities;

import static gr.csri.poeticon.praxicon.db.entities.Concept.Status.VARIABLE;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author dmavroeidis
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "relation_set", namespace = "http://www.csri.gr/relation_set")
@Entity
@NamedQueries({
    @NamedQuery(name =
            "findRelationSet",
            query =
            "SELECT rs FROM RelationSet rs " +
            "WHERE rs = :relationSet"),
    @NamedQuery(name =
            "findRelationSetsByRelationArgument",
            query =
            "SELECT DISTINCT rs FROM RelationSet rs " +
            "JOIN rs.relations rsr " +
            "JOIN rsr.relation r " +
            "WHERE (r.rightArgument = :relationArgument " +
            "OR r.leftArgument = :relationArgument)"),
    @NamedQuery(name =
            "findRelationSetsByLeftRelationArgument",
            query =
            "SELECT DISTINCT rs FROM RelationSet rs " +
            "JOIN rs.relations rsr " +
            "JOIN rsr.relation r " +
            "WHERE r.leftArgument = :relationArgument"),
    @NamedQuery(name =
            "findRelationSetsByRightRelationArgument",
            query =
            "SELECT DISTINCT rs FROM RelationSet rs " +
            "JOIN rs.relations rsr " +
            "JOIN rsr.relation r " +
            "WHERE r.rightArgument = :relationArgument"),
    @NamedQuery(name =
            "findRelationSetsByRelation",
            query =
            "SELECT rs FROM RelationSet rs " +
            "JOIN rs.relations rsr " +
            "WHERE rsr.relation = :relation"),
})
@Table(name = "RelationSets",
        indexes = {
            @Index(columnList = "Name")})
public class RelationSet implements Serializable {

    public static enum Inherent {

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
    @Column(name = "RelationSetId")
    private Long id;

    @Column(name = "Name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relationSet")
    private List<RelationSet_Relation> relations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "LanguageRepresentation_RelationSet",
            joinColumns = {
                @JoinColumn(name = "RelationSetId")},
            inverseJoinColumns = {
                @JoinColumn(name = "LanguageRepresentationId")}
    )
    private List<LanguageRepresentation> languageRepresentations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relationSet")
    private List<VisualRepresentation> visualRepresentations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relationSet")
    private List<MotoricRepresentation> motoricRepresentations;

    /**
     * Constructor #1.
     */
    public RelationSet() {
        this.name = "";
        this.relations = new ArrayList<>();
        this.languageRepresentations = new ArrayList<>();
    }

    /**
     * Constructor #2.
     *
     * @param name
     * @param relationSetRelationsList
     * @param isInherent
     * @param languageRepresentations
     */
    public RelationSet(String name,
            List<RelationSet_Relation> relationSetRelationsList,
            List<LanguageRepresentation> languageRepresentations) {
        this.name = name;
        this.relations = relationSetRelationsList;
        this.languageRepresentations = languageRepresentations;
    }

    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a list of all Relations contained in this RelationSet.
     *
     * @return a list of relations
     */
    public List<Relation> getRelationsList() {
        List<RelationSet_Relation> relationSet_RelationList = new ArrayList();
        List<Relation> relationList = new ArrayList();
        relationSet_RelationList = this.relations;
        for (RelationSet_Relation relationSetRelation : relationSet_RelationList) {
            relationList.add(relationSetRelation.getRelation());
        }
        return relationList;
    }

    /**
     * Gets all Relations contained in this RelationSet in the form of Set,
     * which means that there are no duplicate entries.
     *
     * @return a set of relations
     */
    public Set<Relation> getRelationsSet() {
        HashSet<Relation> relationSet = new HashSet<>(this.getRelationsList());
        return relationSet;
    }

    /**
     * Gets a list of all relations contained in this relation set.
     *
     * @return a List of Relation
     */
    public List<RelationSet_Relation> getRelations() {
        return relations;
    }

    /**
     * Sets the relations for this relation set.
     *
     * @param relations
     */
    public void setRelations(List<RelationSet_Relation> relations) {
        this.relations = relations;
    }

    /**
     * Adds a relation to the relation set without information on the order.
     *
     * @param relation
     */
    public void addRelation(Relation relation) {
        RelationSet_Relation relationSetRelation = new RelationSet_Relation();
        relationSetRelation.setRelation(relation);
        relationSetRelation.setRelationSet(this);
        this.relations.add(relationSetRelation);
    }

    /**
     * Retrieves the list of language representations for this relation set.
     *
     * @return a list of LanguageRepresentation
     */
    public List<LanguageRepresentation> getLanguageRepresentations() {
        return languageRepresentations;
    }

    /**
     * Retrieves the names of the language representations of this relation set.
     *
     * @return a list strings containing the names of language representation
     */
    public List<String> getLanguageRepresentationNames() {
        List<String> languageRepresentationNames = new ArrayList<>();
        for (LanguageRepresentation languageRepresentation
                : languageRepresentations) {
            languageRepresentationNames.add(
                    languageRepresentation.getText());
        }
        return languageRepresentationNames;
    }

    /**
     * Sets the language representation for this relation set.
     *
     * @param languageRepresentations
     */
    public void setLanguageRepresentations(
            List<LanguageRepresentation> languageRepresentations) {
        this.languageRepresentations = languageRepresentations;
    }

    /**
     * Adds a language representation to the relation set.
     *
     * @param languageRepresentation
     */
    public void addLanguageRepresentation(
            LanguageRepresentation languageRepresentation) {
        this.languageRepresentations.add(languageRepresentation);
    }

    public List<VisualRepresentation> getVisualRepresentations() {
        return visualRepresentations;
    }

    public void addVisualRepresentation(
            VisualRepresentation visualRepresentation) {
        this.visualRepresentations.add(visualRepresentation);
    }

    public void setVisualRepresentation(
            List<VisualRepresentation> visualRepresentations) {
        this.visualRepresentations = visualRepresentations;
    }

    public final List<VisualRepresentation> getVisualRepresentationsEntries() {
        List<VisualRepresentation> visualRepresentationEntries =
                new ArrayList<>();
        for (VisualRepresentation VisualRepresentation
                : this.visualRepresentations) {
            visualRepresentationEntries.add(VisualRepresentation);
        }
        return visualRepresentationEntries;
    }

    public final List<MotoricRepresentation> getMotoricRepresentations() {
        return motoricRepresentations;
    }

    public List<MotoricRepresentation> getMotoricRepresentationsEntries() {
        List<MotoricRepresentation> motoric_representation_entries =
                new ArrayList<>();
        for (MotoricRepresentation MotoricRepresentation
                : this.motoricRepresentations) {
            motoric_representation_entries.add(MotoricRepresentation);
        }
        return motoric_representation_entries;
    }

    public void setMotoricRepresentations(
            List<MotoricRepresentation> motoricRepresentations) {
        this.motoricRepresentations = motoricRepresentations;
    }

    public void addMotoricRepresentation(
            MotoricRepresentation motoricRepresentation) {
        this.motoricRepresentations.add(motoricRepresentation);
    }

    /**
     * If any of the concepts inside the relation set has status variable,
     * then the relation set itself is characterized as variable. It searches
     * recursively.
     *
     * @return true/false
     */
    public boolean isVariable() {
        for (Relation relation : this.getRelationsList()) {
            if (relation.getLeftArgument().isConcept()) {
                if (relation.getLeftArgument().getConcept().getStatus() ==
                        VARIABLE) {
                    return true;
                }
            } else if (relation.getLeftArgument().isRelationSet()) {
                if (relation.getLeftArgument().getRelationSet().isVariable()) {
                    return true;
                }
            }

            if (relation.getRightArgument().isConcept()) {
                if (relation.getRightArgument().getConcept().getStatus() ==
                        VARIABLE) {
                    return true;
                }
            } else if (relation.getRightArgument().isRelationSet()) {
                if (relation.getRightArgument().getRelationSet().isVariable()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.relations);
        hash = 13 * hash + Objects.hashCode(this.languageRepresentations);
        hash = 13 * hash + Objects.hashCode(this.visualRepresentations);
        hash = 13 * hash + Objects.hashCode(this.motoricRepresentations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RelationSet other = (RelationSet)obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.relations, other.relations)) {
            return false;
        }
        if (!Objects.equals(this.languageRepresentations,
                other.languageRepresentations)) {
            return false;
        }
        if (!Objects.equals(this.visualRepresentations,
                other.visualRepresentations)) {
            return false;
        }
        if (!Objects.equals(this.motoricRepresentations,
                other.motoricRepresentations)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "gr.csri.poeticon.praxicon.db.entities.RelationSet[ id=" + id +
                " ]";
    }

//    public void afterUnmarshal(Unmarshaller u, Object parent) {
//        if (Globals.ToMergeAfterUnMarshalling) {
//            RelationSetDao rsDao = new RelationSetDaoImpl();
//            rsDao.merge(this);
//            System.out.println("Finished unmarshalling RelationSet");
//        }
//    }
    @XmlRegistry
    class ObjectFactory {

        RelationSet createRelationSet() {
            return new RelationSet();
        }
    }
}
