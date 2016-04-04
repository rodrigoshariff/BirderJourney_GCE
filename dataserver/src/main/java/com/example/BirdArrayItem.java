package com.example;

/**
 * Created by rodrigoshariff on 3/31/2016.
 */
public class BirdArrayItem {

    private String SISRecID;
    private String CommonName;
    private String ScientificName;
    private String FullName;
    private String Family;
    private String Order;
    private String NA_Occurrence;
    private String Description;
    private String IUCN_Category2014;
    private String ImageID;
    private String ImageFileName;

    public BirdArrayItem(){}

    public BirdArrayItem(String textColumn0, String textColumn1, String textColumn2, String textColumn3,
                         String textColumn4, String textColumn5, String textColumn6, String textColumn7,
                         String textColumn8, String textColumn9, String textColumn10) {
        this.SISRecID = textColumn0;
        this.CommonName = textColumn1;
        this.ScientificName = textColumn2;
        this.FullName = textColumn3;
        this.Family = textColumn4;
        this.Order = textColumn5;
        this.NA_Occurrence = textColumn6;
        this.Description = textColumn7;
        this.IUCN_Category2014 = textColumn8;
        this.ImageID = textColumn9;
        this.ImageFileName = textColumn10;
    }


    public String getSISRecID()
    {
        return this.SISRecID;
    }

    public boolean setSISRecID(String BirdID)
    {
        this.SISRecID = BirdID;
        return true;
    }

    public String getCommonName()
    {
        return this.CommonName;
    }

    public boolean setCommonName(String CommonName)
    {
        this.CommonName = CommonName;
        return true;
    }

    public String getScientificName()
    {
        return this.ScientificName;
    }

    public boolean setScientificName(String ScientificName)
    {
        this.ScientificName = ScientificName;
        return true;
    }

    public String getFullName()
    {
        return this.FullName;
    }

    public boolean setFullName(String FullName)
    {
        this.FullName = FullName;
        return true;
    }


    public String getFamily()
    {
        return this.Family;
    }

    public boolean setFamily(String Family)
    {
        this.Family = Family;
        return true;
    }

    public String getOrder()
    {
        return this.Order;
    }

    public boolean setOrder(String Order)
    {
        this.Order = Order;
        return true;
    }

    public String getNA_Occurrence()
    {
        return this.NA_Occurrence;
    }

    public boolean setNA_Occurrence(String NA_Occurrence)
    {
        this.NA_Occurrence = NA_Occurrence;
        return true;
    }

    public String getDescription()
    {
        return this.Description;
    }

    public boolean setDescription(String Description)
    {
        this.Description = Description;
        return true;
    }

    public String getIUCN_Category2014()
    {
        return this.IUCN_Category2014;
    }

    public boolean setIUCN_Category2014(String IUCN_Category2014)
    {
        this.IUCN_Category2014 = IUCN_Category2014;
        return true;
    }

    public String getImageID()
    {
        return this.ImageID;
    }

    public boolean setImageID(String ImageID)
    {
        this.ImageID = ImageID;
        return true;
    }

    public String getImageFileName()
    {
        return this.ImageFileName;
    }

    public boolean setImageFileName(String ImageFileName)
    {
        this.ImageFileName = ImageFileName;
        return true;
    }

}
