package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
            RearWiper, SeatBelt, RPTOGRPTO, SeatBeltWarning, SeatingCapacity, SeatType, Speed, SportMode, Steering, SteeringMountedControl, SteeringType, StrokeInMM, SunRoofMoonRoof, TMPS, Torque, TotalWeight, TransmissionType, TuboChargeNutralAspirated, TurningRadius, TurningRadiusOfBrakes, TypeOfClutchAndBrakePedal, Tyres, TyreSize, TyreSizeFront, TyreSizeRear, WarrantyKilometer, WarrantyYear, WebSite, WeightKg, WheelBaseMM, EngineDetails, Description;
    int newVehicleId;

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
