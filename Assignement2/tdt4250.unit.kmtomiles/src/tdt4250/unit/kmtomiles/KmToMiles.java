package tdt4250.unit.kmtomiles;

import org.osgi.service.component.annotations.Component;

import tdt4250.unit.api.Unit;
import tdt4250.unit.util.UnitsConvert;

@Component(
		property = {
				UnitsConvert.UNIT_NAME_PROP + "=kmtomiles",
				UnitsConvert.UNIT_CONVERSION_PROP + "=x*0.621371"}
		)
public class KmToMiles extends UnitsConvert implements Unit {
}

