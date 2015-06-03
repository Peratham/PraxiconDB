/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.csri.poeticon.praxicon.db.dao;

import gr.csri.poeticon.praxicon.db.entities.MotoricRepresentation;
import javax.persistence.Query;

/**
 *
 * @author dmavroeidis
 */
public interface MotoricRepresentationDao extends
        Dao<Long, MotoricRepresentation> {

    Query getEntityQuery(MotoricRepresentation motoricRepresentation);

}
