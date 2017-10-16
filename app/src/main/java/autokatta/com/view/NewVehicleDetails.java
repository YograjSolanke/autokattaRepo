package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import autokatta.com.R;

public class NewVehicleDetails extends AppCompatActivity {
    TextView mPointLinkage, mAbs, mAirbags, mAirCleaner, mAirCondition, mAlternateFuelType, mAuxiliary,
            mBackUpTorque, mBodyOption;
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
       /* mPointLinkage = (TextView) findViewById(R.id.txtPointLinkage1);
        mAbs = (TextView) findViewById(R.id.ABS1);
        mAirbags = (TextView) findViewById(R.id.AirBags);
        mAirCleaner = (TextView) findViewById(R.id.AirCleaner1);
        mAirCondition = (TextView) findViewById(R.id.AirCondition1);
        mAlternateFuelType = (TextView) findViewById(R.id.AlternateFuelType1);
        mAuxiliary = (TextView) findViewById(R.id.AuxiliaryHydValve1);
        mBackUpTorque = (TextView) findViewById(R.id.BackUpTorque1);
        mBodyOption = (TextView) findViewById(R.id.BodyOptions1);
        mEndDate = (TextView) findViewById(R.id.Boreinmm1);
        mEndDate = (TextView) findViewById(R.id.BoreStroke1);
        mEndDate = (TextView) findViewById(R.id.Brakes1);
        mEndDate = (TextView) findViewById(R.id.BrakeType1);
        mEndDate = (TextView) findViewById(R.id.CentralLoking1);
        mEndDate = (TextView) findViewById(R.id.ChildSeat1);
        mEndDate = (TextView) findViewById(R.id.Clutch1);
        mEndDate = (TextView) findViewById(R.id.ClutchType1);
        mEndDate = (TextView) findViewById(R.id.CoolingSystem1);
        mEndDate = (TextView) findViewById(R.id.CruiseControl1);
        mEndDate = (TextView) findViewById(R.id.CylinderCapacityCC1);
        mEndDate = (TextView) findViewById(R.id.DisplacementCC1);
        mEndDate = (TextView) findViewById(R.id.Drive1);
        mEndDate = (TextView) findViewById(R.id.DualStageAirbags1);
        mEndDate = (TextView) findViewById(R.id.EmissionNorms1);
        mEndDate = (TextView) findViewById(R.id.EngineCC1);
        mEndDate = (TextView) findViewById(R.id.EngineLocation1);
        mEndDate = (TextView) findViewById(R.id.EngineRatedRPM1);
        mEndDate = (TextView) findViewById(R.id.EngineType1);
        mEndDate = (TextView) findViewById(R.id.FIP1);
        mEndDate = (TextView) findViewById(R.id.FloorType1);
        mEndDate = (TextView) findViewById(R.id.FogLamps1);
        mEndDate = (TextView) findViewById(R.id.FoldingRearSeat1);
        mEndDate = (TextView) findViewById(R.id.FrontAxleSuspension1);
        mEndDate = (TextView) findViewById(R.id.RearAxelSuspension1);
        mEndDate = (TextView) findViewById(R.id.FrontTyre1);
        mEndDate = (TextView) findViewById(R.id.RearTyre1);
        mEndDate = (TextView) findViewById(R.id.FuelTank1);
        mEndDate = (TextView) findViewById(R.id.FuelTankCapacityLtr1);
        mEndDate = (TextView) findViewById(R.id.FrontAxleSuspension1);
        mEndDate = (TextView) findViewById(R.id.FuelType1);
        mEndDate = (TextView) findViewById(R.id.FuelSystem1);
        mEndDate = (TextView) findViewById(R.id.GearBox1);
        mEndDate = (TextView) findViewById(R.id.GradeAbility1);
        mEndDate = (TextView) findViewById(R.id.GroundClearance1);
        mEndDate = (TextView) findViewById(R.id.GVWGCW1);
        mEndDate = (TextView) findViewById(R.id.HeadRest1);
        mEndDate = (TextView) findViewById(R.id.HP1);
        mEndDate = (TextView) findViewById(R.id.HPCat1);
        mEndDate = (TextView) findViewById(R.id.Hydraulic1);
        mEndDate = (TextView) findViewById(R.id.HydraulicLiftCapacityKg1);
        mEndDate = (TextView) findViewById(R.id.HydraulicType1);
        mEndDate = (TextView) findViewById(R.id.IntegratedMusicSystem1);
        mEndDate = (TextView) findViewById(R.id.KeylessButtonStart1);
        mEndDate = (TextView) findViewById(R.id.LiftingCapacityAtStandardFrame1);
        mEndDate = (TextView) findViewById(R.id.LoadBody1);
        mEndDate = (TextView) findViewById(R.id.LoadBodyDimensions1);
        mEndDate = (TextView) findViewById(R.id.MaxPower1);
        mEndDate = (TextView) findViewById(R.id.MaxReserveSpeed1);
        mEndDate = (TextView) findViewById(R.id.MaxSpeed1);
        mEndDate = (TextView) findViewById(R.id.Mileage1);
        mEndDate = (TextView) findViewById(R.id.NoOfCylinder1);
        mEndDate = (TextView) findViewById(R.id.MaxTorqueKgm1);
        mEndDate = (TextView) findViewById(R.id.NoOfgears1);
        mEndDate = (TextView) findViewById(R.id.GroundClearance1);
        mEndDate = (TextView) findViewById(R.id.NoOfGearsForword1);
        mEndDate = (TextView) findViewById(R.id.NoOfGearsReverse1);
        mEndDate = (TextView) findViewById(R.id.OverAllLength1);
        mEndDate = (TextView) findViewById(R.id.OverAllWidth1);
        mEndDate = (TextView) findViewById(R.id.ParkingSensors1);
        mEndDate = (TextView) findViewById(R.id.PayLoad1);
        mEndDate = (TextView) findViewById(R.id.PowerKW1);
        mEndDate = (TextView) findViewById(R.id.PowerWindow1);
        mEndDate = (TextView) findViewById(R.id.PTOHP1);
        mEndDate = (TextView) findViewById(R.id.PTORPM1);
        mEndDate = (TextView) findViewById(R.id.PTOSpeed1);
        mEndDate = (TextView) findViewById(R.id.PTOType1);
        mEndDate = (TextView) findViewById(R.id.PumpType1);
        mEndDate = (TextView) findViewById(R.id.RainSensingWiper1);
        mEndDate = (TextView) findViewById(R.id.RatedRPM1);
        mEndDate = (TextView) findViewById(R.id.RearAC1);
       /* mEndDate = (TextView) findViewById(R.id.RearDefogger1);
        mEndDate = (TextView) findViewById(R.id.RearWiper1);
        mEndDate = (TextView) findViewById(R.id.SeatBelt1);
        mEndDate = (TextView) findViewById(R.id.RPTOGRPTO1);
        mEndDate = (TextView) findViewById(R.id.SeatBeltWarning1);
        mEndDate = (TextView) findViewById(R.id.SeatingCapacity1);
        mEndDate = (TextView) findViewById(R.id.SeatType1);
        mEndDate = (TextView) findViewById(R.id.Speed1);
        mEndDate = (TextView) findViewById(R.id.SportMode1);
        mEndDate = (TextView) findViewById(R.id.Steering1);
        mEndDate = (TextView) findViewById(R.id.SteeringMountedControl1);
        mEndDate = (TextView) findViewById(R.id.IntegratedMusicSystem1);
        mEndDate = (TextView) findViewById(R.id.SteeringType1);
        mEndDate = (TextView) findViewById(R.id.StrokeInMM1);
        mEndDate = (TextView) findViewById(R.id.SunRoofMoonRoof1);
        mEndDate = (TextView) findViewById(R.id.TMPS1);
        mEndDate = (TextView) findViewById(R.id.Torque1);
        mEndDate = (TextView) findViewById(R.id.TotalWeight1);
        mEndDate = (TextView) findViewById(R.id.TransmissionType1);
        mEndDate = (TextView) findViewById(R.id.TuboChargeNutralAspirated1);
        mEndDate = (TextView) findViewById(R.id.TurningRadius1);
        mEndDate = (TextView) findViewById(R.id.TurningRadiusOfBrakes1);
        mEndDate = (TextView) findViewById(R.id.TypeOfClutchAndBrakePedal1);
        mEndDate = (TextView) findViewById(R.id.Tyres1);
        mEndDate = (TextView) findViewById(R.id.TyreSize1);
        mEndDate = (TextView) findViewById(R.id.TyreSizeFront1);
        mEndDate = (TextView) findViewById(R.id.TyreSizeRear1);
        mEndDate = (TextView) findViewById(R.id.WarrantyKilometer1);
        mEndDate = (TextView) findViewById(R.id.WarrantyYear1);
        mEndDate = (TextView) findViewById(R.id.WebSite1);
        mEndDate = (TextView) findViewById(R.id.WeightKg1);
        mEndDate = (TextView) findViewById(R.id.WheelBaseMM1);
        mEndDate = (TextView) findViewById(R.id.EngineDetails1);
        mEndDate = (TextView) findViewById(R.id.Description1);*/


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

}
