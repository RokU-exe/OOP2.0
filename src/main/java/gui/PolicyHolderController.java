package gui;

import models.Claim;
import models.PolicyHolder;

import java.util.List;

public class PolicyHolderController {
    private PolicyHolder policyHolder;

    public PolicyHolderController(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public List<Claim> getClaims() {
        return policyHolder.getClaims();
    }

    public void addClaim(Claim claim) {
        policyHolder.getClaims().add(claim);
    }

    public void updateClaim(Claim updatedClaim) {
        List<Claim> claims = policyHolder.getClaims();
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).getId().equals(updatedClaim.getId())) {
                claims.set(i, updatedClaim);
                break;
            }
        }
    }

    public void deleteClaim(String claimId) {
        List<Claim> claims = policyHolder.getClaims();
        claims.removeIf(claim -> claim.getId().equals(claimId));
    }
}
