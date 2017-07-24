package com.davidmata.grupovidanta.listversion.controller;

import com.davidmata.grupovidanta.listversion.Earthquake;
import com.davidmata.grupovidanta.listversion.interfaces.EarthquakeInterface;

import java.util.List;

/**
 * Created by davidmata on 08/11/2016.
 */


public class EarthquakeController implements  EarthquakeInterface{

    EarthquakeInterface earthquakeInterface;
    public EarthquakeController(EarthquakeInterface earthquakeInterface){
        this.earthquakeInterface = earthquakeInterface;
    }


    @Override
    public List<Earthquake> getEarthqueakes() {

        return this.earthquakeInterface.getEarthqueakes();
    }
}
