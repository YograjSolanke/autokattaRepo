package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 11/10/17.
 */

public class NewVehicleAllResponse {

    @SerializedName("Success")
    @Expose
    private Success success;
    @SerializedName("Error")
    @Expose
    private Object error;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("NewVehicle")
        @Expose
        private List<NewVehicle> newVehicle = null;

        public List<NewVehicle> getNewVehicle() {
            return newVehicle;
        }

        public void setNewVehicle(List<NewVehicle> newVehicle) {
            this.newVehicle = newVehicle;
        }

        public class NewVehicle {

            @SerializedName("NewVehicleID")
            @Expose
            private Integer newVehicleID;
            @SerializedName("ThreePointLinkage")
            @Expose
            private String threePointLinkage;
            @SerializedName("ABS")
            @Expose
            private String aBS;
            @SerializedName("AdditionalInformation")
            @Expose
            private String additionalInformation;
            @SerializedName("AirBags")
            @Expose
            private String airBags;
            @SerializedName("AirCleaner")
            @Expose
            private String airCleaner;
            @SerializedName("AirCondition")
            @Expose
            private String airCondition;
            @SerializedName("AlternateFuelType")
            @Expose
            private String alternateFuelType;
            @SerializedName("AuxiliaryHydValve")
            @Expose
            private String auxiliaryHydValve;
            @SerializedName("AxleConfiguration")
            @Expose
            private String axleConfiguration;
            @SerializedName("BackUpTorque")
            @Expose
            private String backUpTorque;
            @SerializedName("BodyOptions")
            @Expose
            private String bodyOptions;
            @SerializedName("Boreinmm")
            @Expose
            private String boreinmm;
            @SerializedName("BoreStroke")
            @Expose
            private String boreStroke;
            @SerializedName("Brakes")
            @Expose
            private String brakes;
            @SerializedName("BrakeType")
            @Expose
            private String brakeType;
            @SerializedName("BrandID")
            @Expose
            private Integer brandID;
            @SerializedName("CategoryID")
            @Expose
            private Integer categoryID;
            @SerializedName("CentralLoking")
            @Expose
            private String centralLoking;
            @SerializedName("ChildSeat")
            @Expose
            private String childSeat;
            @SerializedName("Clutch")
            @Expose
            private String clutch;
            @SerializedName("ClutchType")
            @Expose
            private String clutchType;
            @SerializedName("CoolingSystem")
            @Expose
            private String coolingSystem;
            @SerializedName("CruiseControl")
            @Expose
            private String cruiseControl;
            @SerializedName("CylinderCapacityCC")
            @Expose
            private String cylinderCapacityCC;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("DisplacementCC")
            @Expose
            private String displacementCC;
            @SerializedName("Drive")
            @Expose
            private String drive;
            @SerializedName("DualStageAirbags")
            @Expose
            private String dualStageAirbags;
            @SerializedName("EmissionNorms")
            @Expose
            private String emissionNorms;
            @SerializedName("EngineCC")
            @Expose
            private String engineCC;
            @SerializedName("EngineDetails")
            @Expose
            private String engineDetails;
            @SerializedName("EngineLocation")
            @Expose
            private String engineLocation;
            @SerializedName("EngineRatedRPM")
            @Expose
            private String engineRatedRPM;
            @SerializedName("EngineType")
            @Expose
            private String engineType;
            @SerializedName("FIP")
            @Expose
            private String fIP;
            @SerializedName("FloorType")
            @Expose
            private String floorType;
            @SerializedName("FogLamps")
            @Expose
            private String fogLamps;
            @SerializedName("FoldingRearSeat")
            @Expose
            private String foldingRearSeat;
            @SerializedName("FrontAxleSuspension")
            @Expose
            private String frontAxleSuspension;
            @SerializedName("FrontTyre")
            @Expose
            private String frontTyre;
            @SerializedName("FuelTank")
            @Expose
            private Object fuelTank;
            @SerializedName("FuelTankCapacityLtr")
            @Expose
            private String fuelTankCapacityLtr;
            @SerializedName("FuelType")
            @Expose
            private String fuelType;
            @SerializedName("FuelSystem")
            @Expose
            private String fuelSystem;
            @SerializedName("GearBox")
            @Expose
            private String gearBox;
            @SerializedName("GradeAbility")
            @Expose
            private String gradeAbility;
            @SerializedName("GroundClearance")
            @Expose
            private String groundClearance;
            @SerializedName("GVWGCW")
            @Expose
            private String gVWGCW;
            @SerializedName("HeadRest")
            @Expose
            private String headRest;
            @SerializedName("HP")
            @Expose
            private String hP;
            @SerializedName("HPCat")
            @Expose
            private String hPCat;
            @SerializedName("Hydraulic")
            @Expose
            private String hydraulic;
            @SerializedName("HydraulicLiftCapacityKg")
            @Expose
            private String hydraulicLiftCapacityKg;
            @SerializedName("HydraulicType")
            @Expose
            private String hydraulicType;
            @SerializedName("IntegratedMusicSystem")
            @Expose
            private String integratedMusicSystem;
            @SerializedName("KerbWeight")
            @Expose
            private String kerbWeight;
            @SerializedName("KeylessButtonStart")
            @Expose
            private String keylessButtonStart;
            @SerializedName("LiftingCapacityAtStandardFrame")
            @Expose
            private String liftingCapacityAtStandardFrame;
            @SerializedName("LoadBody")
            @Expose
            private String loadBody;
            @SerializedName("LoadBodyDimensions")
            @Expose
            private String loadBodyDimensions;
            @SerializedName("MaxPower")
            @Expose
            private String maxPower;
            @SerializedName("MaxReserveSpeed")
            @Expose
            private String maxReserveSpeed;
            @SerializedName("MaxSpeed")
            @Expose
            private String maxSpeed;
            @SerializedName("MaxTorqueKgm")
            @Expose
            private String maxTorqueKgm;
            @SerializedName("Mileage")
            @Expose
            private String mileage;
            @SerializedName("ModelID")
            @Expose
            private Integer modelID;
            @SerializedName("NoOfCylinder")
            @Expose
            private String noOfCylinder;
            @SerializedName("NoOfgears")
            @Expose
            private Object noOfgears;
            @SerializedName("NoOfGearsForword")
            @Expose
            private String noOfGearsForword;
            @SerializedName("NoOfGearsReverse")
            @Expose
            private String noOfGearsReverse;
            @SerializedName("OverAllLength")
            @Expose
            private String overAllLength;
            @SerializedName("OverAllWidth")
            @Expose
            private String overAllWidth;
            @SerializedName("ParkingSensors")
            @Expose
            private String parkingSensors;
            @SerializedName("PayLoad")
            @Expose
            private String payLoad;
            @SerializedName("PowerKW")
            @Expose
            private String powerKW;
            @SerializedName("PowerWindow")
            @Expose
            private String powerWindow;
            @SerializedName("PTOHP")
            @Expose
            private String pTOHP;
            @SerializedName("PTORPM")
            @Expose
            private String pTORPM;
            @SerializedName("PTOSpeed")
            @Expose
            private String pTOSpeed;
            @SerializedName("PTOType")
            @Expose
            private String pTOType;
            @SerializedName("PumpType")
            @Expose
            private String pumpType;
            @SerializedName("RainSensingWiper")
            @Expose
            private String rainSensingWiper;
            @SerializedName("RatedRPM")
            @Expose
            private String ratedRPM;
            @SerializedName("RearAC")
            @Expose
            private String rearAC;
            @SerializedName("RearAxelSuspension")
            @Expose
            private String rearAxelSuspension;
            @SerializedName("RearDefogger")
            @Expose
            private String rearDefogger;
            @SerializedName("RearTyre")
            @Expose
            private String rearTyre;
            @SerializedName("RearWiper")
            @Expose
            private String rearWiper;
            @SerializedName("RPTOGRPTO")
            @Expose
            private String rPTOGRPTO;
            @SerializedName("SeatBelt")
            @Expose
            private String seatBelt;
            @SerializedName("SeatBeltWarning")
            @Expose
            private String seatBeltWarning;
            @SerializedName("SeatingCapacity")
            @Expose
            private Integer seatingCapacity;
            @SerializedName("SeatType")
            @Expose
            private String seatType;
            @SerializedName("SeatUpholstery")
            @Expose
            private String seatUpholstery;
            @SerializedName("Speed")
            @Expose
            private String speed;
            @SerializedName("SportMode")
            @Expose
            private String sportMode;
            @SerializedName("Steering")
            @Expose
            private String steering;
            @SerializedName("SteeringMountedControl")
            @Expose
            private String steeringMountedControl;
            @SerializedName("SteeringType")
            @Expose
            private String steeringType;
            @SerializedName("StrokeInMM")
            @Expose
            private String strokeInMM;
            @SerializedName("SubCategoryID")
            @Expose
            private Integer subCategoryID;
            @SerializedName("SunRoofMoonRoof")
            @Expose
            private String sunRoofMoonRoof;
            @SerializedName("TMPS")
            @Expose
            private String tMPS;
            @SerializedName("Torque")
            @Expose
            private String torque;
            @SerializedName("TotalWeight")
            @Expose
            private String totalWeight;
            @SerializedName("TransmissionType")
            @Expose
            private String transmissionType;
            @SerializedName("TuboChargeNutralAspirated")
            @Expose
            private String tuboChargeNutralAspirated;
            @SerializedName("TurningRadius")
            @Expose
            private String turningRadius;
            @SerializedName("TurningRadiusOfBrakes")
            @Expose
            private String turningRadiusOfBrakes;
            @SerializedName("TypeOfClutchAndBrakePedal")
            @Expose
            private String typeOfClutchAndBrakePedal;
            @SerializedName("TypeOfGearReduction")
            @Expose
            private String typeOfGearReduction;
            @SerializedName("Tyres")
            @Expose
            private String tyres;
            @SerializedName("TyreSize")
            @Expose
            private String tyreSize;
            @SerializedName("TyreSizeFront")
            @Expose
            private String tyreSizeFront;
            @SerializedName("TyreSizeRear")
            @Expose
            private String tyreSizeRear;
            @SerializedName("VersionID")
            @Expose
            private Integer versionID;
            @SerializedName("WarrantyKilometer")
            @Expose
            private Object warrantyKilometer;
            @SerializedName("WarrantyYear")
            @Expose
            private Object warrantyYear;
            @SerializedName("WebSite")
            @Expose
            private String webSite;
            @SerializedName("WeightKg")
            @Expose
            private String weightKg;
            @SerializedName("WheelBaseMM")
            @Expose
            private String wheelBaseMM;

            public Integer getNewVehicleID() {
                return newVehicleID;
            }

            public void setNewVehicleID(Integer newVehicleID) {
                this.newVehicleID = newVehicleID;
            }

            public String getThreePointLinkage() {
                return threePointLinkage;
            }

            public void setThreePointLinkage(String threePointLinkage) {
                this.threePointLinkage = threePointLinkage;
            }

            public String getABS() {
                return aBS;
            }

            public void setABS(String aBS) {
                this.aBS = aBS;
            }

            public String getAdditionalInformation() {
                return additionalInformation;
            }

            public void setAdditionalInformation(String additionalInformation) {
                this.additionalInformation = additionalInformation;
            }

            public String getAirBags() {
                return airBags;
            }

            public void setAirBags(String airBags) {
                this.airBags = airBags;
            }

            public String getAirCleaner() {
                return airCleaner;
            }

            public void setAirCleaner(String airCleaner) {
                this.airCleaner = airCleaner;
            }

            public String getAirCondition() {
                return airCondition;
            }

            public void setAirCondition(String airCondition) {
                this.airCondition = airCondition;
            }

            public String getAlternateFuelType() {
                return alternateFuelType;
            }

            public void setAlternateFuelType(String alternateFuelType) {
                this.alternateFuelType = alternateFuelType;
            }

            public String getAuxiliaryHydValve() {
                return auxiliaryHydValve;
            }

            public void setAuxiliaryHydValve(String auxiliaryHydValve) {
                this.auxiliaryHydValve = auxiliaryHydValve;
            }

            public String getAxleConfiguration() {
                return axleConfiguration;
            }

            public void setAxleConfiguration(String axleConfiguration) {
                this.axleConfiguration = axleConfiguration;
            }

            public String getBackUpTorque() {
                return backUpTorque;
            }

            public void setBackUpTorque(String backUpTorque) {
                this.backUpTorque = backUpTorque;
            }

            public String getBodyOptions() {
                return bodyOptions;
            }

            public void setBodyOptions(String bodyOptions) {
                this.bodyOptions = bodyOptions;
            }

            public String getBoreinmm() {
                return boreinmm;
            }

            public void setBoreinmm(String boreinmm) {
                this.boreinmm = boreinmm;
            }

            public String getBoreStroke() {
                return boreStroke;
            }

            public void setBoreStroke(String boreStroke) {
                this.boreStroke = boreStroke;
            }

            public String getBrakes() {
                return brakes;
            }

            public void setBrakes(String brakes) {
                this.brakes = brakes;
            }

            public String getBrakeType() {
                return brakeType;
            }

            public void setBrakeType(String brakeType) {
                this.brakeType = brakeType;
            }

            public Integer getBrandID() {
                return brandID;
            }

            public void setBrandID(Integer brandID) {
                this.brandID = brandID;
            }

            public Integer getCategoryID() {
                return categoryID;
            }

            public void setCategoryID(Integer categoryID) {
                this.categoryID = categoryID;
            }

            public String getCentralLoking() {
                return centralLoking;
            }

            public void setCentralLoking(String centralLoking) {
                this.centralLoking = centralLoking;
            }

            public String getChildSeat() {
                return childSeat;
            }

            public void setChildSeat(String childSeat) {
                this.childSeat = childSeat;
            }

            public String getClutch() {
                return clutch;
            }

            public void setClutch(String clutch) {
                this.clutch = clutch;
            }

            public String getClutchType() {
                return clutchType;
            }

            public void setClutchType(String clutchType) {
                this.clutchType = clutchType;
            }

            public String getCoolingSystem() {
                return coolingSystem;
            }

            public void setCoolingSystem(String coolingSystem) {
                this.coolingSystem = coolingSystem;
            }

            public String getCruiseControl() {
                return cruiseControl;
            }

            public void setCruiseControl(String cruiseControl) {
                this.cruiseControl = cruiseControl;
            }

            public String getCylinderCapacityCC() {
                return cylinderCapacityCC;
            }

            public void setCylinderCapacityCC(String cylinderCapacityCC) {
                this.cylinderCapacityCC = cylinderCapacityCC;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDisplacementCC() {
                return displacementCC;
            }

            public void setDisplacementCC(String displacementCC) {
                this.displacementCC = displacementCC;
            }

            public String getDrive() {
                return drive;
            }

            public void setDrive(String drive) {
                this.drive = drive;
            }

            public String getDualStageAirbags() {
                return dualStageAirbags;
            }

            public void setDualStageAirbags(String dualStageAirbags) {
                this.dualStageAirbags = dualStageAirbags;
            }

            public String getEmissionNorms() {
                return emissionNorms;
            }

            public void setEmissionNorms(String emissionNorms) {
                this.emissionNorms = emissionNorms;
            }

            public String getEngineCC() {
                return engineCC;
            }

            public void setEngineCC(String engineCC) {
                this.engineCC = engineCC;
            }

            public String getEngineDetails() {
                return engineDetails;
            }

            public void setEngineDetails(String engineDetails) {
                this.engineDetails = engineDetails;
            }

            public String getEngineLocation() {
                return engineLocation;
            }

            public void setEngineLocation(String engineLocation) {
                this.engineLocation = engineLocation;
            }

            public String getEngineRatedRPM() {
                return engineRatedRPM;
            }

            public void setEngineRatedRPM(String engineRatedRPM) {
                this.engineRatedRPM = engineRatedRPM;
            }

            public String getEngineType() {
                return engineType;
            }

            public void setEngineType(String engineType) {
                this.engineType = engineType;
            }

            public String getFIP() {
                return fIP;
            }

            public void setFIP(String fIP) {
                this.fIP = fIP;
            }

            public String getFloorType() {
                return floorType;
            }

            public void setFloorType(String floorType) {
                this.floorType = floorType;
            }

            public String getFogLamps() {
                return fogLamps;
            }

            public void setFogLamps(String fogLamps) {
                this.fogLamps = fogLamps;
            }

            public String getFoldingRearSeat() {
                return foldingRearSeat;
            }

            public void setFoldingRearSeat(String foldingRearSeat) {
                this.foldingRearSeat = foldingRearSeat;
            }

            public String getFrontAxleSuspension() {
                return frontAxleSuspension;
            }

            public void setFrontAxleSuspension(String frontAxleSuspension) {
                this.frontAxleSuspension = frontAxleSuspension;
            }

            public String getFrontTyre() {
                return frontTyre;
            }

            public void setFrontTyre(String frontTyre) {
                this.frontTyre = frontTyre;
            }

            public Object getFuelTank() {
                return fuelTank;
            }

            public void setFuelTank(Object fuelTank) {
                this.fuelTank = fuelTank;
            }

            public String getFuelTankCapacityLtr() {
                return fuelTankCapacityLtr;
            }

            public void setFuelTankCapacityLtr(String fuelTankCapacityLtr) {
                this.fuelTankCapacityLtr = fuelTankCapacityLtr;
            }

            public String getFuelType() {
                return fuelType;
            }

            public void setFuelType(String fuelType) {
                this.fuelType = fuelType;
            }

            public String getFuelSystem() {
                return fuelSystem;
            }

            public void setFuelSystem(String fuelSystem) {
                this.fuelSystem = fuelSystem;
            }

            public String getGearBox() {
                return gearBox;
            }

            public void setGearBox(String gearBox) {
                this.gearBox = gearBox;
            }

            public String getGradeAbility() {
                return gradeAbility;
            }

            public void setGradeAbility(String gradeAbility) {
                this.gradeAbility = gradeAbility;
            }

            public String getGroundClearance() {
                return groundClearance;
            }

            public void setGroundClearance(String groundClearance) {
                this.groundClearance = groundClearance;
            }

            public String getGVWGCW() {
                return gVWGCW;
            }

            public void setGVWGCW(String gVWGCW) {
                this.gVWGCW = gVWGCW;
            }

            public String getHeadRest() {
                return headRest;
            }

            public void setHeadRest(String headRest) {
                this.headRest = headRest;
            }

            public String getHP() {
                return hP;
            }

            public void setHP(String hP) {
                this.hP = hP;
            }

            public String getHPCat() {
                return hPCat;
            }

            public void setHPCat(String hPCat) {
                this.hPCat = hPCat;
            }

            public String getHydraulic() {
                return hydraulic;
            }

            public void setHydraulic(String hydraulic) {
                this.hydraulic = hydraulic;
            }

            public String getHydraulicLiftCapacityKg() {
                return hydraulicLiftCapacityKg;
            }

            public void setHydraulicLiftCapacityKg(String hydraulicLiftCapacityKg) {
                this.hydraulicLiftCapacityKg = hydraulicLiftCapacityKg;
            }

            public String getHydraulicType() {
                return hydraulicType;
            }

            public void setHydraulicType(String hydraulicType) {
                this.hydraulicType = hydraulicType;
            }

            public String getIntegratedMusicSystem() {
                return integratedMusicSystem;
            }

            public void setIntegratedMusicSystem(String integratedMusicSystem) {
                this.integratedMusicSystem = integratedMusicSystem;
            }

            public String getKerbWeight() {
                return kerbWeight;
            }

            public void setKerbWeight(String kerbWeight) {
                this.kerbWeight = kerbWeight;
            }

            public String getKeylessButtonStart() {
                return keylessButtonStart;
            }

            public void setKeylessButtonStart(String keylessButtonStart) {
                this.keylessButtonStart = keylessButtonStart;
            }

            public String getLiftingCapacityAtStandardFrame() {
                return liftingCapacityAtStandardFrame;
            }

            public void setLiftingCapacityAtStandardFrame(String liftingCapacityAtStandardFrame) {
                this.liftingCapacityAtStandardFrame = liftingCapacityAtStandardFrame;
            }

            public String getLoadBody() {
                return loadBody;
            }

            public void setLoadBody(String loadBody) {
                this.loadBody = loadBody;
            }

            public String getLoadBodyDimensions() {
                return loadBodyDimensions;
            }

            public void setLoadBodyDimensions(String loadBodyDimensions) {
                this.loadBodyDimensions = loadBodyDimensions;
            }

            public String getMaxPower() {
                return maxPower;
            }

            public void setMaxPower(String maxPower) {
                this.maxPower = maxPower;
            }

            public String getMaxReserveSpeed() {
                return maxReserveSpeed;
            }

            public void setMaxReserveSpeed(String maxReserveSpeed) {
                this.maxReserveSpeed = maxReserveSpeed;
            }

            public String getMaxSpeed() {
                return maxSpeed;
            }

            public void setMaxSpeed(String maxSpeed) {
                this.maxSpeed = maxSpeed;
            }

            public String getMaxTorqueKgm() {
                return maxTorqueKgm;
            }

            public void setMaxTorqueKgm(String maxTorqueKgm) {
                this.maxTorqueKgm = maxTorqueKgm;
            }

            public String getMileage() {
                return mileage;
            }

            public void setMileage(String mileage) {
                this.mileage = mileage;
            }

            public Integer getModelID() {
                return modelID;
            }

            public void setModelID(Integer modelID) {
                this.modelID = modelID;
            }

            public String getNoOfCylinder() {
                return noOfCylinder;
            }

            public void setNoOfCylinder(String noOfCylinder) {
                this.noOfCylinder = noOfCylinder;
            }

            public Object getNoOfgears() {
                return noOfgears;
            }

            public void setNoOfgears(Object noOfgears) {
                this.noOfgears = noOfgears;
            }

            public String getNoOfGearsForword() {
                return noOfGearsForword;
            }

            public void setNoOfGearsForword(String noOfGearsForword) {
                this.noOfGearsForword = noOfGearsForword;
            }

            public String getNoOfGearsReverse() {
                return noOfGearsReverse;
            }

            public void setNoOfGearsReverse(String noOfGearsReverse) {
                this.noOfGearsReverse = noOfGearsReverse;
            }

            public String getOverAllLength() {
                return overAllLength;
            }

            public void setOverAllLength(String overAllLength) {
                this.overAllLength = overAllLength;
            }

            public String getOverAllWidth() {
                return overAllWidth;
            }

            public void setOverAllWidth(String overAllWidth) {
                this.overAllWidth = overAllWidth;
            }

            public String getParkingSensors() {
                return parkingSensors;
            }

            public void setParkingSensors(String parkingSensors) {
                this.parkingSensors = parkingSensors;
            }

            public String getPayLoad() {
                return payLoad;
            }

            public void setPayLoad(String payLoad) {
                this.payLoad = payLoad;
            }

            public String getPowerKW() {
                return powerKW;
            }

            public void setPowerKW(String powerKW) {
                this.powerKW = powerKW;
            }

            public String getPowerWindow() {
                return powerWindow;
            }

            public void setPowerWindow(String powerWindow) {
                this.powerWindow = powerWindow;
            }

            public String getPTOHP() {
                return pTOHP;
            }

            public void setPTOHP(String pTOHP) {
                this.pTOHP = pTOHP;
            }

            public String getPTORPM() {
                return pTORPM;
            }

            public void setPTORPM(String pTORPM) {
                this.pTORPM = pTORPM;
            }

            public String getPTOSpeed() {
                return pTOSpeed;
            }

            public void setPTOSpeed(String pTOSpeed) {
                this.pTOSpeed = pTOSpeed;
            }

            public String getPTOType() {
                return pTOType;
            }

            public void setPTOType(String pTOType) {
                this.pTOType = pTOType;
            }

            public String getPumpType() {
                return pumpType;
            }

            public void setPumpType(String pumpType) {
                this.pumpType = pumpType;
            }

            public String getRainSensingWiper() {
                return rainSensingWiper;
            }

            public void setRainSensingWiper(String rainSensingWiper) {
                this.rainSensingWiper = rainSensingWiper;
            }

            public String getRatedRPM() {
                return ratedRPM;
            }

            public void setRatedRPM(String ratedRPM) {
                this.ratedRPM = ratedRPM;
            }

            public String getRearAC() {
                return rearAC;
            }

            public void setRearAC(String rearAC) {
                this.rearAC = rearAC;
            }

            public String getRearAxelSuspension() {
                return rearAxelSuspension;
            }

            public void setRearAxelSuspension(String rearAxelSuspension) {
                this.rearAxelSuspension = rearAxelSuspension;
            }

            public String getRearDefogger() {
                return rearDefogger;
            }

            public void setRearDefogger(String rearDefogger) {
                this.rearDefogger = rearDefogger;
            }

            public String getRearTyre() {
                return rearTyre;
            }

            public void setRearTyre(String rearTyre) {
                this.rearTyre = rearTyre;
            }

            public String getRearWiper() {
                return rearWiper;
            }

            public void setRearWiper(String rearWiper) {
                this.rearWiper = rearWiper;
            }

            public String getRPTOGRPTO() {
                return rPTOGRPTO;
            }

            public void setRPTOGRPTO(String rPTOGRPTO) {
                this.rPTOGRPTO = rPTOGRPTO;
            }

            public String getSeatBelt() {
                return seatBelt;
            }

            public void setSeatBelt(String seatBelt) {
                this.seatBelt = seatBelt;
            }

            public String getSeatBeltWarning() {
                return seatBeltWarning;
            }

            public void setSeatBeltWarning(String seatBeltWarning) {
                this.seatBeltWarning = seatBeltWarning;
            }

            public Integer getSeatingCapacity() {
                return seatingCapacity;
            }

            public void setSeatingCapacity(Integer seatingCapacity) {
                this.seatingCapacity = seatingCapacity;
            }

            public String getSeatType() {
                return seatType;
            }

            public void setSeatType(String seatType) {
                this.seatType = seatType;
            }

            public String getSeatUpholstery() {
                return seatUpholstery;
            }

            public void setSeatUpholstery(String seatUpholstery) {
                this.seatUpholstery = seatUpholstery;
            }

            public String getSpeed() {
                return speed;
            }

            public void setSpeed(String speed) {
                this.speed = speed;
            }

            public String getSportMode() {
                return sportMode;
            }

            public void setSportMode(String sportMode) {
                this.sportMode = sportMode;
            }

            public String getSteering() {
                return steering;
            }

            public void setSteering(String steering) {
                this.steering = steering;
            }

            public String getSteeringMountedControl() {
                return steeringMountedControl;
            }

            public void setSteeringMountedControl(String steeringMountedControl) {
                this.steeringMountedControl = steeringMountedControl;
            }

            public String getSteeringType() {
                return steeringType;
            }

            public void setSteeringType(String steeringType) {
                this.steeringType = steeringType;
            }

            public String getStrokeInMM() {
                return strokeInMM;
            }

            public void setStrokeInMM(String strokeInMM) {
                this.strokeInMM = strokeInMM;
            }

            public Integer getSubCategoryID() {
                return subCategoryID;
            }

            public void setSubCategoryID(Integer subCategoryID) {
                this.subCategoryID = subCategoryID;
            }

            public String getSunRoofMoonRoof() {
                return sunRoofMoonRoof;
            }

            public void setSunRoofMoonRoof(String sunRoofMoonRoof) {
                this.sunRoofMoonRoof = sunRoofMoonRoof;
            }

            public String getTMPS() {
                return tMPS;
            }

            public void setTMPS(String tMPS) {
                this.tMPS = tMPS;
            }

            public String getTorque() {
                return torque;
            }

            public void setTorque(String torque) {
                this.torque = torque;
            }

            public String getTotalWeight() {
                return totalWeight;
            }

            public void setTotalWeight(String totalWeight) {
                this.totalWeight = totalWeight;
            }

            public String getTransmissionType() {
                return transmissionType;
            }

            public void setTransmissionType(String transmissionType) {
                this.transmissionType = transmissionType;
            }

            public String getTuboChargeNutralAspirated() {
                return tuboChargeNutralAspirated;
            }

            public void setTuboChargeNutralAspirated(String tuboChargeNutralAspirated) {
                this.tuboChargeNutralAspirated = tuboChargeNutralAspirated;
            }

            public String getTurningRadius() {
                return turningRadius;
            }

            public void setTurningRadius(String turningRadius) {
                this.turningRadius = turningRadius;
            }

            public String getTurningRadiusOfBrakes() {
                return turningRadiusOfBrakes;
            }

            public void setTurningRadiusOfBrakes(String turningRadiusOfBrakes) {
                this.turningRadiusOfBrakes = turningRadiusOfBrakes;
            }

            public String getTypeOfClutchAndBrakePedal() {
                return typeOfClutchAndBrakePedal;
            }

            public void setTypeOfClutchAndBrakePedal(String typeOfClutchAndBrakePedal) {
                this.typeOfClutchAndBrakePedal = typeOfClutchAndBrakePedal;
            }

            public String getTypeOfGearReduction() {
                return typeOfGearReduction;
            }

            public void setTypeOfGearReduction(String typeOfGearReduction) {
                this.typeOfGearReduction = typeOfGearReduction;
            }

            public String getTyres() {
                return tyres;
            }

            public void setTyres(String tyres) {
                this.tyres = tyres;
            }

            public String getTyreSize() {
                return tyreSize;
            }

            public void setTyreSize(String tyreSize) {
                this.tyreSize = tyreSize;
            }

            public String getTyreSizeFront() {
                return tyreSizeFront;
            }

            public void setTyreSizeFront(String tyreSizeFront) {
                this.tyreSizeFront = tyreSizeFront;
            }

            public String getTyreSizeRear() {
                return tyreSizeRear;
            }

            public void setTyreSizeRear(String tyreSizeRear) {
                this.tyreSizeRear = tyreSizeRear;
            }

            public Integer getVersionID() {
                return versionID;
            }

            public void setVersionID(Integer versionID) {
                this.versionID = versionID;
            }

            public Object getWarrantyKilometer() {
                return warrantyKilometer;
            }

            public void setWarrantyKilometer(Object warrantyKilometer) {
                this.warrantyKilometer = warrantyKilometer;
            }

            public Object getWarrantyYear() {
                return warrantyYear;
            }

            public void setWarrantyYear(Object warrantyYear) {
                this.warrantyYear = warrantyYear;
            }

            public String getWebSite() {
                return webSite;
            }

            public void setWebSite(String webSite) {
                this.webSite = webSite;
            }

            public String getWeightKg() {
                return weightKg;
            }

            public void setWeightKg(String weightKg) {
                this.weightKg = weightKg;
            }

            public String getWheelBaseMM() {
                return wheelBaseMM;
            }

            public void setWheelBaseMM(String wheelBaseMM) {
                this.wheelBaseMM = wheelBaseMM;
            }


        }
    }
}
