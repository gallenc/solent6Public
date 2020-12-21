package org.solent.com504.project.model.service;

import java.util.List;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;

public interface ServiceFacade {

    public String getHeartbeat();

    public List<Party> findByPartyRole(PartyRole partyRole);
}
