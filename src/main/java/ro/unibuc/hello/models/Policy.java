package ro.unibuc.hello.models;


import javax.persistence.*;

@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idpol;
    private String policyNumber;
    private String policyHolderFirstName;

    private String policyHolderLastName;

    private String CNP;

    public void setPolicyNumber(String policyNumber)
    {
        this.policyNumber = policyNumber;
    }

    public void setPolicyHolderFirstName(String policyHolderFirstName)
    {
        this.policyHolderFirstName = policyHolderFirstName;
    }

    public void setPolicyHolderLastName(String policyHolderLastName)
    {
        this.policyHolderLastName = policyHolderLastName;
    }

    public void setCNP(String CNP)
    {
        this.CNP = CNP;
    }

    public Long getIdPol()
    {
        return idpol;
    }

    public String getPolicyNumber()
    {
        return policyNumber;
    }

    public String getPolicyHolderFirstName()
    {
        return policyHolderFirstName;
    }

    public String getPolicyHolderLastName()
    {
        return policyHolderLastName;
    }

    public String getCNP()
    {
        return CNP;
    }


}
