package org.solent.com504.project.model.party.dao;

import java.util.List;
import java.util.Set;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;

public interface PartyDAO {

    public Party findById(Long id);

    public Party save(Party party);

    public List<Party> findAll();
    
    public Set<Party> findAllParties();

    public void deleteById(long id);

    public void delete(Party party);

    public void deleteAll();

    public List<Party> findByPartyRole(PartyRole partyRole);

    public List<Party> findByName(String firstName, String secondName);
    
    public Party findByUuid(String uuid);
    
    public Party findByUsername(String username);
//    public Party findByNumberPlate(String numberPlate);
}
