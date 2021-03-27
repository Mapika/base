package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainController mockController;
    TrainUser mockUser;
    TrainSensor sensor;

    @Before
    public void before() {
        mockController = mock(TrainController.class);
        mockUser = mock(TrainUser.class);
        sensor = new TrainSensorImpl(mockController, mockUser);
        sensor.overrideSpeedLimit(20);
    }

    @Test
    public void testNormalSpeed() {
        sensor.overrideSpeedLimit(25);
        verify(mockUser, times(0)).setAlarmState(true);
    }

    @Test
    public void testLowerSpeedMargin() {
        sensor.overrideSpeedLimit(-1);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void testUpperSpeedMargin() {
        sensor.overrideSpeedLimit(501);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void testRelativeSpeedMargin() {
        when(mockController.getReferenceSpeed()).thenReturn(40);
        sensor.overrideSpeedLimit(19);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void speedLimitTest() {
        Assert.assertEquals(20, sensor.getSpeedLimit());
    }
}
