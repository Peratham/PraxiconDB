/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csri.poeticon.praxicon.db.entities;

import csri.poeticon.praxicon.Constants;
import csri.poeticon.praxicon.Globals;
import csri.poeticon.praxicon.db.dao.ConceptDao;
import csri.poeticon.praxicon.db.dao.RelationTypeDao;
import csri.poeticon.praxicon.db.dao.implSQL.ConceptDaoImpl;
import csri.poeticon.praxicon.db.dao.implSQL.RelationTypeDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Erevodifwntas
 * @author Dimitris Mavroeidis
 * 
 */

@XmlRootElement()
@Entity
@Table(name="Relations")
public class Relation implements Serializable
{
    public static enum derivation_supported
    {
        YES, NO, UNKNOWN ;
        @Override
        public String toString()
        {
            return this.name();
        }
    }

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CUST_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="CUST_SEQ")
    @Column(name="RelationId")
    private Long Id;

    @Column(name="Comment")
    private String Comment;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "Relation")
    private List<RelationChain_Relation> MainFunctions;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    //@JoinColumn(name="Id")
    private RelationType Type;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    // @JoinColumn(name="ConceptId")
    @NotNull(message="Concept Id name must be specified.")
    private Concept Object;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    // @JoinColumn(name="ConceptId")
    @NotNull(message="Concept Id must be specified.")
    private Concept Subject;

    @Column(name="DerivationSupported")
    @NotNull(message="Derivation support must be specified.")
    @Enumerated(EnumType.STRING)
    protected derivation_supported DerivationSupported;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="LanguageRepresentation_RelationSubject",
        joinColumns={@JoinColumn(name="RelationId")},
        inverseJoinColumns={@JoinColumn(name="LanguageRepresentationId")}
    )
    private List<LanguageRepresentation> LanguageRepresentationSubject;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="LanguageRepresentation_RelationObject",
        joinColumns={@JoinColumn(name="RelationId")},
        inverseJoinColumns={@JoinColumn(name="LanguageRepresentationId")}
    )
    private List<LanguageRepresentation> LanguageRepresentationObject;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="MotoricRepresentation_RelationSubject",
        joinColumns={@JoinColumn(name="RelationId")},
        inverseJoinColumns={@JoinColumn(name="MotoricRepresentationId")}
    )
    private List<MotoricRepresentation> MotoricRepresentationSubject;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="MotoricRepresentation_RelationObject",
        joinColumns={@JoinColumn(name="RelationId")},
        inverseJoinColumns={@JoinColumn(name="MotoricRepresentationId")}
    )
    private List<MotoricRepresentation> MotoricRepresentationObject;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="VisualRepresentation_RelationSubject",
        joinColumns={@JoinColumn(name="RelationId")},
        inverseJoinColumns={@JoinColumn(name="VisualRepresentationId")}
    )
    private List<VisualRepresentation> VisualRepresentationSubject;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="VisualRepresentation_RelationObject",
        joinColumns={@JoinColumn(name="RelationId")},
        inverseJoinColumns={@JoinColumn(name="VisualRepresentationId")}
    )
    private List<VisualRepresentation> VisualRepresentationObject;



// TODO: Uncomment each method after checking it first
    public Relation()
    {
        MainFunctions = new ArrayList<>();
        VisualRepresentationObject = new ArrayList<>();
        VisualRepresentationSubject = new ArrayList<>();
        LanguageRepresentationObject = new ArrayList<>();
        LanguageRepresentationSubject = new ArrayList<>();
        MotoricRepresentationObject = new ArrayList<>();
        MotoricRepresentationSubject = new ArrayList<>();
        Type = new RelationType();
    }

    /**
     * @xmlcomments.args
     *	   xmltag="subject"
     *     xmldescription="This attribute defines the object that the
     *                     relation is related to"
     */
    @XmlAttribute(name="subject")
    @XmlTransient
    public Concept getSubject()
    {
        return Subject;
    }

    public void setSubject(Concept subject)
    {
        this.Subject = subject;
    }

    /**
     * @return whether derivation is supported for this relation.
     * @xmlcomments.args
     *	   xmltag="derivation_supported"
     *     xmldescription="This attribute defines if the relation supports 
     *                     derivation or not"
     */
    @XmlAttribute(name="derivation_supported")
    public derivation_supported DerivationSupported()
    {
        return DerivationSupported;
    }

    public void setDerivation(derivation_supported derivation_supported)
    {
        this.DerivationSupported = derivation_supported;
    }

    @XmlTransient
    public List<RelationChain_Relation> getMainFunctions()
    {
        return MainFunctions;
    }

    public void setMainFunctions(List<RelationChain_Relation> main_functions)
    {
        this.MainFunctions = main_functions;
    }

    @XmlTransient
    @XmlAttribute(name="object")
    /**
     * @xmlcomments.args
     *	   xmltag="object"
     *     xmldescription="This attribute defines the object that the
     *                     relation is related to"
     */
    public Concept getObject()
    {
        return Object;
    }

    public void setObject(Concept object)
    {
        this.Object = object;
    }

    /**
     * @return the type of the relation.
     * @xmlcomments.args
     *	   xmltag="&lt;relation_type&gt;"
     *     xmldescription="This tag defines the type of the relation"
     */
   @XmlElement
    public RelationType getType()
    {
        return Type;
    }

   /**
    * Sets the type of the Relation but it doesn't check if there is the same
    * type twice
    * @param type the type of the relation
    */
    public void setTypeSimple(RelationType type)
    {
        this.Type = type;
    }

    public void setType(RelationType type)
    {
        if(type.getId() == null)
        {
            RelationTypeDao tmp = new RelationTypeDaoImpl();
            RelationType res = tmp.getEntity(type);
            if(res!=null)
            {
                type = res;
            }
        }
        this.Type = type;
    }

    @XmlAttribute
    public Long getId()
    {
        return Id;
    }

    public void setId(Long id)
    {
        this.Id = id;
    }

    @XmlAttribute()
    public String getComment()
    {
        return Comment;
    }

    public void setComment(String comment)
    {
        this.Comment = comment;
    }

   @XmlTransient
    public List<LanguageRepresentation> getLanguageRepresentationObject()
    {
        return LanguageRepresentationObject;
    }

   /**
    * @return the language representation of the concept that is on the object side of the relation.
    * @xmlcomments.args
    *	   xmltag="&lt;language_representation_object&gt;"
    *     xmldescription="This tag defines the LanguageRepresentation
    *                     that should be used to express the Object in this
    *                     relation"
    */
   @XmlElement(name="LanguageRepresentationObject")
    public String getLanguageRepresentationObject_()
    {
        String language_representation_object_;
        language_representation_object_ = new String();
        // TODO: Not sure about the data type below.
        language_representation_object_ = LanguageRepresentationObject.toString();  //.getLanguaText();
        return language_representation_object_;
    }

    public void setLanguageRepresentationObject(List<LanguageRepresentation> language_representation_object)
    {
        this.LanguageRepresentationObject = language_representation_object;
    }

    

    @XmlTransient
    public List<LanguageRepresentation> getLanguageRepresentationSubject()
    {
        return LanguageRepresentationSubject;
    }

    @XmlTransient
    public List<MotoricRepresentation> getMotoricRepresentationObject()
    {
        return MotoricRepresentationObject;
    }

    /**
     * @return the motoric representation of the concept that is on the object side of the relation.
     * @xmlcomments.args
     *	   xmltag="&lt;motoric_representation_object&gt;"
     *     xmldescription="This tag defines the MotoricRepresentation
     *                     that should be used to express the Object
     *                     in this relation"
     */
    @XmlElement(name="motoric_representation_object")
    public List<String> getMotoricRepresentationObject_()
    {
        List<String> motoric_representation_object_ = new ArrayList<>();
        for (MotoricRepresentation MotoricRepresentationObject1 : MotoricRepresentationObject) {
            motoric_representation_object_.add(MotoricRepresentationObject1.toString());
        }
        return motoric_representation_object_;
    }

    public void setMotoricRepresentationObject(List<MotoricRepresentation> motoric_representation_object)
    {
        this.MotoricRepresentationObject = motoric_representation_object;
    }

    @XmlTransient
    public List<MotoricRepresentation> getMotoricRepresentationSubject()
    {
        return MotoricRepresentationSubject;
    }

    @XmlTransient
    public List<VisualRepresentation> getVisualRepresentationObject()
    {
        return VisualRepresentationObject;
    }


    public void setVisualRepresentationObject(List<VisualRepresentation> visual_representation_object)
    {
        this.VisualRepresentationObject = visual_representation_object;
    }

    @XmlTransient
    public List<VisualRepresentation> getVisualRepresentationSubject()
    {
        return VisualRepresentationSubject;
    }

    public void setVisualRepresentationSubject(List<VisualRepresentation> visual_representation_subject)
    {
        this.VisualRepresentationSubject = visual_representation_subject;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relation))
        {
            return false;
        }
        Relation other = (Relation) object;
        try
        {
            if ((this.Type!=null && this.Object!=null && this.Subject!=null
                && this.Type.equals(other.Type) && this.Object.equals(other.Object) && this.Subject.equals(other.Subject))||
                (this.Type!=null && this.Object!=null && this.Subject!=null
                &&this.Type.equals(other.Type) && this.Object.equals(other.Subject) && this.Subject.equals(other.Object)))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString()
    {
        return this.getSubject() + " " + this.getType().getForwardName() + " " + this.getObject();
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        if (Globals.ToMergeAfterUnMarshalling)
        {
            RelationTypeDao rDao = new RelationTypeDaoImpl();
            this.Type = rDao.getEntity(Type);
            ConceptDao cDao = new ConceptDaoImpl();
            this.Object = cDao.getEntity(Object);
            Object.getRelationsContainingConceptAsObject().add(this);
            this.Subject = cDao.getEntity(Subject);
        }
    }
}