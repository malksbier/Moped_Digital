package com.malksbier.MopedDigital.io.virtual;

import java.util.ArrayList;

import com.malksbier.MopedDigital.data.mopedStatus.MopedStatus;
import com.malksbier.MopedDigital.util.ErrorCodes;


public class HazardRecognizer {
     public static String TAG = "[HazardRecognizer] ";

    // TOCAN class could be reworked without array. instead counter of valid switches
    // Problem: hazzard light cant be on when switch is off

    private enum IndicatorState {
        off,
        left,
        right,
        error
    }
    private class IndicatorTimestamp {
        public long timeWhenChanged;
        public IndicatorState state;

        IndicatorTimestamp(IndicatorState state) {
            this.timeWhenChanged = System.currentTimeMillis();
            this.state = state;
        }
    }

    
    public static int HAZARD_TURN_OFF_DURATION = 2000;
    public static int MAX_SWITCHING_DURATION_FOR_ACTIVATION = 1000;
    public static int TIMESTAMPS_LENGTH = 5;
    //private static int TIMES_TO_SWITCH_ON_FOR_ACTIVATION = TIMESTAMPS_LENGTH;

    private ArrayList<IndicatorTimestamp> timestamps;

    public HazardRecognizer() {
        timestamps = new ArrayList<>();
        timestamps.add(new IndicatorTimestamp(IndicatorState.off));
    }
    
    public MopedStatus update(MopedStatus mopedStatus) {

        IndicatorState currentIndicatorState = getState(mopedStatus);

        // case there are both blinkers active
        if(currentIndicatorState == IndicatorState.error) {
            mopedStatus.setFault(mopedStatus.getFault() + ErrorCodes.IndicatorState_Error());
            System.out.println(TAG + "setFault, Indicator signals misleading");
        }

       
        if(mopedStatus.getHazardLights()) {
             // when Hazard is on, turn of if is off for X seconds
            if(currentIndicatorState == IndicatorState.off) {
                if(System.currentTimeMillis() - timestamps.get(0).timeWhenChanged >= HAZARD_TURN_OFF_DURATION) {
                    mopedStatus.setHazardLights(false);
                    System.out.println(TAG + "deactivated Hazard");
                }
                return mopedStatus;
            }
        } else {
            /*  when Hazard is off,
             *      - if indicator is off skip
             *      else 
             *      - track change of On and Off in timestamps
             * 
             *      then check if should turn on
             */
            if(currentIndicatorState == IndicatorState.off) {
                return mopedStatus;
            } else {
                // when switched
                if(currentIndicatorState != timestamps.get(0).state) {
                    timestamps.add(0, new IndicatorTimestamp(currentIndicatorState));
                }
            }

            //check if valid
            boolean shouldTurnOn = true;
            boolean timestampsAreLeftOrRight = true;
            for(int i=0;i<timestamps.size();i++) {
                if(!(timestamps.get(i).state == IndicatorState.left || timestamps.get(i).state == IndicatorState.right)) {
                    timestampsAreLeftOrRight = false;
                    break;
                }
            }

            if(timestampsAreLeftOrRight) {
                for(int i=0;i<timestamps.size()-1;i++) {
                    long timeDiffrenceBetweenSwitch = timestamps.get(i).timeWhenChanged - timestamps.get(i+1).timeWhenChanged;

                    if(timeDiffrenceBetweenSwitch > MAX_SWITCHING_DURATION_FOR_ACTIVATION) {
                        shouldTurnOn = false;
                        break;
                    }
                }

                if(shouldTurnOn) {
                    mopedStatus.setHazardLights(true);
                    System.out.println(TAG + "activated Hazard");
                }
            }
        }

        //remove last when neccecary
        if(timestamps.size() > TIMESTAMPS_LENGTH) {
            timestamps.remove(TIMESTAMPS_LENGTH - 1);
        }

        return mopedStatus;
    }

    

    private static IndicatorState getState(MopedStatus mopedStatus) {
        if(!mopedStatus.getInput().getIndicatorLeft() && !mopedStatus.getInput().getIndicatorRight()) {
            return IndicatorState.off;
        } else 
        if(mopedStatus.getInput().getIndicatorLeft()) {
            return IndicatorState.left;
        } else 
        if(mopedStatus.getInput().getIndicatorLeft()) {
            return IndicatorState.right;
        } else {
        //if(mopedStatus.getIndicatorLeft() && mopedStatus.getIndicatorRight()) {
            return IndicatorState.error;
        }
    }
}
