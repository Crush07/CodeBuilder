package com.hysea.hyseaappapi.output.entity.po;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Data
public class HyCalculateProbability implements Serializable {

    //
    private String id;

    //
    private String deviceId;

    
    public String injuryTypeList;

    
    public Integer effectiveness;

    
    public Integer inspectTime;

    
    public Double corrodeSpeed;

    
    public Double soilCorrodeSpeed;

    
    public Double mediumCorrodeSpeed;

    
    public Integer serviceLife;

    
    public Double electricResistivity;

    
    public Integer foilType;

    
    public Integer emissionType;

    
    public Integer cathodeProtectedType;

    
    public Integer rpbState;

    
    public Double soilTemperature;

    
    public Integer mediumState;

    
    public Double mediumTemperature;

    
    public Integer waterFacilities;

    
    public Integer corrodeType;

    
    public Integer asRequired;

    
    public Integer sedimentationSituation;

    
    public Integer thinningMechanism;

    
    public Integer localityThinning;

    
    public String inspectionMethod;

    
    public Integer externalEnvironment;

    
    public Integer pipeOffset;

    
    public Integer pipeLineComplexity;

    
    public Integer invalidMechanism;

    
    public Integer sensibility;

    
    public Integer brittleFractureQuestion;

    
    public Double mdmt;

    
    public Integer isAvoidCold;

    
    public Double avoidColdTemperature;

    
    public Double recommendMinTemperature;

    
    public Double boilingPoint;

    
    public Double avoidKnockTemperature;

    
    public Integer pwht;

    
    public Integer safeOperation;

    
    public Double jfactor;

    
    public Double changeTemperature;

    
    public Integer soilType;

    
    public Integer fluidType;

    
    public String hole;

    
    public Integer isGoodContact;

    
    public Double hliq;

    
    public Double overCofferPer;

    
    public Double overCofferPerInArea;

    
    public Double overCofferPerOutArea;

    
    public Double sgw;

    
    public Double procost;

    
    public Integer year;

    
    public Double dthinf;

    
    public Double dextdf;

    
    public Double dsccf;

    
    public Double dbritf;

    
    public Integer probabilityLevel;

    
    public Integer consequenceLevel;

    
    public Double scc;

    
    public Boolean isAcceptance;

    
    public String method;

    
    public Integer nextYear;

    
    public Double riskTargetValue;

    
    public Integer riskTargetLevel;

    private String componentId;

    private String historyId;


}