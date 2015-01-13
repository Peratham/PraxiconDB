/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.csri.poeticon.praxicon.db.dao.implSQL;

import gr.csri.poeticon.praxicon.db.dao.LanguageRepresentationDao;
import gr.csri.poeticon.praxicon.db.entities.LanguageRepresentation;
import gr.csri.poeticon.praxicon.db.entities.LanguageRepresentation.Language;
import gr.csri.poeticon.praxicon.db.entities.LanguageRepresentation.PartOfSpeech;
import gr.csri.poeticon.praxicon.db.entities.LanguageRepresentation.UseStatus;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author dmavroeidis
 */
public class LanguageRepresentationDaoImpl extends
        JpaDao<Long, LanguageRepresentation> implements
        LanguageRepresentationDao {

    /**
     * Finds the LanguageRepresentation that has the given Language, text and
 part of speech
 Overloaded.
     *
     * @param language        the Language to search
     * @param text            the text to search
     * @param pos             the pos to search
     * @param useStatus the use status to search
     * @return A LanguageRepresentation (null if not found)
     */
    @Override
    public LanguageRepresentation getLanguageRepresentations(
            Language language, String text, PartOfSpeech pos,
            UseStatus useStatus) {
        Query query = getEntityManager().createNamedQuery(
                "findLanguageRepresentationsByTextLanguagePosPStatus").
                setParameter("text", text).
                setParameter("language", language).
                setParameter("pos", pos).
                setParameter("useStatus", useStatus);
        List res = query.getResultList();
        if (res.size() > 0) {
            return (LanguageRepresentation)res.get(0);
        } else {
            return null;
        }
    }

    /**
     * Finds the LanguageRepresentation that has the given Language, text and
 part of speech
 Case insensitive search.
     *
     * @param language        the Language to search
     * @param text            the text to search
     * @param pos             the pos to search
     * @param useStatus
     * @return A LanguageRepresentation (null if not found)
     */
    @Override
    public LanguageRepresentation getLanguageRepresentationsCaseInsensitive(
            Language language, String text, PartOfSpeech pos,
            UseStatus useStatus) {
        Query query = getEntityManager().createNamedQuery(
                "findLanguageRepresentationsBy" +
                "TextLanguagePosPStatusCaseInsensitive").
                setParameter("text", text).
                setParameter("language", language).
                setParameter("pos", pos).
                setParameter("useStatus", useStatus);
        List res = query.getResultList();
        if (res.size() > 0) {
            return (LanguageRepresentation)res.get(0);
        } else {
            return null;
        }
    }

    /**
     * Finds the LanguageRepresentations that have text field containing the
     * given string Case insensitive search.
     * Overloaded.
     *
     * @param text the Language representation text
     * @return A list of LanguageRepresentations
     */
    @Override
    public List<LanguageRepresentation> getLanguageRepresentations(
            String text) {
        Query query = getEntityManager().createNamedQuery(
                "findLanguageRepresentationsByText").
                setParameter("text", text);
        return query.getResultList();
    }
    
    /**
     * Gets all LanguageRepresentation Texts.
     * Overloaded.
     *
     * @return A list of LanguageRepresentation Texts
     */
    @Override
    public List<String> getAllLanguageRepresentationText() {
        Query query = getEntityManager().createNamedQuery(
                "getAllLanguageRepresentationTextByText");
        return query.getResultList();
    }
    

    /**
     * Creates q query to search for a LanguageRepresentation using text, lang
     * and pos
     *
     * @param lr the LanguageRepresentation
     * @return a query to search for the LanguageRepresentation
     */
    @Override
    public Query getEntityQuery(
            LanguageRepresentation languageRepresentation) {
        Query query = getEntityManager().createNamedQuery(
                "getLanguageRepresentationEntityQuery").
                setParameter("text", languageRepresentation.
                        getText().toUpperCase()).
                setParameter("language", languageRepresentation.
                        getLanguage().name().toUpperCase()).
                setParameter("pos", languageRepresentation.
                        getPartOfSpeech().toString().
                        toUpperCase()).
                setParameter("useStatus",
                        languageRepresentation.getUseStatus().toString().
                        toUpperCase());
        System.out.println("Language Representation Text: " +
                languageRepresentation.getText());
        return query;
    }
}
