package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetNewVehicleByIdResponse;
import retrofit2.Response;

public class NewVehicleDetails extends AppCompatActivity implements RequestNotifier {
    TextView mPointLinkage, mAbs, mAirbags, mAirCleaner, mAirCondition, mAlternateFuelType, mAuxiliary,
            mBackUpTorque, mBodyOption, Boreinmm, BoreStroke, Brakes, BrakeType, CentralLoking, ChildSeat, Clutch, ClutchType, CoolingSystem, CruiseControl, CylinderCapacityCC, DisplacementCC, Drive, DualStageAirbags, EmissionNorms, EngineCC, EngineLocation, EngineRatedRPM, EngineType, FIP, FloorType, FogLamps, FoldingRearSeat, FrontAxleSuspension, RearAxelSuspension, FrontTyre, RearTyre, FuelTank, FuelTankCapacityLtr, FuelType, FuelSystem,
            GearBox, GradeAbility, GroundClearance, GVWGCW, HeadRest, HP, HPCat, Hydraulic, HydraulicLiftCapacityKg, HydraulicType, IntegratedMusicSystem, KeylessButtonStart, LiftingCapacityAtStandardFrame, LoadBody, LoadBodyDimensions, MaxPower, MaxReserveSpeed, MaxSpeed, Mileage, NoOfCylinder, MaxTorqueKgm, NoOfgears, NoOfGearsForword, NoOfGearsReverse, OverAllLength, OverAllWidth, ParkingSensors, PayLoad, PowerKW, PowerWindow, PTOHP, PTORPM, PTOSpeed, PTOType, PumpType, RainSensingWiper, RatedRPM, RearAC, RearDefogger,
            RearWiper, SeatBelt,seatupholestry, kerbweight, RPTOGRPTO, Axelconfiguration, SeatBeltWarning, SeatingCapacity, AdditionalInfo, SeatType, Speed, SportMode, Steering, SteeringMountedControl, SteeringType, StrokeInMM, SunRoofMoonRoof, TMPS, Torque, TotalWeight, TransmissionType, TuboChargeNutralAspirated, TurningRadius, TurningRadiusOfBrakes, TypeOfClutchAndBrakePedal, Tyres, TyreSize, TyreSizeFront, TyreSizeRear, WarrantyKilometer, WarrantyYear, WebSite, WeightKg, WheelBaseMM, EngineDetails, Description;
    int newVehicleId;

    LinearLayout lPointLinkage, lAbs, lAirbags, lAirCleaner, lAirCondition, lAlternateFuelType, lAuxiliary,
            lBackUpTorque, lBodyOption, lBoreinmm, lBoreStroke, lBrakes, lBrakeType, lCentralLoking, lChildSeat, lClutch, lClutchType, lCoolingSystem, lCruiseControl, lCylinderCapacityCC, lDisplacementCC, lDrive, lDualStageAirbags, lEmissionNorms, lEngineCC, lEngineLocation, lEngineRatedRPM, lEngineType, lFIP, lFloorType, lFogLamps, lFoldingRearSeat, lFrontAxleSuspension, lRearAxelSuspension, lFrontTyre, lRearTyre, lFuelTank, lFuelTankCapacityLtr, lFuelType, lFuelSystem,
            lGearBox, lGradeAbility, lGroundClearance, lGVWGCW, lHeadRest, lHP, lHPCat, lHydraulic, lHydraulicLiftCapacityKg, lHydraulicType, lIntegratedMusicSystem, lKeylessButtonStart, lLiftingCapacityAtStandardFrame, lLoadBody, lLoadBodyDimensions, lMaxPower, lMaxReserveSpeed, lMaxSpeed, lMileage, lNoOfCylinder, lMaxTorqueKgm, lNoOfgears, lNoOfGearsForword, lNoOfGearsReverse, lOverAllLength, lOverAllWidth, lParkingSensors, lPayLoad, lPowerKW, lPowerWindow, lPTOHP, lPTORPM, lPTOSpeed, lPTOType, lPumpType, lRainSensingWiper, lRatedRPM, lRearAC, lRearDefogger,
            lRearWiper, lSeatBelt, lKerbweight, lRPTOGRPTO,lseatupholestry, lAxelconfiguration, lSeatBeltWarning, lSeatingCapacity, lSeatType, lAdditionalInfo, lSpeed, lSportMode, lSteering, lSteeringMountedControl, lSteeringType, lStrokeInMM, lSunRoofMoonRoof, lTMPS, lTorque, lTotalWeight, lTransmissionType, lTuboChargeNutralAspirated, lTurningRadius, lTurningRadiusOfBrakes, lTypeOfClutchAndBrakePedal, lTyres, lTyreSize, lTyreSizeFront, lTyreSizeRear, lWarrantyKilometer, lWarrantyYear, lWebSite, lWeightKg, lWheelBaseMM, lEngineDetails, lDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("New Vehicle Details");
        newVehicleId = getIntent().getIntExtra("newVehicleId", 0);
        mPointLinkage = (TextView) findViewById(R.id.txtPointLinkage1);
        mAbs = (TextView) findViewById(R.id.ABS1);
        mAirbags = (TextView) findViewById(R.id.AirBags);
        mAirCleaner = (TextView) findViewById(R.id.AirCleaner1);
        mAirCondition = (TextView) findViewById(R.id.AirCondition1);
        mAlternateFuelType = (TextView) findViewById(R.id.AlternateFuelType1);
        mAuxiliary = (TextView) findViewById(R.id.AuxiliaryHydValve1);
        mBackUpTorque = (TextView) findViewById(R.id.BackUpTorque1);
        mBodyOption = (TextView) findViewById(R.id.BodyOptions1);
        Boreinmm = (TextView) findViewById(R.id.Boreinmm1);
        BoreStroke = (TextView) findViewById(R.id.BoreStroke1);
        Brakes = (TextView) findViewById(R.id.Brakes1);
        BrakeType = (TextView) findViewById(R.id.BrakeType1);
        CentralLoking = (TextView) findViewById(R.id.CentralLoking1);
        ChildSeat = (TextView) findViewById(R.id.ChildSeat1);
        Clutch = (TextView) findViewById(R.id.Clutch1);
        ClutchType = (TextView) findViewById(R.id.ClutchType1);
        CoolingSystem = (TextView) findViewById(R.id.CoolingSystem1);
        CruiseControl = (TextView) findViewById(R.id.CruiseControl1);
        CylinderCapacityCC = (TextView) findViewById(R.id.CylinderCapacityCC1);
        DisplacementCC = (TextView) findViewById(R.id.DisplacementCC1);
        Drive = (TextView) findViewById(R.id.Drive1);
        DualStageAirbags = (TextView) findViewById(R.id.DualStageAirbags1);
        EmissionNorms = (TextView) findViewById(R.id.EmissionNorms1);
        EngineCC = (TextView) findViewById(R.id.EngineCC1);
        EngineLocation = (TextView) findViewById(R.id.EngineLocation1);
        EngineRatedRPM = (TextView) findViewById(R.id.EngineRatedRPM1);
        EngineType = (TextView) findViewById(R.id.EngineType1);
        FIP = (TextView) findViewById(R.id.FIP1);
        FloorType = (TextView) findViewById(R.id.FloorType1);
        FogLamps = (TextView) findViewById(R.id.FogLamps1);
        FoldingRearSeat = (TextView) findViewById(R.id.FoldingRearSeat1);
        FrontAxleSuspension = (TextView) findViewById(R.id.FrontAxleSuspension1);
        RearAxelSuspension = (TextView) findViewById(R.id.RearAxelSuspension1);
        FrontTyre = (TextView) findViewById(R.id.FrontTyre1);
        RearTyre = (TextView) findViewById(R.id.RearTyre1);
        FuelTank = (TextView) findViewById(R.id.FuelTank1);
        FuelTankCapacityLtr = (TextView) findViewById(R.id.FuelTankCapacityLtr1);
        FuelType = (TextView) findViewById(R.id.FuelType1);
        FuelSystem = (TextView) findViewById(R.id.FuelSystem1);
        GearBox = (TextView) findViewById(R.id.GearBox1);
        GradeAbility = (TextView) findViewById(R.id.GradeAbility1);
        GroundClearance = (TextView) findViewById(R.id.GroundClearance1);
        GVWGCW = (TextView) findViewById(R.id.GVWGCW1);
        HeadRest = (TextView) findViewById(R.id.HeadRest1);
        HP = (TextView) findViewById(R.id.HP1);
        HPCat = (TextView) findViewById(R.id.HPCat1);
        Hydraulic = (TextView) findViewById(R.id.Hydraulic1);
        HydraulicLiftCapacityKg = (TextView) findViewById(R.id.HydraulicLiftCapacityKg1);
        HydraulicType = (TextView) findViewById(R.id.HydraulicType1);
        IntegratedMusicSystem = (TextView) findViewById(R.id.IntegratedMusicSystem1);
        KeylessButtonStart = (TextView) findViewById(R.id.KeylessButtonStart1);
        LiftingCapacityAtStandardFrame = (TextView) findViewById(R.id.LiftingCapacityAtStandardFrame1);
        LoadBody = (TextView) findViewById(R.id.LoadBody1);
        LoadBodyDimensions = (TextView) findViewById(R.id.LoadBodyDimensions1);
        MaxPower = (TextView) findViewById(R.id.MaxPower1);
        MaxReserveSpeed = (TextView) findViewById(R.id.MaxReserveSpeed1);
        MaxSpeed = (TextView) findViewById(R.id.MaxSpeed1);
        Mileage = (TextView) findViewById(R.id.Mileage1);
        NoOfCylinder = (TextView) findViewById(R.id.NoOfCylinder1);
        MaxTorqueKgm = (TextView) findViewById(R.id.MaxTorqueKgm1);
        NoOfgears = (TextView) findViewById(R.id.NoOfgears1);
        NoOfGearsForword = (TextView) findViewById(R.id.NoOfGearsForword1);
        NoOfGearsReverse = (TextView) findViewById(R.id.NoOfGearsReverse1);
        OverAllLength = (TextView) findViewById(R.id.OverAllLength1);
        OverAllWidth = (TextView) findViewById(R.id.OverAllWidth1);
        ParkingSensors = (TextView) findViewById(R.id.ParkingSensors1);
        PayLoad = (TextView) findViewById(R.id.PayLoad1);
        PowerKW = (TextView) findViewById(R.id.PowerKW1);
        PowerWindow = (TextView) findViewById(R.id.PowerWindow1);
        PTOHP = (TextView) findViewById(R.id.PTOHP1);
        PTORPM = (TextView) findViewById(R.id.PTORPM1);
        PTOSpeed = (TextView) findViewById(R.id.PTOSpeed1);
        PTOType = (TextView) findViewById(R.id.PTOType1);
        PumpType = (TextView) findViewById(R.id.PumpType1);
        RainSensingWiper = (TextView) findViewById(R.id.RainSensingWiper1);
        RatedRPM = (TextView) findViewById(R.id.RatedRPM1);
        RearAC = (TextView) findViewById(R.id.RearAC1);
        RearDefogger = (TextView) findViewById(R.id.RearDefogger1);
        RearWiper = (TextView) findViewById(R.id.RearWiper1);
        SeatBelt = (TextView) findViewById(R.id.SeatBelt1);
        RPTOGRPTO = (TextView) findViewById(R.id.RPTOGRPTO1);
        SeatBeltWarning = (TextView) findViewById(R.id.SeatBeltWarning1);
        SeatingCapacity = (TextView) findViewById(R.id.SeatingCapacity1);
        SeatType = (TextView) findViewById(R.id.SeatType1);
        Speed = (TextView) findViewById(R.id.Speed1);
        SportMode = (TextView) findViewById(R.id.SportMode1);
        Steering = (TextView) findViewById(R.id.Steering1);
        SteeringMountedControl = (TextView) findViewById(R.id.SteeringMountedControl1);
        SteeringType = (TextView) findViewById(R.id.SteeringType1);
        StrokeInMM = (TextView) findViewById(R.id.StrokeInMM1);
        SunRoofMoonRoof = (TextView) findViewById(R.id.SunRoofMoonRoof1);
        TMPS = (TextView) findViewById(R.id.TMPS1);
        Torque = (TextView) findViewById(R.id.Torque1);
        TotalWeight = (TextView) findViewById(R.id.TotalWeight1);
        TransmissionType = (TextView) findViewById(R.id.TransmissionType1);
        TuboChargeNutralAspirated = (TextView) findViewById(R.id.TuboChargeNutralAspirated1);
        TurningRadius = (TextView) findViewById(R.id.TurningRadius1);
        TurningRadiusOfBrakes = (TextView) findViewById(R.id.TurningRadiusOfBrakes1);
        TypeOfClutchAndBrakePedal = (TextView) findViewById(R.id.TypeOfClutchAndBrakePedal1);
        Tyres = (TextView) findViewById(R.id.Tyres1);
        TyreSize = (TextView) findViewById(R.id.TyreSize1);
        TyreSizeFront = (TextView) findViewById(R.id.TyreSizeFront1);
        TyreSizeRear = (TextView) findViewById(R.id.TyreSizeRear1);
        WarrantyKilometer = (TextView) findViewById(R.id.WarrantyKilometer1);
        WarrantyYear = (TextView) findViewById(R.id.WarrantyYear1);
        WebSite = (TextView) findViewById(R.id.WebSite1);
        WeightKg = (TextView) findViewById(R.id.WeightKg1);
        WheelBaseMM = (TextView) findViewById(R.id.WheelBaseMM1);
        EngineDetails = (TextView) findViewById(R.id.EngineDetails1);
        Description = (TextView) findViewById(R.id.Description1);
        AdditionalInfo = (TextView) findViewById(R.id.Additionalinfo1);
        Axelconfiguration = (TextView) findViewById(R.id.axelconfiguration);
        kerbweight = (TextView) findViewById(R.id.KerbWeight1);
        seatupholestry = (TextView) findViewById(R.id.SeatUpholstery);


        /*Linear layouts*/
        lPointLinkage = (LinearLayout) findViewById(R.id.lPointLinkage);
        lAbs = (LinearLayout) findViewById(R.id.lABS);
        lAirbags = (LinearLayout) findViewById(R.id.lAirBags);
        lAirCleaner = (LinearLayout) findViewById(R.id.lAirCleaner);
        lAirCondition = (LinearLayout) findViewById(R.id.lAirCondition);
        lAlternateFuelType = (LinearLayout) findViewById(R.id.lAlternateFuelType);
        lAuxiliary = (LinearLayout) findViewById(R.id.lAuxiliaryHydValve);
        lBackUpTorque = (LinearLayout) findViewById(R.id.lBackUpTorque);
        lBodyOption = (LinearLayout) findViewById(R.id.lBodyOptions);
        lBoreinmm = (LinearLayout) findViewById(R.id.lBoreinmm);
        lBoreStroke = (LinearLayout) findViewById(R.id.lBoreStroke);
        lBrakes = (LinearLayout) findViewById(R.id.lBrakes);
        lBrakeType = (LinearLayout) findViewById(R.id.lBrakeType);
        lCentralLoking = (LinearLayout) findViewById(R.id.lCentralLoking);
      //  lHypothetication = (LinearLayout) findViewById(R.id.lhypothetication);
        lChildSeat = (LinearLayout) findViewById(R.id.lChildSeat);
        lClutch = (LinearLayout) findViewById(R.id.lClutch);
        lClutchType = (LinearLayout) findViewById(R.id.lClutchType);
        lCoolingSystem = (LinearLayout) findViewById(R.id.lCoolingSystem);
        lCruiseControl = (LinearLayout) findViewById(R.id.lCruiseControl);
        lCylinderCapacityCC = (LinearLayout) findViewById(R.id.lCylinderCapacityCC);
        lDisplacementCC = (LinearLayout) findViewById(R.id.lDisplacementCC);
        lDrive = (LinearLayout) findViewById(R.id.lDrive);
        lDualStageAirbags = (LinearLayout) findViewById(R.id.lDualStageAirbags);
        lEmissionNorms = (LinearLayout) findViewById(R.id.lEmissionNorms);
        lEngineCC = (LinearLayout) findViewById(R.id.lEngineCC);
        lEngineLocation = (LinearLayout) findViewById(R.id.lEngineLocation);
        lEngineRatedRPM = (LinearLayout) findViewById(R.id.lEngineRatedRPM);
        lEngineType = (LinearLayout) findViewById(R.id.lEngineType);
        lFIP = (LinearLayout) findViewById(R.id.lFIP);
        lFloorType = (LinearLayout) findViewById(R.id.lFloorType);
        lFogLamps = (LinearLayout) findViewById(R.id.lFogLamps);
        lFoldingRearSeat = (LinearLayout) findViewById(R.id.lFoldingRearSeat);
        lFrontAxleSuspension = (LinearLayout) findViewById(R.id.lFrontAxleSuspension);
        lRearAxelSuspension = (LinearLayout) findViewById(R.id.lRearAxelSuspension);
        lFrontTyre = (LinearLayout) findViewById(R.id.lFrontTyre);
        lRearTyre = (LinearLayout) findViewById(R.id.lRearTyre);
        lFuelTank = (LinearLayout) findViewById(R.id.lFuelTank);
        lFuelTankCapacityLtr = (LinearLayout) findViewById(R.id.lFuelTankCapacityLtr);
        lFuelType = (LinearLayout) findViewById(R.id.lFuelType);
        lFuelSystem = (LinearLayout) findViewById(R.id.lFuelSystem);
        lGearBox = (LinearLayout) findViewById(R.id.lGearBox);
        lGradeAbility = (LinearLayout) findViewById(R.id.lGradeAbility);
        lGroundClearance = (LinearLayout) findViewById(R.id.lGroundClearance);
        lGVWGCW = (LinearLayout) findViewById(R.id.lGVWGCW);
        lHeadRest = (LinearLayout) findViewById(R.id.lHeadRest);
        lHP = (LinearLayout) findViewById(R.id.lHP);
        lHPCat = (LinearLayout) findViewById(R.id.lHPCat);
        lHydraulic = (LinearLayout) findViewById(R.id.lHydraulic);
        lHydraulicLiftCapacityKg = (LinearLayout) findViewById(R.id.lHydraulicLiftCapacityKg);
        lHydraulicType = (LinearLayout) findViewById(R.id.lHydraulicType);
        lIntegratedMusicSystem = (LinearLayout) findViewById(R.id.lIntegratedMusicSystem);
        lKeylessButtonStart = (LinearLayout) findViewById(R.id.lKeylessButtonStart);
        lLiftingCapacityAtStandardFrame = (LinearLayout) findViewById(R.id.lLiftingCapacityAtStandardFrame);
        lLoadBody = (LinearLayout) findViewById(R.id.lLoadBody);
        lLoadBodyDimensions = (LinearLayout) findViewById(R.id.lLoadBodyDimensions);
        lMaxPower = (LinearLayout) findViewById(R.id.lMaxPower);
        lMaxReserveSpeed = (LinearLayout) findViewById(R.id.lMaxReserveSpeed);
        lMaxSpeed = (LinearLayout) findViewById(R.id.lMaxSpeed);
        lMileage = (LinearLayout) findViewById(R.id.lMileage);
        lNoOfCylinder = (LinearLayout) findViewById(R.id.lNoOfCylinder);
        lMaxTorqueKgm = (LinearLayout) findViewById(R.id.lMaxTorqueKgm);
        lNoOfgears = (LinearLayout) findViewById(R.id.lNoOfgears);
        lNoOfGearsForword = (LinearLayout) findViewById(R.id.lNoOfGearsForword);
        lNoOfGearsReverse = (LinearLayout) findViewById(R.id.lNoOfGearsReverse);
        lOverAllLength = (LinearLayout) findViewById(R.id.lOverAllLength);
        lOverAllWidth = (LinearLayout) findViewById(R.id.lOverAllWidth);
        lParkingSensors = (LinearLayout) findViewById(R.id.lParkingSensors);
        lPayLoad = (LinearLayout) findViewById(R.id.lPayLoad);
        lPowerKW = (LinearLayout) findViewById(R.id.lPowerKW);
        lPowerWindow = (LinearLayout) findViewById(R.id.lPowerWindow);
        lPTOHP = (LinearLayout) findViewById(R.id.lPTOHP);
        lPTORPM = (LinearLayout) findViewById(R.id.lPTORPM);
        lPTOSpeed = (LinearLayout) findViewById(R.id.lPTOSpeed);
        lPTOType = (LinearLayout) findViewById(R.id.lPTOType);
        lPumpType = (LinearLayout) findViewById(R.id.lPumpType);
        lRainSensingWiper = (LinearLayout) findViewById(R.id.lRainSensingWiper);
        lRatedRPM = (LinearLayout) findViewById(R.id.lRatedRPM);
        lRearAC = (LinearLayout) findViewById(R.id.lRearAC);
        lRearDefogger = (LinearLayout) findViewById(R.id.lRearDefogger);
        lRearWiper = (LinearLayout) findViewById(R.id.lRearWiper);
        lSeatBelt = (LinearLayout) findViewById(R.id.lSeatBelt);
        lRPTOGRPTO = (LinearLayout) findViewById(R.id.lRPTOGRPTO);
        lSeatBeltWarning = (LinearLayout) findViewById(R.id.lSeatBeltWarning);
        lSeatingCapacity = (LinearLayout) findViewById(R.id.lSeatingCapacity);
        lSeatType = (LinearLayout) findViewById(R.id.lSeatType);
        lSpeed = (LinearLayout) findViewById(R.id.lSpeed);
        lSportMode = (LinearLayout) findViewById(R.id.lSportMode);
        lSteering = (LinearLayout) findViewById(R.id.lSteering);
        lSteeringMountedControl = (LinearLayout) findViewById(R.id.lSteeringMountedControl);
        lSteeringType = (LinearLayout) findViewById(R.id.lSteeringType);
        lStrokeInMM = (LinearLayout) findViewById(R.id.lStrokeInMM);
        lSunRoofMoonRoof = (LinearLayout) findViewById(R.id.lSunRoofMoonRoof);
        lTMPS = (LinearLayout) findViewById(R.id.lTMPS);
        lTorque = (LinearLayout) findViewById(R.id.lTorque);
        lTotalWeight = (LinearLayout) findViewById(R.id.lTotalWeight);
        lTransmissionType = (LinearLayout) findViewById(R.id.lTransmissionType);
        lTuboChargeNutralAspirated = (LinearLayout) findViewById(R.id.lTuboChargeNutralAspirated);
        lTurningRadius = (LinearLayout) findViewById(R.id.lTurningRadius);
        lTurningRadiusOfBrakes = (LinearLayout) findViewById(R.id.lTurningRadiusOfBrakes);
        lTypeOfClutchAndBrakePedal = (LinearLayout) findViewById(R.id.lTypeOfClutchAndBrakePedal);
        lTyres = (LinearLayout) findViewById(R.id.lTyres);
        lTyreSize = (LinearLayout) findViewById(R.id.lTyreSize);
        lTyreSizeFront = (LinearLayout) findViewById(R.id.lTyreSizeFront);
        lTyreSizeRear = (LinearLayout) findViewById(R.id.lTyreSizeRear);
        lWarrantyKilometer = (LinearLayout) findViewById(R.id.lWarrantyKilometer);
        lWarrantyYear = (LinearLayout) findViewById(R.id.lWarrantyYear);
        lWebSite = (LinearLayout) findViewById(R.id.lWebSite);
        lWeightKg = (LinearLayout) findViewById(R.id.lWeightKg);
        lWheelBaseMM = (LinearLayout) findViewById(R.id.lWheelBaseMM);
        lEngineDetails = (LinearLayout) findViewById(R.id.lEngineDetails);
        lDescription = (LinearLayout) findViewById(R.id.lDescription);
        lAdditionalInfo = (LinearLayout) findViewById(R.id.lAdditionalinfo);
        lAxelconfiguration = (LinearLayout) findViewById(R.id.laxelconfiguration);
        lKerbweight = (LinearLayout) findViewById(R.id.lKerbWeight);
        lseatupholestry = (LinearLayout) findViewById(R.id.lSeatUpholstery);

        getData();
    }

    /*
    Get New Vehicle Data...
     */
    private void getData() {
        ApiCall apiCall = new ApiCall(NewVehicleDetails.this, this);
        apiCall.getnewvehiclebyid(newVehicleId);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetNewVehicleByIdResponse profileGroupResponse = (GetNewVehicleByIdResponse) response.body();
                if (!profileGroupResponse.getSuccess().getNewVehicle().isEmpty()) {
                    for (GetNewVehicleByIdResponse.NewVehicle success : profileGroupResponse.getSuccess().getNewVehicle()) {
                        mPointLinkage.setText(success.getThreePointLinkage());
                        mAbs.setText(success.getABS());
                        mAirbags.setText(success.getAirBags());
                        mAirCleaner.setText(success.getAirCleaner());
                        mAirCondition.setText(success.getAirCondition());
                        mAlternateFuelType.setText(success.getAlternateFuelType());
                        mAuxiliary.setText(success.getAuxiliaryHydValve());
                        mBackUpTorque.setText(success.getBackUpTorque());
                        mBodyOption.setText(success.getBodyOptions());
                        Boreinmm.setText(success.getBoreinmm());
                        BoreStroke.setText(success.getBoreStroke());
                        Brakes.setText(success.getBrakes());
                        BrakeType.setText(success.getBrakeType());
                        CentralLoking.setText(success.getCentralLoking());
                        ChildSeat.setText(success.getChildSeat());
                        Clutch.setText(success.getClutch());
                        ClutchType.setText(success.getClutchType());
                        CoolingSystem.setText(success.getCoolingSystem());
                        CruiseControl.setText(success.getCruiseControl());
                        CylinderCapacityCC.setText(success.getCylinderCapacityCC());
                        DisplacementCC.setText(success.getDisplacementCC());
                        Drive.setText(success.getDrive());
                        DualStageAirbags.setText(success.getDualStageAirbags());
                        EmissionNorms.setText(success.getEmissionNorms());
                        EngineCC.setText(success.getEngineCC());
                        EngineDetails.setText(success.getEngineDetails());
                        EngineLocation.setText(success.getEngineLocation());
                        EngineRatedRPM.setText(success.getEngineRatedRPM());
                        EngineType.setText(success.getEngineType());
                        FIP.setText(success.getFIP());
                        FloorType.setText(success.getFloorType());
                        FogLamps.setText(success.getFogLamps());
                        FoldingRearSeat.setText(success.getFoldingRearSeat());
                        FrontAxleSuspension.setText(success.getFrontAxleSuspension());
                        RearAxelSuspension.setText(success.getRearAxelSuspension());
                        FrontTyre.setText(success.getFrontTyre());
                        RearTyre.setText(success.getRearTyre());
                        FuelTank.setText(success.getFuelTank());
                        FuelSystem.setText(success.getFuelSystem());
                        FuelTankCapacityLtr.setText(success.getFuelTankCapacityLtr());
                        FuelType.setText(success.getFuelType());
                        GearBox.setText(success.getGearBox());
                        GradeAbility.setText(success.getGradeAbility());
                        GroundClearance.setText(success.getGroundClearance());
                        GVWGCW.setText(success.getGVWGCW());
                        HeadRest.setText(success.getHeadRest());
                        HP.setText(success.getHP());
                        HPCat.setText(success.getHPCat());
                        Hydraulic.setText(success.getHydraulic());
                        HydraulicLiftCapacityKg.setText(success.getHydraulicLiftCapacityKg());
                        HydraulicType.setText(success.getHydraulicType());
                        IntegratedMusicSystem.setText(success.getIntegratedMusicSystem());
                        KeylessButtonStart.setText(success.getKeylessButtonStart());
                        LiftingCapacityAtStandardFrame.setText(success.getLiftingCapacityAtStandardFrame());
                        LoadBody.setText(success.getLoadBody());
                        LoadBodyDimensions.setText(success.getLoadBodyDimensions());
                        MaxPower.setText(success.getMaxPower());
                        MaxReserveSpeed.setText(success.getMaxReserveSpeed());
                        MaxSpeed.setText(success.getMaxSpeed());
                        MaxTorqueKgm.setText(success.getMaxTorqueKgm());
                        Mileage.setText(success.getMileage());
                        NoOfCylinder.setText(success.getNoOfCylinder());
                        NoOfgears.setText(success.getNoOfgears());
                        NoOfGearsForword.setText(success.getNoOfGearsForword());
                        NoOfGearsReverse.setText(success.getNoOfGearsReverse());
                        OverAllLength.setText(success.getOverAllLength());
                        OverAllWidth.setText(success.getOverAllWidth());
                        ParkingSensors.setText(success.getParkingSensors());
                        PayLoad.setText(success.getPayLoad());
                        PowerKW.setText(success.getPowerKW());
                        PowerWindow.setText(success.getPowerWindow());
                        PTOHP.setText(success.getPTOHP());
                        PTORPM.setText(success.getPTORPM());
                        PTOSpeed.setText(success.getPTOSpeed());
                        PTOType.setText(success.getPTOType());
                        PumpType.setText(success.getPumpType());
                        RainSensingWiper.setText(success.getRainSensingWiper());
                        RatedRPM.setText(success.getRatedRPM());
                        RearAC.setText(success.getRearAC());
                        RearDefogger.setText(success.getRearDefogger());
                        RearWiper.setText(success.getRearWiper());
                        SeatBelt.setText(success.getSeatBelt());
                        RPTOGRPTO.setText(success.getRPTOGRPTO());
                        SeatBeltWarning.setText(success.getSeatBeltWarning());
                        SeatingCapacity.setText(success.getSeatingCapacity());
                        SeatType.setText(success.getSeatType());
                        Speed.setText(success.getSpeed());
                        SportMode.setText(success.getSportMode());
                        Steering.setText(success.getSteering());
                        SteeringMountedControl.setText(success.getSteeringMountedControl());
                        SteeringType.setText(success.getSteeringType());
                        StrokeInMM.setText(success.getStrokeInMM());
                        SunRoofMoonRoof.setText(success.getSunRoofMoonRoof());
                        TMPS.setText(success.getTMPS());
                        Torque.setText(success.getTorque());
                        TotalWeight.setText(success.getTotalWeight());
                        TransmissionType.setText(success.getTransmissionType());
                        TuboChargeNutralAspirated.setText(success.getTuboChargeNutralAspirated());
                        TurningRadius.setText(success.getTurningRadius());
                        TurningRadiusOfBrakes.setText(success.getTurningRadiusOfBrakes());
                        TypeOfClutchAndBrakePedal.setText(success.getTypeOfClutchAndBrakePedal());
                        Tyres.setText(success.getTyres());
                        TyreSize.setText(success.getTyreSize());
                        TyreSizeFront.setText(success.getTyreSizeFront());
                        TyreSizeRear.setText(success.getTyreSizeRear());
                        WarrantyKilometer.setText(success.getWarrantyKilometer());
                        WarrantyYear.setText(success.getWarrantyYear());
                        WebSite.setText(success.getWebSite());
                        WeightKg.setText(success.getWeightKg());
                        WheelBaseMM.setText(success.getWheelBaseMM());
                        Description.setText(success.getDescription());
                        AdditionalInfo.setText(success.getAdditionalInformation());
                        Axelconfiguration.setText(success.getAxleConfiguration());
                        kerbweight.setText(success.getKerbWeight());
                        seatupholestry.setText(success.getSeatUpholstery());
                      //  Noofdoors.setText(success.getno());

                        if (success.getCategoryName().equalsIgnoreCase("Bus")) {
                            lDescription.setVisibility(View.VISIBLE);
                            lEmissionNorms.setVisibility(View.VISIBLE);
                            lDisplacementCC.setVisibility(View.VISIBLE);
                            lMaxPower.setVisibility(View.VISIBLE);
                            lMaxTorqueKgm.setVisibility(View.VISIBLE);
                            lTransmissionType.setVisibility(View.VISIBLE);
                            lClutch.setVisibility(View.VISIBLE);
                            lGearBox.setVisibility(View.VISIBLE);
                            lFuelType.setVisibility(View.VISIBLE);
                            lFuelTank.setVisibility(View.VISIBLE);
                            lTurningRadius.setVisibility(View.VISIBLE);
                            lMaxSpeed.setVisibility(View.VISIBLE);
                            lEngineLocation.setVisibility(View.VISIBLE);
                            lFloorType.setVisibility(View.VISIBLE);
                            lFrontAxleSuspension.setVisibility(View.VISIBLE);
                            lRearAxelSuspension.setVisibility(View.VISIBLE);
                            lTyres.setVisibility(View.VISIBLE);
                            lWheelBaseMM.setVisibility(View.VISIBLE);
                            lOverAllLength.setVisibility(View.VISIBLE);
                            lGroundClearance.setVisibility(View.VISIBLE);
                            lGVWGCW.setVisibility(View.VISIBLE);
                            lBrakes.setVisibility(View.VISIBLE);
                            lSeatingCapacity.setVisibility(View.VISIBLE);
                            lSeatType.setVisibility(View.VISIBLE);
                            lAdditionalInfo.setVisibility(View.VISIBLE);
                        } else if (success.getCategoryName().equalsIgnoreCase("Car")) {
                            lEngineCC.setVisibility(View.VISIBLE);
                            lSeatingCapacity.setVisibility(View.VISIBLE);
                            lDisplacementCC.setVisibility(View.VISIBLE);
                            lFuelType.setVisibility(View.VISIBLE);
                            lMaxPower.setVisibility(View.VISIBLE);
                            lMaxTorqueKgm.setVisibility(View.VISIBLE);
                            lMileage.setVisibility(View.VISIBLE);
                            lAlternateFuelType.setVisibility(View.VISIBLE);
                            lTransmissionType.setVisibility(View.VISIBLE);
                            lNoOfgears.setVisibility(View.VISIBLE);
                            lDrive.setVisibility(View.VISIBLE);
                            lAirCondition.setVisibility(View.VISIBLE);
                            lPowerWindow.setVisibility(View.VISIBLE);
                            lCentralLoking.setVisibility(View.VISIBLE);
                            lAbs.setVisibility(View.VISIBLE);
                            lAirbags.setVisibility(View.VISIBLE);
                            lseatupholestry.setVisibility(View.VISIBLE);
                            lWheelBaseMM.setVisibility(View.VISIBLE);
                            lKerbweight.setVisibility(View.VISIBLE);
                            lGroundClearance.setVisibility(View.VISIBLE);
                           // lNoofdoors.setVisibility(View.VISIBLE);
                            lFuelTankCapacityLtr.setVisibility(View.VISIBLE);
                            lEngineType.setVisibility(View.VISIBLE);
                            lKeylessButtonStart.setVisibility(View.VISIBLE);
                            lSportMode.setVisibility(View.VISIBLE);
                            lDrive.setVisibility(View.VISIBLE);
                            lRearAxelSuspension.setVisibility(View.VISIBLE);
                            lFrontAxleSuspension.setVisibility(View.VISIBLE);
                            lTurningRadiusOfBrakes.setVisibility(View.VISIBLE);
                            lSteeringType.setVisibility(View.VISIBLE);
                            lFrontTyre.setVisibility(View.VISIBLE);
                            lRearTyre.setVisibility(View.VISIBLE);
                            lDualStageAirbags.setVisibility(View.VISIBLE);
                            lHeadRest.setVisibility(View.VISIBLE);
                            lSeatBeltWarning.setVisibility(View.VISIBLE);
                            lRearAC.setVisibility(View.VISIBLE);
                            lCruiseControl.setVisibility(View.VISIBLE);
                            lParkingSensors.setVisibility(View.VISIBLE);
                            lFoldingRearSeat.setVisibility(View.VISIBLE);
                            lRearDefogger.setVisibility(View.VISIBLE);
                            lRearWiper.setVisibility(View.VISIBLE);
                            lRainSensingWiper.setVisibility(View.VISIBLE);
                            lSunRoofMoonRoof.setVisibility(View.VISIBLE);
                            lFogLamps.setVisibility(View.VISIBLE);
                            lIntegratedMusicSystem.setVisibility(View.VISIBLE);
                            lSteeringMountedControl.setVisibility(View.VISIBLE);
                            lWarrantyKilometer.setVisibility(View.VISIBLE);
                            lWarrantyYear.setVisibility(View.VISIBLE);

                        } else if (success.getCategoryName().equalsIgnoreCase("Construction Equipment")) {

                        } else if (success.getCategoryName().equalsIgnoreCase("Commercial Vehicle")) {
                            lEngineCC.setVisibility(View.VISIBLE);
                            lDisplacementCC.setVisibility(View.VISIBLE);
                            lMaxPower.setVisibility(View.VISIBLE);
                            lMaxTorqueKgm.setVisibility(View.VISIBLE);
                            lTransmissionType.setVisibility(View.VISIBLE);
                            lClutch.setVisibility(View.VISIBLE);
                            lGearBox.setVisibility(View.VISIBLE);
                            lFuelType.setVisibility(View.VISIBLE);
                            lFuelTankCapacityLtr.setVisibility(View.VISIBLE);
                            lGradeAbility.setVisibility(View.VISIBLE);
                            lTurningRadius.setVisibility(View.VISIBLE);
                            lMaxSpeed.setVisibility(View.VISIBLE);
                            lAxelconfiguration.setVisibility(View.VISIBLE);
                            lTyres.setVisibility(View.VISIBLE);
                            lWheelBaseMM.setVisibility(View.VISIBLE);
                            lGroundClearance.setVisibility(View.VISIBLE);
                            lGVWGCW.setVisibility(View.VISIBLE);
                            lKerbweight.setVisibility(View.VISIBLE);
                            lFrontAxleSuspension.setVisibility(View.VISIBLE);
                            lRearAxelSuspension.setVisibility(View.VISIBLE);
                            lBodyOption.setVisibility(View.VISIBLE);
                            lDescription.setVisibility(View.VISIBLE);
                            lNoOfCylinder.setVisibility(View.VISIBLE);
                            lPayLoad.setVisibility(View.VISIBLE);
                            lBrakes.setVisibility(View.VISIBLE);
                            lLoadBodyDimensions.setVisibility(View.VISIBLE);
                            lAdditionalInfo.setVisibility(View.VISIBLE);
                        } else if (success.getCategoryName().equalsIgnoreCase("Tractor")) {
                            lEngineType.setVisibility(View.VISIBLE);
                            lEngineCC.setVisibility(View.VISIBLE);
                            lHP.setVisibility(View.VISIBLE);
                            lPowerKW.setVisibility(View.VISIBLE);
                            lDisplacementCC.setVisibility(View.VISIBLE);
                            lBoreinmm.setVisibility(View.VISIBLE);
                            lBoreStroke.setVisibility(View.VISIBLE);
                            lCoolingSystem.setVisibility(View.VISIBLE);
                            lFuelType.setVisibility(View.VISIBLE);
                            lNoOfCylinder.setVisibility(View.VISIBLE);
                            lTorque.setVisibility(View.VISIBLE);
                            lAirCleaner.setVisibility(View.VISIBLE);
                            lTransmissionType.setVisibility(View.VISIBLE);
                            lDrive.setVisibility(View.VISIBLE);
                            lMaxSpeed.setVisibility(View.VISIBLE);
                            lMaxReserveSpeed.setVisibility(View.VISIBLE);
                            lPTOType.setVisibility(View.VISIBLE);
                            lPTORPM.setVisibility(View.VISIBLE);
                            lNoOfGearsForword.setVisibility(View.VISIBLE);
                            lNoOfGearsReverse.setVisibility(View.VISIBLE);
                            lSteeringType.setVisibility(View.VISIBLE);
                            lBrakeType.setVisibility(View.VISIBLE);
                            lClutchType.setVisibility(View.VISIBLE);
                            lTotalWeight.setVisibility(View.VISIBLE);
                            lWheelBaseMM.setVisibility(View.VISIBLE);
                            lOverAllWidth.setVisibility(View.VISIBLE);
                            lOverAllLength.setVisibility(View.VISIBLE);
                            lTurningRadius.setVisibility(View.VISIBLE);
                            lTyreSizeFront.setVisibility(View.VISIBLE);
                            lTyreSizeRear.setVisibility(View.VISIBLE);
                            lHydraulicType.setVisibility(View.VISIBLE);
                            lLiftingCapacityAtStandardFrame.setVisibility(View.VISIBLE);
                            lPointLinkage.setVisibility(View.VISIBLE);
                            lPumpType.setVisibility(View.VISIBLE);
                        } else if (success.getCategoryName().equalsIgnoreCase("2 Wheeler")) {
                            lDisplacementCC.setVisibility(View.VISIBLE);
                            lMaxPower.setVisibility(View.VISIBLE);
                            lMaxTorqueKgm.setVisibility(View.VISIBLE);
                            lNoOfCylinder.setVisibility(View.VISIBLE);
                            lNoOfgears.setVisibility(View.VISIBLE);
                            lGroundClearance.setVisibility(View.VISIBLE);
                            lKerbweight.setVisibility(View.VISIBLE);
                            lFuelTankCapacityLtr.setVisibility(View.VISIBLE);
                            lFrontTyre.setVisibility(View.VISIBLE);
                            lRearTyre.setVisibility(View.VISIBLE);
                            lOverAllLength.setVisibility(View.VISIBLE);
                            lOverAllWidth.setVisibility(View.VISIBLE);
                            lWheelBaseMM.setVisibility(View.VISIBLE);
                            lDescription.setVisibility(View.VISIBLE);
                            lEngineDetails.setVisibility(View.VISIBLE);
                            lClutch.setVisibility(View.VISIBLE);
                            lTransmissionType.setVisibility(View.VISIBLE);
                            lFrontAxleSuspension.setVisibility(View.VISIBLE);
                            lRearAxelSuspension.setVisibility(View.VISIBLE);
                            lKeylessButtonStart.setVisibility(View.VISIBLE);
                            lMileage.setVisibility(View.VISIBLE);
                            lMaxSpeed.setVisibility(View.VISIBLE);
                        } else {

                        }
                    }

                } else {
                    CustomToast.customToast(this, getString(R.string.no_response));
                }
            } else {
                CustomToast.customToast(this, getString(R.string.no_response));
            }
        } else {

            CustomToast.customToast(this, getString(R.string.no_response));
            //showMessage(activity, getString(R.string.no_response));
        }
    }


    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
